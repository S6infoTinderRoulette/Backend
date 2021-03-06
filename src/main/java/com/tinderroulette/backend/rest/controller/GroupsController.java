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
import com.tinderroulette.backend.rest.dao.GroupsDao;
import com.tinderroulette.backend.rest.exceptions.EmptyJsonResponse;
import com.tinderroulette.backend.rest.exceptions.GroupsIntrouvableException;
import com.tinderroulette.backend.rest.model.Groups;

@RestController
public class GroupsController {
    private GroupsDao groupsDao;
    private PrivilegeValidator validator;

    public GroupsController(GroupsDao groupsDao, PrivilegeValidator validator) {
        this.groupsDao = groupsDao;
        this.validator = validator;
    }

    @GetMapping(value = "/groups/{idGroup}/")
    public Groups findByIdGroup(@PathVariable int idGroup, @CookieValue("auth_user") Cookie userCookie,
            @CookieValue("auth_cred") Cookie credCookie) throws Exception {
        validator.validate(userCookie, credCookie, Status.Student, Status.Teacher, Status.Admin, Status.Support);
        return groupsDao.findByIdGroup(idGroup);
    }

    @GetMapping(value = "/groups/")
    public List<Groups> findAll(@CookieValue("auth_user") Cookie userCookie,
            @CookieValue("auth_cred") Cookie credCookie) throws Exception {
        validator.validate(userCookie, credCookie, Status.Teacher, Status.Admin, Status.Support);
        return groupsDao.findAll();
    }

    @PostMapping(value = "/groups/")
    public ResponseEntity<Void> addGroups(@Valid @RequestBody Groups groups,
            @CookieValue("auth_user") Cookie userCookie, @CookieValue("auth_cred") Cookie credCookie) throws Exception {
        validator.validate(userCookie, credCookie, Status.Admin, Status.Support);
        Groups groupsTest = groupsDao.findByIdGroup(groups.getIdGroup());
        if (groupsTest != null) {
            throw new GroupsIntrouvableException(Message.GROUP_EXIST.toString());
        } else {
            Groups groupsput = groupsDao.save(groups);
            if (groupsput == null) {
                return ResponseEntity.noContent().build();
            } else {
                return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
            }
        }

    }

    @PutMapping(value = "/groups/")
    public ResponseEntity<Void> updateGroups(@Valid @RequestBody Groups groups,
            @CookieValue("auth_user") Cookie userCookie, @CookieValue("auth_cred") Cookie credCookie) throws Exception {
        validator.validate(userCookie, credCookie, Status.Admin, Status.Support);
        Groups groupsTest = groupsDao.findByIdGroup(groups.getIdGroup());
        if (groupsTest == null) {
            throw new GroupsIntrouvableException(Message.GROUP_NOT_EXIST.toString());
        } else {
            Groups groupsput = groupsDao.save(groups);
            if (groupsput == null) {
                return ResponseEntity.noContent().build();
            } else {
                return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
            }
        }

    }

    @DeleteMapping(value = "/groups/{idGroup}/")
    public ResponseEntity<Void> deleteGroups(@PathVariable int idGroup, @CookieValue("auth_user") Cookie userCookie,
            @CookieValue("auth_cred") Cookie credCookie) throws Exception {
        validator.validate(userCookie, credCookie, Status.Admin, Status.Support);
        Groups groupsTest = groupsDao.findByIdGroup(idGroup);
        if (groupsTest == null) {
            throw new GroupsIntrouvableException(Message.GROUP_NOT_EXIST.toString());
        } else {
            groupsDao.deleteByIdGroup(idGroup);
            return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
        }
    }

}
