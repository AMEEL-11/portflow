package com.ensias.portflow;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlertService {
    
    @Autowired
    private AlertRepository alertRepository;
    
    public void createAlert(AlertType type, AlertSeverity severity, String title, String message, String targetRole) {
        Alert alert = new Alert();
        alert.setType(type);
        alert.setSeverity(severity);
        alert.setTitle(title);
        alert.setMessage(message);
        alert.setTargetRole(targetRole);
        
        alertRepository.save(alert);
    }
    
    public List<Alert> getUnacknowledgedAlertsForRole(String role) {
        return alertRepository.findByTargetRoleAndAcknowledgedFalse(role);
    }
    
    public void acknowledgeAlert(Long alertId, String acknowledgedBy) {
        Alert alert = alertRepository.findById(alertId)
            .orElseThrow(() -> new RuntimeException("Alert not found"));
        
        alert.setAcknowledged(true);
        alert.setAcknowledgedAt(LocalDateTime.now());
        alert.setAcknowledgedBy(acknowledgedBy);
        
        alertRepository.save(alert);
    }
}
