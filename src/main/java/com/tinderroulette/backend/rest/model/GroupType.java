package com.tinderroulette.backend.rest.model;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;

public class GroupType {

    @Id
    private int idGroupType;

    @NotNull
    private String type;

    private int minDefault;

    private int maxDefault;

    public GroupType() {
    }

    public GroupType(int idGroupType, @NotNull String type, int minDefault, int maxDefault) {
        this.idGroupType = idGroupType;
        this.type = type;
        this.minDefault = minDefault;
        this.maxDefault = maxDefault;
    }

    public int getIdGroupType() {
        return idGroupType;
    }

    public void setIdGroupType(int idGroupType) {
        this.idGroupType = idGroupType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getMinDefault() {
        return minDefault;
    }

    public void setMinDefault(int minDefault) {
        this.minDefault = minDefault;
    }

    public int getMaxDefault() {
        return maxDefault;
    }

    public void setMaxDefault(int maxDefault) {
        this.maxDefault = maxDefault;
    }
}
