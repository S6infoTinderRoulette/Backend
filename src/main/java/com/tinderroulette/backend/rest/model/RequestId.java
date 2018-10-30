package com.tinderroulette.backend.rest.model;

import java.io.Serializable;

public class RequestId implements Serializable {
    private int idActivity;
    private String cipSeeking;
    private String cipRequested;

	public RequestId(int idActivity, String cipSeeking, String cipRequested) {
        this.idActivity = idActivity;
        this.cipSeeking = cipSeeking;
        this.cipRequested = cipRequested;
    }

    public RequestId() {
    }

    public int getIdActivity() {
        return idActivity;
    }

    public void setIdActivity(int idActivity) {
        this.idActivity = idActivity;
    }

    public String getCipSeeking() {
        return cipSeeking;
    }

    public void setCipSeeking(String cipSeeking) {
        this.cipSeeking = cipSeeking;
    }

    public String getCipRequested() {
        return cipRequested;
    }

    public void setCipRequested(String cipRequested) {
        this.cipRequested = cipRequested;
    }
}
