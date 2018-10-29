package com.tinderroulette.backend.rest;


import com.tinderroulette.backend.rest.dao.AppDao;
import com.tinderroulette.backend.rest.dao.MemberClassDao;
import com.tinderroulette.backend.rest.dao.MembersDao;

import static com.tinderroulette.backend.rest.DBConnection.DBPopulation.memberClassPopulation;
import static com.tinderroulette.backend.rest.DBConnection.DBPopulation.membersPopulation;
import static com.tinderroulette.backend.rest.DBConnection.DBPopulation.appsPopulation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;


import java.io.IOException;


@Controller
@SpringBootApplication
public class Application
{
	private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main( String[] args ) throws IOException {
    	SpringApplication.run(Application.class, args);

    }
    @Bean
    public CommandLineRunner runner(MembersDao repositoryM, AppDao repositoryA, MemberClassDao repositoryMC) throws IOException{
        return (args) -> {
            //appsPopulation("http://zeus.gel.usherbrooke.ca:8080/ms/rest/app_ap?inscription=2018-01-01",repositoryA);
            //membersPopulation("http://zeus.gel.usherbrooke.ca:8080/ms/rest/etudiant_groupe?inscription=2018-01-01&trimestre_id=A18",repositoryM);
            memberClassPopulation("http://zeus.gel.usherbrooke.ca:8080/ms/rest/etudiant_groupe?inscription=2018-01-01&trimestre_id=A18",repositoryMC);
        };
    }
}
