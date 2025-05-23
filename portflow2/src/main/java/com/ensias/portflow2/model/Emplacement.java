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