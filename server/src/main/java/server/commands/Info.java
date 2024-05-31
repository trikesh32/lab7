package server.commands;

import common.network.responses.ResponseInfo;
import server.managers.CollectionManager;
import common.network.requests.Request;
import common.utils.ExecutionResponse;

import java.time.LocalDateTime;

/**
 * Выводит информацию о коллекции.
 * @author trikesh
 */
public class Info extends Command{
    private CollectionManager collectionManager;
    public Info(CollectionManager collectionManager){
        super("info", "выводит информацию о коллекции");
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public ResponseInfo apply(Request req) {

        LocalDateTime lastInitTime = collectionManager.getLastInitTime();
        LocalDateTime lastSaveTime = collectionManager.getLastSaveTime();
        return new ResponseInfo(true, null, lastInitTime, lastSaveTime, collectionManager.getCollection().getClass(), collectionManager.getCollection().size());
    }
}
