package com.ensias.portflow;

import java.util.List;

class MonthComparison {
    private double containerGrowthRate;
    private double efficiencyImprovement;
    private double occupancyChange;
    private List<String> improvements;
    private List<String> declines;
    
    // Getters and Setters
    public double getContainerGrowthRate() { return containerGrowthRate; }
    public void setContainerGrowthRate(double containerGrowthRate) { this.containerGrowthRate = containerGrowthRate; }
    public double getEfficiencyImprovement() { return efficiencyImprovement; }
    public void setEfficiencyImprovement(double efficiencyImprovement) { this.efficiencyImprovement = efficiencyImprovement; }
    public double getOccupancyChange() { return occupancyChange; }
    public void setOccupancyChange(double occupancyChange) { this.occupancyChange = occupancyChange; }
    public List<String> getImprovements() { return improvements; }
    public void setImprovements(List<String> improvements) { this.improvements = improvements; }
    public List<String> getDeclines() { return declines; }
    public void setDeclines(List<String> declines) { this.declines = declines; }
}
