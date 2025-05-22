package com.ensias.portflow;

import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/documentation")
@CrossOrigin(origins = "http://localhost:4200")
public class DocumentationServiceController {
    
    @Autowired
    private ContainerRepository containerRepository;
    
    @Autowired
    private EscaleRepository escaleRepository;
    
    @Autowired
    private StorageZoneRepository zoneRepository;
    
    @Autowired
    private PerformanceIndicatorRepository kpiRepository;
    
    @Autowired
    private CongestionPredictionService congestionService;
    
    @GetMapping("/dashboard")
    public ResponseEntity<DocumentationDashboard> getDashboard() {
        DocumentationDashboard dashboard = new DocumentationDashboard();
        
        // KPIs actuels
        dashboard.setCurrentKPIs(getCurrentKPIs());
        
        // Statistiques générales
        dashboard.setTotalContainersProcessed(containerRepository.count());
        dashboard.setActiveOperations(escaleRepository.countByStatus(EscaleStatus.DOCKED));
        dashboard.setAverageWaitingTime(calculateAverageWaitingTime());
        dashboard.setStorageEfficiency(calculateStorageEfficiency());
        
        // Graphiques pour le dashboard
        dashboard.setMonthlyActivity(getMonthlyActivityData());
        dashboard.setContainerTypeDistribution(getContainerTypeDistribution());
        dashboard.setStorageOccupancyTrend(getStorageOccupancyTrend());
        
        return ResponseEntity.ok(dashboard);
    }
    
    @GetMapping("/kpis")
    public ResponseEntity<List<KPIData>> getAllKPIs() {
        List<KPIData> kpis = new ArrayList<>();
        
        // Temps d'attente moyen
        kpis.add(new KPIData("Temps d'attente moyen", calculateAverageWaitingTime(), "heures", "down"));
        
        // Taux d'occupation des zones de stockage
        kpis.add(new KPIData("Taux d'occupation stockage", calculateStorageOccupancy(), "%", "up"));
        
        // Nombre de conteneurs traités par jour
        kpis.add(new KPIData("Conteneurs/jour", calculateContainersPerDay(), "conteneurs", "up"));
        
        // Productivité des quais
        kpis.add(new KPIData("Productivité quais", calculateBerthProductivity(), "cont/h", "up"));
        
        // Temps de séjour moyen
        kpis.add(new KPIData("Temps de séjour moyen", calculateAverageDwellTime(), "jours", "down"));
        
        return ResponseEntity.ok(kpis);
    }
    
    @GetMapping("/reports/monthly")
    public ResponseEntity<MonthlyReport> getMonthlyReport(@RequestParam int year, @RequestParam int month) {
        MonthlyReport report = new MonthlyReport();
        report.setYear(year);
        report.setMonth(month);
        
        LocalDateTime startDate = LocalDateTime.of(year, month, 1, 0, 0);
        LocalDateTime endDate = startDate.plusMonths(1).minusDays(1);
        
        // Statistiques du mois
        report.setTotalContainers(containerRepository.countByArrivalTimeBetween(startDate, endDate));
        report.setTotalShips(escaleRepository.countByEtaBetween(startDate, endDate));
        report.setAverageProcessingTime(calculateAverageProcessingTime(startDate, endDate));
        report.setPeakOccupancyRate(calculatePeakOccupancy(startDate, endDate));
        
        // Détails par semaine
        report.setWeeklyBreakdown(getWeeklyBreakdown(startDate, endDate));
        
        // Comparaison avec le mois précédent
        report.setComparisonWithPreviousMonth(getMonthComparison(year, month));
        
        // Recommandations
        report.setRecommendations(generateRecommendations(report));
        
        return ResponseEntity.ok(report);
    }
    
