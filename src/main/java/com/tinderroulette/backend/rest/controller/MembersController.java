package com.tinderroulette.backend.rest.controller;

import java.util.List;

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

import com.tinderroulette.backend.rest.Message;
import com.tinderroulette.backend.rest.dao.MembersDao;
import com.tinderroulette.backend.rest.exceptions.EmptyJsonResponse;
import com.tinderroulette.backend.rest.exceptions.MembersIntrouvableException;
import com.tinderroulette.backend.rest.model.Members;

@RestController
public class MembersController {

    private MembersDao membersDao;

    public MembersController(MembersDao membersDao) {
        this.membersDao = membersDao;
    }

    @GetMapping(value = "/members/{cip}/")
    public Members findByCip(@PathVariable String cip) {
        return membersDao.findByCip(cip);
    }

    @GetMapping(value = "/members/")
    public List<Members> findAll() {
        return membersDao.findAll();
    }

    @PostMapping(value = "/members/member")
    public ResponseEntity<Void> addMember(@Valid @RequestBody Members members) {
        Members memberstest = membersDao.findByCip(members.getCip());
        if (memberstest != null) {
            throw new MembersIntrouvableException(Message.format(Message.MEMBER_EXIST, members.getCip()));
        } else {
            Members memberput = membersDao.save(members);
            if (memberput == null) {
                return ResponseEntity.noContent().build();
            } else {
                return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
            }
        }

    }

    @PutMapping(value = "/members/member")
    public ResponseEntity<Void> updateMember(@Valid @RequestBody Members members) {
        Members memberstest = membersDao.findByCip(members.getCip());
        if (memberstest == null) {
            throw new MembersIntrouvableException(Message.format(Message.MEMBER_NOT_EXIST, members.getCip()));
        } else {
            Members memberput = membersDao.save(members);
            if (memberput == null) {
                return ResponseEntity.noContent().build();
            } else {
                return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
            }
        }

    }

    @DeleteMapping(value = "/members/{cip}/")
    public ResponseEntity<Void> deleteMember(@PathVariable String cip) {
        Members membertest = membersDao.findByCip(cip);
        if (membertest == null) {
            throw new MembersIntrouvableException(Message.format(Message.MEMBER_NOT_EXIST, cip));
        } else {
            membersDao.deleteByCip(cip);
            return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
        }
    }

}
