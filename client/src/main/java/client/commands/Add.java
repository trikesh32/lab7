package client.commands;

import client.auth.UserHandler;
import client.console.Asker;
import client.utils.TimeoutException;
import common.models.Vehicle;
import client.network.UDPClient;
import common.network.requests.RequestWithVehicleArg;
import common.utils.Console;
import common.utils.ExecutionResponse;

import java.io.IOException;

/**
 * Добавляет заданный элемент в коллекцию.
 * @author trikesh
 */
public class Add extends Command{
    private final Console console;
    private final UDPClient client;

    public Add(Console console, UDPClient client){
        super("add {element}", "добавляет новый элемент в коллекцию");
        this.console = console;
        this.client = client;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public ExecutionResponse apply(String[] args) {
        try {
            if (!args[1].isEmpty()) return new ExecutionResponse(false, "Команда используется не верно!");
            console.println("Cоздаем новый Vehicle");
            Vehicle o = Asker.askVehicle(console, 99999);
            o.setCreator(UserHandler.getCurrentUser());
            var response = client.sendAndReceiveCommand(new RequestWithVehicleArg("add", o, UserHandler.getCurrentUser()));
            return new ExecutionResponse(response.getExitCode(), response.getMessage());

        } catch (Asker.AskBreak e){
            return new ExecutionResponse(false, "Отмена команды!");
        } catch (IOException e) {
            return new ExecutionResponse(false, "Ошибка взаимодействия с сервером");
        } catch (TimeoutException e) {
            return new ExecutionResponse(false, "Сервер не отвечает");
        }
    }
}
