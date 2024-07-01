package org.example.app;

import org.example.database.DatabaseCredentials;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import static org.example.app.UserSession.getLoggedUserEmail;

public class SendMoney extends DatabaseCredentials {

    private final Scanner scanner = new Scanner(System.in);

    public SendMoney() throws SQLException {
        try {
            sendMoney();
        } catch (SQLException e) {
            System.out.println("An error occurred while sending money: " + e.getMessage());
        }
    }

    private void sendMoney() throws SQLException {
        System.out.println("Enter the amount you want to send:");
        String moneyToSendStr = scanner.nextLine();

        if (!isValidAmount(moneyToSendStr)) {
            System.out.println("Invalid amount entered. Please enter a positive number.");
            new MainPage();
        }

        int moneyToSend = Integer.parseInt(moneyToSendStr);
        if (moneyToSend <= 0) {
            System.out.println("Amount must be greater than zero.");
            new MainPage();
        }

        System.out.println("Enter the IBAN of the person you want to send money to:");
        String receiverIBAN = scanner.nextLine();

        String senderEmail = getLoggedUserEmail();

        try (Connection conn = getDatabaseConnectionDetails()) {
            conn.setAutoCommit(false);

            int senderBalance = getAccountBalance(conn, senderEmail);
            if (senderBalance < moneyToSend) {
                System.out.println("Insufficient funds.");
                conn.rollback();
                new MainPage();
            }

            int receiverBalance = getAccountBalanceByIBAN(conn, receiverIBAN);
            if (receiverBalance < 0) {
                System.out.println("Receiver not found.");
                conn.rollback();
                new MainPage();
            }

            updateSenderBalance(conn, senderEmail, senderBalance - moneyToSend);
            updateReceiverBalance(conn, receiverIBAN, receiverBalance + moneyToSend);

            conn.commit();
            System.out.println("You successfully sent " + moneyToSend + " to " + receiverIBAN);
            new MainPage();

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            throw e; // Rethrow the exception to propagate it up the call stack
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

    private int getAccountBalance(Connection conn, String userEmail) throws SQLException {
        String query = "SELECT SQL_accountBalance FROM users WHERE SQL_registerEmail = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, userEmail);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("SQL_accountBalance");
                } else {
                    return -1; // User not found
                }
            }
        }
    }

    private int getAccountBalanceByIBAN(Connection conn, String iban) throws SQLException {
        String query = "SELECT SQL_accountBalance FROM users WHERE SQL_IBAN = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, iban);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("SQL_accountBalance");
                } else {
                    return -1; // Receiver not found
                }
            }
        }
    }

    private void updateSenderBalance(Connection conn, String userEmail, int newBalance) throws SQLException {
        String query = "UPDATE users SET SQL_accountBalance = ? WHERE SQL_registerEmail = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, newBalance);
            stmt.setString(2, userEmail);
            stmt.executeUpdate();
        }
    }

    private void updateReceiverBalance(Connection conn, String iban, int newBalance) throws SQLException {
        String query = "UPDATE users SET SQL_accountBalance = ? WHERE SQL_IBAN = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, newBalance);
            stmt.setString(2, iban);
            stmt.executeUpdate();
        }
    }
}
