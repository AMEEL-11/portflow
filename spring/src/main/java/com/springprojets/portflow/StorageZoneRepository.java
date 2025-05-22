package com.springprojets.portflow;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StorageZoneRepository extends JpaRepository<StorageZone, Long> {
    List<StorageZone> findByCurrentOccupancyLessThan(int occupancy);
    List<StorageZone> findByNameContainingIgnoreCase(String name);
    List<StorageZone> findByTypeOrderByCurrentOccupancyAsc(String type);
} 