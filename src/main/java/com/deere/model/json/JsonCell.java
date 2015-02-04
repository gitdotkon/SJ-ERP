package com.deere.model.json;

import java.util.ArrayList;
import java.util.List;

public class JsonCell {
	private String name;
	private Integer level;
	private Integer parentId;
	private Boolean isLeaf;
	private Boolean isExpended;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public Boolean getIsLeaf() {
		return isLeaf;
	}
	public void setIsLeaf(Boolean isLeaf) {
		this.isLeaf = isLeaf;
	}
	public Boolean getIsExpended() {
		return isExpended;
	}
	public void setIsExpended(Boolean isExpended) {
		this.isExpended = isExpended;
	}
	
	public Object[] toList(){
		List<Object> r = new ArrayList<Object>();
		r.add(this.getName());
		r.add(this.getLevel());
		r.add(this.getParentId());
		r.add(this.getIsLeaf());
		r.add(this.isExpended);
		

		return r.toArray();
	}
}
