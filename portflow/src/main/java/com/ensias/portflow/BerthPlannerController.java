package com.ensias.portflow;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/berth-planner")
@CrossOrigin(origins = "http://localhost:4200")
public class BerthPlannerController {
    
    @Autowired
    private ShipRepository shipRepository;
    
    @Autowired
    private EscaleRepository escaleRepository;
    
    @Autowired
    private AlertService alertService;
    
    @GetMapping("/ships")
    public ResponseEntity<List<Ship>> getAllShips() {
        return ResponseEntity.ok(shipRepository.findAll());
    }
    
    @GetMapping("/ships/approaching")
    public ResponseEntity<List<Ship>> getApproachingShips() {
        // Logic to get ships in approach based on AIS data
        List<Ship> ships = shipRepository.findAll().stream()
            .filter(ship -> ship.getLatitude() != null && ship.getLongitude() != null)
            .collect(Collectors.toList());
        return ResponseEntity.ok(ships);
    }
    
    @GetMapping("/escales")
    public ResponseEntity<List<Escale>> getAllEscales() {
        return ResponseEntity.ok(escaleRepository.findAll());
    }
    
    @GetMapping("/escales/upcoming")
    public ResponseEntity<List<Escale>> getUpcomingEscales() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime futureLimit = now.plusDays(30);
        return ResponseEntity.ok(escaleRepository.findByEtaBetween(now, futureLimit));
    }
    
    @PostMapping("/escales")
    public ResponseEntity<Escale> createEscale(@RequestBody CreateEscaleRequest request) {
        Escale escale = new Escale();
        escale.setShip(shipRepository.findById(request.getShipId()).orElse(null));
        escale.setEta(request.getEta());
        escale.setEtd(request.getEtd());
        escale.setStatus(EscaleStatus.PLANNED);
        escale.setBerthAssigned(request.getBerthAssigned());
        escale.setExpectedContainers(request.getExpectedContainers());
        
        return ResponseEntity.ok(escaleRepository.save(escale));
    }
    
    @PutMapping("/escales/{id}/assign-berth")
    public ResponseEntity<Escale> assignBerth(@PathVariable Long id, @RequestBody String berthName) {
        Optional<Escale> escaleOpt = escaleRepository.findById(id);
        if (escaleOpt.isPresent()) {
            Escale escale = escaleOpt.get();
            escale.setBerthAssigned(berthName);
            return ResponseEntity.ok(escaleRepository.save(escale));
        }
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping("/alerts/equipment-failure")
    public ResponseEntity<String> reportEquipmentFailure(@RequestBody EquipmentFailureRequest request) {
        alertService.createAlert(
            AlertType.EQUIPMENT_FAILURE,
            AlertSeverity.HIGH,
            "Equipment Failure: " + request.getEquipmentName(),
            request.getDescription(),
            "OPERATIONS_MANAGER"
        );
        return ResponseEntity.ok("Alert created successfully");
    }
    
    @GetMapping("/calendar")
    public ResponseEntity<List<CalendarEvent>> getCalendarEvents() {
        List<Escale> escales = escaleRepository.findAll();
        List<CalendarEvent> events = escales.stream()
            .map(escale -> {
                CalendarEvent event = new CalendarEvent();
                event.setId(escale.getId());
                event.setTitle(escale.getShip().getName());
                event.setStart(escale.getEta());
                event.setEnd(escale.getEtd());
                event.setColor(getColorByStatus(escale.getStatus()));
                return event;
            })
            .collect(Collectors.toList());
        return ResponseEntity.ok(events);
    }
    
    private String getColorByStatus(EscaleStatus status) {
        switch (status) {
            case PLANNED: return "#007bff";
            case IN_APPROACH: return "#ffc107";
            case DOCKED: return "#28a745";
            case LOADING_UNLOADING: return "#17a2b8";
            case DEPARTED: return "#6c757d";
            default: return "#007bff";
        }
    }
}