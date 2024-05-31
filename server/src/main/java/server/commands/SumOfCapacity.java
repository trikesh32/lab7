package server.commands;

import common.network.responses.Response;
import common.network.responses.ResponseInteger;
import server.managers.CollectionManager;
import common.models.Vehicle;
import common.network.requests.Request;
import common.utils.ExecutionResponse;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Выводит сумму capacity
 * @author trikesh
 */
public class  SumOfCapacity extends Command{
    private CollectionManager collectionManager;

    public SumOfCapacity(CollectionManager collectionManager) {
        super("sum_of_capacity", "выводит сумму всех полей capacity");
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public ResponseInteger apply(Request request) {
        int cap = collectionManager.getCollection().stream().mapToInt(Vehicle::getCapacity).sum();
        return new ResponseInteger(true, null, cap);
    }
}
