package org.example.auth;

import org.example.app.LandingPage;
import org.example.database.DatabaseCredentials;

import java.sql.*;
import java.util.Scanner;

public class Register extends DatabaseCredentials {

    private final Scanner scanner = new Scanner(System.in);

    public Register() {
        System.out.println("You chose to register a new account.");
        System.out.println("If you want to go back to the main menu, press 0 and ENTER");
        registerProcedure();
    }

    public void registerProcedure() {
        try (Connection conn = getDatabaseConnectionDetails()) {
            registerEmail();
            registerUsername();
            registerFirstName();
            registerLastName();
            registerBirthYear();
            registerNetIncome();
            registerPassword();
            accountCreation();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void registerEmail() throws SQLException {
        System.out.println("Enter your email address:");
        setSQL_registerEmail(scanner.nextLine());
        if (getSQL_registerEmail().equals("0")) {
            new LandingPage();
            return;
        }
        Connection conn = getDatabaseConnectionDetails();
        String SQLEmail = "SELECT * FROM users WHERE SQL_registerEmail = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(SQLEmail)) {
            preparedStatement.setString(1, getSQL_registerEmail());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    System.out.println("The email is already registered, please try with another one or login.");
                } else {
                    if (isEmailValid(getSQL_registerEmail())) {
                        System.out.println("EMAIL IS OK");
                    } else {
                        System.out.println("The email you provided is invalid, please try again");
                        registerEmail();
                    }
                }
            }
        }
    }

    public void registerUsername() throws SQLException {
        System.out.println("Enter your desired username:");
        setSQL_registerUsername(scanner.nextLine());
        Connection conn = getDatabaseConnectionDetails();
        String SQLUsername = "SELECT * FROM users WHERE SQL_registerUsername = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(SQLUsername)) {
            preparedStatement.setString(1, getSQL_registerUsername());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    System.out.println("The username is already registered, please try with another one.");
                } else {
                    if (isUsernameValid(getSQL_registerUsername())) {
                        System.out.println("USERNAME IS OK");
                    } else {
                        System.out.println("The username you provided is invalid, it must contains only letters and numbers.");
                        registerUsername();
                    }
                }
            }
        }
    }

    public void registerFirstName() {
        System.out.println("Enter your first name:");
        setSQL_registerFirstName(scanner.nextLine());
        if (isNameValid(getSQL_registerFirstName())) {
            System.out.println("FIRST NAME IS OK");
        } else {
            System.out.println("The first name you added does not contain only letters. Please try again.");
            registerFirstName();
        }
    }

    public void registerLastName() {
        System.out.println("Enter your last name:");
        setSQL_registerLastName(scanner.nextLine());
        if (isNameValid(getSQL_registerLastName())) {
            System.out.println("LAST NAME IS OK");
        } else {
            System.out.println("The LAST name you added does not contain only letters. Please try again.");
            registerLastName();
        }
    }

    public void registerBirthYear() {
        System.out.println("Enter your birth year:");
        setSQL_registerBirthYear(scanner.nextLine().trim());
        if (isBirthYearValid(getSQL_registerBirthYear())) {
            System.out.println("AGE IS OK");
        } else {
            System.out.println("The age is not ok");
            registerBirthYear();
        }
    }

    public void registerNetIncome() {
        System.out.println("Enter your net income:");
        setSQL_registerNetIncome(scanner.nextLine());
        if (isNetIncomeValid(getSQL_registerNetIncome())) {
            System.out.println("NET INCOME IS OK");
        } else {
            System.out.println("THE NET INCOME CONTAINS INVALID CHARACTERS");
            registerNetIncome();
        }
    }

    public void registerPassword() {
        System.out.println("Enter your password:");
        String password = scanner.nextLine();
        if (isPasswordValid(password)) {
            System.out.println("Confirm your password:");
            setSQL_registerPassword(scanner.nextLine());
            if (password.equals(getSQL_registerPassword())) {
                System.out.println("Your account has been created");
            } else {
                System.out.println("The password doesn't match. Try again.");
                registerPassword();
            }
        } else {
            System.out.println("The password does not contain at least 8 characters containing numbers and letters. Try again.");
            registerPassword();
        }
    }

    public void IBANsetter() throws SQLException {
        String IBAN = IBANGenerator();
        Connection conn = getDatabaseConnectionDetails();
        String SQLUsername = "SELECT * FROM users WHERE SQL_IBAN = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(SQLUsername)) {
            preparedStatement.setString(1, IBAN);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    IBANsetter();
                } else {
                    setSQL_IBAN(IBAN);
                }
            }
        }
    }

    public void cardNumberSetter() throws SQLException {
        String cardNumber = String.valueOf(cardNumberGenerator());
        Connection conn = getDatabaseConnectionDetails();
        String SQLUsername = "SELECT * FROM users WHERE SQL_cardNumber = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(SQLUsername)) {
            preparedStatement.setString(1, getSQL_cardNumber());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    cardNumberSetter();
                } else {
                    setSQL_cardNumber(cardNumber);
                }
            }
        }
    }

    public void accountCreation() throws SQLException {
        IBANsetter();
        cardNumberSetter();
        pinGenerator();

        Connection conn = getDatabaseConnectionDetails();
        String accountDetails = "INSERT INTO users (\n" +
                " SQL_registerEmail,\n" +
                " SQL_registerUsername, \n" +
                " SQL_registerPassword, \n" +
                " SQL_registerFirstName, \n" +
                " SQL_registerLastName, \n" +
                " SQL_registerBirthYear, \n" +
                " SQL_registerNetIncome, \n" +
                " SQL_IBAN, \n" +
                " SQL_cardNumber, \n" +
                " SQL_PIN, \n" +
                " SQL_accountBalance, \n" +
                " SQL_debtBalance)\n" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = conn.prepareStatement(accountDetails)) {
            preparedStatement.setString(1, getSQL_registerEmail());
            preparedStatement.setString(2, getSQL_registerUsername());
            preparedStatement.setString(3, getSQL_registerPassword());
            preparedStatement.setString(4, getSQL_registerFirstName());
            preparedStatement.setString(5, getSQL_registerLastName());
            preparedStatement.setString(6, getSQL_registerBirthYear());
            preparedStatement.setString(7, getSQL_registerNetIncome());
            preparedStatement.setString(8, setSQL_IBAN(IBANGenerator()));
            preparedStatement.setString(9, setSQL_cardNumber(cardNumberGenerator()));
            preparedStatement.setString(10, setSQL_PIN(pinGenerator()));
            preparedStatement.setString(11, "0");
            preparedStatement.setString(12, "0");

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User account created successfully.");
            } else {
                System.out.println("Failed to create user account.");
            }
        } catch (SQLException e) {
            System.err.println("Error creating user account: " + e.getMessage());
            throw e;
        }
    }
}