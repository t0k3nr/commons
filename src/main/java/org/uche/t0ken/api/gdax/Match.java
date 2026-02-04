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
public class Match extends Data implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -993126163150154315L;
	/*
	 * 
	{"type":"match",
	"trade_id":37228582,
	"maker_order_id":"0dd0ac6e-2586-481a-9cb9-cb61b04aad59",
	"taker_order_id":"4a7a0b11-b533-482e-89ba-5fffa4ccba04",
	"side":"buy",
	"size":"0.26460000",
	"price":"10073.00000000",
	"product_id":"BTC-USD",
	"sequence":5150417573,
	"time":"2018-02-15T21:14:24.259000Z"}
}	 */
	Long trade_id;
	String maker_order_id;
	String taker_order_id;
	String maker_user_id;
	String taker_user_id;
	private Side side;
	private String order_id;
	private BigDecimal size;
	private BigDecimal price;
	private ProductId product_id;
	private Long sequence;
	private String profile_id;
	private BigDecimal fees;
	
	
	@JsonSerialize(using = InstantSerializer.class)
	@JsonDeserialize(using = InstantDeserializer.class)
	private Instant time;
	
	
	public final Long getTrade_id() {
		return trade_id;
	}
	public final void setTrade_id(Long trade_id) {
		this.trade_id = trade_id;
	}
	public final String getMaker_order_id() {
		return maker_order_id;
	}
	public final void setMaker_order_id(String maker_order_id) {
		this.maker_order_id = maker_order_id;
	}
	public final String getTaker_order_id() {
		return taker_order_id;
	}
	public final void setTaker_order_id(String taker_order_id) {
		this.taker_order_id = taker_order_id;
	}
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
	public final BigDecimal getSize() {
		return size;
	}
	public final void setSize(BigDecimal size) {
		this.size = size;
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
	public String getMaker_user_id() {
		return maker_user_id;
	}
	public void setMaker_user_id(String maker_user_id) {
		this.maker_user_id = maker_user_id;
	}
	public String getTaker_user_id() {
		return taker_user_id;
	}
	public void setTaker_user_id(String taker_user_id) {
		this.taker_user_id = taker_user_id;
	}
	public BigDecimal getFees() {
		return fees;
	}
	public void setFees(BigDecimal fees) {
		this.fees = fees;
	}
	
	
}
