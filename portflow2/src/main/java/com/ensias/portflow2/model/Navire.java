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