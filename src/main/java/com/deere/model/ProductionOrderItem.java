package com.deere.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
public class ProductionOrderItem extends GenericModel {
	private Integer itemId;
	private ProductionOrder proOrder;
	private GenericPart part;
	private Integer plannedQty;
	private Integer finishedQty=0;
	private Boolean self;
	private Boolean completed=false;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "productionOrder")
	public ProductionOrder getProOrder() {
		return proOrder;
	}
	public void setProOrder(ProductionOrder proOrder) {
		this.proOrder = proOrder;
	}
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "partCode")
	public GenericPart getPart() {
		return part;
	}
	public void setPart(GenericPart part) {
		this.part = part;
	}
	public Integer getPlannedQty() {
		return plannedQty;
	}
	public void setPlannedQty(Integer plannedQty) {
		this.plannedQty = plannedQty;
	}
	public Integer getFinishedQty() {
		return finishedQty;
	}
	public void setFinishedQty(Integer finishedQty) {
		this.finishedQty = finishedQty;
	}
	
	public Boolean getSelf() {
		return self;
	}
	public void setSelf(Boolean self) {
		this.self = self;
	}
	public Boolean getCompleted() {
		return completed;
	}
	public void setCompleted(Boolean completed) {
		this.completed = completed;
	}
	
	
	
}
