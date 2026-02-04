package org.uche.t0ken.api.gdax;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class Subscriptions extends Data implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8267479819663951492L;
	private List<ProductId> product_ids;
	private List<Channel> channels;
	
	

	public final List<Channel> getChannels() {
		return channels;
	}

	public final void setChannels(List<Channel> channels) {
		this.channels = channels;
	}

	public final List<ProductId> getProduct_ids() {
		return product_ids;
	}

	public final void setProduct_ids(List<ProductId> product_ids) {
		this.product_ids = product_ids;
	}

	
	
}
