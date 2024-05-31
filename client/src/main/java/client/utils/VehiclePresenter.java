package client.utils;

import common.models.Vehicle;

public class VehiclePresenter {
    private final Localizator localizator;

    public VehiclePresenter(Localizator localizator) {
        this.localizator = localizator;
    }

    public String describe(Vehicle product) {
        String info = "";
        info += " ID: " + product.getId();
        info += "\n " + localizator.getKeyString("Name") + ": " + product.getName();
        info += "\n " + localizator.getKeyString("Owner") + ": " + product.getCreator().toString();
        info += "\n " + localizator.getKeyString("CreationDate") + ": " + localizator.getDate(product.getCreationDate());
        info += "\n X: " + product.getCoordinates().getX();
        info += "\n Y: " + product.getCoordinates().getY();
        info += "\n " + localizator.getKeyString("EnginePower") + ": " + product.getEnginePower();
        info += "\n " + localizator.getKeyString("Capacity") + ": " + product.getCapacity();
        info += "\n " + localizator.getKeyString("VehicleType") + ": " + product.getVehicleType();
        info += "\n " + localizator.getKeyString("FuelType") + ": " + product.getFuelType();
        return info;
    }
}