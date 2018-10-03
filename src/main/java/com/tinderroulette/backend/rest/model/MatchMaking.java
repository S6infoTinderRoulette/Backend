package com.tinderroulette.backend.rest.model;

public class MatchMaking {

    private final long id;
    private final String nom;
    private final String cip;

    public MatchMaking(long id, String nom, String cip) {
        this.id = id;
        this.nom = nom;
        this.cip = cip;
    }

    public long getId() {
        return this.id;
    }

    public String getNom() {
        return this.nom;
    }

    public String getCip() {
        return this.cip;
    }



}
