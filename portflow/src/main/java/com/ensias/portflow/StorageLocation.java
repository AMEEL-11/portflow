package com.ensias.portflow;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "storage_locations")
public class StorageLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "zone_id")
    private StorageZone zone;
    
    private String locationCode; // e.g., "A-01-02"
    private Integer row;
    private Integer bay;
    private Integer tier;
    
    @Enumerated(EnumType.STRING)
    private LocationStatus status;
    
    private boolean reserved;
    private LocalDateTime reservedUntil;
    
    @OneToOne(mappedBy = "storageLocation")
    private Container container;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public StorageZone getZone() {
		return zone;
	}

	public void setZone(StorageZone zone) {
		this.zone = zone;
	}

	public String getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	public Integer getRow() {
		return row;
	}

	public void setRow(Integer row) {
		this.row = row;
	}

	public Integer getBay() {
		return bay;
	}

	public void setBay(Integer bay) {
		this.bay = bay;
	}

	public Integer getTier() {
		return tier;
	}

	public void setTier(Integer tier) {
		this.tier = tier;
	}

	public LocationStatus getStatus() {
		return status;
	}

	public void setStatus(LocationStatus status) {
		this.status = status;
	}

	public boolean isReserved() {
		return reserved;
	}

	public void setReserved(boolean reserved) {
		this.reserved = reserved;
	}

	public LocalDateTime getReservedUntil() {
		return reservedUntil;
	}

	public void setReservedUntil(LocalDateTime reservedUntil) {
		this.reservedUntil = reservedUntil;
	}

	public Container getContainer() {
		return container;
	}

	public void setContainer(Container container) {
		this.container = container;
	}
    
    
}
