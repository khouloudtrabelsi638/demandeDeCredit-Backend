package com.example.credit.service;

import com.example.credit.model.Compte;
import com.example.credit.model.DossierCredit;
import com.example.credit.repository.CompteRepository;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CompteService {

    private final CompteRepository compteRepository;

    public CompteService(CompteRepository compteRepository) {
        this.compteRepository = compteRepository;
    }

    public List<Compte> getAllComptes() {
        return compteRepository.findAll();
    }

    public Optional<Compte> getCompteById(Long id) {
        return compteRepository.findById(id);
    }

    public void addCompte(Compte compte) {
        compteRepository.save(compte);
    }

@Transactional
public void updateCompte(Compte compte) {
    Long id = compte.getId();

    if (id == null || !compteRepository.existsById(id)) {
        throw new IllegalStateException("Compte non trouvé");
    }

    compteRepository.save(compte);
}

    public Compte getCompteByDossier(Long dossierCreditId) {
        return this.compteRepository.findCompteByDcId(dossierCreditId);
    }

    public void deleteCompteById(Long id) {
        if (!compteRepository.existsById(id)) {
            throw new IllegalStateException("Compte non trouvé");
        }
        compteRepository.deleteById(id);
    }
    public long getCount(){
        return this.compteRepository.count();
    }

    public long getCountLastMonth(LocalDate date){
        return this.compteRepository.countComptesInTheLastMonth(date);
    }
}
