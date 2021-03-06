package com.tinderroulette.backend.rest.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.lang.Nullable;

@Entity
@Table(name = "grouptype")
public class GroupType {

    public enum types {
        Activity(1), Procedural(2), Tutorat(3), Other(4);
        public final int id;

        types(int id) {
            this.id = id;
        }
    };

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

    public GroupType(int idGroupType, @NotNull String type, Integer minDefault, Integer maxDefault) {
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

    public Integer getMinDefault() {
        return minDefault != null ? minDefault : 1;
    }

    public void setMinDefault(Integer minDefault) {
        this.minDefault = minDefault;
    }

    public Integer getMaxDefault() {
        return maxDefault != null ? maxDefault : Integer.MAX_VALUE;
    }

    public void setMaxDefault(Integer maxDefault) {
        this.maxDefault = maxDefault;
    }
}
