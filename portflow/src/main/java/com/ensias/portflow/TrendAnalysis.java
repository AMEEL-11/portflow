package com.ensias.portflow;

import java.util.List;

class TrendAnalysis {
    private String period;
    private List<TrendData> data;
    private String overallTrend;
    private List<String> insights;
    
    // Getters and Setters
    public String getPeriod() { return period; }
    public void setPeriod(String period) { this.period = period; }
    public List<TrendData> getData() { return data; }
    public void setData(List<TrendData> data) { this.data = data; }
    public String getOverallTrend() { return overallTrend; }
    public void setOverallTrend(String overallTrend) { this.overallTrend = overallTrend; }
    public List<String> getInsights() { return insights; }
    public void setInsights(List<String> insights) { this.insights = insights; }
}