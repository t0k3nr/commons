package org.uche.t0ken.api.gdax.c;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class MarketPlaceFees implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8320586546879270579L;
	private BigDecimal makerFee;
	private BigDecimal takerFee;
	public BigDecimal getMakerFee() {
		return makerFee;
	}
	public void setMakerFee(BigDecimal makerFee) {
		this.makerFee = makerFee;
	}
	public BigDecimal getTakerFee() {
		return takerFee;
	}
	public void setTakerFee(BigDecimal takerFee) {
		this.takerFee = takerFee;
	}

    
}
