package com.springprojets.portflow;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LoginRequestTest {

    @Test
    void testLoginRequestConstructorAndGetters() {
        String email = "test@example.com";
        String password = "password123";
        
        LoginRequest loginRequest = new LoginRequest(email, password);
        
        assertEquals(email, loginRequest.getEmail());
        assertEquals(password, loginRequest.getPassword());
    }

    @Test
    void testLoginRequestSetters() {
        LoginRequest loginRequest = new LoginRequest();
        
        String email = "test@example.com";
        String password = "password123";
        
        loginRequest.setEmail(email);
        loginRequest.setPassword(password);
        
        assertEquals(email, loginRequest.getEmail());
        assertEquals(password, loginRequest.getPassword());
    }

    @Test
    void testDefaultConstructor() {
        LoginRequest loginRequest = new LoginRequest();
        
        assertNull(loginRequest.getEmail());
        assertNull(loginRequest.getPassword());
    }
} 