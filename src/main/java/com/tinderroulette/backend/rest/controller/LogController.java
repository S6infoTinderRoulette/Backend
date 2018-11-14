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

import com.tinderroulette.backend.rest.CAS.PrivilegeValidator;
import com.tinderroulette.backend.rest.CAS.Status;
import com.tinderroulette.backend.rest.dao.LogDao;
import com.tinderroulette.backend.rest.exceptions.EmptyJsonResponse;
import com.tinderroulette.backend.rest.exceptions.LogIntrouvableException;
import com.tinderroulette.backend.rest.model.Logs;

@RestController
public class LogController {

    private LogDao logDao;
    private PrivilegeValidator validator;

    public LogController(LogDao logDao, PrivilegeValidator validator) {
        this.logDao = logDao;
        this.validator = validator;
    }

    @GetMapping(value = "/logs/")
    public List<Logs> findAll(@CookieValue("auth_user") Cookie userCookie, @CookieValue("auth_cred") Cookie credCookie)
            throws Exception {
        validator.validate(userCookie, credCookie, Status.Admin, Status.Support);
        return logDao.findAll();
    }

    @GetMapping(value = "/logs/{idLog}")
    public Logs findByIdLog(@PathVariable int idLog, @CookieValue("auth_user") Cookie userCookie,
            @CookieValue("auth_cred") Cookie credCookie) throws Exception {
        validator.validate(userCookie, credCookie, Status.Admin, Status.Support);
        return logDao.findByIdLog(idLog);
    }

    @PostMapping(value = "/logs/")
    public ResponseEntity<Void> addLog(@Valid @RequestBody Logs log, @CookieValue("auth_user") Cookie userCookie,
            @CookieValue("auth_cred") Cookie credCookie) throws Exception {
        validator.validate(userCookie, credCookie, Status.Admin, Status.Support);
        Logs logTest = logDao.findByIdLog(log.getIdLog());
        if (logTest != null) {
            throw new LogIntrouvableException("Le log correspondant est déjà présent dans la base de données");
        } else {
            Logs logPut = logDao.save(log);
            if (logPut == null) {
                return ResponseEntity.noContent().build();
            } else {
                return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
            }
        }

    }

    @PutMapping(value = "/logs/")
    public ResponseEntity<Void> updateLog(@Valid @RequestBody Logs log, @CookieValue("auth_user") Cookie userCookie,
            @CookieValue("auth_cred") Cookie credCookie) throws Exception {
        validator.validate(userCookie, credCookie, Status.Admin, Status.Support);
        Logs logTest = logDao.findByIdLog(log.getIdLog());
        if (logTest == null) {
            throw new LogIntrouvableException("Le log correspondant est déjà présent dans la base de données");
        } else {
            Logs logPut = logDao.save(log);
            if (logPut == null) {
                return ResponseEntity.noContent().build();
            } else {
                return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
            }
        }

    }

    @DeleteMapping(value = "/logs/{idLog}/")
    public ResponseEntity<Void> deleteLog(@PathVariable int idLog, @CookieValue("auth_user") Cookie userCookie,
            @CookieValue("auth_cred") Cookie credCookie) throws Exception {
        validator.validate(userCookie, credCookie, Status.Admin, Status.Support);
        Logs logTest = logDao.findByIdLog(idLog);
        if (logTest == null) {
            throw new LogIntrouvableException("Le membre correspondant n'est pas présent dans la base de données");
        } else {
            logDao.deleteByIdLog(idLog);
            return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
        }
    }

}
