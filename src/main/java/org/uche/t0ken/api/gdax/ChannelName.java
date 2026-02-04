package org.uche.t0ken.api.gdax;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ChannelName implements Serializable {
	TICKER("ticker"),
	HEARTBEAT("heartbeat"),
	LEVEL2("level2"),
	MATCHES("matches"),
	FULL("full"),
	USER("user");
	
	
	private ChannelName(String index){
		this.index = index;
	}

	private String index;

	public String getIndex(){
		return this.index;
	}

	public static ChannelName getEnum(String index){
		if (index == null)
	return null;

		if (index.equals("ticker")) return TICKER;
		else if (index.equals("heartbeat")) return HEARTBEAT;
		else if (index.equals("level2")) return LEVEL2;
		else if (index.equals("matches")) return MATCHES;
		else if (index.equals("full")) return FULL;
		else if (index.equals("user")) return USER;
		else return null;
		
	}
	
	@JsonValue 
	public String getValue() {
		return index;
	}
}
