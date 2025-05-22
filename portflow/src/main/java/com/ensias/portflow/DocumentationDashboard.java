package com.ensias.portflow;

import java.util.List;

class DocumentationDashboard {
    private List<KPIData> currentKPIs;
    private long totalContainersProcessed;
    private long activeOperations;
    private double averageWaitingTime;
    private double storageEfficiency;
    private List<MonthlyActivityData> monthlyActivity;
    private List<ContainerTypeData> containerTypeDistribution;
    private List<StorageOccupancyData> storageOccupancyTrend;
    
    // Getters and Setters
    public List<KPIData> getCurrentKPIs() { return currentKPIs; }
    public void setCurrentKPIs(List<KPIData> currentKPIs) { this.currentKPIs = currentKPIs; }
    public long getTotalContainersProcessed() { return totalContainersProcessed; }
    public void setTotalContainersProcessed(long totalContainersProcessed) { this.totalContainersProcessed = totalContainersProcessed; }
    public long getActiveOperations() { return activeOperations; }
    public void setActiveOperations(long activeOperations) { this.activeOperations = activeOperations; }
    public double getAverageWaitingTime() { return averageWaitingTime; }
    public void setAverageWaitingTime(double averageWaitingTime) { this.averageWaitingTime = averageWaitingTime; }
    public double getStorageEfficiency() { return storageEfficiency; }
    public void setStorageEfficiency(double storageEfficiency) { this.storageEfficiency = storageEfficiency; }
    public List<MonthlyActivityData> getMonthlyActivity() { return monthlyActivity; }
    public void setMonthlyActivity(List<MonthlyActivityData> monthlyActivity) { this.monthlyActivity = monthlyActivity; }
    public List<ContainerTypeData> getContainerTypeDistribution() { return containerTypeDistribution; }
    public void setContainerTypeDistribution(List<ContainerTypeData> containerTypeDistribution) { this.containerTypeDistribution = containerTypeDistribution; }
    public List<StorageOccupancyData> getStorageOccupancyTrend() { return storageOccupancyTrend; }
    public void setStorageOccupancyTrend(List<StorageOccupancyData> storageOccupancyTrend) { this.storageOccupancyTrend = storageOccupancyTrend; }
}