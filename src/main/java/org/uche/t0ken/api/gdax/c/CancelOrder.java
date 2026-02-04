package org.uche.t0ken.api.gdax.c;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class CancelOrder implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 4401414098315798812L;
	private String orderId;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

}
