package org.example.auth;

import org.example.app.LandingPage;
import org.example.database.DatabaseCredentials;

import java.sql.*;

public class Register extends DatabaseCredentials {

    public Register() throws SQLException {
        System.out.println("You chose to register a new account.");
        registerProcedure();
    }

    public void registerProcedure() throws SQLException {
        registerEmail();
    }

    public void registerEmail() throws SQLException {
        System.out.println("Enter your email address:");
        pressToReturn();
        setSQL_registerEmail(scanner.nextLine());
        if (getSQL_registerEmail().equals("0")) {
            new LandingPage();
            return;
        }
        Connection connection = getDatabaseConnectionDetails();
        String query = "SELECT * FROM users WHERE SQL_registerEmail = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, getSQL_registerEmail());
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    System.out.println("The email is already registered, please try with another one or login.");
                    new LandingPage();
                } else {
                    if (isEmailValid(getSQL_registerEmail())) {
                        registerUsername();
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
        pressToReturn();
        setSQL_registerUsername(scanner.nextLine());
        Connection connection = getDatabaseConnectionDetails();
        String query = "SELECT * FROM users WHERE SQL_registerUsername = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, getSQL_registerUsername());
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    System.out.println("The username is already registered, please try with another one.");
                    registerUsername();
                } else {
                    if ("0".equals(getSQL_registerUsername())) {
                        new LandingPage();
                    } else if (isUsernameValid(getSQL_registerUsername())) {
                        registerFirstName();
                    } else {
                        System.out.println("The username you provided is invalid, it must contains only letters and numbers.");
                        registerUsername();
                    }
                }
            }
        }
    }

    public void registerFirstName() throws SQLException {
        System.out.println("Enter your first name:");
        pressToReturn();
        setSQL_registerFirstName(scanner.nextLine());
        if (isNameValid(getSQL_registerFirstName())) {
            registerLastName();
        } else {
            pressToReturn();
            if (getSQL_registerFirstName().equals("0")) {
                new LandingPage();
            } else {
                System.out.println("The first name you added does not contain only letters. Please try again.");
                registerFirstName();
            }
        }
    }

    public void registerLastName() throws SQLException {
        System.out.println("Enter your last name:");
        pressToReturn();
        setSQL_registerLastName(scanner.nextLine());
        if (isNameValid(getSQL_registerLastName())) {
            registerBirthYear();
        } else {
            pressToReturn();
            if (getSQL_registerLastName().equals("0")) {
                new LandingPage();
            } else {
                System.out.println("The last name must contain only letters. Please try again.");
                registerLastName();
            }
        }
    }

    public void registerBirthYear() throws SQLException {
        System.out.println("Enter your birth year:");
        pressToReturn();
        setSQL_registerBirthYear(scanner.nextLine().trim());
        if (isBirthYearValid(getSQL_registerBirthYear())) {
            registerNetIncome();
        } else {
            pressToReturn();
            if (getSQL_registerBirthYear().equals("0")) {
                new LandingPage();
            } else {
                System.out.println("The age is not valid, please try again.");
                registerBirthYear();
            }
        }
    }

    public void registerNetIncome() throws SQLException {
        System.out.println("Enter your net income:");
        pressToReturn();
        setSQL_registerNetIncome(scanner.nextLine());
        if (isNetIncomeValid(getSQL_registerNetIncome())) {
            registerPassword();
        } else {
            pressToReturn();
            if (getSQL_registerNetIncome().equals("0")) {
                new LandingPage();
            } else {
                System.out.println("The net income contains invalid characters, please try again.");
                registerNetIncome();
            }
        }
    }

    public void registerPassword() throws SQLException {
        System.out.println("Enter your password:");
        String password = scanner.nextLine();
        if (isPasswordValid(password)) {
            System.out.println("Confirm your password:");
            setSQL_registerPassword(scanner.nextLine());
            if (password.equals(getSQL_registerPassword())) {
                accountCreation();
            } else {
                System.out.println("The password doesn't match. Try again.");
                registerPassword();
            }
        } else {
            System.out.println("The password does not contain at least 8 characters containing numbers and letters. Try again.");
            registerPassword();
        }
    }

    public void IBAN_setter() throws SQLException {
        String IBAN = IBANGenerator();
        Connection connection = getDatabaseConnectionDetails();
        String query = "SELECT * FROM users WHERE SQL_IBAN = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, IBAN);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    IBAN_setter();
                } else {
                    setSQL_IBAN(IBAN);
                }
            }
        }
    }

    public void cardNumberSetter() throws SQLException {
        String cardNumber = String.valueOf(cardNumberGenerator());
        Connection connection = getDatabaseConnectionDetails();
        String query = "SELECT * FROM users WHERE SQL_cardNumber = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, getSQL_cardNumber());
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    cardNumberSetter();
                } else {
                    setSQL_cardNumber(cardNumber);
                }
            }
        }
    }

    public void accountCreation() throws SQLException {
        IBAN_setter();
        cardNumberSetter();
        pinGenerator();

        Connection conn = getDatabaseConnectionDetails();
        String accountDetails = """
                INSERT INTO users (
                 SQL_registerEmail,
                 SQL_registerUsername,\s
                 SQL_registerPassword,\s
                 SQL_registerFirstName,\s
                 SQL_registerLastName,\s
                 SQL_registerBirthYear,\s
                 SQL_registerNetIncome,\s
                 SQL_IBAN,\s
                 SQL_cardNumber,\s
                 SQL_PIN,\s
                 SQL_accountBalance,\s
                 SQL_debtBalance)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)""";

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
                System.out.println("Your account has been created.");
                new LandingPage();
            } else {
                System.out.println("Failed to create user account.");
                new LandingPage();
            }
        } catch (SQLException e) {
            System.err.println("Error creating user account: " + e.getMessage());
            throw e;
        }
    }

    public void pressToReturn() {
        System.out.println("If you want to go back to the main menu, press 0 and ENTER");
    }
}