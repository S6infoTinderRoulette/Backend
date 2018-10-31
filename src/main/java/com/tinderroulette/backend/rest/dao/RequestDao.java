package com.tinderroulette.backend.rest.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tinderroulette.backend.rest.model.Request;

@Repository
public interface RequestDao extends JpaRepository<Request,Long> {

    Request save (Request request);

	void deleteByCipRequestedAndCipSeekingAndIdActivity(String CipRequested, String CipSeeking,
			int IdActivity);
    List<Request> findAll ();

	Request findByCipRequestedAndCipSeekingAndIdActivity(String CipRequested, String CipSeeking,
			int IdActivity);
}
