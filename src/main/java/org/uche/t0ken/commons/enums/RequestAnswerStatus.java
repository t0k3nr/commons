package org.uche.t0ken.commons.enums;

import java.io.Serializable;

public enum RequestAnswerStatus implements Serializable {

	OK(0),RETRY_LATER(1),PERMANENT_ERROR(2), UNDEFINED(3), REMOTE_CALL_NEEDED(4), INVALID(5);

	private RequestAnswerStatus(Integer index){
		this.index = index;
	}

	private Integer index;

	public Integer getIndex(){
		return this.index;
	}

	public static RequestAnswerStatus getEnum(Integer index){
		if (index == null)
	return null;

		switch(index){
			case 0: return OK;
			case 1: return RETRY_LATER;
			case 2: return PERMANENT_ERROR;
			case 3: return UNDEFINED;
			case 4: return REMOTE_CALL_NEEDED;
			case 5: return INVALID;
			default: return null;
		}
	}

}