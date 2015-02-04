package com.deere.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Stock extends GenericModel {
	private Integer id;
	private GenericPart part;
	private ProductionOrderItem production;
	private Date warehousingDate;
	private Boolean warehousingType;
	private Integer quantity;
	private Integer invQty;
	private String address; 
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "partCode")
	public GenericPart getPart() {
		return part;
	}
	public void setPart(GenericPart part) {
		this.part = part;
	}
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "productionitemId")
	public ProductionOrderItem getProduction() {
		return production;
	}
	public void setProduction(ProductionOrderItem production) {
		this.production = production;
	}
	public Date getWarehousingDate() {
		return warehousingDate;
	}
	public void setWarehousingDate(Date warehousingDate) {
		this.warehousingDate = warehousingDate;
	}
	public Boolean getWarehousingType() {
		return warehousingType;
	}
	public void setWarehousingType(Boolean warehousingType) {
		this.warehousingType = warehousingType;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Integer getInvQty() {
		return invQty;
	}
	public void setInvQty(Integer invQty) {
		this.invQty = invQty;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	
	
	
}
