package com.springprojets.portflow;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import java.util.List;

@Entity
@Table(name = "storage_zones")
public class StorageZone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Type is required")
    private String type; // e.g., "Container", "Bulk", "Liquid"

    @NotNull(message = "Total capacity is required")
    @Min(value = 1, message = "Total capacity must be at least 1")
    private Integer totalCapacity;

    @NotNull(message = "Current occupancy is required")
    @Min(value = 0, message = "Current occupancy cannot be negative")
    private Integer currentOccupancy;

    private Integer reservedSlots;

    @NotBlank(message = "Location is required")
    private String location;

    private String status;

    @OneToMany(mappedBy = "storageZone", cascade = CascadeType.ALL)
    private List<Container> containers;

    // Constructors
    public StorageZone() {}

    public StorageZone(String name, String type, Integer totalCapacity, String location) {
        this.name = name;
        this.type = type;
        this.totalCapacity = totalCapacity;
        this.currentOccupancy = 0;
        this.reservedSlots = 0;
        this.location = location;
        this.status = "ACTIVE";
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getTotalCapacity() {
        return totalCapacity;
    }

    public void setTotalCapacity(Integer totalCapacity) {
        this.totalCapacity = totalCapacity;
    }

    public Integer getCurrentOccupancy() {
        return currentOccupancy;
    }

    public void setCurrentOccupancy(Integer currentOccupancy) {
        this.currentOccupancy = currentOccupancy;
    }

    public Integer getReservedSlots() {
        return reservedSlots;
    }

    public void setReservedSlots(Integer reservedSlots) {
        this.reservedSlots = reservedSlots;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Container> getContainers() {
        return containers;
    }

    public void setContainers(List<Container> containers) {
        this.containers = containers;
    }

    // Business Logic
    public boolean hasAvailableCapacity() {
        return (currentOccupancy + reservedSlots) < totalCapacity;
    }

    public int getAvailableCapacity() {
        return totalCapacity - (currentOccupancy + reservedSlots);
    }

    public double getOccupancyRate() {
        return totalCapacity > 0 ? ((double) currentOccupancy / totalCapacity) * 100 : 0;
    }
}