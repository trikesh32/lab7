package client.controllers;

import client.auth.UserHandler;
import client.ui.DialogManager;
import client.utils.Localizator;
import common.models.Coordinates;
import common.models.FuelType;
import common.models.Vehicle;
import common.models.VehicleType;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Arrays;

public class EditController {
    private Stage stage;
    private Vehicle vehicle;
    private Localizator localizator;

    @FXML
    private Label titleLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label capacityLabel;
    @FXML
    private Label enginePowerLabel;
    @FXML
    private Label vehicleTypeLabel;
    @FXML
    private Label fuelTypeLabel;

    @FXML
    private TextField nameField;
    @FXML
    private TextField xField;
    @FXML
    private TextField yField;
    @FXML
    private TextField enginePowerField;
    @FXML
    private TextField capacityField;

    @FXML
    private ChoiceBox<String> vehicleTypeBox;
    @FXML
    private ChoiceBox<String> fuelTypeBox;

    @FXML
    private Button cancelButton;
    @FXML
    private Button okButton;

    @FXML
    void initialize(){
        cancelButton.setOnAction(event -> stage.close());
        var vehicleTypes = FXCollections.observableArrayList(Arrays.stream(VehicleType.values()).map(Enum::toString).toList());
        vehicleTypeBox.setItems(vehicleTypes);
        vehicleTypeBox.setStyle("-fx-font: 12px \"Sergoe UI\";");

        var fuelTypes = FXCollections.observableArrayList(Arrays.stream(FuelType.values()).map(Enum::toString).toList());
        fuelTypes.add("<null>");
        fuelTypeBox.setItems(fuelTypes);
        fuelTypeBox.setStyle("-fx-font: 12px \"Sergoe UI\";");

        yField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            try {
                var val = Float.parseFloat(newValue);
                yField.setText(newValue);
            } catch (Exception e){
                if (newValue.isEmpty()){
                    yField.setText("");
                    return;
                }
                yField.setText(oldValue);
            }
        });
        Arrays.asList(xField, enginePowerField, capacityField).forEach(field -> {
            field.textProperty().addListener((observableValue, oldValue, newValue) -> {
                try {
                    var val = Integer.parseInt(newValue);
                    field.setText(newValue);
                } catch (Exception e){
                    if (newValue.isEmpty()){
                        field.setText("");
                        return;
                    }
                    field.setText(oldValue);
                }
            });
        });
    }

    @FXML
    public void ok(){
        nameField.setText(nameField.getText().trim());
        Vehicle newVehicle;
        var name = nameField.getText();
        var x = Integer.parseInt(xField.getText());
        var y = Float.parseFloat(yField.getText());
        var capacity = Integer.parseInt(capacityField.getText());
        var enginePower = Integer.parseInt(enginePowerField.getText());
        var vehicleType = vehicleTypeBox.getValue() == null ? null : VehicleType.valueOf(vehicleTypeBox.getValue());
        var fuelType = fuelTypeBox.getValue() == null || fuelTypeBox.getValue().equals("<null>") ? null : FuelType.valueOf(fuelTypeBox.getValue());
        newVehicle = new Vehicle(1, name, new Coordinates(x, y), enginePower, capacity, vehicleType, fuelType, UserHandler.getCurrentUser());
        if (!newVehicle.check_validity()){
            System.out.println(newVehicle);
            DialogManager.alert("NotValidData", localizator);
        } else{
            vehicle = newVehicle;
            stage.close();
        }
    }

    public Vehicle getVehicle(){
        var tmpVehicle = vehicle;
        vehicle = null;
        return tmpVehicle;
    }
    public void clear(){
        nameField.clear();
        xField.clear();
        yField.clear();
        enginePowerField.clear();
        capacityField.clear();
        vehicleTypeBox.valueProperty().setValue(null);
        fuelTypeBox.valueProperty().setValue(null);
    }

    public void fill(Vehicle vehicle){
        nameField.setText(vehicle.getName());
        xField.setText(Integer.toString(vehicle.getX()));
        yField.setText(Float.toString(vehicle.getY()));
        enginePowerField.setText(Integer.toString(vehicle.getEnginePower()));
        capacityField.setText(Integer.toString(vehicle.getCapacity()));
        vehicleTypeBox.setValue(vehicle.getVehicleType().toString());
        fuelTypeBox.setValue(vehicle.getFuelType() == null ? null : vehicle.getFuelType().toString());
    }

    public void changeLanguage(){
        titleLabel.setText(localizator.getKeyString("EditTitle"));
        cancelButton.setText(localizator.getKeyString("Cancel"));
    }
    public void show(){
        if (!stage.isShowing()) stage.showAndWait();
    }
    public void setStage(Stage stage){
        this.stage = stage;
    }
    public void setLocalizator(Localizator localizator){
        this.localizator = localizator;
    }
}
