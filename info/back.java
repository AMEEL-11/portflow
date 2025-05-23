// PortFlowApplication.java
package com.ensias.portflow2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PortFlowApplication {
    public static void main(String[] args) {
        SpringApplication.run(PortFlowApplication.class, args);
    }
}

// CorsConfig.java
package com.ensias.portflow2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:4200");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", configuration);
        return source;
    }
}

// SecurityConfig.java
package com.ensias.portflow2.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:4200");
        configuration.addAllowedOrigin("http://localhost:3000");
        configuration.addAllowedOrigin("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(false);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/").permitAll()
                .requestMatchers("/index.html").permitAll()
                .requestMatchers("/static/**").permitAll()
                .anyRequest().permitAll() // Temporarily allow all for testing
            );
        
        return http.build();
    }
}
// AdminController.java
package com.ensias.portflow2.controller;

import com.ensias.portflow2.dto.UserDto;
import com.ensias.portflow2.model.Role;
import com.ensias.portflow2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
    
    @PostMapping("/users")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        try {
            UserDto createdUser = userService.createUser(userDto);
            return ResponseEntity.ok(createdUser);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/users/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @Valid @RequestBody UserDto userDto) {
        try {
            UserDto updatedUser = userService.updateUser(id, userDto);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/users/role/{role}")
    public ResponseEntity<List<UserDto>> getUsersByRole(@PathVariable Role role) {
        return ResponseEntity.ok(userService.getUsersByRole(role));
    }
    
    @GetMapping("/roles")
    public ResponseEntity<Role[]> getAllRoles() {
        return ResponseEntity.ok(Role.values());
    }
}
// AuthController.java
package com.ensias.portflow2.controller;

import com.ensias.portflow2.dto.LoginRequest;
import com.ensias.portflow2.dto.LoginResponse;
import com.ensias.portflow2.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {
    
    @Autowired
    private AuthService authService;
    
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        LoginResponse response = authService.authenticate(loginRequest);
        
        if (response.getUserId() != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        return ResponseEntity.ok("Déconnexion réussie");
    }
}
// ConteneurController.java
package com.ensias.portflow2.controller;

import com.ensias.portflow2.model.Conteneur;
import com.ensias.portflow2.service.ConteneurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/conteneurs")
@CrossOrigin(origins = "*")
public class ConteneurController {
    
    @Autowired
    private ConteneurService conteneurService;
    
    @GetMapping
    public ResponseEntity<List<Conteneur>> getAllConteneurs() {
        return ResponseEntity.ok(conteneurService.getAllConteneurs());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Conteneur> getConteneurById(@PathVariable Long id) {
        Conteneur conteneur = conteneurService.getConteneurById(id);
        return conteneur != null ? ResponseEntity.ok(conteneur) : ResponseEntity.notFound().build();
    }
    
    @GetMapping("/statut/{statut}")
    public ResponseEntity<List<Conteneur>> getConteneursByStatut(@PathVariable String statut) {
        return ResponseEntity.ok(conteneurService.getConteneursByStatut(statut));
    }
    
    @GetMapping("/count/stockes")
    public ResponseEntity<Long> countConteneursStockes() {
        return ResponseEntity.ok(conteneurService.countConteneursStockes());
    }
    
    @PostMapping
    public ResponseEntity<Conteneur> createConteneur(@RequestBody Conteneur conteneur) {
        return ResponseEntity.ok(conteneurService.saveConteneur(conteneur));
    }
}
// HomeController.java
package com.ensias.portflow2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    
    @GetMapping("/")
    public String home() {
        return "forward:/index.html";
    }
    
    @GetMapping("/dashboard")
    public String dashboard() {
        return "forward:/index.html";
    }
}
// NavireController.java
package com.ensias.portflow2.controller;

import com.ensias.portflow2.model.Navire;
import com.ensias.portflow2.service.NavireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/navires")
@CrossOrigin(origins = "*")
public class NavireController {
    
    @Autowired
    private NavireService navireService;
    
    @GetMapping
    public ResponseEntity<List<Navire>> getAllNavires() {
        return ResponseEntity.ok(navireService.getAllNavires());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Navire> getNavireById(@PathVariable Long id) {
        Navire navire = navireService.getNavireById(id);
        return navire != null ? ResponseEntity.ok(navire) : ResponseEntity.notFound().build();
    }
    
    @GetMapping("/a-venir")
    public ResponseEntity<List<Navire>> getNaviresAVenir() {
        return ResponseEntity.ok(navireService.getNaviresAVenir());
    }
    
    @GetMapping("/en-approche")
    public ResponseEntity<List<Navire>> getNaviresEnApproche() {
        return ResponseEntity.ok(navireService.getNaviresEnApproche());
    }
    
    @PostMapping
    public ResponseEntity<Navire> createNavire(@RequestBody Navire navire) {
        return ResponseEntity.ok(navireService.saveNavire(navire));
    }
}
// LoginRequest.java
package com.ensias.portflow2.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginRequest {
    @Email
    @NotBlank
    private String email;
    
    @NotBlank
    private String password;
    
    // Constructeurs, getters, setters
    public LoginRequest() {}
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
// LoginResponse.java
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
// UserDto.java
package com.ensias.portflow2.dto;

import com.ensias.portflow2.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserDto {
    private Long id;
    
    @Email
    @NotBlank
    private String email;
    
    private String password;
    
    @NotBlank
    private String firstName;
    
    @NotBlank
    private String lastName;
    
    private Role role;
    private boolean active = true;
    
    // Constructeurs, getters, setters
    public UserDto() {}
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
    
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}
// Alerte.java
package com.ensias.portflow2.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "alertes")
public class Alerte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String type; // EQUIPEMENT, STOCKAGE, NAVIRE, CONTENEUR
    private String niveau; // INFO, ATTENTION, CRITIQUE
    private String titre;
    private String description;
    private String statut; // ACTIVE, RESOLUE, IGNOREE
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User destinataire;
    
    private LocalDateTime dateCreation;
    private LocalDateTime dateResolution;
    private boolean lue = false;
    
    // Constructeur
    public Alerte() {
        this.dateCreation = LocalDateTime.now();
    }
    
    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public String getNiveau() { return niveau; }
    public void setNiveau(String niveau) { this.niveau = niveau; }
    
    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
    
    public User getDestinataire() { return destinataire; }
    public void setDestinataire(User destinataire) { this.destinataire = destinataire; }
    
    public LocalDateTime getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDateTime dateCreation) { this.dateCreation = dateCreation; }
    
    public LocalDateTime getDateResolution() { return dateResolution; }
    public void setDateResolution(LocalDateTime dateResolution) { this.dateResolution = dateResolution; }
    
    public boolean isLue() { return lue; }
    public void setLue(boolean lue) { this.lue = lue; }
}
// Conteneur.java
package com.ensias.portflow2.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "conteneurs")
public class Conteneur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String numeroConteneur;
    
    private String type; // 20FT, 40FT, 40HC
    private String statut; // VIDE, PLEIN, EN_TRANSIT, STOCKE
    private Double poids;
    private String contenu;
    
    @ManyToOne
    @JoinColumn(name = "client_id")
    private User client;
    
    @ManyToOne
    @JoinColumn(name = "navire_id")
    private Navire navire;
    
    @ManyToOne
    @JoinColumn(name = "emplacement_id")
    private Emplacement emplacement;
    
    // Données RFID simulées
    private String rfidTag;
    private LocalDateTime derniereScanRfid;
    
    private LocalDateTime dateArrivee;
    private LocalDateTime dateDepartPrevue;
    private LocalDateTime dateDepartReelle;
    
    // Constructeurs, getters, setters
    public Conteneur() {}
    
    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNumeroConteneur() { return numeroConteneur; }
    public void setNumeroConteneur(String numeroConteneur) { this.numeroConteneur = numeroConteneur; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
    
    public Double getPoids() { return poids; }
    public void setPoids(Double poids) { this.poids = poids; }
    
    public String getContenu() { return contenu; }
    public void setContenu(String contenu) { this.contenu = contenu; }
    
    public User getClient() { return client; }
    public void setClient(User client) { this.client = client; }
    
    public Navire getNavire() { return navire; }
    public void setNavire(Navire navire) { this.navire = navire; }
    
    public Emplacement getEmplacement() { return emplacement; }
    public void setEmplacement(Emplacement emplacement) { this.emplacement = emplacement; }
    
    public String getRfidTag() { return rfidTag; }
    public void setRfidTag(String rfidTag) { this.rfidTag = rfidTag; }
    
    public LocalDateTime getDerniereScanRfid() { return derniereScanRfid; }
    public void setDerniereScanRfid(LocalDateTime derniereScanRfid) { this.derniereScanRfid = derniereScanRfid; }
    
    public LocalDateTime getDateArrivee() { return dateArrivee; }
    public void setDateArrivee(LocalDateTime dateArrivee) { this.dateArrivee = dateArrivee; }
    
    public LocalDateTime getDateDepartPrevue() { return dateDepartPrevue; }
    public void setDateDepartPrevue(LocalDateTime dateDepartPrevue) { this.dateDepartPrevue = dateDepartPrevue; }
    
    public LocalDateTime getDateDepartReelle() { return dateDepartReelle; }
    public void setDateDepartReelle(LocalDateTime dateDepartReelle) { this.dateDepartReelle = dateDepartReelle; }
}
// Emplacement.java
package com.ensias.portflow2.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "emplacements")
public class Emplacement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String reference; // A1-01, B2-15, etc.
    private String statut; // LIBRE, RESERVE, OCCUPE
    private Integer rangee;
    private Integer position;
    private Integer niveau; // pour l'empilage
    
    @ManyToOne
    @JoinColumn(name = "zone_stockage_id")
    private ZoneStockage zoneStockage;
    
    @OneToMany(mappedBy = "emplacement")
    private List<Conteneur> conteneurs;
    
    // Constructeurs, getters, setters
    public Emplacement() {}
    
    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference; }
    
    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
    
    public Integer getRangee() { return rangee; }
    public void setRangee(Integer rangee) { this.rangee = rangee; }
    
    public Integer getPosition() { return position; }
    public void setPosition(Integer position) { this.position = position; }
    
    public Integer getNiveau() { return niveau; }
    public void setNiveau(Integer niveau) { this.niveau = niveau; }
    
    public ZoneStockage getZoneStockage() { return zoneStockage; }
    public void setZoneStockage(ZoneStockage zoneStockage) { this.zoneStockage = zoneStockage; }
    
    public List<Conteneur> getConteneurs() { return conteneurs; }
    public void setConteneurs(List<Conteneur> conteneurs) { this.conteneurs = conteneurs; }
}
// Escale.java
package com.ensias.portflow2.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "escales")
public class Escale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "navire_id")
    private Navire navire;
    
    private String numeroEscale;
    private String statut; // PLANIFIE, EN_COURS, TERMINE, ANNULE
    private LocalDateTime dateArrivee;
    private LocalDateTime dateDepart;
    private String quai;
    private String typeOperation; // CHARGEMENT, DECHARGEMENT, MIXTE
    
    // Constructeurs, getters, setters
    public Escale() {}
    
    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Navire getNavire() { return navire; }
    public void setNavire(Navire navire) { this.navire = navire; }
    
    public String getNumeroEscale() { return numeroEscale; }
    public void setNumeroEscale(String numeroEscale) { this.numeroEscale = numeroEscale; }
    
    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
    
    public LocalDateTime getDateArrivee() { return dateArrivee; }
    public void setDateArrivee(LocalDateTime dateArrivee) { this.dateArrivee = dateArrivee; }
    
    public LocalDateTime getDateDepart() { return dateDepart; }
    public void setDateDepart(LocalDateTime dateDepart) { this.dateDepart = dateDepart; }
    
    public String getQuai() { return quai; }
    public void setQuai(String quai) { this.quai = quai; }
    
    public String getTypeOperation() { return typeOperation; }
    public void setTypeOperation(String typeOperation) { this.typeOperation = typeOperation; }
}
// Navire.java
package com.ensias.portflow2.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "navires")
public class Navire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nom;
    private String imo;
    private String mmsi;
    private String type;
    private Double longueur;
    private Double largeur;
    private Integer capaciteConteneurs;
    
    // Données AIS simulées
    private Double latitude;
    private Double longitude;
    private Double vitesse;
    private String statut; // EN_ROUTE, APPROCHE, ACCOSTAGE, DEPART
    
    private LocalDateTime etaPrevu;
    private LocalDateTime etaReel;
    private LocalDateTime departPrevu;
    private LocalDateTime departReel;
    
    @OneToMany(mappedBy = "navire")
    private List<Escale> escales;
    
    // Constructeurs, getters, setters
    public Navire() {}
    
    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    
    public String getImo() { return imo; }
    public void setImo(String imo) { this.imo = imo; }
    
    public String getMmsi() { return mmsi; }
    public void setMmsi(String mmsi) { this.mmsi = mmsi; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public Double getLongueur() { return longueur; }
    public void setLongueur(Double longueur) { this.longueur = longueur; }
    
    public Double getLargeur() { return largeur; }
    public void setLargeur(Double largeur) { this.largeur = largeur; }
    
    public Integer getCapaciteConteneurs() { return capaciteConteneurs; }
    public void setCapaciteConteneurs(Integer capaciteConteneurs) { this.capaciteConteneurs = capaciteConteneurs; }
    
    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }
    
    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }
    
    public Double getVitesse() { return vitesse; }
    public void setVitesse(Double vitesse) { this.vitesse = vitesse; }
    
    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
    
    public LocalDateTime getEtaPrevu() { return etaPrevu; }
    public void setEtaPrevu(LocalDateTime etaPrevu) { this.etaPrevu = etaPrevu; }
    
    public LocalDateTime getEtaReel() { return etaReel; }
    public void setEtaReel(LocalDateTime etaReel) { this.etaReel = etaReel; }
    
    public LocalDateTime getDepartPrevu() { return departPrevu; }
    public void setDepartPrevu(LocalDateTime departPrevu) { this.departPrevu = departPrevu; }
    
    public LocalDateTime getDepartReel() { return departReel; }
    public void setDepartReel(LocalDateTime departReel) { this.departReel = departReel; }
    
    public List<Escale> getEscales() { return escales; }
    public void setEscales(List<Escale> escales) { this.escales = escales; }
}
// Role.java
package com.ensias.portflow2.model;

