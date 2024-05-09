package client.commands;

import client.auth.UserHandler;
import client.network.UDPClient;
import client.utils.TimeoutException;
import common.network.requests.Request;
import common.user.User;
import common.utils.ExecutionResponse;

import java.io.IOException;

/**
 * Выводит информацию о коллекции.
 * @author trikesh
 */
public class Info extends Command{
    private UDPClient client;
    public Info(UDPClient client){
        super("info", "выводит информацию о коллекции");
        this.client = client;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public ExecutionResponse apply(String[] args) {
        if(!args[1].isEmpty()) return new ExecutionResponse(false, "Команда используется не верно!");
        try {
            var response = client.sendAndReceiveCommand(new Request("info", UserHandler.getCurrentUser()));
            return new ExecutionResponse(response.getExitCode(), response.getMessage());
        } catch (IOException e){
            return new ExecutionResponse(false, "Ошибка взаимодействия с сервером");
        }catch (TimeoutException e) {
            return new ExecutionResponse(false, "Сервер не отвечает");
        }
    }
}
