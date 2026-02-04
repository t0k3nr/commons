package org.uche.t0ken.commons.enums;

import java.io.Serializable;

public enum TradingStrategy implements Serializable {
	INCREASE_BASE(0), INCREASE_QUOTE(1), INCREASE_BOTH(2);

	private TradingStrategy(Integer index){
		this.index = index;
	}

	private Integer index;

	public Integer getIndex(){
		return this.index;
	}

	public static TradingStrategy getEnum(Integer index){
		if (index == null)
	return null;

		switch(index){
		case 0: return INCREASE_BASE; // BTC
		case 1: return INCREASE_QUOTE; // EUR
		case 2: return INCREASE_BOTH; // BOTH
		default: return null;
		}
	}
}
