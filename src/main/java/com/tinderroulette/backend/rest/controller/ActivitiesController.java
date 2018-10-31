package com.tinderroulette.backend.rest.controller;

import com.tinderroulette.backend.rest.dao.ActivitiesDao;
import com.tinderroulette.backend.rest.exceptions.ActivitiesIntrouvableException;
import com.tinderroulette.backend.rest.exceptions.EmptyJsonResponse;
import com.tinderroulette.backend.rest.model.Activities;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ActivitiesController {

    private ActivitiesDao activitiesDao;

    public ActivitiesController(ActivitiesDao activitiesDao) {
        this.activitiesDao = activitiesDao;
    }

    @GetMapping(value = "/activities/")
    public List<Activities> findAll () {
        return activitiesDao.findAll();
    }

    @PostMapping(value = "/activities/")
    public ResponseEntity<Void> addActivities (@Valid @RequestBody Activities activities){
        Activities activitiesTest = activitiesDao.findByIdActivity(activities.getIdActivity());
        if (activitiesTest != null) {
            throw new ActivitiesIntrouvableException("L'Activities correspondante est déjà présente dans la base de données");
        } else {
            Activities activitiesPut = activitiesDao.save(activities);
            if (activitiesPut == null) {
                return ResponseEntity.noContent().build();
            } else {
                return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
            }
        }

    }

    @PutMapping(value = "/activities/")
    public ResponseEntity<Void> updateActivities (@Valid @RequestBody Activities activities){
        Activities activitiesTest = activitiesDao.findByIdActivity(activities.getIdActivity());
        if (activitiesTest == null) {
            throw new ActivitiesIntrouvableException("L'Activities correspondante n'est pas présente dans la base de données");
        } else {
            Activities activitiesPut = activitiesDao.save(activities);
            if (activitiesPut == null) {
                return ResponseEntity.noContent().build();
            } else {
                return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
            }
        }
    }

    @DeleteMapping(value = "/activities/{idActivity}/")
    public ResponseEntity<Void> deleteActivities (@PathVariable int idActivity) {
        Activities activitiesTest = activitiesDao.findByIdActivity(idActivity);
        if (activitiesTest == null) {
            throw new ActivitiesIntrouvableException("L'Activities correspondante n'est pas présente dans la base de données");
        } else {
            activitiesDao.deleteByIdActivity(idActivity);
            return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
        }
    }

}
