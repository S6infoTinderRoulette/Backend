package com.tinderroulette.backend.rest.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "groupstudent")
@IdClass(GroupStudentID.class)
public class GroupStudent {

    @Id
    private String cip;
    @Id
    private int idGroup;

    public GroupStudent(String cip, int idGroup) {
        this.cip = cip;
        this.idGroup = idGroup;
    }

    public GroupStudent() {
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
        if (!(comparedObject instanceof GroupStudent)) {
            return false;
        }
        GroupStudent groupStudent = (GroupStudent) comparedObject;
        return groupStudent.cip.equalsIgnoreCase(cip) && groupStudent.idGroup == idGroup;
    }
}
