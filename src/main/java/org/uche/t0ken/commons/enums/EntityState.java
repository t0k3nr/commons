package org.uche.t0ken.commons.enums;

import java.io.Serializable;

public enum EntityState implements Serializable {

	DISABLED(0), ENABLED(1), DELETED(2);

	private EntityState(Integer index){
		this.index = index;
	}

	private Integer index;

	public Integer getIndex(){
		return this.index;
	}

	public static EntityState getEnum(Integer index){
		if (index == null)
	return null;

		switch(index){
			case 0: return DISABLED;
			case 1: return ENABLED;
			case 2: return DELETED;
			default: return null;
		}
	}

}