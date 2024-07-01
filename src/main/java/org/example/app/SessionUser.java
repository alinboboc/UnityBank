package org.example.app;

public class SessionUser {

    private String SQL_registerEmail;
    private String SQL_registerUsername;
    private String SQL_registerPassword;
    private String SQL_registerFirstName;
    private String SQL_registerLastName;
    private String SQL_registerBirthYear;
    private String SQL_registerNetIncome;
    private String SQL_cardNumber;

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


    public String setSQL_IBAN(String SQL_IBAN) {
        return SQL_IBAN;
    }

    public String getSQL_cardNumber() {
        return SQL_cardNumber;
    }

    public String setSQL_cardNumber(String SQL_cardNumber) {
        this.SQL_cardNumber = SQL_cardNumber;
        return SQL_cardNumber;
    }


    public String setSQL_PIN(String SQL_PIN) {
        return SQL_PIN;
    }

}
