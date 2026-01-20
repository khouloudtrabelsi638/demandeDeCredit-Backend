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
    public void updateCompte(Compte updatedCompte) {
        Long id = updatedCompte.getId();
        if (id == null || !compteRepository.existsById(id)) {
            throw new IllegalStateException("Compte non trouvé");
        }

        Compte existingCompte = compteRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Compte non trouvé"));

        // Update basic fields
        existingCompte.setDateOuverture(updatedCompte.getDateOuverture());
        existingCompte.setChapitre(updatedCompte.getChapitre());

        // Update documents (optional: more advanced merging can be done if needed)
        existingCompte.getDocuments().clear();
        if (updatedCompte.getDocuments() != null) {
            updatedCompte.getDocuments().forEach(doc -> {
                doc.setCompte(existingCompte); // maintain relationship
                existingCompte.getDocuments().add(doc);
            });
        }

        // Update client if needed (optional, usually shouldn't change on update)
        existingCompte.setClient(updatedCompte.getClient());

        // Handle DossierCredit
        if (updatedCompte.getDc() != null) {
            DossierCredit updatedDC = updatedCompte.getDc();
            DossierCredit existingDC = existingCompte.getDc();

            if (existingDC == null) {
                updatedDC.setCompte(existingCompte);
                existingCompte.setDc(updatedDC);
            } else {
                existingDC.setStatus(updatedDC.getStatus());
                existingDC.setAgence(updatedDC.getAgence());
                existingDC.setModifiePar(updatedDC.getModifiePar());
                existingDC.setCreePar(updatedDC.getCreePar());
                existingDC.setAssigneA(updatedDC.getAssigneA());
                existingDC.setDateCreation(updatedDC.getDateCreation());
                existingDC.setDateModification(updatedDC.getDateModification());
                existingDC.setMarche(updatedDC.getMarche());

                // Update lignesCredit
                existingDC.getLignesCredit().clear();
                if (updatedDC.getLignesCredit() != null) {
                    updatedDC.getLignesCredit().forEach(lc -> {
                        lc.setDossier(existingDC); // maintain relationship
                        existingDC.getLignesCredit().add(lc);
                    });
                }
            }
        }

        compteRepository.save(existingCompte);
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
