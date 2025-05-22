package com.ensias.portflow;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "container_movements")
public class ContainerMovement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "container_id")
    private Container container;
    
    private LocalDateTime timestamp;
    private String fromLocation;
    private String toLocation;
    
    @Enumerated(EnumType.STRING)
    private MovementType type;
    
    private String equipment; // Crane, truck, etc.
    private String operator;
    
    // Getters and Setters
}