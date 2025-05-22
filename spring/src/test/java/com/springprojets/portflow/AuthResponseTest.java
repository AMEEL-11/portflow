package com.springprojets.portflow;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AuthResponseTest {

    @Test
    void testAuthResponseConstructorAndGetters() {
        String token = "jwt.token.string";
        String email = "test@example.com";
        UserRole role = UserRole.CLIENT;
        
        AuthResponse authResponse = new AuthResponse(token, email, role);
        
        assertEquals(token, authResponse.getToken());
        assertEquals(email, authResponse.getEmail());
        assertEquals(role, authResponse.getRole());
    }

    @Test
    void testAuthResponseSetters() {
        AuthResponse authResponse = new AuthResponse();
        
        String token = "jwt.token.string";
        String email = "test@example.com";
        UserRole role = UserRole.CLIENT;
        
        authResponse.setToken(token);
        authResponse.setEmail(email);
        authResponse.setRole(role);
        
        assertEquals(token, authResponse.getToken());
        assertEquals(email, authResponse.getEmail());
        assertEquals(role, authResponse.getRole());
    }

    @Test
    void testDefaultConstructor() {
        AuthResponse authResponse = new AuthResponse();
        
        assertNull(authResponse.getToken());
        assertNull(authResponse.getEmail());
        assertNull(authResponse.getRole());
    }
} 