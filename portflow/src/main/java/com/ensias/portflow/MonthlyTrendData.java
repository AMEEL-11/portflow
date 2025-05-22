package com.ensias.portflow;

class MonthlyTrendData {
    private String month;
    private long containers;
    private double efficiency;
    private double occupancy;
    
    // Getters and Setters
    public String getMonth() { return month; }
    public void setMonth(String month) { this.month = month; }
    public long getContainers() { return containers; }
    public void setContainers(long containers) { this.containers = containers; }
    public double getEfficiency() { return efficiency; }
    public void setEfficiency(double efficiency) { this.efficiency = efficiency; }
    public double getOccupancy() { return occupancy; }
    public void setOccupancy(double occupancy) { this.occupancy = occupancy; }
}