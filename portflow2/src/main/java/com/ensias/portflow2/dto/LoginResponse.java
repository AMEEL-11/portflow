package com.ensias.portflow2.dto;

import com.ensias.portflow2.model.Role;

public class LoginResponse {
    private Long userId;
    private String email;
    private String firstName;
    private String lastName;
    private Role role;
    private String message;
    
    // Constructeurs
    public LoginResponse() {}
    
    public LoginResponse(Long userId, String email, String firstName, String lastName, Role role, String message) {
        this.userId = userId;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.message = message;
    }
    
    // Getters et Setters
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}