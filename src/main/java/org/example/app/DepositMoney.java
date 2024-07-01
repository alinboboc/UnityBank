package org.example.app;

import org.example.database.DatabaseCredentials;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class DepositMoney extends DatabaseCredentials {

    private final Scanner scanner = new Scanner(System.in);

    public DepositMoney() {
        try {
            deposit();
        } catch (SQLException e) {
            System.out.println("An error occurred while accessing the database: " + e.getMessage());
        }
    }

    private void deposit() throws SQLException {
        System.out.println("Enter the amount you want to deposit:");
        String depositedSum = scanner.nextLine();

        if (!isValidAmount(depositedSum)) {
            System.out.println("Invalid amount. Please enter a positive number.");
            deposit();
        }

        int depositedAmount = Integer.parseInt(depositedSum);
        int currentAccountBalance = getCurrentAccountBalance();
        int newAccountSum = currentAccountBalance + depositedAmount;

        if (updateAccountBalance(newAccountSum)) {
            System.out.println("Account balance updated successfully.");
            new MainPage();
        } else {
            System.out.println("Failed to update account balance.");
            new MainPage();
        }
    }

    private boolean isValidAmount(String amount) {
        try {
            int value = Integer.parseInt(amount);
            return value > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private int getCurrentAccountBalance() throws SQLException {
        String query = "SELECT SQL_accountBalance FROM users WHERE SQL_registerEmail = ?";
        try (Connection conn = getDatabaseConnectionDetails();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, UserSession.getLoggedUserEmail());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("SQL_accountBalance");
                } else {
                    throw new SQLException("Logged user not found in the database.");
                }
            }
        }
    }

    private boolean updateAccountBalance(int newAccountSum) throws SQLException {
        String query = "UPDATE users SET SQL_accountBalance = ? WHERE SQL_registerEmail = ?";
        try (Connection conn = getDatabaseConnectionDetails();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, newAccountSum);
            preparedStatement.setString(2, UserSession.getLoggedUserEmail());
            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;
        }
    }
}
