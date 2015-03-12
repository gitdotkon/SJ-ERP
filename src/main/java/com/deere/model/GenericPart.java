package com.deere.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "parts")
public class GenericPart extends GenericModel {
	private String partCode; //零件图号
	private String partName;//零件名称
	private String material;//材料
	private String partType;//零件类型
	private Integer weight;//重量
	private Integer time;
	private String machineType;
	
	
	@Id
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
	
	public String getMaterial() {
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	public String getPartType() {
		return partType;
	}
	public void setPartType(String partType) {
		this.partType = partType;
	}
	public Integer getWeight() {
		return weight;
	}
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	public Integer getTime() {
		return time;
	}
	public void setTime(Integer time) {
		this.time = time;
	}
	public String getMachineType() {
		return machineType;
	}
	public void setMachineType(String machineType) {
		this.machineType = machineType;
	}
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		int r=0;
		char[] codeChar =this.partCode.toCharArray();
		for (char c : codeChar) {
			r+=c;
		}
		return r;
	}
	@Override
	public String toString(){
		return this.getPartCode()+this.getPartName();
	}
	
}
