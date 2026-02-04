package org.uche.t0ken.api.gdax;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

import org.uche.t0ken.commons.enums.MarketPlace;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;



public class MpMatch  implements Serializable {

	private static final long serialVersionUID = 1L;
	private MarketPlace mp;
	private ProductId productId;
	private BigDecimal size;
	private BigDecimal price;
	private Side side;
	@JsonSerialize(using = InstantSerializer.class)
	@JsonDeserialize(using = InstantDeserializer.class)
	private Instant ts;
	private Boolean taker;
	private String mpUUID;
	private String myUUID;
	private Long traderId;
	private Long mpTradeId;
	
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
	public BigDecimal getSize() {
		return size;
	}
	public void setSize(BigDecimal size) {
		this.size = size;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public Side getSide() {
		return side;
	}
	public void setSide(Side side) {
		this.side = side;
	}
	public Instant getTs() {
		return ts;
	}
	public void setTs(Instant ts) {
		this.ts = ts;
	}
	public Boolean getTaker() {
		return taker;
	}
	public void setTaker(Boolean taker) {
		this.taker = taker;
	}
	public String getMpUUID() {
		return mpUUID;
	}
	public void setMpUUID(String mpUUID) {
		this.mpUUID = mpUUID;
	}
	public String getMyUUID() {
		return myUUID;
	}
	public void setMyUUID(String myUUID) {
		this.myUUID = myUUID;
	}
	
	public Long getTraderId() {
		return traderId;
	}
	public void setTraderId(Long traderId) {
		this.traderId = traderId;
	}
	public Long getMpTradeId() {
		return mpTradeId;
	}
	public void setMpTradeId(Long mpTradeId) {
		this.mpTradeId = mpTradeId;
	}
	
}
