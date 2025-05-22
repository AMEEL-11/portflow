package com.ensias.portflow;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/yard-planner")
@CrossOrigin(origins = "http://localhost:4200")
public class YardPlannerController {
    
    @Autowired
    private ContainerRepository containerRepository;
    
    @Autowired
    private StorageLocationRepository locationRepository;
    
    @Autowired
    private StorageZoneRepository zoneRepository;
    
    @Autowired
    private StorageOptimizationService optimizationService;
    
    @Autowired
    private EscaleRepository escaleRepository;
    
    @GetMapping("/storage-zones")
    public ResponseEntity<List<StorageZoneDTO>> getStorageZones() {
        List<StorageZone> zones = zoneRepository.findAll();
        List<StorageZoneDTO> zoneDTOs = zones.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(zoneDTOs);
    }
    
    @GetMapping("/containers/pending-assignment")
    public ResponseEntity<List<Container>> getPendingAssignmentContainers() {
        List<Container> containers = containerRepository.findByStatus(ContainerStatus.UNLOADING);
        return ResponseEntity.ok(containers);
    }
    
    @PostMapping("/containers/{containerId}/suggest-location")
    public ResponseEntity<LocationSuggestionResponse> suggestOptimalLocation(@PathVariable Long containerId) {
        Container container = containerRepository.findById(containerId)
            .orElseThrow(() -> new RuntimeException("Container not found"));
        
        try {
            StorageLocation optimalLocation = optimizationService.findOptimalLocation(container);
            
            LocationSuggestionResponse response = new LocationSuggestionResponse();
            response.setSuggestedLocation(optimalLocation);
            response.setReason(generateSuggestionReason(container, optimalLocation));
            response.setAlternatives(findAlternativeLocations(container, optimalLocation));
            
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    
    @PostMapping("/containers/{containerId}/assign-location")
    public ResponseEntity<Container> assignLocation(@PathVariable Long containerId, 
                                                   @RequestBody AssignLocationRequest request) {
        Container container = containerRepository.findById(containerId)
            .orElseThrow(() -> new RuntimeException("Container not found"));
        
        StorageLocation location = locationRepository.findById(request.getLocationId())
            .orElseThrow(() -> new RuntimeException("Location not found"));
        
        // Check if location is available
        if (location.getStatus() != LocationStatus.AVAILABLE) {
            throw new RuntimeException("Location is not available");
        }
        
        // Assign container to location
        container.setStorageLocation(location);
        container.setStatus(ContainerStatus.STORED);
        location.setStatus(LocationStatus.OCCUPIED);
        
        containerRepository.save(container);
        locationRepository.save(location);
        
        // Create movement record
        createMovementRecord(container, "YARD_IN", location.getLocationCode());
        
        return ResponseEntity.ok(container);
    }
    
    @GetMapping("/storage-map")
    public ResponseEntity<StorageMapResponse> getStorageMap() {
        List<StorageZone> zones = zoneRepository.findAll();
        StorageMapResponse response = new StorageMapResponse();
        
        List<ZoneMapData> zoneMapData = zones.stream()
            .map(zone -> {
                ZoneMapData data = new ZoneMapData();
                data.setZone(zone);
                data.setLocations(locationRepository.findByZoneId(zone.getId()));
                data.setOccupancyRate(calculateOccupancyRate(zone));
                return data;
            })
            .collect(Collectors.toList());
        
        response.setZones(zoneMapData);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/alerts")
    public ResponseEntity<List<Alert>> getYardAlerts() {
        return ResponseEntity.ok(alertService.getUnacknowledgedAlertsForRole("YARD_PLANNER"));
    }
    
    private StorageZoneDTO convertToDTO(StorageZone zone) {
        StorageZoneDTO dto = new StorageZoneDTO();
        dto.setId(zone.getId());
        dto.setName(zone.getName());
        dto.setType(zone.getType());
        dto.setTotalCapacity(zone.getTotalCapacity());
        dto.setCurrentOccupancy(zone.getCurrentOccupancy());
        dto.setOccupancyRate(calculateOccupancyRate(zone));
        dto.setAvailableLocations(countAvailableLocations(zone));
        return dto;
    }
    
    private double calculateOccupancyRate(StorageZone zone) {
        if (zone.getTotalCapacity() == 0) return 0.0;
        return (double) zone.getCurrentOccupancy() / zone.getTotalCapacity() * 100;
    }
    
    private int countAvailableLocations(StorageZone zone) {
        return locationRepository.findByZoneIdAndStatus(zone.getId(), LocationStatus.AVAILABLE).size();
    }
    
    private String generateSuggestionReason(Container container, StorageLocation location) {
        StringBuilder reason = new StringBuilder();
        reason.append("Emplacement optimal basé sur: ");
        
        if (container.isRefrigerated()) {
            reason.append("Zone réfrigérée requise, ");
        }
        if (container.isHazardous()) {
            reason.append("Zone sécurisée pour marchandises dangereuses, ");
        }
        
        reason.append("Proximité optimale des équipements de manutention, ");
        reason.append("Minimisation des remaniements futurs.");
        
        return reason.toString();
    }
    
    private List<StorageLocation> findAlternativeLocations(Container container, StorageLocation optimal) {
        ZoneType requiredType = determineRequiredZoneType(container);
        return locationRepository.findAvailableByZoneType(requiredType, LocationStatus.AVAILABLE)
            .stream()
            .filter(loc -> !loc.getId().equals(optimal.getId()))
            .limit(3)
            .collect(Collectors.toList());
    }
    
    private ZoneType determineRequiredZoneType(Container container) {
        if (container.isRefrigerated()) return ZoneType.REFRIGERATED;
        if (container.isHazardous()) return ZoneType.HAZARDOUS;
        if (container.getType() == ContainerType.OPEN_TOP || 
            container.getType() == ContainerType.FLAT_RACK) return ZoneType.OVERSIZE;
        return ZoneType.STANDARD;
    }
    
    private void createMovementRecord(Container container, String movementType, String location) {
        ContainerMovement movement = new ContainerMovement();
        movement.setContainer(container);
        movement.setTimestamp(LocalDateTime.now());
        movement.setToLocation(location);
        movement.setType(MovementType.valueOf(movementType));
        // Save movement record (assuming repository exists)
    }
}
