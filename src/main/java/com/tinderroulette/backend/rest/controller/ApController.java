package com.tinderroulette.backend.rest.controller;

import com.tinderroulette.backend.rest.dao.ApDao;
import com.tinderroulette.backend.rest.exceptions.ApIntrouvableException;
import com.tinderroulette.backend.rest.exceptions.EmptyJsonResponse;
import com.tinderroulette.backend.rest.model.Ap;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ApController {

    private ApDao apDao;

    public ApController(ApDao apDao) {
        this.apDao = apDao;
    }

    @GetMapping(value = "/aps/")
    public List<Ap> findAll () {
        return apDao.findAll();
    }

    @PostMapping(value = "/ap/")
    public ResponseEntity<Void> addAp (@Valid @RequestBody Ap ap){
        Ap apTest = apDao.findByIdAp(ap.getIdAp());
        if (apTest != null) {
            throw new ApIntrouvableException("L'Ap correspondante est déjà présente dans la base de données");
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
    public ResponseEntity<Void> updateAp (@Valid @RequestBody Ap ap){
        Ap apTest = apDao.findByIdAp(ap.getIdAp());
        if (apTest == null) {
            throw new ApIntrouvableException("L'Ap correspondante n'est pas présente dans la base de données");
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
    public ResponseEntity<Void> deleteAp (@PathVariable String idAp) {
        Ap apTest = apDao.findByIdAp(idAp);
        if (apTest == null) {
            throw new ApIntrouvableException("L'Ap correspondante n'est pas présente dans la base de données");
        } else {
            apDao.deleteByIdAp(idAp);
            return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
        }
    }

}
