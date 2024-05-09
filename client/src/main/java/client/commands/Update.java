package client.commands;

import client.auth.UserHandler;
import client.console.Asker;
import client.network.UDPClient;
import client.utils.TimeoutException;
import common.network.requests.RequestWithIntAndVehicleArg;
import common.utils.Console;
import common.utils.ExecutionResponse;

import java.io.IOException;


/**
 * Обновляет элемент коллекции.
 * @author trikesh
 */
public class Update extends Command {
    private Console console;
    private UDPClient client;

    public Update(Console console, UDPClient client) {
        super("update id {element}", "обновляет значение элемента");
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
            if (args[1].isEmpty()) return new ExecutionResponse(false, "Команда используется не верно!");
            int id;
            try {
                id = Integer.parseInt(args[1].trim());
            } catch (NumberFormatException e) {
                return new ExecutionResponse(false, "ID указан не верно!");
            }
            console.println("Cоздаем новый Vehicle");
            var new_vehicle = Asker.askVehicle(console, 99999);
            var response = client.sendAndReceiveCommand(new RequestWithIntAndVehicleArg("update", id, new_vehicle, UserHandler.getCurrentUser()));
            return new ExecutionResponse(response.getExitCode(), response.getMessage());

        } catch (Asker.AskBreak e) {
            return new ExecutionResponse(false, "Отмена команды!");
        } catch (IOException e){
            return new ExecutionResponse(false, "Ошибка взаимодействия с сервером");
        }catch (TimeoutException e) {
            return new ExecutionResponse(false, "Сервер не отвечает");
        }

    }
}
