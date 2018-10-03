package com.tinderroulette.backend.rest.dao;

import com.tinderroulette.backend.rest.model.GroupType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupTypeDao extends JpaRepository<GroupType,Long> {

    GroupType save (GroupType groupType);
    GroupType findByIdGroupType (int idGroupType);
    GroupType findByType (String type);
    List<GroupType> findAll ();
    void deleteByIdGroupType (int idGroupType);

}
