package org.uche.t0ken.commons.util;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalUnit;

public class D12TemporalUnit implements TemporalUnit {

	/*
	 * /*
D1(86400), // H24; D1
D2(172800), // D2
D3(259200), // D3
D4(345600), // D4
D5(432000), // D5
D6(518400), // D6
D8(691200), // D8

	 */
	@Override
	public Duration getDuration() {
		return Duration.ofMinutes(17280);
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
		return (R) temporal.plus(amount * 17280, ChronoUnit.MINUTES);
	}

	@Override
	public long between(Temporal temporal1Inclusive, Temporal temporal2Exclusive) {
		return temporal1Inclusive.until(temporal2Exclusive, this);
	}

}