public enum Role {
    ADMIN,
    BERTH_PLANNER,
    YARD_PLANNER,
    OPERATIONS_MANAGER,
    CLIENT,
    DOCUMENTATION_SERVICE
}
// User.java
package com.ensias.portflow2.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    @Column(nullable = false)
    private String password;
    
    @Enumerated(EnumType.STRING)
    private Role role;
    
    private String firstName;
    private String lastName;
    private boolean active = true;
    private LocalDateTime createdAt;
    
    // Constructeurs, getters, setters
    public User() {
        this.createdAt = LocalDateTime.now();
    }
    
    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
    
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
// ZoneStockage.java
package com.ensias.portflow2.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "zones_stockage")
public class ZoneStockage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nom;
    private String type; // CONTENEURS_20FT, CONTENEURS_40FT, FRIGORIFIQUE
    private Integer capaciteMax;
    private Double coordonneeX;
    private Double coordonneeY;
    private Double superficie;
    
    @OneToMany(mappedBy = "zoneStockage", cascade = CascadeType.ALL)
    private List<Emplacement> emplacements;
    
    // Constructeurs, getters, setters
    public ZoneStockage() {}
    
    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public Integer getCapaciteMax() { return capaciteMax; }
    public void setCapaciteMax(Integer capaciteMax) { this.capaciteMax = capaciteMax; }
    
    public Double getCoordonneeX() { return coordonneeX; }
    public void setCoordonneeX(Double coordonneeX) { this.coordonneeX = coordonneeX; }
    
    public Double getCoordonneeY() { return coordonneeY; }
    public void setCoordonneeY(Double coordonneeY) { this.coordonneeY = coordonneeY; }
    
    public Double getSuperficie() { return superficie; }
    public void setSuperficie(Double superficie) { this.superficie = superficie; }
    
    public List<Emplacement> getEmplacements() { return emplacements; }
    public void setEmplacements(List<Emplacement> emplacements) { this.emplacements = emplacements; }
}
// AlerteRepository.java
package com.ensias.portflow2.repository;

