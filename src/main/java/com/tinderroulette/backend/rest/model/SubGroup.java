package com.tinderroulette.backend.rest.model;

import java.util.List;

public class SubGroup {
    int idGroup;
    List<GroupStudent> groupStudentList;

    public SubGroup(int idGroup, List<GroupStudent> groupStudentList) {
        this.idGroup = idGroup;
        this.groupStudentList = groupStudentList;
    }

    public SubGroup() {

    }

    public int getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(int idGroup) {
        this.idGroup = idGroup;
    }

    public List<GroupStudent> getGroupStudentList() {
        return groupStudentList;
    }

    public void setGroupStudentList(List<GroupStudent> groupStudentList) {
        this.groupStudentList = groupStudentList;
    }

}
