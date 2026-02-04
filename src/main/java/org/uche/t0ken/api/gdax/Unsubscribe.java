package org.uche.t0ken.api.gdax;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class Unsubscribe extends Data implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6473815496110980548L;
	private List<ProductId> product_ids;
	private List<ChannelName> channels;
	
	

	public final List<ProductId> getProduct_ids() {
		return product_ids;
	}

	public final void setProduct_ids(List<ProductId> product_ids) {
		this.product_ids = product_ids;
	}

	public final List<ChannelName> getChannels() {
		return channels;
	}

	public final void setChannels(List<ChannelName> channels) {
		this.channels = channels;
	}
	
}