import com.ensias.portflow2.model.Alerte;
import com.ensias.portflow2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AlerteRepository extends JpaRepository<Alerte, Long> {
    List<Alerte> findByDestinataireAndStatut(User destinataire, String statut);
    List<Alerte> findByDestinataireAndLueFalse(User destinataire);
    List<Alerte> findByTypeAndStatut(String type, String statut);
    
    @Query("SELECT a FROM Alerte a WHERE a.destinataire = ?1 ORDER BY a.dateCreation DESC")
    List<Alerte> findByDestinataireOrderByDateCreationDesc(User destinataire);
    
    @Query("SELECT COUNT(a) FROM Alerte a WHERE a.destinataire = ?1 AND a.lue = false")
    Long countUnreadAlertes(User destinataire);
}
// ConteneurRepository.java
package com.ensias.portflow2.repository;

import com.ensias.portflow2.model.Conteneur;
import com.ensias.portflow2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ConteneurRepository extends JpaRepository<Conteneur, Long> {
    Optional<Conteneur> findByNumeroConteneur(String numeroConteneur);
    List<Conteneur> findByClient(User client);
    List<Conteneur> findByStatut(String statut);
    List<Conteneur> findByNavireId(Long navireId);
    
    @Query("SELECT c FROM Conteneur c WHERE c.emplacement IS NULL AND c.statut = 'STOCKE'")
    List<Conteneur> findConteneursNonAssignes();
    
    @Query("SELECT COUNT(c) FROM Conteneur c WHERE c.statut = 'STOCKE'")
    Long countConteneursStockes();
}
// EmplacementRepository.java
package com.ensias.portflow2.repository;

