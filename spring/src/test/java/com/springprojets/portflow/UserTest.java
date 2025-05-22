package com.springprojets.portflow;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    private Validator validator;
    private User user;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        
        user = new User(
            "test@example.com",
            "password123",
            UserRole.CLIENT,
            "John",
            "Doe"
        );
    }

    @Test
    void testValidUser() {
        var violations = validator.validate(user);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testInvalidEmail() {
        user.setEmail("invalid-email");
        var violations = validator.validate(user);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertTrue(violations.stream()
            .anyMatch(v -> v.getMessage().contains("Invalid email format")));
    }

    @Test
    void testBlankEmail() {
        user.setEmail("");
        var violations = validator.validate(user);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertTrue(violations.stream()
            .anyMatch(v -> v.getMessage().contains("Email is required")));
    }

    @Test
    void testBlankPassword() {
        user.setPassword("");
        var violations = validator.validate(user);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertTrue(violations.stream()
            .anyMatch(v -> v.getMessage().contains("Password is required")));
    }

    @Test
    void testNullRole() {
        user.setRole(null);
        var violations = validator.validate(user);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertTrue(violations.stream()
            .anyMatch(v -> v.getMessage().contains("Role is required")));
    }

    @Test
    void testBlankFirstName() {
        user.setFirstName("");
        var violations = validator.validate(user);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertTrue(violations.stream()
            .anyMatch(v -> v.getMessage().contains("First name is required")));
    }

    @Test
    void testBlankLastName() {
        user.setLastName("");
        var violations = validator.validate(user);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertTrue(violations.stream()
            .anyMatch(v -> v.getMessage().contains("Last name is required")));
    }

    @Test
    void testCreatedAtIsSetOnConstruction() {
        User newUser = new User();
        assertNotNull(newUser.getCreatedAt());
        assertTrue(newUser.getCreatedAt().isBefore(LocalDateTime.now().plusSeconds(1)));
    }

    @Test
    void testAllArgsConstructor() {
        assertNotNull(user.getEmail());
        assertNotNull(user.getPassword());
        assertNotNull(user.getRole());
        assertNotNull(user.getFirstName());
        assertNotNull(user.getLastName());
        assertNotNull(user.getCreatedAt());
        
        assertEquals("test@example.com", user.getEmail());
        assertEquals("password123", user.getPassword());
        assertEquals(UserRole.CLIENT, user.getRole());
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
    }
} 