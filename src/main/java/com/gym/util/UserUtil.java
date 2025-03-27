package com.gym.util;

import com.gym.model.User;

import java.security.SecureRandom;
import java.util.Optional;
import java.util.function.Predicate;

public class UserUtil {
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int PASSWORD_LENGTH = 10;

    private UserUtil() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Generates a unique username by checking its existence using the given predicate.
     */
    public static String generateUsername(String firstname, String lastname, Predicate<String> existsByUsername) {
        int suffix = 1;
        String username = normalize(firstname) + "." + normalize(lastname);
        String base = username;

        while (existsByUsername.test(username)) {
            username = base + suffix;
            suffix++;
        }

        return username;
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

    /**
     * Normalizes a string by converting it to lowercase and replacing spaces.
     */
    private static String normalize(String name) {
        return name.trim().toLowerCase().replaceAll("\\s+", "_");
    }

    public static void authenticate(User user, String password) {
        if (!PasswordEncoder.matches(password, user.getPassword())) {
            throw new SecurityException("Invalid username or password");
        }
    }
}
