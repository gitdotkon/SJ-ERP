package com.deere.model.dto;

public class ProductionDto {
	private String parentCode;
	private String partCode;
	private String partName;
	private String partType;
	private Integer requiredQty;
	private Integer quantity;
	private Integer recommandedQty;
	private Integer actualQty;
	private Boolean proType =true;
	
	public String getParentCode() {
		return parentCode;
	}
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
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
	
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
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
	
	
}
