package com.tinderroulette.backend.rest.controller;

import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tinderroulette.backend.rest.Message;
import com.tinderroulette.backend.rest.CAS.ConfigurationController;
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
    private GroupsDao groupsDao;
    private GroupStudentDao groupStudentDao;
    private ActivitiesDao activitiesDao;

    public MatchMakingController(MatchMakingDao matchmakingDao, GroupsDao groupsDao, GroupStudentDao groupStudentDao,
            ActivitiesDao activitiesDao) {
        this.matchmakingDao = matchmakingDao;
        this.groupsDao = groupsDao;
        this.groupStudentDao = groupStudentDao;
        this.activitiesDao = activitiesDao;
    }

    @PutMapping(value = "/matchmaking/{idActivity}/{isFinal}/")
    public HashMap<Integer, List<GroupStudent>> getTeams(@PathVariable int idActivity, @PathVariable boolean isFinal) {
        List<Groups> activityGroups = groupsDao.findByIdActivity(idActivity);
        HashMap<Integer, List<GroupStudent>> groupStudents = new HashMap<>();
        for (Groups group : activityGroups) {
            groupStudents.put(group.getIdGroup(), groupStudentDao.findByIdGroup(group.getIdGroup()));
        }
        if (isFinal) {
            Activities activity = activitiesDao.findByIdActivity(idActivity);
            activity.setFinal(isFinal);
            activitiesDao.save(activity);
        }
        return groupStudents;
    }

    @GetMapping(value = "/matchmaking/members/{idActivity}/")
    public List<MemberClass> findFreeMembers(@PathVariable int idActivity) {
        return matchmakingDao.findAllFreeUser(idActivity);
    }

    @GetMapping(value = "/matchmaking/groups/{idActivity}/{getOpen}/")
    public List<GroupStudent> findFreeGroup(@PathVariable int idActivity, @PathVariable boolean getOpen) {
        return getOpen ? matchmakingDao.findAllIncompleteGroups(idActivity)
                : matchmakingDao.findAllFullGroups(idActivity);
    }

    @DeleteMapping(value = "/matchmaking/{idActivity}")
    public boolean leaveTeam(@PathVariable int idActivity) {
        String currUser = ConfigurationController.getAuthUser();
        return matchmakingDao.leaveTeam(currUser, idActivity);
    }

    @ResponseBody
    @PostMapping(value = "/matchmaking/{idActivity}/")
    public boolean mergeTeam(HttpEntity<String> httpEntity, @PathVariable int idActivity) {
        JSONObject json = new JSONObject(httpEntity.getBody());
        String seekingCip = json.getString("cip");
        String currUser = ConfigurationController.getAuthUser();
        boolean result = matchmakingDao.mergeTeam(seekingCip, currUser, idActivity);
        if (result) {
            NotificationData eventData = new NotificationData("Matchmaking",
                    Message.format(Message.MATCH_SUCCESS, seekingCip, currUser));
            NotificationDispatcher.addEvent(seekingCip, eventData);
        }
        return result;
    }
}
