package com.tinderroulette.backend.rest.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.Cookie;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tinderroulette.backend.rest.Message;
import com.tinderroulette.backend.rest.CAS.ConfigurationController;
import com.tinderroulette.backend.rest.CAS.PrivilegeValidator;
import com.tinderroulette.backend.rest.CAS.Status;
import com.tinderroulette.backend.rest.dao.ActivitiesDao;
import com.tinderroulette.backend.rest.dao.GroupStudentDao;
import com.tinderroulette.backend.rest.dao.GroupsDao;
import com.tinderroulette.backend.rest.dao.MatchMakingDao;
import com.tinderroulette.backend.rest.model.Activities;
import com.tinderroulette.backend.rest.model.GroupStudent;
import com.tinderroulette.backend.rest.model.Groups;
import com.tinderroulette.backend.rest.model.MemberClass;
import com.tinderroulette.backend.rest.notification.NotificationData;
import com.tinderroulette.backend.rest.notification.NotificationDispatcher;

@RestController
public class MatchMakingController {
    private MatchMakingDao matchmakingDao;
    private PrivilegeValidator validator;
    private GroupsDao groupsDao;
    private GroupStudentDao groupStudentDao;
    private ActivitiesDao activitiesDao;

    public MatchMakingController(MatchMakingDao matchmakingDao, GroupsDao groupsDao, GroupStudentDao groupStudentDao,
            ActivitiesDao activitiesDao, PrivilegeValidator validator) {
        this.matchmakingDao = matchmakingDao;
        this.validator = validator;
        this.groupsDao = groupsDao;
        this.groupStudentDao = groupStudentDao;
        this.activitiesDao = activitiesDao;
    }

    @GetMapping(value = "/matchmaking/{idActivity}/")
    public HashMap<Integer, List<GroupStudent>> getTeams(@PathVariable int idActivity,
            @CookieValue("auth_user") Cookie userCookie, @CookieValue("auth_cred") Cookie credCookie) throws Exception {
        validator.validate(userCookie, credCookie, Status.Student, Status.Teacher, Status.Admin, Status.Support);
        List<Groups> activityGroups = groupsDao.findByIdActivity(idActivity);
        HashMap<Integer, List<GroupStudent>> groupStudents = new HashMap<>();
        for (Groups group : activityGroups) {
            groupStudents.put(group.getIdGroup(), groupStudentDao.findByIdGroup(group.getIdGroup()));
        }
        return groupStudents;
    }

    @GetMapping(value = "/matchmaking/userstatus/{idActivity}/{getOpen}/")
    public List<GroupStudent> isUserTeamFull(@PathVariable int idActivity, @PathVariable boolean getOpen,
            @CookieValue("auth_user") Cookie userCookie, @CookieValue("auth_cred") Cookie credCookie) throws Exception {
        String currUser = validator.validate(userCookie, credCookie, Status.Student, Status.Admin, Status.Support);
        List<GroupStudent> groupStudents = getOpen ? matchmakingDao.findAllIncompleteGroups(idActivity)
                : matchmakingDao.findAllFullGroups(idActivity);
        Optional<GroupStudent> targetStudent = groupStudents.stream().filter(o -> o.getCip().equals(currUser))
                .findFirst();
        List<GroupStudent> selfGroup = new ArrayList<GroupStudent>();
        if (targetStudent.isPresent()) {
            selfGroup = groupStudents.stream().filter(o -> o.getIdGroup() == targetStudent.get().getIdGroup())
                    .collect(Collectors.toList());
        }
        return selfGroup;
    }

    @PutMapping(value = "/matchmaking/{idActivity}/{isFinal}/")
    public HashMap<Integer, List<GroupStudent>> setFinalTeams(@PathVariable int idActivity,
            @PathVariable boolean isFinal, @CookieValue("auth_user") Cookie userCookie,
            @CookieValue("auth_cred") Cookie credCookie) throws Exception {
        validator.validate(userCookie, credCookie, Status.Teacher, Status.Admin, Status.Support);
        HashMap<Integer, List<GroupStudent>> groupStudents = getTeams(idActivity, userCookie, credCookie);
        if (isFinal) {
            Activities activity = activitiesDao.findByIdActivity(idActivity);
            activity.setFinal(isFinal);
            activitiesDao.save(activity);
        }
        return groupStudents;
    }

    @GetMapping(value = "/matchmaking/members/{idActivity}/")
    public List<MemberClass> findFreeMembers(@PathVariable int idActivity, @CookieValue("auth_user") Cookie userCookie,
            @CookieValue("auth_cred") Cookie credCookie) throws Exception {
        String currUser = validator.validate(userCookie, credCookie, Status.Student, Status.Admin, Status.Support);
        return matchmakingDao.findAllFreeUser(idActivity).stream().filter(o -> !o.getCip().equals(currUser))
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/matchmaking/groups/{idActivity}/{getOpen}/")
    public List<GroupStudent> findFreeGroup(@PathVariable int idActivity, @PathVariable boolean getOpen,
            @CookieValue("auth_user") Cookie userCookie, @CookieValue("auth_cred") Cookie credCookie) throws Exception {
        String currUser = validator.validate(userCookie, credCookie, Status.Student, Status.Admin, Status.Support);
        List<GroupStudent> groupStudents = getOpen ? matchmakingDao.findAllIncompleteGroups(idActivity)
                : matchmakingDao.findAllFullGroups(idActivity);
        if (getOpen) {
            Optional<GroupStudent> targetStudent = groupStudents.stream().filter(o -> o.getCip().equals(currUser))
                    .findFirst();
            if (targetStudent.isPresent()) {
                groupStudents = groupStudents.stream().filter(o -> o.getIdGroup() != targetStudent.get().getIdGroup())
                        .collect(Collectors.toList());
            }
        }
        return groupStudents;
    }

    @DeleteMapping(value = "/matchmaking/{idActivity}/")
    public boolean leaveTeam(@PathVariable int idActivity, @CookieValue("auth_user") Cookie userCookie,
            @CookieValue("auth_cred") Cookie credCookie) throws Exception {
        validator.validate(userCookie, credCookie, Status.Student, Status.Admin, Status.Support);
        String currUser = ConfigurationController.getAuthUser();
        return matchmakingDao.leaveTeam(currUser, idActivity);
    }

    @ResponseBody
    @PostMapping(value = "/matchmaking/{idActivity}/")
    public boolean mergeTeam(HttpEntity<String> httpEntity, @PathVariable int idActivity,
            @CookieValue("auth_user") Cookie userCookie, @CookieValue("auth_cred") Cookie credCookie) throws Exception {
        String currUser = validator.validate(userCookie, credCookie, Status.Student, Status.Admin, Status.Support);
        JSONObject json = new JSONObject(httpEntity.getBody());
        String seekingCip = json.getString("cip");
        boolean result = matchmakingDao.mergeTeam(seekingCip, currUser, idActivity);
        if (result) {
            NotificationData eventData = new NotificationData("Matchmaking",
                    Message.format(Message.MATCH_SUCCESS, seekingCip, currUser));
            NotificationDispatcher.addEvent(seekingCip, eventData);
        }
        return result;
    }
}
