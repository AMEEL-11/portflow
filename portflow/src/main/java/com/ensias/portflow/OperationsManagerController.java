package com.ensias.portflow;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ensias.portflow.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/api/operations-manager")
@CrossOrigin(origins = "http://localhost:4200")
public class OperationsManagerController {
    
    private static final String ROLE_OPERATIONS_MANAGER = "OPERATIONS_MANAGER";
    
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
        String searchQuery = query.toUpperCase();
        List<Container> containers = containerRepository.findAll().stream()
            .filter(c -> c.getContainerNumber().toUpperCase().contains(searchQuery) || 
                        (c.getClient() != null && c.getClient().getEmail().toUpperCase().contains(searchQuery)) || 
                        (c.getDestination() != null && c.getDestination().toUpperCase().contains(searchQuery)))
            .collect(Collectors.toList());
        return ResponseEntity.ok(containers);
    }
    
    @GetMapping("/containers/{id}/details")
    public ResponseEntity<ContainerDetailsResponse> getContainerDetails(@PathVariable Long id) {
        Container container = containerRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Container not found with id: " + id));
        
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
        
        dashboard.setTotalContainers(containerRepository.count());
        dashboard.setActiveShips(escaleRepository.countByStatus(EscaleStatus.DOCKED));
        dashboard.setPendingOperations(containerRepository.countByStatus(ContainerStatus.UNLOADING) + 
                                     containerRepository.countByStatus(ContainerStatus.LOADING));
        dashboard.setStorageOccupancy(calculateOverallStorageOccupancy());
        
        dashboard.setRecentMovements(getRecentMovements());
        dashboard.setUpcomingDepartures(getUpcomingDepartures());
        
        dashboard.setUnacknowledgedAlerts(alertService.getUnacknowledgedAlertsForRole(ROLE_OPERATIONS_MANAGER));
        
        return ResponseEntity.ok(dashboard);
    }
    
    @GetMapping("/alerts")
    public ResponseEntity<List<Alert>> getAllAlerts() {
        return ResponseEntity.ok(alertService.getUnacknowledgedAlertsForRole(ROLE_OPERATIONS_MANAGER));
    }
    
    @PostMapping("/alerts/{id}/acknowledge")
    public ResponseEntity<Void> acknowledgeAlert(@PathVariable Long id, @RequestParam String acknowledgedBy) {
        alertService.acknowledgeAlert(id, acknowledgedBy);
        return ResponseEntity.noContent().build();
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
        if (container == null || container.getId() == null) {
            return new ArrayList<>();
        }
        System.out.println("TODO: Implement getContainerMovements for container ID: " + container.getId());
        return new ArrayList<>(); 
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
        
        long totalCapacity = zones.stream().mapToLong(StorageZone::getTotalCapacity).sum();
        long totalOccupied = zones.stream().mapToLong(StorageZone::getCurrentOccupancy).sum();
        
        return totalCapacity > 0 ? (double) totalOccupied / totalCapacity * 100 : 0.0;
    }
    
    private List<ContainerMovement> getRecentMovements() {
        System.out.println("TODO: Implement getRecentMovements");
        return new ArrayList<>(); 
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
        if (container.getStatus() != null) {
            data.setStatus(container.getStatus().toString());
        }
        if (container.getType() != null) {
            data.setType(container.getType().toString());
        }
        return data;
    }
    
    private StorageZoneDTO convertZoneToDTO(StorageZone zone) {
        StorageZoneDTO dto = new StorageZoneDTO();
        dto.setId(zone.getId());
        dto.setName(zone.getName());
        if (zone.getType() != null) {
            dto.setType(zone.getType());
        }
        dto.setTotalCapacity(zone.getTotalCapacity());
        dto.setCurrentOccupancy(zone.getCurrentOccupancy());
        if (zone.getTotalCapacity() != null && zone.getTotalCapacity() > 0) {
            dto.setOccupancyRate((double) zone.getCurrentOccupancy() / zone.getTotalCapacity() * 100);
        } else {
            dto.setOccupancyRate(0.0);
        }
        return dto;
    }
}