package com.ensias.portflow;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "performance_indicators")
public class PerformanceIndicator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private String description;
    private Double value;
    private String unit;
    private LocalDate calculationDate;
    
    @Enumerated(EnumType.STRING)
    private KPIType type;
    
    // Getters and Setters
}
