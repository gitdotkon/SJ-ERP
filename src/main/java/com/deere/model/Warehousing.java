package com.deere.model;

import java.util.List;

import com.deere.model.dto.PartDto;

public class Warehousing {
	private List<PartDto> sucList;
	private List<PartDto> failList;
	public List<PartDto> getSucList() {
		return sucList;
	}
	public void setSucList(List<PartDto> sucList) {
		this.sucList = sucList;
	}
	public List<PartDto> getFailList() {
		return failList;
	}
	public void setFailList(List<PartDto> failList) {
		this.failList = failList;
	}
	
	
}
