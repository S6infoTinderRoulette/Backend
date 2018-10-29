package com.tinderroulette.backend.rest.model;

import java.io.Serializable;

public class ParametercacheId implements Serializable {
    private String cip;
    private String idClass;
    private int idGroupType;

    public ParametercacheId(String cip, String idClass, int idGroupType) {
        this.cip = cip;
        this.idClass = idClass;
        this.idGroupType = idGroupType;
    }

    public ParametercacheId() {
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
}
