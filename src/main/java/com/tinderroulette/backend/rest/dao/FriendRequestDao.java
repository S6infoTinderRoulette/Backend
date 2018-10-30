package com.tinderroulette.backend.rest.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tinderroulette.backend.rest.model.FriendRequest;

@Repository
public interface FriendRequestDao extends JpaRepository<FriendRequest, Long> {

	FriendRequest save(FriendRequest request);

	void deleteByCipSeekingAndCipRequested(String CipSeeking, String CipRequested);

	List<FriendRequest> findAll();

	FriendRequest findByCipSeekingAndCipRequested(String CipSeeking, String CipRequested);
}
