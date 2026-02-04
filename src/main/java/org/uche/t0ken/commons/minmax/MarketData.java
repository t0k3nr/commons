package org.uche.t0ken.commons.minmax;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.uche.t0ken.commons.enums.StatGranularity;

public class MarketData {

	
	public String toString() {
		return (ts + " wt: " + value + (wt1!=null?(" wt1: " + wt1):"") + (dataSg!=null?(" dataSg: " + dataSg):""));
	}
	
	private Instant ts;
	private Instant createdTs;
	private Boolean maximum;
	private BigDecimal value;
	private BigDecimal highestValue;
	private BigDecimal lowestValue;
	private Instant highestValueTs;
	private Instant lowestValueTs;
	
	private StatGranularity dataSg;

	
	private BigDecimal low;
	private BigDecimal high;
	private BigDecimal hlc3;
	private BigDecimal wt1;
	private BigDecimal wt2;
	
	
	private BigDecimal lowestWt;
	private BigDecimal highestWt;
	private Instant lowestWtTs;
	private Instant highestWtTs;
	
	
	private BigDecimal lowestWt1;
	private BigDecimal highestWt1;
	private Instant lowestWt1Ts;
	private Instant highestWt1Ts;
	
	
	private Map<StatGranularity, BigDecimal> highestWts = new HashMap<StatGranularity, BigDecimal>();
	private Map<StatGranularity, Instant> highestWtsTs = new HashMap<StatGranularity, Instant>();
	private Map<StatGranularity, BigDecimal> lowestWts = new HashMap<StatGranularity, BigDecimal>();
	private Map<StatGranularity, Instant> lowestWtsTs = new HashMap<StatGranularity, Instant>();
	
	private Map<StatGranularity, BigDecimal> highestWt1s = new HashMap<StatGranularity, BigDecimal>();
	private Map<StatGranularity, Instant> highestWt1sTs = new HashMap<StatGranularity, Instant>();
	private Map<StatGranularity, BigDecimal> lowestWt1s = new HashMap<StatGranularity, BigDecimal>();
	private Map<StatGranularity, Instant> lowestWt1sTs = new HashMap<StatGranularity, Instant>();
	
	
	/*private BigDecimal highestWtM5;
	private BigDecimal highestWtM10;
	private BigDecimal highestWtM15;
	private BigDecimal highestWtM30;
	private BigDecimal highestWtM45;
	private BigDecimal highestWtH1;
	private BigDecimal highestWtH2;
	private BigDecimal highestWtH3;
	private BigDecimal highestWtH4;
	private BigDecimal highestWtH6;
	
	private BigDecimal lowestWtM5;
	private BigDecimal lowestWtM10;
	private BigDecimal lowestWtM15;
	private BigDecimal lowestWtM30;
	private BigDecimal lowestWtM45;
	private BigDecimal lowestWtH1;
	private BigDecimal lowestWtH2;
	private BigDecimal lowestWtH3;
	private BigDecimal lowestWtH4;
	private BigDecimal lowestWtH6;
	*/
	/*
	private Instant highestWtM5Ts;
	private Instant highestWtM10Ts;
	private Instant highestWtM15Ts;
	private Instant highestWtM30Ts;
	private Instant highestWtM45Ts;
	private Instant highestWtH1Ts;
	private Instant highestWtH2Ts;
	private Instant highestWtH3Ts;
	private Instant highestWtH4Ts;
	private Instant highestWtH6Ts;
	
	private Instant lowestWtM5Ts;
	private Instant lowestWtM10Ts;
	private Instant lowestWtM15Ts;
	private Instant lowestWtM30Ts;
	private Instant lowestWtM45Ts;
	private Instant lowestWtH1Ts;
	private Instant lowestWtH2Ts;
	private Instant lowestWtH3Ts;
	private Instant lowestWtH4Ts;
	private Instant lowestWtH6Ts;
	
	

	private BigDecimal highestWt1M5;
	private BigDecimal highestWt1M10;
	private BigDecimal highestWt1M15;
	private BigDecimal highestWt1M30;
	private BigDecimal highestWt1M45;
	private BigDecimal highestWt1H1;
	private BigDecimal highestWt1H2;
	private BigDecimal highestWt1H3;
	private BigDecimal highestWt1H4;
	private BigDecimal highestWt1H6;
	
	private BigDecimal lowestWt1M5;
	private BigDecimal lowestWt1M10;
	private BigDecimal lowestWt1M15;
	private BigDecimal lowestWt1M30;
	private BigDecimal lowestWt1M45;
	private BigDecimal lowestWt1H1;
	private BigDecimal lowestWt1H2;
	private BigDecimal lowestWt1H3;
	private BigDecimal lowestWt1H4;
	private BigDecimal lowestWt1H6;
	
	
	private Instant highestWt1M5Ts;
	private Instant highestWt1M10Ts;
	private Instant highestWt1M15Ts;
	private Instant highestWt1M30Ts;
	private Instant highestWt1M45Ts;
	private Instant highestWt1H1Ts;
	private Instant highestWt1H2Ts;
	private Instant highestWt1H3Ts;
	private Instant highestWt1H4Ts;
	private Instant highestWt1H6Ts;
	
	private Instant lowestWt1M5Ts;
	private Instant lowestWt1M10Ts;
	private Instant lowestWt1M15Ts;
	private Instant lowestWt1M30Ts;
	private Instant lowestWt1M45Ts;
	private Instant lowestWt1H1Ts;
	private Instant lowestWt1H2Ts;
	private Instant lowestWt1H3Ts;
	private Instant lowestWt1H4Ts;
	private Instant lowestWt1H6Ts;
	
	
	*/
	
	
	private BigDecimal cdema;
	private BigDecimal ema100;
	private BigDecimal ema180;
	private BigDecimal ema390;
	private BigDecimal ema720;
	private BigDecimal ema1560;
	private BigDecimal ema4320;
	
