package com.ensias.portflow;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/client")
@CrossOrigin(origins = "http://localhost:4200")
public class ClientController {
    
    @Autowired
    private ContainerRepository containerRepository;
    
    @Autowired
    private ContainerMovementRepository movementRepository;
    
    @GetMapping("/containers")
    public ResponseEntity<List<Container>> getMyContainers(@RequestParam Long clientId) {
        List<Container> containers = containerRepository.findByClientId(clientId);
        return ResponseEntity.ok(containers);
    }
    
    @GetMapping("/containers/{containerNumber}/track")
    public ResponseEntity<ContainerTrackingResponse> trackContainer(@PathVariable String containerNumber, 
                                                                   @RequestParam Long clientId) {
        Optional<Container> containerOpt = containerRepository.findByContainerNumber(containerNumber);
        
        if (containerOpt.isPresent()) {
            Container container = containerOpt.get();
            
            // Verify ownership
            if (!container.getClient().getId().equals(clientId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            
            ContainerTrackingResponse response = new ContainerTrackingResponse();
            response.setContainer(container);
            response.setCurrentStatus(container.getStatus().toString());
            response.setCurrentLocation(getCurrentLocationDescription(container));
            response.setMovementHistory(movementRepository.findByContainerOrderByTimestampDesc(container));
            response.setEstimatedDeparture(getEstimatedDeparture(container));
            response.setProgressPercentage(calculateProgressPercentage(container));
            
            return ResponseEntity.ok(response);
        }
        
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/containers/{containerNumber}/history")
    public ResponseEntity<ContainerHistoryResponse> getContainerHistory(@PathVariable String containerNumber,
                                                                       @RequestParam Long clientId) {
        Optional<Container> containerOpt = containerRepository.findByContainerNumber(containerNumber);
        
        if (containerOpt.isPresent()) {
            Container container = containerOpt.get();
            
            // Verify ownership
            if (!container.getClient().getId().equals(clientId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            
            ContainerHistoryResponse response = new ContainerHistoryResponse();
            response.setContainerNumber(containerNumber);
            response.setClient(container.getClient().getFirstName() + " " + container.getClient().getLastName());
            response.setOrigin(container.getOrigin());
            response.setDestination(container.getDestination());
            response.setArrivalTime(container.getArrivalTime());
            response.setDepartureTime(container.getDepartureTime());
            response.setFullHistory(movementRepository.findByContainerOrderByTimestampDesc(container));
            response.setDocuments(generateTraceabilityDocuments(container));
            
            return ResponseEntity.ok(response);
        }
        
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/dashboard")
    public ResponseEntity<ClientDashboard> getClientDashboard(@RequestParam Long clientId) {
        List<Container> containers = containerRepository.findByClientId(clientId);
        
        ClientDashboard dashboard = new ClientDashboard();
        dashboard.setTotalContainers(containers.size());
        dashboard.setInTransitContainers(containers.stream()
            .mapToInt(c -> c.getStatus() == ContainerStatus.IN_TRANSIT ? 1 : 0).sum());
        dashboard.setStoredContainers(containers.stream()
            .mapToInt(c -> c.getStatus() == ContainerStatus.STORED ? 1 : 0).sum());
        dashboard.setReadyForPickup(containers.stream()
            .mapToInt(c -> c.getStatus() == ContainerStatus.LOADING ? 1 : 0).sum());
        
        dashboard.setRecentContainers(containers.stream()
            .sorted((a, b) -> b.getArrivalTime().compareTo(a.getArrivalTime()))
            .limit(5)
            .collect(Collectors.toList()));
        
        return ResponseEntity.ok(dashboard);
    }
    
    private String getCurrentLocationDescription(Container container) {
        if (container.getStorageLocation() != null) {
            return "Zone de stockage: " + container.getStorageLocation().getZone().getName() + 
                   " - Emplacement: " + container.getStorageLocation().getLocationCode();
        }
        
        switch (container.getStatus()) {
            case ARRIVING:
                return "En approche du port";
            case UNLOADING:
                return "En cours de déchargement";
            case LOADING:
                return "En cours de chargement";
            case DEPARTED:
                return "Parti du port";
            case IN_TRANSIT:
                return "En transit";
            default:
                return "Position inconnue";
        }
    }
    
    private LocalDateTime getEstimatedDeparture(Container container) {
        if (container.getEscale() != null) {
            return container.getEscale().getEtd();
        }
        return null;
    }
    
    private int calculateProgressPercentage(Container container) {
        switch (container.getStatus()) {
            case ARRIVING: return 10;
            case UNLOADING: return 30;
            case STORED: return 60;
            case LOADING: return 80;
            case DEPARTED: return 100;
            default: return 0;
        }
    }
    
    private List<String> generateTraceabilityDocuments(Container container) {
        List<String> documents = new ArrayList<>();
        documents.add("Bon de réception - " + container.getArrivalTime());
        documents.add("Fiche de stockage - Zone " + 
            (container.getStorageLocation() != null ? container.getStorageLocation().getZone().getName() : "N/A"));
        if (container.getDepartureTime() != null) {
            documents.add("Bon de sortie - " + container.getDepartureTime());
        }
        return documents;
    }
}