package com.ensias.portflow;

class MonthlyActivityData {
    private String month;
    private int year;
    private long containerCount;
    private long shipCount;
    private double averageProcessingTime;
    private double peakOccupancy;
    
    // Getters and Setters
    public String getMonth() { return month; }
    public void setMonth(String month) { this.month = month; }
    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }
    public long getContainerCount() { return containerCount; }
    public void setContainerCount(long containerCount) { this.containerCount = containerCount; }
    public long getShipCount() { return shipCount; }
    public void setShipCount(long shipCount) { this.shipCount = shipCount; }
    public double getAverageProcessingTime() { return averageProcessingTime; }
    public void setAverageProcessingTime(double averageProcessingTime) { this.averageProcessingTime = averageProcessingTime; }
    public double getPeakOccupancy() { return peakOccupancy; }
    public void setPeakOccupancy(double peakOccupancy) { this.peakOccupancy = peakOccupancy; }
}
