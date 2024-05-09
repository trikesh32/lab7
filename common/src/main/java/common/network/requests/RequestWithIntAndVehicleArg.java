package common.network.requests;

import common.models.Vehicle;
import common.user.User;

public class RequestWithIntAndVehicleArg extends Request{
    private Integer integerArg;
    private Vehicle vehicleArg;
    public RequestWithIntAndVehicleArg(String name, Integer a, Vehicle o, User user){
        super(name,user);
        this.integerArg = a;
        this.vehicleArg = o;
    }

    public Integer getIntegerArg() {
        return integerArg;
    }

    public Vehicle getVehicleArg() {
        return vehicleArg;
    }
}
