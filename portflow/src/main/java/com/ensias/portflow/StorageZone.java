package com.ensias.portflow;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "storage_zones")
public class StorageZone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private String description;
    private Integer totalCapacity;
    private Integer currentOccupancy;
    
    @Enumerated(EnumType.STRING)
    private ZoneType type;
    
    @OneToMany(mappedBy = "zone", cascade = CascadeType.ALL)
    private List<StorageLocation> locations;

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

	public Integer getTotalCapacity() {
		return totalCapacity;
	}

	public void setTotalCapacity(Integer totalCapacity) {
		this.totalCapacity = totalCapacity;
	}

	public Integer getCurrentOccupancy() {
		return currentOccupancy;
	}

	public void setCurrentOccupancy(Integer currentOccupancy) {
		this.currentOccupancy = currentOccupancy;
	}

	public ZoneType getType() {
		return type;
	}

	public void setType(ZoneType type) {
		this.type = type;
	}

	public List<StorageLocation> getLocations() {
		return locations;
	}

	public void setLocations(List<StorageLocation> locations) {
		this.locations = locations;
	}
    
    
}