package com.tinderroulette.backend.rest.controller;

import com.tinderroulette.backend.rest.dao.LogDao;
import com.tinderroulette.backend.rest.exceptions.EmptyJsonResponse;
import com.tinderroulette.backend.rest.exceptions.LogIntrouvableException;
import com.tinderroulette.backend.rest.model.Logs;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class LogController {

    private LogDao logDao;

    public LogController(LogDao logDao) {
        this.logDao = logDao;
    }

    @GetMapping(value = "/logs/")
    public List<Logs> findAll () {
        return logDao.findAll();
    }

    @GetMapping (value = "/logs/{idLog}")
    public Logs findByIdLog (@PathVariable int idLog){
        return logDao.findByIdLog(idLog);
    }

    @PostMapping(value = "/logs/")
    public ResponseEntity<Void> addLog (@Valid @RequestBody Logs log){
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
    public ResponseEntity<Void> updateLog (@Valid @RequestBody Logs log){
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

    @DeleteMapping (value = "/logs/{idLog}/")
    public ResponseEntity <Void> deleteLog (@PathVariable int idLog) {
        Logs logTest = logDao.findByIdLog(idLog);
        if (logTest == null) {
            throw new LogIntrouvableException("Le membre correspondant n'est pas présent dans la base de données");
        } else {
            logDao.deleteByIdLog(idLog);
            return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
        }
    }


}
