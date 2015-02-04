package com.deere.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Inventory extends GenericModel {

	/**
	 * 
	 */
	private Integer id;
	private GenericPart part;
	private String address;
	private Integer quantity=0;

	
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
	
	
	public void addQuantity(Integer num){
		this.quantity=this.quantity+num;
	}
}
