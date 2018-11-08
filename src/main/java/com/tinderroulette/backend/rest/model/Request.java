package com.tinderroulette.backend.rest.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import org.springframework.lang.Nullable;

@Entity
@Table(name = "request")
@IdClass(RequestId.class)
public class Request {

    @Id
    private int idActivity;

    @Id
    private String cipSeeking;

    @Id
    private String cipRequested;

    @Nullable
    @Column(insertable = false)
    private Date requestTimestamp;

    public Request() {
    }

	public Request(int idActivity, String cipSeeking, String cipRequested, Date requestTimestamp) {
        this.idActivity = idActivity;
        this.cipSeeking = cipSeeking;
        this.cipRequested = cipRequested;
        this.requestTimestamp = requestTimestamp;
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
    public Date getRequestTimestamp() {
        return requestTimestamp;
    }

    public void setRequestTimestamp(Date requestTimestamp) {
        this.requestTimestamp = requestTimestamp;
    }

}
