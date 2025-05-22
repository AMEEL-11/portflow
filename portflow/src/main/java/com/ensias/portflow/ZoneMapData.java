package com.ensias.portflow;

import java.util.List;

class ZoneMapData {
    private StorageZone zone;
    private List<StorageLocation> locations;
    private double occupancyRate;
    
    public StorageZone getZone() { return zone; }
    public void setZone(StorageZone zone) { this.zone = zone; }
    public List<StorageLocation> getLocations() { return locations; }
    public void setLocations(List<StorageLocation> locations) { this.locations = locations; }
    public double getOccupancyRate() { return occupancyRate; }
    public void setOccupancyRate(double occupancyRate) { this.occupancyRate = occupancyRate; }
}

