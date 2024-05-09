package common.network.requests;

import common.models.Vehicle;
import common.user.User;

public class RequestWithVehicleArg extends Request{
    private Vehicle vehicleArg;
    public RequestWithVehicleArg(String name, Vehicle o, User user){
        super(name, user);
        vehicleArg = o;
    }

    public Vehicle getVehicleArg() {
        return vehicleArg;
    }
}
