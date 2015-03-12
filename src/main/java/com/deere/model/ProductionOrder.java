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
public class ProductionOrder extends GenericModel {
	private String orderNum;
	private Date orderDate;
	private User responsible;
	
	public Date getOrderDate() {
		return orderDate;
	}
	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "responsible")
	public User getResponsible() {
		return responsible;
	}
	public void setResponsible(User responsible) {
		this.responsible = responsible;
	}

	
}
