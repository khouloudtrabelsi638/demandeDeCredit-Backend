package com.example.credit.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type_client")
@JsonSubTypes({
    @JsonSubTypes.Type(value = ClientPersonnePhysique.class, name = "p"),
    @JsonSubTypes.Type(value = ClientPersonneMorale.class, name = "m")
})
@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type_client")
@AllArgsConstructor
@NoArgsConstructor
public abstract class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long idRelation;
    protected String pay;
    protected String gouvernorat;
    protected String adresse;
    protected String infoComplementaires;
    protected String profession;
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    protected List<Compte> comptes;
    
}
