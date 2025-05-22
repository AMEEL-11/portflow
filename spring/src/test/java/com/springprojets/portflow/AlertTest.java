package com.springprojets.portflow;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class AlertTest {
    private Validator validator;
    private Alert alert;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        
        alert = new Alert(
            "Test Alert",
            "This is a test alert message",
            AlertPriority.HIGH,
            "[\"ADMINISTRATOR\",\"OPERATIONS_MANAGER\"]"
        );
    }

    @Test
    void testValidAlert() {
        var violations = validator.validate(alert);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testBlankTitle() {
        alert.setTitle("");
        var violations = validator.validate(alert);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertTrue(violations.stream()
            .anyMatch(v -> v.getMessage().contains("Title is required")));
    }

    @Test
    void testBlankMessage() {
        alert.setMessage("");
        var violations = validator.validate(alert);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertTrue(violations.stream()
            .anyMatch(v -> v.getMessage().contains("Message is required")));
    }

    @Test
    void testNullPriority() {
        alert.setPriority(null);
        var violations = validator.validate(alert);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertTrue(violations.stream()
            .anyMatch(v -> v.getMessage().contains("Priority is required")));
    }

    @Test
    void testBlankTargetRoles() {
        alert.setTargetRoles("");
        var violations = validator.validate(alert);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertTrue(violations.stream()
            .anyMatch(v -> v.getMessage().contains("Target roles are required")));
    }

    @Test
    void testUpdateStatus() {
        LocalDateTime beforeUpdate = LocalDateTime.now();
        alert.updateStatus(AlertStatus.ACKNOWLEDGED);
        
        assertEquals(AlertStatus.ACKNOWLEDGED, alert.getStatus());
        assertNotNull(alert.getUpdatedAt());
        assertTrue(alert.getUpdatedAt().isAfter(beforeUpdate));
        assertNull(alert.getResolvedAt());

        alert.updateStatus(AlertStatus.RESOLVED);
        assertEquals(AlertStatus.RESOLVED, alert.getStatus());
        assertNotNull(alert.getResolvedAt());
        assertTrue(alert.getResolvedAt().isAfter(beforeUpdate));
    }

    @Test
    void testDefaultConstructor() {
        Alert newAlert = new Alert();
        
        assertNotNull(newAlert.getCreatedAt());
        assertEquals(AlertStatus.NEW, newAlert.getStatus());
        assertNull(newAlert.getUpdatedAt());
        assertNull(newAlert.getResolvedAt());
    }
} 