package client.commands;

import common.utils.ExecutionResponse;

/**
 * Боль.....
 * @author trikesh
 */
public class ExecuteScript extends Command{

    public ExecuteScript() {
        super("execute_script filename", "выполнить скрипт из указанного файла");
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public ExecutionResponse apply(String[] args) {
        if(args[1].isEmpty()) return new ExecutionResponse(false, "Команда используется не верно!");
        return new ExecutionResponse("Выполняю скрипт из файла " + args[1] + "...");
    }
}
