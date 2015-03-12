package com.deere.model.dto;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.deere.model.GenericPart;

public class ProductionDto {
	private String partCode;
	private String partName;
	private String partType;
	private Integer requiredQty;
	private Integer inventoryQty;
	private Integer recommandedQty;
	private Integer actualQty;
	private Boolean proType =true;
	
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
	public Integer getRequiredQty() {
		return requiredQty;
	}
	public void setRequiredQty(Integer requiredQty) {
		this.requiredQty = requiredQty;
	}
	
	
	public Integer getInventoryQty() {
		return inventoryQty;
	}
	public void setInventoryQty(Integer inventoryQty) {
		this.inventoryQty = inventoryQty;
	}
	public Integer getRecommandedQty() {
		return recommandedQty;
	}
	public void setRecommandedQty(Integer recommandedQty) {
		this.recommandedQty = recommandedQty;
	}
	public Integer getActualQty() {
		return actualQty;
	}
	public void setActualQty(Integer actualQty) {
		this.actualQty = actualQty;
	}
	public Boolean getProType() {
		return proType;
	}
	public void setProType(Boolean proType) {
		this.proType = proType;
	}
	public ProductionDto(){
		
	}
	
//	public ProductionDto( String partCode, String partName,
//			String partType, Integer requiredQty) {
//		this.partCode = partCode;
//		this.partName = partName;
//		this.partType = partType;
//		this.requiredQty = requiredQty;
	
	public ProductionDto( GenericPart part,  Long requiredQty) {
		this.partCode = part.getPartCode();
		this.partName=part.getPartName();
		this.partType=part.getPartType();
		this.requiredQty = requiredQty != null ? requiredQty.intValue() : null;
	}
	
	
	
}
