package server.commands;

import server.managers.CollectionManager;
import common.network.requests.Request;
import common.utils.ExecutionResponse;

/**
 * Сортирует коллекцию
 * @author trikesh
 */
public class Sort extends Command{
    private CollectionManager collectionManager;

    public Sort(CollectionManager collectionManager) {
        super("sort", "сортирует коллекцию по именам элементов");
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public ExecutionResponse apply(Request request) {
        collectionManager.sortCollection();
        return new ExecutionResponse("Коллекция отсортирована успешно!");
    }
}
