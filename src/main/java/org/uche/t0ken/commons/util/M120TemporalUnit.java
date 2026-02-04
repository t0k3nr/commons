package org.uche.t0ken.commons.util;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalUnit;

public class M120TemporalUnit implements TemporalUnit {

	@Override
	public Duration getDuration() {
		return Duration.ofMinutes(120);
	}

	@Override
	public boolean isDurationEstimated() {
		return false;
	}

	@Override
	public boolean isDateBased() {
		return false;
	}

	@Override
	public boolean isTimeBased() {
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <R extends Temporal> R addTo(R temporal, long amount) {
		return (R) temporal.plus(amount * 120, ChronoUnit.MINUTES);
	}

	@Override
	public long between(Temporal temporal1Inclusive, Temporal temporal2Exclusive) {
		return temporal1Inclusive.until(temporal2Exclusive, this);
	}

}
