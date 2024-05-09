package common.network.requests;

import common.user.User;

public class RequestWithUserArg extends Request{
    private User userArg;
    public RequestWithUserArg(String name, User userArg, User user){
        super(name,user);
        this.userArg = userArg;
    }

    public User getUserArg() {
        return userArg;
    }
}
