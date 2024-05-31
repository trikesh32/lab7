package server.commands;

import common.network.requests.Request;
import common.network.requests.RequestWithUserArg;
import common.network.responses.ResponseUser;
import common.utils.ExecutionResponse;
import server.managers.AuthManager;

public class Register extends Command{
    private final AuthManager authManager;
    private final int MAX_USERNAME_LENGTH = 40;
    public Register(AuthManager authManager){
        super("reg", "Зарегистрировать пользователя");
        this.authManager = authManager;
    }

    public ResponseUser apply(Request request){
        var req = (RequestWithUserArg) request;
        var user = req.getUserArg();
        if (user.getName().length() >= MAX_USERNAME_LENGTH){
            return new ResponseUser(false, "UsernameTooLong", null);
        }
        try {
            var newUserId = authManager.registerUser(user.getName(), user.getPassword());
            if (newUserId == 0){
                return new ResponseUser(false, "UserNotCreated", null);
            } if (newUserId == -1){
                return new ResponseUser(false, "UserAlreadyExists", null);
            }
            return new ResponseUser(true, null, user.copy(newUserId));
        } catch (Exception e){
            return new ResponseUser(false, e.toString(), null);
        }
    }
}
