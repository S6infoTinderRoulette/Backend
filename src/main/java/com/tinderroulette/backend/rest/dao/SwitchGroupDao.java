package com.tinderroulette.backend.rest.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

@Repository
public class SwitchGroupDao {

    @PersistenceContext
    private EntityManager em;

    public int insertSwitchRequest(String cip, String idClass, int tutorat) {
        String sql = "SELECT * FROM tinderroulette.insert_switchgrouprequest(?, ?, ?)";
        Query query = em.createNativeQuery(sql)
                .setParameter(1, cip)
                .setParameter(2, idClass)
                .setParameter(3, tutorat);
        Object result = query.getSingleResult();
        if(result == null) {
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
        return (boolean) query.getSingleResult();
    }
}
