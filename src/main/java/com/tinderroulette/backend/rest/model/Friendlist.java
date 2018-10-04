package com.tinderroulette.backend.rest.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table (name = "friendlist")
@IdClass(FriendlistId.class)
public class Friendlist {
    @Id
    private String cip;
    @Id
    private String friendCip;

    public Friendlist(String cip, String friendCip) {
        this.cip = cip;
        this.friendCip = friendCip;
    }

    public Friendlist() {
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
