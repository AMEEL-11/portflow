package com.springprojets.portflow;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/storage")
@CrossOrigin(origins = "http://localhost:4200")
public class StorageController {
    
    @Autowired
    private StorageOptimizationService storageService;
    
    @Autowired
    private StorageZoneRepository storageZoneRepository;
    
    @GetMapping("/zones")
    public ResponseEntity<List<StorageZone>> getStorageZones() {
        return ResponseEntity.ok(storageZoneRepository.findAll());
    }
    
    @PostMapping("/allocate")
    public ResponseEntity<StorageAllocation> allocateStorage(@RequestBody Container container) {
        StorageAllocation allocation = storageService.findOptimalLocation(container);
        return ResponseEntity.ok(allocation);
    }
    
    @GetMapping("/occupancy")
    public ResponseEntity<List<ZoneOccupancyDto>> getZoneOccupancy() {
        List<StorageZone> zones = storageZoneRepository.findAll();
        List<ZoneOccupancyDto> occupancy = zones.stream()
            .map(zone -> new ZoneOccupancyDto(
                zone.getName(),
                zone.getCurrentOccupancy(),
                zone.getTotalCapacity(),
                zone.getReservedSlots()
            ))
            .collect(Collectors.toList());
        return ResponseEntity.ok(occupancy);
    }
}