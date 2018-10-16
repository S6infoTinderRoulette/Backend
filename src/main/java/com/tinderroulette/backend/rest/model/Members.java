package com.tinderroulette.backend.rest.model;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "members")
public class Members {

    @Id
    private String cip;
    private int idMemberStatus;
    private String lastName;
    private String firstName;
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
