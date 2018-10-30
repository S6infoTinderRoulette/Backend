package com.tinderroulette.backend.rest.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tinderroulette.backend.rest.CAS.ConfigurationController;
import com.tinderroulette.backend.rest.dao.ClassesDao;
import com.tinderroulette.backend.rest.exceptions.ClassesIntrouvableException;
import com.tinderroulette.backend.rest.exceptions.EmptyJsonResponse;
import com.tinderroulette.backend.rest.model.Classes;

@RestController
public class ClassesController {

    private ClassesDao classesDao;

    public ClassesController(ClassesDao classesDao) {
        this.classesDao = classesDao;
    }

    @GetMapping(value = "/classes/")
    public List<Classes> findAll () {
		System.out.println(ConfigurationController.getAuthUser());
        return classesDao.findAll();
    }

    @PostMapping(value = "/class/")
    public ResponseEntity<Void> addClasses (@Valid @RequestBody Classes classes){
        Classes classesTest = classesDao.findByIdAp(classes.getIdAp());
        if (classesTest != null) {
            throw new ClassesIntrouvableException("La Classes correspondante est déjà présente dans la base de données");
        } else {
            Classes classesput = classesDao.save(classes);
            if (classesput == null) {
                return ResponseEntity.noContent().build();
            } else {
                return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
            }
        }

    }

    @PutMapping(value = "/classes/")
    public ResponseEntity<Void> updateClasses (@Valid @RequestBody Classes classes){
        Classes classesTest = classesDao.findByIdAp(classes.getIdAp());
        if (classesTest == null) {
            throw new ClassesIntrouvableException("La Classes correspondante n'est pas présente dans la base de données");
        } else {
            Classes classesput = classesDao.save(classes);
            if (classesput == null) {
                return ResponseEntity.noContent().build();
            } else {
                return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
            }
        }

    }

    @DeleteMapping(value = "/classes/{idAp}/")
    public ResponseEntity<Void> deleteFriendlist (@PathVariable String idAp) {
        Classes classesTest = classesDao.findByIdAp(idAp);
        if (classesTest == null) {
            throw new ClassesIntrouvableException("La Classes correspondante n'est pas présente dans la base de données");
        } else {
            classesDao.deleteByIdAp(idAp);
            return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
        }
    }
}