    @GetMapping("/reports/annual")
    public ResponseEntity<AnnualReport> getAnnualReport(@RequestParam int year) {
        AnnualReport report = new AnnualReport();
        report.setYear(year);
        
        LocalDateTime startDate = LocalDateTime.of(year, 1, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(year, 12, 31, 23, 59);
        
        // Statistiques annuelles
        report.setTotalContainers(containerRepository.countByArrivalTimeBetween(startDate, endDate));
        report.setTotalShips(escaleRepository.countByEtaBetween(startDate, endDate));
        report.setAverageAnnualOccupancy(calculateAverageOccupancy(startDate, endDate));
        report.setBestPerformingMonth(findBestPerformingMonth(year));
        report.setWorstPerformingMonth(findWorstPerformingMonth(year));
        
        // Tendances mensuelles
        report.setMonthlyTrends(getMonthlyTrends(year));
        
        // Analyse des pics d'activité
        report.setPeakAnalysis(analyzePeakPeriods(year));
        
        // Projections pour l'année suivante
        report.setNextYearProjections(generateProjections(year + 1));
        
        return ResponseEntity.ok(report);
    }
    
    @GetMapping("/congestion/prediction")
    public ResponseEntity<CongestionPrediction> predictCongestion(@RequestParam int daysAhead) {
        return ResponseEntity.ok(congestionService.predictCongestion(daysAhead));
    }
    
    @GetMapping("/export/excel")
    public ResponseEntity<byte[]> exportToExcel(@RequestParam String reportType, 
                                               @RequestParam(required = false) Integer year,
                                               @RequestParam(required = false) Integer month) {
        byte[] excelData = generateExcelReport(reportType, year, month);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "portflow-report.xlsx");
        
        return ResponseEntity.ok().headers(headers).body(excelData);
    }
    
    @GetMapping("/analytics/trends")
    public ResponseEntity<TrendAnalysis> getTrendAnalysis(@RequestParam String period) {
        TrendAnalysis analysis = new TrendAnalysis();
        
        switch (period.toLowerCase()) {
            case "weekly":
                analysis = getWeeklyTrends();
                break;
            case "monthly":
                analysis = getMonthlyTrends();
                break;
            case "yearly":
                analysis = getYearlyTrends();
                break;
        }
        
        return ResponseEntity.ok(analysis);
    }
    
    // Méthodes privées pour les calculs
    private List<KPIData> getCurrentKPIs() {
        List<KPIData> kpis = new ArrayList<>();
        
        // Récupérer les KPIs de la base de données
        List<PerformanceIndicator> indicators = kpiRepository.findByCalculationDate(LocalDate.now());
        
        for (PerformanceIndicator indicator : indicators) {
            KPIData kpi = new KPIData();
            kpi.setName(indicator.getName());
            kpi.setValue(indicator.getValue());
            kpi.setUnit(indicator.getUnit());
            kpi.setTrend(calculateTrend(indicator));
            kpis.add(kpi);
        }
        
        return kpis;
    }
    
    private double calculateAverageWaitingTime() {
        // Logique pour calculer le temps d'attente moyen
        List<Escale> escales = escaleRepository.findAll();
        return escales.stream()
            .filter(e -> e.getAta() != null && e.getEta() != null)
            .mapToLong(e -> ChronoUnit.HOURS.between(e.getEta(), e.getAta()))
            .average()
            .orElse(0.0);
    }
    
    private double calculateStorageEfficiency() {
        List<StorageZone> zones = zoneRepository.findAll();
        if (zones.isEmpty()) return 0.0;
        
        double totalCapacity = zones.stream().mapToInt(StorageZone::getTotalCapacity).sum();
        double totalOccupied = zones.stream().mapToInt(StorageZone::getCurrentOccupancy).sum();
        
        return (totalOccupied / totalCapacity) * 100;
    }
    
    private double calculateStorageOccupancy() {
        return calculateStorageEfficiency();
    }
    
    private double calculateContainersPerDay() {
        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = endDate.minusDays(30);
        
        long containerCount = containerRepository.countByArrivalTimeBetween(startDate, endDate);
        return containerCount / 30.0;
    }
    
    private double calculateBerthProductivity() {
        // Calcul de la productivité des quais (conteneurs par heure)
        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = endDate.minusDays(7);
        
        List<Escale> recentEscales = escaleRepository.findByEtaBetween(startDate, endDate);
        
        return recentEscales.stream()
            .filter(e -> e.getAta() != null && e.getAtd() != null)
            .mapToDouble(e -> {
                long hours = ChronoUnit.HOURS.between(e.getAta(), e.getAtd());
                return hours > 0 ? (double) e.getExpectedContainers() / hours : 0;
            })
            .average()
            .orElse(0.0);
    }
    
