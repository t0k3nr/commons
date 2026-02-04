package org.uche.t0ken.commons.enums;

import java.io.Serializable;

public enum Indicator implements Serializable {

	
	
	EMAC(0),MACDC(1),CMOC(2);

	private Indicator(Integer index){
		this.index = index;
	}

	private Integer index;

	public Integer getIndex(){
		return this.index;
	}

	public static Indicator getEnum(Integer index){
		if (index == null)
	return null;

		switch(index){
		// 	EMAC(0),MACDC(1),CMOC(2);

		case 0: return EMAC;
		case 1: return MACDC;
		case 2: return CMOC;
		default: return null;
		}
	}

}