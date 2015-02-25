package com.deere.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class SalesOrderItem extends GenericModel{
	/**
	 * 
	 */
	private Integer itemNum;
	private SalesOrder salesOrder;
	private GenericPart part;
	private Float price;
	private Integer quantity;
	
	
	
	@Id
	@GeneratedValue
	public Integer getItemNum() {
		return itemNum;
	}
	public void setItemNum(Integer itemNum) {
		this.itemNum = itemNum;
	}
	
	
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	@ManyToOne   
    @JoinColumn(name = "orderNum",referencedColumnName="orderNum")   
	public SalesOrder getSalesOrder() {
		return salesOrder;
	}
	public void setSalesOrder(SalesOrder salesOrder) {
		this.salesOrder = salesOrder;
	}
	@ManyToOne
	@JoinColumn(name="partCode")
	public GenericPart getPart() {
		return part;
	}
	public void setPart(GenericPart part) {
		this.part = part;
	}
	public void setPartCode(String partCode) {
		GenericPart gpart = new GenericPart();
		gpart.setPartCode(partCode);
		this.part=gpart;
	}
	
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
}
