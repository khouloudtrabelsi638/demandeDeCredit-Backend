package com.example.credit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.credit.model.Gerant;

@Repository
public interface GerantRepository extends JpaRepository<Gerant,Long>{
    public List<Gerant> findAllByClientIdRelation(Long id);
}
