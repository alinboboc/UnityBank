package org.example.app;

import java.time.Year;
import java.util.regex.Pattern;

public class DataValidators extends DataGenerators {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[\\p{Alnum}]*$");
    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z]+$");
    private static final Pattern NET_INCOME_PATTERN = Pattern.compile("^\\d+$");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^[\\p{Alnum}]{8,}$"); // At least 8 characters

    public boolean isEmailValid(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

    public boolean isUsernameValid(String username) {
        return USERNAME_PATTERN.matcher(username).matches();
    }

    public boolean isNameValid(String name) {
        return NAME_PATTERN.matcher(name).matches();
    }

    public boolean isNetIncomeValid(String netIncome) {
        return NET_INCOME_PATTERN.matcher(netIncome).matches();
    }

    public boolean isPasswordValid(String password) {
        return PASSWORD_PATTERN.matcher(password).matches();
    }

    public boolean isBirthYearValid(String birthYear) {
        if (!birthYear.matches("\\d{4}")) {
            return false;
        }

        int currentYear = Year.now().getValue();
        int inputYear;
        try {
            inputYear = Integer.parseInt(birthYear);
        } catch (NumberFormatException e) {
            return false;
        }

        int age = currentYear - inputYear;
        return inputYear <= currentYear && age >= 18;
    }
}