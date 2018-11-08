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

import com.tinderroulette.backend.rest.Message;
import com.tinderroulette.backend.rest.dao.ApDao;
import com.tinderroulette.backend.rest.exceptions.ApIntrouvableException;
import com.tinderroulette.backend.rest.exceptions.EmptyJsonResponse;
import com.tinderroulette.backend.rest.model.Ap;

@RestController
public class ApController {

    private ApDao apDao;

    public ApController(ApDao apDao) {
        this.apDao = apDao;
    }

    @GetMapping(value = "/aps/")
    public List<Ap> findAll() {
        return apDao.findAll();
    }

    @PostMapping(value = "/ap/")
    public ResponseEntity<Void> addAp(@Valid @RequestBody Ap ap) {
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
    public ResponseEntity<Void> updateAp(@Valid @RequestBody Ap ap) {
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
    public ResponseEntity<Void> deleteAp(@PathVariable String idAp) {
        Ap apTest = apDao.findByIdAp(idAp);
        if (apTest == null) {
            throw new ApIntrouvableException((Message.AP_NOT_EXIST.toString()));
        } else {
            apDao.deleteByIdAp(idAp);
            return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
        }
    }

}
