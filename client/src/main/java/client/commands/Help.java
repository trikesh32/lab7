package client.commands;

import client.managers.CommandManager;
import client.network.UDPClient;
import client.utils.TimeoutException;
import common.network.requests.Request;
import common.utils.ExecutionResponse;

import java.io.IOException;
import java.util.TreeMap;

/**
 * Выводит информацию о командах.
 *
 * @author trikesh
 */
public class Help extends Command {
    private CommandManager commandManager;
    private UDPClient client;

    public Help(CommandManager commandManager, UDPClient client) {
        super("help", "выводит информацию о доступных командах");
        this.commandManager = commandManager;
        this.client = client;
    }

    /**
     * Выполняет команду
     *
     * @return Успешность выполнения команды.
     */
    @Override
    public ExecutionResponse apply(String[] args) {
        if (!args[1].isEmpty()) return new ExecutionResponse(false, "Команда используется не верно!");
        StringBuilder res = new StringBuilder();
        new TreeMap<>(commandManager.getCommands()).keySet().forEach(key -> {res.append(commandManager.getCommands().get(key).getName() + ": " + commandManager.getCommands().get(key).getDescription() + "\n");});
        return new ExecutionResponse(res.toString().trim());
    }
}
