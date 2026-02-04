package org.uche.t0ken.api.gdax.c;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Fees  implements Serializable {

	
	
	private static final long serialVersionUID = 1L;
	private String maker_fee_rate;
	private String taker_fee_rate;
	private String usd_volume;
	
	public String getMaker_fee_rate() {
		return maker_fee_rate;
	}
	public void setMaker_fee_rate(String maker_fee_rate) {
		this.maker_fee_rate = maker_fee_rate;
	}
	public String getTaker_fee_rate() {
		return taker_fee_rate;
	}
	public void setTaker_fee_rate(String taker_fee_rate) {
		this.taker_fee_rate = taker_fee_rate;
	}
	public String getUsd_volume() {
		return usd_volume;
	}
	public void setUsd_volume(String usd_volume) {
		this.usd_volume = usd_volume;
	}
	
	
	
}
