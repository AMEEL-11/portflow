package com.ensias.portflow;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerformanceIndicatorRepository extends JpaRepository<PerformanceIndicator, Long> {
    List<PerformanceIndicator> findByCalculationDate(LocalDate date);
    List<PerformanceIndicator> findByType(KPIType type);
    List<PerformanceIndicator> findByCalculationDateBetween(LocalDate start, LocalDate end);
}