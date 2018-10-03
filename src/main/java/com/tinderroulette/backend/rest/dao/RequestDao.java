package com.tinderroulette.backend.rest.dao;

import com.tinderroulette.backend.rest.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestDao extends JpaRepository<Request,Long> {

    Request save (Request request);
    void deleteByCipRequestedAndCipSeekingAndIdActivityAndIdRequestType (String CipRequested, String CipSeeking, int IdActivity, int IdRequestType);
    List<Request> findAll ();
    Request findByCipRequestedAndCipSeekingAndIdActivityAndIdRequestType (String CipRequested, String CipSeeking, int IdActivity, int IdRequestType);
}
