package org.uche.t0ken.api.gdax.c;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

import org.uche.t0ken.api.gdax.InstantDeserializer;
import org.uche.t0ken.api.gdax.InstantSerializer;
import org.uche.t0ken.api.gdax.OrderStatus;
import org.uche.t0ken.api.gdax.OrderType;
import org.uche.t0ken.api.gdax.ProductId;
import org.uche.t0ken.api.gdax.Reason;
import org.uche.t0ken.api.gdax.Side;
import org.uche.t0ken.api.gdax.Stp;
import org.uche.t0ken.api.gdax.TimeInForce;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;


@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderResponse  implements Serializable {

	
	private static final long serialVersionUID = 1L;
	private String id;
	
	private BigDecimal price;
	private BigDecimal size;
	private ProductId product_id;
	private Side side;
	private Stp stp;
	private OrderType type;
	private TimeInForce time_in_force;
	private Boolean post_only;
	@JsonSerialize(using = InstantSerializer.class)
	@JsonDeserialize(using = InstantDeserializer.class)
	private Instant created_at;
	private BigDecimal fill_fees;
	private BigDecimal filled_size;
	private BigDecimal executed_value;
	private OrderStatus status;
	private Boolean settled;
	private Reason done_reason;
	@JsonSerialize(using = InstantSerializer.class)
	@JsonDeserialize(using = InstantDeserializer.class)
	private Instant done_at;
	private BigDecimal funds;
	private BigDecimal specified_funds;
	
	
	public final BigDecimal getPrice() {
		return price;
	}
	public final void setPrice(BigDecimal price) {
		this.price = price;
	}
	public final BigDecimal getSize() {
		return size;
	}
	public final void setSize(BigDecimal size) {
		this.size = size;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public ProductId getProduct_id() {
		return product_id;
	}
	public void setProduct_id(ProductId product_id) {
		this.product_id = product_id;
	}
	public Side getSide() {
		return side;
	}
	public void setSide(Side side) {
		this.side = side;
	}
	public Stp getStp() {
		return stp;
	}
	public void setStp(Stp stp) {
		this.stp = stp;
	}
	public OrderType getType() {
		return type;
	}
	public void setType(OrderType type) {
		this.type = type;
	}
	public TimeInForce getTime_in_force() {
		return time_in_force;
	}
	public void setTime_in_force(TimeInForce time_in_force) {
		this.time_in_force = time_in_force;
	}
	public Boolean getPost_only() {
		return post_only;
	}
	public void setPost_only(Boolean post_only) {
		this.post_only = post_only;
	}
	public Instant getCreated_at() {
		return created_at;
	}
	public void setCreated_at(Instant created_at) {
		this.created_at = created_at;
	}
	public BigDecimal getFill_fees() {
		return fill_fees;
	}
	public void setFill_fees(BigDecimal fill_fees) {
		this.fill_fees = fill_fees;
	}
	public BigDecimal getFilled_size() {
		return filled_size;
	}
	public void setFilled_size(BigDecimal filled_size) {
		this.filled_size = filled_size;
	}
	public BigDecimal getExecuted_value() {
		return executed_value;
	}
	public void setExecuted_value(BigDecimal executed_value) {
		this.executed_value = executed_value;
	}
	public OrderStatus getStatus() {
		return status;
	}
	public void setStatus(OrderStatus status) {
		this.status = status;
	}
	public Boolean getSettled() {
		return settled;
	}
	public void setSettled(Boolean settled) {
		this.settled = settled;
	}
	public Reason getDone_reason() {
		return done_reason;
	}
	public void setDone_reason(Reason done_reason) {
		this.done_reason = done_reason;
	}
	public Instant getDone_at() {
		return done_at;
	}
	public void setDone_at(Instant done_at) {
		this.done_at = done_at;
	}
	public BigDecimal getFunds() {
		return funds;
	}
	public void setFunds(BigDecimal funds) {
		this.funds = funds;
	}
	public BigDecimal getSpecified_funds() {
		return specified_funds;
	}
	public void setSpecified_funds(BigDecimal specified_funds) {
		this.specified_funds = specified_funds;
	}
	
	
}
