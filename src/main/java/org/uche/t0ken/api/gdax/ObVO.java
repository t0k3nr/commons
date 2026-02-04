package org.uche.t0ken.api.gdax;

import java.io.Serializable;
import java.math.BigDecimal;

import org.uche.t0ken.commons.enums.MarketPlace;

public class ObVO implements Serializable {

	private BigDecimal bid;
	private BigDecimal ask;
	private MarketPlace mp;
	private ProductId productId; 
	
	public BigDecimal getBid() {
		return bid;
	}
	public void setBid(BigDecimal bid) {
		this.bid = bid;
	}
	public BigDecimal getAsk() {
		return ask;
	}
	public void setAsk(BigDecimal ask) {
		this.ask = ask;
	}
	public MarketPlace getMp() {
		return mp;
	}
	public void setMp(MarketPlace mp) {
		this.mp = mp;
	}
	public ProductId getProductId() {
		return productId;
	}
	public void setProductId(ProductId productId) {
		this.productId = productId;
	}
	
}