	private BigDecimal highestEma390;
	private BigDecimal highestEma720;
	private BigDecimal highestEma1560;
	private BigDecimal highestEma4320;
	private BigDecimal highestHlc3;
	
	
	private BigDecimal lowestEma390;
	private BigDecimal lowestEma720;
	private BigDecimal lowestEma1560;
	private BigDecimal lowestEma4320;
	private BigDecimal lowestHlc3;
	
	
	private Instant lastSeenPositiveTs = null;
	private Instant lastSeenNegativeTs = null;
	private BigDecimal highestValueSincePositive = null;
	private BigDecimal lowestValueSinceNegative = null;
	
	
	/*private Instant lastSwitchedToPositiveTs = null;
	private Instant lastSwitchedToNegativeTs = null;
	
	private BigDecimal lowestValueSinceLastSwitchedToNegative = null;
	private BigDecimal highestValueSinceLastSwitchedToPositive = null;*/
	
	
	public Instant lastSeenBelowEma100Ts;
	public Instant lastSeenBelowEma180Ts;
	public Instant lastSeenBelowEma390Ts;
	public Instant lastSeenBelowEma720Ts;
	public Instant lastSeenBelowEma1560Ts;
	public Instant lastSeenBelowEma4320Ts;
	
	public Instant lastSeenAboveEma100Ts;
	public Instant lastSeenAboveEma180Ts;
	public Instant lastSeenAboveEma390Ts;
	public Instant lastSeenAboveEma720Ts;
	public Instant lastSeenAboveEma1560Ts;
	public Instant lastSeenAboveEma4320Ts;
	public Instant getTs() {
		return ts;
	}
	public void setTs(Instant ts) {
		this.ts = ts;
	}
	public Instant getCreatedTs() {
		return createdTs;
	}
	public void setCreatedTs(Instant createdTs) {
		this.createdTs = createdTs;
	}
	public Boolean getMaximum() {
		return maximum;
	}
	public void setMaximum(Boolean maximum) {
		this.maximum = maximum;
	}
	public BigDecimal getValue() {
		return value;
	}
	public void setValue(BigDecimal target) {
		this.value = target;
	}
	public BigDecimal getLow() {
		return low;
	}
	public void setLow(BigDecimal low) {
		this.low = low;
	}
	public BigDecimal getHigh() {
		return high;
	}
	public void setHigh(BigDecimal high) {
		this.high = high;
	}
	public BigDecimal getHlc3() {
		return hlc3;
	}
	public void setHlc3(BigDecimal hlc3) {
		this.hlc3 = hlc3;
	}
	public BigDecimal getWt1() {
		return wt1;
	}
	public void setWt1(BigDecimal wt1) {
		this.wt1 = wt1;
	}
	public BigDecimal getWt2() {
		return wt2;
	}
	public void setWt2(BigDecimal wt2) {
		this.wt2 = wt2;
	}
	public BigDecimal getLowestWt() {
		return lowestWt;
	}
	public void setLowestWt(BigDecimal lowestWt) {
		this.lowestWt = lowestWt;
	}
	public BigDecimal getHighestWt() {
		return highestWt;
	}
	public void setHighestWt(BigDecimal highestWt) {
		this.highestWt = highestWt;
	}
	public Instant getLowestWtTs() {
		return lowestWtTs;
	}
	public void setLowestWtTs(Instant lowestWtTs) {
		this.lowestWtTs = lowestWtTs;
	}
	public Instant getHighestWtTs() {
		return highestWtTs;
	}
	public void setHighestWtTs(Instant highestWtTs) {
		this.highestWtTs = highestWtTs;
	}
	
