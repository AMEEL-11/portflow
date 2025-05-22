package com.springprojets.portflow;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "container_movements")
public class ContainerMovement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Container is required")
    @ManyToOne
    @JoinColumn(name = "container_id")
    private Container container;

    @NotNull(message = "Movement date is required")
    private LocalDateTime movementDate;

    private String fromLocation;
    private String toLocation;

    @NotNull(message = "Movement type is required")
    private String movementType; // e.g., "LOAD", "UNLOAD", "TRANSFER"

    private String operator;
    private String equipment;
    private String notes;

    // Constructors
    public ContainerMovement() {}

    public ContainerMovement(Container container, String fromLocation, String toLocation, String movementType) {
        this.container = container;
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
        this.movementType = movementType;
        this.movementDate = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Container getContainer() {
        return container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }

    public LocalDateTime getMovementDate() {
        return movementDate;
    }

    public void setMovementDate(LocalDateTime movementDate) {
        this.movementDate = movementDate;
    }

    public String getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    public String getMovementType() {
        return movementType;
    }

    public void setMovementType(String movementType) {
        this.movementType = movementType;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
} 