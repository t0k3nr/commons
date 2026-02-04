package org.uche.t0ken.commons.minmax;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.NavigableMap;

public interface MinMax {

	//public void injectVal(BigDecimal v, Instant now);
	public NavigableMap<Instant, MarketData> getLows();

	public NavigableMap<Instant, MarketData> getHighs();
	
	public void resetV2(Instant now);
	public Boolean isPossibleExtreme();
	public String getLowDisplayString();
	public String getHighDisplayString();
	public BigDecimal getPreviousValue();
}
