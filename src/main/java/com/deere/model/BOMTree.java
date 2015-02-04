package com.deere.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "BOMTree")
@IdClass(com.deere.model.pk.BOMTreeComposeIdPk.class)
public class BOMTree extends GenericModel {
	private GenericPart parent;//父亲
	private GenericPart child;//孩子
	private Integer dosage;//数量
	private String note;
	@Id
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "parent")
	public GenericPart getParent() {
		return parent;
	}
	public void setParent(GenericPart parent) {
		this.parent = parent;
	}
	@Id
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "child")
	public GenericPart getChild() {
		return child;
	}
	public void setChild(GenericPart child) {
		this.child = child;
	}
	public Integer getDosage() {
		return dosage;
	}
	public void setDosage(Integer dosage) {
		this.dosage = dosage;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
	
	
}
