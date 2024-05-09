package server;
import com.google.j2objc.annotations.ReflectionSupport;
import org.checkerframework.checker.units.qual.A;
import org.postgresql.ds.PGSimpleDataSource;
import server.handlers.CommandHandler;
import server.managers.*;
import server.commands.*;
import server.network.UDPDatagramServer;

import javax.sql.DataSource;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.*;

public class App {
    public static final int PORT = 17535;
    public static Logger logger = Logger.getLogger("ServerLogger");

    public static void main(String[] args){
        try {
            FileHandler fileHandler = new FileHandler("server_log_%u.%g.log", 1024 * 1024, 10, false);
            fileHandler.setFormatter(new SimpleFormatter());
            fileHandler.setLevel(Level.ALL);
            logger.addHandler(fileHandler);
            logger.setLevel(Level.ALL);
        } catch (Exception e){
            System.out.println("Неудалось создать логи(");
            System.exit(1);
        }
        DataSource dataSource = creatDS();
        var dbManager = new DBManager(dataSource);
        var collectionManager = new CollectionManager(dbManager);
        var authManager = new AuthManager(dataSource, "pepper");
        CommandManager commandManager = new CommandManager();
        commandManager.register("add", new Add(collectionManager));
        commandManager.register("show", new Show(collectionManager));
        commandManager.register("help", new Help(commandManager));
        commandManager.register("info", new Info(collectionManager));
        commandManager.register("update", new Update(collectionManager));
        commandManager.register("remove_by_id", new RemoveById(collectionManager));
        commandManager.register("clear", new Clear(collectionManager));
        commandManager.register("sort", new Sort(collectionManager));
        commandManager.register("remove_lower", new RemoveLower(collectionManager));
        commandManager.register("sum_of_capacity", new SumOfCapacity(collectionManager));
        commandManager.register("filter_by_capacity", new FilterByCapacity(collectionManager));
        commandManager.register("filter_less_than_type", new FilterLessThanType(collectionManager));
        commandManager.register("auth", new Authenticate(authManager));
        commandManager.register("reg", new Register(authManager));
        try {
            var server = new UDPDatagramServer(InetAddress.getLocalHost(), PORT, new CommandHandler(commandManager));
            server.run();
        } catch (SocketException e){
            logger.severe("Ошибка сокета" + e);
        } catch (UnknownHostException e){
            logger.severe("Неизвестный хост");
        }
    }
    public static DataSource creatDS(){
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setServerName("192.168.10.80");
        dataSource.setDatabaseName("studs");
        dataSource.setUser("s409711");
        dataSource.setPassword("HSudpSWKMUVZ59Kn");
        return dataSource;
    }
}
