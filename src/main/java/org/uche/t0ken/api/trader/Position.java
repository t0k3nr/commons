package org.uche.t0ken.api.trader;

import java.math.BigDecimal;

import org.uche.t0ken.commons.enums.Action;
import org.uche.t0ken.commons.enums.ActionOrderType;
import org.uche.t0ken.commons.enums.Risk;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class Position {

	
	private BigDecimal minBuy;
	private BigDecimal maxBuy;
	
	private BigDecimal minSell;
	private BigDecimal maxSell;
	
	private ActionOrderType sellActiontype;
	private ActionOrderType buyActiontype;
	
	private Action action;
	
	private Risk risk;
	
	private BigDecimal accountPercentToSpend;
	
	
	public BigDecimal getMinBuy() {
		return minBuy;
	}
	public void setMinBuy(BigDecimal minBuy) {
		this.minBuy = minBuy;
	}
	public BigDecimal getMaxBuy() {
		return maxBuy;
	}
	public void setMaxBuy(BigDecimal maxBuy) {
		this.maxBuy = maxBuy;
	}
	public BigDecimal getMinSell() {
		return minSell;
	}
	public void setMinSell(BigDecimal minSell) {
		this.minSell = minSell;
	}
	public BigDecimal getMaxSell() {
		return maxSell;
	}
	public void setMaxSell(BigDecimal maxSell) {
		this.maxSell = maxSell;
	}
	public Risk getRisk() {
		return risk;
	}
	public void setRisk(Risk risk) {
		this.risk = risk;
	}
	public BigDecimal getAccountPercentToSpend() {
		return accountPercentToSpend;
	}
	public void setAccountPercentToSpend(BigDecimal accountPercentToSpend) {
		this.accountPercentToSpend = accountPercentToSpend;
	}
	public ActionOrderType getSellActiontype() {
		return sellActiontype;
	}
	public void setSellActiontype(ActionOrderType sellActiontype) {
		this.sellActiontype = sellActiontype;
	}
	public ActionOrderType getBuyActiontype() {
		return buyActiontype;
	}
	public void setBuyActiontype(ActionOrderType buyActiontype) {
		this.buyActiontype = buyActiontype;
	}
	public Action getAction() {
		return action;
	}
	public void setAction(Action action) {
		this.action = action;
	}
	
	
	
	
	
}
