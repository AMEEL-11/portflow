package com.springprojets.portflow;

import java.util.List;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Service
public class StorageOptimizationService {
    
    private static final int MAX_STACK_HEIGHT = 4;
    private static final int MAX_ROWS = 10;
    private static final int MAX_COLUMNS = 10;
    
    @Autowired
    private StorageZoneRepository storageZoneRepository;
    
    /**
     * Algorithme d'allocation optimale des emplacements:
     * 1. Prioriser les zones avec le moins d'occupation
     * 2. Considérer la proximité du quai d'arrivée
     * 3. Grouper les conteneurs du même client
     * 4. Optimiser l'accessibilité pour les futures opérations
     */
    public StorageAllocation findOptimalLocation(Container container) {
        List<StorageZone> availableZones = storageZoneRepository.findAll().stream()
            .filter(zone -> zone.getCurrentOccupancy() < zone.getTotalCapacity())
            .toList();
        
        if (availableZones.isEmpty()) {
            throw new RuntimeException("No storage space available");
        }
        
        StorageZone optimalZone = availableZones.stream()
            .min((z1, z2) -> {
                // Score basé sur l'occupation et la proximité
                double score1 = calculateZoneScore(z1, container);
                double score2 = calculateZoneScore(z2, container);
                return Double.compare(score1, score2);
            })
            .orElseThrow(() -> new RuntimeException("No storage space available"));
            
        // Calculer la position exacte dans la zone
        StoragePosition position = calculateOptimalPosition(optimalZone, container);
        
        return new StorageAllocation(optimalZone, position);
    }
    
    private double calculateZoneScore(StorageZone zone, Container container) {
        double occupancyRate = (double) zone.getCurrentOccupancy() / zone.getTotalCapacity();
        double proximityScore = calculateProximityToBerth(zone, container.getEscale() != null ? container.getEscale().getBerth() : null);
        
        // Plus le score est bas, mieux c'est
        return occupancyRate * 0.7 + proximityScore * 0.3;
    }
    
    private double calculateProximityToBerth(StorageZone zone, Berth berth) {
        if (berth == null || zone.getLocation() == null) {
            return 1.0; // Score maximum si pas de quai ou pas de localisation
        }
        
        // Simplified proximity calculation based on location string comparison
        // In a real system, this would use actual coordinates and distance calculation
        return zone.getLocation().equals(berth.getLocation()) ? 0.0 : 1.0;
    }
    
    private StoragePosition calculateOptimalPosition(StorageZone zone, Container container) {
        int currentOccupancy = zone.getCurrentOccupancy();
        
        // Adjust max stack height based on container type and weight
        int maxHeight = getMaxStackHeight(container);
        
        // Calculate base position
        int baseRow = (currentOccupancy / (MAX_COLUMNS * maxHeight)) % MAX_ROWS;
        int baseColumn = (currentOccupancy / maxHeight) % MAX_COLUMNS;
        
        // Calculate level based on weight constraints
        int level = calculateOptimalLevel(zone, container, maxHeight);
        
        // Adjust position based on container type
        if (container.getType() == ContainerType.REFRIGERATED) {
            // Place refrigerated containers near power sources (assumed to be at the edges)
            baseColumn = baseColumn < MAX_COLUMNS / 2 ? 0 : MAX_COLUMNS - 1;
        } else if (container.getType() == ContainerType.DANGEROUS) {
            // Place dangerous goods in designated areas (assumed to be at the back)
            baseRow = MAX_ROWS - 1;
        }
        
        return new StoragePosition(baseRow, baseColumn, level);
    }
    
    private int getMaxStackHeight(Container container) {
        // Reduce max stack height for heavy or special containers
        if (container.getWeight() > 25000) { // 25 tonnes
            return 2;
        } else if (container.getType() == ContainerType.DANGEROUS) {
            return 2;
        } else if (container.getType() == ContainerType.REFRIGERATED) {
            return 3;
        }
        return MAX_STACK_HEIGHT;
    }
    
    private int calculateOptimalLevel(StorageZone zone, Container container, int maxHeight) {
        // Heavier containers should be placed lower
        if (container.getWeight() > 20000) { // 20 tonnes
            return 0;
        } else if (container.getWeight() > 15000) { // 15 tonnes
            return Math.min(1, maxHeight - 1);
        }
        
        // For lighter containers, use zone's current occupancy to determine level
        return zone.getCurrentOccupancy() % maxHeight;
    }
}
