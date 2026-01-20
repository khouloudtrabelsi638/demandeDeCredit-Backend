package com.example.credit.controller;

import com.example.credit.model.Gerant;
import com.example.credit.service.GerantService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@CrossOrigin
@RestController
@RequestMapping("/gerants")
public class GerantController {

    private final GerantService gerantService;

    public GerantController(GerantService gerantService) {
        this.gerantService = gerantService;
    }

    @GetMapping
    public List<Gerant> getAll() {
        return gerantService.getAllGerants();
    }

    @GetMapping("/{id}")
    public Gerant getById(@PathVariable long id) {
        Optional<Gerant> gerant = gerantService.getGerantById(id);
        return gerant.orElse(null);
    }

    @PostMapping
    public String add(@RequestBody Gerant gerant) {
        gerantService.addGerant(gerant);
        return "Gerant ajouté";
    }

    @PutMapping
    public String update(@RequestBody Gerant gerant) {
        gerantService.updateGerant(gerant);
        return "Mise à jour avec succès";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable long id) {
        gerantService.deleteGerantById(id);
        return "Gerant supprimé";
    }
}
