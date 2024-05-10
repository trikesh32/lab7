package client.commands;

import client.auth.UserHandler;
import client.utils.TimeoutException;
import common.utils.Console;
import common.utils.ExecutionResponse;

import java.io.IOException;

public class Logout extends Command{
    public Logout(){
        super("logout", "делает логаут хз чо еще добавить");
    }
    public ExecutionResponse apply(String[] args){
        if (!args[1].isEmpty()) return new ExecutionResponse(false, "Команда используется не верно!");
        UserHandler.setCurrentUser(null);
        return new ExecutionResponse(true, "Вы успешно вышли из аккаунта");
    }
}
