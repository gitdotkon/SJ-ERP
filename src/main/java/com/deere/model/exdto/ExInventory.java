package com.deere.model.exdto;

import javax.persistence.Id;

import com.deere.common.ExcelAnnotation;
import com.deere.model.GenericPart;

public class ExInventory {
	@ExcelAnnotation(field = "图号")
	private String partCode;
	@ExcelAnnotation(field = "存放地址")
	private String address;
	@ExcelAnnotation(field = "现结存数量")
	private Integer quantity;
	
	private GenericPart part;
	
	@Id
	public String getPartCode() {
		return partCode;
	}
	public void setPartCode(String partCode) {
		this.partCode = partCode;
		GenericPart gpart= new GenericPart();
		gpart.setPartCode(partCode);
		setPart(gpart);
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
	public GenericPart getPart() {
		return part;
	}
	public void setPart(GenericPart part) {
		this.part = part;
	}
	
	
}
