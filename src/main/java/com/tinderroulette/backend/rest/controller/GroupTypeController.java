package com.tinderroulette.backend.rest.controller;


import com.tinderroulette.backend.rest.dao.GroupTypeDao;
import com.tinderroulette.backend.rest.exceptions.EmptyJsonResponse;
import com.tinderroulette.backend.rest.exceptions.GroupTypeIntrouvableException;
import com.tinderroulette.backend.rest.exceptions.MembersIntrouvableException;
import com.tinderroulette.backend.rest.model.GroupType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class GroupTypeController {

    private GroupTypeDao groupTypeDao;

    public GroupTypeController(GroupTypeDao groupTypeDao) {
        this.groupTypeDao = groupTypeDao;
    }

    @GetMapping (value = "/grouptypes/")
    public List<GroupType> findAll () {
        return groupTypeDao.findAll();
    }

    @GetMapping (value = "/grouptype/{type}")
    public GroupType findByType (@PathVariable String type){
        return groupTypeDao.findByType(type);
    }

    @PostMapping (value = "/grouptype/")
    public ResponseEntity<Void> addGroupType (@Valid @RequestBody GroupType groupType){
        GroupType groupTest = groupTypeDao.findByIdGroupType(groupType.getIdGroupType());
        if (groupTest != null) {
            throw new GroupTypeIntrouvableException("Le GroupType correspondant au groupType : " + groupType.getIdGroupType() + " est déjà présent dans la base de données");
        } else {
            GroupType groupTypePut = groupTypeDao.save(groupType);
            if (groupTypePut == null) {
                return ResponseEntity.noContent().build();
            } else {
                return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
            }
        }

    }

    @GetMapping (value = "/grouptype/defaultValue/{idGroupId}/")
    public Integer getDefaultGroupSize(@PathVariable int idGroupId){return groupTypeDao.findByIdGroupType(idGroupId).getMaxDefault();}

    @PutMapping (value = "/grouptype/")
    public ResponseEntity<Void> updateGroupType (@Valid @RequestBody GroupType groupType){
        GroupType groupTest = groupTypeDao.findByIdGroupType(groupType.getIdGroupType());
        if (groupTest == null) {
            throw new GroupTypeIntrouvableException("Le GroupType correspondant au groupType : " + groupType.getIdGroupType() + " est déjà présent dans la base de données");
        } else {
            GroupType groupTypePut = groupTypeDao.save(groupType);
            if (groupTypePut == null) {
                return ResponseEntity.noContent().build();
            } else {
                return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
            }
        }

    }

    @DeleteMapping (value = "/grouptype/{idGroupId}/")
    public ResponseEntity <Void> deleteGroupType (@PathVariable int idGroupId) {
        GroupType groupTest = groupTypeDao.findByIdGroupType(idGroupId);
        if (groupTest == null) {
            throw new GroupTypeIntrouvableException("Le GroupType correspondant au groupType : " + idGroupId + " n'est pas présent dans la base de données");
        } else {
            groupTypeDao.deleteByIdGroupType(idGroupId);
            return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
        }
    }



}
