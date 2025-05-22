package com.ensias.portflow;

import java.time.LocalDate;
import java.util.List;

class CongestionPrediction {
    private LocalDate predictionDate;
    private int daysAhead;
    private RiskLevel riskLevel;
    private String message;
    private int expectedContainers;
    private int availableCapacity;
    private double congestionPercentage;
    private List<String> recommendations;
    private List<ZoneCongestionAnalysis> zoneAnalysis;
    private List<TimeSlotAnalysis> timeSlotAnalysis;
    
    // Getters and Setters
    public LocalDate getPredictionDate() { return predictionDate; }
    public void setPredictionDate(LocalDate predictionDate) { this.predictionDate = predictionDate; }
    public int getDaysAhead() { return daysAhead; }
    public void setDaysAhead(int daysAhead) { this.daysAhead = daysAhead; }
    public RiskLevel getRiskLevel() { return riskLevel; }
    public void setRiskLevel(RiskLevel riskLevel) { this.riskLevel = riskLevel; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public int getExpectedContainers() { return expectedContainers; }
    public void setExpectedContainers(int expectedContainers) { this.expectedContainers = expectedContainers; }
    public int getAvailableCapacity() { return availableCapacity; }
    public void setAvailableCapacity(int availableCapacity) { this.availableCapacity = availableCapacity; }
    public double getCongestionPercentage() { return congestionPercentage; }
    public void setCongestionPercentage(double congestionPercentage) { this.congestionPercentage = congestionPercentage; }
    public List<String> getRecommendations() { return recommendations; }
    public void setRecommendations(List<String> recommendations) { this.recommendations = recommendations; }
    public List<ZoneCongestionAnalysis> getZoneAnalysis() { return zoneAnalysis; }
    public void setZoneAnalysis(List<ZoneCongestionAnalysis> zoneAnalysis) { this.zoneAnalysis = zoneAnalysis; }
    public List<TimeSlotAnalysis> getTimeSlotAnalysis() { return timeSlotAnalysis; }
    public void setTimeSlotAnalysis(List<TimeSlotAnalysis> timeSlotAnalysis) { this.timeSlotAnalysis = timeSlotAnalysis; }
}