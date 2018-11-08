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
import com.tinderroulette.backend.rest.dao.MemberClassDao;
import com.tinderroulette.backend.rest.exceptions.EmptyJsonResponse;
import com.tinderroulette.backend.rest.exceptions.MemberClassIntrouvableException;
import com.tinderroulette.backend.rest.model.MemberClass;

@RestController
public class MemberClassController {

    private MemberClassDao memberClassDao;

    public MemberClassController(MemberClassDao memberClassDao) {
        this.memberClassDao = memberClassDao;
    }

    @GetMapping(value = "/memberclass/{cip}/{idClass}/")
    public MemberClass findByCipAndIdClass(@PathVariable String cip, @PathVariable String idClass) {
        return memberClassDao.findByCipAndIdClass(cip, idClass);
    }

    @GetMapping(value = "/memberclass/{idClass}/")
    public int findNumberOfStudentByClass(@PathVariable String idClass) {
        return memberClassDao.findByIdClass(idClass).size();
    }

    @GetMapping(value = "/memberclass/student/{cip}/")
    public List<MemberClass> findClassesofStudent (@PathVariable String cip) {
        return memberClassDao.findByCip(cip);
    }

    @GetMapping (value = "/memberclass/")
    public List<MemberClass> findAll () {
        return memberClassDao.findAll();
    }

    @PostMapping(value = "/memberclass/")
    public ResponseEntity<Void> addMemberClass(@Valid @RequestBody MemberClass memberclass) {
        MemberClass memberClassTest = memberClassDao.findByCipAndIdClass(memberclass.getCip(),
                memberclass.getIdClass());
        if (memberClassTest != null) {
            throw new MemberClassIntrouvableException(Message.MEMBERCLASS_EXIST.toString());
        } else {
            MemberClass memberClassPut = memberClassDao.save(memberclass);
            if (memberClassPut == null) {
                return ResponseEntity.noContent().build();
            } else {
                return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
            }
        }

    }

    @PutMapping(value = "/memberclass/")
    public ResponseEntity<Void> updateMemberClass(@Valid @RequestBody MemberClass memberClass) {
        MemberClass memberClassTest = memberClassDao.findByCipAndIdClass(memberClass.getCip(),
                memberClass.getIdClass());
        if (memberClassTest == null) {
            throw new MemberClassIntrouvableException(Message.MEMBERCLASS_NOT_EXIST.toString());
        } else {
            MemberClass memberClassPut = memberClassDao.save(memberClass);
            if (memberClassPut == null) {
                return ResponseEntity.noContent().build();
            } else {
                return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
            }
        }

    }

    @DeleteMapping(value = "/memberclass/{cip}/{idClass}")
    public ResponseEntity<Void> deleteMemberClass(@PathVariable String cip, @PathVariable String idClass) {
        MemberClass membertest = memberClassDao.findByCipAndIdClass(cip, idClass);
        if (membertest == null) {
            throw new MemberClassIntrouvableException(Message.MEMBERCLASS_NOT_EXIST.toString());
        } else {
            memberClassDao.deleteByCipAndIdClass(cip, idClass);
            return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
        }
    }

}
