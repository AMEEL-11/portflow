package com.ensias.portflow;

import java.util.List;

class MonthlyReport {
    private int year;
    private int month;
    private long totalContainers;
    private long totalShips;
    private double averageProcessingTime;
    private double peakOccupancyRate;
    private List<WeeklyData> weeklyBreakdown;
    private MonthComparison comparisonWithPreviousMonth;
    private List<String> recommendations;
    private List<String> achievements;
    private List<String> challenges;
    
    // Getters and Setters
    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }
    public int getMonth() { return month; }
    public void setMonth(int month) { this.month = month; }
    public long getTotalContainers() { return totalContainers; }
    public void setTotalContainers(long totalContainers) { this.totalContainers = totalContainers; }
    public long getTotalShips() { return totalShips; }
    public void setTotalShips(long totalShips) { this.totalShips = totalShips; }
    public double getAverageProcessingTime() { return averageProcessingTime; }
    public void setAverageProcessingTime(double averageProcessingTime) { this.averageProcessingTime = averageProcessingTime; }
    public double getPeakOccupancyRate() { return peakOccupancyRate; }
    public void setPeakOccupancyRate(double peakOccupancyRate) { this.peakOccupancyRate = peakOccupancyRate; }
    public List<WeeklyData> getWeeklyBreakdown() { return weeklyBreakdown; }
    public void setWeeklyBreakdown(List<WeeklyData> weeklyBreakdown) { this.weeklyBreakdown = weeklyBreakdown; }
    public MonthComparison getComparisonWithPreviousMonth() { return comparisonWithPreviousMonth; }
    public void setComparisonWithPreviousMonth(MonthComparison comparisonWithPreviousMonth) { this.comparisonWithPreviousMonth = comparisonWithPreviousMonth; }
    public List<String> getRecommendations() { return recommendations; }
    public void setRecommendations(List<String> recommendations) { this.recommendations = recommendations; }
    public List<String> getAchievements() { return achievements; }
    public void setAchievements(List<String> achievements) { this.achievements = achievements; }
    public List<String> getChallenges() { return challenges; }
    public void setChallenges(List<String> challenges) { this.challenges = challenges; }
}