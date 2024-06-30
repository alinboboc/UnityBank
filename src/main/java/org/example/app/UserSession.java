package org.example.app;

public class UserSession {
    private static String loggedUserEmail;

    public static String getLoggedUserEmail() {
        return loggedUserEmail;
    }

    public static void setLoggedUserEmail(String email) {
        loggedUserEmail = email;
    }
}