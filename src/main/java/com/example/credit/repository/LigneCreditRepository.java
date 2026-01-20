package com.example.credit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.credit.model.LigneCredit;

public interface LigneCreditRepository extends JpaRepository<LigneCredit,Long>{
    List<LigneCredit> findByFamille(String famille);
    
    
    @Query("SELECT SUM(l.montant) FROM LigneCredit l WHERE l.devise = :devise")
    Double sumMontantByDevise(@Param("devise") String devise);
}
