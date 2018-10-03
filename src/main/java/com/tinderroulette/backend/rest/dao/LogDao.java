package com.tinderroulette.backend.rest.dao;

import com.tinderroulette.backend.rest.model.Logs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogDao extends JpaRepository<Logs,Long> {

    Logs save (Logs log);
    List<Logs> findAll ();
    Logs findByIdLog (int idLog);
    void deleteByIdLog (int idLog);
}
