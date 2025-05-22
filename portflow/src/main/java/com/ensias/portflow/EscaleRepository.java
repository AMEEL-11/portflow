package com.ensias.portflow;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EscaleRepository extends JpaRepository<Escale, Long> {
    List<Escale> findByEtaBetween(LocalDateTime start, LocalDateTime end);
    List<Escale> findByStatus(EscaleStatus status);
    long countByStatus(EscaleStatus status);
    long countByEtaBetween(LocalDateTime start, LocalDateTime end);
}