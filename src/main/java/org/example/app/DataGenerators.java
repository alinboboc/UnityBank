package org.example.app;

import java.util.Random;

public class DataGenerators extends SessionUser {

    private final Random random = new Random();

    public String randomNumbersGenerator() {
        int randomNumbers = random.nextInt(9000) + 1000; // Ensure four-digit number
        return String.valueOf(randomNumbers);
    }

    public String pinGenerator() {
        return randomNumbersGenerator();
    }

    public String IBANGenerator() {
        return "RO66BACX" + "4290" + randomNumbersGenerator() + randomNumbersGenerator() + randomNumbersGenerator();
    }

    public String cardNumberGenerator() {
        return "4290" + randomNumbersGenerator() + randomNumbersGenerator() + randomNumbersGenerator();
    }
}