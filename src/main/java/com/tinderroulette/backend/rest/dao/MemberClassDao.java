package com.tinderroulette.backend.rest.dao;

import com.tinderroulette.backend.rest.model.MemberClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberClassDao extends JpaRepository<MemberClass,String> {

    MemberClass save (MemberClass memberClass);
    void deleteByCipAndIdClass (String cip, String idClass);
    MemberClass findByCipAndIdClass (String cip, String idClass);
    List<MemberClass> findAll ();
    List<MemberClass> findByIdClass (String idClass);
    List<MemberClass> findByCip (String cip);

}
