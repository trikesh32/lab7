package server.commands;

import server.managers.CollectionManager;
import common.models.Vehicle;
import common.network.requests.Request;
import common.network.requests.RequestWithVehicleArg;
import common.utils.ExecutionResponse;

/**
 * Добавляет заданный элемент в коллекцию.
 * @author trikesh
 */
public class Add extends Command{
    private final CollectionManager collectionManager;
    public Add(CollectionManager collectionManager){
        super("add {element}", "добавляет новый элемент в коллекцию");
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public ExecutionResponse apply(Request request) {
        var req = (RequestWithVehicleArg) request;
        var inputVehicle = req.getVehicleArg();
        var user = req.getUser();
        try {
            if (inputVehicle != null && inputVehicle.check_validity()){
                   collectionManager.add(inputVehicle, user);
                   return new ExecutionResponse("Vehicle успешно добавлен!");
            } return new ExecutionResponse(false,"Данные не валидны!");
        } catch (Exception e){
            return new ExecutionResponse(false, e.toString());
        }
    }
}
