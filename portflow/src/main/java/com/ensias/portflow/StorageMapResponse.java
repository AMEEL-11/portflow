package com.ensias.portflow;

import java.util.List;

class StorageMapResponse {
    private List<ZoneMapData> zones;
    
    public List<ZoneMapData> getZones() { return zones; }
    public void setZones(List<ZoneMapData> zones) { this.zones = zones; }
}