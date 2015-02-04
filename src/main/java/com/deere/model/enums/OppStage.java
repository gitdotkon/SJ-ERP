package com.deere.model.enums;

public enum OppStage {
	POTENTIAL("潜在客户", 1), DEAL("成交客户", 4), LOST("丢失客户", 3);

	private String name;
	private int index;

	private OppStage(String name, int index) {
		this.name = name;
		this.index = index;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

}
