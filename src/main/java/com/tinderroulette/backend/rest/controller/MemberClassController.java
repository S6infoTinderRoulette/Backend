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

import javax.validation.Valid;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping (value = "/memberclass/connected/")
    public List<MemberClass> findConnectedUserClasses () {
        String currUser = "pelm2528";//ConfigurationController.getAuthUser();
        List<MemberClass> classes = memberClassDao.findByCip(currUser)
                                                  .stream()
                                                  .filter(c -> checkIfCurrentClasses(c.getIdClass()))
                                                  .collect(Collectors.toList());
        return classes;
    }

    private boolean checkIfCurrentClasses(String idClass) {
        char classSemester = idClass.charAt(idClass.length() - 3);
        String classYear = idClass.substring(idClass.length() - 2);

        int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
        int currentSemester = (int) Math.floor(currentMonth/4);

        String currentYear = Integer.toString(Calendar.getInstance().get(Calendar.YEAR));
        currentYear = currentYear.substring(currentYear.length() - 2);

        return (currentYear.equals(classYear)
                && ((currentSemester == 0 && classSemester == 'H')
                || (currentSemester == 1 && classSemester == 'E')
                || (currentSemester == 2 && classSemester == 'A')));
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
