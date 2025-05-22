package com.springprojets.portflow;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EnumTest {

    @Test
    void testAlertPriorityValues() {
        assertEquals(4, AlertPriority.values().length);
        assertNotNull(AlertPriority.LOW);
        assertNotNull(AlertPriority.MEDIUM);
        assertNotNull(AlertPriority.HIGH);
        assertNotNull(AlertPriority.CRITICAL);
    }

    @Test
    void testAlertStatusValues() {
        assertEquals(5, AlertStatus.values().length);
        assertNotNull(AlertStatus.NEW);
        assertNotNull(AlertStatus.ACKNOWLEDGED);
        assertNotNull(AlertStatus.IN_PROGRESS);
        assertNotNull(AlertStatus.RESOLVED);
        assertNotNull(AlertStatus.CLOSED);
    }

    @Test
    void testContainerStatusValues() {
        assertEquals(7, ContainerStatus.values().length);
        assertNotNull(ContainerStatus.EMPTY);
        assertNotNull(ContainerStatus.LOADED);
        assertNotNull(ContainerStatus.IN_TRANSIT);
        assertNotNull(ContainerStatus.STORED);
        assertNotNull(ContainerStatus.DAMAGED);
        assertNotNull(ContainerStatus.UNDER_INSPECTION);
        assertNotNull(ContainerStatus.READY_FOR_PICKUP);
    }

    @Test
    void testEscaleStatusValues() {
        assertEquals(9, EscaleStatus.values().length);
        assertNotNull(EscaleStatus.SCHEDULED);
        assertNotNull(EscaleStatus.APPROACHING);
        assertNotNull(EscaleStatus.ARRIVED);
        assertNotNull(EscaleStatus.BERTHING);
        assertNotNull(EscaleStatus.BERTHED);
        assertNotNull(EscaleStatus.LOADING);
        assertNotNull(EscaleStatus.UNLOADING);
        assertNotNull(EscaleStatus.COMPLETED);
        assertNotNull(EscaleStatus.DEPARTED);
    }

    @Test
    void testShipStatusValues() {
        assertEquals(7, ShipStatus.values().length);
        assertNotNull(ShipStatus.EN_ROUTE);
        assertNotNull(ShipStatus.ANCHORED);
        assertNotNull(ShipStatus.DOCKED);
        assertNotNull(ShipStatus.LOADING);
        assertNotNull(ShipStatus.UNLOADING);
        assertNotNull(ShipStatus.MAINTENANCE);
        assertNotNull(ShipStatus.OUT_OF_SERVICE);
    }

    @Test
    void testEnumValueOf() {
        assertEquals(AlertPriority.HIGH, AlertPriority.valueOf("HIGH"));
        assertEquals(AlertStatus.NEW, AlertStatus.valueOf("NEW"));
        assertEquals(ContainerStatus.LOADED, ContainerStatus.valueOf("LOADED"));
        assertEquals(EscaleStatus.BERTHED, EscaleStatus.valueOf("BERTHED"));
        assertEquals(ShipStatus.DOCKED, ShipStatus.valueOf("DOCKED"));
    }

    @Test
    void testEnumOrdinal() {
        assertEquals(0, AlertPriority.LOW.ordinal());
        assertEquals(0, AlertStatus.NEW.ordinal());
        assertEquals(0, ContainerStatus.EMPTY.ordinal());
        assertEquals(0, EscaleStatus.SCHEDULED.ordinal());
        assertEquals(0, ShipStatus.EN_ROUTE.ordinal());
    }
} 