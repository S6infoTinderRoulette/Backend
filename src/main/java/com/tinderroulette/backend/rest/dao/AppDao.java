package com.tinderroulette.backend.rest.dao;

import com.tinderroulette.backend.rest.model.App;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppDao extends JpaRepository <App,String> {

    App save (App app);
    List<App> findAll();
    App findByIdApp (String IdApp);
    void deleteByIdApp (String IdApp);
}
