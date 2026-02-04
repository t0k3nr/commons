package org.uche.t0ken.api.gdax;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Reason implements Serializable {
	CANCELED("canceled"),
	FILLED("filled");
	
	private Reason(String index){
		this.index = index;
	}

	private String index;

	public String getIndex(){
		return this.index;
	}

	public static Reason getEnum(String index){
		if (index == null)
	return null;

		if (index.equals("canceled")) return CANCELED;
		else if (index.equals("filled")) return FILLED;
		else return null;
		
	}
	
	@JsonValue 
	public String getValue() {
		return index;
	}
}
