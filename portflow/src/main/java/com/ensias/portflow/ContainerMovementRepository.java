package com.ensias.portflow;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContainerMovementRepository extends JpaRepository<ContainerMovement, Long> {
    List<ContainerMovement> findByContainerOrderByTimestampDesc(Container container);
    List<ContainerMovement> findByTimestampBetween(LocalDateTime start, LocalDateTime end);
}
