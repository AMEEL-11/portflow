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