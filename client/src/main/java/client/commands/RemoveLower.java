package client.commands;

import client.auth.UserHandler;
import client.network.UDPClient;
import client.console.Asker;
import client.utils.TimeoutException;
import common.models.Vehicle;
import common.network.requests.RequestWithVehicleArg;
import common.user.User;
import common.utils.Console;
import common.utils.ExecutionResponse;

import java.io.IOException;

/**
 * Удаляет элементы меньше заданного.
 * @author trikesh
 */
public class RemoveLower extends Command{
    private final Console console;
    private UDPClient client;
    public RemoveLower(Console console, UDPClient client){
        super("remove_lower {element}", "удаляет все элементы, меньшие чем заданный");
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
            console.println("Выполняется remove_lower...");
            console.println("Задаем критерий Vehicle");
            Vehicle o = Asker.askVehicle(console, 99999);
            var response = client.sendAndReceiveCommand(new RequestWithVehicleArg("remove_lower", o, UserHandler.getCurrentUser()));
            return new ExecutionResponse(response.getExitCode(), response.getMessage());
        } catch (Asker.AskBreak e){
            return new ExecutionResponse(false, "Отмена команды!");
        } catch (IOException e){
            return new ExecutionResponse(false, "Ошибка взаимодействия с сервером");
        }catch (TimeoutException e) {
            return new ExecutionResponse(false, "Сервер не отвечает");
        }
    }
}