    private double calculateAverageDwellTime() {
        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = endDate.minusDays(30);
        
        List<Container> containers = containerRepository.findByArrivalTimeBetween(startDate, endDate);
        
        return containers.stream()
            .filter(c -> c.getArrivalTime() != null && c.getDepartureTime() != null)
            .mapToLong(c -> ChronoUnit.DAYS.between(c.getArrivalTime(), c.getDepartureTime()))
            .average()
            .orElse(0.0);
    }
    
    private List<MonthlyActivityData> getMonthlyActivityData() {
        List<MonthlyActivityData> data = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        
        for (int i = 11; i >= 0; i--) {
            LocalDateTime monthStart = now.minusMonths(i).withDayOfMonth(1);
            LocalDateTime monthEnd = monthStart.plusMonths(1).minusDays(1);
            
            MonthlyActivityData monthData = new MonthlyActivityData();
            monthData.setMonth(monthStart.getMonth().name());
            monthData.setYear(monthStart.getYear());
            monthData.setContainerCount(containerRepository.countByArrivalTimeBetween(monthStart, monthEnd));
            monthData.setShipCount(escaleRepository.countByEtaBetween(monthStart, monthEnd));
            
            data.add(monthData);
        }
        
        return data;
    }
    
    private List<ContainerTypeData> getContainerTypeDistribution() {
        List<ContainerTypeData> distribution = new ArrayList<>();
        
        for (ContainerType type : ContainerType.values()) {
            long count = containerRepository.countByType(type);
            if (count > 0) {
                ContainerTypeData data = new ContainerTypeData();
                data.setType(type.name());
                data.setCount(count);
                data.setPercentage(calculatePercentage(count));
                distribution.add(data);
            }
        }
        
        return distribution;
    }
    
    private List<StorageOccupancyData> getStorageOccupancyTrend() {
        List<StorageOccupancyData> trend = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        
        for (int i = 30; i >= 0; i--) {
            LocalDate date = now.minusDays(i).toLocalDate();
            StorageOccupancyData data = new StorageOccupancyData();
            data.setDate(date);
            data.setOccupancyRate(calculateHistoricalOccupancy(date));
            trend.add(data);
        }
        
        return trend;
    }
    
    private double calculatePercentage(long count) {
        long total = containerRepository.count();
        return total > 0 ? (double) count / total * 100 : 0.0;
    }
    
    private double calculateHistoricalOccupancy(LocalDate date) {
        // Logique pour calculer l'occupation historique pour une date donnée
        // Ceci nécessiterait un système d'historisation des données
        return Math.random() * 100; // Placeholder
    }
    
    private String calculateTrend(PerformanceIndicator indicator) {
        // Comparer avec la valeur précédente pour déterminer la tendance
        return "stable"; // Placeholder
    }
    
    // Méthodes pour les rapports (simplified)
    private double calculateAverageProcessingTime(LocalDateTime start, LocalDateTime end) {
        return 4.5; // Placeholder
    }
    
    private double calculatePeakOccupancy(LocalDateTime start, LocalDateTime end) {
        return 85.0; // Placeholder
    }
    
    private List<WeeklyData> getWeeklyBreakdown(LocalDateTime start, LocalDateTime end) {
        return new ArrayList<>(); // Placeholder
    }
    
    private MonthComparison getMonthComparison(int year, int month) {
        return new MonthComparison(); // Placeholder
    }
    
    private List<String> generateRecommendations(MonthlyReport report) {
        List<String> recommendations = new ArrayList<>();
        recommendations.add("Optimiser les horaires des navires pour réduire les pics de congestion");
        recommendations.add("Améliorer la rotation des conteneurs dans les zones de stockage");
        return recommendations;
    }
    
    private byte[] generateExcelReport(String reportType, Integer year, Integer month) {
        // Logique de génération Excel
        return new byte[0]; // Placeholder
    }
    
    private TrendAnalysis getWeeklyTrends() {
        return new TrendAnalysis(); // Placeholder
    }
    
    private TrendAnalysis getMonthlyTrends() {
        return new TrendAnalysis(); // Placeholder
    }
    
    private TrendAnalysis getYearlyTrends() {
        return new TrendAnalysis(); // Placeholder
    }
}