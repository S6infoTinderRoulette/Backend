package com.tinderroulette.backend.rest.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.lang.Nullable;

@Entity
@Table(name = "groups")
public class Groups {
	@Id
	@GeneratedValue
	private int idGroup;
	private int idGroupType;
	@Nullable
	private Integer idActivity;
	@Nullable
	private String idClass;
	@Nullable
	private int groupIndex;

	public Groups(int idGroup, int idGroupType, Integer idActivity, String idClass, int groupIndex) {
		this.idGroup = idGroup;
		this.idGroupType = idGroupType;
		this.idActivity = idActivity;
		this.idClass = idClass;
		this.groupIndex = groupIndex;
	}

	public Groups(int idGroupType, Integer idActivity, String idClass, int groupIndex) {
		this.idGroupType = idGroupType;
		this.idActivity = idActivity;
		this.idClass = idClass;
		this.groupIndex = groupIndex;
	}

	public Groups() {
	}

	public int getIdGroup() {
		return idGroup;
	}

	public void setIdGroup(int idGroup) {
		this.idGroup = idGroup;
	}

	public int getIdGroupType() {
		return idGroupType;
	}

	public void setIdGroupType(int idGroupType) {
		this.idGroupType = idGroupType;
	}

	public Integer getIdActivity() {
		return idActivity;
	}

	public void setIdActivity(Integer idActivity) {
		this.idActivity = idActivity;
	}

	public String getIdClass() {
		return idClass;
	}

	public void setIdClass(String idClass) {
		this.idClass = idClass;
	}

	public int getGroupIndex() {
		return groupIndex;
	}

	public void setGroupIndex(int groupIndex) {
		this.groupIndex = groupIndex;
	}
}
