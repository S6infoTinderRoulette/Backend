package com.tinderroulette.backend.rest.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "classes")
public class Classes {
    @Id
    private String idClass;
    private String idApp;
    private String idAp;

    public Classes(String idClass, String idApp, String idAp) {
        this.idClass = idClass;
        this.idApp = idApp;
        this.idAp = idAp;
    }

    public Classes() {
    }

    public String getIdClass() {
        return idClass;
    }

    public void setIdClass(String idClass) {
        this.idClass = idClass;
    }

    public String getIdApp() {
        return idApp;
    }

    public void setIdApp(String idApp) {
        this.idApp = idApp;
    }

    public String getIdAp() {
        return idAp;
    }

    public void setIdAp(String idAp) {
        this.idAp = idAp;
    }
}
