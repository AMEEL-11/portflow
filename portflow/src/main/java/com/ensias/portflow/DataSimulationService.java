package com.ensias.portflow;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;


@Service
public class DataSimulationService {
 
 @Autowired
 private ShipRepository shipRepository;
 
 @Autowired
 private ContainerRepository containerRepository;
 
 @Autowired
 private EscaleRepository escaleRepository;
 
 @PostConstruct
 public void initializeSimulatedData() {
     if (shipRepository.count() == 0) {
         createSimulatedShips();
         createSimulatedEscales();
         createSimulatedContainers();
     }
 }
 
 private void createSimulatedShips() {
     List<Ship> ships = Arrays.asList(
         createShip("MAERSK SHANGHAI", "IMO9632174", "OZHF2", "Denmark", 23000),
         createShip("MSC OSCAR", "IMO9744465", "3ELV9", "Panama", 19200),
         createShip("CMA CGM MARCO POLO", "IMO9454436", "FLSU", "France", 16000),
         createShip("COSCO SHIPPING UNIVERSE", "IMO9795010", "BQZX", "China", 21000),
         createShip("EVER GIVEN", "IMO9811000", "H3RC", "Panama", 20000)
     );
     
     shipRepository.saveAll(ships);
 }
 
 private Ship createShip(String name, String imo, String callSign, String flag, int capacity) {
     Ship ship = new Ship();
     ship.setName(name);
     ship.setImoNumber(imo);
     ship.setCallSign(callSign);
     ship.setFlag(flag);
     ship.setCapacity(capacity);
     ship.setType("Container Ship");
     
     // Simulation de données AIS
     ship.setLatitude(33.5731 + (Math.random() - 0.5) * 2); // Autour de Casablanca
     ship.setLongitude(-7.5898 + (Math.random() - 0.5) * 2);
     ship.setSpeed(12.5 + Math.random() * 10);
     ship.setCourse(Math.random() * 360);
     ship.setLastUpdate(LocalDateTime.now().minusHours((int)(Math.random() * 24)));
     
     return ship;
 }
 
 private void createSimulatedEscales() {
     List<Ship> ships = shipRepository.findAll();
     List<Escale> escales = new ArrayList<>();
     
     for (Ship ship : ships) {
         // Créer des escales passées, présentes et futures
         for (int i = -5; i <= 10; i++) {
             Escale escale = new Escale();
             escale.setShip(ship);
             
             LocalDateTime baseTime = LocalDateTime.now().plusDays(i * 7);
             escale.setEta(baseTime.plusHours((int)(Math.random() * 24)));
             escale.setEtd(escale.getEta().plusHours(12 + (int)(Math.random() * 36)));
             
             if (i < 0) {
                 escale.setStatus(EscaleStatus.DEPARTED);
                 escale.setAta(escale.getEta().minusHours((int)(Math.random() * 4)));
                 escale.setAtd(escale.getEtd().minusHours((int)(Math.random() * 4)));
             } else if (i == 0) {
                 escale.setStatus(EscaleStatus.DOCKED);
                 escale.setAta(escale.getEta().minusHours((int)(Math.random() * 2)));
             } else if (i == 1) {
                 escale.setStatus(EscaleStatus.IN_APPROACH);
             } else {
                 escale.setStatus(EscaleStatus.PLANNED);
             }
             
             escale.setBerthAssigned("Berth " + (char)('A' + (int)(Math.random() * 6)));
             escale.setExpectedContainers(500 + (int)(Math.random() * 1500));
             
             escales.add(escale);
         }
     }
     
     escaleRepository.saveAll(escales);
 }
 
 private void createSimulatedContainers() {
     List<Escale> escales = escaleRepository.findAll();
     List<Container> containers = new ArrayList<>();
     
     for (Escale escale : escales) {
         int containerCount = escale.getExpectedContainers();
         
         for (int i = 0; i < containerCount; i++) {
             Container container = new Container();
             container.setContainerNumber(generateContainerNumber());
             container.setType(getRandomContainerType());
             container.setStatus(getRandomContainerStatus(escale.getStatus()));
             container.setEscale(escale);
             
             // Données RFID/GPS simulées
             container.setRfidTag("RFID" + System.currentTimeMillis() + i);
             container.setCurrentLatitude(33.5731 + (Math.random() - 0.5) * 0.1);
             container.setCurrentLongitude(-7.5898 + (Math.random() - 0.5) * 0.1);
             container.setLastLocationUpdate(LocalDateTime.now().minusMinutes((int)(Math.random() * 60)));
             
             // Détails du conteneur
             container.setDestination(getRandomDestination());
             container.setOrigin(getRandomOrigin());
             container.setWeight(5000 + Math.random() * 25000);
             container.setRefrigerated(Math.random() < 0.15);
             container.setHazardous(Math.random() < 0.1);
             
             if (escale.getStatus() != EscaleStatus.PLANNED) {
                 container.setArrivalTime(escale.getAta());
             }
             
             if (escale.getStatus() == EscaleStatus.DEPARTED) {
                 container.setDepartureTime(escale.getAtd());
             }
             
             containers.add(container);
             
             // Limiter le nombre pour éviter la surcharge
             if (containers.size() >= 1000) break;
         }
         
         if (containers.size() >= 1000) break;
     }
     
     containerRepository.saveAll(containers);
 }
 
 private String generateContainerNumber() {
     String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
     StringBuilder sb = new StringBuilder();
     
     // 4 lettres
     for (int i = 0; i < 4; i++) {
         sb.append(letters.charAt((int)(Math.random() * 26)));
     }
     
     // 7 chiffres
     for (int i = 0; i < 7; i++) {
         sb.append((int)(Math.random() * 10));
     }
     
     return sb.toString();
 }
 
 private ContainerType getRandomContainerType() {
     ContainerType[] types = ContainerType.values();
     return types[(int)(Math.random() * types.length)];
 }
 
 private ContainerStatus getRandomContainerStatus(EscaleStatus escaleStatus) {
     switch (escaleStatus) {
         case PLANNED:
             return ContainerStatus.ARRIVING;
         case IN_APPROACH:
             return ContainerStatus.ARRIVING;
         case DOCKED:
             return Math.random() < 0.5 ? ContainerStatus.UNLOADING : ContainerStatus.STORED;
         case LOADING_UNLOADING:
             return Math.random() < 0.3 ? ContainerStatus.UNLOADING : 
                    Math.random() < 0.6 ? ContainerStatus.STORED : ContainerStatus.LOADING;
         case DEPARTED:
             return ContainerStatus.DEPARTED;
         default:
             return ContainerStatus.ARRIVING;
     }
 }
 
 private String getRandomDestination() {
     String[] destinations = {
         "Paris, France", "Madrid, Spain", "Hamburg, Germany", 
         "Rotterdam, Netherlands", "Antwerp, Belgium", "Genoa, Italy",
         "Marseille, France", "Barcelona, Spain", "Liverpool, UK"
     };
     return destinations[(int)(Math.random() * destinations.length)];
 }
 
 private String getRandomOrigin() {
     String[] origins = {
         "Shanghai, China", "Singapore", "Dubai, UAE", 
         "Hong Kong", "Busan, South Korea", "Qingdao, China",
         "Ningbo, China", "Guangzhou, China", "Tianjin, China"
     };
     return origins[(int)(Math.random() * origins.length)];
 }
}
