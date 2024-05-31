package server.commands;

import common.network.responses.Response;
import server.managers.CollectionManager;
import common.network.requests.Request;
import common.utils.ExecutionResponse;

import java.sql.SQLException;

/**
 * Очищает коллекцию.
 * @author trikesh
 */
public class Clear extends Command{
    private CollectionManager collectionManager;

    public Clear(CollectionManager collectionManager) {
        super("clear", "очищает коллекцию");
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public Response apply(Request request) {
        try {
            collectionManager.clear(request.getUser());
        } catch (SQLException e){
            return new Response(false, "DBError");
        }
        return new Response(true, null);
    }
}
