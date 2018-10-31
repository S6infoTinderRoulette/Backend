package com.tinderroulette.backend.rest.model;

import java.io.Serializable;

public class FriendRequestId implements Serializable {
	private String cipSeeking;
	private String cipRequested;

	public FriendRequestId(String cipSeeking, String cipRequested) {
		this.cipSeeking = cipSeeking;
		this.cipRequested = cipRequested;
	}

	public FriendRequestId() {
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
