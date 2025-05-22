package com.springprojets.portflow;

import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;

@Entity
@Table(name = "berths")
public class Berth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Name is required")
    private String name;
    
    @NotNull(message = "Length is required")
    @Min(value = 1, message = "Length must be at least 1 meter")
    private Double length;
    
    @NotNull(message = "Width is required")
    @Min(value = 1, message = "Width must be at least 1 meter")
    private Double width;
    
    @NotNull(message = "Depth is required")
    @Min(value = 1, message = "Depth must be at least 1 meter")
    private Double depth;
    
    private String location;
    private Boolean isOperational;
    
    @OneToMany(mappedBy = "currentBerth")
    private List<Ship> ships;
    
    @OneToMany(mappedBy = "berth")
    private List<Escale> escales;

    // Constructors
    public Berth() {}

    public Berth(String name, Double length, Double width, Double depth) {
        this.name = name;
        this.length = length;
        this.width = width;
        this.depth = depth;
        this.isOperational = true;
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

    public Double getDepth() {
        return depth;
    }

    public void setDepth(Double depth) {
        this.depth = depth;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Boolean getIsOperational() {
        return isOperational;
    }

    public void setIsOperational(Boolean isOperational) {
        this.isOperational = isOperational;
    }

    public List<Ship> getShips() {
        return ships;
    }

    public void setShips(List<Ship> ships) {
        this.ships = ships;
    }

    public List<Escale> getEscales() {
        return escales;
    }

    public void setEscales(List<Escale> escales) {
        this.escales = escales;
    }

    // Business Logic
    public boolean canAccommodateShip(Ship ship) {
        return isOperational &&
               ship.getLength() <= this.length &&
               ship.getWidth() <= this.width &&
               ship.getDraft() <= this.depth;
    }
}