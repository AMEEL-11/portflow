package com.springprojets.portflow;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ZoneOccupancyDtoTest {

    @Test
    void testZoneOccupancyDtoConstructorAndGetters() {
        String zoneName = "Zone A";
        int currentOccupancy = 75;
        int totalCapacity = 100;
        int reservedSlots = 10;
        
        ZoneOccupancyDto dto = new ZoneOccupancyDto(zoneName, currentOccupancy, totalCapacity, reservedSlots);
        
        assertEquals(zoneName, dto.getZoneName());
        assertEquals(currentOccupancy, dto.getCurrentOccupancy());
        assertEquals(totalCapacity, dto.getTotalCapacity());
        assertEquals(reservedSlots, dto.getReservedSlots());
        assertEquals(75.0, dto.getOccupancyRate(), 0.01); // 75% occupancy rate
    }

    @Test
    void testZoneOccupancyDtoSetters() {
        ZoneOccupancyDto dto = new ZoneOccupancyDto();
        
        String zoneName = "Zone A";
        int currentOccupancy = 75;
        int totalCapacity = 100;
        int reservedSlots = 10;
        
        dto.setZoneName(zoneName);
        dto.setCurrentOccupancy(currentOccupancy);
        dto.setTotalCapacity(totalCapacity);
        dto.setReservedSlots(reservedSlots);
        
        assertEquals(zoneName, dto.getZoneName());
        assertEquals(currentOccupancy, dto.getCurrentOccupancy());
        assertEquals(totalCapacity, dto.getTotalCapacity());
        assertEquals(reservedSlots, dto.getReservedSlots());
        assertEquals(75.0, dto.getOccupancyRate(), 0.01);
    }

    @Test
    void testDefaultConstructor() {
        ZoneOccupancyDto dto = new ZoneOccupancyDto();
        
        assertNull(dto.getZoneName());
        assertEquals(0, dto.getCurrentOccupancy());
        assertEquals(0, dto.getTotalCapacity());
        assertEquals(0, dto.getReservedSlots());
        assertEquals(0.0, dto.getOccupancyRate(), 0.01);
    }

    @Test
    void testOccupancyRateCalculation() {
        ZoneOccupancyDto dto = new ZoneOccupancyDto("Zone B", 30, 60, 5);
        assertEquals(50.0, dto.getOccupancyRate(), 0.01); // 50% occupancy rate
        
        dto = new ZoneOccupancyDto("Zone C", 0, 100, 0);
        assertEquals(0.0, dto.getOccupancyRate(), 0.01); // 0% occupancy rate
        
        dto = new ZoneOccupancyDto("Zone D", 100, 100, 0);
        assertEquals(100.0, dto.getOccupancyRate(), 0.01); // 100% occupancy rate
        
        dto = new ZoneOccupancyDto("Zone E", 0, 0, 0);
        assertEquals(0.0, dto.getOccupancyRate(), 0.01); // Handle division by zero
    }
} 