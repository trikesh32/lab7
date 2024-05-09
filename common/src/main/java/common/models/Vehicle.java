package common.models;

import common.utils.Validatable;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;


/**
 * Класс Vehicle
 * @author trikesh
 */
public class Vehicle implements Validatable, Comparable<Vehicle>, Serializable {
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private int enginePower; //Значение поля должно быть больше 0
    private Integer capacity; //Поле не может быть null, Значение поля должно быть больше 0
    private VehicleType type; //Поле не может быть null
    private FuelType fuelType; //Поле может быть null
    private Integer userId;

    public Vehicle(Integer id, String name, Coordinates coordinates, LocalDate creationDate, Integer enginePower, Integer capacity, VehicleType type, FuelType fuelType, Integer user_id) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.enginePower = enginePower;
        this.capacity = capacity;
        this.type = type;
        this.fuelType = fuelType;
        this.userId = user_id;
    }
    public Vehicle(Integer id, String name, Coordinates coordinates, Integer enginePower, Integer capacity, VehicleType type, FuelType fuelType, Integer userId){
        this(id, name, coordinates, LocalDate.now(), enginePower, capacity, type, fuelType, userId);
    }
    public Vehicle(Integer id, String name, Coordinates coordinates, Integer enginePower, Integer capacity, VehicleType type, FuelType fuelType){
        this(id, name, coordinates, LocalDate.now(), enginePower, capacity, type, fuelType, null);
    }


    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public int getEnginePower() {
        return enginePower;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public VehicleType getVehicleType() {
        return type;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public int getX() {
        return coordinates.getX();
    }

    public float getY() {
        return coordinates.getY();
    }

    /**
     * Проверяет поля экземпляра на валидность
     * @return true если валидны, иначе false
     */
    public boolean check_validity() {
        if (id == null || id <= 0) return false;
        if (name == null || name.isEmpty()) return false;
        if (coordinates == null || !coordinates.check_validity()) return false;
        if (creationDate == null) return false;
        if (enginePower <= 0) return false;
        if (capacity == null || capacity <= 0) return false;
        if (type == null) return false;
        if (userId == null) return false;
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return enginePower == vehicle.enginePower && Objects.equals(id, vehicle.id) && Objects.equals(name, vehicle.name) && Objects.equals(coordinates, vehicle.coordinates) && Objects.equals(creationDate, vehicle.creationDate) && Objects.equals(capacity, vehicle.capacity) && type == vehicle.type && fuelType == vehicle.fuelType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, creationDate, enginePower, capacity, type, fuelType);
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id: " + id +
                ", name: '" + name +
                "', coordinates: " + coordinates +
                ", creationDate: " + creationDate +
                ", enginePower: " + enginePower +
                ", capacity: " + capacity +
                ", type: " + type +
                ", fuelType: " + fuelType +
                '}';
    }
    public int compareTo(Vehicle o){
        return this.getName().compareTo(o.getName());
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
