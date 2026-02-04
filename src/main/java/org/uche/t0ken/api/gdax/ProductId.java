package org.uche.t0ken.api.gdax;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ProductId implements Serializable {
	BTC_USD("BTC-USD",0),
	BTC_EUR("BTC-EUR",1),
	BTC_GBP("BTC-GBP",2),
	BCH_USD("BCH-USD",3),
	BCH_BTC("BCH-BTC",4),
	BCH_EUR("BCH-EUR",5),
	ETH_USD("ETH-USD",6),
	ETH_BTC("ETH-BTC",7),
	ETH_EUR("ETH-EUR",8),
	LTC_USD("LTC-USD",9),
	LTC_BTC("LTC-BTC",10),
	LTC_EUR("LTC-EUR",11),
	BTC_USDT("BTC-USDT",12),
	ETH_USDT("ETH-USDT",13),
	SOL_USD("SOL-USD",14),
	SUI_USD("SUI-USD",15),
	SEI_USD("SEI-USD",16),
	HBAR_USD("HBAR-USD",17),
	ATOM_USD("ATOM-USD",18),
	
	;
	
	
	private ProductId(String name, Integer index){
		this.name = name;
		this.index = index;
	}

	private String name;

	public String getName(){
		return this.name;
	}

	private Integer index;
	
	public Integer getIndex() {
		return this.index;
	}
	
	public static ProductId getEnum(String name){
		if (name == null)
	return null;

		if (name.equals("BTC-USD")) return BTC_USD;
		else if (name.equals("BTC-EUR")) return BTC_EUR;
		else if (name.equals("BTC-USDT")) return BTC_USDT;
		else if (name.equals("ETH-USDT")) return ETH_USDT;
		else if (name.equals("SOL-USD")) return SOL_USD;
		else if (name.equals("BTC-GBP")) return BTC_GBP;
		else if (name.equals("BCH-USD")) return BCH_USD;
		else if (name.equals("BCH-BTC")) return BCH_BTC;
		else if (name.equals("BCH-EUR")) return BCH_EUR;
		else if (name.equals("ETH-USD")) return ETH_USD;
		else if (name.equals("ETH-BTC")) return ETH_BTC;
		else if (name.equals("ETH-EUR")) return ETH_EUR;
		else if (name.equals("LTC-USD")) return LTC_USD;
		else if (name.equals("LTC-BTC")) return LTC_BTC;
		else if (name.equals("LTC-EUR")) return LTC_EUR;
		else if (name.equals("SUI-USD")) return SUI_USD;
		else if (name.equals("SEI-USD")) return SEI_USD;
		else if (name.equals("HBAR-USD")) return HBAR_USD;
		else if (name.equals("ATOM-USD")) return ATOM_USD;
		else return null;
		
		
	}
	
	public static ProductId getEnum(Integer index){
		if (index == null)
	return null;

		switch (index) {
		case 0: return BTC_USD;
		case 1: return BTC_EUR;
		case 2:	return BTC_GBP;
		case 3: return BCH_USD;
		case 4: return BCH_BTC;
		case 5: return BCH_EUR;
		case 6: return ETH_USD;
		case 7: return ETH_BTC;
		case 8: return ETH_EUR;
		case 9: return LTC_USD;
		case 10: return LTC_BTC;
		case 11: return LTC_EUR;
		case 12: return BTC_USDT;
		case 13: return ETH_USDT;
		case 14: return SOL_USD;
		case 15: return SUI_USD;
		case 16: return SEI_USD;
		case 17: return HBAR_USD;
		case 18: return ATOM_USD;
		
		
		
		}
		return null;
		
	}
	
	@JsonValue 
	public String getValue() {
		return name;
	}
}
