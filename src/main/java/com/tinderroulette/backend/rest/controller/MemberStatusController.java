package com.tinderroulette.backend.rest.controller;


import com.tinderroulette.backend.rest.dao.MemberStatusDao;
import com.tinderroulette.backend.rest.exceptions.EmptyJsonResponse;
import com.tinderroulette.backend.rest.exceptions.MemberStatusIntrouvableException;
import com.tinderroulette.backend.rest.model.MemberStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class MemberStatusController {

    private MemberStatusDao memberStatusDao ;

    public MemberStatusController(MemberStatusDao memberStatusDao) {
        this.memberStatusDao = memberStatusDao;
    }

    @GetMapping(value = "/memberStatus/{idMemberStatus}/")
    public MemberStatus findByCip(@PathVariable int idMemberStatus) {
        return memberStatusDao.findByIdMemberStatus(idMemberStatus);
    }

    @GetMapping(value = "/memberStatus/")
    public List<MemberStatus> findAll() {
        return memberStatusDao.findAll();
    }

    @PostMapping(value = "/memberStatus/")
    public ResponseEntity<Void> addMemberStatus (@Valid @RequestBody MemberStatus memberStatus) {
        MemberStatus memberStatustest = memberStatusDao.findByIdMemberStatus(memberStatus.getIdMemberStatus());
        if (memberStatustest != null) {
            throw new MemberStatusIntrouvableException("Le MemberStatus correspondant à l'Id  : " + memberStatus.getIdMemberStatus() + " est déjà présent dans la base de données");
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
    public ResponseEntity<Void> updateMemberStatus (@Valid @RequestBody MemberStatus memberStatus) {
        MemberStatus memberStatustest = memberStatusDao.findByIdMemberStatus(memberStatus.getIdMemberStatus());
        if (memberStatustest == null) {
            throw new MemberStatusIntrouvableException("Le MemberStatus correspondant à l'Id  : " + memberStatus.getIdMemberStatus() + " n'est pas présent dans la base de données");
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
    public ResponseEntity<Void> deleteMemberStatus (@PathVariable int idMemberStatus) {
        MemberStatus memberStatustest = memberStatusDao.findByIdMemberStatus(idMemberStatus);
        if (memberStatustest == null) {
            throw new MemberStatusIntrouvableException("Le MemberStatus correspondant à l'Id  : " + idMemberStatus + " n'est pas présent dans la base de données");
        } else {
            memberStatusDao.deleteByIdMemberStatus(idMemberStatus);
            return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
        }
    }

}

