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
import com.tinderroulette.backend.rest.dao.ApDao;
import com.tinderroulette.backend.rest.exceptions.ApIntrouvableException;
import com.tinderroulette.backend.rest.exceptions.EmptyJsonResponse;
import com.tinderroulette.backend.rest.model.Ap;

@RestController
public class ApController {
    private ApDao apDao;
    private PrivilegeValidator validator;

    public ApController(ApDao apDao, PrivilegeValidator validator) {
        this.apDao = apDao;
        this.validator = validator;
    }

    @GetMapping(value = "/ap/")
    public List<Ap> findAll(@CookieValue("auth_user") Cookie userCookie, @CookieValue("auth_cred") Cookie credCookie)
            throws Exception {
        validator.validate(userCookie, credCookie, Status.Teacher, Status.Admin, Status.Support);
        return apDao.findAll();
    }

    @PostMapping(value = "/ap/")
    public ResponseEntity<Void> addAp(@Valid @RequestBody Ap ap, @CookieValue("auth_user") Cookie userCookie,
            @CookieValue("auth_cred") Cookie credCookie) throws Exception {
        validator.validate(userCookie, credCookie, Status.Teacher, Status.Admin, Status.Support);
        Ap apTest = apDao.findByIdAp(ap.getIdAp());
        if (apTest != null) {
            throw new ApIntrouvableException(Message.AP_EXIST.toString());
        } else {
            Ap apPut = apDao.save(ap);
            if (apPut == null) {
                return ResponseEntity.noContent().build();
            } else {
                return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
            }
        }

    }

    @PutMapping(value = "/ap/")
    public ResponseEntity<Void> updateAp(@Valid @RequestBody Ap ap, @CookieValue("auth_user") Cookie userCookie,
            @CookieValue("auth_cred") Cookie credCookie) throws Exception {
        validator.validate(userCookie, credCookie, Status.Teacher, Status.Admin, Status.Support);
        Ap apTest = apDao.findByIdAp(ap.getIdAp());
        if (apTest == null) {
            throw new ApIntrouvableException(Message.AP_NOT_EXIST.toString());
        } else {
            Ap apPut = apDao.save(ap);
            if (apPut == null) {
                return ResponseEntity.noContent().build();
            } else {
                return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
            }
        }
    }

    @DeleteMapping(value = "/ap/{idAp}/")
    public ResponseEntity<Void> deleteAp(@PathVariable String idAp, @CookieValue("auth_user") Cookie userCookie,
            @CookieValue("auth_cred") Cookie credCookie) throws Exception {
        validator.validate(userCookie, credCookie, Status.Teacher, Status.Admin, Status.Support);
        Ap apTest = apDao.findByIdAp(idAp);
        if (apTest == null) {
            throw new ApIntrouvableException((Message.AP_NOT_EXIST.toString()));
        } else {
            apDao.deleteByIdAp(idAp);
            return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
        }
    }

}
