package com.springprojets.portflow;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "ships")
public class Ship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Name is required")
    private String name;
    
    @NotBlank(message = "IMO number is required")
    private String imoNumber;
    
    private String flag;
    private Double length;
    private Double width;
    private Double draft;
    
    // Données AIS/GPS simulées
    private Double latitude;
    private Double longitude;
    private Double speed;
    private LocalDateTime lastLocationUpdate;
    
    @NotNull(message = "Status is required")
    @Enumerated(EnumType.STRING)
    private ShipStatus status;
    
    @ManyToOne
    @JoinColumn(name = "current_berth_id")
    private Berth currentBerth;
    
    // Relations
    @OneToMany(mappedBy = "ship", cascade = CascadeType.ALL)
    private List<Escale> escales;

    // Constructors
    public Ship() {}

    public Ship(String name, String imoNumber, String flag) {
        this.name = name;
        this.imoNumber = imoNumber;
        this.flag = flag;
        this.status = ShipStatus.EN_ROUTE;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImoNumber() {
        return imoNumber;
    }

    public void setImoNumber(String imoNumber) {
        this.imoNumber = imoNumber;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getDraft() {
        return draft;
    }

    public void setDraft(Double draft) {
        this.draft = draft;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public LocalDateTime getLastLocationUpdate() {
        return lastLocationUpdate;
    }

    public void setLastLocationUpdate(LocalDateTime lastLocationUpdate) {
        this.lastLocationUpdate = lastLocationUpdate;
    }

    public ShipStatus getStatus() {
        return status;
    }

    public void setStatus(ShipStatus status) {
        this.status = status;
    }

    public Berth getCurrentBerth() {
        return currentBerth;
    }

    public void setCurrentBerth(Berth currentBerth) {
        this.currentBerth = currentBerth;
    }

    public List<Escale> getEscales() {
        return escales;
    }

    public void setEscales(List<Escale> escales) {
        this.escales = escales;
    }
}
