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
public class Change extends Data implements Serializable {

	/**
	 * "type": "change",
    "time": "2014-11-07T08:19:27.028459Z",
    "sequence": 80,
    "order_id": "ac928c66-ca53-498f-9c13-a110027a60e8",
    "product_id": "BTC-USD",
    "new_size": "5.23512",
    "old_size": "12.234412",
    "price": "400.23",
    "side": "sell"
	 */
	private static final long serialVersionUID = 1L;
	
	@JsonSerialize(using = InstantSerializer.class)
	@JsonDeserialize(using = InstantDeserializer.class)
	private Instant time;
	
	private String order_id;
	private ProductId product_id;
	private BigDecimal old_size;
	private BigDecimal new_size;
	private Side side;
	private BigDecimal price;
	private BigDecimal old_funds;
	private BigDecimal new_funds;
	
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
	public BigDecimal getOld_size() {
		return old_size;
	}
	public void setOld_size(BigDecimal old_size) {
		this.old_size = old_size;
	}
	public BigDecimal getNew_size() {
		return new_size;
	}
	public void setNew_size(BigDecimal new_size) {
		this.new_size = new_size;
	}
	public Side getSide() {
		return side;
	}
	public void setSide(Side side) {
		this.side = side;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public BigDecimal getOld_funds() {
		return old_funds;
	}
	public void setOld_funds(BigDecimal old_funds) {
		this.old_funds = old_funds;
	}
	public BigDecimal getNew_funds() {
		return new_funds;
	}
	public void setNew_funds(BigDecimal new_funds) {
		this.new_funds = new_funds;
	}
	
	
}
