package client.console;

import common.models.Coordinates;
import common.models.FuelType;
import common.models.Vehicle;
import common.models.VehicleType;
import common.user.User;
import common.utils.Console;

import java.util.NoSuchElementException;

/**
 * Сборщик данных об объекте
 * @author trikesh
 */
public class Asker {
    public static class AskBreak extends Exception {}

    /**
     * Считывает vehicle
     */
    public static Vehicle askVehicle(Console console, int id) throws AskBreak {
        try{
            console.print("name: ");
            String name;
            while (true) {
                name = console.readln().trim();
                if (name.equals("exit")) throw new AskBreak();
                if (!name.isEmpty()) break;
                console.print("name: ");
            }
            var coordinates = askCoordinates(console);
            console.print("engine power (>0): ");
            Integer engine_power;
            while (true){
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                try {
                    engine_power = Integer.parseInt(line);
                    if (engine_power > 0) break;
                } catch (NumberFormatException e) {}
                console.print("engine power (>0): ");
            }
            console.print("capacity (>0): ");
            Integer capacity;
            while (true){
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                try {
                    capacity = Integer.parseInt(line);
                    if (capacity > 0) break;
                } catch (NumberFormatException e) {}
                console.print("capacity (>0): ");
            }
            var vehicleType = askVehicleType(console);
            var fuelType = askFuelType(console);
            return new Vehicle(id, name, coordinates, engine_power, capacity, vehicleType, fuelType);
        } catch (NoSuchElementException | IllegalStateException e){
            console.printError("Ошибка чтения");
            return null;
        }
    }

    /**
     * Считывает coordinates
     */
    public static Coordinates askCoordinates(Console console) throws AskBreak {
        try {
            console.print("coordinates.x: ");
            Integer x;
            while (true) {
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.isEmpty()) {
                    try { x = Integer.parseInt(line); if (x <= 636) break; } catch (NumberFormatException e) { }
                }
                console.print("coordinates.x: ");
            }
            console.print("coordinates.y: ");
            Float y;
            while (true) {
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.isEmpty()) {
                    try { y = Float.parseFloat(line); break; } catch (NumberFormatException e) { }
                }
                console.print("coordinates.y: ");
            }

            return new Coordinates(x, y);
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Ошибка чтения");
            return null;
        }
    }

    /**
     * Считывает VehicleType
     */
    public static VehicleType askVehicleType(Console console) throws AskBreak {
        try {
            console.print("VehicleType ("+VehicleType.names()+"): ");
            VehicleType r;
            while (true) {
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.isEmpty()) {
                    try { r = VehicleType.valueOf(line.toUpperCase()); break; } catch (NullPointerException | IllegalArgumentException  e) { }
                }
                console.print("VehicleType: ");
            }
            return r;
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Ошибка чтения");
            return null;
        }
    }

    /**
     * Считывает FuelType
     */
    public static FuelType askFuelType(Console console) throws AskBreak {
        try {
            console.print("FuelType ("+FuelType.names()+", для null нажмите enter): ");
            FuelType r;
            while (true) {
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.isEmpty()) {
                    try { r = FuelType.valueOf(line.toUpperCase()); break; } catch (NullPointerException | IllegalArgumentException  e) { }
                } if (line.isEmpty()) return null;
                console.print("FuelType: ");
            }
            return r;
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Ошибка чтения");
            return null;
        }
    }

    public static User askUser(Console console){
        try {
            console.print("Login: ");
            String login;
            while (true){
                var line = console.readln().trim();
                if (line.length() < 40 && !line.isEmpty()){
                    login = line;
                    break;
                }
                console.print("Login: ");
            }
            console.print("Passwd: ");
            String passwd;
            while(true){
                var line = console.readln().trim();
                if (!line.isEmpty()){
                    passwd = line;
                    break;
                }
                console.print("Passwd: ");
            }
            return new User(0, login, passwd);
        }catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Ошибка чтения");
            return null;
        }
    }
}
