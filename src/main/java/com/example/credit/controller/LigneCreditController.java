package com.example.credit.controller;

import com.example.credit.model.LigneCredit;
import com.example.credit.service.LigneCreditService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/lignes")
public class LigneCreditController {

    private final LigneCreditService ligneCreditService;

    public LigneCreditController(LigneCreditService ligneCreditService) {
        this.ligneCreditService = ligneCreditService;
    }

    @GetMapping
    public List<LigneCredit> getAll() {
        return ligneCreditService.getAllLignes();
    }

    @GetMapping("/{id}")
    public LigneCredit getById(@PathVariable long id) {
        Optional<LigneCredit> ligne = ligneCreditService.getLigneById(id);
        return ligne.orElse(null);
    }

    @PostMapping
    public String add(@RequestBody LigneCredit ligneCredit) {
        ligneCreditService.addLigne(ligneCredit);
        return "LigneCredit ajoutée";
    }

    @PutMapping
    public String update(@RequestBody LigneCredit ligneCredit) {
        ligneCreditService.updateLigne(ligneCredit);
        return "Mise à jour avec succès";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable long id) {
        ligneCreditService.deleteLigneById(id);
        return "LigneCredit supprimée";
    }

    @GetMapping("/grouped/famille/{famille}")
    public Map<String, List<LigneCredit>> getGroupedByNature(@PathVariable String famille) {
        return this.ligneCreditService.getByFamilleGroupedByNature(famille);
    }


    @GetMapping("/sum-by-devise")
    public ResponseEntity<Double> getSumByDevise(@RequestParam String devise) {
        Double total = ligneCreditService.getMontantTotal(devise);
        return ResponseEntity.ok(total);
    }
}
