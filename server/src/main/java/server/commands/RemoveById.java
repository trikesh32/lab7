package server.commands;

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
    public ExecutionResponse apply(Request request) {
        var req = (RequestWithIntArg)request;
        if (collectionManager.getById(req.getIntegerArg()) == null || !collectionManager.isContain(collectionManager.getById(req.getIntegerArg()))) {
            return new ExecutionResponse(false, "Элемент с заданым ID не найден!");
        }
        try {
            collectionManager.remove(req.getUser(), req.getIntegerArg());
        } catch (SQLException e){
            return new ExecutionResponse(false, "Ошибка работы базы данных" + e.toString());
        } catch (DBManager.NotFoundException e){
            return new ExecutionResponse(false, "Элемент не найден");
        } catch (ForbiddenException e) {
            return new ExecutionResponse(false, "Это не ваш объект");
        }
        return new ExecutionResponse("Vehicle успешно удален!");
    }


}
