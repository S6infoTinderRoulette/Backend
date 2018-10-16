package com.tinderroulette.backend.rest.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "memberclass")
@IdClass(MemberClassId.class)
public class MemberClass {

    @Id
    private String cip;

    @Id
    private String idClass;

    public MemberClass() {
    }

    public MemberClass(String cip, String idClass) {
        this.cip = cip;
        this.idClass = idClass;
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
    public boolean equals(Object comparedObject) {
        if (comparedObject == this) {
            return true;
        }
        if (!(comparedObject instanceof MemberClass)) {
            return false;
        }
        MemberClass memberClass = (MemberClass) comparedObject;
        return memberClass.cip.equalsIgnoreCase(cip) && memberClass.idClass.equalsIgnoreCase(idClass);
    }
}
