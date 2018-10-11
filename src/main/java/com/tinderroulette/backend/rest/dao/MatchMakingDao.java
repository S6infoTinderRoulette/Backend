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
		Query query = em.createNativeQuery(sql, MemberClass.class).setParameter(1, idActivity);
		return (List<MemberClass>) query.getResultList();
	}

	public List<GroupStudent> findAllIncompleteGroups(int idActivity) {
		String sql = "SELECT * FROM tinderroulette.get_open_group(?)";
		Query query = em.createNativeQuery(sql, GroupStudent.class).setParameter(1, idActivity);
		return (List<GroupStudent>) query.getResultList();
	}

	public boolean mergeTeam(String cip1, String cip2, int idActivity) {
		String sql = "SELECT * FROM tinderroulette.merge_teams(?,?,?)";
		Query query = em.createNativeQuery(sql).setParameter(1, cip1).setParameter(2, cip2).setParameter(3, idActivity);
		return (boolean) query.getSingleResult();
	}
}
