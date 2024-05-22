package client.controllers;

import client.auth.UserHandler;
//import client.network.UDPClient;
//import client.ui.DialogManager;
//import client.utility.Localizator;
//import common.exceptions.*;
//import common.network.requests.AuthenticateRequest;
//import common.network.requests.RegisterRequest;
//import common.network.responses.AuthenticateResponse;
//import common.network.responses.RegisterResponse;
import client.network.UDPClient;
import client.ui.DialogManager;
import client.utils.*;
import common.network.requests.RequestWithUserArg;
import common.network.responses.ResponseUser;
import common.user.User;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.*;

public class AuthController {
    private Runnable callback;
    private Localizator localizator;
    private UDPClient client;
    private final HashMap<String, Locale> localeMap = new HashMap<>() {{
        put("Русский", new Locale("ru", "RU"));
        put("Norsk", new Locale("no", "NO"));
        put("Français", new Locale("fr", "FR"));
        put("Español", new Locale("es", "DO"));
    }};

    @FXML
    private Label titleLabel;
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button okButton;
    @FXML
    private CheckBox signUpButton;
    @FXML
    private ComboBox<String> languageComboBox;

    @FXML
    void initialize() {
        languageComboBox.setItems(FXCollections.observableArrayList(localeMap.keySet()));

        languageComboBox.setValue(UserHandler.getCurrentLanguage());
        languageComboBox.setStyle("-fx-font: 13px \"Sergoe UI\";");

        languageComboBox.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            localizator.setBundle(ResourceBundle.getBundle("client.locales.gui", localeMap.get(newValue)));
            UserHandler.setCurrentLanguage(newValue);
            changeLanguage();
        });
        loginField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue.matches(".{0,40}")) {
                loginField.setText(oldValue);
            }
        });
        passwordField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue.matches("\\S*")) {
                passwordField.setText(oldValue);
            }
        });
    }

    @FXML
    void ok() {
        if (signUpButton.isSelected()) {
            register();
        } else {
            authenticate();
        }
    }

    public void register() {
        try {
            if (loginField.getText().isEmpty() || loginField.getText().length() > 40 || passwordField.getText().isEmpty()) {
                throw new InvalidFormException();
            }

            var user = new User(-1, loginField.getText(), passwordField.getText());
            var response = (ResponseUser) client.sendAndReceiveCommand(new RequestWithUserArg("reg", user, null));
            if (!response.getExitCode()) {
                switch (response.getMessage()) {
                    case "Имя пользователя занято" -> throw new UserAlreadyExistsException();
                    case "Ошибка взаимодействия с сервером" -> DialogManager.alert("ServerIOException", localizator);
                    case "Сервер не отвечает" -> DialogManager.alert("ServerDoesntResponse", localizator);
                    default -> DialogManager.createAlert(
                            localizator.getKeyString("Error"), response.getMessage(), Alert.AlertType.ERROR, false
                    );
                }
                return;
            }

            UserHandler.setCurrentUser(response.getUser());
            UserHandler.setCurrentLanguage(languageComboBox.getValue());
            DialogManager.info("RegisterSuccess", localizator);

//            callback.run();

        } catch (InvalidFormException exception) {
            DialogManager.alert("InvalidCredentials", localizator);
        }  catch (TimeoutException e) {
            DialogManager.alert("Timeout", localizator);
        } catch (UserAlreadyExistsException exception) {
            DialogManager.alert("UserAlreadyExists", localizator);
        } catch(IOException e) {
            DialogManager.alert("UnavailableError", localizator);
        }
    }


    public void authenticate() {
        try {
            if (loginField.getText().isEmpty() || loginField.getText().length() > 40 || passwordField.getText().isEmpty()) {
                throw new InvalidFormException();
            }

            var user = new User(-1, loginField.getText(), passwordField.getText());
            var response = (ResponseUser) client.sendAndReceiveCommand(new RequestWithUserArg("auth", user, null));
            if (!response.getExitCode()) {
                switch (response.getMessage()){
                    case "Нет такого пользователя" -> DialogManager.alert("UserNotFound", localizator);
                    case "Не правильный пароль" -> DialogManager.alert("PasswordIncorrect", localizator);
                    default -> DialogManager.createAlert(localizator.getKeyString("Error"), response.getMessage(), Alert.AlertType.ERROR, false);
                }
                return;
            }

            UserHandler.setCurrentUser(response.getUser());
            UserHandler.setCurrentLanguage(languageComboBox.getValue());
            DialogManager.info("AuthSuccess", localizator);
//            callback.run();

        } catch (InvalidFormException exception) {
            DialogManager.alert("InvalidCredentials", localizator);
        } catch (TimeoutException e) {
            DialogManager.alert("Timeout", localizator);
        } catch(IOException e) {
            DialogManager.alert("UnavailableError", localizator);
        }
    }

    public void changeLanguage() {
        titleLabel.setText(localizator.getKeyString("AuthTitle"));
        loginField.setPromptText(localizator.getKeyString("LoginField"));
        passwordField.setPromptText(localizator.getKeyString("PasswordField"));
        signUpButton.setText(localizator.getKeyString("SignUpButton"));
    }

    public void setCallback(Runnable callback) {
        this.callback = callback;
    }

    public void setClient(UDPClient client) {
        this.client = client;
    }

    public void setLocalizator(Localizator localizator) {
        this.localizator = localizator;
    }
}