package com.tinderroulette.backend.test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

import org.json.JSONArray;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.tinderroulette.backend.rest.Application;
import com.tinderroulette.backend.rest.controller.PartitionneurController;
import com.tinderroulette.backend.rest.dao.ClassesDao;
import com.tinderroulette.backend.rest.dao.GroupStudentDao;
import com.tinderroulette.backend.rest.dao.GroupTypeDao;
import com.tinderroulette.backend.rest.dao.GroupsDao;
import com.tinderroulette.backend.rest.dao.MemberClassDao;
import com.tinderroulette.backend.rest.model.GroupStudent;
import com.tinderroulette.backend.rest.model.GroupType;
import com.tinderroulette.backend.rest.model.Groups;
import com.tinderroulette.backend.rest.model.MemberClass;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@TestPropertySource(locations = "classpath:test.properties")
@TestExecutionListeners({ TestListener.class, DependencyInjectionTestExecutionListener.class })
public class PartitionneurTest {

    @Autowired
    private GroupsDao groupsDao;

    @Autowired
    private GroupStudentDao groupStudentDao;

    @Autowired
    private ClassesDao classesDao;

    @Autowired
    private GroupTypeDao groupTypeDao;

    @Autowired
    private MemberClassDao memberClassDao;

    @Test
    public void splitWithSpecificNumber() {
        PartitionneurController partitionneurController = new PartitionneurController(groupsDao, groupStudentDao,
                classesDao, groupTypeDao, memberClassDao);
        List<List<MemberClass>> splittedGroups = partitionneurController.createGroupNbPerson("test0", 4);
        splittedGroups = this.sort(splittedGroups);
        assertEquals(splittedGroups.size(), 3);
        for (int i = 0; i < 3; i++) {
            if (i != 2)
                assertEquals(splittedGroups.get(i).size(), 4);
            else
                assertEquals(splittedGroups.get(i).size(), 3);
        }
    }

    @Test
    public void splitWithMultipleSize() throws Exception {
        PartitionneurController partitionneurController = new PartitionneurController(groupsDao, groupStudentDao,
                classesDao, groupTypeDao, memberClassDao);
        List<List<MemberClass>> splittedGroups = partitionneurController.createGroupArraySizes("test0",
                new int[] { 2, 2, 2, 5 });
        splittedGroups = this.sort(splittedGroups);
        assertEquals(splittedGroups.size(), 4);
        for (int i = 0; i < 4; i++) {
            if (i != 0)
                assertEquals(splittedGroups.get(i).size(), 2);
            else
                assertEquals(splittedGroups.get(i).size(), 5);
        }
    }

    @Test
    public void saveGroup() throws Exception {
        PartitionneurController partitionneurController = new PartitionneurController(groupsDao, groupStudentDao,
                classesDao, groupTypeDao, memberClassDao);
        List<List<MemberClass>> splittedGroups = partitionneurController.createGroupNbPerson("test1", 2);
        splittedGroups = this.sort(splittedGroups);
        JSONArray jsonSplittedGroups = new JSONArray(splittedGroups);
        List<List<GroupStudent>> groups = partitionneurController.saveGroupStudent(jsonSplittedGroups, "test1",
                GroupType.types.Other.id);
        groups = this.sort(groups);
        assertEquals(6, groups.size());
        for (int i = 0; i < 6; i++) {
            if (i != 5)
                assertEquals(groups.get(i).size(), 2);
            else
                assertEquals(groups.get(i).size(), 1);
        }
        Optional<Groups> group = groupsDao.findById(groups.get(0).get(0).getIdGroup());
        assertTrue(group.isPresent());
        assertEquals(group.get().getIdGroupType(), GroupType.types.Other.id);
    }

    public <T> List<List<T>> sort(List<List<T>> list) {
        list.sort((subList1, subList2) -> subList2.size() - subList1.size());
        return list;
    }
}
