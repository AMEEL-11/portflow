package com.ensias.portflow;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {
    
    @Autowired
    private AuthService authService;
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            User user = authService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
            
            LoginResponse response = new LoginResponse();
            response.setUser(user);
            response.setToken(generateToken(user)); // JWT token generation
            response.setMessage("Login successful");
            
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse("Invalid credentials"));
        }
    }
    
    private String generateToken(User user) {
        // JWT token generation logic
        return "jwt-token-" + user.getId() + "-" + System.currentTimeMillis();
    }
}