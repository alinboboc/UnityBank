package org.example.auth;

import org.example.app.LandingPage;
import org.example.app.MainPage;
import org.example.app.UserSession;
import org.example.database.DatabaseCredentials;

import java.sql.*;
import java.util.Scanner;

public class Login extends DatabaseCredentials {

    private final Scanner scanner = new Scanner(System.in);

    public Login() throws SQLException {
        System.out.println("You chose to login into your account.");
        System.out.println("If you want to go back to the main menu, press 0 and ENTER");
        loginProcedure();
    }

    private void loginProcedure() throws SQLException {
        System.out.println("Enter your email address:");
        String userEmailInput = scanner.nextLine().trim();

        if (userEmailInput.equals("0")) {
            new LandingPage();
            return;
        }

        if (!isEmailValid(userEmailInput)) {
            System.out.println("The email you entered is not valid, please try again.");
            loginProcedure();
            return;
        }

        try (Connection connection = getDatabaseConnectionDetails()) {
            String query = "SELECT * FROM users WHERE SQL_registerEmail = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, userEmailInput);
                try (ResultSet rs = statement.executeQuery()) {
                    if (rs.next()) {
                        UserSession.setLoggedUserEmail(userEmailInput);
                        handlePassword(rs);
                    } else {
                        System.out.println("Email not found. Please try again.");
                        loginProcedure();
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    private void handlePassword(ResultSet rs) {
        int passwordAttempts = 4;

        while (passwordAttempts > 0) {
            System.out.print("Enter your password: ");
            String inputPassword = scanner.nextLine();

            try {
                if (inputPassword.equals(rs.getString("SQL_registerPassword"))) {
                    new MainPage();
                    return;
                } else {
                    passwordAttempts--;
                    if (passwordAttempts > 0) {
                        System.out.println("Your password is incorrect, please try again. " + (passwordAttempts == 1 ? "This is the last attempt." : passwordAttempts + " attempts remaining"));
                    } else {
                        System.out.println("You exceeded the password limit. Try again.");
                        loginProcedure();
                        return;
                    }
                }
            } catch (SQLException e) {
                System.out.println("Database error: " + e.getMessage());
                return;
            }
        }
    }
}
