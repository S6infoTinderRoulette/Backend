package com.tinderroulette.backend.rest.DBConnection;


import com.tinderroulette.backend.rest.dao.AppDao;
import com.tinderroulette.backend.rest.dao.MemberClassDao;
import com.tinderroulette.backend.rest.dao.MembersDao;
import com.tinderroulette.backend.rest.model.App;
import com.tinderroulette.backend.rest.model.MemberClass;
import com.tinderroulette.backend.rest.model.Members;
import org.springframework.web.bind.annotation.RestController;


import java.io.IOException;
import java.util.Iterator;
import java.util.List;


@RestController
public class DBPopulation {
    private AppDao appDao;
    private MembersDao membersDao;
    private MemberClassDao memberClassDao;
    private JsonReader jsonReader;


    public DBPopulation(AppDao appDao, MembersDao membersDao, MemberClassDao memberClassDao, JsonReader jsonReader) {
        this.appDao = appDao;
        this.membersDao = membersDao;
        this.memberClassDao = memberClassDao;
        this.jsonReader = jsonReader;
    }

    public void appsPopulation(String url)throws IOException {
        List<App> apps = jsonReader.appReader(url);
        for (Iterator<App> itr = apps.iterator(); itr.hasNext(); ) {
            App a = itr.next();
            appDao.save(a);
        }
    }

    public void membersPopulation(String url)throws IOException {
        List<Members> members = jsonReader.memberReader(url);
        for (Iterator<Members> itr = members.iterator(); itr.hasNext(); ) {
            Members m = itr.next();
            membersDao.save(m);
        }
    }

    public void memberClassPopulation(String url)throws IOException {
        List<MemberClass> memberClasses = jsonReader.memberClassReader(url);
        for (Iterator<MemberClass> itr = memberClasses.iterator(); itr.hasNext(); ) {
            MemberClass mc= itr.next();
            memberClassDao.save(mc);
        }
    }
}
