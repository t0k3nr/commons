package org.uche.t0ken.commons.enums;

public enum OrderError {
	NO_FUNDS(0), ALREADY_DONE(1), INVALID_PARAMS(2), UNKNOWN_ERROR(3);

	private OrderError(Integer index){
		this.index = index;
	}

	private Integer index;

	public Integer getIndex(){
		return this.index;
	}

	public static OrderError getEnum(Integer index){
		if (index == null)
	return null;

		switch(index){
		case 0: return NO_FUNDS;
		case 1: return ALREADY_DONE;
		case 2: return INVALID_PARAMS;
		case 3: return UNKNOWN_ERROR;
		default: return null;
		}
	}
}
