package com.springprojets.portflow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/alerts")
public class AlertController {
    private final AlertService alertService;

    @Autowired
    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    @PostMapping
    public ResponseEntity<Alert> createAlert(@Valid @RequestBody Alert alert) {
        return ResponseEntity.ok(alertService.createAlert(alert));
    }

    @GetMapping
    public ResponseEntity<List<Alert>> getAllAlerts() {
        return ResponseEntity.ok(alertService.findAllAlerts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Alert> getAlertById(@PathVariable Long id) {
        return alertService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Alert>> getAlertsByStatus(@PathVariable AlertStatus status) {
        return ResponseEntity.ok(alertService.findByStatus(status));
    }

    @GetMapping("/priority/{priority}")
    public ResponseEntity<List<Alert>> getAlertsByPriority(@PathVariable AlertPriority priority) {
        return ResponseEntity.ok(alertService.findByPriority(priority));
    }

    @GetMapping("/active/priority/{priority}")
    public ResponseEntity<List<Alert>> getActiveAlertsByPriority(@PathVariable AlertPriority priority) {
        return ResponseEntity.ok(alertService.findActiveAlertsByPriority(priority));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Alert> updateAlert(@PathVariable Long id, @Valid @RequestBody Alert alert) {
        return alertService.findById(id)
                .map(existingAlert -> {
                    alert.setId(id);
                    return ResponseEntity.ok(alertService.updateAlert(alert));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Alert> updateAlertStatus(
            @PathVariable Long id,
            @RequestParam AlertStatus status) {
        try {
            Alert updatedAlert = alertService.updateAlertStatus(id, status);
            return ResponseEntity.ok(updatedAlert);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlert(@PathVariable Long id) {
        return alertService.findById(id)
                .map(alert -> {
                    alertService.deleteAlert(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
} 