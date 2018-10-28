package com.tinderroulette.backend.rest.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
import com.tinderroulette.backend.rest.dao.RequestDao;
import com.tinderroulette.backend.rest.exceptions.EmptyJsonResponse;
import com.tinderroulette.backend.rest.exceptions.RequestIntrouvableException;
import com.tinderroulette.backend.rest.model.Request;

@RestController
public class RequestController {

    private RequestDao requestDao;

    public RequestController(RequestDao requestDao) {
        this.requestDao = requestDao;
    }

    @GetMapping(value = "/request/")
    public List<Request> findAll () {
        return requestDao.findAll();
    }

    @GetMapping(value = "/request/requested/")
    public List<Request> findAllRequested() {
        String currUser = ConfigurationController.getAuthUser();
        return requestDao.findAll().stream().filter(o -> o.getCipRequested().equals(currUser))
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/request/seeking/")
    public List<Request> findAllSeeking() {
        String currUser = ConfigurationController.getAuthUser();
        return requestDao.findAll().stream().filter(o -> o.getCipSeeking().equals(currUser))
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/request/duplicate/")
    public List<Request> findAllDuplicate() {
        List<Request> requestedList = findAllRequested();
        List<Request> seekingList = findAllSeeking();
        List<Request> duplicateList = new ArrayList<Request>();
        for (Request requested : requestedList) {
            for (Request seeking : seekingList) {
                if (requested.getCipSeeking().equals(seeking.getCipRequested())) {
                    duplicateList.add(requested);
                }
            }
        }
        return duplicateList;
    }

    @PostMapping(value = "/request/")
    public ResponseEntity<Void> addRequest (@Valid @RequestBody Request Request){
        Request requestTest = requestDao.findByCipRequestedAndCipSeekingAndIdActivityAndIdRequestType(Request.getCipRequested(),Request.getCipSeeking(),Request.getIdActivity(),Request.getIdRequestType());
        if (requestTest != null) {
            throw new RequestIntrouvableException("La Request correspondante est déjà présente dans la base de données");
        } else {
            Request requestput = requestDao.save(Request);
            if (requestput == null) {
                return ResponseEntity.noContent().build();
            } else {
                return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
            }
        }

    }

    @PutMapping(value = "/request/")
    public ResponseEntity<Void> updateRequest (@Valid @RequestBody Request Request){
        Request requestTest = requestDao.findByCipRequestedAndCipSeekingAndIdActivityAndIdRequestType(Request.getCipRequested(),Request.getCipSeeking(),Request.getIdActivity(),Request.getIdRequestType());
        if (requestTest == null) {
            throw new RequestIntrouvableException("La Request correspondante n'est pas présente dans la base de données");
        } else {
            Request requestput = requestDao.save(Request);
            if (requestput == null) {
                return ResponseEntity.noContent().build();
            } else {
                return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
            }
        }

    }

    @DeleteMapping(value = "/request/{ciprequested}/{cipseeking}/{idactivity}/{idrequesttype}/")
    public ResponseEntity<Void> deleteRequest (@PathVariable String ciprequested, @PathVariable String cipseeking, @PathVariable int idactivity, @PathVariable int idrequesttype ) {
        Request requestTest = requestDao.findByCipRequestedAndCipSeekingAndIdActivityAndIdRequestType(ciprequested,cipseeking,idactivity,idrequesttype);
        if (requestTest == null) {
            throw new RequestIntrouvableException("La request correspondante n'est pas présente dans la base de données");
        } else {
            requestDao.deleteByCipRequestedAndCipSeekingAndIdActivityAndIdRequestType(ciprequested,cipseeking,idactivity,idrequesttype);
            return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
        }
    }
}
