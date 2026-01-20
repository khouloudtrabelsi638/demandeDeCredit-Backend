package com.example.credit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.credit.model.Actionneur;

@Repository
public interface ActionneurRepository extends JpaRepository<Actionneur,String>{
    public List<Actionneur> findAllByClientIdRelation(Long id);
}
