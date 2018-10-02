package com.tinderroulette.backend.rest;

public class MatchMaking {

    private final long id;
    private final String nom;
    private final String cip;

    public MatchMaking(long id, String nom, String cip) {
        this.id = id;
        this.nom = nom;
        this.cip = cip;
    }

    public long GetId() {
        return this.id;
    }

    public String GetNom() {
        return this.nom;
    }

    public String GetCIP() {
        return this.cip;
    }

}
