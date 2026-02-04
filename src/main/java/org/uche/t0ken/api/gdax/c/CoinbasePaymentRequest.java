package org.uche.t0ken.api.gdax.c;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class CoinbasePaymentRequest implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -7101189779373028991L;
	String currency;
    BigDecimal amount;
    String coinbase_account_id;

    public CoinbasePaymentRequest(BigDecimal amount, String currency, String coinbase_account_id) {
        this.currency = currency;
        this.amount = amount;
        this.coinbase_account_id = coinbase_account_id;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCoinbase_account_id() {
        return coinbase_account_id;
    }

    public void setCoinbase_account_id(String coinbase_account_id) {
        this.coinbase_account_id = coinbase_account_id;
    }
}
