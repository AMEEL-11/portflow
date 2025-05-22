package com.springprojets.portflow;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "escales")
public class Escale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "Ship is required")
    @ManyToOne
    @JoinColumn(name = "ship_id")
    private Ship ship;
    
    @NotNull(message = "Berth is required")
    @ManyToOne
    @JoinColumn(name = "berth_id")
    private Berth berth;
    
    @NotNull(message = "Estimated arrival time is required")
    private LocalDateTime estimatedArrivalTime;
    private LocalDateTime actualArrivalTime;
    private LocalDateTime estimatedDepartureTime;
    private LocalDateTime actualDepartureTime;
    
    @NotNull(message = "Status is required")
    @Enumerated(EnumType.STRING)
    private EscaleStatus status;
    
    private String purpose; // e.g., "Loading", "Unloading", "Both"
    private String notes;
    
    @OneToMany(mappedBy = "escale", cascade = CascadeType.ALL)
    private List<Container> containers;

    // Constructors
    public Escale() {}

    public Escale(Ship ship, Berth berth, LocalDateTime estimatedArrivalTime) {
        this.ship = ship;
        this.berth = berth;
        this.estimatedArrivalTime = estimatedArrivalTime;
        this.status = EscaleStatus.SCHEDULED;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public Berth getBerth() {
        return berth;
    }

    public void setBerth(Berth berth) {
        this.berth = berth;
    }

    public LocalDateTime getEstimatedArrivalTime() {
        return estimatedArrivalTime;
    }

    public void setEstimatedArrivalTime(LocalDateTime estimatedArrivalTime) {
        this.estimatedArrivalTime = estimatedArrivalTime;
    }

    public LocalDateTime getActualArrivalTime() {
        return actualArrivalTime;
    }

    public void setActualArrivalTime(LocalDateTime actualArrivalTime) {
        this.actualArrivalTime = actualArrivalTime;
    }

    public LocalDateTime getEstimatedDepartureTime() {
        return estimatedDepartureTime;
    }

    public void setEstimatedDepartureTime(LocalDateTime estimatedDepartureTime) {
        this.estimatedDepartureTime = estimatedDepartureTime;
    }

    public LocalDateTime getActualDepartureTime() {
        return actualDepartureTime;
    }

    public void setActualDepartureTime(LocalDateTime actualDepartureTime) {
        this.actualDepartureTime = actualDepartureTime;
    }

    public EscaleStatus getStatus() {
        return status;
    }

    public void setStatus(EscaleStatus status) {
        this.status = status;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<Container> getContainers() {
        return containers;
    }

    public void setContainers(List<Container> containers) {
        this.containers = containers;
    }
}
