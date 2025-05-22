package com.ensias.portflow;

class ZoneCongestionAnalysis {
    private String zoneName;
    private ZoneType zoneType;
    private int currentOccupancy;
    private int totalCapacity;
    private int expectedIncoming;
    private RiskLevel riskLevel;
    private String recommendation;
    
    // Getters and Setters
    public String getZoneName() { return zoneName; }
    public void setZoneName(String zoneName) { this.zoneName = zoneName; }
    public ZoneType getZoneType() { return zoneType; }
    public void setZoneType(ZoneType zoneType) { this.zoneType = zoneType; }
    public int getCurrentOccupancy() { return currentOccupancy; }
    public void setCurrentOccupancy(int currentOccupancy) { this.currentOccupancy = currentOccupancy; }
    public int getTotalCapacity() { return totalCapacity; }
    public void setTotalCapacity(int totalCapacity) { this.totalCapacity = totalCapacity; }
    public int getExpectedIncoming() { return expectedIncoming; }
    public void setExpectedIncoming(int expectedIncoming) { this.expectedIncoming = expectedIncoming; }
    public RiskLevel getRiskLevel() { return riskLevel; }
    public void setRiskLevel(RiskLevel riskLevel) { this.riskLevel = riskLevel; }
    public String getRecommendation() { return recommendation; }
    public void setRecommendation(String recommendation) { this.recommendation = recommendation; }
}