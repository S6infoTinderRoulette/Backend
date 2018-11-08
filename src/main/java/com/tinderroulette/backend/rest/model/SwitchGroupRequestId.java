package com.tinderroulette.backend.rest.model;

import java.io.Serializable;

public class SwitchGroupRequestId implements Serializable {
	private int idGroup;
	private String cip;
	private String idClass;

	public SwitchGroupRequestId(int idGroup, String cip, String idClass) {
		this.idGroup = idGroup;
		this.cip = cip;
		this.idClass = idClass;
	}

	public SwitchGroupRequestId() {
	}

	public int getIdGroup() {
		return idGroup;
	}

	public void setIdGroup(int idGroup) {
		this.idGroup = idGroup;
	}

	public String getCip() {
		return cip;
	}

	public void setCip(String cip) {
		this.cip = cip;
	}

	public String getIdClass() {
		return idClass;
	}

	public void setIdClass(String idClass) {
		this.idClass = idClass;
	}
}
