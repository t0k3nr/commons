package org.uche.t0ken.api.gdax;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class Snapshot extends Data implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3077026839085231378L;
	/*
	 * 
	 *{
    "type": "snapshot",
    "product_id": "BTC-EUR",
    "bids": [["1", "2"]],
    "asks": [["2", "3"]]
}

	 */
	private ProductId product_id;
	private List<Order> bids;
	private List<Order> asks;
	
	public final ProductId getProduct_id() {
		return product_id;
	}
	public final void setProduct_id(ProductId product_id) {
		this.product_id = product_id;
	}
	public final List<Order> getBids() {
		return bids;
	}
	public final void setBids(List<Order> bids) {
		this.bids = bids;
	}
	public final List<Order> getAsks() {
		return asks;
	}
	public final void setAsks(List<Order> asks) {
		this.asks = asks;
	}
	
	
	
}
