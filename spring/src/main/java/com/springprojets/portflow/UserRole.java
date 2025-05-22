package com.springprojets.portflow;

public enum UserRole {
    ADMINISTRATOR("Administrator"),
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
    
    public static UserRole fromDisplayName(String displayName) {
        for (UserRole role : values()) {
            if (role.getDisplayName().equalsIgnoreCase(displayName)) {
                return role;
            }
        }
        throw new IllegalArgumentException("No role found for display name: " + displayName);
    }
}