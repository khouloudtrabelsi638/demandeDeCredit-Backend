package com.example.credit.model;

import java.time.LocalDate;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue("P")
public class ClientPersonnePhysique extends Client{
    private String nom;
    private String prenom;
    private String sexe;
    private String situationFamiliale;
    private LocalDate dateDeNaissance;
    private String profession;
    private String typeContrat; 
    private String typeEmployeur;
}