import com.ensias.portflow2.model.Emplacement;
import com.ensias.portflow2.model.ZoneStockage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EmplacementRepository extends JpaRepository<Emplacement, Long> {
    List<Emplacement> findByZoneStockage(ZoneStockage zoneStockage);
    List<Emplacement> findByStatut(String statut);
    List<Emplacement> findByZoneStockageAndStatut(ZoneStockage zoneStockage, String statut);
    
    @Query("SELECT e FROM Emplacement e WHERE e.statut = 'LIBRE' ORDER BY e.zoneStockage.id, e.rangee, e.position")
    List<Emplacement> findEmplacementsLibresOptimaux();
    
    @Query("SELECT COUNT(e) FROM Emplacement e WHERE e.statut = ?1")
    Long countByStatut(String statut);
}

// EscaleRepository.java
package com.ensias.portflow2.repository;

import com.ensias.portflow2.model.Escale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EscaleRepository extends JpaRepository<Escale, Long> {
    List<Escale> findByStatut(String statut);
    List<Escale> findByDateArriveeBetween(LocalDateTime debut, LocalDateTime fin);
    List<Escale> findByNavireId(Long navireId);

    @Query("SELECT e FROM Escale e WHERE e.dateArrivee >= CURRENT_TIMESTAMP ORDER BY e.dateArrivee")
    List<Escale> findEscalesPlanifiees();

    @Query("SELECT e FROM Escale e WHERE e.statut = 'EN_COURS'")
    List<Escale> findEscalesEnCours();

    @Query("SELECT e FROM Escale e WHERE e.dateDepart <= CURRENT_TIMESTAMP AND e.statut = 'TERMINE'")
    List<Escale> findEscalesPassees();

    @Query("SELECT COUNT(e) FROM Escale e WHERE e.statut = 'EN_COURS'")
    Long countEscalesEnCours();
}

