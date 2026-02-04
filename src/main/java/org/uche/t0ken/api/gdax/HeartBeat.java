package org.uche.t0ken.api.gdax;

import java.io.Serializable;
import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class HeartBeat extends Data implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 539408686528892704L;
	/*
	 * 
	 *"{\"type\":\"heartbeat\",
	 *\"last_trade_id\":37127948,
	 *\"product_id\":\"BTC-USD\",
	 *\"sequence\":5140822841,
	 *\"time\":\"2018-02-14T23:21:12.467000Z\"}"

	 */
	private Long last_trade_id;
	private ProductId product_id;
	private Long sequence;
	@JsonSerialize(using = InstantSerializer.class)
	@JsonDeserialize(using = InstantDeserializer.class)
	private Instant time;
	public final Long getLast_trade_id() {
		return last_trade_id;
	}
	public final void setLast_trade_id(Long last_trade_id) {
		this.last_trade_id = last_trade_id;
	}
	public final ProductId getProduct_id() {
		return product_id;
	}
	public final void setProduct_id(ProductId product_id) {
		this.product_id = product_id;
	}
	public final Long getSequence() {
		return sequence;
	}
	public final void setSequence(Long sequence) {
		this.sequence = sequence;
	}
	public final Instant getTime() {
		return time;
	}
	public final void setTime(Instant time) {
		this.time = time;
	}
	
	
}
