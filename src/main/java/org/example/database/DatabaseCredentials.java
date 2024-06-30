package org.example.database;

import org.example.app.DataValidators;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class DatabaseCredentials extends DataValidators {

    public final Scanner scanner = new Scanner(System.in);

    public Connection getDatabaseConnectionDetails() throws SQLException {
        String USER = "root";
        String PASS = "root";
        String DB_URL = "jdbc:mysql://localhost:3306/UNITYBANK";
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }
}