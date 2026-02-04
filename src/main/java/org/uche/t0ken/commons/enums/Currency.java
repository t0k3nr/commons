package org.uche.t0ken.commons.enums;

import java.io.Serializable;

public enum Currency implements Serializable {

	
	
	USD(0), EUR(1), GBP(2), BTC(3), ETH(4), BCH(5), LTC(6);

	private Currency(Integer index){
		this.index = index;
	}

	private Integer index;

	public Integer getIndex(){
		return this.index;
	}

	public static Currency getEnum(Integer index){
		if (index == null)
	return null;

		switch(index){
		case 0: return USD;
		case 1: return EUR;
		case 2: return GBP;
		case 3: return BTC;
		case 4: return ETH;
		case 5: return BCH;
		case 6: return LTC;
		default: return null;
		}
	}

}