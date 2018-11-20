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
import com.tinderroulette.backend.rest.dao.GroupTypeDao;
import com.tinderroulette.backend.rest.exceptions.EmptyJsonResponse;
import com.tinderroulette.backend.rest.exceptions.GroupTypeIntrouvableException;
import com.tinderroulette.backend.rest.model.GroupType;

@RestController
public class GroupTypeController {

    private GroupTypeDao groupTypeDao;
    private PrivilegeValidator validator;

    public GroupTypeController(GroupTypeDao groupTypeDao, PrivilegeValidator validator) {
        this.groupTypeDao = groupTypeDao;
        this.validator = validator;
    }

    @GetMapping(value = "/grouptype/")
    public List<GroupType> findAll(@CookieValue("auth_user") Cookie userCookie,
            @CookieValue("auth_cred") Cookie credCookie) throws Exception {
        validator.validate(userCookie, credCookie, Status.Student, Status.Teacher, Status.Admin, Status.Support);
        return groupTypeDao.findAll();
    }

    @GetMapping(value = "/grouptype/{type}/")
    public GroupType findByType(@PathVariable String type, @CookieValue("auth_user") Cookie userCookie,
            @CookieValue("auth_cred") Cookie credCookie) throws Exception {
        validator.validate(userCookie, credCookie, Status.Student, Status.Teacher, Status.Admin, Status.Support);
        return groupTypeDao.findByType(type);
    }

    @PostMapping(value = "/grouptype/")
    public ResponseEntity<Void> addGroupType(@Valid @RequestBody GroupType groupType,
            @CookieValue("auth_user") Cookie userCookie, @CookieValue("auth_cred") Cookie credCookie) throws Exception {
        validator.validate(userCookie, credCookie, Status.Teacher, Status.Admin, Status.Support);
        GroupType groupTest = groupTypeDao.findByIdGroupType(groupType.getIdGroupType());
        if (groupTest != null) {
            throw new GroupTypeIntrouvableException(
                    Message.format(Message.GROUPTYPE_EXIST, groupType.getIdGroupType()));
        } else {
            GroupType groupTypePut = groupTypeDao.save(groupType);
            if (groupTypePut == null) {
                return ResponseEntity.noContent().build();
            } else {
                return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
            }
        }

    }

    @GetMapping(value = "/grouptype/defaultValue/{idGroupId}/")
    public Integer getDefaultGroupSize(@PathVariable int idGroupId, @CookieValue("auth_user") Cookie userCookie,
            @CookieValue("auth_cred") Cookie credCookie) throws Exception {
        validator.validate(userCookie, credCookie, Status.Student, Status.Teacher, Status.Admin, Status.Support);
        return groupTypeDao.findByIdGroupType(idGroupId).getMaxDefault();
    }

    @PutMapping(value = "/grouptype/")
    public ResponseEntity<Void> updateGroupType(@Valid @RequestBody GroupType groupType,
            @CookieValue("auth_user") Cookie userCookie, @CookieValue("auth_cred") Cookie credCookie) throws Exception {
        validator.validate(userCookie, credCookie, Status.Teacher, Status.Admin, Status.Support);
        GroupType groupTest = groupTypeDao.findByIdGroupType(groupType.getIdGroupType());
        if (groupTest == null) {
            throw new GroupTypeIntrouvableException(
                    Message.format(Message.GROUPTYPE_NOT_EXIST, groupType.getIdGroupType()));
        } else {
            GroupType groupTypePut = groupTypeDao.save(groupType);
            if (groupTypePut == null) {
                return ResponseEntity.noContent().build();
            } else {
                return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
            }
        }

    }

    @DeleteMapping(value = "/grouptype/{idGroupId}/")
    public ResponseEntity<Void> deleteGroupType(@PathVariable int idGroupId,
            @CookieValue("auth_user") Cookie userCookie, @CookieValue("auth_cred") Cookie credCookie) throws Exception {
        validator.validate(userCookie, credCookie, Status.Teacher, Status.Admin, Status.Support);
        GroupType groupTest = groupTypeDao.findByIdGroupType(idGroupId);
        if (groupTest == null) {
            throw new GroupTypeIntrouvableException(Message.format(Message.GROUPTYPE_NOT_EXIST, idGroupId));
        } else {
            groupTypeDao.deleteByIdGroupType(idGroupId);
            return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
        }
    }

}
