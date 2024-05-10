package server.managers;

import common.models.Coordinates;
import common.models.FuelType;
import common.models.Vehicle;
import common.models.VehicleType;
import common.user.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class DBManager {
    static public class NotFoundException extends Exception{}
    private final DataSource dataSource;

    public DBManager(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public int add(User user, Vehicle vehicle) throws SQLException {
        Connection conn = dataSource.getConnection();
        PreparedStatement newIdStmt = conn.prepareStatement("SELECT nextval('id_sequence')");
        ResultSet rs = newIdStmt.executeQuery();
        rs.next();
        int newId = rs.getInt("nextval");
        PreparedStatement vehicleStmt = conn.prepareStatement("INSERT INTO vehicle(id, name, x, y, creation_date, engine_power, capacity, type, fuel_type, user_id) VALUES " +
                "(?, ?, ?, ?, ?, ?, ?, cast(? AS vehicle_type), cast(? As fuel_type), ?)");
        vehicleStmt.setInt(1, newId);
        vehicleStmt.setString(2, vehicle.getName());
        vehicleStmt.setInt(3, vehicle.getX());
        vehicleStmt.setFloat(4, vehicle.getY());
        vehicleStmt.setDate(5, Date.valueOf(vehicle.getCreationDate()));
        vehicleStmt.setInt(6, vehicle.getEnginePower());
        vehicleStmt.setInt(7, vehicle.getCapacity());
        vehicleStmt.setString(8, vehicle.getVehicleType().name());
        vehicleStmt.setString(9, vehicle.getFuelType() == null ? null : vehicle.getFuelType().name());
        vehicleStmt.setInt(10, user.getId());
        vehicleStmt.executeUpdate();
        conn.close();
        return newId;
    }
    public void update(User user, Vehicle vehicle, Integer id) throws SQLException, NotFoundException {
        var conn = dataSource.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) as c FROM vehicle WHERE user_id = ? AND id = ?");
        stmt.setInt(1, user.getId());
        stmt.setInt(2, id);
        ResultSet res = stmt.executeQuery();
        res.next();
        if (res.getInt("c") != 1) throw new NotFoundException();
        stmt = conn.prepareStatement("UPDATE vehicle SET name=?, x=?, y=?, creation_date=?, engine_power=?, capacity=?, type=cast(? AS vehicle_type), fuel_type=cast(? AS fuel_type) where id=? AND user_id=?");
        stmt.setString(1, vehicle.getName());
        stmt.setInt(2, vehicle.getX());
        stmt.setFloat(3, vehicle.getY());
        stmt.setDate(4, Date.valueOf(vehicle.getCreationDate()));
        stmt.setInt(5, vehicle.getEnginePower());
        stmt.setInt(6, vehicle.getCapacity());
        stmt.setString(7, vehicle.getVehicleType().name());
        stmt.setString(8, vehicle.getFuelType() == null ? null : vehicle.getFuelType().name());
        stmt.setInt(9, id);
        stmt.setInt(10, user.getId());
        stmt.executeUpdate();
        conn.close();
    }
    public void clear(User user) throws SQLException {
        var conn = dataSource.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM vehicle WHERE user_id = ?");
        stmt.setInt(1, user.getId());
        stmt.executeUpdate();
        conn.close();
    }

    public void remove(User user, int id) throws SQLException, NotFoundException {
        var conn = dataSource.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) as c FROM vehicle WHERE user_id = ? AND id = ?");
        stmt.setInt(1, user.getId());
        stmt.setInt(2, id);
        ResultSet res = stmt.executeQuery();
        res.next();
        if (res.getInt("c") != 1) throw new NotFoundException();
        stmt = conn.prepareStatement("DELETE FROM vehicle WHERE user_id = ? AND id = ?");
        stmt.setInt(1, user.getId());
        stmt.setInt(2, id);
        stmt.executeUpdate();
        conn.close();
    }
    public Stack<Vehicle> loadVehicle() throws SQLException {
        Stack<Vehicle> coll = new Stack<>();
        var conn = dataSource.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM vehicle");
        ResultSet res = stmt.executeQuery();
        while (res.next()){
            Vehicle o = new Vehicle(res.getInt(1), res.getString(2), new Coordinates(res.getInt(3), res.getFloat(4)), res.getDate(5).toLocalDate(),
                    res.getInt(6), res.getInt(7), VehicleType.valueOf(res.getString(8)), res.getString(9) == null ? null: FuelType.valueOf(res.getString(9)), res.getInt(10));
            coll.push(o);
        }
        return coll;
    }

    public void remove_lower(User user, Vehicle vehicle) throws SQLException {
        var conn = dataSource.getConnection();
        PreparedStatement stmt;
        stmt = conn.prepareStatement("DELETE FROM vehicle WHERE user_id = ? AND name < ?");
        stmt.setInt(1, user.getId());
        stmt.setString(2, vehicle.getName());
        stmt.executeUpdate();
        conn.close();
    }

    public Map<Integer, String> get_usernames() throws SQLException{
        var conn = dataSource.getConnection();
        HashMap<Integer, String> output = new HashMap<>();
        PreparedStatement stmt = conn.prepareStatement("SELECT vehicle.id as id, users.name as name from vehicle join users on vehicle.user_id = users.id");
        ResultSet res = stmt.executeQuery();
        while (res.next()){
            output.put(res.getInt("id"), res.getString("name"));
        }
        return output;
    }
}