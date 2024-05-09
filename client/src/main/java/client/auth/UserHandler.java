package client.auth;
import common.user.User;

public class UserHandler {
    private static User currentUser = null;

    public static User getCurrentUser(){
        return currentUser;
    }
    public static void setCurrentUser(User currentUser){
        UserHandler.currentUser = currentUser;
    }
}
