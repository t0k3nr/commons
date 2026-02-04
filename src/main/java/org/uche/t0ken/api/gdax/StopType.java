package org.uche.t0ken.api.gdax;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonValue;

public enum StopType implements Serializable {
	ENTRY("entry",0),
	LOSS("loss",1),
	;
	
	private StopType(String name, Integer index){
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
	

	public static StopType getEnum(String name){
		if (name == null) return null;

		if (name.equals("entry")) return ENTRY;
		else if (name.equals("loss")) return LOSS;
		else return null;
		
	}
	
	public static StopType getEnum(Integer index){
		if (index == null)
	return null;
		
		switch (index) {
			case 0: return ENTRY;
			case 1: return LOSS;
			}
		return null;
	}
	
	
	
	@JsonValue 
	public String getValue() {
		return name;
	}
}
