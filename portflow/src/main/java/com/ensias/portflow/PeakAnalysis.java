package com.ensias.portflow;

import java.util.List;

class PeakAnalysis {
    private String peakPeriod;
    private double peakOccupancy;
    private String peakReason;
    private List<String> mitigationStrategies;
    
    // Getters and Setters
    public String getPeakPeriod() { return peakPeriod; }
    public void setPeakPeriod(String peakPeriod) { this.peakPeriod = peakPeriod; }
    public double getPeakOccupancy() { return peakOccupancy; }
    public void setPeakOccupancy(double peakOccupancy) { this.peakOccupancy = peakOccupancy; }
    public String getPeakReason() { return peakReason; }
    public void setPeakReason(String peakReason) { this.peakReason = peakReason; }
    public List<String> getMitigationStrategies() { return mitigationStrategies; }
    public void setMitigationStrategies(List<String> mitigationStrategies) { this.mitigationStrategies = mitigationStrategies; }
}