package client.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.nio.file.*;

import client.auth.UserHandler;
import client.managers.CommandManager;
import common.utils.Console;
import common.utils.ExecutionResponse;


/**
 * Раннер команд
 * @author trikesh
 */
public class Runner {
    private Console console;
    private final CommandManager commandManager;

    private final List<String> scriptStack = new ArrayList<>();
    private Integer lengthRecursion = null;

    public Runner(Console console, CommandManager commandManager) {
        this.console = console;
        this.commandManager = commandManager;
        this.console.selectConsoleScanner();
    }

    /**
     * Интерактивный режим
     */
    public void interactiveMode() {
        try {
            ExecutionResponse commandStatus;
            String[] userCommand = {"", ""};

            while (true) {
                console.print(UserHandler.getCurrentUser() == null ? "": UserHandler.getCurrentUser().getName() + " ");
                console.prompt();
                userCommand = (console.readln().trim() + " ").split(" ", 2);
                userCommand[1] = userCommand[1].trim();
                if (UserHandler.getCurrentUser() == null && !userCommand[0].equals("reg") && !userCommand[0].equals("auth")){
                    console.println("Авторизируйтесь по команде auth \nЗарегестрируйтесь по команде reg");
                    continue;
                }
                if (!userCommand[0].isEmpty()) {
                    commandStatus = executeCommand(userCommand);

                    if (commandStatus.getMessage().equals("special_mark_exit")) {
                        break;
                    }
                    console.println(commandStatus.getMessage());
                }
            }
        } catch (NoSuchElementException exception) {
            console.printError("Пользовательский ввод не обнаружен!");
        } catch (IllegalStateException exception) {
            console.printError("Непредвиденная ошибка!");
        }
    }

    /**
     * Проверяет рекурсивность выполнения скриптов.
     *
     * @param args Название запускаемого скрипта
     * @return можно ли выполнять скрипт.
     */
    private boolean checkRecursion(String args, Scanner scriptScanner) {
        Integer recStart = null;
        int i = 0;
        for (String script : scriptStack) {
            i++;
            if (args.equals(script)) {
                if (recStart == null) recStart = i;
                if (lengthRecursion == null) {
                    console.selectConsoleScanner();
                    console.println("Была замечена рекурсия! Введите максимальную глубину рекурсии (0..500)");
                    while (lengthRecursion == null || lengthRecursion > 500 || lengthRecursion < 0) {
                        try {
                            console.print("> ");
                            lengthRecursion = Integer.parseInt(console.readln().trim());
                        } catch (NumberFormatException e) {
                            console.println("длина не распознана");
                        }
                    }
                    console.selectFileScanner(scriptScanner);
                }
                if (i > recStart + lengthRecursion || i > 500)
                    return false;
            }
        }
        return true;
    }


    /**
     * Режим для запуска скрипта.
     *
     * @param args Аргумент скрипта
     * @return Код завершения.
     */
    private ExecutionResponse scriptMode(String args) {
        String[] userCommand = {"", ""};
        StringBuilder executionOutput = new StringBuilder();

        if (!new File(args).exists()) return new ExecutionResponse(false, "Файл не существет!");
        if (!Files.isReadable(Paths.get(args))) return new ExecutionResponse(false, "Прав для чтения нет!");

        scriptStack.add(args);
        try (Scanner scriptScanner = new Scanner(new FileReader(args))) {

            ExecutionResponse commandStatus;

            if (!scriptScanner.hasNext()) throw new NoSuchElementException();
            console.selectFileScanner(scriptScanner);
            do {
                userCommand = (console.readln().trim() + " ").split(" ", 2);
                userCommand[1] = userCommand[1].trim();
                while (console.isCanReadLn() && userCommand[0].isEmpty()) {
                    userCommand = (console.readln().trim() + " ").split(" ", 2);
                    userCommand[1] = userCommand[1].trim();
                }
                executionOutput.append(console.getScriptPrompt() + String.join(" ", userCommand).trim() + "\n");
                var needLaunch = true;
                if (userCommand[0].equals("execute_script")) {
                    needLaunch = checkRecursion(userCommand[1], scriptScanner);
                }

                commandStatus = needLaunch ? executeCommand(userCommand) : new ExecutionResponse("Превышена максимальная глубина рекурсии");
                if (userCommand[0].equals("execute_script")) console.selectFileScanner(scriptScanner);
                executionOutput.append(commandStatus.getMessage() + "\n");
            } while (commandStatus.getExitCode() && !commandStatus.getMessage().equals("exit") && console.isCanReadLn());

            console.selectConsoleScanner();
            if (!commandStatus.getExitCode() && !(userCommand[0].equals("execute_script") && !userCommand[1].isEmpty())) {
                executionOutput.append("Проверьте скрипт на корректность введенных данных!\n");
            }

            return new ExecutionResponse(commandStatus.getExitCode(), executionOutput.toString());
        } catch (FileNotFoundException exception) {
            return new ExecutionResponse(false, "Файл со скриптом не найден!");
        } catch (NoSuchElementException exception) {
            return new ExecutionResponse(false, "Файл со скриптом пуст!");
        } catch (IllegalStateException exception) {
            console.printError("Непредвиденная ошибка!");
            System.exit(0);
        } finally {
            scriptStack.remove(scriptStack.size() - 1);
        }
        return new ExecutionResponse("");
    }

    /**
     * Выполняет команду
     *
     * @param userCommand Команда для запуска
     * @return Код завершения.
     */
    private ExecutionResponse executeCommand(String[] userCommand) {
        var command = commandManager.getCommands().get(userCommand[0]);

        if (command == null) return new ExecutionResponse(false, "Команда '" + userCommand[0] + "' не найдена.");

        if (userCommand[0].equals("execute_script")) {
            ExecutionResponse tmp = command.apply(userCommand);
            if (!tmp.getExitCode()) return tmp;
            ExecutionResponse tmp2 = scriptMode(userCommand[1]);
            return new ExecutionResponse(tmp2.getExitCode(), tmp.getMessage() + "\n" + tmp2.getMessage().trim());
        } else {
            return command.apply(userCommand);
        }
    }
}

