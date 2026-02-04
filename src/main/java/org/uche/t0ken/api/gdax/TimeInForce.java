package org.uche.t0ken.api.gdax;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TimeInForce implements Serializable {
	// time_in_force	[optional] GTC, GTT, IOC, or FOK (default is GTC)
	GTC("GTC"),
	GTT("GTT"),
	IOC("IOC"),
	FOK("FOK");
	
	private TimeInForce(String index){
		this.index = index;
	}

	private String index;

	public String getIndex(){
		return this.index;
	}

	public static TimeInForce getEnum(String index){
		if (index == null)
	return null;

		if (index.equals("GTC")) return GTC;
		else if (index.equals("GTT")) return GTT;
		else if (index.equals("IOC")) return IOC;
		else if (index.equals("FOK")) return FOK;
		else return null;
		
	}
	
	@JsonValue 
	public String getValue() {
		return index;
	}
}
