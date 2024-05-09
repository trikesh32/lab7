package client.commands;
import client.auth.UserHandler;
import client.network.UDPClient;
import client.utils.TimeoutException;
import common.network.requests.RequestWithIntArg;
import common.utils.ExecutionResponse;

import java.io.IOException;

/**
 * Выводит элементы, у которых capacity равен заданному.
 * @author trikesh
 */
public class FilterByCapacity extends Command{
    private UDPClient client;

    public FilterByCapacity(UDPClient client) {
        super("filter_by_capacity", "выводит элементы, у которых поле capacity равно заданному");
        this.client = client;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public ExecutionResponse apply(String[] args) {
        if (args[1].isEmpty()) return new ExecutionResponse(false, "Команда используется не верно!");
        int capacity;
        try {
            capacity = Integer.parseInt(args[1].trim());
        } catch (NumberFormatException e) {
            return new ExecutionResponse(false, "capacity указан не верно!");
        }
        try {
            var response = client.sendAndReceiveCommand(new RequestWithIntArg("filter_by_capacity", capacity, UserHandler.getCurrentUser()));
            return new ExecutionResponse(response.getExitCode(), response.getMessage());
        } catch (IOException e){
            return new ExecutionResponse(false, "Ошибка взаимодействия с сервером");
        }catch (TimeoutException e) {
            return new ExecutionResponse(false, "Сервер не отвечает");
        }
    }
}
