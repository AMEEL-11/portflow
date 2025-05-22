package com.ensias.portflow;

import java.util.List;

class ClientDashboard {
    private int totalContainers;
    private int inTransitContainers;
    private int storedContainers;
    private int readyForPickup;
    private List<Container> recentContainers;
    
    // Getters and Setters
    public int getTotalContainers() { return totalContainers; }
    public void setTotalContainers(int totalContainers) { this.totalContainers = totalContainers; }
    public int getInTransitContainers() { return inTransitContainers; }
    public void setInTransitContainers(int inTransitContainers) { this.inTransitContainers = inTransitContainers; }
    public int getStoredContainers() { return storedContainers; }
    public void setStoredContainers(int storedContainers) { this.storedContainers = storedContainers; }
    public int getReadyForPickup() { return readyForPickup; }
    public void setReadyForPickup(int readyForPickup) { this.readyForPickup = readyForPickup; }
    public List<Container> getRecentContainers() { return recentContainers; }
    public void setRecentContainers(List<Container> recentContainers) { this.recentContainers = recentContainers; }
}
