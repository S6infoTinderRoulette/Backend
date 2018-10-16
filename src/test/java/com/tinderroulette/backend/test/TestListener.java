package com.tinderroulette.backend.test;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

import com.tinderroulette.backend.rest.dao.ActivitiesDao;
import com.tinderroulette.backend.rest.dao.ApDao;
import com.tinderroulette.backend.rest.dao.AppDao;
import com.tinderroulette.backend.rest.dao.ClassesDao;
import com.tinderroulette.backend.rest.dao.FriendlistDao;
import com.tinderroulette.backend.rest.dao.GroupStudentDao;
import com.tinderroulette.backend.rest.dao.GroupTypeDao;
import com.tinderroulette.backend.rest.dao.GroupsDao;
import com.tinderroulette.backend.rest.dao.MemberClassDao;
import com.tinderroulette.backend.rest.dao.MemberStatusDao;
import com.tinderroulette.backend.rest.dao.MembersDao;
import com.tinderroulette.backend.rest.dao.RequestDao;
import com.tinderroulette.backend.rest.dao.RequestTypeDao;
import com.tinderroulette.backend.rest.model.Activities;
import com.tinderroulette.backend.rest.model.App;
import com.tinderroulette.backend.rest.model.GroupType;
import com.tinderroulette.backend.rest.model.MemberClass;
import com.tinderroulette.backend.rest.model.MemberStatus;
import com.tinderroulette.backend.rest.model.Members;
import com.tinderroulette.backend.rest.model.RequestType;

public class TestListener extends AbstractTestExecutionListener {

    @Autowired
    private ActivitiesDao activitiesDao;

    @Autowired
    private ApDao apDao;

	@Autowired
    private AppDao appDao;

    @Autowired
    private ClassesDao classesDao;

    @Autowired
    private FriendlistDao friendlistDao;

    @Autowired
    private GroupsDao groupsDao;

    @Autowired
    private GroupStudentDao groupStudentDao;

    @Autowired
    private GroupTypeDao groupTypeDao;

    @Autowired
    private MemberClassDao memberClassDao;

	@Autowired
	private MembersDao membersDao;

	@Autowired
	private MemberStatusDao memberStatusDao;

    @Autowired
    private RequestDao requestDao;

    @Autowired
    private RequestTypeDao requestTypeDao;


	@Override
	public void beforeTestClass(TestContext testContext) throws Exception {
		super.beforeTestClass(testContext);
        testContext.getApplicationContext().getAutowireCapableBeanFactory().autowireBean(this);

		List<App> appList = new ArrayList<>();
		for (int j = 0; j < 4; j++) {
			appList.add(new App("test" + j, ""));
		}
		appDao.saveAll(appList);

		List<MemberStatus> statusList = new ArrayList<>();
		statusList.add(new MemberStatus(1, "Étudiant"));
		statusList.add(new MemberStatus(2, "Enseignant"));
		memberStatusDao.saveAll(statusList);

		List<Members> memberList = new ArrayList<>();
		for (int i = 0; i <= 10; i++) {
			memberList.add(new Members("member" + i, 1, "nom" + i, "nom" + i, ""));
		}
		memberList.add(new Members("teacher", 2, "Gestion", "McGestionFace", "gestion@email.com"));
		membersDao.saveAll(memberList);

		List<Activities> activitiesList = new ArrayList<>();
		for (int k = 0; k < appList.size(); k++) {
			activitiesList.add(new Activities(k, appList.get(k).getIdApp(), "teacher", k + 2));
		}
		activitiesDao.saveAll(activitiesList);

		List<MemberClass> memberClasses = new ArrayList<>();
		for (Members member : memberList) {
			for (Activities activity : activitiesList) {
                if (member.getIdMemberStatus() == 1)
                    memberClasses.add(new MemberClass(member.getCip(), activity.getIdClass()));
			}
		}
		memberClassDao.saveAll(memberClasses);

        requestTypeDao.save(new RequestType(1, "Team"));
        requestTypeDao.save(new RequestType(2, "Friend"));

        groupTypeDao.save(new GroupType(1, "Groupe Activité", 0, 50));
        groupTypeDao.save(new GroupType(2, "Procédural", 0, 50));
        groupTypeDao.save(new GroupType(3, "Tutorat", 0, 50));

        System.out.println("Data Initialization Done");
	}

	@Override
	public void afterTestClass(TestContext testContext) throws Exception {
		super.afterTestClass(testContext);
        testContext.getApplicationContext().getAutowireCapableBeanFactory().autowireBean(this);

        requestDao.deleteAll();
        requestTypeDao.deleteAll();

        groupStudentDao.deleteAll();
        memberClassDao.deleteAll();
        friendlistDao.deleteAll();

        groupsDao.deleteAll();
        groupTypeDao.deleteAll();

        activitiesDao.deleteAll();
        classesDao.deleteAll();
        apDao.deleteAll();
        appDao.deleteAll();

        membersDao.deleteAll();
        memberStatusDao.deleteAll();


        System.out.println("Data Cleanup Done");
	}
}
