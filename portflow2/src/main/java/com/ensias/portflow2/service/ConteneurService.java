package com.ensias.portflow2.service;

import com.ensias.portflow2.model.Conteneur;
import com.ensias.portflow2.repository.ConteneurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConteneurService {
    
    @Autowired
    private ConteneurRepository conteneurRepository;
    
    public List<Conteneur> getAllConteneurs() {
        return conteneurRepository.findAll();
    }
    
    public Conteneur getConteneurById(Long id) {
        return conteneurRepository.findById(id).orElse(null);
    }
    
    public Conteneur saveConteneur(Conteneur conteneur) {
        return conteneurRepository.save(conteneur);
    }
    
    public List<Conteneur> getConteneursByStatut(String statut) {
        return conteneurRepository.findByStatut(statut);
    }
    
    public Long countConteneursStockes() {
        return conteneurRepository.countConteneursStockes();
    }
}