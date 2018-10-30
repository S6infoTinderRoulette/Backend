package com.tinderroulette.backend.rest.dao;

import com.tinderroulette.backend.rest.model.Classes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassesDao extends JpaRepository <Classes,String> {

    Classes save (Classes classes);
    List<Classes> findAll();
    Classes findByIdClass (String IdClass);
    void deleteByIdClass (String IdClass);
}
