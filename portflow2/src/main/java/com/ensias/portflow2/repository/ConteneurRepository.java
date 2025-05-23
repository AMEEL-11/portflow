package com.ensias.portflow2.repository;

import com.ensias.portflow2.model.Conteneur;
import com.ensias.portflow2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ConteneurRepository extends JpaRepository<Conteneur, Long> {
    Optional<Conteneur> findByNumeroConteneur(String numeroConteneur);
    List<Conteneur> findByClient(User client);
    List<Conteneur> findByStatut(String statut);
    List<Conteneur> findByNavireId(Long navireId);
    
    @Query("SELECT c FROM Conteneur c WHERE c.emplacement IS NULL AND c.statut = 'STOCKE'")
    List<Conteneur> findConteneursNonAssignes();
    
    @Query("SELECT COUNT(c) FROM Conteneur c WHERE c.statut = 'STOCKE'")
    Long countConteneursStockes();
}