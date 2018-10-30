package com.tinderroulette.backend.rest.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.IntStream;

import com.tinderroulette.backend.rest.exceptions.GroupsIntrouvableException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping (value = "/createGroup/")
    public List<List<MemberClass>> createGroup (HttpEntity<String> httpEntity) throws Exception {
        JSONObject params = new JSONObject(httpEntity.getBody());
        List<List<MemberClass>> finalList;
        boolean [] binParam = {params.has("idClass"),params.has("idGroupType"),params.has("nbMember"),params.has("sizes")};

        int n = 0, l = binParam.length;
        for (int i = 0; i < l; ++i) {
            n = (n << 1) + (binParam[i] ? 1 : 0);
        }
        switch (n) {
            case  8 : finalList = createGroupNoParam(params.getString("idClass"));
                      break;
            case  9 : String sizes = params.getString("sizes");
                      String[] integerStrings = sizes.split(",");
                      int[] sizesInt = new int[integerStrings.length];
                      for (int i = 0; i < sizesInt.length; i++){
                         sizesInt[i] = Integer.parseInt(integerStrings[i]);
                      }
                      finalList = createGroupArraySizes(params.getString("idClass"),sizesInt);
                      break;
            case 10 : finalList = createGroupNbPerson(params.getString("idClass"),params.getInt("nbMember"));
                      break;
            case 12 : finalList = createGroupGroupType(params.getString("idClass"),params.getInt("idGroupType"));
                      break;
            case 13 : sizes = params.getString("sizes");
                      integerStrings = sizes.split(",");
                      sizesInt = new int[integerStrings.length];
                      for (int i = 0; i < sizesInt.length; i++){
                          sizesInt[i] = Integer.parseInt(integerStrings[i]);
                      }
                      finalList = createGroupGroupTypeAndArray(params.getString("idClass"),params.getInt("idGroupType"),sizesInt);
                      break;
            default : throw new Exception("Paramètres de post incohérents");
        }
        return finalList;
    }

    public List<List<MemberClass>> createGroupNoParam(@PathVariable String idClass) {
        List<List<MemberClass>> memberActivity = new ArrayList<>();
        List<MemberClass> member = memberClassDao.findByIdClass(idClass);
        memberActivity.add(member);
        return memberActivity;
    }

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

    public List<List<MemberClass>> createGroupGroupType(@PathVariable String idClass, @PathVariable int idGroupType) {
        GroupType groupType = groupTypeDao.findByIdGroupType(idGroupType);
        List<MemberClass> studentActivity = memberClassDao.findByIdClass(idClass);
        Collections.shuffle(studentActivity);
        List<List<MemberClass>> groups = new ArrayList<>();
        Iterator<MemberClass> iterator = studentActivity.iterator();

        int smallestRemainder = Integer.MAX_VALUE;
        int totalStudent = studentActivity.size();
        List<Integer> optimalValues = new ArrayList();
        for (int i = 0; i <= (groupType.getMaxDefault() - groupType.getMinDefault()); i++) {
            int currentGroupSize = groupType.getMinDefault() + i;
            int currentRemainder = totalStudent % currentGroupSize;
            int currentNumberGroup = totalStudent / currentGroupSize;
            if (currentRemainder <= (groupType.getMaxDefault() - currentGroupSize) * currentNumberGroup
                    || totalStudent <= currentGroupSize) {
                currentRemainder = 0;
            }
            if (smallestRemainder >= (currentGroupSize + currentRemainder)) {
                smallestRemainder = currentGroupSize + currentRemainder;
                optimalValues = new ArrayList();
                for (int j = 0; j < currentGroupSize; j++) {
                    optimalValues.add(groupType.getMinDefault() + i);
                }
                if (currentRemainder == 0) {
                    int diff = groupType.getMaxDefault() - currentGroupSize;
                    for (int j = 0; j < totalStudent % currentGroupSize; j++) {
                        optimalValues.set(j % diff, optimalValues.get(j % diff) + 1);
                    }
                } 
                else {
                    optimalValues.add(currentRemainder);
                }
            }
        }

        int index = 0;
        while (iterator.hasNext()) {
            List<MemberClass> tempoList = new ArrayList<>();
            for (int j = 0; j < optimalValues.get(index); j++) {
                if (iterator.hasNext()) {
                    tempoList.add(iterator.next());
                } else {
                    break;
                }
            }
            groups.add(tempoList);
            index++;
        }
        return groups;
    }

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

    public List<List<MemberClass>> createGroupGroupTypeAndArray(@PathVariable String idClass,
            @PathVariable int idGroupType, @PathVariable int[] sizes) {
        GroupType groupType = groupTypeDao.findByIdGroupType(idGroupType);
        List<MemberClass> studentActivity = memberClassDao.findByIdClass(idClass);
        Collections.shuffle(studentActivity);
        List<List<MemberClass>> groups = new ArrayList<>();
        Iterator<MemberClass> iterator = studentActivity.iterator();

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
        
        int smallestRemainder = Integer.MAX_VALUE;
        int totalStudent = studentActivity.size() - IntStream.of(sizes).sum();
        List<Integer> optimalValues = new ArrayList();
        for (int i = 0; i <= (groupType.getMaxDefault() - groupType.getMinDefault()); i++) {
            int currentGroupSize = groupType.getMinDefault() + i;
            int currentRemainder = totalStudent % currentGroupSize;
            int currentNumberGroup = totalStudent / currentGroupSize;
            if (currentRemainder <= (groupType.getMaxDefault() - currentGroupSize) * currentNumberGroup
                    || totalStudent <= currentGroupSize) {
                currentRemainder = 0;
            }
            if (smallestRemainder >= (currentGroupSize + currentRemainder)) {
                smallestRemainder = currentGroupSize + currentRemainder;
                optimalValues = new ArrayList();
                for (int j = 0; j < currentGroupSize; j++) {
                    optimalValues.add(groupType.getMinDefault() + i);
                }
                if (currentRemainder == 0) {
                    int diff = groupType.getMaxDefault() - currentGroupSize;
                    for (int j = 0; j < totalStudent % currentGroupSize; j++) {
                        optimalValues.set(j % diff, optimalValues.get(j % diff) + 1);
                    }
                }
                else {
                    optimalValues.add(currentRemainder);
                }
            }
        }

        int index = 0;
        while (iterator.hasNext()) {
            List<MemberClass> tempoList = new ArrayList<>();
            for (int j = 0; j < optimalValues.get(index); j++) {
                if (iterator.hasNext()) {
                    tempoList.add(iterator.next());
                } else {
                    break;
                }
            }
            groups.add(tempoList);
            index++;
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
        List<Groups> grpTest = groupsDao.findByIdClassAndIdGroupType(idClass,idGroupType);
        if (idGroupType == 3 && !grpTest.isEmpty()) {
            throw new GroupsIntrouvableException("Ce tutorat existe déjà dans la base de données !");
        } else {
            List<List<GroupStudent>> groups = new ArrayList<>();
            int indexMax = 0;
            if (!grpTest.isEmpty() && idGroupType != 3) {
                for (int i = 0; i < grpTest.size(); i++) {
                    if (grpTest.get(i).getGroupIndex()> indexMax) {
                        indexMax = grpTest.get(i).getGroupIndex();
                    }
                }
            }
            for (int i = 0; i < classGroups.length(); i++) {
                JSONArray subGroup = classGroups.getJSONArray(i);
                Groups group;
                if (idGroupType == 3) {
                    group = groupsDao.save(new Groups(idGroupType, null, idClass, i+1));
                } else {
                    group = groupsDao.save(new Groups(idGroupType, null, idClass, indexMax+1));
                }
                List<GroupStudent> groupStudent = new ArrayList<>();
                for (int j = 0; j < subGroup.length(); j++) {
                    groupStudent.add(new GroupStudent(subGroup.getJSONObject(j).getString("cip"), group.getIdGroup()));
                }
                groups.add(groupStudentDao.saveAll(groupStudent));
            }
            return groups;
        }
    }
}
