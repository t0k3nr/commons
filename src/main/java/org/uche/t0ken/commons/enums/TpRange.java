package org.uche.t0ken.commons.enums;

import java.io.Serializable;

public enum TpRange implements Serializable {

	SECOND(0),MINUTE(1), HOUR(2), DAY(3), WEEK(4), MONTH(5);

	private TpRange(Integer index){
		this.index = index;
	}

	private Integer index;

	public Integer getIndex(){
		return this.index;
	}

	public static TpRange getEnum(Integer index){
		if (index == null)
	return null;

		switch(index){
			case 0: return SECOND;
			case 1: return MINUTE;
			case 2: return HOUR;
			case 3: return DAY;
			case 4: return WEEK;
			case 5: return MONTH;
			default: return null;
		}
	}

}