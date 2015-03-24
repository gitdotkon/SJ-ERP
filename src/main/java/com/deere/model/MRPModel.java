package com.deere.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.deere.model.enums.MRPType;

@Entity
public class MRPModel extends GenericModel {
	private Integer id;
	private SalesOrder salesOrder;
	private GenericPart part;
	private Integer requiredQty;
	/*private Integer invQty;
	private Integer recommandedQty;
	private Integer actualQty;*/
	private MRPType mprType =MRPType.self;
	private Date deliveryDate;
	private Date dueDate;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@ManyToOne
	@JoinColumn(name="salesOrder")
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
	public Integer getRequiredQty() {
		return requiredQty;
	}
	public void setRequiredQty(Integer requiredQty) {
		this.requiredQty = requiredQty;
	}
	
	public MRPType getMprType() {
		return mprType;
	}
	public void setMprType(MRPType mprType) {
		this.mprType = mprType;
	}
	public Date getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	
	

}
