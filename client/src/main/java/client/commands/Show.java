package client.commands;

import client.network.UDPClient;
import client.utils.TimeoutException;
import common.utils.Console;
import common.network.requests.Request;
import common.utils.ExecutionResponse;

import java.io.IOException;

/**
 * Выводит элементы коллекции.
 *
 * @author trikesh
 */
public class Show extends Command {
    private UDPClient client;

    public Show(Console console, UDPClient client) {
        super("show", "выводит информацию о коллекции");
        this.client = client;
    }

    /**
     * Выполняет команду
     *
     * @return Успешность выполнения команды.
     */
    @Override
    public ExecutionResponse apply(String[] args) {
        try {
            if (!args[1].isEmpty()) return new ExecutionResponse(false, "Команда используется не верно!");
            var response = client.sendAndReceiveCommand(new Request("show"));
            return new ExecutionResponse(response.getExitCode(), response.getMessage());
        } catch (IOException e){
            System.out.println(e);
            return new ExecutionResponse(false, "Ошибка взаимодействия с сервером");
        }catch (TimeoutException e) {
            return new ExecutionResponse(false, "Сервер не отвечает");
        }
    }
}
