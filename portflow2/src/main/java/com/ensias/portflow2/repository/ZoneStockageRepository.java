package com.ensias.portflow2.repository;

import com.ensias.portflow2.model.ZoneStockage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ZoneStockageRepository extends JpaRepository<ZoneStockage, Long> {
    List<ZoneStockage> findByType(String type);
    
    @Query("SELECT z FROM ZoneStockage z WHERE SIZE(z.emplacements) < z.capaciteMax")
    List<ZoneStockage> findZonesDisponibles();
    
    @Query("SELECT z, COUNT(e) FROM ZoneStockage z LEFT JOIN z.emplacements e WHERE e.statut = 'OCCUPE' GROUP BY z")
    List<Object[]> getTauxOccupationZones();
}