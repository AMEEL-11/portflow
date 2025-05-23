package com.ensias.portflow2.service;

import com.ensias.portflow2.model.Navire;
import com.ensias.portflow2.repository.NavireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NavireService {
    
    @Autowired
    private NavireRepository navireRepository;
    
    public List<Navire> getAllNavires() {
        return navireRepository.findAll();
    }
    
    public Navire getNavireById(Long id) {
        return navireRepository.findById(id).orElse(null);
    }
    
    public Navire saveNavire(Navire navire) {
        return navireRepository.save(navire);
    }
    
    public List<Navire> getNaviresAVenir() {
        return navireRepository.findNaviresAVenir();
    }
    
    public List<Navire> getNaviresEnApproche() {
        return navireRepository.findNaviresEnApproche();
    }
}