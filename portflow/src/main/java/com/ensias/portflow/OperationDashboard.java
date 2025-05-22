package com.ensias.portflow;

import java.util.List;

class OperationsDashboard {
    private long totalContainers;
    private long activeShips;
    private long pendingOperations;
    private double storageOccupancy;
    private List<ContainerMovement> recentMovements;
    private List<Container> upcomingDepartures;
    private List<Alert> unacknowledgedAlerts;
    
    // Getters and Setters
    public long getTotalContainers() { return totalContainers; }
    public void setTotalContainers(long totalContainers) { this.totalContainers = totalContainers; }
    public long getActiveShips() { return activeShips; }
    public void setActiveShips(long activeShips) { this.activeShips = activeShips; }
    public long getPendingOperations() { return pendingOperations; }
    public void setPendingOperations(long pendingOperations) { this.pendingOperations = pendingOperations; }
    public double getStorageOccupancy() { return storageOccupancy; }
    public void setStorageOccupancy(double storageOccupancy) { this.storageOccupancy = storageOccupancy; }
    public List<ContainerMovement> getRecentMovements() { return recentMovements; }
    public void setRecentMovements(List<ContainerMovement> recentMovements) { this.recentMovements = recentMovements; }
    public List<Container> getUpcomingDepartures() { return upcomingDepartures; }
    public void setUpcomingDepartures(List<Container> upcomingDepartures) { this.upcomingDepartures = upcomingDepartures; }
    public List<Alert> getUnacknowledgedAlerts() { return unacknowledgedAlerts; }
    public void setUnacknowledgedAlerts(List<Alert> unacknowledgedAlerts) { this.unacknowledgedAlerts = unacknowledgedAlerts; }
}