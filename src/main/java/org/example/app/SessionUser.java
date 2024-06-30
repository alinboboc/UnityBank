package org.example.app;

public class SessionUser{

    private String SQL_registerEmail;
    private String SQL_registerUsername;
    private String SQL_registerPassword;
    private String SQL_registerFirstName;
    private String SQL_registerLastName;
    private String SQL_registerBirthYear;
    private String SQL_registerNetIncome;

    public String getSQL_registerEmail() {
        return SQL_registerEmail;
    }

    public void setSQL_registerEmail(String SQL_registerEmail) {
        this.SQL_registerEmail = SQL_registerEmail;
    }

    public String getSQL_registerUsername() {
        return SQL_registerUsername;
    }

    public void setSQL_registerUsername(String SQL_registerUsername) {
        this.SQL_registerUsername = SQL_registerUsername;
    }

    public String getSQL_registerPassword() {
        return SQL_registerPassword;
    }

    public void setSQL_registerPassword(String SQL_registerPassword) {
        this.SQL_registerPassword = SQL_registerPassword;
    }

    public String getSQL_registerFirstName() {
        return SQL_registerFirstName;
    }

    public void setSQL_registerFirstName(String SQL_registerFirstName) {
        this.SQL_registerFirstName = SQL_registerFirstName;
    }

    public String getSQL_registerLastName() {
        return SQL_registerLastName;
    }

    public void setSQL_registerLastName(String SQL_registerLastName) {
        this.SQL_registerLastName = SQL_registerLastName;
    }

    public String getSQL_registerBirthYear() {
        return SQL_registerBirthYear;
    }

    public void setSQL_registerBirthYear(String SQL_registerBirthYear) {
        this.SQL_registerBirthYear = SQL_registerBirthYear;
    }

    public String getSQL_registerNetIncome() {
        return SQL_registerNetIncome;
    }

    public void setSQL_registerNetIncome(String SQL_registerNetIncome) {
        this.SQL_registerNetIncome = SQL_registerNetIncome;
    }

    private String SQL_IBAN;
    private String SQL_cardNumber;
    private String SQL_accountBalance;
    private String SQL_debtBalance;
    private String SQL_PIN;

    public String getSQL_IBAN() {
        return SQL_IBAN;
    }

    public String setSQL_IBAN(String SQL_IBAN) {
        this.SQL_IBAN = SQL_IBAN;
        return SQL_IBAN;
    }

    public String getSQL_cardNumber() {
        return SQL_cardNumber;
    }

    public String setSQL_cardNumber(String SQL_cardNumber) {
        this.SQL_cardNumber = SQL_cardNumber;
        return SQL_cardNumber;
    }

    public String getSQL_accountBalance() {
        return SQL_accountBalance;
    }

    public void setSQL_accountBalance(String SQL_accountBalance) {
        this.SQL_accountBalance = SQL_accountBalance;
    }

    public String getSQL_debtBalance() {
        return SQL_debtBalance;
    }

    public void setSQL_debtBalance(String SQL_debtBalance) {
        this.SQL_debtBalance = SQL_debtBalance;
    }

    public String getSQL_PIN() {
        return SQL_PIN;
    }

    public String setSQL_PIN(String SQL_PIN) {
        this.SQL_PIN = SQL_PIN;
        return SQL_PIN;
    }

    private static String SQL_loginEmail;
    private static String SQL_loginUsername;
    private static String SQL_loginFirstName;
    private static String SQL_loginLastName;
    private static String SQL_loginBirthYear;
    private static String SQL_loginNetIncome;
    private static String SQL_loginIBAN;
    private static String SQL_loginCardNumber;
    private static String SQL_loginAccountBalance;
    private static String SQL_loginDebtBalance;

    public static String getSQL_loginEmail() {
        return SQL_loginEmail;
    }

    public static void setSQL_loginEmail(String SQL_loginEmail) {
        SessionUser.SQL_loginEmail = SQL_loginEmail;
    }

    public static String getSQL_loginUsername() {
        return SQL_loginUsername;
    }

    public static void setSQL_loginUsername(String SQL_loginUsername) {
        SessionUser.SQL_loginUsername = SQL_loginUsername;
    }

    public static String getSQL_loginFirstName() {
        return SQL_loginFirstName;
    }

    public static void setSQL_loginFirstName(String SQL_loginFirstName) {
        SessionUser.SQL_loginFirstName = SQL_loginFirstName;
    }

    public static String getSQL_loginLastName() {
        return SQL_loginLastName;
    }

    public static void setSQL_loginLastName(String SQL_loginLastName) {
        SessionUser.SQL_loginLastName = SQL_loginLastName;
    }

    public static String getSQL_loginBirthYear() {
        return SQL_loginBirthYear;
    }

    public static void setSQL_loginBirthYear(String SQL_loginBirthYear) {
        SessionUser.SQL_loginBirthYear = SQL_loginBirthYear;
    }

    public static String getSQL_loginNetIncome() {
        return SQL_loginNetIncome;
    }

    public static void setSQL_loginNetIncome(String SQL_loginNetIncome) {
        SessionUser.SQL_loginNetIncome = SQL_loginNetIncome;
    }

    public static String getSQL_loginIBAN() {
        return SQL_loginIBAN;
    }

    public static void setSQL_loginIBAN(String SQL_loginIBAN) {
        SessionUser.SQL_loginIBAN = SQL_loginIBAN;
    }

    public static String getSQL_loginCardNumber() {
        return SQL_loginCardNumber;
    }

    public static void setSQL_loginCardNumber(String SQL_loginCardNumber) {
        SessionUser.SQL_loginCardNumber = SQL_loginCardNumber;
    }

    public static String getSQL_loginAccountBalance() {
        return SQL_loginAccountBalance;
    }

    public static void setSQL_loginAccountBalance(String SQL_loginAccountBalance) {
        SessionUser.SQL_loginAccountBalance = SQL_loginAccountBalance;
    }

    public static String getSQL_loginDebtBalance() {
        return SQL_loginDebtBalance;
    }

    public static void setSQL_loginDebtBalance(String SQL_loginDebtBalance) {
        SessionUser.SQL_loginDebtBalance = SQL_loginDebtBalance;
    }

}
