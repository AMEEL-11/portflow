package com.ensias.portflow2.controller;

import com.ensias.portflow2.model.Conteneur;
import com.ensias.portflow2.service.ConteneurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/conteneurs")
@CrossOrigin(origins = "*")
public class ConteneurController {
    
    @Autowired
    private ConteneurService conteneurService;
    
    @GetMapping
    public ResponseEntity<List<Conteneur>> getAllConteneurs() {
        return ResponseEntity.ok(conteneurService.getAllConteneurs());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Conteneur> getConteneurById(@PathVariable Long id) {
        Conteneur conteneur = conteneurService.getConteneurById(id);
        return conteneur != null ? ResponseEntity.ok(conteneur) : ResponseEntity.notFound().build();
    }
    
    @GetMapping("/statut/{statut}")
    public ResponseEntity<List<Conteneur>> getConteneursByStatut(@PathVariable String statut) {
        return ResponseEntity.ok(conteneurService.getConteneursByStatut(statut));
    }
    
    @GetMapping("/count/stockes")
    public ResponseEntity<Long> countConteneursStockes() {
        return ResponseEntity.ok(conteneurService.countConteneursStockes());
    }
    
    @PostMapping
    public ResponseEntity<Conteneur> createConteneur(@RequestBody Conteneur conteneur) {
        return ResponseEntity.ok(conteneurService.saveConteneur(conteneur));
    }
}