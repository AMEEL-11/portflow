package com.ensias.portflow;

import java.time.LocalDateTime;

class KPIData {
    private String name;
    private double value;
    private String unit;
    private String trend; // "up", "down", "stable"
    private String description;
    private LocalDateTime lastUpdate;
    
    public KPIData() {}
    
    public KPIData(String name, double value, String unit, String trend) {
        this.name = name;
        this.value = value;
        this.unit = unit;
        this.trend = trend;
        this.lastUpdate = LocalDateTime.now();
    }
    
    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public double getValue() { return value; }
    public void setValue(double value) { this.value = value; }
    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }
    public String getTrend() { return trend; }
    public void setTrend(String trend) { this.trend = trend; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDateTime getLastUpdate() { return lastUpdate; }
    public void setLastUpdate(LocalDateTime lastUpdate) { this.lastUpdate = lastUpdate; }
}