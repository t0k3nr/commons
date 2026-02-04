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
public class Account implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 5815188372230181119L;
	private String id;
    private String currency;
    private BigDecimal balance;
    private BigDecimal available;
    private BigDecimal hold;
    private String profile_id;
    
    private BigDecimal tradePrice;
    private BigDecimal tradeSize;
    private BigDecimal lastTradePrice;
    private BigDecimal lastTradeSize;
    @JsonSerialize(using = InstantSerializer.class)
	@JsonDeserialize(using = InstantDeserializer.class)
	private Instant lastTradeTs;
    
    public Account() {}

    public Account(String id,
                   String currency,
                   BigDecimal balance,
                   BigDecimal available,
                   BigDecimal hold,
                   String profile_id) {
        this.id = id;
        this.currency = currency;
        this.balance = balance;
        this.available = available;
        this.hold = hold;
        this.profile_id = profile_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getHold() {
        return hold;
    }

    public void setHold(BigDecimal hold) {
        this.hold = hold;
    }

    public BigDecimal getAvailable() {
        return available;
    }

    public void setAvailable(BigDecimal available) {
        this.available = available;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getProfile_id() {
        return profile_id;
    }

    public void setProfile_id(String profile_id) {
        this.profile_id = profile_id;
    }

	public BigDecimal getTradePrice() {
		return tradePrice;
	}

	public void setTradePrice(BigDecimal tradePrice) {
		this.tradePrice = tradePrice;
	}

	public BigDecimal getTradeSize() {
		return tradeSize;
	}

	public void setTradeSize(BigDecimal tradeSize) {
		this.tradeSize = tradeSize;
	}

	public BigDecimal getLastTradePrice() {
		return lastTradePrice;
	}

	public void setLastTradePrice(BigDecimal lastTradePrice) {
		this.lastTradePrice = lastTradePrice;
	}

	public BigDecimal getLastTradeSize() {
		return lastTradeSize;
	}

	public void setLastTradeSize(BigDecimal lastTradeSize) {
		this.lastTradeSize = lastTradeSize;
	}

	public Instant getLastTradeTs() {
		return lastTradeTs;
	}

	public void setLastTradeTs(Instant lastTradeTs) {
		this.lastTradeTs = lastTradeTs;
	}
}
