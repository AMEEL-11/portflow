package com.ensias.portflow;

import java.util.List;

class YearProjection {
    private int projectedContainers;
    private double expectedGrowthRate;
    private List<String> infrastructureNeeds;
    private List<String> recommendations;
    
    // Getters and Setters
    public int getProjectedContainers() { return projectedContainers; }
    public void setProjectedContainers(int projectedContainers) { this.projectedContainers = projectedContainers; }
    public double getExpectedGrowthRate() { return expectedGrowthRate; }
    public void setExpectedGrowthRate(double expectedGrowthRate) { this.expectedGrowthRate = expectedGrowthRate; }
    public List<String> getInfrastructureNeeds() { return infrastructureNeeds; }
    public void setInfrastructureNeeds(List<String> infrastructureNeeds) { this.infrastructureNeeds = infrastructureNeeds; }
    public List<String> getRecommendations() { return recommendations; }
    public void setRecommendations(List<String> recommendations) { this.recommendations = recommendations; }
}