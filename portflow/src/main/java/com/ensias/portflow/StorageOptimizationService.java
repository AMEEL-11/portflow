package com.ensias.portflow;

import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StorageOptimizationService {
    
    @Autowired
    private StorageLocationRepository locationRepository;
    
    @Autowired
    private ContainerRepository containerRepository;
    
    /**
     * Algorithme d'optimisation pour l'allocation d'emplacement:
     * 1. Prioriser par type de conteneur (réfrigéré, dangereux, standard)
     * 2. Minimiser la distance par rapport au quai d'origine
     * 3. Grouper par destination finale
     * 4. Considérer la durée de séjour prévue
     * 5. Optimiser l'accessibilité pour les équipements
     */
    public StorageLocation findOptimalLocation(Container container) {
        // Déterminer le type de zone requis
        ZoneType requiredZoneType = determineRequiredZoneType(container);
        
        // Obtenir les emplacements disponibles du bon type
        List<StorageLocation> availableLocations = locationRepository
            .findAvailableByZoneType(requiredZoneType, LocationStatus.AVAILABLE);
        
        if (availableLocations.isEmpty()) {
            throw new RuntimeException("No available storage locations for container type: " + requiredZoneType);
        }
        
        // Appliquer l'algorithme de scoring
        return availableLocations.stream()
            .max(Comparator.comparing(location -> calculateLocationScore(container, location)))
            .orElseThrow(() -> new RuntimeException("Unable to calculate optimal location"));
    }
    
    private ZoneType determineRequiredZoneType(Container container) {
        if (container.isRefrigerated()) {
            return ZoneType.REFRIGERATED;
        } else if (container.isHazardous()) {
            return ZoneType.HAZARDOUS;
        } else if (container.getType() == ContainerType.OPEN_TOP || 
                   container.getType() == ContainerType.FLAT_RACK) {
            return ZoneType.OVERSIZE;
        }
        return ZoneType.STANDARD;
    }
    
    private double calculateLocationScore(Container container, StorageLocation location) {
        double score = 0.0;
        
        // 1. Accessibilité (proximité des équipements de manutention)
        score += calculateAccessibilityScore(location);
        
        // 2. Regroupement par destination
        score += calculateDestinationGroupingScore(container, location);
        
        // 3. Durée de séjour (conteneurs courte durée près des sorties)
        score += calculateDwellTimeScore(container, location);
        
        // 4. Minimisation des remaniements futurs
        score += calculateRestowMinimizationScore(container, location);
        
        return score;
    }
    
    private double calculateAccessibilityScore(StorageLocation location) {
        // Logic to calculate accessibility based on location position
        // Closer to main lanes = higher score
        return 10.0 - (location.getRow() * 0.5 + location.getBay() * 0.3);
    }
    
    private double calculateDestinationGroupingScore(Container container, StorageLocation location) {
        // Check if nearby containers have same destination
        // This would require additional queries to find nearby containers
        return 5.0; // Simplified for now
    }
    
    private double calculateDwellTimeScore(Container container, StorageLocation location) {
        // Containers with shorter expected dwell time should be closer to exit gates
        if (container.getDepartureTime() != null && container.getArrivalTime() != null) {
            long dwellHours = ChronoUnit.HOURS.between(container.getArrivalTime(), container.getDepartureTime());
            if (dwellHours < 48) { // Short dwell time
                return 15.0 - location.getRow(); // Prefer locations closer to gates
            }
        }
        return 0.0;
    }
    
    private double calculateRestowMinimizationScore(Container container, StorageLocation location) {
        // Logic to minimize future restows by considering stacking order
        return 3.0; // Simplified
    }
}
