package com.ensias.portflow;

import java.time.LocalDateTime;
import java.util.List;

class ContainerHistoryResponse {
    private String containerNumber;
    private String client;
    private String origin;
    private String destination;
    private LocalDateTime arrivalTime;
    private LocalDateTime departureTime;
    private List<ContainerMovement> fullHistory;
    private List<String> documents;
    
    // Getters and Setters
    public String getContainerNumber() { return containerNumber; }
    public void setContainerNumber(String containerNumber) { this.containerNumber = containerNumber; }
    public String getClient() { return client; }
    public void setClient(String client) { this.client = client; }
    public String getOrigin() { return origin; }
    public void setOrigin(String origin) { this.origin = origin; }
    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }
    public LocalDateTime getArrivalTime() { return arrivalTime; }
    public void setArrivalTime(LocalDateTime arrivalTime) { this.arrivalTime = arrivalTime; }
    public LocalDateTime getDepartureTime() { return departureTime; }
    public void setDepartureTime(LocalDateTime departureTime) { this.departureTime = departureTime; }
    public List<ContainerMovement> getFullHistory() { return fullHistory; }
    public void setFullHistory(List<ContainerMovement> fullHistory) { this.fullHistory = fullHistory; }
    public List<String> getDocuments() { return documents; }
    public void setDocuments(List<String> documents) { this.documents = documents; }
}