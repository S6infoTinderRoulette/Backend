package com.tinderroulette.backend.rest.model;

import java.io.Serializable;

public class GroupStudentID implements Serializable {
    private String cip;
    private int idGroup;

    public GroupStudentID(String cip, int idGroup) {
        this.cip = cip;
        this.idGroup = idGroup;
    }

    public GroupStudentID() {
    }

    public String getCip() {
        return cip;
    }

    public void setCip(String cip) {
        this.cip = cip;
    }

    public int getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(int idGroup) {
        this.idGroup = idGroup;
    }

    @Override
    public boolean equals(Object comparedObject) {
        if (comparedObject == this) {
            return true;
        }
        if (!(comparedObject instanceof GroupStudentID)) {
            return false;
        }
        GroupStudentID groupStudentId = (GroupStudentID) comparedObject;
        return groupStudentId.cip.equalsIgnoreCase(cip) && groupStudentId.idGroup == idGroup;
    }
}
