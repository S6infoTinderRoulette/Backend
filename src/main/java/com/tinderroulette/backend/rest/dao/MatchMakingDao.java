package com.tinderroulette.backend.rest.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.tinderroulette.backend.rest.model.GroupStudent;
import com.tinderroulette.backend.rest.model.MemberClass;

@Repository
public class MatchMakingDao {

	@PersistenceUnit
	private EntityManagerFactory emf;

	public List<MemberClass> findAllFreeUser(int idActivity) {
		EntityManager em = emf.createEntityManager();
		String sql = "SELECT * FROM tinderroulette.get_single_member(?)";
		Query query = em.createNativeQuery(sql, MemberClass.class);
		query.setParameter(1, idActivity);
		return (List<MemberClass>) query.getResultList();
	}

	public List<GroupStudent> findAllIncompleteGroups(int idActivity) {
		EntityManager em = emf.createEntityManager();
		String sql = "SELECT * FROM tinderroulette.get_open_group(?)";
		Query query = em.createNativeQuery(sql, GroupStudent.class);
		query.setParameter(1, idActivity);
		return (List<GroupStudent>) query.getResultList();
	}
}
