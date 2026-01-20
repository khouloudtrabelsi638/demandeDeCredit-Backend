package com.example.credit.service;

import com.example.credit.model.Compte;
import com.example.credit.model.DossierCredit;
import com.example.credit.repository.CompteRepository;
import com.example.credit.repository.DossierCreditRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DossierCreditService {

    private final DossierCreditRepository dossierCreditRepository;
    private final CompteRepository compteRepository;

    public DossierCreditService(DossierCreditRepository dossierCreditRepository, CompteRepository compteRepository) {
        this.dossierCreditRepository = dossierCreditRepository;
        this.compteRepository = compteRepository;
    }

    public List<DossierCredit> getAllDossiers() {
        return dossierCreditRepository.findAll();
    }

    public Optional<DossierCredit> getDossierById(long id) {
        return dossierCreditRepository.findById(id);
    }

    public void addDossier(DossierCredit dossierCredit) {
        dossierCreditRepository.save(dossierCredit);
    }

    public List<DossierCredit> getDossiersByAssigneA(String assigneA) {
        return dossierCreditRepository.findByAssigneA(assigneA);
    }

    @Transactional
    public void updateDossier(DossierCredit dossierCredit) {
        // 1. Load existing dossier from DB
        DossierCredit existingDossier = dossierCreditRepository.findById(dossierCredit.getId())
                .orElseThrow(() -> new IllegalStateException("DossierCredit non trouvé"));

        // 2. Load existing compte from DB
        Compte existingCompte = compteRepository.findById(dossierCredit.getCompte().getId())
                .orElseThrow(() -> new RuntimeException("Compte not found"));

        // 3. Update fields of existing dossier manually
        existingDossier.setAgence(dossierCredit.getAgence());
        existingDossier.setAssigneA(dossierCredit.getAssigneA());
        existingDossier.setCreePar(dossierCredit.getCreePar());
        existingDossier.setDateCreation(dossierCredit.getDateCreation());
        existingDossier.setDateModification(dossierCredit.getDateModification());
        existingDossier.setMarche(dossierCredit.getMarche());
        existingDossier.setModifiePar(dossierCredit.getModifiePar());
        existingDossier.setStatus(dossierCredit.getStatus());

        // 4. Set existing compte reference
        existingDossier.setCompte(existingCompte);

        // 5. Save updated dossier
        dossierCreditRepository.save(existingDossier);
    }

    @Transactional
    public void deleteDossierById(long id) {
        if (!dossierCreditRepository.existsById(id)) {
            throw new IllegalStateException("DossierCredit non trouvé");
        }
        dossierCreditRepository.deleteById(id);
    }

    public List<DossierCredit> getDossiersByStatus(String status) {
        return dossierCreditRepository.findByStatusIgnoreCase(status);
    }

    public long getCount(){
        return this.dossierCreditRepository.count();
    }

    public long getCountLastMonth(LocalDate date){
        return this.dossierCreditRepository.countDossiersInTheLastMonth(date);
    }
}
