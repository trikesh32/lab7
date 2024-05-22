package client.auth;
import common.user.User;

public class UserHandler {
    private static User currentUser = null;
    private static String currentLanguage = "Русский";

    public static User getCurrentUser(){
        return currentUser;
    }
    public static void setCurrentUser(User currentUser){
        UserHandler.currentUser = currentUser;
    }
    public static String getCurrentLanguage() {
        return currentLanguage;
    }
    public static void setCurrentLanguage(String currentLanguage) {
        UserHandler.currentLanguage = currentLanguage;
    }
}
