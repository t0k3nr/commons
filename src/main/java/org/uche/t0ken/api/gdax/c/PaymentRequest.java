package org.uche.t0ken.api.gdax.c;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class PaymentRequest implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -1119992668907451481L;
	BigDecimal amount;
    String currency;
    String payment_method_id;

    public PaymentRequest(BigDecimal amount, String currency, String payment_method_id) {
        this.amount = amount;
        this.currency = currency;
        this.payment_method_id = payment_method_id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPayment_method_id() {
        return payment_method_id;
    }

    public void setPayment_method_id(String payment_method_id) {
        this.payment_method_id = payment_method_id;
    }
}
