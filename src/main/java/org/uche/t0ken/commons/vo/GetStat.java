package org.uche.t0ken.commons.vo;

import java.io.Serializable;
import java.time.Instant;

import org.uche.t0ken.commons.enums.StatGranularity;
import org.uche.t0ken.commons.util.InstantDeserializer;
import org.uche.t0ken.commons.util.InstantSerializer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonInclude(Include.NON_NULL)
public class GetStat implements Serializable {

	
	private static final long serialVersionUID = 992993471748733351L;
	private StatGranularity statGranularity;
	@JsonSerialize(using = InstantSerializer.class)
	@JsonDeserialize(using = InstantDeserializer.class)
	private Instant ts;
	private Integer depth;
	public StatGranularity getStatGranularity() {
		return statGranularity;
	}
	public void setStatGranularity(StatGranularity statGranularity) {
		this.statGranularity = statGranularity;
	}
	public Instant getTs() {
		return ts;
	}
	public void setTs(Instant ts) {
		this.ts = ts;
	}
	public Integer getDepth() {
		return depth;
	}
	public void setDepth(Integer depth) {
		this.depth = depth;
	}

}
