package com.ensias.portflow;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "containers")
public class Container {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true)
    private String containerNumber;
    
    @Enumerated(EnumType.STRING)
    private ContainerType type;
    
    @Enumerated(EnumType.STRING)
    private ContainerStatus status;
    
    @ManyToOne
    @JoinColumn(name = "escale_id")
    private Escale escale;
    
    @ManyToOne
    @JoinColumn(name = "client_id")
    private User client;
    
    @ManyToOne
    @JoinColumn(name = "storage_location_id")
    private StorageLocation storageLocation;
    
    // RFID/GPS Data
    private String rfidTag;
    private Double currentLatitude;
    private Double currentLongitude;
    private LocalDateTime lastLocationUpdate;
    
    // Container Details
    private String destination;
    private String origin;
    private Double weight;
    private boolean refrigerated;
    private boolean hazardous;
    
    private LocalDateTime arrivalTime;
    private LocalDateTime departureTime;
    
    @OneToMany(mappedBy = "container", cascade = CascadeType.ALL)
    private List<ContainerMovement> movements;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContainerNumber() {
		return containerNumber;
	}

	public void setContainerNumber(String containerNumber) {
		this.containerNumber = containerNumber;
	}

	public ContainerType getType() {
		return type;
	}

	public void setType(ContainerType type) {
		this.type = type;
	}

	public ContainerStatus getStatus() {
		return status;
	}

	public void setStatus(ContainerStatus status) {
		this.status = status;
	}

	public Escale getEscale() {
		return escale;
	}

	public void setEscale(Escale escale) {
		this.escale = escale;
	}

	public User getClient() {
		return client;
	}

	public void setClient(User client) {
		this.client = client;
	}

	public StorageLocation getStorageLocation() {
		return storageLocation;
	}

	public void setStorageLocation(StorageLocation storageLocation) {
		this.storageLocation = storageLocation;
	}

	public String getRfidTag() {
		return rfidTag;
	}

	public void setRfidTag(String rfidTag) {
		this.rfidTag = rfidTag;
	}

	public Double getCurrentLatitude() {
		return currentLatitude;
	}

	public void setCurrentLatitude(Double currentLatitude) {
		this.currentLatitude = currentLatitude;
	}

	public Double getCurrentLongitude() {
		return currentLongitude;
	}

	public void setCurrentLongitude(Double currentLongitude) {
		this.currentLongitude = currentLongitude;
	}

	public LocalDateTime getLastLocationUpdate() {
		return lastLocationUpdate;
	}

	public void setLastLocationUpdate(LocalDateTime lastLocationUpdate) {
		this.lastLocationUpdate = lastLocationUpdate;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public boolean isRefrigerated() {
		return refrigerated;
	}

	public void setRefrigerated(boolean refrigerated) {
		this.refrigerated = refrigerated;
	}

	public boolean isHazardous() {
		return hazardous;
	}

	public void setHazardous(boolean hazardous) {
		this.hazardous = hazardous;
	}

	public LocalDateTime getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(LocalDateTime arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public LocalDateTime getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(LocalDateTime departureTime) {
		this.departureTime = departureTime;
	}

	public List<ContainerMovement> getMovements() {
		return movements;
	}

	public void setMovements(List<ContainerMovement> movements) {
		this.movements = movements;
	}
    
    
}
