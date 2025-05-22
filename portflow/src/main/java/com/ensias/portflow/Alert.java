package com.ensias.portflow;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "alerts")
public class Alert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Enumerated(EnumType.STRING)
    private AlertType type;
    
    @Enumerated(EnumType.STRING)
    private AlertSeverity severity;
    
    private String title;
    private String message;
    private String targetRole; // Which role should see this alert
    
    private LocalDateTime createdAt = LocalDateTime.now();
    private boolean acknowledged = false;
    private LocalDateTime acknowledgedAt;
    private String acknowledgedBy;
    
    // Reference to related entity
    private Long relatedEntityId;
    private String relatedEntityType;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public AlertType getType() {
		return type;
	}
	public void setType(AlertType type) {
		this.type = type;
	}
	public AlertSeverity getSeverity() {
		return severity;
	}
	public void setSeverity(AlertSeverity severity) {
		this.severity = severity;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getTargetRole() {
		return targetRole;
	}
	public void setTargetRole(String targetRole) {
		this.targetRole = targetRole;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public boolean isAcknowledged() {
		return acknowledged;
	}
	public void setAcknowledged(boolean acknowledged) {
		this.acknowledged = acknowledged;
	}
	public LocalDateTime getAcknowledgedAt() {
		return acknowledgedAt;
	}
	public void setAcknowledgedAt(LocalDateTime acknowledgedAt) {
		this.acknowledgedAt = acknowledgedAt;
	}
	public String getAcknowledgedBy() {
		return acknowledgedBy;
	}
	public void setAcknowledgedBy(String acknowledgedBy) {
		this.acknowledgedBy = acknowledgedBy;
	}
	public Long getRelatedEntityId() {
		return relatedEntityId;
	}
	public void setRelatedEntityId(Long relatedEntityId) {
		this.relatedEntityId = relatedEntityId;
	}
	public String getRelatedEntityType() {
		return relatedEntityType;
	}
	public void setRelatedEntityType(String relatedEntityType) {
		this.relatedEntityType = relatedEntityType;
	}
    
    
}