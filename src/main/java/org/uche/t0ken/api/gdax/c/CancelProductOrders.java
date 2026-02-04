package org.uche.t0ken.api.gdax.c;

import java.io.Serializable;

import org.uche.t0ken.api.gdax.ProductId;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class CancelProductOrders implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2115765351749431087L;
	private ProductId product_id;

	public ProductId getProduct_id() {
		return product_id;
	}

	public void setProduct_id(ProductId product_id) {
		this.product_id = product_id;
	}

	

	
}
