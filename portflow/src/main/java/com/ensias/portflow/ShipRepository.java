package com.ensias.portflow;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.*;

@Repository
public interface ShipRepository extends JpaRepository<Ship, Long> {
    List<Ship> findByNameContaining(String name);
    Optional<Ship> findByImoNumber(String imoNumber);
}
