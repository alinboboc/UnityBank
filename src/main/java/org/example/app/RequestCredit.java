package org.example.app;

import org.example.database.DatabaseCredentials;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Year;
import java.util.Scanner;

public class RequestCredit extends DatabaseCredentials {
    private static final int MINIMUM_NET_INCOME = 2500;
    private static final double MAXIMUM_REQUEST_RATE = 0.4;
    private static final int MINIMUM_AGE = 18;
    private static final int MAX_PAYMENT_MONTHS = 60;

    private final Scanner scanner = new Scanner(System.in);

    public RequestCredit() throws SQLException {
        System.out.println("You chose to request a credit.");
        System.out.println("We can offer you a credit with 5% interest rate.");
        System.out.println("If you want to continue press 1 then Enter or if you want to go back, press 0 then Enter.");
        checkIfEligible();
    }

    private void checkIfEligible() throws SQLException {
        String userInput = scanner.nextLine();
        if ("0".equals(userInput)) {
            new MainPage();
            return;
        }

        System.out.println("Now we need to check if you are eligible to request a credit.");
        System.out.println("You must be at least 18 years old and have a minimum net income of 2500 RON.");

        try (Connection conn = getDatabaseConnectionDetails()) {
            String sqlEmailQuery = "SELECT SQL_registerBirthYear, SQL_registerNetIncome FROM users WHERE SQL_registerEmail = ?";
            try (PreparedStatement preparedStatement = conn.prepareStatement(sqlEmailQuery)) {
                preparedStatement.setString(1, getSQL_loginEmail());

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        int userBirthYear = resultSet.getInt("SQL_registerBirthYear");
                        int userNetIncome = resultSet.getInt("SQL_registerNetIncome");
                        int currentYear = Year.now().getValue();

                        if (currentYear - userBirthYear >= MINIMUM_AGE && userNetIncome >= MINIMUM_NET_INCOME) {
                            int maxCreditRequest = creditCalculator(userNetIncome);
                            System.out.println("You can request a credit!");
                            System.out.println("The maximum sum you can request is " + maxCreditRequest);

                            processCreditRequest(maxCreditRequest);
                        } else {
                            System.out.println("You do not meet the eligibility criteria to request a credit.");
                            new MainPage();
                        }
                    } else {
                        System.out.println("User not found.");
                        new MainPage();
                    }
                }
            }
        }
    }

    private void processCreditRequest(int maxCreditRequest) throws SQLException {
        System.out.println("Enter the sum you want to request:");
        int sumRequest = Integer.parseInt(scanner.nextLine());

        while (sumRequest > maxCreditRequest) {
            System.out.println("The sum you entered is bigger than the maximum.");
            System.out.println("You can request up to " + maxCreditRequest);
            System.out.println("If you want to cancel the request, press 0 then Enter.");
            sumRequest = Integer.parseInt(scanner.nextLine());

            if (sumRequest == 0) {
                new MainPage();
                return;
            }
        }

        System.out.println("The sum you requested is " + sumRequest);
        System.out.println("How many months do you want to pay? (max 60)");
        int months = Integer.parseInt(scanner.nextLine());

        while (months > MAX_PAYMENT_MONTHS) {
            System.out.println("The period is too long, maximum period is 60 months.");
            System.out.println("If you want to cancel the request, press 0 then Enter.");
            months = Integer.parseInt(scanner.nextLine());

            if (months == 0) {
                new MainPage();
                return;
            }
        }

        double monthlyPayment = (double) sumRequest / months;
        System.out.println("Your monthly payment will be " + monthlyPayment);
        System.out.println("If you accept the credit conditions, press 1 then Enter, otherwise press 0 then Enter.");
        String creditDecision = scanner.nextLine();

        if ("0".equals(creditDecision)) {
            new MainPage();
            return;
        }

        if ("1".equals(creditDecision)) {
            try (Connection conn = getDatabaseConnectionDetails()) {
                String sqlEmailQuery = "SELECT SQL_debtBalance, SQL_accountBalance FROM users WHERE SQL_registerEmail = ?";
                try (PreparedStatement preparedStatement = conn.prepareStatement(sqlEmailQuery)) {
                    preparedStatement.setString(1, getSQL_loginEmail());

                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        if (resultSet.next()) {
                            int currentDebtBalance = resultSet.getInt("SQL_debtBalance");
                            int currentAccountBalance = resultSet.getInt("SQL_accountBalance");

                            int newDebtBalance = currentDebtBalance + sumRequest;
                            int newAccountBalance = currentAccountBalance + sumRequest;

                            String updateBalanceQuery = "UPDATE users SET SQL_debtBalance = ?, SQL_accountBalance = ? WHERE SQL_registerEmail = ?";
                            try (PreparedStatement updateStmt = conn.prepareStatement(updateBalanceQuery)) {
                                updateStmt.setInt(1, newDebtBalance);
                                updateStmt.setInt(2, newAccountBalance);
                                updateStmt.setString(3, getSQL_loginEmail());
                                updateStmt.executeUpdate();

                                System.out.println("Your credit request has been approved and the balance has been updated.");
                                new MainPage();
                            }
                        } else {
                            System.out.println("User not found.");
                            new MainPage();
                        }
                    }
                }
            }
        }
    }

    private int creditCalculator(int userNetIncome) {
        return (int) (userNetIncome * MAXIMUM_REQUEST_RATE * MAX_PAYMENT_MONTHS);
    }
}
