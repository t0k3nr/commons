package org.uche.t0ken.commons.enums;

import java.io.Serializable;

public enum RessourceLoad implements Serializable {

	LOW(0),MEDIUM(1),HIGH(2);

	private RessourceLoad(Integer index){
		this.index = index;
	}

	private Integer index;

	public Integer getIndex(){
		return this.index;
	}

	public static RessourceLoad getEnum(Integer index){
		if (index == null)
	return null;

		switch(index){
			case 0: return LOW;
			case 1: return MEDIUM;
			case 2: return HIGH;
			default: return null;
		}
	}

}