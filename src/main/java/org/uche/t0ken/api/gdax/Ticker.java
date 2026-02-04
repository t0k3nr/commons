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
public class Ticker extends Data implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4588956819289195128L;
	/*
	 * 
	 *{"type":"ticker","sequence":5140719908,"product_id":"BTC-USD","price":"9409.78000000",
	 * "open_24h":"8529.59000000","volume_24h":"23746.87366416",
	 * "low_24h":"9409.78000000","high_24h":"9444.00000000",
	 * "volume_30d":"885591.82426684","best_bid":"9409.78",
	 * "best_ask":"9409.79","side":"sell","time":"2018-02-14T23:05:26.899000Z","trade_id":37127242,
	 * "last_size":"0.54000000"}

	 */
	private Long sequence;
	private ProductId product_id;
	private BigDecimal price;
	private BigDecimal open_24h;
	private BigDecimal volume_24h;
	private BigDecimal low_24h;
	private BigDecimal high_24h;
	private BigDecimal volume_30d;
	private BigDecimal best_bid;
	private BigDecimal best_ask;
	private Side side;
	//@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX'")
	@JsonSerialize(using = InstantSerializer.class)
	@JsonDeserialize(using = InstantDeserializer.class)
	private Instant time;
	private Long trade_id;
	private BigDecimal last_size;
	public final Long getSequence() {
		return sequence;
	}
	public final void setSequence(Long sequence) {
		this.sequence = sequence;
	}
	public final ProductId getProduct_id() {
		return product_id;
	}
	public final void setProduct_id(ProductId product_id) {
		this.product_id = product_id;
	}
	public final BigDecimal getPrice() {
		return price;
	}
	public final void setPrice(BigDecimal price) {
		this.price = price;
	}
	public final BigDecimal getOpen_24h() {
		return open_24h;
	}
	public final void setOpen_24h(BigDecimal open_24h) {
		this.open_24h = open_24h;
	}
	public final BigDecimal getVolume_24h() {
		return volume_24h;
	}
	public final void setVolume_24h(BigDecimal volume_24h) {
		this.volume_24h = volume_24h;
	}
	public final BigDecimal getLow_24h() {
		return low_24h;
	}
	public final void setLow_24h(BigDecimal low_24h) {
		this.low_24h = low_24h;
	}
	public final BigDecimal getHigh_24h() {
		return high_24h;
	}
	public final void setHigh_24h(BigDecimal high_24h) {
		this.high_24h = high_24h;
	}
	public final BigDecimal getVolume_30d() {
		return volume_30d;
	}
	public final void setVolume_30d(BigDecimal volume_30d) {
		this.volume_30d = volume_30d;
	}
	public final BigDecimal getBest_bid() {
		return best_bid;
	}
	public final void setBest_bid(BigDecimal best_bid) {
		this.best_bid = best_bid;
	}
	public final BigDecimal getBest_ask() {
		return best_ask;
	}
	public final void setBest_ask(BigDecimal best_ask) {
		this.best_ask = best_ask;
	}
	public final Side getSide() {
		return side;
	}
	public final void setSide(Side side) {
		this.side = side;
	}
	public final Instant getTime() {
		return time;
	}
	public final void setTime(Instant time) {
		this.time = time;
	}
	public final Long getTrade_id() {
		return trade_id;
	}
	public final void setTrade_id(Long trade_id) {
		this.trade_id = trade_id;
	}
	public final BigDecimal getLast_size() {
		return last_size;
	}
	public final void setLast_size(BigDecimal last_size) {
		this.last_size = last_size;
	}
	
	
	
}
