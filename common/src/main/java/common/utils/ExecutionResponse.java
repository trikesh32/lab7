package common.utils;

import common.user.User;

/**
 * Класс ответа выполнения
 * @author trikesh
 */
public class ExecutionResponse {
    private boolean exitCode;
    private String message;
    private User user;
    public ExecutionResponse(boolean code, String s){
        exitCode = code;
        message = s;
    }
    public ExecutionResponse(boolean code, String s, User user){
        exitCode = code;
        message = s;
        this.user = user;
    }
    public ExecutionResponse(String s){
        this(true, s);
    }

    public boolean getExitCode() {
        return exitCode;
    }

    public String getMessage() {
        return message;
    }

    public User getUser(){
        return user;
    }

    @Override
    public String toString() {
        return String.valueOf(exitCode)+" " + message;
    }
}
