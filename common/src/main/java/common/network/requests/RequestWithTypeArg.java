package common.network.requests;

import common.models.VehicleType;
import common.user.User;

public class RequestWithTypeArg extends Request{
    VehicleType typeArg;
    public RequestWithTypeArg(String name, VehicleType o, User user){
        super(name, user);
        typeArg = o;
    }

    public VehicleType getTypeArg() {
        return typeArg;
    }
}
