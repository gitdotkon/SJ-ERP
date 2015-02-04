package com.deere.model.dto;

import java.util.Date;

import com.deere.model.GenericPart;

public class PartDto extends GenericPart {
	private String address;
	private Integer quantity;

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

	public PartDto(){}
	public PartDto(String partCode, String partName, String partType,String address, Integer quantity,String modifiedBy,Date modifiedDate) {
		this.setPartCode(partCode);
		this.setPartName(partName);
		this.setPartType(partType);
		this.setModifiedBy(modifiedBy);
		this.setModifiedDate(modifiedDate);
		this.address = address;
		this.quantity = quantity;
	}
	
	
	public GenericPart getPart(){
		GenericPart part = new GenericPart();
		part.setPartCode(this.getPartCode());
		part.setPartName(this.getPartName());
		part.setPartType(this.getPartType());
		return part;
		
	}
	
	

}
