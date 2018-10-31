package com.tinderroulette.backend.rest.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import org.springframework.lang.Nullable;

@Entity
@Table(name = "switchgrouprequest")
@IdClass(SwitchGroupRequestId.class)
public class SwitchGroupRequest {

	@Id
	private int idGroup;

	@Id
	private String cip;

	@Id
	private String idClass;

	@Nullable
	@Column(insertable = false)
	private Date switchgroupTimestamp;

	public SwitchGroupRequest() {
	}

	public SwitchGroupRequest(int idGroup, String cip, String idClass, Date switchgroupTimestamp) {
		this.idGroup = idGroup;
		this.cip = cip;
		this.idClass = idClass;
		this.switchgroupTimestamp = switchgroupTimestamp;
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

	public Date getSwitchgroupTimestamp() {
		return switchgroupTimestamp;
	}

	public void setSwitchgroupTimestamp(Date switchgroupTimestamp) {
		this.switchgroupTimestamp = switchgroupTimestamp;
	}
}
