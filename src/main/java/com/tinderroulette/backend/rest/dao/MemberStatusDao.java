package com.tinderroulette.backend.rest.dao;

import com.tinderroulette.backend.rest.model.MemberStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberStatusDao extends JpaRepository<MemberStatus, Long> {

    void deleteByIdMemberStatus(int idMemberStatus);
    MemberStatus save (MemberStatus memberStatus);
    List<MemberStatus> findAll ();
    MemberStatus findByIdMemberStatus (int idMemberStatus);
    MemberStatus findByStatus (String status);

}
