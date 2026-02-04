package org.uche.t0ken.api.gdax;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(using = OrderSerializer.class)
@JsonDeserialize(using = OrderDeserializer.class)
public class Order  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal price;
	private BigDecimal size;
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
	
	
}
