package com.deere.model.enums;

public enum FactoryEnum {

	JDNW("JDNW", "宁波工厂"), JDTAW("JDTAW", "天津工厂"), JDHBW("JDHBW", "哈尔滨工厂"), JDJW("JDJW", "佳木斯工厂"), PEXT("PEXT", "农具");

	private String index;
	private String name;

	private FactoryEnum(String index, String name) {
		this.index = index;
		this.name = name;
	}

	/**
	 * @Title: getName
	 * @Description: Get name through index
	 * @param index
	 * @return String
	 * @throws
	 */
	public static String getName(String index) {
		for (FactoryEnum factory : FactoryEnum.values()) {
			if (factory.getIndex().equals(index)) {
				return factory.getName();
			}
		}
		return null;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the index
	 */
	public String getIndex() {
		return index;
	}

	/**
	 * @param index the index to set
	 */
	public void setIndex(String index) {
		this.index = index;
	}

}
