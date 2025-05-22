package com.springprojets.portflow;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserRoleTest {
    
    @Test
    void testGetDisplayName() {
        assertEquals("Administrator", UserRole.ADMINISTRATOR.getDisplayName());
        assertEquals("Berth Planner", UserRole.BERTH_PLANNER.getDisplayName());
        assertEquals("Yard Planner", UserRole.YARD_PLANNER.getDisplayName());
        assertEquals("Operations Manager", UserRole.OPERATIONS_MANAGER.getDisplayName());
        assertEquals("Client", UserRole.CLIENT.getDisplayName());
        assertEquals("Documentation Service", UserRole.DOCUMENTATION_SERVICE.getDisplayName());
    }
    
    @Test
    void testFromDisplayNameValid() {
        assertEquals(UserRole.ADMINISTRATOR, UserRole.fromDisplayName("Administrator"));
        assertEquals(UserRole.BERTH_PLANNER, UserRole.fromDisplayName("Berth Planner"));
        assertEquals(UserRole.YARD_PLANNER, UserRole.fromDisplayName("Yard Planner"));
        assertEquals(UserRole.OPERATIONS_MANAGER, UserRole.fromDisplayName("Operations Manager"));
        assertEquals(UserRole.CLIENT, UserRole.fromDisplayName("Client"));
        assertEquals(UserRole.DOCUMENTATION_SERVICE, UserRole.fromDisplayName("Documentation Service"));
    }
    
    @Test
    void testFromDisplayNameCaseInsensitive() {
        assertEquals(UserRole.ADMINISTRATOR, UserRole.fromDisplayName("administrator"));
        assertEquals(UserRole.BERTH_PLANNER, UserRole.fromDisplayName("berth planner"));
    }
    
    @Test
    void testFromDisplayNameInvalid() {
        assertThrows(IllegalArgumentException.class, () -> 
            UserRole.fromDisplayName("Invalid Role"));
    }
} 