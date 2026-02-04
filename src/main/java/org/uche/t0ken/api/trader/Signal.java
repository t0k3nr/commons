package org.uche.t0ken.api.trader;

import java.time.Instant;

import org.uche.t0ken.api.gdax.Side;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class Signal {

	
	private String product;
	private Side side;
	private Boolean take;
	private Instant ts;
	
	public Side getSide() {
		return side;
	}
	public void setSide(Side side) {
		this.side = side;
	}
	public Boolean getTake() {
		return take;
	}
	public void setTake(Boolean take) {
		this.take = take;
	}
	
	public Instant getTs() {
		return ts;
	}
	public void setTs(Instant ts) {
		this.ts = ts;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	
	
	
	
}
