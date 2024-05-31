package server.managers;

import common.models.Vehicle;
import common.user.User;
import common.utils.ExecutionResponse;
import server.App;
import server.utils.ForbiddenException;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Менеджер коллекции
 * @author trikesh
 */
public class CollectionManager {
    private Stack<Vehicle> collection;
    private final Logger logger = App.logger;
    private LocalDateTime lastInitTime;
    private LocalDateTime lastSaveTime;
    private final DBManager dbManager;
    private Map<Integer, Vehicle> vehicleMap = new HashMap<>();
    private final ReentrantLock lock = new ReentrantLock();

    public CollectionManager(DBManager dbManager){
        lastInitTime = null;
        lastSaveTime = null;
        this.dbManager = dbManager;

        try {
            if(!init()) throw new Exception();
        } catch (Exception e){
            logger.severe("Ошибка загрзки продуктов из базы данных!" + e);
            System.exit(3);
        }
    }
    public CollectionManager(DBManager dumpManager, Stack<Vehicle> coll){
        this(dumpManager);
        this.collection = coll;

    }


    /**
     * Получает объект по id
     * @param id ид
     * @return экземпляр vehicle
     */
    public Vehicle getById(Integer id){
        return vehicleMap.get(id);
    }

    /**
     * Проверяет, содержится ли в коллекции
     * @param o экземпляр vehicle
     * @return true если содержится или false в противном случае
     */
    public boolean isContain(Vehicle o){
        return o == null || getById(o.getId()) != null;
    }

    /**
     * Добавляет объект vehicle в коллекцию
     * @param o объект vehicle
     * @return true если успешно, false если нет
     */
    public boolean add(Vehicle o, User user) throws SQLException {
        var newId = dbManager.add(user, o);
        Vehicle vehicle = new Vehicle(newId, o.getName(), o.getCoordinates(), o.getCreationDate(), o.getEnginePower(), o.getCapacity(), o.getVehicleType(), o.getFuelType(), user);
        lock.lock();
        vehicleMap.put(newId, vehicle);
        collection.push(vehicle);
        lastSaveTime = LocalDateTime.now();
        lock.unlock();
        return true;
    }

    /**
     * Убирает объект vehicle из коллекции
     * @param id ид объекта
     * @return true если успешно, false если нет
     */
    public void remove(User user, Integer id) throws SQLException, DBManager.NotFoundException, ForbiddenException {
        if (getById(id).getCreator().getId() != user.getId()){
            throw new ForbiddenException();
        }
        dbManager.remove(user, id);
        lock.lock();
        var o = getById(id);
        vehicleMap.remove(id);
        collection.remove(o);
        lastSaveTime = LocalDateTime.now();
        lock.unlock();
    }

    /**
     * Сортирует коллекцию
     */
    public void sortCollection(){
        Collections.sort(collection);
    }
    public void sortByName(){
        Collections.sort(collection, new Comparator<Vehicle>() {
            @Override
            public int compare(Vehicle o1, Vehicle o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
    }

    /**
     * Инициализирует коллекцию
     * @return true, если успешно, false, если нет
     */
    public boolean init() throws SQLException {
        lock.lock();
        vehicleMap.clear();
        collection = dbManager.loadVehicle();
        if (collection == null){
            return false;
        }
        lastInitTime = LocalDateTime.now();
        for (var o : collection){
            if (getById(o.getId()) != null){
                collection.clear();
                lock.unlock();
                return false;
            }
            vehicleMap.put(o.getId(), o);
        }
        lock.unlock();
        return true;
    }
    public LocalDateTime getLastInitTime(){
        return lastInitTime;
    }
    public  LocalDateTime getLastSaveTime(){
        return lastSaveTime;
    }
    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        for (var o: collection){
            res.append(o).append("\n");
        }
        return res.toString().trim();
    }

    public Stack<Vehicle> getCollection() {
        return collection;
    }
    public boolean isEmpty(){
        return collection.isEmpty();
    }

    /**
     * Очищает коллекцию
     */
    public void clear(User user) throws SQLException {
        dbManager.clear(user);
        lock.lock();
        collection.removeIf(vehicle -> vehicle.getCreator().getId() == user.getId());
        lastSaveTime = LocalDateTime.now();
        lock.unlock();
    }

    public CollectionManager sorted(){
        var res = new CollectionManager(dbManager, collection);
        res.sortByName();
        return res;
    }
    public void removeLower(Vehicle o, User user) throws SQLException {
        dbManager.remove_lower(user, o);
        lock.lock();
        if (o != null && o.check_validity()){
            collection = collection.stream().filter(x -> x.compareTo(o) >= 0 || x.getCreator().getId() != user.getId()).collect(Collectors.toCollection(Stack::new));
        }
        lastSaveTime = LocalDateTime.now();
        lock.unlock();
    }
    public void update(User user, Vehicle v, Integer id) throws ForbiddenException, SQLException, DBManager.NotFoundException {
        lock.lock();
        var vehicle = getById(id);
        if (vehicle.getCreator().getId() != user.getId()) throw new ForbiddenException();
        lock.unlock();
        dbManager.update(user, v, id);
        lock.lock();
        var new_vehicle = new Vehicle(id, v.getName(), v.getCoordinates(), v.getCreationDate(), v.getEnginePower(), v.getCapacity(), v.getVehicleType(), v.getFuelType(), user);
        collection.remove(getById(id));
        vehicleMap.put(id, new_vehicle);
        collection.push(new_vehicle);
        lastSaveTime = LocalDateTime.now();
        lock.unlock();
    }

    public String show() throws SQLException, DBManager.NotFoundException {
        StringBuilder out = new StringBuilder();
        var usernames = dbManager.get_usernames();
        lock.lock();
        try {
            for (var o : collection){
                var username = usernames.get(o.getId());
                if (username == null) throw new DBManager.NotFoundException();
                out.append("Owner: " + username + " " + o + "\n");
              }
            return out.toString();
        } catch (Exception e){
            throw e;
        } finally {
            lock.unlock();
        }
    }
}
