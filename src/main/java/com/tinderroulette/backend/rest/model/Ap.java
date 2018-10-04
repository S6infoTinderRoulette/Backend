package com.tinderroulette.backend.rest.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ap")
public class Ap {

    @Id
    private String idAp;
    private String description;

    public Ap(String idAp, String description) {
        this.idAp = idAp;
        this.description = description;
    }

    public Ap() {
    }

    public String getIdAp() {
        return idAp;
    }

    public void setIdAp(String idAp) {
        this.idAp = idAp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
