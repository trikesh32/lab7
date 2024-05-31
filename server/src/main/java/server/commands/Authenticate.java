package server.commands;

import common.network.requests.Request;
import common.network.requests.RequestWithUserArg;
import common.network.responses.ResponseUser;
import common.utils.ExecutionResponse;
import server.managers.AuthManager;

import java.sql.SQLException;

public class Authenticate extends Command{
    private final AuthManager authManager;
    public Authenticate(AuthManager authManager){
        super("auth", "аутентифицировать пользователя по логину и паролю");
        this.authManager = authManager;
    }

    public ResponseUser apply(Request request){
        var req = (RequestWithUserArg) request;
        var user = req.getUserArg();
        try {
            var userId = authManager.authenticateUser(user.getName(), user.getPassword());
            if (userId == 0){
                return new ResponseUser(false, "UserNotFound", null);
            } else if (userId == -1) {
                return new ResponseUser(false, "PasswordIncorrect", null);
            }
            return new ResponseUser(true, null, user.copy(userId));
        } catch (Exception e){
            return new ResponseUser(false, e.toString(), null);
        }
    }
}
