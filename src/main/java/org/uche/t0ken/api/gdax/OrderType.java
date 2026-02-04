package org.uche.t0ken.api.gdax;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonValue;

public enum OrderType implements Serializable {
	LIMIT("limit",0),
	STOP("stop",1),
	MARKET("market",2);
	
	private OrderType(String name, Integer index){
		this.name = name;
		this.index = index;
	}

	private Integer index;

	public Integer getIndex(){
		return this.index;
	}

	private String name;
	
	public String getName() {
		return this.name;
	}
	

	public static OrderType getEnum(String name){
		if (name == null) return null;

		if (name.equals("limit")) return LIMIT;
		else if (name.equals("stop")) return STOP;
		else if (name.equals("market")) return MARKET;
		else return null;
		
	}
	
	public static OrderType getEnum(Integer index){
		if (index == null)
	return null;
		
		switch (index) {
			case 0: return LIMIT;
			case 1: return STOP;
			case 2: return MARKET;
			}
		return null;
	}
	
	
	
	@JsonValue 
	public String getValue() {
		return name;
	}
}
