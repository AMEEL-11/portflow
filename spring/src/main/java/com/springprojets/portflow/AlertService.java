package com.springprojets.portflow;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.*;
import org.springframework.scheduling.annotation.*;
import org.springframework.stereotype.*;
import jakarta.transaction.Transactional;


@Service
public class AlertService {
    
    private final AlertRepository alertRepository;
    
    @Autowired
    public AlertService(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }
    
    @Transactional
    public Alert createAlert(Alert alert) {
        alert.setCreatedAt(LocalDateTime.now());
        alert.setStatus(AlertStatus.NEW);
        return alertRepository.save(alert);
    }
    
    public List<Alert> findAllAlerts() {
        return alertRepository.findAll();
    }
    
    public Optional<Alert> findById(Long id) {
        return alertRepository.findById(id);
    }
    
    public List<Alert> findByStatus(AlertStatus status) {
        return alertRepository.findByStatus(status);
    }
    
    public List<Alert> findByPriority(AlertPriority priority) {
        return alertRepository.findByPriority(priority);
    }
    
    public List<Alert> findByTargetRoles(String targetRoles) {
        return alertRepository.findByTargetRoles(targetRoles);
    }
    
    @Transactional
    public Alert updateAlert(Alert alert) {
        alert.setUpdatedAt(LocalDateTime.now());
        return alertRepository.save(alert);
    }
    
    @Transactional
    public Alert updateAlertStatus(Long alertId, AlertStatus newStatus) {
        Alert alert = alertRepository.findById(alertId)
            .orElseThrow(() -> new RuntimeException("Alert not found with id: " + alertId));
        
        alert.updateStatus(newStatus);
        return alertRepository.save(alert);
    }
    
    @Transactional
    public void deleteAlert(Long id) {
        alertRepository.deleteById(id);
    }
    
    public List<Alert> findActiveAlertsByPriority(AlertPriority priority) {
        return alertRepository.findAll().stream()
            .filter(alert -> alert.getPriority() == priority)
            .filter(alert -> alert.getStatus() != AlertStatus.RESOLVED 
                && alert.getStatus() != AlertStatus.CLOSED)
            .toList();
    }
    
    public void createEquipmentAlert(String equipmentId, String message) {
        Alert alert = new Alert();
        alert.setTitle("Equipment Issue");
        alert.setMessage(message);
        alert.setAlertType(AlertType.EQUIPMENT);
        alert.setPriority(AlertPriority.HIGH);
        alert.setStatus(AlertStatus.ACTIVE);
        alert.setTargetRoles("[\"BERTH_PLANNER\", \"OPERATIONS_MANAGER\"]");
        alert.setCreatedAt(LocalDateTime.now());
        
        alertRepository.save(alert);
    }
    
    public void createStorageAlert(StorageZone zone, String message) {
        Alert alert = new Alert();
        alert.setTitle("Storage Alert");
        alert.setMessage(message);
        alert.setAlertType(AlertType.STORAGE);
        alert.setPriority(AlertPriority.MEDIUM);
        alert.setStatus(AlertStatus.ACTIVE);
        alert.setTargetRoles("[\"YARD_PLANNER\", \"OPERATIONS_MANAGER\"]");
        alert.setRelatedEntityId(zone.getId().toString());
        alert.setRelatedEntityType("STORAGE_ZONE");
        alert.setCreatedAt(LocalDateTime.now());
        
        alertRepository.save(alert);
    }
    
    public List<Alert> getAlertsForRole(UserRole role) {
        return alertRepository.findActiveAlertsByRole(role.name());
    }
}