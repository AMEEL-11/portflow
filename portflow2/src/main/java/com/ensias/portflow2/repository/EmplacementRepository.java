package com.ensias.portflow2.repository;

import com.ensias.portflow2.model.Emplacement;
import com.ensias.portflow2.model.ZoneStockage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EmplacementRepository extends JpaRepository<Emplacement, Long> {
    List<Emplacement> findByZoneStockage(ZoneStockage zoneStockage);
    List<Emplacement> findByStatut(String statut);
    List<Emplacement> findByZoneStockageAndStatut(ZoneStockage zoneStockage, String statut);
    
    @Query("SELECT e FROM Emplacement e WHERE e.statut = 'LIBRE' ORDER BY e.zoneStockage.id, e.rangee, e.position")
    List<Emplacement> findEmplacementsLibresOptimaux();
    
    @Query("SELECT COUNT(e) FROM Emplacement e WHERE e.statut = ?1")
    Long countByStatut(String statut);
}