package client;

import client.console.StandardConsole;
import client.controllers.AuthController;
import client.network.UDPClient;
import client.utils.Runner;
import client.managers.CommandManager;
import client.commands.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.*;
import client.utils.Localizator;

public class App extends Application {
    private static final int PORT = 17535;
    public static final Logger logger = Logger.getLogger("ClientLogger");
    public static UDPClient client;
    private Stage mainStage;
    private Localizator localizator;

    public static void main(String[] args){
        try {
            Logger rootLogger = Logger.getLogger("");
            Handler[] handlers = rootLogger.getHandlers();
            if (handlers.length > 0) {
                rootLogger.removeHandler(handlers[0]);
            }
            FileHandler fileHandler = new FileHandler("client_log_%u.%g.log", 1024 * 1024, 10, false);
            fileHandler.setFormatter(new SimpleFormatter());
            fileHandler.setLevel(Level.ALL);
            logger.addHandler(fileHandler);
            logger.setLevel(Level.ALL);
        } catch (Exception e){
            System.out.println("Неудалось создать логи(");
            System.exit(1);
        }
        try{
//            var client = new UDPClient(InetAddress.getByAddress(new byte[]{(byte) 192, (byte) 168, (byte)10, (byte)80}), PORT);
            client = new UDPClient(InetAddress.getLocalHost(), PORT);
            launch(args);
        } catch (UnknownHostException e) {
            logger.severe("Неизвестный хост");
            System.err.println("Неизвестный хост");

        } catch (IOException e) {
            logger.severe("Невозможно подключиться к серверу");
            System.err.println("Невозможно подключиться к серверу");
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        localizator = new Localizator(ResourceBundle.getBundle("client.locales.gui", new Locale("ru", "RU")));
        mainStage = stage;
        authStage();
    }
    private void authStage(){
        var authLoader = new FXMLLoader(getClass().getResource("/auth.fxml"));
        Parent authRoot = loadFxml(authLoader);
        AuthController authController = authLoader.getController();
        authController.setCallback(this::startMain);
        authController.setClient(client);
        authController.setLocalizator(localizator);

        mainStage.setScene(new Scene(authRoot));
        mainStage.setTitle("Vehicle fun");
        mainStage.setResizable(false);
        mainStage.show();
    }
    public void startMain(){

    }
    private Parent loadFxml(FXMLLoader loader){
        Parent parent = null;
        try {
            parent = loader.load();
        } catch (IOException e){
            logger.severe("Can't load " + loader.toString());
            System.err.println(e);
            System.exit(1);
        }
        return parent;
    }
}
