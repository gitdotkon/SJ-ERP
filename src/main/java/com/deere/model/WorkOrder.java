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
	/*
	 * 流水号,主键
	 */
	private int batchNum;
	/*
	 *  与之相关的生产计划单
	 */
	private String planOrder;
	/*
	 * 此工作单完成的数量
	 */
	private int quantity;
	/*
	 * 此工作单合格的数量
	 */
	private int checkOutQty;
	/*
	 * 检验员
	 */
	private String qualityInspector;
	/*
	 * 工作单的状态。
	 * 此字段用来跟踪工作单的状态，用状态来控制工作单在不同环节的流转，实现工作流的效果。
	 */
	private String status;
	/*
	 * 流水发生的日期
	 */
	private Date workOrderDate = new Date();

	public String getWorkerName() {
		return workerName;
	}
	public void setWorkerName(String workerName) {
		this.workerName = workerName;
	}
	public String getPartCode() {
		return partCode;
	}
	public void setPartCode(String partCode) {
		this.partCode = partCode;
	}
	public String getPartName() {
		return partName;
	}
	public void setPartName(String partName) {
		this.partName = partName;
	}
	
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public int getCheckOutQty() {
		return checkOutQty;
	}
	public void setCheckOutQty(int checkOutQty) {
		this.checkOutQty = checkOutQty;
	}
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
	@Temporal(TemporalType.TIMESTAMP)  
	@Column(updatable=false)
	public Date getWorkOrderDate() {
		return workOrderDate;
	}
	public void setWorkOrderDate(Date workOrderDate) {
		this.workOrderDate = workOrderDate;
	}
	public String getQualityInspector() {
		return qualityInspector;
	}
	public void setQualityInspector(String qualityInspector) {
		this.qualityInspector = qualityInspector;
	}
	@Column(name="STATUS" ,nullable=true,columnDefinition="VARCHAR(15) default 'NEW'")
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}
