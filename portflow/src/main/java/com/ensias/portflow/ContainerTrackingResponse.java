package com.ensias.portflow;

import java.time.LocalDateTime;
import java.util.List;

class ContainerTrackingResponse {
    private Container container;
    private String currentStatus;
    private String currentLocation;
    private List<ContainerMovement> movementHistory;
    private LocalDateTime estimatedDeparture;
    private int progressPercentage;
    
    // Getters and Setters
    public Container getContainer() { return container; }
    public void setContainer(Container container) { this.container = container; }
    public String getCurrentStatus() { return currentStatus; }
    public void setCurrentStatus(String currentStatus) { this.currentStatus = currentStatus; }
    public String getCurrentLocation() { return currentLocation; }
    public void setCurrentLocation(String currentLocation) { this.currentLocation = currentLocation; }
    public List<ContainerMovement> getMovementHistory() { return movementHistory; }
    public void setMovementHistory(List<ContainerMovement> movementHistory) { this.movementHistory = movementHistory; }
    public LocalDateTime getEstimatedDeparture() { return estimatedDeparture; }
    public void setEstimatedDeparture(LocalDateTime estimatedDeparture) { this.estimatedDeparture = estimatedDeparture; }
    public int getProgressPercentage() { return progressPercentage; }
    public void setProgressPercentage(int progressPercentage) { this.progressPercentage = progressPercentage; }
}