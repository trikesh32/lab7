package server.commands;

import common.network.requests.Request;
import common.network.requests.RequestWithUserArg;
import common.utils.ExecutionResponse;
import server.managers.AuthManager;

public class Register extends Command{
    private final AuthManager authManager;
    private final int MAX_USERNAME_LENGTH = 40;
    public Register(AuthManager authManager){
        super("reg", "Зарегистрировать пользователя");
        this.authManager = authManager;
    }

    public ExecutionResponse apply(Request request){
        var req = (RequestWithUserArg) request;
        var user = req.getUserArg();
        if (user.getName().length() >= MAX_USERNAME_LENGTH){
            return new ExecutionResponse(false, "Длина имени пользователя должна быть меньше " + MAX_USERNAME_LENGTH);
        }
        try {
            var newUserId = authManager.registerUser(user.getName(), user.getPassword());
            if (newUserId == 0){
                return new ExecutionResponse(false, "Не удалось создать пользователя");
            } if (newUserId == -1){
                return new ExecutionResponse(false, "Имя пользователя занято");
            }
            return new ExecutionResponse(true, "Пользователь найден!", user.copy(newUserId));
        } catch (Exception e){
            return new ExecutionResponse(false, e.toString());
        }
    }
}
