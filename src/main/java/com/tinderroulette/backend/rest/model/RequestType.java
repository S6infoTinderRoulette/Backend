package com.tinderroulette.backend.rest.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "requesttype")
public class RequestType {

    @Id
    private int idRequestType;

    @NotNull
    private String requestType;

    public RequestType(int idRequestType, @NotNull String requestType) {
        this.idRequestType = idRequestType;
        this.requestType = requestType;
    }

    public RequestType() {
    }

    public int getIdRequestType() {
        return idRequestType;
    }

    public void setIdRequestType(int idRequestType) {
        this.idRequestType = idRequestType;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }
}
