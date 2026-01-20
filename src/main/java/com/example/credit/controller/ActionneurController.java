package com.example.credit.controller;
import org.springframework.web.bind.annotation.*;
import com.example.credit.model.Actionneur;
import com.example.credit.service.ActionneurService;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/actionneurs")
public class ActionneurController {

    private final ActionneurService actionneurService;

    public ActionneurController(ActionneurService actionneurService) {
        this.actionneurService = actionneurService;
    }

    @GetMapping
    public List<Actionneur> getAll() {
        return actionneurService.getAllActionneurs();
    }

    @GetMapping("/{id}")
    public Actionneur getById(@PathVariable String id) {
        Optional<Actionneur> actionneur = actionneurService.getActionneurById(id);
        return actionneur.orElse(null); 
    }   

    @PostMapping
    public String add(@RequestBody Actionneur actionneur) {
        try{
        actionneurService.addActionneur(actionneur);
        return "Actionneur ajouté";
    }catch (IllegalStateException e){
        return "Erreur lors de l'ajout";
    }
    }

    @PutMapping
    public String update(@RequestBody Actionneur actionneur) {
        try {
            actionneurService.updateActionneur(actionneur);
            return "mise à jour avec succé";
        } catch (IllegalStateException e) {
            return "Actionneur not found";
        }
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable String id) {
        try{
            actionneurService.deleteActionneurById(id);
            return "Actionneur supprimé";
        }catch (IllegalStateException e){
            return "Actionneur non trouvé";
        }
    }
}
