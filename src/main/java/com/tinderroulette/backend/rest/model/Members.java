package com.tinderroulette.backend.rest.model;


import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Members {

    @Id
    private String cip;
    private int id_member_status;
    private String last_name;
    private String first_name;
    private String email;

    public Members (){

    }

    public Members(String cip, int id_member_status, String last_name, String first_name, String email) {
        this.cip = cip;
        this.id_member_status = id_member_status;
        this.last_name = last_name;
        this.first_name = first_name;
        this.email = email;
    }

    public String getCip() {
        return cip;
    }

    public void setCip(String cip) {
        this.cip = cip;
    }

    public int getId_member_status() {
        return id_member_status;
    }

    public void setId_member_status(int id_member_status) {
        this.id_member_status = id_member_status;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
