package org.uche.t0ken.api.gdax;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class L2Update extends Data implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8434926931042190788L;
	/*
	 * 
	{"type":"l2update",
	"product_id":"BTC-USD",
	"time":"2018-02-15T15:54:04.218Z",
	"changes":[["sell","9842.51000000","0"]]}
}	 */
	private ProductId product_id;
	@JsonSerialize(using = InstantSerializer.class)
	@JsonDeserialize(using = InstantDeserializer.class)
	private Instant time;
	private List<L2Change> changes;
	public final ProductId getProduct_id() {
		return product_id;
	}
	public final void setProduct_id(ProductId product_id) {
		this.product_id = product_id;
	}
	public final Instant getTime() {
		return time;
	}
	public final void setTime(Instant time) {
		this.time = time;
	}
	public List<L2Change> getChanges() {
		return changes;
	}
	public void setChanges(List<L2Change> changes) {
		this.changes = changes;
	}
	
	
	
}
