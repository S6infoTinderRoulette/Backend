package com.tinderroulette.backend.rest.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.IntStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tinderroulette.backend.rest.Message;
import com.tinderroulette.backend.rest.dao.GroupStudentDao;
import com.tinderroulette.backend.rest.dao.GroupTypeDao;
import com.tinderroulette.backend.rest.dao.GroupsDao;
import com.tinderroulette.backend.rest.dao.MemberClassDao;
import com.tinderroulette.backend.rest.exceptions.EmptyJsonResponse;
import com.tinderroulette.backend.rest.exceptions.GroupsIntrouvableException;
import com.tinderroulette.backend.rest.model.GroupStudent;
import com.tinderroulette.backend.rest.model.GroupType;
import com.tinderroulette.backend.rest.model.Groups;
import com.tinderroulette.backend.rest.model.MemberClass;

@RestController
public class PartitionneurController {

    private GroupsDao groupsDao;
    private GroupStudentDao groupStudentDao;
    private GroupTypeDao groupTypeDao;
    private MemberClassDao memberClassDao;

    public PartitionneurController(GroupsDao groupsDao, GroupStudentDao groupStudentDao, GroupTypeDao groupTypeDao,
            MemberClassDao memberClassDao) {
        this.groupsDao = groupsDao;
        this.groupStudentDao = groupStudentDao;
        this.groupTypeDao = groupTypeDao;
        this.memberClassDao = memberClassDao;
    }

    @PostMapping(value = "/createGroup/")
    public List<List<MemberClass>> createGroup(HttpEntity<String> httpEntity) throws Exception {
        JSONObject params = new JSONObject(httpEntity.getBody());
        List<List<MemberClass>> finalList;
        boolean[] binParam = { params.has("idClass"), params.has("idGroupType"), params.has("nbMember"),
                params.has("sizes") };

        int n = 0, l = binParam.length;
        for (int i = 0; i < l; ++i) {
            n = (n << 1) + (binParam[i] ? 1 : 0);
        }
        switch (n) {
        case 0b1000:
            finalList = createGroupNoParam(params.getString("idClass"));
            break;
        case 0b1001:
            JSONArray arr = params.getJSONArray("sizes");
            int[] sizesInt = new int[arr.length()];
            for (int i = 0; i < sizesInt.length; i++) {
                sizesInt[i] = arr.getInt(i);
            }
            finalList = createGroupArraySizes(params.getString("idClass"), sizesInt);
            break;
        case 0b1110:
            finalList = createGroupNbPerson(params.getString("idClass"), params.getInt("nbMember"));
            break;
        case 0b1100:
            finalList = createGroupGroupType(params.getString("idClass"), params.getInt("idGroupType"));
            break;
        case 0b1101:
            arr = params.getJSONArray("sizes");
            sizesInt = new int[arr.length()];
            for (int i = 0; i < sizesInt.length; i++) {
                sizesInt[i] = arr.getInt(i);
            }
            finalList = createGroupGroupTypeAndArray(params.getString("idClass"), params.getInt("idGroupType"),
                    sizesInt);
            break;
        default:
            throw new Exception(Message.PARTITIONNEUR_PARAM_ERROR.toString());
        }
        return finalList;
    }

    public List<Integer> createGroups(int nbStudent, int max) {
        int nbGroups = (int) Math.ceil((double) nbStudent / max);
        List<Integer> optimalValues = new ArrayList();
        for (int i = 0; i < nbGroups; i++) {
            optimalValues.add((int) Math.floor((double) nbStudent / nbGroups));
        }
        for (int i = 0; i < nbStudent % nbGroups; i++) {
            optimalValues.set(i, optimalValues.get(i) + 1);
        }

        return optimalValues;
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
        List<Integer> optimalValues = createGroups(memberActivity.size(), nbMember);

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
            ++index;
            groups.add(tempoList);
        }
        return groups;
    }

    public List<List<MemberClass>> createGroupGroupType(@PathVariable String idClass, @PathVariable int idGroupType) {
        GroupType groupType = groupTypeDao.findByIdGroupType(idGroupType);
        List<List<MemberClass>> groups = new ArrayList<>();
        List<MemberClass> studentActivity = memberClassDao.findByIdClass(idClass);
        Collections.shuffle(studentActivity);
        Iterator<MemberClass> iterator = studentActivity.iterator();
        List<Integer> optimalValues = createGroups(studentActivity.size(), groupType.getMaxDefault());
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
            throw new Exception(Message.PARTITIONNEUR_SUM_EXCEED.toString());
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

        List<Integer> optimalValues = createGroups(studentActivity.size() - IntStream.of(sizes).sum(),
                groupType.getMaxDefault());
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

    @PostMapping(value = "/saveGroup/{idClass}/{idGroupType}/")
    public ResponseEntity saveGroupWithType(HttpEntity<String> httpEntity, @PathVariable String idClass,
            @PathVariable int idGroupType) {
        JSONArray classGroups = new JSONArray(httpEntity.getBody());
        saveGroupStudent(classGroups, idClass, idGroupType);
        return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
    }

    public List<List<GroupStudent>> saveGroupStudent(JSONArray classGroups, String idClass, int idGroupType) {
        List<Groups> grpTest = groupsDao.findByIdClassAndIdGroupType(idClass, idGroupType);
        if (idGroupType == 3 && !grpTest.isEmpty()) {
            throw new GroupsIntrouvableException(Message.PARTITIONNEUR_TUTORAT_EXIST.toString());
        } else {
            List<List<GroupStudent>> groups = new ArrayList<>();
            int indexMax = 0;
            if (!grpTest.isEmpty() && idGroupType != 3) {
                for (int i = 0; i < grpTest.size(); i++) {
                    if (grpTest.get(i).getGroupIndex() > indexMax) {
                        indexMax = grpTest.get(i).getGroupIndex();
                    }
                }
            }

            int index = 0;
            for (int i = 0; i < classGroups.length(); i++) {
                JSONArray subGroup = classGroups.getJSONArray(i);
                Groups group;
                if (!subGroup.isEmpty()) {
                    if (idGroupType == 3) {
                        group = groupsDao.save(new Groups(idGroupType, null, idClass, index + 1));
                    } else {
                        group = groupsDao.save(new Groups(idGroupType, null, idClass, indexMax + 1));
                    }
                    List<GroupStudent> groupStudent = new ArrayList<>();
                    for (int j = 0; j < subGroup.length(); j++) {
                        groupStudent
                                .add(new GroupStudent(subGroup.getJSONObject(j).getString("cip"), group.getIdGroup()));
                    }
                    groups.add(groupStudentDao.saveAll(groupStudent));
                    index++;
                }
            }
            return groups;
        }
    }
}
