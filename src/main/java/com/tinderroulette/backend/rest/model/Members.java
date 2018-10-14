package com.tinderroulette.backend.rest.model;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "members")
@JsonPropertyOrder({"cip_etudiant", "courriel", "nom", "prenom"})
@JsonIgnoreProperties({"app","cote_r","departement","inscription", "profil_id", "programme","trimestre_id", "unit_id","idMemberStatus"})
public class Members {

    @Id
    @JsonAlias({ "cip_etudiant", "cip" }) private String cip;
    private int idMemberStatus;
    @JsonAlias({ "nom", "lastName" }) private String lastName;
    @JsonAlias({ "prenom", "firstName" }) private String firstName;
    @JsonAlias({ "courriel", "email" }) private String email;

    public Members() {
    }

    public Members(String cip, int idMemberStatus, String lastName, String firstName, String email) {
        this.cip = cip;
        this.idMemberStatus = idMemberStatus;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
    }

    public String getCip() {
        return cip;
    }

    public void setCip(String cip) {
        this.cip = cip;
    }

    public int getIdMemberStatus() {
        return idMemberStatus;
    }

    public void setIdMemberStatus(int idMemberStatus) {
        this.idMemberStatus = idMemberStatus;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
