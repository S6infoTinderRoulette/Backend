package com.tinderroulette.backend.rest.model;

import java.io.Serializable;

public class MemberClassId implements Serializable {

    private String cip;
    private String idClass;

    public MemberClassId(String cip, String idClass) {
        this.cip = cip;
        this.idClass = idClass;
    }

    public MemberClassId() {
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

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof MemberClassId)) {
            return false;
        }
        MemberClassId m = (MemberClassId) o;
        return m.cip.equalsIgnoreCase(cip) && m.idClass.equalsIgnoreCase(idClass);
    }
}