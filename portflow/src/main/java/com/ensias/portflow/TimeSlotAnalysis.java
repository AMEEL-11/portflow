package com.ensias.portflow;

class TimeSlotAnalysis {
    private String timeSlot;
    private int expectedContainers;
    private int availableCapacity;
    private RiskLevel riskLevel;
    
    // Getters and Setters
    public String getTimeSlot() { return timeSlot; }
    public void setTimeSlot(String timeSlot) { this.timeSlot = timeSlot; }
    public int getExpectedContainers() { return expectedContainers; }
    public void setExpectedContainers(int expectedContainers) { this.expectedContainers = expectedContainers; }
    public int getAvailableCapacity() { return availableCapacity; }
    public void setAvailableCapacity(int availableCapacity) { this.availableCapacity = availableCapacity; }
    public RiskLevel getRiskLevel() { return riskLevel; }
    public void setRiskLevel(RiskLevel riskLevel) { this.riskLevel = riskLevel; }
}