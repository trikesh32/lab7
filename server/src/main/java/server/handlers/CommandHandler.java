package server.handlers;

import common.network.responses.ResponseUser;
import server.managers.CommandManager;
import common.network.requests.Request;
import common.network.responses.Response;
import common.utils.ExecutionResponse;

public class CommandHandler {
    private final CommandManager commandManager;
    public CommandHandler(CommandManager commandManager){
        this.commandManager = commandManager;
    }

    public Response handle(Request request){
        var command = commandManager.getCommands().get(request.getCommandName());
        if (command == null) return new Response(false, "Нет такой команды");
        return command.apply(request);
    }
}
