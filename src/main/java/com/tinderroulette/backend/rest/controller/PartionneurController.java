package com.tinderroulette.backend.rest.controller;

import com.tinderroulette.backend.rest.dao.ActivitiesDao;
import com.tinderroulette.backend.rest.dao.GroupTypeDao;
import com.tinderroulette.backend.rest.dao.GroupsDao;
import com.tinderroulette.backend.rest.dao.MemberClassDao;
import com.tinderroulette.backend.rest.model.Activities;
import com.tinderroulette.backend.rest.model.GroupType;
import com.tinderroulette.backend.rest.model.MemberClass;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.IntStream;

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

    @GetMapping (value ="/creationGroupeNb/{idActivity}/{nbPersonnes}/")
    public List<List<MemberClass>> createGroupNbPersonne (@PathVariable int idActivity, @PathVariable int nbPersonnes) {
        Activities activities = activitiesDao.findByIdActivity(idActivity);
        List<MemberClass> EtudiantsActivite = memberClassDao.findByIdClass(activities.getIdClass());
        Collections.shuffle(EtudiantsActivite);
        List<List<MemberClass>> Groups = new ArrayList<>();
        Iterator<MemberClass> iterator = EtudiantsActivite.iterator();
        while (iterator.hasNext()) {
            List<MemberClass> tempoList = new ArrayList<>();
            for (int j = 0; j < nbPersonnes; j++) {
                if(iterator.hasNext()) {
                    tempoList.add(iterator.next());
                } else {
                    break; 
                }
            }
            Groups.add(tempoList);
        }
        return Groups;
    }

    @GetMapping (value ="/creationGroupeGroup/{idActivity}/{idGroupType}/")
    public List<List<MemberClass>> createGroupGroupType (@PathVariable int idActivity, @PathVariable int idGroupType) {
        GroupType groupType = groupTypeDao.findByIdGroupType(idGroupType);
        Activities activities = activitiesDao.findByIdActivity(idActivity);
        List<MemberClass> EtudiantsActivite = memberClassDao.findByIdClass(activities.getIdClass());
        Collections.shuffle(EtudiantsActivite);
        List<List<MemberClass>> Groups = new ArrayList<>();
        Iterator<MemberClass> iterator = EtudiantsActivite.iterator();

        int smallestRemainder = Integer.MAX_VALUE;
        int optimalValue = 0;
        for (int i = 0; i <= (groupType.getMaxDefault()-groupType.getMinDefault()); i++) {
            int currentRemainder = (EtudiantsActivite.size() % (groupType.getMinDefault() + i));
            if (currentRemainder < smallestRemainder || (currentRemainder == smallestRemainder && (groupType.getMinDefault() + i) > optimalValue )) {
                smallestRemainder = currentRemainder;
                optimalValue = groupType.getMinDefault() + i;
            }
        }

        while (iterator.hasNext()) {
            List<MemberClass> tempoList = new ArrayList<>();
            for (int j = 0; j < optimalValue; j++) {
                if(iterator.hasNext()) {
                    tempoList.add(iterator.next());
                } else {
                    break;
                }
            }
            Groups.add(tempoList);
        }
        return Groups;
    }

    @GetMapping (value ="/creationGroupeArrays/{idActivity}/{sizes}/")
    public List<List<MemberClass>> createGroupArraySizes (@PathVariable int idActivity, @PathVariable int [] sizes) throws Exception{
        Activities activities = activitiesDao.findByIdActivity(idActivity);
        List<MemberClass> EtudiantsActivite = memberClassDao.findByIdClass(activities.getIdClass());
        if (IntStream.of(sizes).sum() > EtudiantsActivite.size()) {
            throw new Exception("La somme des tailles des groupe est inférieure au nombre d'étudiants inscrits !");
        } else {
            Collections.shuffle(EtudiantsActivite);
            List<List<MemberClass>> Groups = new ArrayList<>();
            Iterator<MemberClass> iterator = EtudiantsActivite.iterator();
            while (iterator.hasNext()) {
                for (int i = 0; i < sizes.length; i++){
                    List<MemberClass> tempoList = new ArrayList<>();
                    for (int j = 0; j < sizes[i] ; j++) {
                        if(iterator.hasNext()) {
                            tempoList.add(iterator.next());
                        } else {
                            break;
                        }
                    }
                    Groups.add(tempoList);
                }
            }
            return Groups;
        }
    }

    @GetMapping (value ="/creationGroupeGroupArray/{idActivity}/{idGroupType}/{sizes}/")
    public List<List<MemberClass>> createGroupGroupTypeAndArray (@PathVariable int idActivity, @PathVariable int idGroupType, @PathVariable int [] sizes) {
        GroupType groupType = groupTypeDao.findByIdGroupType(idGroupType);
        Activities activities = activitiesDao.findByIdActivity(idActivity);
        List<MemberClass> EtudiantsActivite = memberClassDao.findByIdClass(activities.getIdClass());
        Collections.shuffle(EtudiantsActivite);
        List<List<MemberClass>> Groups = new ArrayList<>();
        Iterator<MemberClass> iteratorSizes = EtudiantsActivite.iterator();

        while (iteratorSizes.hasNext()) {
            for (int i = 0; i < sizes.length; i++){
                List<MemberClass> tempoList = new ArrayList<>();
                for (int j = 0; j < sizes[i] ; j++) {
                    if(iteratorSizes.hasNext()) {
                        tempoList.add(iteratorSizes.next());
                    } else {
                        break;
                    }
                }
                Groups.add(tempoList);
            }
        }

        int smallestRemainder = Integer.MAX_VALUE;
        int optimalValue = 0;
        if (EtudiantsActivite.size()/groupType.getMinDefault() == 0){
            optimalValue = EtudiantsActivite.size()-IntStream.of(sizes).sum();
        } else {
            for (int i = 0; i <= (groupType.getMaxDefault()-groupType.getMinDefault()); i++) {
                int currentRemainder = (EtudiantsActivite.size() % (groupType.getMinDefault() + i));
                if (currentRemainder < smallestRemainder || (currentRemainder == smallestRemainder && (groupType.getMinDefault() + i) > optimalValue )) {
                    smallestRemainder = currentRemainder;
                    optimalValue = groupType.getMinDefault() + i;
                }
            }
        }

        while (iteratorSizes.hasNext()) {
            List<MemberClass> tempoList = new ArrayList<>();
            for (int j = 0; j < optimalValue; j++) {
                if(iteratorSizes.hasNext()) {
                    tempoList.add(iteratorSizes.next());
                } else {
                    break;
                }
            }
            Groups.add(tempoList);
        }
        return Groups;
    }

}
