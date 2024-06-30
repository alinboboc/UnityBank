package org.example;

import org.example.app.LandingPage;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        LandingPage app = new LandingPage();
    }
}

/*
TODO LIST:
1. need to add uppercase validation for first name and last name before pushing in SQL database
2. need to add verification for credit request, if a credit is active and the monthly pay is 40% the credit cannot be received
3. need to check if session user is still required after the code refactor
4. need to add the functionality to modify the account details
5. need to recheck all the variables from classes because some of them doesn't have any meaning
6. need to test if i can use only one result set to update and read data from SQL,
7. register nu merge
8. NEED TO SET USER NAME LOCALLY TO UPDATE THE WELCOME MESSAGE.
*/