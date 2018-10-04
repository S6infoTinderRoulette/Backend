package com.tinderroulette.backend.rest.dao;

import com.tinderroulette.backend.rest.model.Ap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApDao extends JpaRepository <Ap,String> {

    Ap save (Ap ap);
    List<Ap> findAll();
    Ap findByIdAp (String IdAp);
    void deleteByIdAp (String IdAp);
}
