package com.example.credit.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.example.credit.model.Actionneur;
import com.example.credit.repository.ActionneurRepository;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class ActionneurService {
    private final ActionneurRepository actionneurRepository;

    public ActionneurService(ActionneurRepository actionneurRepository) {
        this.actionneurRepository = actionneurRepository;
    }

    public Optional<Actionneur> getActionneurById(String id){
        return this.actionneurRepository.findById(id);
    }

    public List<Actionneur> getAllActionneurs(){
        return this.actionneurRepository.findAll();
    }

    public void addActionneur(Actionneur actionneur){
        this.actionneurRepository.save(actionneur);
    }

    public void deleteActionneurById(String id){
        this.actionneurRepository.deleteById(id);
    }

    public void updateActionneur(Actionneur actionneur){
        Optional<Actionneur> act = this.actionneurRepository.findById(actionneur.getCin());
        if (act.isPresent()){
            Actionneur actionneur1 = act.get();
            if (!Objects.equals(actionneur.getNom(), actionneur1.getNom())) {
                actionneur1.setNom(actionneur.getNom());}

            if (!Objects.equals(actionneur.getPrenom(), actionneur1.getPrenom())) {
                actionneur1.setPrenom(actionneur.getPrenom());}

            if (!Objects.equals(actionneur.getDateDeNaissance(), actionneur1.getDateDeNaissance())) {
                actionneur1.setDateDeNaissance(actionneur.getDateDeNaissance());}

            if (!Objects.equals(actionneur.getNationalite(), actionneur1.getNationalite())) {
                actionneur1.setNationalite(actionneur.getNationalite());}

            if (!Objects.equals(actionneur.getCapital(), actionneur1.getCapital())) {
                actionneur1.setCapital(actionneur.getCapital());}
            this.actionneurRepository.save(actionneur1);
            }
        else{
            throw new IllegalStateException("actionneur non existant");
        }    
        }
}
