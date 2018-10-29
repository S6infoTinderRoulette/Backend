package com.tinderroulette.backend.rest.model;

import javax.persistence.Entity;

@Entity
public class Parametercache {
    private String cip;
    private String idClass;
    private int idGroupType;
    private String groupsize;
    private int nbgroup;

    public Parametercache() {
    }

    public Parametercache(String cip, String idClass, int idGroupType, String groupsize, int nbgroup) {
        this.cip = cip;
        this.idClass = idClass;
        this.idGroupType = idGroupType;
        this.groupsize = groupsize;
        this.nbgroup = nbgroup;
    }

    public String getCip() {
        return cip;
    }

    public void setCip(String cip) {
        this.cip = cip;
    }

    public String getIdClass() {
        return idClass;
    }

    public void setIdClass(String idClass) {
        this.idClass = idClass;
    }

    public int getIdGroupType() {
        return idGroupType;
    }

    public void setIdGroupType(int idGroupType) {
        this.idGroupType = idGroupType;
    }

    public String getGroupsize() {
        return groupsize;
    }

    public void setGroupsize(String groupsize) {
        this.groupsize = groupsize;
    }

    public int getNbgroup() {
        return nbgroup;
    }

    public void setNbgroup(int nbgroup) {
        this.nbgroup = nbgroup;
    }
}
