package client.commands;

import client.auth.UserHandler;
import client.console.Asker;
import client.network.UDPClient;
import client.utils.TimeoutException;
import common.network.requests.RequestWithUserArg;
import common.network.responses.ResponseUser;
import common.utils.Console;
import common.utils.ExecutionResponse;

import java.io.IOException;

public class Authenticate extends Command{
    private final UDPClient client;
    private final Console console;
    public Authenticate(UDPClient client, Console console){
        super("auth", "Проводит аутентификацию");
        this.client = client;
        this.console = console;
    }

    @Override
    public ExecutionResponse apply(String[] args) {
        try {
            if (!args[1].isEmpty()) return new ExecutionResponse(false, "Команда используется не верно!");
            var user = Asker.askUser(console);
            var response = (ResponseUser) client.sendAndReceiveCommand(new RequestWithUserArg("auth", user, null));
            if (response.getExitCode()){
                UserHandler.setCurrentUser(response.getUser());
                return new ExecutionResponse("Пользователь " + response.getUser().getName() + " залогинился");
            }
            return new ExecutionResponse(false, response.getMessage());
        } catch (IOException e) {
            return new ExecutionResponse(false, "Ошибка взаимодействия с сервером");
        } catch (TimeoutException e) {
            return new ExecutionResponse(false, "Сервер не отвечает");
        }
    }
}
