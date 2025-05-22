package com.springprojets.portflow;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "alerts")
public class Alert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Title is required")
    private String title;
    
    @NotBlank(message = "Message is required")
    private String message;
    
    @NotNull(message = "Priority is required")
    @Enumerated(EnumType.STRING)
    private AlertPriority priority;
    
    @NotNull(message = "Status is required")
    @Enumerated(EnumType.STRING)
    private AlertStatus status;
    
    @NotNull(message="Type of alert is required")
    @Enumerated(EnumType.STRING)
    private AlertType alertType;
    
    @NotBlank(message = "Target roles are required")
    private String targetRoles; // JSON des rôles concernés
    
    private String relatedEntityId; // ID de l'entité concernée (navire, conteneur, etc.)
    private String relatedEntityType;
    
    @NotNull(message = "Creation date is required")
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
    private LocalDateTime resolvedAt;

    // Constructors
    public Alert() {
        this.createdAt = LocalDateTime.now();
        this.status = AlertStatus.NEW;
    }

    public Alert(Long id, @NotBlank(message = "Title is required") String title,
			@NotBlank(message = "Message is required") String message,
			@NotNull(message = "Priority is required") AlertPriority priority,
			@NotNull(message = "Status is required") AlertStatus status,
			@NotNull(message = "Type of alert is required") AlertType alertType,
			@NotBlank(message = "Target roles are required") String targetRoles, String relatedEntityId,
			String relatedEntityType, @NotNull(message = "Creation date is required") LocalDateTime createdAt,
			LocalDateTime updatedAt, LocalDateTime resolvedAt) {
		super();
		this.id = id;
		this.title = title;
		this.message = message;
		this.priority = priority;
		this.status = status;
		this.alertType = alertType;
		this.targetRoles = targetRoles;
		this.relatedEntityId = relatedEntityId;
		this.relatedEntityType = relatedEntityType;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.resolvedAt = resolvedAt;
	}



	public AlertType getAlertType() {
		return alertType;
	}

	public void setAlertType(AlertType alertType) {
		this.alertType = alertType;
	}

	// Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public AlertPriority getPriority() {
        return priority;
    }

    public void setPriority(AlertPriority priority) {
        this.priority = priority;
    }

    public AlertStatus getStatus() {
        return status;
    }

    public void setStatus(AlertStatus status) {
        this.status = status;
        if (status == AlertStatus.RESOLVED) {
            this.resolvedAt = LocalDateTime.now();
        }
    }

    public String getTargetRoles() {
        return targetRoles;
    }

    public void setTargetRoles(String targetRoles) {
        this.targetRoles = targetRoles;
    }

    public String getRelatedEntityId() {
        return relatedEntityId;
    }

    public void setRelatedEntityId(String relatedEntityId) {
        this.relatedEntityId = relatedEntityId;
    }

    public String getRelatedEntityType() {
        return relatedEntityType;
    }

    public void setRelatedEntityType(String relatedEntityType) {
        this.relatedEntityType = relatedEntityType;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getResolvedAt() {
        return resolvedAt;
    }

    public void setResolvedAt(LocalDateTime resolvedAt) {
        this.resolvedAt = resolvedAt;
    }

    // Business Logic
    public void updateStatus(AlertStatus newStatus) {
        this.status = newStatus;
        this.updatedAt = LocalDateTime.now();
        if (newStatus == AlertStatus.RESOLVED) {
            this.resolvedAt = LocalDateTime.now();
        }
    }
}
