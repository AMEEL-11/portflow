package com.springprojets.portflow;

public class StorageAllocation {
    private StorageZone zone;
    private StoragePosition position;

    public StorageAllocation(StorageZone zone, StoragePosition position) {
        this.zone = zone;
        this.position = position;
    }

    public StorageZone getZone() {
        return zone;
    }

    public void setZone(StorageZone zone) {
        this.zone = zone;
    }

    public StoragePosition getPosition() {
        return position;
    }

    public void setPosition(StoragePosition position) {
        this.position = position;
    }
} 