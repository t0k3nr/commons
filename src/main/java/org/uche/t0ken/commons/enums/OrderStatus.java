package org.uche.t0ken.commons.enums;

public enum OrderStatus {
	PENDING(0), RECEIVED(1), OPEN(2), DONE_FILLED(3), DONE_CANCELED(4), ERROR_API(5),
	NEW(6), CANCELED_WAITING_DONE(7), FILLED_MISSING_FILLS(8), DONE_ERROR_FUNDS(9),
	CANCELED_MISSING_FILLS(10), SETTLED_FILLED(11), SETTLED_CANCELED(12);

	private OrderStatus(Integer index){
		this.index = index;
	}

	private Integer index;

	public Integer getIndex(){
		return this.index;
	}

	public static OrderStatus getEnum(Integer index){
		if (index == null)
	return null;

		switch(index){
		case 0: return PENDING;
		case 1: return RECEIVED;
		case 2: return OPEN;
		case 3: return DONE_FILLED;
		case 4: return DONE_CANCELED;
		case 5: return ERROR_API;
		case 6: return NEW;
		case 7: return CANCELED_WAITING_DONE;
		case 8: return FILLED_MISSING_FILLS;
		case 9: return DONE_ERROR_FUNDS;
		case 10: return CANCELED_MISSING_FILLS;
		case 11: return SETTLED_FILLED;
		case 12: return SETTLED_CANCELED;
		
		default: return null;
		}
	}
	
}
