package com.tinderroulette.backend.rest.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tinderroulette.backend.rest.model.GroupStudent;

@Repository
public interface GroupStudentDao extends JpaRepository<GroupStudent, String> {

    GroupStudent save(GroupStudent groupStudent);

    List<GroupStudent> findAll();
    GroupStudent findByCipAndIdGroup (String cip, int idGroup );
    List<GroupStudent> findByIdGroup(int idGroup);
    void deleteByCipAndIdGroup (String cip, int idGroup );
}
