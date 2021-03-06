package com.deere.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Inventory extends GenericModel {

	/**
	 * 
	 */
	private String partCode;
	private String partName;
	private String partType;
	private String address;
	private Integer quantity=0;
	
	
	
	@Id
	public String getPartCode() {
		return partCode;
	}

	public void setPartCode(String partCode) {
		this.partCode = partCode;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}
	

	public String getPartType() {
		return partType;
	}

	public void setPartType(String partType) {
		this.partType = partType;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	
	public void addQuantity(Integer num){
		this.quantity=this.quantity+num;
	}
}
