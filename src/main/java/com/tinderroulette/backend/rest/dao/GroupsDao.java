package com.tinderroulette.backend.rest.dao;

import com.tinderroulette.backend.rest.model.Groups;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupsDao extends JpaRepository <Groups,String> {

    Groups save (Groups groups);
    List<Groups> findAll();
    Groups findByIdGroupType ( int idGroupType );
    void deleteByIdGroupType ( int idGroupType );
}
