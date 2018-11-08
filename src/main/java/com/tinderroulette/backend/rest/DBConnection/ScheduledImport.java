package com.tinderroulette.backend.rest.DBConnection;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;



@Component
public class ScheduledImport {
    DBPopulation dbPopulation;

    public ScheduledImport(DBPopulation dbPopulation) {
        this.dbPopulation = dbPopulation;
    }

    @Scheduled(cron = "0 0 1 * * *") //every day at 1AM
    public void ScheduledImport() throws IOException {
        System.out.println("Starting to import APP...");
        dbPopulation.appsPopulation("http://zeus.gel.usherbrooke.ca:8080/ms/rest/app_ap?inscription=2010-01-01");
        System.out.println("Starting to import members...");
        dbPopulation.membersPopulation("http://zeus.gel.usherbrooke.ca:8080/ms/rest/etudiant_groupe?inscription=2018-01-01&trimestre_id=A18");
        System.out.println("Starting to import memberclasses...");
        dbPopulation.memberClassPopulation("http://zeus.gel.usherbrooke.ca:8080/ms/rest/etudiant_groupe?inscription=2018-01-01&trimestre_id=A18");
        System.out.println("Finished importing all the data.");
    }
}
