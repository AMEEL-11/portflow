package com.ensias.portflow;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "escales")
public class Escale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "ship_id")
    private Ship ship;
    
    private LocalDateTime eta; // Estimated Time of Arrival
    private LocalDateTime etd; // Estimated Time of Departure
    private LocalDateTime ata; // Actual Time of Arrival
    private LocalDateTime atd; // Actual Time of Departure
    
    @Enumerated(EnumType.STRING)
    private EscaleStatus status;
    
    private String berthAssigned;
    private Integer expectedContainers;
    
    @OneToMany(mappedBy = "escale", cascade = CascadeType.ALL)
    private List<Container> containers;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Ship getShip() {
		return ship;
	}

	public void setShip(Ship ship) {
		this.ship = ship;
	}

	public LocalDateTime getEta() {
		return eta;
	}

	public void setEta(LocalDateTime eta) {
		this.eta = eta;
	}

	public LocalDateTime getEtd() {
		return etd;
	}

	public void setEtd(LocalDateTime etd) {
		this.etd = etd;
	}

	public LocalDateTime getAta() {
		return ata;
	}

	public void setAta(LocalDateTime ata) {
		this.ata = ata;
	}

	public LocalDateTime getAtd() {
		return atd;
	}

	public void setAtd(LocalDateTime atd) {
		this.atd = atd;
	}

	public EscaleStatus getStatus() {
		return status;
	}

	public void setStatus(EscaleStatus status) {
		this.status = status;
	}

	public String getBerthAssigned() {
		return berthAssigned;
	}

	public void setBerthAssigned(String berthAssigned) {
		this.berthAssigned = berthAssigned;
	}

	public Integer getExpectedContainers() {
		return expectedContainers;
	}

	public void setExpectedContainers(Integer expectedContainers) {
		this.expectedContainers = expectedContainers;
	}

	public List<Container> getContainers() {
		return containers;
	}

	public void setContainers(List<Container> containers) {
		this.containers = containers;
	}
    
}