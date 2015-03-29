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
@Table(name="WORK_ORDER")
public class WorkOrder extends GenericModel {
	/*
	 * 姓名
	 */
	private String workerName;
	/*
	 * 零件图号
	 */
	private String partCode;
	/*
	 * 零件名称
	 */
	private String partName;
	/**
	 * 流水号
	 */
	private int batchNum;
	/**
	 *  与之相关的计划单
	 */
	private String planOrder;
	/**
	 * 完成的数量
	 */
	private int finishQty;
	/**
	 * 流水发生的日期
	 */
	private Date date = new Date();

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getBatchNum() {
		return batchNum;
	}
	public void setBatchNum(int batchNum) {
		this.batchNum = batchNum;
	}
	public String getPlanOrder() {
		return planOrder;
	}
	public void setPlanOrder(String planOrder) {
		this.planOrder = planOrder;
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
