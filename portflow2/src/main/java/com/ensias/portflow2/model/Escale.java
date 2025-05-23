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