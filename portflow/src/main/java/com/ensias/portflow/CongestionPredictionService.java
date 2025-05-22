package com.ensias.portflow;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CongestionPredictionService {
    
    @Autowired
    private EscaleRepository escaleRepository;
    
    @Autowired
    private ContainerRepository containerRepository;
    
    @Autowired
    private StorageZoneRepository zoneRepository;
    
    public CongestionPrediction predictCongestion(int daysAhead) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime futureDate = now.plusDays(daysAhead);
        
        CongestionPrediction prediction = new CongestionPrediction();
        prediction.setPredictionDate(futureDate.toLocalDate());
        prediction.setDaysAhead(daysAhead);
        
        // Prédiction basée sur les escales prévues
        List<Escale> upcomingEscales = escaleRepository.findByEtaBetween(now, futureDate);
        int expectedContainers = upcomingEscales.stream()
            .mapToInt(Escale::getExpectedContainers)
            .sum();
        
        // Calcul de la capacité disponible
        int totalCapacity = zoneRepository.findAll().stream()
            .mapToInt(StorageZone::getTotalCapacity)
            .sum();
        
        int currentOccupancy = zoneRepository.findAll().stream()
            .mapToInt(StorageZone::getCurrentOccupancy)
            .sum();
        
        int availableCapacity = totalCapacity - currentOccupancy;
        
        // Niveau de risque de congestion
        double congestionRisk = (double) expectedContainers / availableCapacity;
        
        if (congestionRisk > 0.9) {
            prediction.setRiskLevel(RiskLevel.HIGH);
            prediction.setMessage("Risque élevé de congestion - Capacité critique");
        } else if (congestionRisk > 0.7) {
            prediction.setRiskLevel(RiskLevel.MEDIUM);
            prediction.setMessage("Risque modéré de congestion - Surveillance requise");
        } else {
            prediction.setRiskLevel(RiskLevel.LOW);
            prediction.setMessage("Risque faible de congestion - Capacité suffisante");
        }
        
        prediction.setExpectedContainers(expectedContainers);
        prediction.setAvailableCapacity(availableCapacity);
        prediction.setCongestionPercentage(Math.min(congestionRisk * 100, 100));
        
        // Recommandations
        prediction.setRecommendations(generateCongestionRecommendations(prediction));
        
        // Analyse détaillée par zone
        prediction.setZoneAnalysis(analyzeZoneCongestion(upcomingEscales));
        
        return prediction;
    }
    
    private List<String> generateCongestionRecommendations(CongestionPrediction prediction) {
        List<String> recommendations = new ArrayList<>();
        
        switch (prediction.getRiskLevel()) {
            case HIGH:
                recommendations.add("Accélérer l'évacuation des conteneurs en stock");
                recommendations.add("Reporter certaines escales non critiques");
                recommendations.add("Optimiser les zones de stockage temporaire");
                recommendations.add("Augmenter les équipes de manutention");
                break;
            case MEDIUM:
                recommendations.add("Surveiller l'évolution des arrivées");
                recommendations.add("Préparer des zones de stockage supplémentaires");
                recommendations.add("Coordonner avec les clients pour l'évacuation rapide");
                break;
            case LOW:
                recommendations.add("Maintenir le rythme opérationnel actuel");
                recommendations.add("Profiter de la période pour la maintenance préventive");
                break;
        }
        
        return recommendations;
    }
    
    private List<ZoneCongestionAnalysis> analyzeZoneCongestion(List<Escale> upcomingEscales) {
        List<ZoneCongestionAnalysis> analyses = new ArrayList<>();
        List<StorageZone> zones = zoneRepository.findAll();
        
        for (StorageZone zone : zones) {
            ZoneCongestionAnalysis analysis = new ZoneCongestionAnalysis();
            analysis.setZoneName(zone.getName());
            analysis.setZoneType(zone.getType());
            analysis.setCurrentOccupancy(zone.getCurrentOccupancy());
            analysis.setTotalCapacity(zone.getTotalCapacity());
            
            // Estimer les conteneurs entrants pour cette zone
            int expectedContainersForZone = estimateContainersForZone(upcomingEscales, zone.getType());
            analysis.setExpectedIncoming(expectedContainersForZone);
            
            int availableSpace = zone.getTotalCapacity() - zone.getCurrentOccupancy();
            double zoneRisk = (double) expectedContainersForZone / availableSpace;
            
            if (zoneRisk > 0.9) {
                analysis.setRiskLevel(RiskLevel.HIGH);
            } else if (zoneRisk > 0.7) {
                analysis.setRiskLevel(RiskLevel.MEDIUM);
            } else {
                analysis.setRiskLevel(RiskLevel.LOW);
            }
            
            analyses.add(analysis);
        }
        
        return analyses;
    }
    
    private int estimateContainersForZone(List<Escale> escales, ZoneType zoneType) {
        // Estimation basée sur des ratios historiques
        int totalContainers = escales.stream()
            .mapToInt(Escale::getExpectedContainers)
            .sum();
        
        // Ratios approximatifs par type de zone
        switch (zoneType) {
            case STANDARD: return (int) (totalContainers * 0.7); // 70% standard
            case REFRIGERATED: return (int) (totalContainers * 0.15); // 15% réfrigéré
            case HAZARDOUS: return (int) (totalContainers * 0.1); // 10% dangereux
            case OVERSIZE: return (int) (totalContainers * 0.05); // 5% hors gabarit
            default: return 0;
        }
    }
}