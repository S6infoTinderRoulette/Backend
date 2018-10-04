package com.tinderroulette.backend.rest.dao;

import com.tinderroulette.backend.rest.model.Activities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivitiesDao extends JpaRepository <Activities,String> {

    Activities save (Activities activities);
    List<Activities> findAll();
    Activities findByIdActivity (int idActivity);
    void deleteByIdActivity (int IdActivity);
}
