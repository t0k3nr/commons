package org.uche.t0ken.api.gdax;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

import org.uche.t0ken.commons.enums.OrderError;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;



@JsonInclude(Include.NON_NULL)
public class MpOrder  implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	private String mpUUID;
	private String myUUID;
	private BigDecimal price;
	private BigDecimal size;
	private ProductId productId;
	private String productName;
	private Side side;
	private Stp stp;
	private OrderType orderType;
	private TimeInForce timeInForce;
	private Boolean postOnly;
	@JsonSerialize(using = InstantSerializer.class)
	@JsonDeserialize(using = InstantDeserializer.class)
	private Instant createdAt;
	@JsonSerialize(using = InstantSerializer.class)
	@JsonDeserialize(using = InstantDeserializer.class)
	private Instant doneAt;
	@JsonSerialize(using = InstantSerializer.class)
	@JsonDeserialize(using = InstantDeserializer.class)
	private Instant builtAt;
	@JsonSerialize(using = InstantSerializer.class)
	@JsonDeserialize(using = InstantDeserializer.class)
	private Instant canceledAt;
	private BigDecimal fillFees;
	private BigDecimal filledSize;
	private BigDecimal remainingSize;
	private BigDecimal executedValue;
	private org.uche.t0ken.commons.enums.OrderStatus status;
	private OrderError error;
	private Long traderId;
	private BigDecimal funds;
	private BigDecimal specifiedFunds;
	private BigDecimal stopPrice;
	
	private boolean mpCancelRequested = false;
	private boolean mpCreateRequested = false;
	private boolean pendingSimulatorActions = false;
	private Boolean feeInQuote;
	
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
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public BigDecimal getSize() {
		return size;
	}
	public void setSize(BigDecimal size) {
		this.size = size;
	}
	public ProductId getProductId() {
		return productId;
	}
	public void setProductId(ProductId productId) {
		this.productId = productId;
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
	
	
	public Instant getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}
	public BigDecimal getFillFees() {
		return fillFees;
	}
	public void setFillFees(BigDecimal fillFees) {
		this.fillFees = fillFees;
	}
	public BigDecimal getFilledSize() {
		return filledSize;
	}
	public void setFilledSize(BigDecimal filledSize) {
		this.filledSize = filledSize;
	}
	public BigDecimal getExecutedValue() {
		return executedValue;
	}
	public void setExecutedValue(BigDecimal executedValue) {
		this.executedValue = executedValue;
	}
	public org.uche.t0ken.commons.enums.OrderStatus getStatus() {
		return status;
	}
	public void setStatus(org.uche.t0ken.commons.enums.OrderStatus status) {
		this.status = status;
	}
	public OrderError getError() {
		return error;
	}
	public void setError(OrderError error) {
		this.error = error;
	}
	public TimeInForce getTimeInForce() {
		return timeInForce;
	}
	public void setTimeInForce(TimeInForce timeInForce) {
		this.timeInForce = timeInForce;
	}
	public Boolean getPostOnly() {
		return postOnly;
	}
	public void setPostOnly(Boolean postOnly) {
		this.postOnly = postOnly;
	}
	
	public Long getTraderId() {
		return traderId;
	}
	public void setTraderId(Long traderId) {
		this.traderId = traderId;
	}
	public BigDecimal getRemainingSize() {
		return remainingSize;
	}
	public void setRemainingSize(BigDecimal remainingSize) {
		this.remainingSize = remainingSize;
	}
	public Instant getDoneAt() {
		return doneAt;
	}
	public void setDoneAt(Instant doneAt) {
		this.doneAt = doneAt;
	}
	public Instant getCanceledAt() {
		return canceledAt;
	}
	public void setCanceledAt(Instant canceledAt) {
		this.canceledAt = canceledAt;
	}
	public BigDecimal getFunds() {
		return funds;
	}
	public void setFunds(BigDecimal funds) {
		this.funds = funds;
	}
	public BigDecimal getStopPrice() {
		return stopPrice;
	}
	public void setStopPrice(BigDecimal stopPrice) {
		this.stopPrice = stopPrice;
	}
	public OrderType getOrderType() {
		return orderType;
	}
	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}
	
	
	
	@Override
	public String toString() {
		return (side + " @" + price);
	}
	public BigDecimal getSpecifiedFunds() {
		return specifiedFunds;
	}
	public void setSpecifiedFunds(BigDecimal specifiedFunds) {
		this.specifiedFunds = specifiedFunds;
	}
	
	public boolean isMpCancelRequested() {
		return mpCancelRequested;
	}
	public void setMpCancelRequested(boolean mpCancelRequested) {
		this.mpCancelRequested = mpCancelRequested;
	}
	public boolean isMpCreateRequested() {
		return mpCreateRequested;
	}
	public void setMpCreateRequested(boolean mpCreateRequested) {
		this.mpCreateRequested = mpCreateRequested;
	}
	public boolean isPendingSimulatorActions() {
		return pendingSimulatorActions;
	}
	public void setPendingSimulatorActions(boolean pendingSimulatorActions) {
		this.pendingSimulatorActions = pendingSimulatorActions;
	}
	public Instant getBuiltAt() {
		return builtAt;
	}
	public void setBuiltAt(Instant builtAt) {
		this.builtAt = builtAt;
	}
	public Boolean getFeeInQuote() {
		return feeInQuote;
	}
	public void setFeeInQuote(Boolean feeInQuote) {
		this.feeInQuote = feeInQuote;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	
}
