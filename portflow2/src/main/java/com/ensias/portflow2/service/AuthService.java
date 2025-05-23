package com.ensias.portflow2.service;

import com.ensias.portflow2.dto.LoginRequest;
import com.ensias.portflow2.dto.LoginResponse;
import com.ensias.portflow2.model.User;
import com.ensias.portflow2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public LoginResponse authenticate(LoginRequest loginRequest) {
        try {
            User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Email ou mot de passe incorrect"));
            
            if (!user.isActive()) {
                throw new RuntimeException("Compte désactivé");
            }
            
            if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                throw new RuntimeException("Email ou mot de passe incorrect");
            }
            
            return new LoginResponse(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getRole(),
                "Connexion réussie"
            );
            
        } catch (Exception e) {
            return new LoginResponse(null, null, null, null, null, e.getMessage());
        }
    }
}