package com.deere.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class InventoryClass {
	private String invCode;
	private String invName;
	private String invGrade;
	private Boolean invEnd;
	
	@Id
	public String getInvCode() {
		return invCode;
	}
	public void setInvCode(String invCode) {
		this.invCode = invCode;
	}
	public String getInvName() {
		return invName;
	}
	public void setInvName(String invName) {
		this.invName = invName;
	}
	public String getInvGrade() {
		return invGrade;
	}
	public void setInvGrade(String invGrade) {
		this.invGrade = invGrade;
	}
	public Boolean getInvEnd() {
		return invEnd;
	}
	public void setInvEnd(Boolean invEnd) {
		this.invEnd = invEnd;
	}
	
	

}
