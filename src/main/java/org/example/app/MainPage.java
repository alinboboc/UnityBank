package org.example.app;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.stream.Collectors;

import static org.example.app.UserSession.getLoggedUserEmail;

public class MainPage extends SessionUser {

    public MainPage() throws SQLException {
        System.out.println("Welcome back!");
        mainPageNavigation();
    }

    private void mainPageNavigation() throws SQLException {
        try (Scanner scanner = new Scanner(System.in)) {
            boolean isValidInput = false;
            while (!isValidInput) {
                System.out.println("1. Check account details");
                System.out.println("2. Deposit money");
                System.out.println("3. Send money");
                System.out.println("4. Request credit");
                System.out.println("5. Log off");
                System.out.print("-> ");
                String userInput = filterWhitespace(scanner.nextLine());

                switch (userInput) {
                    case "1":
                        new CheckAccountDetails();
                        isValidInput = true;
                        break;
                    case "2":
                        new DepositMoney();
                        isValidInput = true;
                        break;
                    case "3":
                        new SendMoney();
                        isValidInput = true;
                        break;
                    case "4":
                        new RequestCredit();
                        isValidInput = true;
                        break;
                    case "5":
                        System.out.println("Goodbye!");
                        new LandingPage();
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
