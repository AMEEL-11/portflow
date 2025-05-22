package com.springprojets.portflow;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AlertController.class)
class AlertControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AlertService alertService;

    @Autowired
    private ObjectMapper objectMapper;

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
    void createAlert_Success() throws Exception {
        when(alertService.createAlert(any(Alert.class))).thenReturn(testAlert);

        mockMvc.perform(post("/api/alerts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testAlert)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(testAlert.getTitle()))
                .andExpect(jsonPath("$.message").value(testAlert.getMessage()))
                .andExpect(jsonPath("$.priority").value(testAlert.getPriority().toString()));

        verify(alertService).createAlert(any(Alert.class));
    }

    @Test
    void getAllAlerts_Success() throws Exception {
        when(alertService.findAllAlerts()).thenReturn(Arrays.asList(testAlert));

        mockMvc.perform(get("/api/alerts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value(testAlert.getTitle()))
                .andExpect(jsonPath("$[0].message").value(testAlert.getMessage()));

        verify(alertService).findAllAlerts();
    }

    @Test
    void getAlertById_Success() throws Exception {
        when(alertService.findById(1L)).thenReturn(Optional.of(testAlert));

        mockMvc.perform(get("/api/alerts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(testAlert.getTitle()))
                .andExpect(jsonPath("$.message").value(testAlert.getMessage()));

        verify(alertService).findById(1L);
    }

    @Test
    void getAlertById_NotFound() throws Exception {
        when(alertService.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/alerts/1"))
                .andExpect(status().isNotFound());

        verify(alertService).findById(1L);
    }

    @Test
    void getAlertsByStatus_Success() throws Exception {
        when(alertService.findByStatus(any(AlertStatus.class)))
                .thenReturn(Arrays.asList(testAlert));

        mockMvc.perform(get("/api/alerts/status/NEW"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value(testAlert.getTitle()));

        verify(alertService).findByStatus(AlertStatus.NEW);
    }

    @Test
    void getAlertsByPriority_Success() throws Exception {
        when(alertService.findByPriority(any(AlertPriority.class)))
                .thenReturn(Arrays.asList(testAlert));

        mockMvc.perform(get("/api/alerts/priority/HIGH"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value(testAlert.getTitle()));

        verify(alertService).findByPriority(AlertPriority.HIGH);
    }

    @Test
    void updateAlert_Success() throws Exception {
        when(alertService.findById(1L)).thenReturn(Optional.of(testAlert));
        when(alertService.updateAlert(any(Alert.class))).thenReturn(testAlert);

        mockMvc.perform(put("/api/alerts/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testAlert)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(testAlert.getTitle()));

        verify(alertService).updateAlert(any(Alert.class));
    }

    @Test
    void updateAlert_NotFound() throws Exception {
        when(alertService.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/alerts/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testAlert)))
                .andExpect(status().isNotFound());

        verify(alertService, never()).updateAlert(any(Alert.class));
    }

    @Test
    void updateAlertStatus_Success() throws Exception {
        when(alertService.updateAlertStatus(1L, AlertStatus.ACKNOWLEDGED))
                .thenReturn(testAlert);

        mockMvc.perform(patch("/api/alerts/1/status")
                .param("status", "ACKNOWLEDGED"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(testAlert.getTitle()));

        verify(alertService).updateAlertStatus(1L, AlertStatus.ACKNOWLEDGED);
    }

    @Test
    void deleteAlert_Success() throws Exception {
        when(alertService.findById(1L)).thenReturn(Optional.of(testAlert));
        doNothing().when(alertService).deleteAlert(1L);

        mockMvc.perform(delete("/api/alerts/1"))
                .andExpect(status().isOk());

        verify(alertService).deleteAlert(1L);
    }

    @Test
    void deleteAlert_NotFound() throws Exception {
        when(alertService.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/alerts/1"))
                .andExpect(status().isNotFound());

        verify(alertService, never()).deleteAlert(anyLong());
    }
} 