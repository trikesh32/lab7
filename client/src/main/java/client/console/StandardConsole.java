package client.console;

import java.util.Scanner;
import common.utils.Console;

/**
 * Для ввода команд и вывода результат
 * @author trikesh
 */
public class StandardConsole implements Console {
    private final String PROMPT = "$ ";
    private final String SCRIPT_PROMPT = "> ";
    private Scanner prompt_scanner = null;

    /**
     * Выводит obj.toString() + \n в консоль
     * @param obj Объект для печати
     */
    public void println(Object obj) {
        System.out.println(obj);
    }

    /**
     * Выводит obj.toString() в консоль
     * @param obj Объект для печати
     */
    public void print(Object obj) {
        System.out.print(obj);
    }

    /**
     * Считывает строку
     */
    public String readln() {
        return prompt_scanner.nextLine();
    }

    /**
     * Проверяет, возможно ли считывание строки
     */
    public boolean isCanReadLn() {
        return prompt_scanner.hasNextLine();
    }

    public void selectConsoleScanner() {
        prompt_scanner = new Scanner(System.in);
    }

    public void selectFileScanner(Scanner s) {
        prompt_scanner = s;
    }

    /**
     * Выводит ошибку
     */
    public void printError(Object obj){
        System.err.println("Error(err): " + obj);
        System.out.println("Error(out) " + obj);
    }

    /**
     * Печатает PROMPT
     */
    public void prompt(){
        print(PROMPT);
    }
    public String getPrompt(){
        return PROMPT;
    }
    public String getScriptPrompt() {
        return SCRIPT_PROMPT;
    }
}