	public BigDecimal getCdema() {
		return cdema;
	}
	public void setCdema(BigDecimal cdema) {
		this.cdema = cdema;
	}
	public BigDecimal getEma100() {
		return ema100;
	}
	public void setEma100(BigDecimal ema100) {
		this.ema100 = ema100;
	}
	public BigDecimal getEma180() {
		return ema180;
	}
	public void setEma180(BigDecimal ema180) {
		this.ema180 = ema180;
	}
	public BigDecimal getEma390() {
		return ema390;
	}
	public void setEma390(BigDecimal ema390) {
		this.ema390 = ema390;
	}
	public BigDecimal getEma720() {
		return ema720;
	}
	public void setEma720(BigDecimal ema720) {
		this.ema720 = ema720;
	}
	public BigDecimal getEma1560() {
		return ema1560;
	}
	public void setEma1560(BigDecimal ema1560) {
		this.ema1560 = ema1560;
	}
	public BigDecimal getEma4320() {
		return ema4320;
	}
	public void setEma4320(BigDecimal ema4320) {
		this.ema4320 = ema4320;
	}
	public BigDecimal getHighestEma390() {
		return highestEma390;
	}
	public void setHighestEma390(BigDecimal highestEma390) {
		this.highestEma390 = highestEma390;
	}
	public BigDecimal getHighestEma720() {
		return highestEma720;
	}
	public void setHighestEma720(BigDecimal highestEma720) {
		this.highestEma720 = highestEma720;
	}
	public BigDecimal getHighestEma1560() {
		return highestEma1560;
	}
	public void setHighestEma1560(BigDecimal highestEma1560) {
		this.highestEma1560 = highestEma1560;
	}
	public BigDecimal getHighestEma4320() {
		return highestEma4320;
	}
	public void setHighestEma4320(BigDecimal highestEma4320) {
		this.highestEma4320 = highestEma4320;
	}
	public BigDecimal getHighestHlc3() {
		return highestHlc3;
	}
	public void setHighestHlc3(BigDecimal highestHlc3) {
		this.highestHlc3 = highestHlc3;
	}
	public BigDecimal getLowestEma390() {
		return lowestEma390;
	}
	public void setLowestEma390(BigDecimal lowestEma390) {
		this.lowestEma390 = lowestEma390;
	}
	public BigDecimal getLowestEma720() {
		return lowestEma720;
	}
	public void setLowestEma720(BigDecimal lowestEma720) {
		this.lowestEma720 = lowestEma720;
	}
	public BigDecimal getLowestEma1560() {
		return lowestEma1560;
	}
	public void setLowestEma1560(BigDecimal lowestEma1560) {
		this.lowestEma1560 = lowestEma1560;
	}
	public BigDecimal getLowestEma4320() {
		return lowestEma4320;
	}
	public void setLowestEma4320(BigDecimal lowestEma4320) {
		this.lowestEma4320 = lowestEma4320;
	}
	public BigDecimal getLowestHlc3() {
		return lowestHlc3;
	}
	public void setLowestHlc3(BigDecimal lowestHlc3) {
		this.lowestHlc3 = lowestHlc3;
	}
	public Instant getLastSeenPositiveTs() {
		return lastSeenPositiveTs;
	}
	public void setLastSeenPositiveTs(Instant lastSeenPositiveTs) {
		this.lastSeenPositiveTs = lastSeenPositiveTs;
	}
	public Instant getLastSeenNegativeTs() {
		return lastSeenNegativeTs;
	}
	public void setLastSeenNegativeTs(Instant lastSeenNegativeTs) {
		this.lastSeenNegativeTs = lastSeenNegativeTs;
	}
	public Instant getLastSeenBelowEma100Ts() {
		return lastSeenBelowEma100Ts;
	}
	public void setLastSeenBelowEma100Ts(Instant lastSeenBelowEma100Ts) {
		this.lastSeenBelowEma100Ts = lastSeenBelowEma100Ts;
	}
	public Instant getLastSeenBelowEma180Ts() {
		return lastSeenBelowEma180Ts;
	}
	public void setLastSeenBelowEma180Ts(Instant lastSeenBelowEma180Ts) {
		this.lastSeenBelowEma180Ts = lastSeenBelowEma180Ts;
	}
	public Instant getLastSeenBelowEma390Ts() {
		return lastSeenBelowEma390Ts;
	}
	public void setLastSeenBelowEma390Ts(Instant lastSeenBelowEma390Ts) {
		this.lastSeenBelowEma390Ts = lastSeenBelowEma390Ts;
	}
	public Instant getLastSeenBelowEma720Ts() {
		return lastSeenBelowEma720Ts;
	}
	public void setLastSeenBelowEma720Ts(Instant lastSeenBelowEma720Ts) {
		this.lastSeenBelowEma720Ts = lastSeenBelowEma720Ts;
	}
	public Instant getLastSeenBelowEma1560Ts() {
		return lastSeenBelowEma1560Ts;
	}
	public void setLastSeenBelowEma1560Ts(Instant lastSeenBelowEma1560Ts) {
		this.lastSeenBelowEma1560Ts = lastSeenBelowEma1560Ts;
	}
	public Instant getLastSeenBelowEma4320Ts() {
		return lastSeenBelowEma4320Ts;
	}
	public void setLastSeenBelowEma4320Ts(Instant lastSeenBelowEma4320Ts) {
		this.lastSeenBelowEma4320Ts = lastSeenBelowEma4320Ts;
	}
	public Instant getLastSeenAboveEma100Ts() {
		return lastSeenAboveEma100Ts;
	}
	public void setLastSeenAboveEma100Ts(Instant lastSeenAboveEma100Ts) {
		this.lastSeenAboveEma100Ts = lastSeenAboveEma100Ts;
	}
	public Instant getLastSeenAboveEma180Ts() {
		return lastSeenAboveEma180Ts;
	}
	public void setLastSeenAboveEma180Ts(Instant lastSeenAboveEma180Ts) {
		this.lastSeenAboveEma180Ts = lastSeenAboveEma180Ts;
	}
	public Instant getLastSeenAboveEma390Ts() {
		return lastSeenAboveEma390Ts;
	}
	public void setLastSeenAboveEma390Ts(Instant lastSeenAboveEma390Ts) {
		this.lastSeenAboveEma390Ts = lastSeenAboveEma390Ts;
	}
	public Instant getLastSeenAboveEma720Ts() {
		return lastSeenAboveEma720Ts;
	}
	public void setLastSeenAboveEma720Ts(Instant lastSeenAboveEma720Ts) {
		this.lastSeenAboveEma720Ts = lastSeenAboveEma720Ts;
	}
	public Instant getLastSeenAboveEma1560Ts() {
		return lastSeenAboveEma1560Ts;
	}
	public void setLastSeenAboveEma1560Ts(Instant lastSeenAboveEma1560Ts) {
		this.lastSeenAboveEma1560Ts = lastSeenAboveEma1560Ts;
	}
	public Instant getLastSeenAboveEma4320Ts() {
		return lastSeenAboveEma4320Ts;
	}
	public void setLastSeenAboveEma4320Ts(Instant lastSeenAboveEma4320Ts) {
		this.lastSeenAboveEma4320Ts = lastSeenAboveEma4320Ts;
	}
	public BigDecimal getLowestWt1() {
		return lowestWt1;
	}
	public void setLowestWt1(BigDecimal lowestWt1) {
		this.lowestWt1 = lowestWt1;
	}
	public BigDecimal getHighestWt1() {
		return highestWt1;
	}
	public void setHighestWt1(BigDecimal highestWt1) {
		this.highestWt1 = highestWt1;
	}
	public Instant getLowestWt1Ts() {
		return lowestWt1Ts;
	}
	public void setLowestWt1Ts(Instant lowestWt1Ts) {
		this.lowestWt1Ts = lowestWt1Ts;
	}
	public Instant getHighestWt1Ts() {
		return highestWt1Ts;
	}
	public void setHighestWt1Ts(Instant highestWt1Ts) {
		this.highestWt1Ts = highestWt1Ts;
	}
	
