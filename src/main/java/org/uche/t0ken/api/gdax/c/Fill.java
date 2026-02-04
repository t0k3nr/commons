package org.uche.t0ken.api.gdax.c;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

import org.uche.t0ken.api.gdax.InstantDeserializer;
import org.uche.t0ken.api.gdax.InstantSerializer;
import org.uche.t0ken.api.gdax.ProductId;
import org.uche.t0ken.api.gdax.Side;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class Fill implements Serializable {
    
	private static final long serialVersionUID = 8524373802303830641L;
	private Long trade_id;
    private ProductId product_id;
    private BigDecimal size;
    private String order_id;
    @JsonSerialize(using = InstantSerializer.class)
	@JsonDeserialize(using = InstantDeserializer.class)
	private Instant created_at;
    private String liquidity;
    private BigDecimal fee;
    private BigDecimal price;
    private Boolean settled;
    private Side side;
    
	public Long getTrade_id() {
		return trade_id;
	}
	public void setTrade_id(Long trade_id) {
		this.trade_id = trade_id;
	}
	public ProductId getProduct_id() {
		return product_id;
	}
	public void setProduct_id(ProductId product_id) {
		this.product_id = product_id;
	}
	public BigDecimal getSize() {
		return size;
	}
	public void setSize(BigDecimal size) {
		this.size = size;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public Instant getCreated_at() {
		return created_at;
	}
	public void setCreated_at(Instant created_at) {
		this.created_at = created_at;
	}
	
	public BigDecimal getFee() {
		return fee;
	}
	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}
	public Boolean getSettled() {
		return settled;
	}
	public void setSettled(Boolean settled) {
		this.settled = settled;
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
	public String getLiquidity() {
		return liquidity;
	}
	public void setLiquidity(String liquidity) {
		this.liquidity = liquidity;
	}

}
