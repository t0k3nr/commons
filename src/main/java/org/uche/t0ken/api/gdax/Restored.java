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
public class Restored extends Data implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6502575982834698656L;
	
	private String order_id;
	private OrderType order_type;
	private BigDecimal size;
	private BigDecimal price;
	private Side side;
	private String client_oid;
	private ProductId product_id;
	private Long sequence;
	private BigDecimal funds;
	private String user_id;
	private String profile_id;
	private Boolean post_only;
	private Boolean feeInQuote;
	private TimeInForce timeInForce;
	private String productName;
	
	@JsonSerialize(using = InstantSerializer.class)
	@JsonDeserialize(using = InstantDeserializer.class)
	private Instant time;
	
	public final String getOrder_id() {
		return order_id;
	}
	public final void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public final OrderType getOrder_type() {
		return order_type;
	}
	public final void setOrder_type(OrderType order_type) {
		this.order_type = order_type;
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
	public final Side getSide() {
		return side;
	}
	public final void setSide(Side side) {
		this.side = side;
	}
	public final String getClient_oid() {
		return client_oid;
	}
	public final void setClient_oid(String client_oid) {
		this.client_oid = client_oid;
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
	public final BigDecimal getFunds() {
		return funds;
	}
	public final void setFunds(BigDecimal funds) {
		this.funds = funds;
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
	public Boolean getPost_only() {
		return post_only;
	}
	public void setPost_only(Boolean post_only) {
		this.post_only = post_only;
	}
	public Boolean getFeeInQuote() {
		return feeInQuote;
	}
	public void setFeeInQuote(Boolean feeInQuote) {
		this.feeInQuote = feeInQuote;
	}
	public TimeInForce getTimeInForce() {
		return timeInForce;
	}
	public void setTimeInForce(TimeInForce timeInForce) {
		this.timeInForce = timeInForce;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	
}
