package com.deere.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class SalesOrder extends GenericModel{
	private String orderNum;
	private Date orderDate;
	private Date deliveryDate;
	private Date dueDate;
	private User sales;
	private Customer customer;
	private float amount;
	private String notes;
	private Boolean planned=false;
	private Set<SalesOrderItem> orderItems = new HashSet<SalesOrderItem>();
	private String orderStatus;
	
	@Id
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	

	public Date getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	@ManyToOne
	@JoinColumn(name="sales")
	public User getSales() {
		return sales;
	}
	public void setSales(User sales) {
		this.sales = sales;
	}
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="customer")
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	@OneToMany(mappedBy="salesOrder",fetch=FetchType.EAGER)
	public Set<SalesOrderItem> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(Set<SalesOrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public float getAmount() {
		return amount;
	}
	
	
	public Boolean getPlanned() {
		return planned;
	}
	public void setPlanned(Boolean planned) {
		this.planned = planned;
	}
	
	
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	/*  
     * 添加订单  
     */    
    public void addOrderItem(SalesOrderItem orderitem) {    
        if (!this.orderItems.contains(orderitem)) {    
            this.orderItems.add(orderitem);    
            orderitem.setSalesOrder(this);    
        }    
    }    
    
    /*  
     * 删除订单  
     */    
    public void removeOrderItem(SalesOrderItem orderitem) {    
        orderitem.setSalesOrder(null);    
        this.orderItems.remove(orderitem);    
    }
    public SalesOrder(){
    	
    }
	public SalesOrder(String orderNum) {
		this.setOrderNum(orderNum);
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	} 
	
    
    
}
