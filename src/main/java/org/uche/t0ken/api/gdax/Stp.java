package org.uche.t0ken.api.gdax;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonValue;

/*
 * SELF-TRADE PREVENTION
Self-trading is not allowed on GDAX. Two orders from the same user will not be allowed to match with one another. To change the self-trade behavior, specify the stp flag.

Flag	Name
dc	Decrease and Cancel (default)
co	Cancel oldest
cn	Cancel newest
cb	Cancel both
 */
public enum Stp implements Serializable {
	DC("dc",0),
	CO("co",1),
	CN("cn",2),
	CB("cb",3);
	
	private Stp(String name, Integer index){
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
	
	public static Stp getEnum(String name){
		if (name == null)
	return null;
		
		if (name.equals("dc")) return DC;
		else if (name.equals("co")) return CO;
		else if (name.equals("cn")) return CN;
		else if (name.equals("cb")) return CB;
		else return null;
		
	}
	
	public static Stp getEnum(Integer index){
		if (index == null)
	return null;
		
		switch (index) {
			case 0: return DC;
			case 1: return CO;
			case 2: return CN;
			case 3: return CB;
			}
		return null;
	}
	
	@JsonValue 
	public String getValue() {
		return name;
	}
}
