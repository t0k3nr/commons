package org.uche.t0ken.api.gdax.c;

import java.io.Serializable;

import org.uche.t0ken.api.gdax.OrderType;
import org.uche.t0ken.api.gdax.ProductId;
import org.uche.t0ken.api.gdax.Side;
import org.uche.t0ken.api.gdax.Stp;

public abstract class NewOrderSingle implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String client_oid; //optional
    private OrderType type; //default is limit, other types are market and stop
    private Side side;
    private ProductId product_id;
    private Stp stp; //optional: values are dc, co , cn , cb
   // 

    public Stp getStp() {
        return stp;
    }

    public void setStp(Stp stp) {
        this.stp = stp;
    }

   
    public String getClient_oid() {
        return client_oid;
    }

    public void setClient_oid(String client_oid) {
        this.client_oid = client_oid;
    }

	public OrderType getType() {
		return type;
	}

	public void setType(OrderType type) {
		this.type = type;
	}

	public Side getSide() {
		return side;
	}

	public void setSide(Side side) {
		this.side = side;
	}

	public ProductId getProduct_id() {
		return product_id;
	}

	public void setProduct_id(ProductId product_id) {
		this.product_id = product_id;
	}

  
}
