package com.ensias.portflow2.repository;

import com.ensias.portflow2.model.Alerte;
import com.ensias.portflow2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AlerteRepository extends JpaRepository<Alerte, Long> {
    List<Alerte> findByDestinataireAndStatut(User destinataire, String statut);
    List<Alerte> findByDestinataireAndLueFalse(User destinataire);
    List<Alerte> findByTypeAndStatut(String type, String statut);
    
    @Query("SELECT a FROM Alerte a WHERE a.destinataire = ?1 ORDER BY a.dateCreation DESC")
    List<Alerte> findByDestinataireOrderByDateCreationDesc(User destinataire);
    
    @Query("SELECT COUNT(a) FROM Alerte a WHERE a.destinataire = ?1 AND a.lue = false")
    Long countUnreadAlertes(User destinataire);
}