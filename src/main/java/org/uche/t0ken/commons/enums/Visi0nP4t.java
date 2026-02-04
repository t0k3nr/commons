package org.uche.t0ken.commons.enums;

import java.io.Serializable;

public enum Visi0nP4t implements Serializable {

	
	
	UNDEF_INV(0), UNDEF_EQ(1), UP(2), DOWN(3), UP_PU(4), DOWN_PU(5);

	private Visi0nP4t(Integer index){
		this.index = index;
	}

	private Integer index;

	public Integer getIndex(){
		return this.index;
	}

	public static Visi0nP4t getEnum(Integer index){
		if (index == null)
	return null;

		switch(index){
		case 0: return UNDEF_INV;
		case 1: return UNDEF_EQ;
		case 2: return UP;
		case 3: return DOWN;
		case 4: return UP_PU;
		case 5: return DOWN_PU;
		
		default: return null;
		}
	}

}