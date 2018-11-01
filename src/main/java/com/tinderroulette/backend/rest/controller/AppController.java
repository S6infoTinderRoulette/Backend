package com.tinderroulette.backend.rest.controller;

import com.tinderroulette.backend.rest.dao.AppDao;
import com.tinderroulette.backend.rest.exceptions.AppIntrouvableException;
import com.tinderroulette.backend.rest.exceptions.EmptyJsonResponse;
import com.tinderroulette.backend.rest.model.App;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class AppController {

    private AppDao appDao;

    public AppController(AppDao appDao) {
        this.appDao = appDao;
    }

    @GetMapping(value = "/apps/")
    public List<App> findAll () {
        return appDao.findAll();
    }

    @PostMapping(value = "/app/")
    public ResponseEntity<Void> addApp (@Valid @RequestBody App app){
        App appTest = appDao.findByIdApp(app.getIdApp());
        if (appTest != null) {
            throw new AppIntrouvableException("L'App correspondante est déjà présente dans la base de données");
        } else {
            App appPut = appDao.save(app);
            if (appPut == null) {
                return ResponseEntity.noContent().build();
            } else {
                return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
            }
        }

    }

    @PutMapping(value = "/app/")
    public ResponseEntity<Void> updateApp (@Valid @RequestBody App app){
        App appTest = appDao.findByIdApp(app.getIdApp());
        if (appTest == null) {
            throw new AppIntrouvableException("L'App correspondante n'est pas présente dans la base de données");
        } else {
            App appPut = appDao.save(app);
            if (appPut == null) {
                return ResponseEntity.noContent().build();
            } else {
                return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
            }
        }
    }

    @DeleteMapping(value = "/app/{idApp}/")
    public ResponseEntity<Void> deleteApp (@PathVariable String idApp) {
        App appTest = appDao.findByIdApp(idApp);
        if (appTest == null) {
            throw new AppIntrouvableException("L'App correspondante n'est pas présente dans la base de données");
        } else {
            appDao.deleteByIdApp(idApp);
            return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
        }
    }

}
