package server.commands;

import server.managers.CollectionManager;
import common.network.requests.Request;
import common.network.requests.RequestWithIntArg;
import common.utils.ExecutionResponse;

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
    public ExecutionResponse apply(Request request) {
        var req = (RequestWithIntArg) request;
        int capacity = req.getIntegerArg();
        StringBuilder res = new StringBuilder();
        StringBuilder finalRes = res;
        collectionManager.getCollection().stream().filter(x -> x.getCapacity() == capacity).forEach(x -> finalRes.append(x + "\n"));
        if (finalRes.isEmpty()){
            res = new StringBuilder("Элементов удовлетворяющим условию не найдено!");
        }
        return new ExecutionResponse(res.toString().trim());
    }
}
