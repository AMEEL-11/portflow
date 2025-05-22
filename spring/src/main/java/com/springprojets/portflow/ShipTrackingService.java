package com.springprojets.portflow;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.*;
import org.springframework.scheduling.annotation.*;
import org.springframework.stereotype.*;

@Service
public class ShipTrackingService {
    
    @Autowired
    private ShipRepository shipRepository;
    
    // Simulation des données AIS/GPS
    @Scheduled(fixedRate = 30000) // Toutes les 30 secondes
    public void updateShipPositions() {
        List<Ship> activeShips = shipRepository.findByStatus(ShipStatus.EN_ROUTE);
        
        for (Ship ship : activeShips) {
            // Simulation du mouvement
            Double newLat = ship.getLatitude() + (Math.random() - 0.5) * 0.01;
            Double newLng = ship.getLongitude() + (Math.random() - 0.5) * 0.01;
            Double newSpeed = Math.random() * 20; // 0-20 knots
            
            ship.setLatitude(newLat);
            ship.setLongitude(newLng);
            ship.setSpeed(newSpeed);
            ship.setLastLocationUpdate(LocalDateTime.now());
        }
        
        shipRepository.saveAll(activeShips);
    }
    
    public List<Ship> getActiveShips() {
        return shipRepository.findByStatusIn(
            Arrays.asList(ShipStatus.EN_ROUTE, ShipStatus.ANCHORED, ShipStatus.DOCKED)
        );
    }
}
