package com.tinderroulette.backend.rest.model;

import java.util.List;

public class SubGroup {
    Integer idGroup;
    List<GroupStudent> groupStudentList;

    public SubGroup(Integer idGroup, List<GroupStudent> groupStudentList) {
        this.idGroup = idGroup;
        this.groupStudentList = groupStudentList;
    }

    public SubGroup() {

    }

    public Integer getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(Integer idGroup) {
        this.idGroup = idGroup;
    }

    public List<GroupStudent> getGroupStudentList() {
        return groupStudentList;
    }

    public void setGroupStudentList(List<GroupStudent> groupStudentList) {
        this.groupStudentList = groupStudentList;
    }

}
