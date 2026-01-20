package com.example.credit.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.credit.model.DossierCredit;

@Repository
public interface DossierCreditRepository extends JpaRepository<DossierCredit, Long> {
    DossierCredit findDossierCreditByCompteId(long compteId);

    List<DossierCredit> findByAssigneA(String assigneA);

    List<DossierCredit> findByStatusIgnoreCase(String status);

    @Query("SELECT COUNT(d) FROM DossierCredit d WHERE d.dateCreation >= :lastMonth")
    long countDossiersInTheLastMonth(@Param("lastMonth") LocalDate lastMonth);

}
