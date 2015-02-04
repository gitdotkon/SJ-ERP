package com.deere.model.enums;

public enum PartType {
	self("2", "自制"),purchase("1", "外购"),melt("3", "组焊"),combine("4", "组合"),machine("5", "整机"),subMachine("6", "总成");
	
	
	private PartType(String index, String type) {
		this.index = index;
		this.type = type;
	}

	private String index;
	private String type;
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public static String getPartType(String index){
		for(PartType part:PartType.values()){
			if(part.getIndex().equals(index))
				return part.getType();
		}
		return null;
	}
	
}
