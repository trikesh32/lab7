package server.managers;

import server.commands.Command;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Менеджер команд
 * @author trikesh
 */
public class CommandManager {
    private Map<String, Command> commands = new LinkedHashMap<>();

    /**
     * Регистрирует команду
     * @param commandName имя команды
     * @param command экземпляр команды
     */
    public void register(String commandName, Command command){
        commands.put(commandName, command);
    }
    public Map<String, Command> getCommands(){
        return commands;
    }

}