	public BigDecimal getHighestValue() {
		return highestValue;
	}
	public void setHighestValue(BigDecimal highestValue) {
		this.highestValue = highestValue;
	}
	public BigDecimal getLowestValue() {
		return lowestValue;
	}
	public void setLowestValue(BigDecimal lowestValue) {
		this.lowestValue = lowestValue;
	}
	
	public Map<StatGranularity, BigDecimal> getHighestWts() {
		return highestWts;
	}
	public void setHighestWts(Map<StatGranularity, BigDecimal> highestWts) {
		this.highestWts = highestWts;
	}
	public Map<StatGranularity, Instant> getHighestWtsTs() {
		return highestWtsTs;
	}
	public void setHighestWtsTs(Map<StatGranularity, Instant> highestWtsTs) {
		this.highestWtsTs = highestWtsTs;
	}
	public Map<StatGranularity, BigDecimal> getLowestWts() {
		return lowestWts;
	}
	public void setLowestWts(Map<StatGranularity, BigDecimal> lowestWts) {
		this.lowestWts = lowestWts;
	}
	public Map<StatGranularity, Instant> getLowestWtsTs() {
		return lowestWtsTs;
	}
	public void setLowestWtsTs(Map<StatGranularity, Instant> lowestWtsTs) {
		this.lowestWtsTs = lowestWtsTs;
	}
	public Map<StatGranularity, BigDecimal> getHighestWt1s() {
		return highestWt1s;
	}
	public void setHighestWt1s(Map<StatGranularity, BigDecimal> highestWt1s) {
		this.highestWt1s = highestWt1s;
	}
	public Map<StatGranularity, Instant> getHighestWt1sTs() {
		return highestWt1sTs;
	}
	public void setHighestWt1sTs(Map<StatGranularity, Instant> highestWt1sTs) {
		this.highestWt1sTs = highestWt1sTs;
	}
	public Map<StatGranularity, BigDecimal> getLowestWt1s() {
		return lowestWt1s;
	}
	public void setLowestWt1s(Map<StatGranularity, BigDecimal> lowestWt1s) {
		this.lowestWt1s = lowestWt1s;
	}
	public Map<StatGranularity, Instant> getLowestWt1sTs() {
		return lowestWt1sTs;
	}
	public void setLowestWt1sTs(Map<StatGranularity, Instant> lowestWt1sTs) {
		this.lowestWt1sTs = lowestWt1sTs;
	}
	public Instant getHighestValueTs() {
		return highestValueTs;
	}
	public void setHighestValueTs(Instant highestValueTs) {
		this.highestValueTs = highestValueTs;
	}
	public Instant getLowestValueTs() {
		return lowestValueTs;
	}
	public void setLowestValueTs(Instant lowestValueTs) {
		this.lowestValueTs = lowestValueTs;
	}
	public BigDecimal getHighestValueSincePositive() {
		return highestValueSincePositive;
	}
	public void setHighestValueSincePositive(BigDecimal highestValueSincePositive) {
		this.highestValueSincePositive = highestValueSincePositive;
	}
	public BigDecimal getLowestValueSinceNegative() {
		return lowestValueSinceNegative;
	}
	public void setLowestValueSinceNegative(BigDecimal lowestValueSinceNegative) {
		this.lowestValueSinceNegative = lowestValueSinceNegative;
	}
	
	public StatGranularity getDataSg() {
		return dataSg;
	}

	public void setDataSg(StatGranularity dataSg) {
		this.dataSg = dataSg;
	}
	
	
}
