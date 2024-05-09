package server.commands;

import common.network.requests.Request;
import common.network.requests.RequestWithUserArg;
import common.utils.ExecutionResponse;
import server.managers.AuthManager;

import java.sql.SQLException;

public class Authenticate extends Command{
    private final AuthManager authManager;
    public Authenticate(AuthManager authManager){
        super("auth", "аутентифицировать пользователя по логину и паролю");
        this.authManager = authManager;
    }

    public ExecutionResponse apply(Request request){
        var req = (RequestWithUserArg) request;
        var user = req.getUserArg();
        try {
            var userId = authManager.authenticateUser(user.getName(), user.getPassword());
            if (userId == 0){
                return new ExecutionResponse(false, "Нет такого пользоватьля");
            } else if (userId == -1) {
                return new ExecutionResponse(false, "Не правильный пароль");
            }
            return new ExecutionResponse(true, "Пользователь найден", user.copy(userId));
        } catch (Exception e){
            return new ExecutionResponse(false, e.toString());
        }
    }
}
