package com.tinderroulette.backend.rest.controller;

import com.tinderroulette.backend.rest.dao.MembersDao;
import com.tinderroulette.backend.rest.exceptions.EmptyJsonResponse;
import com.tinderroulette.backend.rest.exceptions.MembersIntrouvableException;
import com.tinderroulette.backend.rest.model.Members;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class MembersController {

    private MembersDao membersDao;

    public MembersController(MembersDao membersDao) {
        this.membersDao = membersDao;
    }

    @GetMapping(value = "/members/{cip}/")
    public Members findByCip (@PathVariable String cip) {
        return membersDao.findByCip(cip);
    }

    @GetMapping (value = "/members/")
    public List <Members> findAll () {
        return membersDao.findAll();
    }

    @PostMapping (value = "/members/member")
    public ResponseEntity<Void> addMember (@Valid @RequestBody Members members){
        Members memberstest = membersDao.findByCip(members.getCip());
        if (memberstest != null) {
            throw new MembersIntrouvableException("Le membre correspondant au CIP : " + members.getCip() + " est déjà présent dans la base de données");
        } else {
            Members memberput = membersDao.save(members);
            if (memberput == null) {
                return ResponseEntity.noContent().build();
            } else {
                return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
            }
        }

    }

    @PutMapping (value = "/members/member")
    public ResponseEntity<Void> updateMember (@Valid @RequestBody Members members){
        Members memberstest = membersDao.findByCip(members.getCip());
        if (memberstest == null) {
            throw new MembersIntrouvableException("Le membre correspondant au CIP : " + members.getCip() + " n'est pas présent dans la base de données");
        } else {
            Members memberput = membersDao.save(members);
            if (memberput == null) {
                return ResponseEntity.noContent().build();
            } else {
                return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
            }
        }

    }

    @DeleteMapping (value = "/members/{cip}/")
    public ResponseEntity <Void> deleteMember (@PathVariable String cip) {
        Members membertest = membersDao.findByCip(cip);
        if (membertest == null) {
            throw new MembersIntrouvableException("Le membre correspondant au CIP : " + cip + " n'est pas présent dans la base de données");
        } else {
            membersDao.deleteByCip(cip);
            return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
        }
    }


}
