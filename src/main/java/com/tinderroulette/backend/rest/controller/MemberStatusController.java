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
import com.tinderroulette.backend.rest.dao.MemberStatusDao;
import com.tinderroulette.backend.rest.exceptions.EmptyJsonResponse;
import com.tinderroulette.backend.rest.exceptions.MemberStatusIntrouvableException;
import com.tinderroulette.backend.rest.model.MemberStatus;

@RestController
public class MemberStatusController {

    private MemberStatusDao memberStatusDao;
    private PrivilegeValidator validator;

    public MemberStatusController(MemberStatusDao memberStatusDao, PrivilegeValidator validator) {
        this.memberStatusDao = memberStatusDao;
        this.validator = validator;
    }

    @GetMapping(value = "/memberStatus/{idMemberStatus}/")
    public MemberStatus findByCip(@PathVariable int idMemberStatus, @CookieValue("auth_user") Cookie userCookie,
            @CookieValue("auth_cred") Cookie credCookie) throws Exception {
        validator.validate(userCookie, credCookie, Status.Student, Status.Teacher, Status.Admin, Status.Support);
        return memberStatusDao.findByIdMemberStatus(idMemberStatus);
    }

    @GetMapping(value = "/memberStatus/")
    public List<MemberStatus> findAll(@CookieValue("auth_user") Cookie userCookie,
            @CookieValue("auth_cred") Cookie credCookie) throws Exception {
        validator.validate(userCookie, credCookie, Status.Student, Status.Teacher, Status.Admin, Status.Support);
        return memberStatusDao.findAll();
    }

    @PostMapping(value = "/memberStatus/")
    public ResponseEntity<Void> addMemberStatus(@Valid @RequestBody MemberStatus memberStatus,
            @CookieValue("auth_user") Cookie userCookie, @CookieValue("auth_cred") Cookie credCookie) throws Exception {
        validator.validate(userCookie, credCookie, Status.Student, Status.Teacher, Status.Admin, Status.Support);
        MemberStatus memberStatustest = memberStatusDao.findByIdMemberStatus(memberStatus.getIdMemberStatus());
        if (memberStatustest != null) {
            throw new MemberStatusIntrouvableException(
                    Message.format(Message.MEMBERSTATUS_EXIST, memberStatus.getIdMemberStatus()));
        } else {
            MemberStatus memberStatusput = memberStatusDao.save(memberStatus);
            if (memberStatusput == null) {
                return ResponseEntity.noContent().build();
            } else {
                return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
            }
        }

    }

    @PutMapping(value = "/memberStatus/")
    public ResponseEntity<Void> updateMemberStatus(@Valid @RequestBody MemberStatus memberStatus,
            @CookieValue("auth_user") Cookie userCookie, @CookieValue("auth_cred") Cookie credCookie) throws Exception {
        validator.validate(userCookie, credCookie, Status.Student, Status.Teacher, Status.Admin, Status.Support);
        MemberStatus memberStatustest = memberStatusDao.findByIdMemberStatus(memberStatus.getIdMemberStatus());
        if (memberStatustest == null) {
            throw new MemberStatusIntrouvableException(
                    Message.format(Message.MEMBERSTATUS_NOT_EXIST, memberStatus.getIdMemberStatus()));
        } else {
            MemberStatus memberstatusput = memberStatusDao.save(memberStatus);
            if (memberstatusput == null) {
                return ResponseEntity.noContent().build();
            } else {
                return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
            }
        }

    }

    @DeleteMapping(value = "/memberStatus/{idMemberStatus}/")
    public ResponseEntity<Void> deleteMemberStatus(@PathVariable int idMemberStatus,
            @CookieValue("auth_user") Cookie userCookie, @CookieValue("auth_cred") Cookie credCookie) throws Exception {
        validator.validate(userCookie, credCookie, Status.Student, Status.Teacher, Status.Admin, Status.Support);
        MemberStatus memberStatustest = memberStatusDao.findByIdMemberStatus(idMemberStatus);
        if (memberStatustest == null) {
            throw new MemberStatusIntrouvableException(Message.format(Message.MEMBERSTATUS_NOT_EXIST, idMemberStatus));
        } else {
            memberStatusDao.deleteByIdMemberStatus(idMemberStatus);
            return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
        }
    }

}
