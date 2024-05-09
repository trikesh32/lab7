package server.commands;

import server.managers.CollectionManager;
import common.models.Vehicle;
import common.network.requests.Request;
import common.network.requests.RequestWithIntAndVehicleArg;
import common.utils.Console;
import common.utils.ExecutionResponse;
import server.managers.DBManager;
import server.utils.ForbiddenException;

import java.sql.SQLException;


/**
 * Обновляет элемент коллекции.
 * @author trikesh
 */
public class Update extends Command {
    private CollectionManager collectionManager;

    public Update(CollectionManager collectionManager) {
        super("update id {element}", "обновляет значение элемента");
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public ExecutionResponse apply(Request request) {
        var req = (RequestWithIntAndVehicleArg) request;
        try {
            int id;
            var old_vehicle = collectionManager.getById(req.getIntegerArg());
            if (old_vehicle == null || !collectionManager.isContain(old_vehicle)) {
                return new ExecutionResponse(false, "Элемент с заданым ID не найден!");
            }
            if (!req.getVehicleArg().check_validity()){
                return new ExecutionResponse(false, "Данные не валидны");
            }
            collectionManager.update(req.getUser(), req.getVehicleArg(), req.getIntegerArg());
            return new ExecutionResponse("Vehicle успешно обновлен!");
        } catch (ForbiddenException ex) {
            return new ExecutionResponse(false, "Объект не принадлежит вам!");
        } catch (SQLException ex) {
            return new ExecutionResponse(false, "Ошибка работы с базой данных!");
        } catch (DBManager.NotFoundException ex) {
            return new ExecutionResponse(false, "Объект не найден!");
        } catch (Exception e) {
            return new ExecutionResponse(false, e.toString());
        }

    }
}
