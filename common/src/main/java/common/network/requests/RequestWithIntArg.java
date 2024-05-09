package common.network.requests;

import common.user.User;

public class RequestWithIntArg extends Request{
    private Integer integerArg;
    public RequestWithIntArg(String name, Integer a, User user){
        super(name, user);
        this.integerArg = a;
    }

    public Integer getIntegerArg() {
        return integerArg;
    }
}
