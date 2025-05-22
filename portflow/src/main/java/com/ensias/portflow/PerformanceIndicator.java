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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public LocalDate getCalculationDate() {
		return calculationDate;
	}

	public void setCalculationDate(LocalDate calculationDate) {
		this.calculationDate = calculationDate;
	}

	public KPIType getType() {
		return type;
	}

	public void setType(KPIType type) {
		this.type = type;
	}
    
    
}
