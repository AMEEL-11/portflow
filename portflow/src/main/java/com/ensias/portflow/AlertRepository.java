package com.ensias.portflow;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.*;

@Repository
public interface AlertRepository extends JpaRepository<Alert, Long> {
    List<Alert> findByTargetRoleAndAcknowledgedFalse(String targetRole);
    List<Alert> findByCreatedAtAfterOrderByCreatedAtDesc(LocalDateTime since);
}
