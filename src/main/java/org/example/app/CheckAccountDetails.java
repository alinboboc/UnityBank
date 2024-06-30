package org.example.app;

import org.example.database.DatabaseCredentials;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class CheckAccountDetails extends DatabaseCredentials {
    private final Scanner scanner = new Scanner(System.in);

    public CheckAccountDetails() throws SQLException {
        System.out.println("Account details:");
        listAccountDetails();
    }

    public void listAccountDetails() throws SQLException {
        String loggedUserEmail = UserSession.getLoggedUserEmail();
        if (loggedUserEmail == null) {
            System.out.println("No user is currently logged in.");
            new MainPage();
            return;
        }

        String query = "SELECT * FROM users WHERE SQL_registerEmail = ?";
        try (Connection connection = getDatabaseConnectionDetails(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, loggedUserEmail);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Email: " + rs.getString("SQL_registerEmail"));
                    System.out.println("Username: " + rs.getString("SQL_registerUsername"));
                    System.out.println("IBAN: " + rs.getString("SQL_IBAN"));
                    System.out.println("Card number: " + rs.getString("SQL_cardNumber"));
                    System.out.println("Account balance: " + rs.getDouble("SQL_accountBalance"));
                    System.out.println("Account debt: " + rs.getDouble("SQL_debtBalance"));
                    System.out.println("First name: " + rs.getString("SQL_registerFirstName"));
                    System.out.println("Last name: " + rs.getString("SQL_registerLastName"));
                    System.out.println("Birth year: " + rs.getInt("SQL_registerBirthYear"));
                    System.out.println("Net income: " + rs.getDouble("SQL_registerNetIncome"));
                } else {
                    System.out.println("No user found with the provided email.");
                    new MainPage();
                }
            }
        }
        promptToGoBack();
    }

    private void promptToGoBack() {
        System.out.println("To go back to the main page, press 0 then Enter.");
        while (true) {
            String userInput = scanner.nextLine();
            if ("0".equals(userInput)) {
                try {
                    new MainPage();
                } catch (SQLException e) {
                    System.out.println("Error returning to main page: " + e.getMessage());
                }
                break;
            } else {
                System.out.println("Input is invalid, to go back press 0 then Enter.");
            }
        }
    }
}
