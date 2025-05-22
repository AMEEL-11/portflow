package com.ensias.portflow;

class WeeklyData {
    private int weekNumber;
    private long containerCount;
    private double averageWaitTime;
    private double occupancyRate;
    
    // Getters and Setters
    public int getWeekNumber() { return weekNumber; }
    public void setWeekNumber(int weekNumber) { this.weekNumber = weekNumber; }
    public long getContainerCount() { return containerCount; }
    public void setContainerCount(long containerCount) { this.containerCount = containerCount; }
    public double getAverageWaitTime() { return averageWaitTime; }
    public void setAverageWaitTime(double averageWaitTime) { this.averageWaitTime = averageWaitTime; }
    public double getOccupancyRate() { return occupancyRate; }
    public void setOccupancyRate(double occupancyRate) { this.occupancyRate = occupancyRate; }
}