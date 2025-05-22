package com.ensias.portflow;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/operations-manager")
@CrossOrigin(origins = "http://localhost:4200")
public class OperationsManagerController {
    
    @Autowired
    private ContainerRepository containerRepository;
    
    @Autowired
    private AlertService alertService;
    
    @Autowired
    private EscaleRepository escaleRepository;
    
    @Autowired
    private StorageZoneRepository zoneRepository;
    
    @GetMapping("/containers")
    public ResponseEntity<List<Container>> getAllContainers() {
        return ResponseEntity.ok(containerRepository.findAll());
    }
    
    @GetMapping("/containers/search")
    public ResponseEntity<List<Container>> searchContainers(@RequestParam String query) {
        List<Container> containers = containerRepository.findAll().stream()
            .filter(c -> c.getContainerNumber().contains(query.toUpperCase()) ||
                        (c.getClient() != null && c.getClient().getEmail().contains(query)) ||
                        (c.getDestination() != null && c.getDestination().contains(query)))
            .collect(Collectors.toList());
        return ResponseEntity.ok(containers);
    }
    
    @GetMapping("/containers/{id}/details")
    public ResponseEntity<ContainerDetailsResponse> getContainerDetails(@PathVariable Long id) {
        Container container = containerRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Container not found"));
        
        ContainerDetailsResponse response = new ContainerDetailsResponse();
        response.setContainer(container);
        response.setMovements(getContainerMovements(container));
        response.setCurrentLocation(getCurrentLocation(container));
        response.setEstimatedDeparture(getEstimatedDeparture(container));
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/dashboard")
    public ResponseEntity<OperationsDashboard> getDashboard() {
        OperationsDashboard dashboard = new OperationsDashboard();
        
        // Current statistics
        dashboard.setTotalContainers(containerRepository.count());
        dashboard.setActiveShips(escaleRepository.countByStatus(EscaleStatus.DOCKED));
        dashboard.setPendingOperations(containerRepository.countByStatus(ContainerStatus.UNLOADING) + 
                                     containerRepository.countByStatus(ContainerStatus.LOADING));
        dashboard.setStorageOccupancy(calculateOverallStorageOccupancy());
        
        // Recent activities
        dashboard.setRecentMovements(getRecentMovements());
        dashboard.setUpcomingDepartures(getUpcomingDepartures());
        
        // Alerts
        dashboard.setUnacknowledgedAlerts(alertService.getUnacknowledgedAlertsForRole("OPERATIONS_MANAGER"));
        
        return ResponseEntity.ok(dashboard);
    }
    
    @GetMapping("/alerts")
    public ResponseEntity<List<Alert>> getAllAlerts() {
        return ResponseEntity.ok(alertService.getUnacknowledgedAlertsForRole("OPERATIONS_MANAGER"));
    }
    
    @PostMapping("/alerts/{id}/acknowledge")
    public ResponseEntity<String> acknowledgeAlert(@PathVariable Long id, @RequestParam String acknowledgedBy) {
        alertService.acknowledgeAlert(id, acknowledgedBy);
        return ResponseEntity.ok("Alert acknowledged");
    }
    
    @GetMapping("/map/containers")
    public ResponseEntity<List<ContainerMapData>> getContainersForMap() {
        List<Container> containers = containerRepository.findAll();
        List<ContainerMapData> mapData = containers.stream()
            .filter(c -> c.getCurrentLatitude() != null && c.getCurrentLongitude() != null)
            .map(this::convertToMapData)
            .collect(Collectors.toList());
        return ResponseEntity.ok(mapData);
    }
    
    @GetMapping("/storage-overview")
    public ResponseEntity<List<StorageZoneDTO>> getStorageOverview() {
        List<StorageZone> zones = zoneRepository.findAll();
        List<StorageZoneDTO> overview = zones.stream()
            .map(this::convertZoneToDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(overview);
    }
    
    private List<ContainerMovement> getContainerMovements(Container container) {
        // Implementation to get container movements
        return new ArrayList<>(); // Placeholder
    }
    
    private String getCurrentLocation(Container container) {
        if (container.getStorageLocation() != null) {
            return container.getStorageLocation().getLocationCode();
        }
        return "En transit";
    }
    
    private LocalDateTime getEstimatedDeparture(Container container) {
        if (container.getEscale() != null) {
            return container.getEscale().getEtd();
        }
        return null;
    }
    
    private double calculateOverallStorageOccupancy() {
        List<StorageZone> zones = zoneRepository.findAll();
        if (zones.isEmpty()) return 0.0;
        
        int totalCapacity = zones.stream().mapToInt(StorageZone::getTotalCapacity).sum();
        int totalOccupied = zones.stream().mapToInt(StorageZone::getCurrentOccupancy).sum();
        
        return totalCapacity > 0 ? (double) totalOccupied / totalCapacity * 100 : 0.0;
    }
    
    private List<ContainerMovement> getRecentMovements() {
        // Get recent movements from last 24 hours
        return new ArrayList<>(); // Placeholder
    }
    
    private List<Container> getUpcomingDepartures() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime next24Hours = now.plusHours(24);
        
        return containerRepository.findAll().stream()
            .filter(c -> c.getEscale() != null && 
                        c.getEscale().getEtd() != null &&
                        c.getEscale().getEtd().isAfter(now) && 
                        c.getEscale().getEtd().isBefore(next24Hours))
            .collect(Collectors.toList());
    }
    
    private ContainerMapData convertToMapData(Container container) {
        ContainerMapData data = new ContainerMapData();
        data.setId(container.getId());
        data.setContainerNumber(container.getContainerNumber());
        data.setLatitude(container.getCurrentLatitude());
        data.setLongitude(container.getCurrentLongitude());
        data.setStatus(container.getStatus().toString());
        data.setType(container.getType().toString());
        return data;
    }
    
    private StorageZoneDTO convertZoneToDTO(StorageZone zone) {
        StorageZoneDTO dto = new StorageZoneDTO();
        dto.setId(zone.getId());
        dto.setName(zone.getName());
        dto.setType(zone.getType());
        dto.setTotalCapacity(zone.getTotalCapacity());
        dto.setCurrentOccupancy(zone.getCurrentOccupancy());
        dto.setOccupancyRate((double) zone.getCurrentOccupancy() / zone.getTotalCapacity() * 100);
        return dto;
    }
}