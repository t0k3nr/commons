package org.uche.t0ken.api.gdax;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
@JsonTypeInfo(use = Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
@Type(value = Error.class, name="error"),
@Type(value = Subscribe.class, name="subscribe"),
@Type(value = Subscriptions.class, name="subscriptions"),
@Type(value = Unsubscribe.class, name="unsubscribe"),
@Type(value = Ticker.class, name="ticker"),
@Type(value = Snapshot.class, name="snapshot"),
@Type(value = HeartBeat.class, name="heartbeat"),
@Type(value = L2Update.class, name="l2update"),
@Type(value = Open.class, name="open"),
@Type(value = Done.class, name="done"),
@Type(value = Change.class, name="change"),
@Type(value = Match.class, name="match"),
@Type(value = LastMatch.class, name="last_match"),
@Type(value = Received.class, name="received"),
@Type(value = Restored.class, name="restored"),
@Type(value = Confirmed.class, name="confirmed"),
@Type(value = Activate.class, name="activate")})

@JsonInclude(Include.NON_NULL)
public abstract class Data implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
}
