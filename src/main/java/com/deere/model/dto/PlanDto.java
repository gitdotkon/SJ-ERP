package com.deere.model.dto;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class PlanDto {
	@Id
	private String partCode;
	private Integer requiredQty;
	private Integer processingQty;
	private Integer reservedQty;
	public String getPartCode() {
		return partCode;
	}
	public void setPartCode(String partCode) {
		this.partCode = partCode;
	}
	public Integer getRequiredQty() {
		return requiredQty;
	}
	public void setRequiredQty(Integer requiredQty) {
		this.requiredQty = requiredQty;
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
	
	
}
