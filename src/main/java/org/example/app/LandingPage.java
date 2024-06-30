package org.example.app;

import org.example.auth.Login;
import org.example.auth.Register;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.stream.Collectors;

public class LandingPage {

    public LandingPage() throws SQLException {
        welcome();
        mainNavigation();
    }

    private void welcome() {
        System.out.println("Welcome to Unity Bank!");
        System.out.println("To continue, please login or register a new account.");
    }

    private void mainNavigation() throws SQLException {
        try (Scanner scanner = new Scanner(System.in)) {
            boolean isValidInput = false;
            while (!isValidInput) {
                System.out.println("1. Login");
                System.out.println("2. Register");
                System.out.println("3. Exit application");
                System.out.print("-> ");
                String userInput = filterWhitespace(scanner.nextLine());

                switch (userInput) {
                    case "1":
                        new Login();
                        isValidInput = true;
                        break;
                    case "2":
                        new Register();
                        isValidInput = true;
                        break;
                    case "3":
                        System.out.println("You choose to exit the application");
                        isValidInput = true;
                        break;
                    default:
                        System.out.println("The input is not valid. Please try again.");
                        break;
                }
            }
        }
    }

    private String filterWhitespace(String input) {
        return input.chars()
                .filter(c -> !Character.isWhitespace(c))
                .mapToObj(c -> String.valueOf((char) c))
                .collect(Collectors.joining());
    }
}