package com.springprojets.portflow;

public class ZoneOccupancyDto {
    private String zoneName;
    private int currentOccupancy;
    private int totalCapacity;
    private int reservedSlots;
    private double occupancyRate;

    // Constructors
    public ZoneOccupancyDto() {}

    public ZoneOccupancyDto(String zoneName, int currentOccupancy, int totalCapacity, int reservedSlots) {
        this.zoneName = zoneName;
        this.currentOccupancy = currentOccupancy;
        this.totalCapacity = totalCapacity;
        this.reservedSlots = reservedSlots;
        this.occupancyRate = totalCapacity > 0 ? (double) currentOccupancy / totalCapacity * 100 : 0;
    }

    // Getters and Setters
    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public int getCurrentOccupancy() {
        return currentOccupancy;
    }

    public void setCurrentOccupancy(int currentOccupancy) {
        this.currentOccupancy = currentOccupancy;
        calculateOccupancyRate();
    }

    public int getTotalCapacity() {
        return totalCapacity;
    }

    public void setTotalCapacity(int totalCapacity) {
        this.totalCapacity = totalCapacity;
        calculateOccupancyRate();
    }

    public int getReservedSlots() {
        return reservedSlots;
    }

    public void setReservedSlots(int reservedSlots) {
        this.reservedSlots = reservedSlots;
    }

    public double getOccupancyRate() {
        return occupancyRate;
    }

    private void calculateOccupancyRate() {
        this.occupancyRate = totalCapacity > 0 ? (double) currentOccupancy / totalCapacity * 100 : 0;
    }
} 