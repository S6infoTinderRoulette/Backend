package com.tinderroulette.backend.rest.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "memberstatus")
public class MemberStatus {

    @Id
    private int idMemberStatus;
    @NotNull
    private String status;

    public MemberStatus() {
    }

    public MemberStatus(int idMemberStatus, String status1) {
        this.idMemberStatus = idMemberStatus;
        status = status1;
    }

    public int getIdMemberStatus() {
        return idMemberStatus;
    }

    public void setIdMemberStatus(int idMemberStatus) {
        this.idMemberStatus = idMemberStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status1) {
        status = status1;
    }
}
