package com.tinderroulette.backend.rest.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import org.springframework.lang.Nullable;

@Entity
@Table(name = "friendrequest")
@IdClass(FriendRequestId.class)
public class FriendRequest {

	@Id
	private String cipSeeking;

	@Id
	private String cipRequested;

	@Nullable
	@Column(insertable = false)
	private Date friendrequestTimestamp;

	public FriendRequest() {
	}

	public FriendRequest(String cipSeeking, String cipRequested, Date friendrequestTimestamp) {
		this.cipSeeking = cipSeeking;
		this.cipRequested = cipRequested;
		this.friendrequestTimestamp = friendrequestTimestamp;
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

	public Date getFriendrequestTimestamp() {
		return friendrequestTimestamp;
	}

	public void setFriendrequestTimestamp(Date friendrequestTimestamp) {
		this.friendrequestTimestamp = friendrequestTimestamp;
	}
}
