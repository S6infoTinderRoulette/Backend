package com.tinderroulette.backend.rest.controller;

import com.tinderroulette.backend.rest.dao.RequestTypeDao;
import com.tinderroulette.backend.rest.exceptions.EmptyJsonResponse;
import com.tinderroulette.backend.rest.exceptions.RequestTypeIntrouvableException;
import com.tinderroulette.backend.rest.model.RequestType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

public class RequestTypeController {

    private RequestTypeDao requestTypeDao;

    public RequestTypeController(RequestTypeDao requestTypeDao) {
        this.requestTypeDao = requestTypeDao;
    }

    @GetMapping(value = "/requestType/")
    public List<RequestType> findAll () {
        return requestTypeDao.findAll();
    }

    @PostMapping(value = "/requestType/")
    public ResponseEntity<Void> addRequest (@Valid @RequestBody RequestType requestType){
        RequestType requestTypeTest = requestTypeDao.findByIdRequestType(requestType.getIdRequestType());
        if (requestTypeTest != null) {
            throw new RequestTypeIntrouvableException("La RequestType correspondante est déjà présente dans la base de données");
        } else {
            RequestType requestTypeput = requestTypeDao.save(requestType);
            if (requestTypeput == null) {
                return ResponseEntity.noContent().build();
            } else {
                return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
            }
        }

    }

    @PutMapping(value = "/requestType/")
    public ResponseEntity<Void> updateRequestType (@Valid @RequestBody RequestType requestType){
        RequestType requestTypeTest = requestTypeDao.findByIdRequestType(requestType.getIdRequestType());
        if (requestTypeTest == null) {
            throw new RequestTypeIntrouvableException("La RequestType correspondante n'est pas présente dans la base de données");
        } else {
            RequestType requestTypeput = requestTypeDao.save(requestType);
            if (requestTypeput == null) {
                return ResponseEntity.noContent().build();
            } else {
                return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
            }
        }

    }

    @DeleteMapping(value = "/requestType/{IdRequestType}/")
    public ResponseEntity<Void> deleteRequestType (@PathVariable int IdRequestType) {
        RequestType requestTest = requestTypeDao.findByIdRequestType(IdRequestType);
        if (requestTest == null) {
            throw new RequestTypeIntrouvableException("La request correspondante n'est pas présente dans la base de données");
        } else {
            requestTypeDao.deleteByIdRequestType(IdRequestType);
            return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
        }
    }
}
