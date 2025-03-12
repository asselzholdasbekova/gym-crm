package com.gym.util;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

class UserUtilTest {

    @Test
    void testGeneratePassword() {
        String password = UserUtil.generatePassword();
        assertNotNull(password);
        assertEquals(10, password.length());
        assertTrue(password.matches("[A-Za-z0-9]{10}"));
    }

    @Test
    void testGenerateUsername_Unique() {
        Predicate<String> existsByUsername = username -> false; // No username exists
        String username = UserUtil.generateUsername("John", "Doe", existsByUsername);
        assertEquals("John.Doe", username);
    }

    @Test
    void testGenerateUsername_WithDuplicates() {
        Set<String> existingUsernames = new HashSet<>();
        existingUsernames.add("John.Doe");
        existingUsernames.add("John.Doe1");

        Predicate<String> existsByUsername = existingUsernames::contains;

        String newUsername = UserUtil.generateUsername("John", "Doe", existsByUsername);
        assertEquals("John.Doe2", newUsername);
    }
}
