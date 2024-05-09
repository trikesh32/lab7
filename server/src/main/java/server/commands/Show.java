package server.commands;

import server.managers.CollectionManager;
import common.network.requests.Request;
import common.utils.ExecutionResponse;
import server.managers.DBManager;

import java.sql.SQLException;

/**
 * Выводит элементы коллекции.
 * @author trikesh
 */
public class Show extends Command{
    private CollectionManager collectionManager;
    public Show(CollectionManager collectionManager){
        super("show", "выводит информацию о коллекции");
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public ExecutionResponse apply(Request req) {
        if (collectionManager.isEmpty()) return new ExecutionResponse("Коллекция пустая!");
        try {
            return new ExecutionResponse(collectionManager.show());
        } catch (SQLException e){
            return new ExecutionResponse(false, "Ошибка работы с базой данных");
        } catch (DBManager.NotFoundException e){
            return new ExecutionResponse(false, "В коллекции существует объект принадлежащий несуществующему пользователю");
        }

    }
}
