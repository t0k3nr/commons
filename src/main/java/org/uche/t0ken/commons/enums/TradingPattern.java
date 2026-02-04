package org.uche.t0ken.commons.enums;

public enum TradingPattern {


	UNDEF_INV(0), UNDEF_EQ(1), UP(2), DOWN(3);

	private TradingPattern(Integer index){
		this.index = index;
	}

	private Integer index;

	public Integer getIndex(){
		return this.index;
	}

	public static TradingPattern getEnum(Integer index){
		if (index == null)
	return null;

		switch(index){
		case 0: return UNDEF_INV;
		case 1: return UNDEF_EQ;
		case 2: return UP;
		case 3: return DOWN;
		default: return null;
		}
	}
}
