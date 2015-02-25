package com.deere.model.dto;

import java.util.Date;

import com.deere.model.GenericPart;

public class ExStockDto extends PartDto {
	
	private Integer requireQty;
	private Integer retrievalQty;
	public Integer getRequireQty() {
		return requireQty;
	}
	public void setRequireQty(Integer requireQty) {
		this.requireQty = requireQty;
	}
	public Integer getRetrievalQty() {
		return retrievalQty;
	}
	public void setRetrievalQty(Integer retrievalQty) {
		this.retrievalQty = retrievalQty;
	}
	
	
}
