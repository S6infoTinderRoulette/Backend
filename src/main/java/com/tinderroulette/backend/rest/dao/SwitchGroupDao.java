package com.tinderroulette.backend.rest.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.tinderroulette.backend.rest.model.MemberClass;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SwitchGroupDao {

    @PersistenceContext
    private EntityManager em;

    public List<MemberClass> findAllSwitchGroupRequest(String cip, String idClass) {
        String sql = "SELECT * FROM tinderroulette.get_switch_group(?, ?)";
        Query query = em.createNativeQuery(sql)
                .setParameter(1, cip)
                .setParameter(2, idClass);
        return (List<MemberClass>) query.getResultList();
    }

    public int insertSwitchRequest(String cip, String idClass, int tutorat) {
        String sql = "SELECT * FROM tinderroulette.insert_switchgrouprequest(?, ?, ?)";
        Query query = em.createNativeQuery(sql)
                .setParameter(1, cip)
                .setParameter(2, idClass)
                .setParameter(3, tutorat);

        Object result;
        try {

            result = query.getSingleResult();

        } catch (Exception error) {
            return 0;
        }

        return (int) result;
    }


    public boolean acceptSwitchRequest(String cipRequested, String cipUser, String idClass) {
        String sql = "SELECT * FROM tinderroulette.accept_switchgroup( ?, ?, ?);";
        Query query = em.createNativeQuery(sql)
                .setParameter(1, cipRequested)
                .setParameter(2, cipUser)
                .setParameter(3, idClass);

        Object result;
        try {

            result = query.getSingleResult();

        } catch (Exception error) {
            return false;
        }

        return (boolean) result;
    }
}
