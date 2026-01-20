package com.example.credit.service;

import com.example.credit.model.Gerant;
import com.example.credit.repository.GerantRepository;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class GerantService {

    private final GerantRepository gerantRepository;

    public GerantService(GerantRepository gerantRepository) {
        this.gerantRepository = gerantRepository;
    }

    public List<Gerant> getAllGerants() {
        return gerantRepository.findAll();
    }

    public Optional<Gerant> getGerantById(long id) {
        return gerantRepository.findById(id);
    }

    public void addGerant(Gerant gerant) {
        gerantRepository.save(gerant);
    }

    public void updateGerant(Gerant gerant) {
        long id = gerant.getId();
        if (!gerantRepository.existsById(id)) {
            throw new IllegalStateException("Gerant non trouvé");
        }
        gerantRepository.save(gerant);
    }

    public void deleteGerantById(long id) {
        if (!gerantRepository.existsById(id)) {
            throw new IllegalStateException("Gerant non trouvé");
        }
        gerantRepository.deleteById(id);
    }
}
