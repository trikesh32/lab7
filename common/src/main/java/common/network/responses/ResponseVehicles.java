package common.network.responses;

import common.models.Vehicle;
import java.util.List;

public class ResponseVehicles extends Response {
    private List<Vehicle> vehicles;
    public ResponseVehicles(boolean exitCode, String message, List<Vehicle> filteredVehicles){
        super(exitCode, message);
        this.vehicles = filteredVehicles;
    }
    public List<Vehicle> getFilteredVehicles() {
        return vehicles;
    }
}
