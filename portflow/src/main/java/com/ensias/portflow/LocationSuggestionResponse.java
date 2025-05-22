package com.ensias.portflow;

import java.util.List;

class LocationSuggestionResponse {
    private StorageLocation suggestedLocation;
    private String reason;
    private List<StorageLocation> alternatives;
    
    // Getters and Setters
    public StorageLocation getSuggestedLocation() { return suggestedLocation; }
    public void setSuggestedLocation(StorageLocation suggestedLocation) { this.suggestedLocation = suggestedLocation; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
    public List<StorageLocation> getAlternatives() { return alternatives; }
    public void setAlternatives(List<StorageLocation> alternatives) { this.alternatives = alternatives; }
}

