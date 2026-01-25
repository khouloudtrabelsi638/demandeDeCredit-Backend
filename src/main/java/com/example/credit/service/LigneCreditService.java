package com.example.credit.service;

import com.example.credit.model.LigneCredit;
import com.example.credit.repository.LigneCreditRepository;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
public class LigneCreditService {

    private final LigneCreditRepository ligneCreditRepository;

    public LigneCreditService(LigneCreditRepository ligneCreditRepository) {
        this.ligneCreditRepository = ligneCreditRepository;
    }

    public List<LigneCredit> getAllLignes() {
        return ligneCreditRepository.findAll();
    }

    public Optional<LigneCredit> getLigneById(long id) {
        return ligneCreditRepository.findById(id);
    }

    public void addLigne(LigneCredit ligneCredit) {
        ligneCreditRepository.save(ligneCredit);
}

   @Transactional
   public void updateLigne(LigneCredit ligneCredit) {
       long id = ligneCredit.getId();

       if (!ligneCreditRepository.existsById(id)) {
           throw new IllegalStateException("LigneCredit non trouvée");
       }

       LigneCredit existingLigne = ligneCreditRepository.findById(id).get();

       existingLigne.setDateEcheance(ligneCredit.getDateEcheance());
       existingLigne.setDevise(ligneCredit.getDevise());
       existingLigne.setFamille(ligneCredit.getFamille());
       existingLigne.setMarge(ligneCredit.getMarge());
       existingLigne.setMontant(ligneCredit.getMontant());
       existingLigne.setMontantcontreValeur(ligneCredit.getMontantcontreValeur());
       existingLigne.setNature(ligneCredit.getNature());
       existingLigne.setTaux(ligneCredit.getTaux());
       existingLigne.setType(ligneCredit.getType());
       existingLigne.setTypetaux(ligneCredit.getTypetaux());
       ligneCreditRepository.save(existingLigne);
   }


    public void deleteLigneById(long id) {
        if (!ligneCreditRepository.existsById(id)) {
            throw new IllegalStateException("LigneCredit non trouvée");
        }
        ligneCreditRepository.deleteById(id);
    }

    public Map<String, List<LigneCredit>> getByFamilleGroupedByNature(String famille) {
    return this.ligneCreditRepository.findByFamille(famille)
                     .stream()
                     .collect(Collectors.groupingBy(LigneCredit::getNature));
    }

    public double getMontantTotal(String devise){
        return this.ligneCreditRepository.sumMontantByDevise(devise);
    }

}
