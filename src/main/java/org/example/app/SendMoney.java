package org.example.app;

import org.example.database.DatabaseCredentials;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class SendMoney extends DatabaseCredentials {

    public SendMoney() throws SQLException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the amount you want to send:");
        String moneyToSendStr = scanner.nextLine();
        int moneyToSend;
        try {
            moneyToSend = Integer.parseInt(moneyToSendStr);
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount entered.");
            return;
        }

        System.out.println("Enter the IBAN of the person you want to receive the money:");
        String receiverIBAN = scanner.nextLine();

        String senderEmail = getSQL_loginEmail();

        try (Connection conn = getDatabaseConnectionDetails()) {
            conn.setAutoCommit(false);

            String getSenderBalanceQuery = "SELECT SQL_accountBalance FROM users WHERE SQL_registerEmail = ?";
            try (PreparedStatement getSenderBalanceStmt = conn.prepareStatement(getSenderBalanceQuery)) {
                getSenderBalanceStmt.setString(1, senderEmail);
                try (ResultSet senderResultSet = getSenderBalanceStmt.executeQuery()) {
                    if (senderResultSet.next()) {
                        int senderBalance = senderResultSet.getInt("SQL_accountBalance");
                        if (senderBalance < moneyToSend) {
                            System.out.println("Insufficient funds.");
                            new MainPage();
                        }

                        String getReceiverQuery = "SELECT SQL_accountBalance FROM users WHERE SQL_IBAN = ?";
                        try (PreparedStatement getReceiverStmt = conn.prepareStatement(getReceiverQuery)) {
                            getReceiverStmt.setString(1, receiverIBAN);
                            try (ResultSet receiverResultSet = getReceiverStmt.executeQuery()) {
                                if (receiverResultSet.next()) {
                                    int receiverBalance = receiverResultSet.getInt("SQL_accountBalance");

                                    String updateSenderBalanceQuery = "UPDATE users SET SQL_accountBalance = ? WHERE SQL_registerEmail = ?";
                                    try (PreparedStatement updateSenderStmt = conn.prepareStatement(updateSenderBalanceQuery)) {
                                        updateSenderStmt.setInt(1, senderBalance - moneyToSend);
                                        updateSenderStmt.setString(2, senderEmail);
                                        updateSenderStmt.executeUpdate();
                                    }

                                    String updateReceiverBalanceQuery = "UPDATE users SET SQL_accountBalance = ? WHERE SQL_IBAN = ?";
                                    try (PreparedStatement updateReceiverStmt = conn.prepareStatement(updateReceiverBalanceQuery)) {
                                        updateReceiverStmt.setInt(1, receiverBalance + moneyToSend);
                                        updateReceiverStmt.setString(2, receiverIBAN);
                                        updateReceiverStmt.executeUpdate();
                                    }

                                    conn.commit();
                                    System.out.println("You sent " + moneyToSend + " to " + receiverIBAN);
                                    new MainPage();
                                } else {
                                    System.out.println("Receiver not found.");
                                    conn.rollback();
                                    new MainPage();
                                }
                            }
                        }
                    } else {
                        System.out.println("Sender not found.");
                        conn.rollback();
                        new MainPage();
                    }
                }
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }
}