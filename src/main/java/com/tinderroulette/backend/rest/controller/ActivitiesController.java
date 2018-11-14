package com.tinderroulette.backend.rest.controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tinderroulette.backend.rest.Message;
import com.tinderroulette.backend.rest.CAS.PrivilegeValidator;
import com.tinderroulette.backend.rest.CAS.Status;
import com.tinderroulette.backend.rest.dao.ActivitiesDao;
import com.tinderroulette.backend.rest.exceptions.ActivitiesIntrouvableException;
import com.tinderroulette.backend.rest.exceptions.EmptyJsonResponse;
import com.tinderroulette.backend.rest.model.Activities;

@RestController
public class ActivitiesController {

    private ActivitiesDao activitiesDao;
    private PrivilegeValidator validator;

    public ActivitiesController(ActivitiesDao activitiesDao, PrivilegeValidator validator) {
        this.activitiesDao = activitiesDao;
        this.validator = validator;
    }

    @GetMapping(value = "/activities/")
    public List<Activities> findAll(@CookieValue("auth_user") Cookie userCookie,
            @CookieValue("auth_cred") Cookie credCookie) throws Exception {
        validator.validate(userCookie, credCookie, Status.Student, Status.Teacher, Status.Admin, Status.Support);
        return activitiesDao.findAll();
    }

    @PostMapping(value = "/activities/")
    public ResponseEntity<Void> addActivities(@Valid @RequestBody Activities activities,
            @CookieValue("auth_user") Cookie userCookie, @CookieValue("auth_cred") Cookie credCookie) throws Exception {
        validator.validate(userCookie, credCookie, Status.Teacher, Status.Admin, Status.Support);
        Activities activitiesTest = activitiesDao.findByIdActivity(activities.getIdActivity());
        if (activitiesTest != null) {
            throw new ActivitiesIntrouvableException(Message.ACTIVITY_EXIST.toString());
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
    public ResponseEntity<Void> updateActivities(@Valid @RequestBody Activities activities,
            @CookieValue("auth_user") Cookie userCookie, @CookieValue("auth_cred") Cookie credCookie) throws Exception {
        validator.validate(userCookie, credCookie, Status.Teacher, Status.Admin, Status.Support);
        Activities activitiesTest = activitiesDao.findByIdActivity(activities.getIdActivity());
        if (activitiesTest == null) {
            throw new ActivitiesIntrouvableException(Message.ACTIVITY_NOT_EXIST.toString());
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
    public ResponseEntity<Void> deleteActivities(@PathVariable int idActivity,
            @CookieValue("auth_user") Cookie userCookie, @CookieValue("auth_cred") Cookie credCookie) throws Exception {
        validator.validate(userCookie, credCookie, Status.Teacher, Status.Admin, Status.Support);
        Activities activitiesTest = activitiesDao.findByIdActivity(idActivity);
        if (activitiesTest == null) {
            throw new ActivitiesIntrouvableException(Message.ACTIVITY_NOT_EXIST.toString());
        } else {
            activitiesDao.deleteByIdActivity(idActivity);
            return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
        }
    }

}
