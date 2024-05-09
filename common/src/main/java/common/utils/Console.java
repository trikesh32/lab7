package common.utils;

import java.util.Scanner;
/**
 * Интерфейс для реализации консоли ввода-вывода
 * @author trikesh
 */
public interface Console {
    void print(Object obj);
    void println(Object obj);
    String readln();
    boolean isCanReadLn();
    void printError(Object obj);
    void prompt();
    String getPrompt();
    String getScriptPrompt();
    void selectFileScanner(Scanner obj);
    void selectConsoleScanner();
}
