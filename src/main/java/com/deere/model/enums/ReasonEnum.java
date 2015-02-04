package com.deere.model.enums;

/**
 * @ClassName: ReasonEnum
 * @Description: Define a reason enum
 * @author Gavin Li ligavin@johndeere.com
 * @date Aug 22, 2014 11:32:21 AM
 * 
 */
public enum ReasonEnum {

	REASON1("未购买的原因1:价格太高", 1), REASON2("未购买的原因2:产品性能不能满足要求", 2), REASON3("未购买的原因3:交货时间不能满足要求", 3), REASON4(
			"未购买的原因4:当地政府补贴还未开始", 4), REASON5("未购买的原因5:零售信贷政策不能满足用户要求", 5), REASON6("未购买的原因6:已购买竞争对手产品", 6), REASON7(
			"未购买的原因7:其它", 7);

	private String name;
	private int index;

	private ReasonEnum(String name, int index) {
		this.name = name;
		this.index = index;
	}

	public static String getName(int index) {

		for (ReasonEnum reason : ReasonEnum.values()) {
			if (reason.getIndex() == index) {
				return reason.getName();
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
	public int getIndex() {
		return index;
	}

	/**
	 * @param index the index to set
	 */
	public void setIndex(int index) {
		this.index = index;
	}

}
