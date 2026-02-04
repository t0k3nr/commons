package org.uche.t0ken.api.gdax;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Side implements Serializable {
	BUY("buy",0),
	SELL("sell",1);
	
	private Side(String name, Integer index){
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
	
	public static Side getEnum(String name){
		if (name == null)
	return null;
		
		if (name.equals("buy")) return BUY;
		else if (name.equals("sell")) return SELL;
		else return null;
		
	}
	
	public static Side getEnum(Integer index){
		if (index == null)
	return null;
		switch (index) {
		case 0: return BUY;
		case 1: return SELL;
		}
		return null;
		
		
	}
	
	@JsonValue 
	public String getValue() {
		return name;
	}
}
