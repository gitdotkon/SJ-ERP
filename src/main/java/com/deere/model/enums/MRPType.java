package com.deere.model.enums;

public enum MRPType {
	
	self(0, "自制"),outsource(1, "外协"),parchase(2, "外购"),standard(3, "标准件");
	
	
	private MRPType(int index, String type) {
		this.index = index;
		this.type = type;
	}
	private String type;
	private int index;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	
	public static String getMRPType(int index){
		for(MRPType type:MRPType.values()){
			if(type.getIndex()==index)
				return type.getType();
		}
		return null;
	}
	
	
}
