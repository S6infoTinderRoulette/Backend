package com.tinderroulette.backend.rest.DBConnection;

import com.tinderroulette.backend.rest.dao.AppDao;
import com.tinderroulette.backend.rest.dao.MemberClassDao;
import com.tinderroulette.backend.rest.dao.MembersDao;
import com.tinderroulette.backend.rest.model.App;
import com.tinderroulette.backend.rest.model.MemberClass;
import com.tinderroulette.backend.rest.model.Members;

import static com.tinderroulette.backend.rest.DBConnection.JsonReader.memberClassReader;
import static com.tinderroulette.backend.rest.DBConnection.JsonReader.memberReader;
import static com.tinderroulette.backend.rest.DBConnection.JsonReader.appReader;

import java.io.IOException;
import java.util.List;


public class DBPopulation {
    public static void membersPopulation(String url, MembersDao repository)throws IOException {
        List<Members> members = memberReader(url);
        repository.saveAll(members);
    }

    public static void appsPopulation(String url, AppDao repository)throws IOException {
        List<App> apps = appReader(url);
        repository.saveAll(apps);
    }

    public static void memberClassPopulation(String url, MemberClassDao repository)throws IOException {
        List<MemberClass> memberClasses = memberClassReader(url);
        repository.saveAll(memberClasses);
    }
}
