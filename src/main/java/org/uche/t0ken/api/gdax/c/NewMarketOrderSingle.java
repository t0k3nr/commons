package org.uche.t0ken.api.gdax.c;

import java.math.BigDecimal;

import org.uche.t0ken.api.gdax.OrderType;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


@JsonInclude(Include.NON_NULL)
public class NewMarketOrderSingle extends NewOrderSingle {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1412674788668092720L;
	private BigDecimal size; //optional: Desired amount in BTC
    private BigDecimal funds;
    
    
    public NewMarketOrderSingle(){
        setType(OrderType.MARKET);
    }

    public BigDecimal getSize() {
        return size;
    }

    public void setSize(BigDecimal size) {
        this.size = size;
    }

	public BigDecimal getFunds() {
		return funds;
	}

	public void setFunds(BigDecimal funds) {
		this.funds = funds;
	}

	

}
