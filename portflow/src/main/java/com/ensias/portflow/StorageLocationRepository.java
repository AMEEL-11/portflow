package com.ensias.portflow;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.*;

@Repository
public interface StorageLocationRepository extends JpaRepository<StorageLocation, Long> {
    List<StorageLocation> findByZoneIdAndStatus(Long zoneId, LocationStatus status);
    List<StorageLocation> findByZoneId(Long zoneId);
    List<StorageLocation> findByStatus(LocationStatus status);
    
    @Query("SELECT sl FROM StorageLocation sl WHERE sl.zone.type = :zoneType AND sl.status = :status")
    List<StorageLocation> findAvailableByZoneType(@Param("zoneType") ZoneType zoneType, @Param("status") LocationStatus status);
}
