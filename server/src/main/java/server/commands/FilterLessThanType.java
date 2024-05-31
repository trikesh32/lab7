package server.commands;

import common.network.responses.ResponseVehicles;
import server.managers.CollectionManager;
import common.models.Vehicle;
import common.models.VehicleType;
import common.network.requests.RequestWithTypeArg;
import common.network.requests.Request;

import java.util.List;

/**
 * Выводит те элементы, чей VehicleType меньше заданного.
 * @author trikesh
 */
public class FilterLessThanType extends Command{
    private CollectionManager collectionManager;

    public FilterLessThanType(CollectionManager collectionManager) {
        super("filter_less_than_type", "выводит элементы, значения поля VehicleType меньше заданного");
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public ResponseVehicles apply(Request request) {
        var req = (RequestWithTypeArg) request;
        VehicleType type = req.getTypeArg();
        List<Vehicle> filteredVehicles = collectionManager.getCollection().stream().filter(x -> x.getVehicleType().compareTo(type) < 0).toList();
        return new ResponseVehicles(true, null, filteredVehicles);
    }
}
