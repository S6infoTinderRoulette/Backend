package com.tinderroulette.backend.rest.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "logs")
public class Logs {

    @Id
    private int idLog;

    private String cip;

    @NotNull
    private String description;

    @NotNull
    private Date logTimestamp;

    public Logs(int idLog, String cip, String description, Date logTimestamp) {
        this.idLog = idLog;
        this.cip = cip;
        this.description = description;
        this.logTimestamp = logTimestamp;
    }

    public Logs() {
    }

    public int getIdLog() {
        return idLog;
    }

    public void setIdLog(int idLog) {
        this.idLog = idLog;
    }

    public String getCip() {
        return cip;
    }

    public void setCip(String cip) {
        this.cip = cip;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getLogTimestamp() {
        return logTimestamp;
    }

    public void setLogTimestamp(Date logTimestamp) {
        this.logTimestamp = logTimestamp;
    }
}
