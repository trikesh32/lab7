package client.commands;

import client.auth.UserHandler;
import client.network.UDPClient;

import client.utils.TimeoutException;
import common.models.VehicleType;
import common.network.requests.RequestWithTypeArg;
import common.utils.ExecutionResponse;

import java.io.IOException;

/**
 * Выводит те элементы, чей VehicleType меньше заданного.
 * @author trikesh
 */
public class FilterLessThanType extends Command{
    private UDPClient client;

    public FilterLessThanType(UDPClient client) {
        super("filter_less_than_type", "выводит элементы, значения поля VehicleType меньше заданного");
        this.client = client;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public ExecutionResponse apply(String[] args) {
        if (args[1].isEmpty()) return new ExecutionResponse(false, "Команда используется не верно!");
        VehicleType type;
        try {
            type = VehicleType.valueOf(args[1].trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return new ExecutionResponse(false, "type указан не верно!");
        }
        try {
            var response = client.sendAndReceiveCommand(new RequestWithTypeArg("filter_less_than_type", type, UserHandler.getCurrentUser()));
            return new ExecutionResponse(response.getExitCode(), response.getMessage());
        } catch (IOException e){
            return new ExecutionResponse(false, "Ошибка взаимодействия с сервером");
        }catch (TimeoutException e) {
            return new ExecutionResponse(false, "Сервер не отвечает");
        }
    }
}
