package com.springprojets.portflow;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AlertServiceTest {

    @Mock
    private AlertRepository alertRepository;

    @InjectMocks
    private AlertService alertService;

    private Alert testAlert;

    @BeforeEach
    void setUp() {
        testAlert = new Alert(
            "Test Alert",
            "This is a test alert message",
            AlertPriority.HIGH,
            "[\"ADMINISTRATOR\"]"
        );
        testAlert.setId(1L);
    }

    @Test
    void createAlert_Success() {
        when(alertRepository.save(any(Alert.class))).thenReturn(testAlert);

        Alert result = alertService.createAlert(testAlert);

        assertNotNull(result);
        assertEquals(testAlert.getTitle(), result.getTitle());
        assertEquals(AlertStatus.NEW, result.getStatus());
        verify(alertRepository).save(testAlert);
    }

    @Test
    void findAllAlerts_Success() {
        List<Alert> alerts = Arrays.asList(testAlert);
        when(alertRepository.findAll()).thenReturn(alerts);

        List<Alert> result = alertService.findAllAlerts();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void findById_Success() {
        when(alertRepository.findById(anyLong())).thenReturn(Optional.of(testAlert));

        Optional<Alert> result = alertService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(testAlert.getId(), result.get().getId());
    }

    @Test
    void findByStatus_Success() {
        List<Alert> alerts = Arrays.asList(testAlert);
        when(alertRepository.findByStatus(any(AlertStatus.class))).thenReturn(alerts);

        List<Alert> result = alertService.findByStatus(AlertStatus.NEW);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void findByPriority_Success() {
        List<Alert> alerts = Arrays.asList(testAlert);
        when(alertRepository.findByPriority(any(AlertPriority.class))).thenReturn(alerts);

        List<Alert> result = alertService.findByPriority(AlertPriority.HIGH);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void updateAlert_Success() {
        when(alertRepository.save(any(Alert.class))).thenReturn(testAlert);

        Alert result = alertService.updateAlert(testAlert);

        assertNotNull(result);
        assertNotNull(result.getUpdatedAt());
        verify(alertRepository).save(testAlert);
    }

    @Test
    void updateAlertStatus_Success() {
        when(alertRepository.findById(anyLong())).thenReturn(Optional.of(testAlert));
        when(alertRepository.save(any(Alert.class))).thenReturn(testAlert);

        Alert result = alertService.updateAlertStatus(1L, AlertStatus.ACKNOWLEDGED);

        assertNotNull(result);
        assertEquals(AlertStatus.ACKNOWLEDGED, result.getStatus());
        assertNotNull(result.getUpdatedAt());
        verify(alertRepository).save(testAlert);
    }

    @Test
    void updateAlertStatus_NotFound() {
        when(alertRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> 
            alertService.updateAlertStatus(1L, AlertStatus.ACKNOWLEDGED));

        verify(alertRepository, never()).save(any(Alert.class));
    }

    @Test
    void deleteAlert_Success() {
        doNothing().when(alertRepository).deleteById(anyLong());

        alertService.deleteAlert(1L);

        verify(alertRepository).deleteById(1L);
    }

    @Test
    void findActiveAlertsByPriority_Success() {
        Alert resolvedAlert = new Alert(
            "Resolved Alert",
            "This is resolved",
            AlertPriority.HIGH,
            "[\"ADMINISTRATOR\"]"
        );
        resolvedAlert.setStatus(AlertStatus.RESOLVED);

        List<Alert> allAlerts = Arrays.asList(testAlert, resolvedAlert);
        when(alertRepository.findAll()).thenReturn(allAlerts);

        List<Alert> result = alertService.findActiveAlertsByPriority(AlertPriority.HIGH);

        assertEquals(1, result.size());
        assertEquals(testAlert.getId(), result.get(0).getId());
    }
} 