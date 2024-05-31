package server.commands;

import server.managers.CollectionManager;
import common.models.Vehicle;
import common.network.requests.Request;
import common.network.requests.RequestWithIntAndVehicleArg;
import common.network.responses.Response;
import server.managers.DBManager;
import server.utils.ForbiddenException;

import java.sql.SQLException;


/**
 * Обновляет элемент коллекции.
 * @author trikesh
 */
public class Update extends Command {
    private CollectionManager collectionManager;

    public Update(CollectionManager collectionManager) {
        super("update id {element}", "обновляет значение элемента");
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public Response apply(Request request) {
        var req = (RequestWithIntAndVehicleArg) request;
        try {
            int id;
            var old_vehicle = collectionManager.getById(req.getIntegerArg());
            if (old_vehicle == null || !collectionManager.isContain(old_vehicle)) {
                return new Response(false, "ObjectNotFound");
            }
            if (!req.getVehicleArg().check_validity()){
                return new Response(false, "NotValidData");
            }
            collectionManager.update(req.getUser(), req.getVehicleArg(), req.getIntegerArg());
            return new Response(true, null);
        } catch (ForbiddenException ex) {
            return new Response(false, "Forbidden");
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return new Response(false, "DBError");
        } catch (DBManager.NotFoundException ex) {
            return new Response(false, "ObjectNotFound");
        } catch (Exception e) {
            return new Response(false, e.toString());
        }

    }
}
