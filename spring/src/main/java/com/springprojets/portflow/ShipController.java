package com.springprojets.portflow;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ships")
@CrossOrigin(origins = "http://localhost:4200")
public class ShipController {
    
    @Autowired
    private ShipTrackingService shipTrackingService;
    
    @GetMapping("/active")
    public ResponseEntity<List<Ship>> getActiveShips() {
        return ResponseEntity.ok(shipTrackingService.getActiveShips());
    }
    
    @GetMapping("/tracking")
    public ResponseEntity<List<ShipTrackingDto>> getShipTracking() {
        List<Ship> ships = shipTrackingService.getActiveShips();
        List<ShipTrackingDto> tracking = ships.stream()
            .map(this::mapToTrackingDto)
            .collect(Collectors.toList());
        return ResponseEntity.ok(tracking);
    }
    
    private ShipTrackingDto mapToTrackingDto(Ship ship) {
        return ShipTrackingDto.builder()
            .shipId(ship.getId())
            .name(ship.getName())
            .imoNumber(ship.getImoNumber())
            .latitude(ship.getLatitude())
            .longitude(ship.getLongitude())
            .speed(ship.getSpeed())
            .status(ship.getStatus())
            .lastUpdate(ship.getLastLocationUpdate())
            .currentBerthName(ship.getCurrentBerth() != null ? ship.getCurrentBerth().getName() : null)
            .build();
    }
}