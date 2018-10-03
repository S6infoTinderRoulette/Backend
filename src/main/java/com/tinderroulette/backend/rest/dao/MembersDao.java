package com.tinderroulette.backend.rest.dao;

import com.tinderroulette.backend.rest.model.Members;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MembersDao extends JpaRepository<Members, String> {

    Members save (Members members);
    Members findByCip (String cip);
    List <Members> findByLast_name (String LastName);
    List <Members> findById_member_status (String idMember);
    List <Members> findAll (String idMember);
    void deleteByCip (String cip);


}
