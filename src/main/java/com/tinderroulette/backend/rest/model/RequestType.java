package com.tinderroulette.backend.rest.model;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;

public class RequestType {

    @Id
    private int idRequestType;
    @NotNull
    private String Type;

    public RequestType() {
    }

    public RequestType(int idRequestType, @NotNull String type) {
        this.idRequestType = idRequestType;
        Type = type;
    }

    public int getIdRequestType() {
        return idRequestType;
    }

    public void setIdRequestType(int idRequestType) {
        this.idRequestType = idRequestType;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}
