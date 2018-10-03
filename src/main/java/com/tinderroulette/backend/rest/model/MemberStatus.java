package com.tinderroulette.backend.rest.model;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;

public class MemberStatus {

    @Id
    private int idMemberStatus;
    @NotNull
    private String Status;

    public MemberStatus() {
    }

    public MemberStatus(int idMemberStatus, String status) {
        this.idMemberStatus = idMemberStatus;
        Status = status;
    }

    public int getIdMemberStatus() {
        return idMemberStatus;
    }

    public void setIdMemberStatus(int idMemberStatus) {
        this.idMemberStatus = idMemberStatus;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
