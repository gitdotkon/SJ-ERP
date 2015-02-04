package com.deere.model.exdto;

import javax.persistence.Id;

import com.deere.common.ExcelAnnotation;
import com.deere.model.GenericPart;

public class ExBOMTree {
	@ExcelAnnotation(field = "图号")
	private String partCode;
	@ExcelAnnotation(field = "名称")
	private String partName;
	@ExcelAnnotation(field = "数量")
	private Integer dosage;
	@ExcelAnnotation(field = "材料")
	private String material;
	@ExcelAnnotation(field = "备注")
	private String notes;
	@ExcelAnnotation(field = "零件类型")
	private String partType;
	private GenericPart parent;
	private GenericPart child;
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
	
	public Integer getDosage() {
		return dosage;
	}
	public void setDosage(Integer dosage) {
		this.dosage = dosage;
	}
	public String getMaterial() {
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getPartType() {
		return partType;
	}
	public void setPartType(String partType) {
		this.partType = partType;
	}
	public GenericPart getParent() {
		return parent;
	}
	public void setParent(GenericPart parent) {
		this.parent = parent;
	}
	public GenericPart getChild() {
		GenericPart cPart = new GenericPart();
		cPart.setMaterial(material);
		cPart.setPartCode(partCode);
		cPart.setPartName(partName);
		cPart.setPartType(partType);
		if(partType ==null)
			cPart.setPartType("电气");
		else if(partType.equals(""))
			cPart.setPartType("总成");
		
		return cPart;
	}
	public void setChild(GenericPart child) {
		this.child = child;
	}
	
	

}
