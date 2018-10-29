package com.tinderroulette.backend.rest.dao;

import com.tinderroulette.backend.rest.model.Parametercache;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParametercacheDao extends JpaRepository <Parametercache,String> {

    Parametercache save (Parametercache parameterCache);
    List<Parametercache> findAll();
    Parametercache findByCipAndIdClassAndIdGroupType (String cip, String idClass, int idGroupType);
    void deleteByCipAndIdClassAndIdGroupType (String cip, String idClass, int idGroupType);
}
