package com.ensias.portflow2.repository;

import com.ensias.portflow2.model.Escale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EscaleRepository extends JpaRepository<Escale, Long> {
    List<Escale> findByStatut(String statut);
    List<Escale> findByDateArriveeBetween(LocalDateTime debut, LocalDateTime fin);
    List<Escale> findByNavireId(Long navireId);
    
    @Query("SELECT e FROM Escale e WHERE e.dateArrivee >= CURRENT_TIMESTAMP ORDER BY e.dateArrivee")
    List<Escale> findEscalesPlanifiees();
    
    @Query("SELECT e FROM Escale e WHERE e.statut = 'EN_COURS'")
    List<Escale> findEscalesEnCours();
    //TODO
}