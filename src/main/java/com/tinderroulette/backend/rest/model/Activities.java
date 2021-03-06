package com.tinderroulette.backend.rest.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "activities")
public class Activities {
	@Id
	private int idActivity;
	@NotNull
	private String idClass;
	private String cipInCharge;
	@NotNull
	private int nbPartners;
	@NotNull
	private boolean Final;

	public Activities(int idActivity, String idClass, String cipInCharge, int nbPartners, boolean Final) {
		this.idActivity = idActivity;
		this.idClass = idClass;
		this.cipInCharge = cipInCharge;
		this.nbPartners = nbPartners;
		this.Final = Final;
	}

	public Activities() {
	}

	public int getIdActivity() {
		return idActivity;
	}

	public void setIdActivity(int idActivity) {
		this.idActivity = idActivity;
	}

	public String getIdClass() {
		return idClass;
	}

	public void setIdClass(String idClass) {
		this.idClass = idClass;
	}

	public String getCipInCharge() {
		return cipInCharge;
	}

	public void setCipInCharge(String cipInCharge) {
		this.cipInCharge = cipInCharge;
	}

	public int getNbPartners() {
		return nbPartners;
	}

	public void setNbPartners(int nbPartners) {
		this.nbPartners = nbPartners;
	}

	public boolean isFinal() {
		return Final;
	}

	public void setFinal(boolean isFinal) {
		this.Final = isFinal;
	}
}
