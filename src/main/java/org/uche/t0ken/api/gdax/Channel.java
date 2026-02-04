package org.uche.t0ken.api.gdax;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class Channel  implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ChannelName name;
	private List<ProductId> product_ids;
	
	public final ChannelName getName() {
		return name;
	}
	public final void setName(ChannelName name) {
		this.name = name;
	}
	public final List<ProductId> getProduct_ids() {
		return product_ids;
	}
	public final void setProduct_ids(List<ProductId> product_ids) {
		this.product_ids = product_ids;
	}
	
	
	
}
