package com.tinderroulette.backend.rest.dao;

import com.tinderroulette.backend.rest.model.Friendlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendlistDao extends JpaRepository <Friendlist,String> {

    Friendlist save (Friendlist friendlist);
    List<Friendlist> findAll();
    Friendlist findByCipAndFriendCip (String cip, String FriendCip );
    void deleteByCipAndAndFriendCip (String cip, String FriendCip );
}
