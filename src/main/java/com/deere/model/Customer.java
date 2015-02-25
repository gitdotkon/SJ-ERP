package com.deere.model;


import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Customer extends GenericModel {
	/**
	 * 
	 */
	private String companyName;
	private String contactPerson;
	private String contactNumber;
	private String address;
	
	@Id
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getContactPerson() {
		return contactPerson;
	}
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Customer (String companyName,
			String contactPerson, String contactNumber, String address) {
		super();
		this.companyName = companyName;
		this.contactPerson = contactPerson;
		this.contactNumber = contactNumber;
		this.address = address;
	}
	public Customer (){}
	
	
}
