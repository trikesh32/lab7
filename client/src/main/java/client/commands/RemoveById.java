package client.commands;

import client.auth.UserHandler;
import client.network.UDPClient;
import client.utils.TimeoutException;
import common.network.requests.RequestWithIntArg;
import common.utils.ExecutionResponse;

import java.io.IOException;

/**
 * Убирает элемент по id.
 * @author trikesh
 */
public class RemoveById extends Command{
    private UDPClient client;

    public RemoveById(UDPClient client) {
        super("remove_by_id id", "удаляет элемент по id");
        this.client = client;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public ExecutionResponse apply(String[] args) {
        if (args[1].isEmpty()) return new ExecutionResponse(false, "Команда используется не верно!");
        int id;
        try {
            id = Integer.parseInt(args[1].trim());
        } catch (NumberFormatException e) {
            return new ExecutionResponse(false, "ID указан не верно!");
        }
        try {
            var response = client.sendAndReceiveCommand(new RequestWithIntArg("remove_by_id", id, UserHandler.getCurrentUser()));
            return new ExecutionResponse(response.getExitCode(), response.getMessage());
        } catch (IOException e){
            return new ExecutionResponse(false, "Ошибка взаимодействия с сервером");
        }catch (TimeoutException e) {
            return new ExecutionResponse(false, "Сервер не отвечает");
        }
    }


}
