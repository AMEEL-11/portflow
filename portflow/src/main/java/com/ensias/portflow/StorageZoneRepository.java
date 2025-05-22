package com.ensias.portflow;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StorageZoneRepository extends JpaRepository<StorageZone, Long> {
    List<StorageZone> findByType(ZoneType type);
}
