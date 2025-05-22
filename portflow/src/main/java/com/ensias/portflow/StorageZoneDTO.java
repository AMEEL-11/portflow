package com.ensias.portflow;

class StorageZoneDTO {
    private Long id;
    private String name;
    private ZoneType type;
    private Integer totalCapacity;
    private Integer currentOccupancy;
    private double occupancyRate;
    private int availableLocations;
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public ZoneType getType() { return type; }
    public void setType(ZoneType type) { this.type = type; }
    public Integer getTotalCapacity() { return totalCapacity; }
    public void setTotalCapacity(Integer totalCapacity) { this.totalCapacity = totalCapacity; }
    public Integer getCurrentOccupancy() { return currentOccupancy; }
    public void setCurrentOccupancy(Integer currentOccupancy) { this.currentOccupancy = currentOccupancy; }
    public double getOccupancyRate() { return occupancyRate; }
    public void setOccupancyRate(double occupancyRate) { this.occupancyRate = occupancyRate; }
    public int getAvailableLocations() { return availableLocations; }
    public void setAvailableLocations(int availableLocations) { this.availableLocations = availableLocations; }
}
