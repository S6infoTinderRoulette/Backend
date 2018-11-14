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
import com.tinderroulette.backend.rest.dao.AppDao;
import com.tinderroulette.backend.rest.exceptions.AppIntrouvableException;
import com.tinderroulette.backend.rest.exceptions.EmptyJsonResponse;
import com.tinderroulette.backend.rest.model.App;

@RestController
public class AppController {
    private AppDao appDao;
    private PrivilegeValidator validator;

    public AppController(AppDao appDao, PrivilegeValidator validator) {
        this.appDao = appDao;
        this.validator = validator;
    }

    @GetMapping(value = "/app/")
    public List<App> findAll(@CookieValue("auth_user") Cookie userCookie, @CookieValue("auth_cred") Cookie credCookie)
            throws Exception {
        validator.validate(userCookie, credCookie, Status.Teacher, Status.Admin, Status.Support);
        return appDao.findAll();
    }

    @PostMapping(value = "/app/")
    public ResponseEntity<Void> addApp(@Valid @RequestBody App app, @CookieValue("auth_user") Cookie userCookie,
            @CookieValue("auth_cred") Cookie credCookie) throws Exception {
        validator.validate(userCookie, credCookie, Status.Teacher, Status.Admin, Status.Support);
        App appTest = appDao.findByIdApp(app.getIdApp());
        if (appTest != null) {
            throw new AppIntrouvableException(Message.APP_EXIST.toString());
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
    public ResponseEntity<Void> updateApp(@Valid @RequestBody App app, @CookieValue("auth_user") Cookie userCookie,
            @CookieValue("auth_cred") Cookie credCookie) throws Exception {
        validator.validate(userCookie, credCookie, Status.Teacher, Status.Admin, Status.Support);
        App appTest = appDao.findByIdApp(app.getIdApp());
        if (appTest == null) {
            throw new AppIntrouvableException(Message.APP_NOT_EXIST.toString());
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
    public ResponseEntity<Void> deleteApp(@PathVariable String idApp, @CookieValue("auth_user") Cookie userCookie,
            @CookieValue("auth_cred") Cookie credCookie) throws Exception {
        validator.validate(userCookie, credCookie, Status.Teacher, Status.Admin, Status.Support);
        App appTest = appDao.findByIdApp(idApp);
        if (appTest == null) {
            throw new AppIntrouvableException(Message.APP_NOT_EXIST.toString());
        } else {
            appDao.deleteByIdApp(idApp);
            return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
        }
    }

}
