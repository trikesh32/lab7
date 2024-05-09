package server.commands;

import server.managers.CollectionManager;
import common.models.Vehicle;
import common.network.requests.Request;
import common.network.requests.RequestWithVehicleArg;
import common.utils.ExecutionResponse;

import java.sql.SQLException;
import java.util.Stack;

/**
 * Удаляет элементы меньше заданного.
 *
 * @author trikesh
 */
public class RemoveLower extends Command {
    private final CollectionManager collectionManager;

    public RemoveLower(CollectionManager collectionManager) {
        super("remove_lower {element}", "удаляет все элементы, меньшие чем заданный");
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду
     *
     * @return Успешность выполнения команды.
     */
    @Override
    public ExecutionResponse apply(Request request) {
        try {
            var req = (RequestWithVehicleArg) request;
            Vehicle o = req.getVehicleArg();
            if (o != null && o.check_validity()) {
                collectionManager.removeLower(o, req.getUser());
                return new ExecutionResponse("Элементы меньше заданного удалены успешно!");
            }
            return new ExecutionResponse(false, "Данные не валидны!");
        } catch (SQLException e){
            return new ExecutionResponse(false, "Ошибка работы базы данных");
        }
    }
}
