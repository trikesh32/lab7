package server.commands;

import server.managers.CollectionManager;
import common.models.Vehicle;
import common.models.VehicleType;
import common.network.requests.RequestWithTypeArg;
import common.network.requests.Request;
import common.utils.ExecutionResponse;

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
    public ExecutionResponse apply(Request request) {
        var req = (RequestWithTypeArg) request;
        VehicleType type = req.getTypeArg();
        StringBuilder res = new StringBuilder();
        StringBuilder finalRes = res;
        collectionManager.getCollection().stream().filter(x -> x.getVehicleType().compareTo(type) < 0).forEach(x -> finalRes.append(x + "\n"));
        if (res.isEmpty()){
            res = new StringBuilder("Элементов удовлетворяющим условию не найдено!");
        }
        return new ExecutionResponse(res.toString().trim());
    }
}
