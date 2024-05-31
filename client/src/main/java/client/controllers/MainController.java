package client.controllers;

import client.auth.UserHandler;
import client.network.UDPClient;
import client.ui.DialogManager;
import client.utils.Localizator;
import client.utils.TimeoutException;
import common.models.Vehicle;
import common.models.VehicleType;
import common.network.requests.*;
import common.network.responses.Response;
import common.network.responses.ResponseInfo;
import common.network.responses.ResponseInteger;
import common.network.responses.ResponseVehicles;
import javafx.animation.FillTransition;
import javafx.animation.PathTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Platform;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import client.script.ScriptExecutor;
import client.utils.VehiclePresenter;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.text.MessageFormat;
import java.util.*;

public class MainController {
    private Localizator localizator;
    private UDPClient client;
    private Runnable authCallback;
    private volatile boolean isRefreshing = false;
    private Stack<Vehicle> collection;
    private final HashMap<String, Locale> localeMap = new HashMap<>() {{
        put("Русский", new Locale("ru", "RU"));
        put("Norsk", new Locale("no", "NO"));
        put("Français", new Locale("fr", "FR"));
        put("Español", new Locale("es", "DO"));
    }};
    private HashMap<String, Color> colorMap;
    private HashMap<Integer, Label> infoMap;
    private Random random;
    private EditController editController;
    private Stage stage;
    @FXML
    private ComboBox<String> languageComboBox;
    @FXML
    private Label userLabel;
    @FXML
    private Button helpButton;
    @FXML
    private Button infoButton;
    @FXML
    private Button addButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button removeByIdButton;
    @FXML
    private Button clearButton;
    @FXML
    private Button executeScriptButton;
    @FXML
    private Button removeLowerButton;
    @FXML
    private Button sumOfCapacityButton;
    @FXML
    private Button filterByCapacityButton;
    @FXML
    private Button filterLessThanTypeButton;
    @FXML
    private Button exitButton;
    @FXML
    private Button logoutButton;

    @FXML
    private Tab tableTab;
    @FXML
    private TableView<Vehicle> tableTable;

    @FXML
    private TableColumn<Vehicle, String> ownerColumn;
    @FXML
    private TableColumn<Vehicle, Integer> idColumn;
    @FXML
    private TableColumn<Vehicle, String> nameColumn;
    @FXML
    private TableColumn<Vehicle, Integer> xColumn;
    @FXML
    private TableColumn<Vehicle, Float> yColumn;
    @FXML
    private TableColumn<Vehicle, String> dateColumn;
    @FXML
    private TableColumn<Vehicle, Integer> enginePowerColumn;
    @FXML
    private TableColumn<Vehicle, Integer> capacityColumn;
    @FXML
    private TableColumn<Vehicle, String> vehicleTypeColumn;
    @FXML
    private TableColumn<Vehicle, String> fuelTypeColumn;

    @FXML
    private Tab visualTab;
    @FXML
    private AnchorPane visualPane;

