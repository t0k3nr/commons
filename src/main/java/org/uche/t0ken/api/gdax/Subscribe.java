package org.uche.t0ken.api.gdax;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class Subscribe extends Data implements Serializable {
	/**
	 * private signMessage(msg: any): any {
        const headers: AuthHeaders = this.coinbaseProAPI.getSignature('GET', '/users/self/verify', '');
        msg.signature = headers['CB-ACCESS-SIGN'];
        msg.key = headers['CB-ACCESS-KEY'];
        msg.timestamp = headers['CB-ACCESS-TIMESTAMP'];
        msg.passphrase = headers['CB-ACCESS-PASSPHRASE'];
        return msg;
    }
	 */
	
	private static final long serialVersionUID = -8598302922332765535L;
	private List<ProductId> product_ids;
	private List<ChannelName> channels;
	private String signature;
	private String key;
	private String timestamp;
	private String passphrase;
	
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

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	

	public String getPassphrase() {
		return passphrase;
	}

	public void setPassphrase(String passphrase) {
		this.passphrase = passphrase;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
}
