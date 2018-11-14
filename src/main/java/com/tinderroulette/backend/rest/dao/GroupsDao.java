package com.tinderroulette.backend.rest.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tinderroulette.backend.rest.model.Groups;

@Repository
public interface GroupsDao extends JpaRepository<Groups, Integer> {

    Groups save(Groups groups);

    List<Groups> findAll();

    Groups findByIdGroup(int idGroup);

    void deleteByIdGroup(int idGroup);

    List<Groups> findByIdClassAndIdGroupType(String idClass, int idGroupType);

    List<Groups> findByIdActivity(Integer idActivity);
    List<Groups> findByGroupIndexAndIdClassAndIdGroupType(int groupIndex, String idClass, int idGroupType);
}
