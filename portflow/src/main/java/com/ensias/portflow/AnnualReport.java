package com.ensias.portflow;

import java.util.List;

class AnnualReport {
    private int year;
    private long totalContainers;
    private long totalShips;
    private double averageAnnualOccupancy;
    private String bestPerformingMonth;
    private String worstPerformingMonth;
    private List<MonthlyTrendData> monthlyTrends;
    private PeakAnalysis peakAnalysis;
    private YearProjection nextYearProjections;
    private List<String> yearlyRecommendations;
    
    // Getters and Setters
    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }
    public long getTotalContainers() { return totalContainers; }
    public void setTotalContainers(long totalContainers) { this.totalContainers = totalContainers; }
    public long getTotalShips() { return totalShips; }
    public void setTotalShips(long totalShips) { this.totalShips = totalShips; }
    public double getAverageAnnualOccupancy() { return averageAnnualOccupancy; }
    public void setAverageAnnualOccupancy(double averageAnnualOccupancy) { this.averageAnnualOccupancy = averageAnnualOccupancy; }
    public String getBestPerformingMonth() { return bestPerformingMonth; }
    public void setBestPerformingMonth(String bestPerformingMonth) { this.bestPerformingMonth = bestPerformingMonth; }
    public String getWorstPerformingMonth() { return worstPerformingMonth; }
    public void setWorstPerformingMonth(String worstPerformingMonth) { this.worstPerformingMonth = worstPerformingMonth; }
    public List<MonthlyTrendData> getMonthlyTrends() { return monthlyTrends; }
    public void setMonthlyTrends(List<MonthlyTrendData> monthlyTrends) { this.monthlyTrends = monthlyTrends; }
    public PeakAnalysis getPeakAnalysis() { return peakAnalysis; }
    public void setPeakAnalysis(PeakAnalysis peakAnalysis) { this.peakAnalysis = peakAnalysis; }
    public YearProjection getNextYearProjections() { return nextYearProjections; }
    public void setNextYearProjections(YearProjection nextYearProjections) { this.nextYearProjections = nextYearProjections; }
    public List<String> getYearlyRecommendations() { return yearlyRecommendations; }
    public void setYearlyRecommendations(List<String> yearlyRecommendations) { this.yearlyRecommendations = yearlyRecommendations; }
}