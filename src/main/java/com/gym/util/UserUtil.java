package com.gym.util;

import java.security.SecureRandom;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserUtil {
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int PASSWORD_LENGTH = 10;

    private static final Map<String, Integer> usernameTracker = new ConcurrentHashMap<>();

    /**
     * Generates a unique username by combining first name and last name.
     * If the username already exists, adds a serial number.
     */
    public static String generateUsername(String firstname, String lastname) {
        String baseUsername = firstname + "." + lastname;
        int count = usernameTracker.getOrDefault(baseUsername, 0) + 1;
        usernameTracker.put(baseUsername, count);

        return (count == 1) ? baseUsername : baseUsername + count;
    }

    /**
     * Generates a random 10-character password.
     */
    public static String generatePassword() {
        StringBuilder password = new StringBuilder(PASSWORD_LENGTH);
        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            password.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }
        return password.toString();
    }
}
