package com.deere.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
/**
 * 用来记录计划每天的流水记录
 * @author zhaohongxing
 *
 */
@Entity
@Table(name="ACCOUNT_STATEMENT")
public class AccountStatement extends GenericModel {
	/**
	 * 流水号
	 */
	private int swiftNumber;
	/**
	 *  与之相关的计划单
	 */
	private String planOrder;
	/**
	 * 记录的物料
	 */
	private String item;
	/**
	 * 物料与之相关的CODE
	 */
	private String code;
	/**
	 * 完成的数据
	 */
	private int finishQty;
	/**
	 * 流水发生的日期
	 */
	private Date date = new Date();

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getSwiftNumber() {
		return swiftNumber;
	}
	public void setSwiftNumber(int swiftNumber) {
		this.swiftNumber = swiftNumber;
	}
	public String getPlanOrder() {
		return planOrder;
	}
	public void setPlanOrder(String planOrder) {
		this.planOrder = planOrder;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public int getFinishQty() {
		return finishQty;
	}
	public void setFinishQty(int finishQty) {
		this.finishQty = finishQty;
	}
	@Temporal(TemporalType.TIMESTAMP)  
	@Column(updatable=false)
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

}