// NavireRepository.java
package com.ensias.portflow2.repository;

import com.ensias.portflow2.model.Navire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NavireRepository extends JpaRepository<Navire, Long> {
    List<Navire> findByStatut(String statut);
    List<Navire> findByEtaPrevuBetween(LocalDateTime debut, LocalDateTime fin);
    
    @Query("SELECT n FROM Navire n WHERE n.etaPrevu >= CURRENT_TIMESTAMP ORDER BY n.etaPrevu")
    List<Navire> findNaviresAVenir();
    
    @Query("SELECT n FROM Navire n WHERE n.statut IN ('APPROCHE', 'ACCOSTAGE')")
    List<Navire> findNaviresEnApproche();
}
// UserRepository.java
package com.ensias.portflow2.repository;

import com.ensias.portflow2.model.User;
import com.ensias.portflow2.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    List<User> findByRole(Role role);
    List<User> findByActiveTrue();
    boolean existsByEmail(String email);
}
// ZoneStockageRepository.java
package com.ensias.portflow2.repository;

import com.ensias.portflow2.model.ZoneStockage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ZoneStockageRepository extends JpaRepository<ZoneStockage, Long> {
    List<ZoneStockage> findByType(String type);
    
    @Query("SELECT z FROM ZoneStockage z WHERE SIZE(z.emplacements) < z.capaciteMax")
    List<ZoneStockage> findZonesDisponibles();
    
    @Query("SELECT z, COUNT(e) FROM ZoneStockage z LEFT JOIN z.emplacements e WHERE e.statut = 'OCCUPE' GROUP BY z")
    List<Object[]> getTauxOccupationZones();
}
// AuthService.java
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
// ConteneurService.java
package com.ensias.portflow2.service;

