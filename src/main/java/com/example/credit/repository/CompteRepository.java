package com.example.credit.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.credit.model.Compte;

@Repository
public interface CompteRepository extends JpaRepository<Compte,Long> {
    public List<Compte> findAllByClientIdRelation(Long id);
    
    @Query("SELECT c FROM Compte c WHERE c.dc.id = :id")
    Compte findCompteByDcId(@Param("id") long id);

    @Query("SELECT COUNT(d) FROM Compte d WHERE d.dateOuverture >= :lastMonth")
    long countComptesInTheLastMonth(@Param("lastMonth") LocalDate lastMonth);
}
