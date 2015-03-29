package com.deere.model.dto;


import com.deere.model.GenericPart;

public class ProductionDto {
	private String partCode;
	private String partName;
	private String partType;
	private Integer requiredQty;
	private Integer inventoryQty;
	private Integer recommandedQty;
	private Integer processingQty;
	private Integer reservedQty;
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
	
	public Integer getProcessingQty() {
		return processingQty;
	}
	public void setProcessingQty(Integer processingQty) {
		this.processingQty = processingQty;
	}
	
	public Integer getReservedQty() {
		return reservedQty;
	}
	public void setReservedQty(Integer reservedQty) {
		this.reservedQty = reservedQty;
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
	
	public ProductionDto( GenericPart part,  Long requiredQty, Long processingQty,Long reservedQty) {
		this.partCode = part.getPartCode();
		this.partName=part.getPartName();
		this.partType=part.getPartType();
		this.requiredQty = requiredQty != null ? requiredQty.intValue() : 0;
		this.processingQty = processingQty != null ? processingQty.intValue() : 0;
		this.reservedQty=reservedQty != null ? reservedQty.intValue() : 0;
	}
	
	
	
}
