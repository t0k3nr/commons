package org.uche.t0ken.commons.enums;


public enum Action {

	BUY(0),
	SELL(1),
	WAIT(2);
	
	
	
	private Action(Integer index){
		this.index = index;
	}

	private Integer index;

	public Integer getIndex(){
		return this.index;
	}

	public static Action getEnum(Integer index){
		if (index == null)
	return null;

		switch(index){
			case 0: return BUY;
			case 1: return SELL;
			case 2: return WAIT;
			default: return null;
		}
	}
	
	
	
}
