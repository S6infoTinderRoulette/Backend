package com.tinderroulette.backend.rest.model;

import java.io.Serializable;

public class FriendlistId implements Serializable {
    private String cip;
    private String friendCip;

    public FriendlistId(String cip, String friendCip) {
        this.cip = cip;
        this.friendCip = friendCip;
    }

    public FriendlistId() {
    }

    public String getCip() {
        return cip;
    }

    public void setCip(String cip) {
        this.cip = cip;
    }

    public String getFriendCip() {
        return friendCip;
    }

    public void setFriendCip(String friendCip) {
        this.friendCip = friendCip;
    }
}
