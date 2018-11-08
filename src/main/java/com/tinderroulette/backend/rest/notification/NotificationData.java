package com.tinderroulette.backend.rest.notification;

import java.util.Date;

public class NotificationData {

    private String service;
    private String description;
    private Date timestamp;

    public NotificationData(String service, String description) {
        this.service = service;
        this.description = description;
        this.timestamp = new Date();
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
