package com.example.credit.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DossierCredit {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
    private String status;
    private String agence;
    private String modifiePar;
    private String creePar;
    private String assigneA;
    private LocalDate dateCreation;
    private LocalDate dateModification;
    private String marche;
    @OneToMany(mappedBy = "dossier", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<LigneCredit> lignesCredit;
    @OneToOne
    @JoinColumn(name = "id_compte")
    @JsonBackReference  
    private Compte compte;
}
