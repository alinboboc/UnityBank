package org.example.auth;

import org.example.app.MainPage;
import org.example.app.SessionUser;
import org.example.database.DatabaseCredentials;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login extends DatabaseCredentials {

    public Login() throws SQLException {
        System.out.println("You chose to login into your account.");
        System.out.println("If you want to go back to the main menu, press 0 and ENTER");
        loginProcedure();
    }

    private void loginProcedure() throws SQLException {
        System.out.println("Enter your email address:");
        String emailInput = scanner.nextLine().trim();
        if (emailInput.equals("0")) {
            new MainPage();
            return;
        }

        try (Connection conn = getDatabaseConnectionDetails()) {
            String sql = "SELECT * FROM users WHERE SQL_registerEmail = ?";
            try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                preparedStatement.setString(1, emailInput);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        handlePassword(resultSet);
                    } else {
                        System.out.println("Email not found. Please try again.");
                        loginProcedure();
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            // Log the exception or handle it appropriately
        }
    }

    private void handlePassword(ResultSet resultSet) {
        int passwordAttemptsLeft = 4;
        try {
            while (passwordAttemptsLeft > 0) {
                System.out.print("Enter your password: ");
                String inputPassword = scanner.nextLine().trim();
                if (inputPassword.equals(resultSet.getString("SQL_registerPassword"))) {
                    setUserInfo(resultSet);
                    new MainPage();
                    return;
                } else {
                    passwordAttemptsLeft--;
                    if (passwordAttemptsLeft > 0) {
                        System.out.println("Your password is incorrect, please try again. " +
                                (passwordAttemptsLeft == 1 ? "This is the last attempt." : passwordAttemptsLeft + " attempts remaining"));
                    } else {
                        System.out.println("You exceeded the password limit. Try again.");
                        loginProcedure();
                        return;
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    private void setUserInfo(ResultSet resultSet) throws SQLException {
        SessionUser.setSQL_loginEmail(resultSet.getString("SQL_registerEmail"));
        SessionUser.setSQL_loginUsername(resultSet.getString("SQL_registerUsername"));
        SessionUser.setSQL_loginFirstName(resultSet.getString("SQL_registerFirstName"));
        SessionUser.setSQL_loginLastName(resultSet.getString("SQL_registerLastName"));
        SessionUser.setSQL_loginBirthYear(resultSet.getString("SQL_registerBirthYear"));
        SessionUser.setSQL_loginNetIncome(resultSet.getString("SQL_registerNetIncome"));
        SessionUser.setSQL_loginIBAN(resultSet.getString("SQL_IBAN"));
        SessionUser.setSQL_loginCardNumber(resultSet.getString("SQL_cardNumber"));
        SessionUser.setSQL_loginAccountBalance(resultSet.getString("SQL_accountBalance"));
        SessionUser.setSQL_loginDebtBalance(resultSet.getString("SQL_debtBalance"));
    }
}
