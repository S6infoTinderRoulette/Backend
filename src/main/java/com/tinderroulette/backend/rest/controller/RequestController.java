package com.tinderroulette.backend.rest.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
import com.tinderroulette.backend.rest.CAS.ConfigurationController;
import com.tinderroulette.backend.rest.CAS.PrivilegeValidator;
import com.tinderroulette.backend.rest.CAS.Status;
import com.tinderroulette.backend.rest.dao.RequestDao;
import com.tinderroulette.backend.rest.exceptions.EmptyJsonResponse;
import com.tinderroulette.backend.rest.exceptions.RequestIntrouvableException;
import com.tinderroulette.backend.rest.model.Request;

@RestController
public class RequestController {
    private RequestDao requestDao;
    private PrivilegeValidator validator;

    public RequestController(RequestDao requestDao, PrivilegeValidator validator) {
        this.requestDao = requestDao;
        this.validator = validator;
    }

    @GetMapping(value = "/request/")
    public List<Request> findAll(@CookieValue("auth_user") Cookie userCookie,
            @CookieValue("auth_cred") Cookie credCookie) throws Exception {
        validator.validate(userCookie, credCookie, Status.Student, Status.Admin, Status.Support);
        return requestDao.findAll();
    }

    @GetMapping(value = "/request/requested/")
    public List<Request> findAllRequested(@CookieValue("auth_user") Cookie userCookie,
            @CookieValue("auth_cred") Cookie credCookie) throws Exception {
        validator.validate(userCookie, credCookie, Status.Student, Status.Admin, Status.Support);
        String currUser = ConfigurationController.getAuthUser();
        return requestDao.findAll().stream().filter(o -> o.getCipRequested().equals(currUser))
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/request/seeking/")
    public List<Request> findAllSeeking(@CookieValue("auth_user") Cookie userCookie,
            @CookieValue("auth_cred") Cookie credCookie) throws Exception {
        validator.validate(userCookie, credCookie, Status.Student, Status.Admin, Status.Support);
        String currUser = ConfigurationController.getAuthUser();
        return requestDao.findAll().stream().filter(o -> o.getCipSeeking().equals(currUser))
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/request/duplicate/")
    public List<Request> findAllDuplicate(@CookieValue("auth_user") Cookie userCookie,
            @CookieValue("auth_cred") Cookie credCookie) throws Exception {
        validator.validate(userCookie, credCookie, Status.Student, Status.Admin, Status.Support);
        List<Request> requestedList = findAllRequested(userCookie, credCookie);
        List<Request> seekingList = findAllSeeking(userCookie, credCookie);
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
    public ResponseEntity<Void> addRequest(@Valid @RequestBody Request Request,
            @CookieValue("auth_user") Cookie userCookie, @CookieValue("auth_cred") Cookie credCookie) throws Exception {
        validator.validate(userCookie, credCookie, Status.Student, Status.Admin, Status.Support);
        Request requestTest = requestDao.findByCipRequestedAndCipSeekingAndIdActivity(Request.getCipRequested(),
                Request.getCipSeeking(), Request.getIdActivity());
        if (requestTest != null) {
            throw new RequestIntrouvableException(Message.REQUEST_EXIST.toString());
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
    public ResponseEntity<Void> updateRequest(@Valid @RequestBody Request Request,
            @CookieValue("auth_user") Cookie userCookie, @CookieValue("auth_cred") Cookie credCookie) throws Exception {
        validator.validate(userCookie, credCookie, Status.Admin, Status.Support);
        Request requestTest = requestDao.findByCipRequestedAndCipSeekingAndIdActivity(Request.getCipRequested(),
                Request.getCipSeeking(), Request.getIdActivity());
        if (requestTest == null) {
            throw new RequestIntrouvableException(Message.REQUEST_NOT_EXIST.toString());
        } else {
            Request requestput = requestDao.save(Request);
            if (requestput == null) {
                return ResponseEntity.noContent().build();
            } else {
                return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
            }
        }

    }

    @DeleteMapping(value = "/request/{ciprequested}/{cipseeking}/{idactivity}/")
    public ResponseEntity<Void> deleteRequest(@PathVariable String ciprequested, @PathVariable String cipseeking,
            @PathVariable int idactivity, @CookieValue("auth_user") Cookie userCookie,
            @CookieValue("auth_cred") Cookie credCookie) throws Exception {
        validator.validate(userCookie, credCookie, Status.Admin, Status.Support);
        Request requestTest = requestDao.findByCipRequestedAndCipSeekingAndIdActivity(ciprequested, cipseeking,
                idactivity);
        if (requestTest == null) {
            throw new RequestIntrouvableException(Message.REQUEST_NOT_EXIST.toString());
        } else {
            requestDao.deleteByCipRequestedAndCipSeekingAndIdActivity(ciprequested, cipseeking, idactivity);
            return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
        }
    }
}
