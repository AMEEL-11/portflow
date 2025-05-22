package com.ensias.portflow;

public enum UserRole {
    ADMIN("Administrateur"),
    BERTH_PLANNER("Berth Planner"),
    YARD_PLANNER("Yard Planner"),
    OPERATIONS_MANAGER("Operations Manager"),
    CLIENT("Client"),
    DOCUMENTATION_SERVICE("Documentation Service");
    
    private final String displayName;
    
    UserRole(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}