    @FXML
    public void initialize() {
        colorMap = new HashMap<>();
        infoMap = new HashMap<>();
        random = new Random();

        languageComboBox.setItems(FXCollections.observableArrayList(localeMap.keySet()));
        languageComboBox.setStyle("-fx-font: 13px \"Sergoe UI\";");
        languageComboBox.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            localizator.setBundle(ResourceBundle.getBundle("client.locales.gui", localeMap.get(newValue)));
            changeLanguage();
        });

        ownerColumn.setCellValueFactory(vehicle -> new SimpleStringProperty(vehicle.getValue().getCreator().getName()));
        idColumn.setCellValueFactory(vehicle -> new SimpleIntegerProperty(vehicle.getValue().getId()).asObject());
        nameColumn.setCellValueFactory(vehicle -> new SimpleStringProperty(vehicle.getValue().getName()));
        xColumn.setCellValueFactory(vehicle -> new SimpleIntegerProperty(vehicle.getValue().getCoordinates().getX()).asObject());
        yColumn.setCellValueFactory(vehicle -> new SimpleFloatProperty(vehicle.getValue().getCoordinates().getY()).asObject());
        dateColumn.setCellValueFactory(vehicle -> new SimpleStringProperty(localizator.getDate(vehicle.getValue().getCreationDate())));
        enginePowerColumn.setCellValueFactory(vehicle -> new SimpleIntegerProperty(vehicle.getValue().getEnginePower()).asObject());
        capacityColumn.setCellValueFactory(vehicle -> new SimpleIntegerProperty(vehicle.getValue().getCapacity()).asObject());
        vehicleTypeColumn.setCellValueFactory(vehicle -> new SimpleStringProperty(vehicle.getValue().getVehicleType().toString()));
        fuelTypeColumn.setCellValueFactory(vehicle -> new SimpleStringProperty(vehicle.getValue().getFuelType() != null ? vehicle.getValue().getFuelType().toString() : null));

        tableTable.setRowFactory(tableView -> {
            var row = new TableRow<Vehicle>();
            row.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getClickCount() == 2 && !row.isEmpty()) {
                    doubleClickUpdate(row.getItem());
                }
            });
            return row;
        });
        visualTab.setOnSelectionChanged(event -> visualize(false));
    }

    @FXML
    public void exit() {
        System.exit(0);
    }

    @FXML
    public void logout() {
        UserHandler.setCurrentUser(null);
        UserHandler.setCurrentLanguage("Русский");
        setRefreshing(false);
        authCallback.run();
    }

    @FXML
    public void help() {
        DialogManager.createAlert(localizator.getKeyString("Help"), localizator.getKeyString("HelpResult"), Alert.AlertType.INFORMATION, true);
    }

    @FXML
    public void info() {
        try {
            var response = (ResponseInfo) client.sendAndReceiveCommand(new Request("info", UserHandler.getCurrentUser()));
            var formatted = MessageFormat.format(localizator.getKeyString("InfoResult"), response.getType(), response.getSize(), localizator.getDate(response.getLastSaveTime()), localizator.getDate(response.getLastInitTime()));
            DialogManager.createAlert(localizator.getKeyString("Info"), formatted, Alert.AlertType.INFORMATION, true);
        } catch (IOException e) {
            DialogManager.alert("UnavailableError", localizator);
        } catch (TimeoutException e) {
            DialogManager.alert("Timeout", localizator);
        }
    }

    @FXML
    public void add() {
        editController.clear();
        editController.show();
        var vehicle = editController.getVehicle();
        if (vehicle != null) {
            vehicle = vehicle.copy(vehicle.getId(), UserHandler.getCurrentUser());
            try {
                var response = (Response) client.sendAndReceiveCommand(new RequestWithVehicleArg("add", vehicle, UserHandler.getCurrentUser()));
                if (!response.getExitCode()) {
                    DialogManager.alert(response.getMessage(), localizator);
                    return;
                }
                loadCollection();
                DialogManager.createAlert(localizator.getKeyString("Add"), localizator.getKeyString("Success"), Alert.AlertType.INFORMATION, false);
            } catch (TimeoutException e) {
                DialogManager.alert("Timeout", localizator);
            } catch (IOException e) {
                DialogManager.alert("UnavailableError", localizator);
            }
        }
    }

    @FXML
    public void update() {
        Optional<String> input = DialogManager.createDialog(localizator.getKeyString("Update"), "ID:");
        if (input.isPresent() && !input.get().isEmpty()) {
            try {
                var id = Integer.parseInt(input.orElse(""));
                var vehicle = collection.stream().filter(v -> v.getId() == id).findAny().orElse(null);
                if (vehicle == null) {
                    DialogManager.alert("ObjectNotFound", localizator);
                    return;
                }
                if (vehicle.getCreator().getId() != UserHandler.getCurrentUser().getId()) {
                    DialogManager.alert("Forbidden", localizator);
                    return;
                }
                doubleClickUpdate(vehicle, false);
            } catch (NumberFormatException e){
                DialogManager.alert("NumberFormatException", localizator);
            }
        }
    }

    @FXML
    public void removeById(){
        Optional<String> input = DialogManager.createDialog(localizator.getKeyString("Update"), "ID:");
        if (input.isPresent() && !input.get().isEmpty()){
            try{
                var id = Integer.parseInt(input.orElse(""));
                var vehicle = collection.stream().filter(v -> v.getId() == id).findAny().orElse(null);
                if (vehicle == null) {
                    DialogManager.alert("ObjectNotFound", localizator);
                    return;
                }
                if (vehicle.getCreator().getId() != UserHandler.getCurrentUser().getId()) {
                    DialogManager.alert("Forbidden", localizator);
                    return;
                }
                var response = (Response) client.sendAndReceiveCommand(new RequestWithIntArg("remove_by_id", id, UserHandler.getCurrentUser()));
                if (!response.getExitCode()){
                    DialogManager.alert(response.getMessage(), localizator);
                    return;
                }
                loadCollection();
                DialogManager.createAlert(localizator.getKeyString("RemoveById"), localizator.getKeyString("RemoveByIDSuc"), Alert.AlertType.INFORMATION, false);
            } catch (IOException e){
                DialogManager.alert("UnavailableError", localizator);
            } catch (NumberFormatException e) {
                DialogManager.alert("NumberFormatException", localizator);
            } catch (TimeoutException e) {
                DialogManager.alert("Timeout", localizator);
            }
        }
    }

    @FXML
    public void clear(){
        try {
            var response = (Response) client.sendAndReceiveCommand(new Request("clear", UserHandler.getCurrentUser()));
            if (!response.getExitCode()){
                DialogManager.alert(response.getMessage(), localizator);
                return;
            }
            loadCollection();
            DialogManager.createAlert(localizator.getKeyString("Clear"), localizator.getKeyString("ClearSuc"), Alert.AlertType.INFORMATION, false);
        } catch (IOException e){
            DialogManager.alert("UnavailableError", localizator);
        }  catch (TimeoutException e) {
            DialogManager.alert("Timeout", localizator);
        }
    }

    @FXML
    public void executeScript(){
        var chooser = new FileChooser();
        chooser.setInitialDirectory(new File("."));
        var file = chooser.showOpenDialog(stage);
        if (file != null){
            var result = (new ScriptExecutor(this, localizator)).run(file.getAbsolutePath());
            if (result == ScriptExecutor.ExitCode.ERROR){
                DialogManager.alert("ScriptExecutionErr", localizator);
            } else {
                DialogManager.info("ScriptExecutionSuc", localizator);
            }
        }
    }

    @FXML
    public void removeLower(){
        editController.clear();
        editController.show();
        var vehicle = editController.getVehicle();
        if (vehicle != null) {
            vehicle = vehicle.copy(vehicle.getId(), UserHandler.getCurrentUser());
            try {
                var response = (Response) client.sendAndReceiveCommand(new RequestWithVehicleArg("remove_lower", vehicle, UserHandler.getCurrentUser()));
                if (!response.getExitCode()) {
                    DialogManager.alert(response.getMessage(), localizator);
                    return;
                }
                loadCollection();
                DialogManager.createAlert(localizator.getKeyString("RemoveLower"), localizator.getKeyString("Success"), Alert.AlertType.INFORMATION, false);
            } catch (TimeoutException e) {
                DialogManager.alert("Timeout", localizator);
            } catch (IOException e) {
                DialogManager.alert("UnavailableError", localizator);
            }
        }
    }

    @FXML
    public void sumOfCapacity(){
        try {
            var response = (ResponseInteger) client.sendAndReceiveCommand(new Request("sum_of_capacity", UserHandler.getCurrentUser()));
            if (!response.getExitCode()) {
                DialogManager.alert(response.getMessage(), localizator);
                return;
            }
            DialogManager.createAlert(localizator.getKeyString("SumOfCapacity"), localizator.getKeyString("Result") + " " + response.getRes(), Alert.AlertType.INFORMATION, false);
        } catch (TimeoutException e) {
            DialogManager.alert("Timeout", localizator);
        } catch (IOException e) {
            DialogManager.alert("UnavailableError", localizator);
        }
    }

    @FXML
    public void filterByCapacity(){
        var dialogCapacity = new TextInputDialog();
        dialogCapacity.setTitle(localizator.getKeyString("FilterByCapacity"));
        dialogCapacity.setHeaderText(null);
        dialogCapacity.setContentText(localizator.getKeyString("Capacity"));
        var capacity =  dialogCapacity.showAndWait();
        if (capacity.isPresent()){
            try{
                var cap = Integer.parseInt(capacity.get());
                var response = (ResponseVehicles) client.sendAndReceiveCommand(new RequestWithIntArg("filter_by_capacity", cap, UserHandler.getCurrentUser()));
                var result = new StringBuilder();
                response.getFilteredVehicles().forEach(product -> {
                    result.append(new VehiclePresenter(localizator).describe(product)).append("\n\n");
                });

                DialogManager.createAlert(
                        localizator.getKeyString("FilterByCapacity"),
                        MessageFormat.format(localizator.getKeyString("FilterByCapacity"), String.valueOf(response.getFilteredVehicles().size())) + result,
                        Alert.AlertType.INFORMATION,
                        true
                );
            } catch (IllegalArgumentException e){
                DialogManager.alert("WrongNumber", localizator);
            } catch (TimeoutException e) {
                DialogManager.alert("Timeout", localizator);
            } catch (IOException e) {
                DialogManager.alert("UnavailableError", localizator);
            }
        }
    }

    @FXML
    public void filterLessThanType(){
        var dialogType = new TextInputDialog();
        dialogType.setTitle(localizator.getKeyString("FilterLessThanType"));
        dialogType.setHeaderText(null);
        dialogType.setContentText(localizator.getKeyString("VehicleType"));
        var type =  dialogType.showAndWait();
        if (type.isPresent()){
            try{
                var vehicleType = VehicleType.valueOf(type.get().trim().toUpperCase());
                var response = (ResponseVehicles) client.sendAndReceiveCommand(new RequestWithTypeArg("filter_less_than_type", vehicleType, UserHandler.getCurrentUser()));
                var result = new StringBuilder();
                response.getFilteredVehicles().forEach(product -> {
                    result.append(new VehiclePresenter(localizator).describe(product)).append("\n\n");
                });

                DialogManager.createAlert(
                        localizator.getKeyString("FilterLessThanType"),
                        MessageFormat.format(localizator.getKeyString("FilterLessThanTypeResult"), String.valueOf(response.getFilteredVehicles().size())) + result,
                        Alert.AlertType.INFORMATION,
                        true
                );
            } catch (IllegalArgumentException e){
                DialogManager.alert("WrongType", localizator);
            } catch (TimeoutException e) {
                DialogManager.alert("Timeout", localizator);
            } catch (IOException e) {
                DialogManager.alert("UnavailableError", localizator);
            }
        }
    }

    public void refresh(){
        Thread refresher = new Thread(() -> {
            while (isRefreshing()){
                Platform.runLater(this::loadCollection);
                try{
                    Thread.sleep(5_000);
                } catch (InterruptedException ignored){
                    Thread.currentThread().interrupt();
                    System.out.println("Thread was interrupted, Failed to complete operation");
                }
            }
        });
        refresher.start();
    }

    public void visualize(boolean refresh){
        visualPane.getChildren().clear();
        infoMap.clear();

        for (var vehicle : tableTable.getItems()){
            var creatorName = vehicle.getCreator().getName();
            if (!colorMap.containsKey(creatorName)){
                var r = random.nextDouble();
                var g = random.nextDouble();
                var b = random.nextDouble();
                if (Math.abs(r - g) + Math.abs(r - b) + Math.abs(b - g) < 0.6) {
                    r += (1 - r) / 1.4;
                    g += (1 - g) / 1.4;
                    b += (1 - b) / 1.4;
                }
                colorMap.put(creatorName, Color.color(r, g, b));
            }
            var size = Math.min(125, Math.max(75, vehicle.getCapacity() * 2) / 2);
            var circle = new Circle(size, colorMap.get(creatorName));
            double x = Math.abs(vehicle.getX());
            while (x >= 720){
                x = x / 10;
            }
            double y = Math.abs(vehicle.getY());
            while (y >= 370){
                y = y / 3;
            }
            if (y < 100) y += 125;
            var id = new Text("#" + String.valueOf(vehicle.getId()));
            var info = new Label(new VehiclePresenter(localizator).describe(vehicle));
            info.setVisible(false);
            circle.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getClickCount() == 2){
                    doubleClickUpdate(vehicle);
                }
            });
            circle.setOnMouseEntered(mouseEvent -> {
                id.setVisible(false);
                info.setVisible(true);
                circle.setFill(colorMap.get(creatorName).brighter());
            });
            circle.setOnMouseExited(mouseEvent -> {
                info.setVisible(false);
                id.setVisible(true);
                circle.setFill(colorMap.get(creatorName).brighter());
            });
            id.setFont(Font.font("Segoe UI", size / 1.4));
            info.setStyle("-fx-background-color: white; -fx-border-color: #c0c0c0; -fx-border-width: 2");
            info.setFont(Font.font("Segoe UI", 15));

            visualPane.getChildren().add(circle);
            visualPane.getChildren().add(id);

            infoMap.put(vehicle.getId(), info);
            if (!refresh) {
                var path = new Path();
                path.getElements().add(new MoveTo(-50, -150));
                path.getElements().add(new HLineTo(x));
                path.getElements().add(new VLineTo(y));
                id.translateXProperty().bind(circle.translateXProperty().subtract(id.getLayoutBounds().getWidth() / 2));
                id.translateYProperty().bind(circle.translateYProperty().add(id.getLayoutBounds().getHeight() / 4));
                info.translateXProperty().bind(circle.translateXProperty().add(circle.getRadius()));
                info.translateYProperty().bind(circle.translateYProperty().subtract(120));
                var transition = new PathTransition();
                transition.setDuration(Duration.millis(750));
                transition.setNode(circle);
                transition.setPath(path);
                transition.setOrientation(PathTransition.OrientationType.NONE);
                transition.play();
            } else {
                circle.setCenterX(x);
                circle.setCenterY(y);
                info.translateXProperty().bind(circle.centerXProperty().add(circle.getRadius()));
                info.translateYProperty().bind(circle.centerYProperty().subtract(120));
                id.translateXProperty().bind(circle.centerXProperty().subtract(id.getLayoutBounds().getWidth() / 2));
                id.translateYProperty().bind(circle.centerYProperty().add(id.getLayoutBounds().getHeight() / 4));
                var darker = new FillTransition(Duration.millis(750), circle);
                darker.setFromValue(colorMap.get(creatorName));
                darker.setToValue(colorMap.get(creatorName).darker().darker());
                var brighter = new FillTransition(Duration.millis(750), circle);
                brighter.setFromValue(colorMap.get(creatorName).darker().darker());
                brighter.setToValue(colorMap.get(creatorName));
                var transition = new SequentialTransition(darker, brighter);
                transition.play();
            }
        }
        for (var id : infoMap.keySet()){
            visualPane.getChildren().add(infoMap.get(id));
        }
    }
    private void loadCollection(){
        try {
            var response = (ResponseVehicles) client.sendAndReceiveCommand(new Request("show", UserHandler.getCurrentUser()));
            if (!response.getExitCode()){
                DialogManager.alert(response.getMessage(), localizator);
                return;
            }
            setCollection(response.getFilteredVehicles());
            visualize(true);
        } catch (IOException e){
            DialogManager.alert("UnavailableError", localizator);
        }  catch (TimeoutException e) {
            DialogManager.alert("Timeout", localizator);
        }
    }

    private void doubleClickUpdate(Vehicle vehicle){
        doubleClickUpdate(vehicle, true);
    }
    private void doubleClickUpdate(Vehicle vehicle, boolean ignoreAnotherUser){
        if (ignoreAnotherUser && vehicle.getCreator().getId() != UserHandler.getCurrentUser().getId()) return;
        editController.fill(vehicle);
        editController.show();
        var updatedVehicle = editController.getVehicle();
        if (updatedVehicle != null){
            updatedVehicle = updatedVehicle.copy(vehicle.getId(), UserHandler.getCurrentUser());
            try {
                if (!updatedVehicle.check_validity()){
                    DialogManager.alert("NotValidData", localizator);
                    return;
                }
                var response = (Response) client.sendAndReceiveCommand(new RequestWithIntAndVehicleArg("update", vehicle.getId(), updatedVehicle, UserHandler.getCurrentUser()));
                if (!response.getExitCode()){
                    DialogManager.alert(response.getMessage(), localizator);
                    return;
                }
                loadCollection();
                DialogManager.createAlert(localizator.getKeyString("Update"), localizator.getKeyString("UpdateSuc"), Alert.AlertType.INFORMATION, false);
            } catch (IOException e){
                DialogManager.alert("UnavailableError", localizator);
            }  catch (TimeoutException e) {
                DialogManager.alert("Timeout", localizator);
            }
        }
    }

    public void changeLanguage() {
        userLabel.setText(localizator.getKeyString("UserLabel") + " " + UserHandler.getCurrentUser().getName());

        exitButton.setText(localizator.getKeyString("Exit"));
        logoutButton.setText(localizator.getKeyString("LogOut"));
        helpButton.setText(localizator.getKeyString("Help"));
        infoButton.setText(localizator.getKeyString("Info"));
        addButton.setText(localizator.getKeyString("Add"));
        updateButton.setText(localizator.getKeyString("Update"));
        removeByIdButton.setText(localizator.getKeyString("RemoveByID"));
        clearButton.setText(localizator.getKeyString("Clear"));
        executeScriptButton.setText(localizator.getKeyString("ExecuteScript"));
        removeLowerButton.setText(localizator.getKeyString("RemoveLower"));
        sumOfCapacityButton.setText(localizator.getKeyString("SumOfCapacity"));
        filterByCapacityButton.setText(localizator.getKeyString("FilterByCapacity"));
        filterLessThanTypeButton.setText(localizator.getKeyString("FilterLessThanType"));
        tableTab.setText(localizator.getKeyString("TableTab"));
        visualTab.setText(localizator.getKeyString("VisualTab"));
        ownerColumn.setText(localizator.getKeyString("Owner"));
        nameColumn.setText(localizator.getKeyString("Name"));
        dateColumn.setText(localizator.getKeyString("CreationDate"));
        enginePowerColumn.setText(localizator.getKeyString("EnginePower"));
        capacityColumn.setText(localizator.getKeyString("Capacity"));
        vehicleTypeColumn.setText(localizator.getKeyString("VehicleType"));
        fuelTypeColumn.setText(localizator.getKeyString("FuelType"));
        editController.changeLanguage();
        loadCollection();
    }

    public void setCollection(List<Vehicle> collection){
        this.collection = (Stack<Vehicle>) collection;
        tableTable.setItems(FXCollections.observableArrayList(collection));
    }

    public void setAuthCallback(Runnable authCallback){
        this.authCallback = authCallback;
    }
    public void setContext(UDPClient client, Localizator localizator, Stage stage) {
        this.client = client;
        this.localizator = localizator;
        this.stage = stage;
        this.stage.setOnCloseRequest(event -> {System.exit(0);});

        languageComboBox.setValue(UserHandler.getCurrentLanguage());
        localizator.setBundle(ResourceBundle.getBundle("client.locales.gui", localeMap.get(UserHandler.getCurrentLanguage())));
        changeLanguage();

        userLabel.setText(localizator.getKeyString("UserLabel") + " " + UserHandler.getCurrentUser().getName());
    }
    public boolean isRefreshing() {
        return isRefreshing;
    }

    public void setRefreshing(boolean refreshing) {
        isRefreshing = refreshing;
    }

    public void setEditController(EditController editController) {
        this.editController = editController;
        editController.changeLanguage();
    }
}
