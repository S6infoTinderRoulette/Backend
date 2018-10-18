package com.tinderroulette.backend.rest.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.IntStream;

import org.json.JSONArray;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tinderroulette.backend.rest.dao.ClassesDao;
import com.tinderroulette.backend.rest.dao.GroupStudentDao;
import com.tinderroulette.backend.rest.dao.GroupTypeDao;
import com.tinderroulette.backend.rest.dao.GroupsDao;
import com.tinderroulette.backend.rest.dao.MemberClassDao;
import com.tinderroulette.backend.rest.exceptions.EmptyJsonResponse;
import com.tinderroulette.backend.rest.model.GroupStudent;
import com.tinderroulette.backend.rest.model.GroupType;
import com.tinderroulette.backend.rest.model.Groups;
import com.tinderroulette.backend.rest.model.MemberClass;

@RestController
public class PartitionneurController {

    private GroupsDao groupsDao;
    private GroupStudentDao groupStudentDao;
    private ClassesDao classesDao;
    private GroupTypeDao groupTypeDao;
    private MemberClassDao memberClassDao;

    public PartitionneurController(GroupsDao groupsDao, GroupStudentDao groupStudentDao, ClassesDao classesDao,
            GroupTypeDao groupTypeDao, MemberClassDao memberClassDao) {
        this.groupsDao = groupsDao;
        this.groupStudentDao = groupStudentDao;
        this.classesDao = classesDao;
        this.groupTypeDao = groupTypeDao;
        this.memberClassDao = memberClassDao;
    }

    @GetMapping(value = "/createGroup/{idClass}/")
    public List<MemberClass> createGroupNoParam(@PathVariable String idClass) {
        List<MemberClass> memberActivity = memberClassDao.findByIdClass(idClass);
        return memberActivity;
    }

    @GetMapping(value = "/createGroupPerNb/{idClass}/{nbMember}/")
    public List<List<MemberClass>> createGroupNbPerson(@PathVariable String idClass, @PathVariable int nbMember) {
        List<MemberClass> memberActivity = memberClassDao.findByIdClass(idClass);
        Collections.shuffle(memberActivity);
        List<List<MemberClass>> groups = new ArrayList<>();
        Iterator<MemberClass> iterator = memberActivity.iterator();
        while (iterator.hasNext()) {
            List<MemberClass> tempoList = new ArrayList<>();
            for (int j = 0; j < nbMember; j++) {
                if (iterator.hasNext()) {
                    tempoList.add(iterator.next());
                } else {
                    break;
                }
            }
            groups.add(tempoList);
        }
        return groups;
    }

    @GetMapping(value = "/createGroupPerType/{idClass}/{idGroupType}/")
    public List<List<MemberClass>> createGroupGroupType(@PathVariable String idClass, @PathVariable int idGroupType) {
        GroupType groupType = groupTypeDao.findByIdGroupType(idGroupType);
        List<MemberClass> studentActivity = memberClassDao.findByIdClass(idClass);
        Collections.shuffle(studentActivity);
        List<List<MemberClass>> groups = new ArrayList<>();
        Iterator<MemberClass> iterator = studentActivity.iterator();

        int smallestRemainder = Integer.MAX_VALUE;
        int optimalValue = 0;
        for (int i = 0; i <= (groupType.getMaxDefault() - groupType.getMinDefault()); i++) {
            int currentRemainder = (studentActivity.size() % (groupType.getMinDefault() + i));
            if (currentRemainder < smallestRemainder
                    || (currentRemainder == smallestRemainder && (groupType.getMinDefault() + i) > optimalValue)) {
                smallestRemainder = currentRemainder;
                optimalValue = groupType.getMinDefault() + i;
            }
        }

        while (iterator.hasNext()) {
            List<MemberClass> tempoList = new ArrayList<>();
            for (int j = 0; j < optimalValue; j++) {
                if (iterator.hasNext()) {
                    tempoList.add(iterator.next());
                } else {
                    break;
                }
            }
            groups.add(tempoList);
        }
        return groups;
    }

