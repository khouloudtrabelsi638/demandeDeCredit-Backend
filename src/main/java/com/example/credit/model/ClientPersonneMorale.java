package com.example.credit.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue("M")
public class ClientPersonneMorale extends Client{
    private String acronyme;
    private String formeJuridique;
    private String groupe;
    private Double capitalSocial;
    private String matriculeFiscale;
    private int nbrEmployes;
    private String secteurActivite;
    private LocalDate dateVisite;
    private String visitePar;
    private String personnesContactees;
    private String raison;
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Gerant> gerants;
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Actionneur> actionneurs;
}
