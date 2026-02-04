package org.uche.t0ken.commons.enums;

public enum OrderActor {
	TAKER(0), MAKER(1);

	private OrderActor(Integer index){
		this.index = index;
	}

	private Integer index;

	public Integer getIndex(){
		return this.index;
	}

	public static OrderActor getEnum(Integer index){
		if (index == null)
	return null;

		switch(index){
		case 0: return TAKER;
		case 1: return MAKER;
		default: return null;
		}
	}
}
