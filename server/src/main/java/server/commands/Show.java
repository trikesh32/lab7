package server.commands;

import common.network.responses.ResponseVehicles;
import server.managers.CollectionManager;
import common.network.requests.Request;
import common.utils.ExecutionResponse;
import server.managers.DBManager;

import java.sql.SQLException;

/**
 * Выводит элементы коллекции.
 * @author trikesh
 */
public class Show extends Command{
    private CollectionManager collectionManager;
    public Show(CollectionManager collectionManager){
        super("show", "выводит информацию о коллекции");
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public ResponseVehicles apply(Request req) {
        return new ResponseVehicles(true, null, collectionManager.getCollection());
    }
}
