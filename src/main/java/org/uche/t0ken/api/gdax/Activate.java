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
public class Activate extends Data implements Serializable {

	/**
	 * {
  -"type": "activate",
  -"product_id": "test-product",
  "timestamp": "1483736448.299000",
  -"user_id": "12",
  --"profile_id": "30000727-d308-cf50-7b1c-c06deb1934fc",
  -"order_id": "7b52009b-64fd-0a2a-49e6-d8a939753077",
  "stop_type": "entry",
  "side": "buy",
  "stop_price": "80",
  "size": "2",
  "funds": "50",
  "private": true
}
	 */
	private static final long serialVersionUID = 1L;
	
	@JsonSerialize(using = InstantSerializer.class)
	@JsonDeserialize(using = InstantDeserializer.class)
	private Instant time;
	
	private String order_id;
	private ProductId product_id;
	private String user_id;
	private String profile_id;
	private StopType stop_type;
	private Side side;
	private String stop_price;
	private BigDecimal size;
	private BigDecimal funds;
	private String timestamp;
	
	public Instant getTime() {
		return time;
	}
	public void setTime(Instant time) {
		this.time = time;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public ProductId getProduct_id() {
		return product_id;
	}
	public void setProduct_id(ProductId product_id) {
		this.product_id = product_id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getProfile_id() {
		return profile_id;
	}
	public void setProfile_id(String profile_id) {
		this.profile_id = profile_id;
	}
	public StopType getStop_type() {
		return stop_type;
	}
	public void setStop_type(StopType stop_type) {
		this.stop_type = stop_type;
	}
	public Side getSide() {
		return side;
	}
	public void setSide(Side side) {
		this.side = side;
	}
	public String getStop_price() {
		return stop_price;
	}
	public void setStop_price(String stop_price) {
		this.stop_price = stop_price;
	}
	public BigDecimal getSize() {
		return size;
	}
	public void setSize(BigDecimal size) {
		this.size = size;
	}
	public BigDecimal getFunds() {
		return funds;
	}
	public void setFunds(BigDecimal funds) {
		this.funds = funds;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
	
}
