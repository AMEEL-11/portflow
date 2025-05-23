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