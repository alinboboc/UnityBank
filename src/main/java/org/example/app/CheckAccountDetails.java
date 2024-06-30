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
        String query = "SELECT * FROM users WHERE SQL_registerEmail = ?";
        try (Connection conn = getDatabaseConnectionDetails();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, getSQL_loginEmail());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    System.out.println("Email: " + resultSet.getString("SQL_registerEmail"));
                    System.out.println("Username: " + resultSet.getString("SQL_registerUsername"));
                    System.out.println("IBAN: " + resultSet.getString("SQL_IBAN"));
                    System.out.println("Card number: " + resultSet.getString("SQL_cardNumber"));
                    System.out.println("Account balance: " + resultSet.getDouble("SQL_accountBalance"));
                    System.out.println("Account debt: " + resultSet.getDouble("SQL_debtBalance"));
                    System.out.println("First name: " + resultSet.getString("SQL_registerFirstName"));
                    System.out.println("Last name: " + resultSet.getString("SQL_registerLastName"));
                    System.out.println("Birth year: " + resultSet.getInt("SQL_registerBirthYear"));
                    System.out.println("Net income: " + resultSet.getDouble("SQL_registerNetIncome"));
                } else {
                    System.out.println("No user found with the provided email.");
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