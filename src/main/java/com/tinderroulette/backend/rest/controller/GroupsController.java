package com.tinderroulette.backend.rest.controller;

import com.tinderroulette.backend.rest.dao.GroupsDao;
import com.tinderroulette.backend.rest.exceptions.EmptyJsonResponse;
import com.tinderroulette.backend.rest.exceptions.GroupsIntrouvableException;
import com.tinderroulette.backend.rest.model.Groups;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class GroupsController {

    private GroupsDao groupsDao ;

    public GroupsController(GroupsDao groupsDao) {
        this.groupsDao = groupsDao;
    }

    @GetMapping(value = "/groups/{idGroupType}")
    public Groups findByIdGroupType (@PathVariable int idGroupType) {
        return groupsDao.findByIdGroupType(idGroupType);
    }

    @GetMapping(value = "/groups/")
    public List<Groups> findAll() {
        return groupsDao.findAll();
    }

    @PostMapping(value = "/groups/")
    public ResponseEntity<Void> addGroups (@Valid @RequestBody Groups groups) {
        Groups groupsTest = groupsDao.findByIdGroupType(groups.getIdGroupType());
        if (groupsTest != null) {
            throw new GroupsIntrouvableException("Le Groups correspondant est déjà présent dans la base de données");
        } else {
            Groups Groupsput = groupsDao.save(groups);
            if (Groupsput == null) {
                return ResponseEntity.noContent().build();
            } else {
                return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
            }
        }

    }

    @PutMapping(value = "/groups/")
    public ResponseEntity<Void> updateGroups (@Valid @RequestBody Groups groups) {
        Groups groupsTest = groupsDao.findByIdGroupType(groups.getIdGroupType());
        if (groupsTest == null) {
            throw new GroupsIntrouvableException("Le MemberStatus correspondant n'est pas présent dans la base de données");
        } else {
            Groups Groupsput = groupsDao.save(groups);
            if (Groupsput == null) {
                return ResponseEntity.noContent().build();
            } else {
                return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
            }
        }

    }

    @DeleteMapping(value = "/groups/{idGroupType}/")
    public ResponseEntity<Void> deleteGroups (@PathVariable int idGroupType) {
        Groups groupsTest = groupsDao.findByIdGroupType(idGroupType);
        if (groupsTest == null) {
            throw new GroupsIntrouvableException("Le Groups correspondant n'est pas présent dans la base de données");
        } else {
            groupsDao.deleteByIdGroupType(idGroupType);
            return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
        }
    }

}
