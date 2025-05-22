package com.springprojets.portflow;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Collection;

@Repository
public interface ShipRepository extends JpaRepository<Ship, Long> {
    List<Ship> findByStatus(ShipStatus status);
    List<Ship> findByStatusIn(Collection<ShipStatus> statuses);
    List<Ship> findByNameContainingIgnoreCase(String name);
    List<Ship> findByCurrentBerthIsNotNull();
} 