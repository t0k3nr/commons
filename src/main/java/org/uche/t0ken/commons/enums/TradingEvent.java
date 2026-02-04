package org.uche.t0ken.commons.enums;

import java.io.Serializable;

public enum TradingEvent implements Serializable {

	
	ORDER_ATTEMPT(0),
	MATCH(1),
	RESUME(2),
	PAUSE(3),
	ORDER_BOOK(4),
	VALUE(5),
	CANCEL_ATTEMPT(6),
	UPDATE_BALANCE(7),
	PROFIT(8),
	LOSS(9),
	FEE_LOSS(10),
	POSITION(11),
	ORDER_ERROR(12),
	CANCEL_ERROR(13),
	ORDER_OK(14),
	CANCEL_OK(15),
	LOCK_FUNDS(16),
	RELEASE_FUNDS(17),
	RECEIVED(18),
	ORDER_TIMEOUT(19),
	CANCEL_TIMEOUT(20),
	ORDER_DONE_FILLED(21),
	ORDER_DONE_CANCELED(22),
	ORDER_OPEN(23),
	FILL(24),
	ORDER_DONE_CANCELED_MISSING_FILLS(25),
	ORDER_DONE_FILLED_MISSING_FILLS(26),
	ORDER_DONE_ERROR_FUNDS(27),
	CANCEL_ERROR_ALREADY_DONE(28),
	REFUND_FEES(29)
	;

	private TradingEvent(Integer index){
		this.index = index;
	}

	private Integer index;

	public Integer getIndex(){
		return this.index;
	}

	public static TradingEvent getEnum(Integer index){
		if (index == null)
	return null;

		switch(index){
		case 0: return ORDER_ATTEMPT;
		case 1: return MATCH;
		case 2: return RESUME;
		case 3: return PAUSE;
		case 4: return ORDER_BOOK;
		case 5: return VALUE;
		case 6: return CANCEL_ATTEMPT;
		case 7: return UPDATE_BALANCE;
		case 8: return PROFIT;
		case 9: return LOSS;
		case 10: return FEE_LOSS;
		case 11: return POSITION;
		case 12: return ORDER_ERROR;
		case 13: return CANCEL_ERROR;
		case 14: return ORDER_OK;
		case 15: return CANCEL_OK;
		case 16: return LOCK_FUNDS;
		case 17: return RELEASE_FUNDS;
		case 18: return RECEIVED;
		case 19: return ORDER_TIMEOUT;
		case 20: return CANCEL_TIMEOUT;
		case 21: return ORDER_DONE_FILLED;
		case 22: return ORDER_DONE_CANCELED;
		case 23: return ORDER_OPEN;
		case 24: return FILL;
		case 25: return ORDER_DONE_CANCELED_MISSING_FILLS;
		case 26: return ORDER_DONE_FILLED_MISSING_FILLS;
		case 27: return ORDER_DONE_ERROR_FUNDS;
		case 28: return CANCEL_ERROR_ALREADY_DONE;
		case 29: return REFUND_FEES;
		default: return null;
		}
	}

}