package com.example.credit.controller;

import com.example.credit.model.DossierCredit;
import com.example.credit.service.DossierCreditService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.GetMapping;


@CrossOrigin
@RestController
@RequestMapping("/dossiers")
public class DossierCreditController {

    private final DossierCreditService dossierCreditService;

    public DossierCreditController(DossierCreditService dossierCreditService) {
        this.dossierCreditService = dossierCreditService;
    }

    @GetMapping
    public List<DossierCredit> getAll() {
        return dossierCreditService.getAllDossiers();
    }

    @GetMapping("/assigneA/{username}")
    public List<DossierCredit> getByAssigneA(@PathVariable String username) {
        return dossierCreditService.getDossiersByAssigneA(username);
    }

    @GetMapping("/{id}")
    public DossierCredit getById(@PathVariable long id) {
        Optional<DossierCredit> dossier = dossierCreditService.getDossierById(id);
        return dossier.orElse(null);
    }

    @PostMapping
    public String add(@RequestBody DossierCredit dossierCredit) {
        dossierCreditService.addDossier(dossierCredit);
        return "DossierCredit ajouté";
    }

    @PutMapping
    public String update(@RequestBody DossierCredit dossierCredit) {
        dossierCreditService.updateDossier(dossierCredit);
        return "Mise à jour avec succès";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable long id) {
        dossierCreditService.deleteDossierById(id);
        return "DossierCredit supprimé";
    }

    @GetMapping("/by-status/{status}")
    public List<DossierCredit> getByStatus(@PathVariable String status) {
        return dossierCreditService.getDossiersByStatus(status);
    }

    @GetMapping("/count")
    public long getDossierCreditCount() {
        return this.dossierCreditService.getCount();
    }

    @GetMapping("/count/lastMonth")
    public long getDossierCreditCountInTheLastMonth(@RequestParam LocalDate date) {
        return this.dossierCreditService.getCountLastMonth(date);
    }
    
}
