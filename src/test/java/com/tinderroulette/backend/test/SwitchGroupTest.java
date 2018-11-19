package com.tinderroulette.backend.test;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.tinderroulette.backend.rest.Application;
import com.tinderroulette.backend.rest.dao.*;
import com.tinderroulette.backend.rest.model.GroupStudent;
import com.tinderroulette.backend.rest.model.Groups;
import com.tinderroulette.backend.rest.model.MemberClass;
import com.tinderroulette.backend.rest.model.Request;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AbstractTestExecutionListener;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@TestPropertySource(locations = "classpath:test.properties")
@TestExecutionListeners({ TestListener.class, DependencyInjectionTestExecutionListener.class })
public class SwitchGroupTest extends AbstractTestExecutionListener {

    @Autowired
    private SwitchGroupDao switchgroupDao;

    @Autowired
    private GroupsDao groupsDao;

    @Autowired
    private GroupStudentDao groupStudentDao;

    @Autowired
    private RequestDao requestDao;

    @Test
    public void InsertSwitchGroupRequestThenAccept() {

        // Add groups
        for (int i = 1; i <= 3; i++) {
            groupsDao.save(new Groups(i, 3, null, "test0", i));
        }

        List<Groups> groups = groupsDao.findAll();
        int g1 = groups.get(0).getIdGroup();
        int g2 = groups.get(1).getIdGroup();
        int g3 = groups.get(2).getIdGroup();

        groupStudentDao.save(new GroupStudent("member0", g1));
        groupStudentDao.save(new GroupStudent("member1", g2));
        groupStudentDao.save(new GroupStudent("member2", g3));

        // Test wrong insert request because request sent to the same group_type
        //assertTrue(switchgroupDao.insertSwitchRequest("member0", "test0", 1) == 0);

        // Test good insert request on a different group_type
        //assertTrue(switchgroupDao.insertSwitchRequest("member0", "test0", 2) > 0);

        // Test wrong accept request on the same member
        //assertFalse(switchgroupDao.acceptSwitchRequest("member1", "member1", "test0"));

        // Test good accept request on a different member in the
        //assertTrue(switchgroupDao.acceptSwitchRequest("member0", "member1", "test0"));

    }
}
