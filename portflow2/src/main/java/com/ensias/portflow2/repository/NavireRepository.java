package com.ensias.portflow2.repository;

import com.ensias.portflow2.model.Navire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NavireRepository extends JpaRepository<Navire, Long> {
    List<Navire> findByStatut(String statut);
    List<Navire> findByEtaPrevuBetween(LocalDateTime debut, LocalDateTime fin);
    
    @Query("SELECT n FROM Navire n WHERE n.etaPrevu >= CURRENT_TIMESTAMP ORDER BY n.etaPrevu")
    List<Navire> findNaviresAVenir();
    
    @Query("SELECT n FROM Navire n WHERE n.statut IN ('APPROCHE', 'ACCOSTAGE')")
    List<Navire> findNaviresEnApproche();
}