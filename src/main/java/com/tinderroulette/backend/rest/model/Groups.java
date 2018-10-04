package com.tinderroulette.backend.rest.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table (name= "groups")
public class Groups {
    @Id
    private int idGroup;
    private int idGroupType;
    @NotNull
    private int idActivity;

    public Groups(int idGroup, int idGroupType, int idActivity) {
        this.idGroup = idGroup;
        this.idGroupType = idGroupType;
        this.idActivity = idActivity;
    }

    public Groups() {
    }

    public int getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(int idGroup) {
        this.idGroup = idGroup;
    }

    public int getIdGroupType() {
        return idGroupType;
    }

    public void setIdGroupType(int idGroupType) {
        this.idGroupType = idGroupType;
    }

    public int getIdActivity() {
        return idActivity;
    }

    public void setIdActivity(int idActivity) {
        this.idActivity = idActivity;
    }
}
