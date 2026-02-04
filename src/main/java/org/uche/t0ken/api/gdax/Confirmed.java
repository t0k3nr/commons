package org.uche.t0ken.api.gdax;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class Confirmed extends Data implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6277387998657546622L;
	/*
	 * 
	"type":"open",
	"side":"buy",
	"price":"10071.36000000",
	"order_id":"02e87bd6-da2f-4632-a06c-1b9dfbd0114b",
	"remaining_size":"0.01000000","product_id":"BTC-USD","sequence":5150416095,
	"time":"2018-02-15T21:14:06.061000Z"}
}	 */
	private String order_id;
	private String client_oid;
	private Boolean success;
	
	
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getClient_oid() {
		return client_oid;
	}
	public void setClient_oid(String client_oid) {
		this.client_oid = client_oid;
	}
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	
	
	
}
