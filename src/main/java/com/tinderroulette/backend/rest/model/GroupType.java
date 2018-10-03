package com.tinderroulette.backend.rest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.lang.Nullable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "grouptype")
public class GroupType {

    @Id
    private int idGroupType;

    @NotNull
    private String type;

    @Nullable
    private Integer minDefault;

    @Nullable
    private Integer maxDefault;

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
