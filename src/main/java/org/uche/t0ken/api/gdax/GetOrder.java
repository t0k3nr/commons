package org.uche.t0ken.api.gdax;

import java.io.Serializable;

import org.uche.t0ken.commons.enums.MarketPlace;

public class GetOrder implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String mpUUID;
	private String myUUID;
	private MarketPlace mp;
	private Long traderId;
	private ProductId productId;
	
	
	public MarketPlace getMp() {
		return mp;
	}
	public void setMp(MarketPlace mp) {
		this.mp = mp;
	}
	
	public Long getTraderId() {
		return traderId;
	}
	public void setTraderId(Long traderId) {
		this.traderId = traderId;
	}
	public ProductId getProductId() {
		return productId;
	}
	public void setProductId(ProductId productId) {
		this.productId = productId;
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
	
	

}
