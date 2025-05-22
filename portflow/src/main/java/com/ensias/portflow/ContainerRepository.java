package com.ensias.portflow;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.*;

@Repository
public interface ContainerRepository extends JpaRepository<Container, Long> {
    Optional<Container> findByContainerNumber(String containerNumber);
    List<Container> findByClientId(Long clientId);
    List<Container> findByStatus(ContainerStatus status);
    List<Container> findByEscaleId(Long escaleId);
    long countByStatus(ContainerStatus status);
    long countByType(ContainerType type);
    long countByArrivalTimeBetween(LocalDateTime start, LocalDateTime end);
    List<Container> findByArrivalTimeBetween(LocalDateTime start, LocalDateTime end);
    
    @Query("SELECT c FROM Container c WHERE c.storageLocation.zone.id = :zoneId")
    List<Container> findByStorageZoneId(@Param("zoneId") Long zoneId);
}
