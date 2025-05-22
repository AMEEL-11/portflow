package com.ensias.portflow;

import java.time.LocalDateTime;

class CreateEscaleRequest {
    private Long shipId;
    private LocalDateTime eta;
    private LocalDateTime etd;
    private String berthAssigned;
    private Integer expectedContainers;
    
    // Getters and Setters
    public Long getShipId() { return shipId; }
    public void setShipId(Long shipId) { this.shipId = shipId; }
    public LocalDateTime getEta() { return eta; }
    public void setEta(LocalDateTime eta) { this.eta = eta; }
    public LocalDateTime getEtd() { return etd; }
    public void setEtd(LocalDateTime etd) { this.etd = etd; }
    public String getBerthAssigned() { return berthAssigned; }
    public void setBerthAssigned(String berthAssigned) { this.berthAssigned = berthAssigned; }
    public Integer getExpectedContainers() { return expectedContainers; }
    public void setExpectedContainers(Integer expectedContainers) { this.expectedContainers = expectedContainers; }
}