    @GetMapping(value = "/createGroupMultipleSize/{idClass}/{sizes}/")
    public List<List<MemberClass>> createGroupArraySizes(@PathVariable String idClass, @PathVariable int[] sizes)
            throws Exception {
        List<MemberClass> studentActivity = memberClassDao.findByIdClass(idClass);
        if (IntStream.of(sizes).sum() > studentActivity.size()) {
            throw new Exception("La somme des tailles des groupe est inférieure au nombre d'étudiants inscrits !");
        } else {
            Collections.shuffle(studentActivity);
            List<List<MemberClass>> groups = new ArrayList<>();
            Iterator<MemberClass> iterator = studentActivity.iterator();
            while (iterator.hasNext()) {
                for (int i = 0; i < sizes.length; i++) {
                    List<MemberClass> tempoList = new ArrayList<>();
                    for (int j = 0; j < sizes[i]; j++) {
                        if (iterator.hasNext()) {
                            tempoList.add(iterator.next());
                        } else {
                            break;
                        }
                    }
                    groups.add(tempoList);
                }
            }
            return groups;
        }
    }

    @GetMapping(value = "/createGroupMultipleSizeType/{idClass}/{idGroupType}/{sizes}/")
    public List<List<MemberClass>> createGroupGroupTypeAndArray(@PathVariable String idClass,
            @PathVariable int idGroupType, @PathVariable int[] sizes) {
        GroupType groupType = groupTypeDao.findByIdGroupType(idGroupType);
        List<MemberClass> studentActivity = memberClassDao.findByIdClass(idClass);
        Collections.shuffle(studentActivity);
        List<List<MemberClass>> groups = new ArrayList<>();
        Iterator<MemberClass> iteratorSizes = studentActivity.iterator();

        while (iteratorSizes.hasNext()) {
            for (int i = 0; i < sizes.length; i++) {
                List<MemberClass> tempoList = new ArrayList<>();
                for (int j = 0; j < sizes[i]; j++) {
                    if (iteratorSizes.hasNext()) {
                        tempoList.add(iteratorSizes.next());
                    } else {
                        break;
                    }
                }
                groups.add(tempoList);
            }
        }

        int smallestRemainder = Integer.MAX_VALUE;
        int optimalValue = 0;
        if (studentActivity.size() / groupType.getMinDefault() == 0) {
            optimalValue = studentActivity.size() - IntStream.of(sizes).sum();
        } else {
            for (int i = 0; i <= (groupType.getMaxDefault() - groupType.getMinDefault()); i++) {
                int currentRemainder = (studentActivity.size() % (groupType.getMinDefault() + i));
                if (currentRemainder < smallestRemainder
                        || (currentRemainder == smallestRemainder && (groupType.getMinDefault() + i) > optimalValue)) {
                    smallestRemainder = currentRemainder;
                    optimalValue = groupType.getMinDefault() + i;
                }
            }
        }

        while (iteratorSizes.hasNext()) {
            List<MemberClass> tempoList = new ArrayList<>();
            for (int j = 0; j < optimalValue; j++) {
                if (iteratorSizes.hasNext()) {
                    tempoList.add(iteratorSizes.next());
                } else {
                    break;
                }
            }
            groups.add(tempoList);
        }
        return groups;
    }

    @PostMapping(value = "/saveGroup/{idClass}/")
    public ResponseEntity saveGroup(HttpEntity<String> httpEntity, @PathVariable String idClass) {
        return saveGroupWithType(httpEntity, idClass, GroupType.types.Other.id);
    }

    @PostMapping(value = "/saveGroup/{idClass}/{idGroupType}/")
    public ResponseEntity saveGroupWithType(HttpEntity<String> httpEntity, @PathVariable String idClass,
            @PathVariable int idGroupType) {
        JSONArray classGroups = new JSONArray(httpEntity.getBody());
        saveGroupStudent(classGroups, idClass, idGroupType);
        return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
    }

    public List<List<GroupStudent>> saveGroupStudent(JSONArray classGroups, String idClass, int idGroupType) {
        List<List<GroupStudent>> groups = new ArrayList<>();
        for (int i = 0; i < classGroups.length(); i++) {
            JSONArray subGroup = classGroups.getJSONArray(i);
            Groups group = groupsDao.save(new Groups(idGroupType, null, idClass));
            List<GroupStudent> groupStudent = new ArrayList<>();
            for (int j = 0; j < subGroup.length(); j++) {
                groupStudent.add(new GroupStudent(subGroup.getJSONObject(j).getString("cip"), group.getIdGroup()));
            }
            groups.add(groupStudentDao.saveAll(groupStudent));
        }
        return groups;
    }
}
