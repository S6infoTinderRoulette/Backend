package com.tinderroulette.backend.rest.controller;

import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.tinderroulette.backend.rest.dao.MatchMakingDao;

@RestController
public class MatchMakingController {
	private MatchMakingDao matchmakingDao;

	public MatchMakingController(MatchMakingDao matchmakingDao) {
		this.matchmakingDao = matchmakingDao;
	}

	@GetMapping(value = "/matchmaking/{idActivity}/", produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> findFreeSpot(@PathVariable int idActivity) {
		JSONArray singleMembers = new JSONArray(matchmakingDao.findAllFreeUser(idActivity));
		JSONArray openGroups = new JSONArray(matchmakingDao.findAllIncompleteGroups(idActivity));
		JSONObject freeSpot = new JSONObject();
		freeSpot.put("singleMembers", singleMembers);
		freeSpot.put("openGroup", openGroups);
		return freeSpot.toMap();
	}
}
