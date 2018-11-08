package com.tinderroulette.backend.rest.model;

import com.tinderroulette.backend.rest.DBConnection.MembersDeserializer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import javax.persistence.*;

@Entity
@Table(name = "members")
@JsonIgnoreProperties({"app","cote_r","departement","inscription", "profil_id", "programme","trimestre_id", "unit_id"})
@JsonDeserialize(using = MembersDeserializer.class)
public class Members {

    @Id
    @Column(name = "cip", columnDefinition="VARCHAR(8)")
    private String cip;

    @Column(name = "id_member_status")
    private int idMemberStatus;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "email")
    private String email;

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

    @Override
    public boolean equals(Object comparedObject) {
        if (comparedObject == this) {
            return true;
        }
        if (!(comparedObject instanceof Members)) {
            return false;
        }
        Members member = (Members) comparedObject;
        return member.cip.equalsIgnoreCase(cip) && member.firstName.equalsIgnoreCase(firstName) && member.lastName.equalsIgnoreCase(lastName)
                && member.idMemberStatus == idMemberStatus && member.email.equalsIgnoreCase(email);
    }
}