import com.ensias.portflow2.model.Conteneur;
import com.ensias.portflow2.repository.ConteneurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConteneurService {
    
    @Autowired
    private ConteneurRepository conteneurRepository;
    
    public List<Conteneur> getAllConteneurs() {
        return conteneurRepository.findAll();
    }
    
    public Conteneur getConteneurById(Long id) {
        return conteneurRepository.findById(id).orElse(null);
    }
    
    public Conteneur saveConteneur(Conteneur conteneur) {
        return conteneurRepository.save(conteneur);
    }
    
    public List<Conteneur> getConteneursByStatut(String statut) {
        return conteneurRepository.findByStatut(statut);
    }
    
    public Long countConteneursStockes() {
        return conteneurRepository.countConteneursStockes();
    }
}
// NavireService.java
package com.ensias.portflow2.service;

import com.ensias.portflow2.model.Navire;
import com.ensias.portflow2.repository.NavireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NavireService {
    
    @Autowired
    private NavireRepository navireRepository;
    
    public List<Navire> getAllNavires() {
        return navireRepository.findAll();
    }
    
    public Navire getNavireById(Long id) {
        return navireRepository.findById(id).orElse(null);
    }
    
    public Navire saveNavire(Navire navire) {
        return navireRepository.save(navire);
    }
    
    public List<Navire> getNaviresAVenir() {
        return navireRepository.findNaviresAVenir();
    }
    
    public List<Navire> getNaviresEnApproche() {
        return navireRepository.findNaviresEnApproche();
    }
}
// UserService.java
package com.ensias.portflow2.service;

import com.ensias.portflow2.dto.UserDto;
import com.ensias.portflow2.model.User;
import com.ensias.portflow2.model.Role;
import com.ensias.portflow2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }
    
    public UserDto createUser(UserDto userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new RuntimeException("Un utilisateur avec cet email existe déjà");
        }
        
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setRole(userDto.getRole());
        user.setActive(userDto.isActive());
        
        User savedUser = userRepository.save(user);
        return convertToDto(savedUser);
    }
    
    public UserDto updateUser(Long id, UserDto userDto) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
        
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setRole(userDto.getRole());
        user.setActive(userDto.isActive());
        
        if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }
        
        User savedUser = userRepository.save(user);
        return convertToDto(savedUser);
    }
    
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("Utilisateur introuvable");
        }
        userRepository.deleteById(id);
    }
    
    public List<UserDto> getUsersByRole(Role role) {
        return userRepository.findByRole(role).stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }
    
    private UserDto convertToDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setRole(user.getRole());
        dto.setActive(user.isActive());
        return dto;
    }
}
