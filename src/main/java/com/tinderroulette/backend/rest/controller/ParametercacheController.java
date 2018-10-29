package com.tinderroulette.backend.rest.controller;

import com.tinderroulette.backend.rest.dao.GroupsDao;
import com.tinderroulette.backend.rest.dao.ParametercacheDao;
import com.tinderroulette.backend.rest.exceptions.EmptyJsonResponse;
import com.tinderroulette.backend.rest.exceptions.GroupsIntrouvableException;
import com.tinderroulette.backend.rest.exceptions.ParametercacheIntrouvableException;
import com.tinderroulette.backend.rest.model.Groups;
import com.tinderroulette.backend.rest.model.Parametercache;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ParametercacheController {

    private ParametercacheDao parametercacheDao ;

    public ParametercacheController(ParametercacheDao parametercacheDao) {
        this.parametercacheDao = parametercacheDao;
    }

    @GetMapping(value = "/parametercache/{cip}/{idClass}/{idGroupType}/")
    public Parametercache findByIdGroupType (@PathVariable int idGroupType, @PathVariable String cip, @PathVariable String idClass) {
        return parametercacheDao.findByCipAndIdClassAndIdGroupType(cip,idClass,idGroupType);
    }

    @GetMapping(value = "/parametercache/")
    public List<Parametercache> findAll() {
        return parametercacheDao.findAll();
    }

    @PostMapping(value = "/parametercache/")
    public ResponseEntity<Void> addParametercache (@Valid @RequestBody Parametercache parametercache) {
        Parametercache parametercacheTest = parametercacheDao.findByCipAndIdClassAndIdGroupType(parametercache.getCip(), parametercache.getIdClass(),parametercache.getIdGroupType());
        if (parametercacheTest != null) {
            throw new ParametercacheIntrouvableException("Le Parametercache correspondant est déjà présent dans la base de données");
        } else {
            Parametercache Parametercacheput = parametercacheDao.save(parametercache);
            if (Parametercacheput == null) {
                return ResponseEntity.noContent().build();
            } else {
                return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
            }
        }

    }

    @PutMapping(value = "/parametercache/")
    public ResponseEntity<Void> updateParametercache (@Valid @RequestBody Parametercache parametercache) {
        Parametercache parametercacheTest = parametercacheDao.findByCipAndIdClassAndIdGroupType(parametercache.getCip(), parametercache.getIdClass(),parametercache.getIdGroupType());
        if (parametercacheTest == null) {
            throw new ParametercacheIntrouvableException("Le Parametercache correspondant n'est pas présent dans la base de données");
        } else {
            Parametercache Parametercacheput = parametercacheDao.save(parametercache);
            if (Parametercacheput == null) {
                return ResponseEntity.noContent().build();
            } else {
                return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
            }
        }

    }

    @DeleteMapping(value = "/parametercache/{cip}/{idClass}/{idGroupType}/")
    public ResponseEntity<Void> deleteParametercache (@PathVariable int idGroupType, @PathVariable String cip, @PathVariable String idClass) {
        Parametercache parametercacheTest = parametercacheDao.findByCipAndIdClassAndIdGroupType(cip,idClass,idGroupType);
        if (parametercacheTest == null) {
            throw new ParametercacheIntrouvableException("Le Parametercache correspondant n'est pas présent dans la base de données");
        } else {
            parametercacheDao.deleteByCipAndIdClassAndIdGroupType(cip, idClass, idGroupType);
            return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
        }
    }

}