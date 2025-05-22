package com.springprojets.portflow;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class ShipTrackingDtoTest {

    @Test
    void testShipTrackingDtoConstructorAndGetters() {
        Long shipId = 1L;
        String name = "Cargo Ship 1";
        Double latitude = 40.7128;
        Double longitude = -74.0060;
        Double speed = 15.5;
        ShipStatus status = ShipStatus.EN_ROUTE;
        LocalDateTime lastUpdated = LocalDateTime.now();
        
        ShipTrackingDto dto = new ShipTrackingDto(shipId, name, latitude, longitude, speed, status, lastUpdated);
        
        assertEquals(shipId, dto.getShipId());
        assertEquals(name, dto.getName());
        assertEquals(latitude, dto.getLatitude());
        assertEquals(longitude, dto.getLongitude());
        assertEquals(speed, dto.getSpeed());
        assertEquals(status, dto.getStatus());
        assertEquals(lastUpdated, dto.getLastUpdated());
    }

    @Test
    void testShipTrackingDtoSetters() {
        ShipTrackingDto dto = new ShipTrackingDto();
        
        Long shipId = 1L;
        String name = "Cargo Ship 1";
        Double latitude = 40.7128;
        Double longitude = -74.0060;
        Double speed = 15.5;
        ShipStatus status = ShipStatus.EN_ROUTE;
        LocalDateTime lastUpdated = LocalDateTime.now();
        
        dto.setShipId(shipId);
        dto.setName(name);
        dto.setLatitude(latitude);
        dto.setLongitude(longitude);
        dto.setSpeed(speed);
        dto.setStatus(status);
        dto.setLastUpdated(lastUpdated);
        
        assertEquals(shipId, dto.getShipId());
        assertEquals(name, dto.getName());
        assertEquals(latitude, dto.getLatitude());
        assertEquals(longitude, dto.getLongitude());
        assertEquals(speed, dto.getSpeed());
        assertEquals(status, dto.getStatus());
        assertEquals(lastUpdated, dto.getLastUpdated());
    }

    @Test
    void testDefaultConstructor() {
        ShipTrackingDto dto = new ShipTrackingDto();
        
        assertNull(dto.getShipId());
        assertNull(dto.getName());
        assertNull(dto.getLatitude());
        assertNull(dto.getLongitude());
        assertNull(dto.getSpeed());
        assertNull(dto.getStatus());
        assertNull(dto.getLastUpdated());
    }
} 