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
public class Done extends Data  implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*
	 * 
	{"type":"done",
	"side":"buy",
	"order_id":"b6e3847a-33cd-40e0-bdbf-53f459fe6bf9",
	"reason":"canceled",
	"product_id":"BTC-USD",
	"price":"10062.16000000",
	"remaining_size":"0.50000000",
	"sequence":5150416093,"time":"2018-02-15T21:14:06.053000Z"}
}	 */
	private Side side;
	private String order_id;
	private Reason reason;
	private BigDecimal remaining_size;
	private BigDecimal executed_size;
	
	private BigDecimal price;
	private ProductId product_id;
	private Long sequence;
	private String profile_id;
	private String user_id;
	
	@JsonSerialize(using = InstantSerializer.class)
	@JsonDeserialize(using = InstantDeserializer.class)
	private Instant time;
	public final Side getSide() {
		return side;
	}
	public final void setSide(Side side) {
		this.side = side;
	}
	public final String getOrder_id() {
		return order_id;
	}
	public final void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public final Reason getReason() {
		return reason;
	}
	public final void setReason(Reason reason) {
		this.reason = reason;
	}
	public final BigDecimal getRemaining_size() {
		return remaining_size;
	}
	public final void setRemaining_size(BigDecimal remaining_size) {
		this.remaining_size = remaining_size;
	}
	public final BigDecimal getPrice() {
		return price;
	}
	public final void setPrice(BigDecimal price) {
		this.price = price;
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
	public String getProfile_id() {
		return profile_id;
	}
	public void setProfile_id(String profile_id) {
		this.profile_id = profile_id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public BigDecimal getExecuted_size() {
		return executed_size;
	}
	public void setExecuted_size(BigDecimal executed_size) {
		this.executed_size = executed_size;
	}
	
}
