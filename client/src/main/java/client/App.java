package client;

import client.console.StandardConsole;
import client.network.UDPClient;
import client.utils.Runner;
import client.managers.CommandManager;
import client.commands.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.*;

public class App {
    private static final int PORT = 17535;
    public static final Logger logger = Logger.getLogger("ClientLogger");

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
        var console = new StandardConsole();
        try{
//            var client = new UDPClient(InetAddress.getByAddress(new byte[]{(byte) 192, (byte) 168, (byte)10, (byte)80}), PORT);
            var client = new UDPClient(InetAddress.getLocalHost(), PORT);
            CommandManager commandManager = new CommandManager();
            commandManager.register("add", new Add(console, client));
            commandManager.register("exit", new Exit(console));
            commandManager.register("show", new Show(console, client));
            commandManager.register("help", new Help(commandManager, client));
            commandManager.register("info", new Info(client));
            commandManager.register("update", new Update(console, client));
            commandManager.register("remove_by_id", new RemoveById(client));
            commandManager.register("clear", new Clear(client));
            commandManager.register("sort", new Sort(client));
            commandManager.register("remove_lower", new RemoveLower(console, client));
            commandManager.register("sum_of_capacity", new SumOfCapacity(client));
            commandManager.register("filter_by_capacity", new FilterByCapacity(client));
            commandManager.register("filter_less_than_type", new FilterLessThanType(client));
            commandManager.register("execute_script", new ExecuteScript());
            commandManager.register("reg", new Register(client, console));
            commandManager.register("auth", new Authenticate(client, console));
            commandManager.register("logout", new Logout());
            var cli = new Runner(console, commandManager);
            cli.interactiveMode();
        } catch (UnknownHostException e) {
            logger.warning("Неизвестный хост");

        } catch (IOException e) {
            logger.warning("Невозможно подключиться к серверу.");
        }
    }
}
