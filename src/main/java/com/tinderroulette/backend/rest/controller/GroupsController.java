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

    @GetMapping(value = "/groups/{idGroup}")
    public Groups findByIdGroup (@PathVariable int idGroup) {
        return groupsDao.findByIdGroup(idGroup);
    }

    @GetMapping(value = "/groups/")
    public List<Groups> findAll() {
        return groupsDao.findAll();
    }

    @PostMapping(value = "/groups/")
    public ResponseEntity<Void> addGroups (@Valid @RequestBody Groups groups) {
        Groups groupsTest = groupsDao.findByIdGroup(groups.getIdGroup());
        if (groupsTest != null) {
            throw new GroupsIntrouvableException("Le Groups correspondant est déjà présent dans la base de données");
        } else {
            Groups groupsput = groupsDao.save(groups);
            if (groupsput == null) {
                return ResponseEntity.noContent().build();
            } else {
                return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
            }
        }

    }

    @PutMapping(value = "/groups/")
    public ResponseEntity<Void> updateGroups (@Valid @RequestBody Groups groups) {
        Groups groupsTest = groupsDao.findByIdGroup(groups.getIdGroup());
        if (groupsTest == null) {
            throw new GroupsIntrouvableException("Le Groups correspondant n'est pas présent dans la base de données");
        } else {
            Groups groupsput = groupsDao.save(groups);
            if (groupsput == null) {
                return ResponseEntity.noContent().build();
            } else {
                return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
            }
        }

    }

    @DeleteMapping(value = "/groups/{idGroup}/")
    public ResponseEntity<Void> deleteGroups (@PathVariable int idGroup) {
        Groups groupsTest = groupsDao.findByIdGroup(idGroup);
        if (groupsTest == null) {
            throw new GroupsIntrouvableException("Le Groups correspondant n'est pas présent dans la base de données");
        } else {
            groupsDao.deleteByIdGroup(idGroup);
            return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
        }
    }

}
