package com.tinderroulette.backend.rest.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tinderroulette.backend.rest.dao.FriendRequestDao;
import com.tinderroulette.backend.rest.exceptions.EmptyJsonResponse;
import com.tinderroulette.backend.rest.exceptions.RequestIntrouvableException;
import com.tinderroulette.backend.rest.model.FriendRequest;

@RestController
public class FriendRequestController {

	private FriendRequestDao friendRequestDao;

	public FriendRequestController(FriendRequestDao friendRequestDao) {
		this.friendRequestDao = friendRequestDao;
	}

	@GetMapping(value = "/friendRequest/")
	public List<FriendRequest> findAll() {
		return friendRequestDao.findAll();
	}

	@PostMapping(value = "/friendRequest/")
	public ResponseEntity<Void> addRequest(@Valid @RequestBody FriendRequest FriendRequest) {
		FriendRequest requestTest = friendRequestDao.findByCipSeekingAndCipRequested(FriendRequest.getCipSeeking(),
				FriendRequest.getCipRequested());
		if (requestTest != null) {
			throw new RequestIntrouvableException(
					"La friendRequest correspondante est déjà présente dans la base de données");
		} else {
			FriendRequest requestput = friendRequestDao.save(FriendRequest);
			if (requestput == null) {
				return ResponseEntity.noContent().build();
			} else {
				return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
			}
		}

	}

	@PutMapping(value = "/friendRequest/")
	public ResponseEntity<Void> updateRequest(@Valid @RequestBody FriendRequest FriendRequest) {
		FriendRequest requestTest = friendRequestDao.findByCipSeekingAndCipRequested(FriendRequest.getCipSeeking(),
				FriendRequest.getCipRequested());
		if (requestTest == null) {
			throw new RequestIntrouvableException(
					"La friendRequest correspondante n'est pas présente dans la base de données");
		} else {
			FriendRequest requestput = friendRequestDao.save(FriendRequest);
			if (requestput == null) {
				return ResponseEntity.noContent().build();
			} else {
				return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
			}
		}

	}

	@DeleteMapping(value = "/friendRequest/{cipSeeking}/{cipRequesting}/")
	public ResponseEntity<Void> deleteRequest(@PathVariable String cipSeeking, @PathVariable String cipRequested) {
		FriendRequest requestTest = friendRequestDao.findByCipSeekingAndCipRequested(cipSeeking, cipRequested);
		if (requestTest == null) {
			throw new RequestIntrouvableException(
					"La friendRequest correspondante n'est pas présente dans la base de données");
		} else {
			friendRequestDao.deleteByCipSeekingAndCipRequested(cipSeeking, cipRequested);
			return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
		}
	}
}
