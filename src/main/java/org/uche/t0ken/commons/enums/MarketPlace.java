package org.uche.t0ken.commons.enums;

import java.io.Serializable;

public enum MarketPlace implements Serializable {

	
	
	GDAX(0), BITFINEX(1), FAKE(2), KRAKEN(3), BITSTAMP(4), KUCOIN(5), CRYPTO_COM(6), BINANCE(7);

	private MarketPlace(Integer index){
		this.index = index;
	}

	private Integer index;

	public Integer getIndex(){
		return this.index;
	}

	public static MarketPlace getEnum(Integer index){
		if (index == null)
	return null;

		switch(index){
		case 0: return GDAX;
		case 1: return BITFINEX;
		case 2: return FAKE;
		case 3: return KRAKEN;
		case 4: return BITSTAMP;
		case 5: return KUCOIN;
		case 6: return CRYPTO_COM;
		case 7: return BINANCE;
		default: return null;
		}
	}

}