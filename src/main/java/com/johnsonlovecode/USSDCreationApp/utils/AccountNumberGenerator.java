package com.johnsonlovecode.USSDCreationApp.utils;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class AccountNumberGenerator {

    public String generateAccountNumber() {
        int maxDigits = 10;
        Random random = new Random();
        long number = (long) (random.nextDouble() * Math.pow(10, maxDigits));
        return String.format("%010d", number);
    }
}
