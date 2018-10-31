package com.tinderroulette.backend.rest.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tinderroulette.backend.rest.model.SwitchGroupRequest;

@Repository
public interface SwitchGroupRequestDao extends JpaRepository<SwitchGroupRequest, Long> {

	SwitchGroupRequest save(SwitchGroupRequest request);

	void deleteByCipAndIdClassAndIdGroup(String Cip, String IdClass, int IdGroup);

	List<SwitchGroupRequest> findAll();

	SwitchGroupRequest findByCipAndIdClassAndIdGroup(String Cip, String IdClass, int IdGroup);
}
