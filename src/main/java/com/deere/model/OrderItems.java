package com.deere.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class OrderItems {
	private Order order;
	private GenericPart part;
	private Integer quantity;
	private String notes;
	
	@ManyToOne
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	
	@ManyToOne
	public GenericPart getPart() {
		return part;
	}
	public void setPart(GenericPart part) {
		this.part = part;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	
}
