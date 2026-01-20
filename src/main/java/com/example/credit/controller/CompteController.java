package com.example.credit.controller;

import com.example.credit.model.Compte;
import com.example.credit.service.CompteService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@CrossOrigin
@RestController
@RequestMapping("/comptes")
public class CompteController {

    private final CompteService compteService;

    public CompteController(CompteService compteService) {
        this.compteService = compteService;
    }
     @GetMapping("/dossier/{dossierCreditId}")
    public ResponseEntity<Compte> getClientByDossierCreditId(@PathVariable Long dossierCreditId) {
        Compte compte = compteService.getCompteByDossier(dossierCreditId);
        return ResponseEntity.ok(compte);
    }

    @GetMapping
    public List<Compte> getAll() {
        return compteService.getAllComptes();
    }

    @GetMapping("/{id}")
    public Compte getById(@PathVariable Long id) {
        Optional<Compte> compte = compteService.getCompteById(id);
        return compte.orElse(null);
    }

    @PostMapping
    public String add(@RequestBody Compte compte) {
        compteService.addCompte(compte);
        return "Compte ajouté";
    }

    @PutMapping
    public String update(@RequestBody Compte compte) {
        compteService.updateCompte(compte);
        return "Mise à jour avec succès";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        compteService.deleteCompteById(id);
        return "Compte supprimé";
    }

    @GetMapping("/_count")
    public long getComptesCount() {
        return this.compteService.getCount();
    }

    @GetMapping("/_count/lastMonth")
    public long getComptesLastMonth(@RequestParam LocalDate date) {
        return this.compteService.getCountLastMonth(date);
    }
}
