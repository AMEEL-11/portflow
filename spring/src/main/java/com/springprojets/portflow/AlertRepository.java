package com.springprojets.portflow;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AlertRepository extends JpaRepository<Alert, Long> {
    List<Alert> findByStatus(AlertStatus status);
    List<Alert> findByPriority(AlertPriority priority);
    List<Alert> findByTargetRoles(String targetRoles);
	List<Alert> findActiveAlertsByRole(String name);
} 