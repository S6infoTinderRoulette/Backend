package com.tinderroulette.backend.test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AbstractTestExecutionListener;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.tinderroulette.backend.rest.Application;
import com.tinderroulette.backend.rest.dao.MatchMakingDao;
import com.tinderroulette.backend.rest.dao.RequestDao;
import com.tinderroulette.backend.rest.model.GroupStudent;
import com.tinderroulette.backend.rest.model.MemberClass;
import com.tinderroulette.backend.rest.model.Request;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@TestPropertySource(locations = "classpath:test.properties")
@TestExecutionListeners({ TestListener.class, DependencyInjectionTestExecutionListener.class })
public class MatchmakingTest extends AbstractTestExecutionListener {

    @Autowired
    private MatchMakingDao matchmakingDao;

    @Autowired
    private RequestDao requestDao;

    @Test
    public void merge2Member() {
        List<MemberClass> memberList = new ArrayList<>();
        for (int i = 0; i <= 10; i++) {
            memberList.add(new MemberClass(String.format("%1$-8s", "member" + i), "test0"));
        }
        assertTrue(matchmakingDao.findAllFreeUser(0).equals(memberList));
        assertFalse(matchmakingDao.mergeTeam("member0", "member1", 0));
		requestDao.save(new Request(0, "member0", "member1", new Date()));
        assertTrue(matchmakingDao.mergeTeam("member0", "member1", 0));
        memberList.remove(0);
        memberList.remove(0);
        assertTrue(matchmakingDao.findAllFreeUser(0).equals(memberList));
        assertTrue(matchmakingDao.findAllIncompleteGroups(0).size() == 2);
    }

    @Test
    public void merge3Member() {
        List<MemberClass> memberList = new ArrayList<>();
        for (int i = 2; i <= 10; i++) {
            memberList.add(new MemberClass(String.format("%1$-8s", "member" + i), "test0"));
        }
		requestDao.save(new Request(1, "member0", "member1", new Date()));
		requestDao.save(new Request(1, "member0", "member2", new Date()));
		requestDao.save(new Request(1, "member0", "member3", new Date()));
		requestDao.save(new Request(1, "member4", "member5", new Date()));
		requestDao.save(new Request(1, "member4", "member6", new Date()));
		requestDao.save(new Request(1, "member4", "member7", new Date()));

        assertTrue(matchmakingDao.mergeTeam("member0", "member1", 1));
        assertTrue(matchmakingDao.mergeTeam("member4", "member5", 1));
        assertTrue(matchmakingDao.findAllFullGroups(1).size() == 0);
        assertTrue(matchmakingDao.findAllIncompleteGroups(1).size() == 4);
        assertTrue(matchmakingDao.findAllFreeUser(1).size() == 7);

        assertTrue(matchmakingDao.mergeTeam("member0", "member2", 1));
        assertTrue(matchmakingDao.mergeTeam("member0", "member3", 1));
        assertTrue(matchmakingDao.mergeTeam("member4", "member6", 1));
        assertTrue(matchmakingDao.mergeTeam("member4", "member7", 1));
        assertTrue(matchmakingDao.findAllFullGroups(1).size() == 8);
        assertTrue(matchmakingDao.findAllIncompleteGroups(1).size() == 0);
        assertTrue(matchmakingDao.findAllFreeUser(1).size() == 3);
    }

    @Test
    public void merge2Group() {
		requestDao.save(new Request(2, "member0", "member1", new Date()));
		requestDao.save(new Request(2, "member0", "member2", new Date()));
		requestDao.save(new Request(2, "member3", "member4", new Date()));
		requestDao.save(new Request(2, "member3", "member0", new Date()));

        assertTrue(matchmakingDao.mergeTeam("member0", "member1", 2));
        assertTrue(matchmakingDao.mergeTeam("member0", "member2", 2));
        assertTrue(matchmakingDao.mergeTeam("member3", "member4", 2));
        assertTrue(matchmakingDao.mergeTeam("member3", "member0", 2));

        List<GroupStudent> groups = matchmakingDao.findAllFullGroups(2);
        assertTrue(groups.size() == 5);
        assertTrue(matchmakingDao.findAllFreeUser(2).size() == 6);
        int groupId = groups.get(0).getIdGroup();
        for (GroupStudent student : groups) {
            assertEquals(student.getIdGroup(), groupId);
        }
    }

    @Test
    public void mergeExceeding() {
		requestDao.save(new Request(3, "member0", "member1", new Date()));
		requestDao.save(new Request(3, "member0", "member2", new Date()));
		requestDao.save(new Request(3, "member0", "member3", new Date()));
		requestDao.save(new Request(3, "member0", "member4", new Date()));
		requestDao.save(new Request(3, "member0", "member5", new Date()));
		requestDao.save(new Request(3, "member0", "member6", new Date()));

        assertTrue(matchmakingDao.mergeTeam("member0", "member1", 3));
        assertTrue(matchmakingDao.mergeTeam("member0", "member2", 3));
        assertTrue(matchmakingDao.mergeTeam("member0", "member3", 3));
        assertTrue(matchmakingDao.mergeTeam("member0", "member4", 3));
        assertTrue(matchmakingDao.mergeTeam("member0", "member5", 3));
        assertFalse(matchmakingDao.mergeTeam("member0", "member6", 3));
    }
}
