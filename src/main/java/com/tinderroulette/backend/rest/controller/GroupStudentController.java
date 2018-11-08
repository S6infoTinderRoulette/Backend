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
import com.tinderroulette.backend.rest.dao.GroupStudentDao;
import com.tinderroulette.backend.rest.exceptions.EmptyJsonResponse;
import com.tinderroulette.backend.rest.exceptions.GroupStudentIntrouvableException;
import com.tinderroulette.backend.rest.exceptions.GroupTypeIntrouvableException;
import com.tinderroulette.backend.rest.model.GroupStudent;

@RestController
public class GroupStudentController {

    private GroupStudentDao groupStudentDao;

    public GroupStudentController(GroupStudentDao groupStudentDao) {
        this.groupStudentDao = groupStudentDao;
    }

    @GetMapping(value = "/groupstudents/")
    public List<GroupStudent> findAll() {
        return groupStudentDao.findAll();
    }

    @GetMapping(value = "/groupstudent/{cip}/{idGroup}")
    public GroupStudent findByCipAndIdGroup(@PathVariable String cip, @PathVariable int idGroup) {
        return groupStudentDao.findByCipAndIdGroup(cip, idGroup);
    }

    @PostMapping(value = "/groupstudent/")
    public ResponseEntity<Void> addGroupStudent(@Valid @RequestBody GroupStudent groupStudent) {
        GroupStudent groupTest = groupStudentDao.findByCipAndIdGroup(groupStudent.getCip(), groupStudent.getIdGroup());
        if (groupTest != null) {
            throw new GroupStudentIntrouvableException(Message.GROUPSTUDENT_EXIST.toString());
        } else {
            GroupStudent groupStudentPut = groupStudentDao.save(groupStudent);
            if (groupStudentPut == null) {
                return ResponseEntity.noContent().build();
            } else {
                return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
            }
        }

    }

    @PutMapping(value = "/groupstudent/")
    public ResponseEntity<Void> updateGroupStudent(@Valid @RequestBody GroupStudent groupStudent) {
        GroupStudent groupTest = groupStudentDao.findByCipAndIdGroup(groupStudent.getCip(), groupStudent.getIdGroup());
        if (groupTest == null) {
            throw new GroupStudentIntrouvableException(Message.GROUPSTUDENT_NOT_EXIST.toString());
        } else {
            GroupStudent groupStudentPut = groupStudentDao.save(groupStudent);
            if (groupStudentPut == null) {
                return ResponseEntity.noContent().build();
            } else {
                return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
            }
        }

    }

    @DeleteMapping(value = "/groupstudent/{cip}/{idGroup}")
    public ResponseEntity<Void> deleteGroupStudent(@PathVariable String cip, @PathVariable int idGroup) {
        GroupStudent groupTest = groupStudentDao.findByCipAndIdGroup(cip, idGroup);
        if (groupTest == null) {
            throw new GroupTypeIntrouvableException(Message.GROUPSTUDENT_NOT_EXIST.toString());
        } else {
            groupStudentDao.deleteByCipAndIdGroup(cip, idGroup);
            return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
        }
    }

}
