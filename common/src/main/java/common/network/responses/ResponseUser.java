package common.network.responses;

import common.user.User;

public class ResponseUser extends Response{
    private User user;
    public ResponseUser(boolean exitCode, String message, User user){
        super(exitCode, message);
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
