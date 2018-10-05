package com.tinderroulette.backend.rest.controller;

import com.tinderroulette.backend.rest.dao.ActivitiesDao;
import com.tinderroulette.backend.rest.dao.GroupTypeDao;
import com.tinderroulette.backend.rest.dao.GroupsDao;
import com.tinderroulette.backend.rest.dao.MemberClassDao;
import com.tinderroulette.backend.rest.model.Activities;
import com.tinderroulette.backend.rest.model.MemberClass;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class PartionneurController {

    private GroupsDao groupsDao;
    private ActivitiesDao activitiesDao;
    private GroupTypeDao groupTypeDao;
    private MemberClassDao memberClassDao;

    public PartionneurController(GroupsDao groupsDao, ActivitiesDao activitiesDao, GroupTypeDao groupTypeDao, MemberClassDao memberClassDao) {
        this.groupsDao = groupsDao;
        this.activitiesDao = activitiesDao;
        this.groupTypeDao = groupTypeDao;
        this.memberClassDao = memberClassDao;
    }

    @GetMapping (value = "/creationGroupe/{idActivity}/")
    public List<MemberClass> createGroupNoParam (@PathVariable int idActivity) {
        Activities activities = activitiesDao.findByIdActivity(idActivity);
        List<MemberClass> EtudiantsActivite = memberClassDao.findByIdClass(activities.getIdClass());
        return EtudiantsActivite;
    }

    @GetMapping (value ="/creationGroupe/{idActivity}/{nbPersonnes}/")
    public List<List<MemberClass>> createGroupNbPersonne (@PathVariable int idActivity, @PathVariable int nbPersonnes) {
        Activities activities = activitiesDao.findByIdActivity(idActivity);
        List<MemberClass> EtudiantsActivite = memberClassDao.findByIdClass(activities.getIdClass());
        Collections.shuffle(EtudiantsActivite);
        System.out.println ("test0");
        List<List<MemberClass>> Groups = new ArrayList<List<MemberClass>>();
        Iterator<MemberClass> iterator = EtudiantsActivite.iterator();
        while (iterator.hasNext()) {
            System.out.println ("test1");
            List<MemberClass> tempoList = new ArrayList<>();
            for (int j = 0; j < nbPersonnes; j++) {
                if(iterator.hasNext()) {
                    tempoList.add(iterator.next());
                    System.out.println ("test2");
                } else {
                    break; 
                }
            }
            Groups.add(tempoList);
            System.out.println ("test3");
        }
        return Groups;
    }

    @GetMapping (value ="/creationGroupe/{idActivity}/{idGroupType}/")
    public List<List<MemberClass>> createGroupGroupType (@PathVariable int idActivity, @PathVariable int idGroupType) {
        Activities activities = activitiesDao.findByIdActivity(idActivity);
        List<MemberClass> EtudiantsActivite = memberClassDao.findByIdClass(activities.getIdClass());
        Collections.shuffle(EtudiantsActivite);
        System.out.println ("test0");
        List<List<MemberClass>> Groups = new ArrayList<List<MemberClass>>();
        Iterator<MemberClass> iterator = EtudiantsActivite.iterator();
        while (iterator.hasNext()) {
            System.out.println ("test1");
            List<MemberClass> tempoList = new ArrayList<>();
            for (int j = 0; j < nbPersonnes; j++) {
                if(iterator.hasNext()) {
                    tempoList.add(iterator.next());
                    System.out.println ("test2");
                } else {
                    break;
                }
            }
            Groups.add(tempoList);
            System.out.println ("test3");
        }
        return Groups;
    }


}
