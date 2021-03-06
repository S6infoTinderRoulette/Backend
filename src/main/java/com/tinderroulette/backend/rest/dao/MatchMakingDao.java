package com.tinderroulette.backend.rest.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.tinderroulette.backend.rest.model.GroupStudent;
import com.tinderroulette.backend.rest.model.MemberClass;

@Repository
public class MatchMakingDao {

    @PersistenceContext
    private EntityManager em;

    public List<MemberClass> findAllFreeUser(int idActivity) {
        String sql = "SELECT * FROM tinderroulette.get_single_member(?)";
        Query query = em.createNativeQuery(sql, MemberClass.class)
                .setParameter(1, idActivity);
        return (List<MemberClass>) query.getResultList();
    }

    public List<GroupStudent> findAllIncompleteGroups(int idActivity) {
        String sql = "SELECT * FROM tinderroulette.get_open_group(?)";
        Query query = em.createNativeQuery(sql, GroupStudent.class)
                .setParameter(1, idActivity);
        return (List<GroupStudent>) query.getResultList();
    }

    public List<GroupStudent> findAllFullGroups(int idActivity) {
        String sql = "SELECT * FROM tinderroulette.get_full_group(?)";
        Query query = em.createNativeQuery(sql, GroupStudent.class)
                .setParameter(1, idActivity);
        return (List<GroupStudent>) query.getResultList();
    }

    public boolean leaveTeam(String cip, int idActivity) {
        String sql = "SELECT * FROM tinderroulette.leave_group(?,?)";
        Query query = em.createNativeQuery(sql)
                .setParameter(1, cip)
                .setParameter(2, idActivity);
        return (boolean) query.getSingleResult();
    }

    public boolean mergeTeam(String seekingCip, String requestedCip, int idActivity) {
        String sql = "SELECT * FROM tinderroulette.merge_teams(?,?,?)";
        Query query = em.createNativeQuery(sql)
                .setParameter(1, seekingCip)
                .setParameter(2, requestedCip)
                .setParameter(3, idActivity);
        return (boolean) query.getSingleResult();
    }
}
