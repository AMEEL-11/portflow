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