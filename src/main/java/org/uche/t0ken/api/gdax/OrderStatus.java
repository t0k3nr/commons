package org.uche.t0ken.api.gdax;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonValue;

public enum OrderStatus implements Serializable {
	// [open, pending, active
	OPEN("open"),
	PENDING("pending"),
	ACTIVE("active"),
	DONE("done"),
	RECEIVED("received");
	
	private OrderStatus(String index){
		this.index = index;
	}

	private String index;

	public String getIndex(){
		return this.index;
	}

	public static OrderStatus getEnum(String index){
		if (index == null)
	return null;

		if (index.equals("open")) return OPEN;
		else if (index.equals("pending")) return PENDING;
		else if (index.equals("active")) return ACTIVE;
		else if (index.equals("received")) return RECEIVED;
		else if (index.equals("done")) return DONE;
		else return null;
		
	}
	
	@JsonValue 
	public String getValue() {
		return index;
	}
}
