package com.tinderroulette.backend.rest.dao;

import com.tinderroulette.backend.rest.model.RequestType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestTypeDao extends JpaRepository<RequestType,Long> {

    RequestType save (RequestType requestType);
    List<RequestType> findAll();
    RequestType findByIdRequestType (int idRequestType);
    RequestType findByRequestType (String Type);
    void deleteByIdRequestType (int idRequestType);

}
