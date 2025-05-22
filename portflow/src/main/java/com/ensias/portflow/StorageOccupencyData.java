package com.ensias.portflow;

import java.time.LocalDate;

class StorageOccupancyData {
    private LocalDate date;
    private double occupancyRate;
    private int totalContainers;
    private int availableSpots;
    
    // Getters and Setters
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public double getOccupancyRate() { return occupancyRate; }
    public void setOccupancyRate(double occupancyRate) { this.occupancyRate = occupancyRate; }
    public int getTotalContainers() { return totalContainers; }
    public void setTotalContainers(int totalContainers) { this.totalContainers = totalContainers; }
    public int getAvailableSpots() { return availableSpots; }
    public void setAvailableSpots(int availableSpots) { this.availableSpots = availableSpots; }
}