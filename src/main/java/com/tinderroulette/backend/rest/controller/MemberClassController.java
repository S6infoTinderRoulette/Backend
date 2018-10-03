package com.tinderroulette.backend.rest.controller;

import com.tinderroulette.backend.rest.dao.MemberClassDao;
import com.tinderroulette.backend.rest.exceptions.EmptyJsonResponse;
import com.tinderroulette.backend.rest.exceptions.MemberClassIntrouvableException;
import com.tinderroulette.backend.rest.model.MemberClass;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class MemberClassController {

    private MemberClassDao memberClassDao;

    public MemberClassController(MemberClassDao memberClassDao) {
        this.memberClassDao = memberClassDao;
    }

    @GetMapping(value = "/memberclass/{cip}/{idClass}/")
    public MemberClass findByCip (@PathVariable String cip, @PathVariable String idClass) {
        return memberClassDao.findByCipAndAndIdClass(cip, idClass);
    }

    @GetMapping (value = "/memberclass/")
    public List<MemberClass> findAll () {
        return memberClassDao.findAll();
    }

    @PostMapping(value = "/memberclass/")
    public ResponseEntity<Void> addMemberClass (@Valid @RequestBody MemberClass memberclass){
        MemberClass memberClassTest = memberClassDao.findByCipAndAndIdClass(memberclass.getCip(), memberclass.getIdClass());
        if (memberClassTest != null) {
            throw new MemberClassIntrouvableException("Le membreclass correspondant est déjà présent dans la base de données");
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
    public ResponseEntity<Void> updateMemberClass (@Valid @RequestBody MemberClass memberClass){
        MemberClass memberClassTest = memberClassDao.findByCipAndAndIdClass(memberClass.getCip(),memberClass.getIdClass());
        if (memberClassTest == null) {
            throw new MemberClassIntrouvableException("Le membreclass correspondant n'est pas présent dans la base de données");
        } else {
            MemberClass memberClassPut = memberClassDao.save(memberClass);
            if (memberClassPut == null) {
                return ResponseEntity.noContent().build();
            } else {
                return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
            }
        }


    }

    @DeleteMapping (value = "/memberclass/{cip}/{idClass}")
    public ResponseEntity <Void> deleteMemberClass (@PathVariable String cip, @PathVariable String idClass) {
        MemberClass membertest = memberClassDao.findByCipAndAndIdClass(cip,idClass);
        if (membertest == null) {
            throw new MemberClassIntrouvableException("Le membreclass correspondant  n'est pas présent dans la base de données");
        } else {
            memberClassDao.deleteByCipAndIdClass(cip,idClass);
            return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
        }
    }

}
