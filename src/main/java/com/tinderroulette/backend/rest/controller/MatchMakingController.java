package com.tinderroulette.backend.rest.controller;

import java.util.List;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tinderroulette.backend.rest.CAS.ConfigurationController;
import com.tinderroulette.backend.rest.dao.MatchMakingDao;
import com.tinderroulette.backend.rest.model.GroupStudent;
import com.tinderroulette.backend.rest.model.MemberClass;

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

	@GetMapping(value = "/matchmaking/groups/{idActivity}/")
	public List<GroupStudent> findFreeGroup(@PathVariable int idActivity) {
		return matchmakingDao.findAllIncompleteGroups(idActivity);
	}

	@ResponseBody
	@PostMapping(value = "/matchmaking/{idActivity}/")
	public boolean mergeTeam(HttpEntity<String> httpEntity, @PathVariable int idActivity) {
		JSONObject json = new JSONObject(httpEntity.getBody());
		String currUser = ConfigurationController.getAuthUser();
		return matchmakingDao.mergeTeam(json.getString("cip"), currUser, idActivity);
	}
}
