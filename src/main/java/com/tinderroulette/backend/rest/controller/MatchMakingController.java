package com.tinderroulette.backend.rest.controller;

import java.util.List;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tinderroulette.backend.rest.Message;
import com.tinderroulette.backend.rest.CAS.ConfigurationController;
import com.tinderroulette.backend.rest.dao.MatchMakingDao;
import com.tinderroulette.backend.rest.model.GroupStudent;
import com.tinderroulette.backend.rest.model.MemberClass;
import com.tinderroulette.backend.rest.notification.NotificationData;
import com.tinderroulette.backend.rest.notification.NotificationDispatcher;

@RestController
public class MatchMakingController {
    private MatchMakingDao matchmakingDao;

    public MatchMakingController(MatchMakingDao matchmakingDao) {
        this.matchmakingDao = matchmakingDao;
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
