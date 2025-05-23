package com.ensias.portflow2.controller;

import com.ensias.portflow2.model.Navire;
import com.ensias.portflow2.service.NavireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/navires")
@CrossOrigin(origins = "*")
public class NavireController {
    
    @Autowired
    private NavireService navireService;
    
    @GetMapping
    public ResponseEntity<List<Navire>> getAllNavires() {
        return ResponseEntity.ok(navireService.getAllNavires());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Navire> getNavireById(@PathVariable Long id) {
        Navire navire = navireService.getNavireById(id);
        return navire != null ? ResponseEntity.ok(navire) : ResponseEntity.notFound().build();
    }
    
    @GetMapping("/a-venir")
    public ResponseEntity<List<Navire>> getNaviresAVenir() {
        return ResponseEntity.ok(navireService.getNaviresAVenir());
    }
    
    @GetMapping("/en-approche")
    public ResponseEntity<List<Navire>> getNaviresEnApproche() {
        return ResponseEntity.ok(navireService.getNaviresEnApproche());
    }
    
    @PostMapping
    public ResponseEntity<Navire> createNavire(@RequestBody Navire navire) {
        return ResponseEntity.ok(navireService.saveNavire(navire));
    }
}