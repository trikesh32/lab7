package server.commands;

import common.models.Vehicle;
import common.network.responses.ResponseVehicles;
import server.managers.CollectionManager;
import common.network.requests.Request;
import common.network.requests.RequestWithIntArg;

import java.util.List;

/**
 * Выводит элементы, у которых capacity равен заданному.
 * @author trikesh
 */
public class FilterByCapacity extends Command{
    private CollectionManager collectionManager;

    public FilterByCapacity(CollectionManager collectionManager) {
        super("filter_by_capacity", "выводит элементы, у которых поле capacity равно заданному");
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public ResponseVehicles apply(Request request) {
        var req = (RequestWithIntArg) request;
        int capacity = req.getIntegerArg();
        List<Vehicle> filteredVehicles = collectionManager.getCollection().stream().filter(x -> x.getCapacity() == capacity).toList();
        return new ResponseVehicles(true, null, filteredVehicles);
    }
}
