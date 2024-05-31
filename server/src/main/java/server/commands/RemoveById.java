package server.commands;

import common.network.responses.Response;
import server.managers.CollectionManager;
import common.network.requests.Request;
import common.network.requests.RequestWithIntArg;
import common.utils.ExecutionResponse;
import server.managers.DBManager;
import server.utils.ForbiddenException;

import java.sql.SQLException;

/**
 * Убирает элемент по id.
 * @author trikesh
 */
public class RemoveById extends Command{
    private CollectionManager collectionManager;

    public RemoveById(CollectionManager collectionManager) {
        super("remove_by_id id", "удаляет элемент по id");
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public Response apply(Request request) {
        var req = (RequestWithIntArg)request;
        if (collectionManager.getById(req.getIntegerArg()) == null || !collectionManager.isContain(collectionManager.getById(req.getIntegerArg()))) {
            return new Response(false, "ObjectNotFound");
        }
        try {
            collectionManager.remove(req.getUser(), req.getIntegerArg());
        } catch (SQLException e){
            return new Response(false, "DBError");
        } catch (DBManager.NotFoundException e){
            return new Response(false, "ObjectNotFound");
        } catch (ForbiddenException e) {
            return new Response(false, "Forbidden");
        }
        return new Response(true, null);
    }


}
