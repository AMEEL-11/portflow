package com.ensias.portflow;

import java.time.LocalDateTime;
import java.util.List;

class ContainerDetailsResponse {
    private Container container;
    private List<ContainerMovement> movements;
    private String currentLocation;
    private LocalDateTime estimatedDeparture;
    
    // Getters and Setters
    public Container getContainer() { return container; }
    public void setContainer(Container container) { this.container = container; }
    public List<ContainerMovement> getMovements() { return movements; }
    public void setMovements(List<ContainerMovement> movements) { this.movements = movements; }
    public String getCurrentLocation() { return currentLocation; }
    public void setCurrentLocation(String currentLocation) { this.currentLocation = currentLocation; }
    public LocalDateTime getEstimatedDeparture() { return estimatedDeparture; }
    public void setEstimatedDeparture(LocalDateTime estimatedDeparture) { this.estimatedDeparture = estimatedDeparture; }
}