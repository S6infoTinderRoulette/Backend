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
import com.tinderroulette.backend.rest.dao.SwitchGroupRequestDao;
import com.tinderroulette.backend.rest.exceptions.EmptyJsonResponse;
import com.tinderroulette.backend.rest.exceptions.RequestIntrouvableException;
import com.tinderroulette.backend.rest.model.SwitchGroupRequest;

@RestController
public class SwitchGroupRequestController {
    private SwitchGroupRequestDao switchGroupRequestDao;
    private PrivilegeValidator validator;

    public SwitchGroupRequestController(SwitchGroupRequestDao switchGroupRequestDao, PrivilegeValidator validator) {
        this.switchGroupRequestDao = switchGroupRequestDao;
        this.validator = validator;
    }

    @GetMapping(value = "/switchGroupRequest/")
    public List<SwitchGroupRequest> findAll(@CookieValue("auth_user") Cookie userCookie,
            @CookieValue("auth_cred") Cookie credCookie) throws Exception {
        validator.validate(userCookie, credCookie, Status.Student, Status.Teacher, Status.Admin, Status.Support);
        return switchGroupRequestDao.findAll();
    }

    @PostMapping(value = "/switchGroupRequest/")
    public ResponseEntity<Void> addRequest(@Valid @RequestBody SwitchGroupRequest SwitchGroupRequest,
            @CookieValue("auth_user") Cookie userCookie, @CookieValue("auth_cred") Cookie credCookie) throws Exception {
        validator.validate(userCookie, credCookie, Status.Student, Status.Admin, Status.Support);
        SwitchGroupRequest requestTest = switchGroupRequestDao.findByCipAndIdClassAndIdGroup(
                SwitchGroupRequest.getCip(), SwitchGroupRequest.getIdClass(), SwitchGroupRequest.getIdGroup());
        if (requestTest != null) {
            throw new RequestIntrouvableException(Message.SWITCHREQUEST_EXIST.toString());
        } else {
            SwitchGroupRequest requestput = switchGroupRequestDao.save(SwitchGroupRequest);
            if (requestput == null) {
                return ResponseEntity.noContent().build();
            } else {
                return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
            }
        }

    }

    @PutMapping(value = "/switchGroupRequest/")
    public ResponseEntity<Void> updateRequest(@Valid @RequestBody SwitchGroupRequest SwitchGroupRequest,
            @CookieValue("auth_user") Cookie userCookie, @CookieValue("auth_cred") Cookie credCookie) throws Exception {
        validator.validate(userCookie, credCookie, Status.Admin, Status.Support);
        SwitchGroupRequest requestTest = switchGroupRequestDao.findByCipAndIdClassAndIdGroup(
                SwitchGroupRequest.getCip(), SwitchGroupRequest.getIdClass(), SwitchGroupRequest.getIdGroup());
        if (requestTest == null) {
            throw new RequestIntrouvableException(Message.SWITCHREQUEST_NOT_EXIST.toString());
        } else {
            SwitchGroupRequest requestput = switchGroupRequestDao.save(SwitchGroupRequest);
            if (requestput == null) {
                return ResponseEntity.noContent().build();
            } else {
                return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
            }
        }

    }

    @DeleteMapping(value = "/switchGroupRequest/{idgroup}/{cip}/{idclass}/")
    public ResponseEntity<Void> deleteRequest(@PathVariable int idgroup, @PathVariable String cip,
            @PathVariable String idclass, @CookieValue("auth_user") Cookie userCookie,
            @CookieValue("auth_cred") Cookie credCookie) throws Exception {
        validator.validate(userCookie, credCookie, Status.Admin, Status.Support);
        SwitchGroupRequest requestTest = switchGroupRequestDao.findByCipAndIdClassAndIdGroup(cip, idclass, idgroup);
        if (requestTest == null) {
            throw new RequestIntrouvableException(Message.SWITCHREQUEST_NOT_EXIST.toString());
        } else {
            switchGroupRequestDao.deleteByCipAndIdClassAndIdGroup(cip, idclass, idgroup);
            return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
        }
    }
}
