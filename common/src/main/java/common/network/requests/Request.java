package common.network.requests;

import common.user.User;

import java.io.Serializable;

public class Request implements Serializable {
    private String commandName;
    private User user = null;
    public Request(String name){
        commandName = name;
    }
    public Request(String name, User user){
        this(name);
        this.user = user;
    }

    public String getCommandName() {
        return commandName;
    }
    public User getUser(){
        return user;
    }
}
