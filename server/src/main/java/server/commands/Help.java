package server.commands;

import server.managers.CommandManager;
import common.network.requests.Request;
import common.utils.ExecutionResponse;

import java.util.TreeMap;

/**
 * Выводит информацию о командах.
 * @author trikesh
 */
public class Help extends Command{
    private CommandManager commandManager;
    public Help(CommandManager commandManager){
        super("help", "выводит информацию о доступных командах");
        this.commandManager = commandManager;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public ExecutionResponse apply(Request req) {
        StringBuilder res = new StringBuilder();
        new TreeMap<>(commandManager.getCommands()).keySet().forEach(key -> {res.append(commandManager.getCommands().get(key).getName() + ": " + commandManager.getCommands().get(key).getDescription() + "\n");});
        return new ExecutionResponse(res.toString().trim());
    }
}
