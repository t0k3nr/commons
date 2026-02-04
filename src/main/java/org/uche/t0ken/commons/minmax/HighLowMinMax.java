package org.uche.t0ken.commons.minmax;

import java.math.BigDecimal;
import java.time.Instant;

import org.jboss.logging.Logger;
import org.uche.t0ken.commons.util.Indicators;

public class HighLowMinMax extends AbstractMinMax implements MinMax {

	
	private static final Logger logger = Logger.getLogger("MinMax");
	
	
	
	
	
	private Boolean positive = null;
	private BigDecimal minVarInPercent = null;
	
	private boolean debug = false;
	
	public HighLowMinMax(BigDecimal minVarInPercent, boolean debug, String name) {
		this.debug = debug;
		this.name = name;
		this.minVarInPercent = minVarInPercent;
	}
	
	
	
	public void injectVal(BigDecimal treshold, 
			BigDecimal wt1, 
			BigDecimal wt2, 
			BigDecimal low, 
			BigDecimal high, 
			BigDecimal hlc3,
			BigDecimal ema100,
			BigDecimal ema180,
			BigDecimal ema390,
			BigDecimal ema720,
			BigDecimal ema1560,
			BigDecimal ema4320,
			Instant now) {


		if (previousValue != null) {
			if (positive == null) {
				positive = (treshold.signum()>0);

				if (positive) {
					if (debug) logger.info("injectVal: " + name + " comparing currentHigh: " + currentHigh + " high: " + high);

					if (currentHigh == null) {
						currentHigh = high;
						currentHighTs = now;
						
						marketData.setLow(low);
						marketData.setHigh(high);
						marketData.setHlc3(hlc3);
						marketData.setEma100(ema100);
						marketData.setEma180(ema180);
						marketData.setEma390(ema390);
						marketData.setEma720(ema720);
						marketData.setEma1560(ema1560);
						marketData.setEma4320(ema4320);
						marketData.setWt1(wt1);
						marketData.setWt2(wt2);
						marketData.setTs(now);
						
						
						
					} else if (high.compareTo(currentHigh) > 0) {
						currentHigh = high;
						currentHighTs = now;
						
						marketData.setLow(low);
						marketData.setHigh(high);
						marketData.setHlc3(hlc3);
						marketData.setEma100(ema100);
						marketData.setEma180(ema180);
						marketData.setEma390(ema390);
						marketData.setEma720(ema720);
						marketData.setEma1560(ema1560);
						marketData.setEma4320(ema4320);
						marketData.setWt1(wt1);
						marketData.setWt2(wt2);
						marketData.setTs(now);
						
					}
					
				} else {
					if (debug) logger.info("injectVal: " + name + " comparing currentLow: " + currentLow + " low: " + low);

					if (currentLow == null) {
						currentLow = low;
						currentLowTs = now;
						
						marketData.setLow(low);
						marketData.setHigh(high);
						marketData.setHlc3(hlc3);
						marketData.setEma100(ema100);
						marketData.setEma180(ema180);
						marketData.setEma390(ema390);
						marketData.setEma720(ema720);
						marketData.setEma1560(ema1560);
						marketData.setEma4320(ema4320);
						marketData.setWt1(wt1);
						marketData.setWt2(wt2);
						marketData.setTs(now);
						
					} else if (low.compareTo(currentLow) < 0) {
						currentLow = low;
						currentLowTs = now;
						
						marketData.setLow(low);
						marketData.setHigh(high);
						marketData.setHlc3(hlc3);
						marketData.setEma100(ema100);
						marketData.setEma180(ema180);
						marketData.setEma390(ema390);
						marketData.setEma720(ema720);
						marketData.setEma1560(ema1560);
						marketData.setEma4320(ema4320);
						marketData.setWt1(wt1);
						marketData.setWt2(wt2);
						marketData.setTs(now);
						
					}

				}
			} else {
				if (positive == (treshold.signum()>0)) {
					if (debug) logger.info("injectVal: " + name + " going same way: positive: " + positive + " hcl3.signum()>0 " + (treshold.signum()>0));
					// going the same way, updating extreme
					if (positive) {
						if (debug) logger.info("injectVal: " + name + " comparing currentHigh: " + currentHigh + " high: " + high);
						if (currentHigh == null) {
							currentHigh = high;
							currentHighTs = now;
							
							marketData.setLow(low);
							marketData.setHigh(high);
							marketData.setHlc3(hlc3);
							marketData.setEma100(ema100);
							marketData.setEma180(ema180);
							marketData.setEma390(ema390);
							marketData.setEma720(ema720);
							marketData.setEma1560(ema1560);
							marketData.setEma4320(ema4320);
							marketData.setWt1(wt1);
							marketData.setWt2(wt2);
							marketData.setTs(now);
							
						} else if (high.compareTo(currentHigh) > 0) {
							currentHigh = high;
							currentHighTs = now;
							
							marketData.setLow(low);
							marketData.setHigh(high);
							marketData.setHlc3(hlc3);
							marketData.setEma100(ema100);
							marketData.setEma180(ema180);
							marketData.setEma390(ema390);
							marketData.setEma720(ema720);
							marketData.setEma1560(ema1560);
							marketData.setEma4320(ema4320);
							marketData.setWt1(wt1);
							marketData.setWt2(wt2);
							marketData.setTs(now);
							
						}
					} else {
						if (debug) logger.info("injectVal: " + name + " comparing currentLow: " + currentLow + " low: " + low);
						if (currentLow == null) {
							currentLow = low;
							currentLowTs = now;
							
							marketData.setLow(low);
							marketData.setHigh(high);
							marketData.setHlc3(hlc3);
							marketData.setEma100(ema100);
							marketData.setEma180(ema180);
							marketData.setEma390(ema390);
							marketData.setEma720(ema720);
							marketData.setEma1560(ema1560);
							marketData.setEma4320(ema4320);
							marketData.setWt1(wt1);
							marketData.setWt2(wt2);
							marketData.setTs(now);
							
						} else if (low.compareTo(currentLow) < 0) {
							currentLow = low;
							currentLowTs = now;
							
							marketData.setLow(low);
							marketData.setHigh(high);
							marketData.setHlc3(hlc3);
							marketData.setEma100(ema100);
							marketData.setEma180(ema180);
							marketData.setEma390(ema390);
							marketData.setEma720(ema720);
							marketData.setEma1560(ema1560);
							marketData.setEma4320(ema4320);
							marketData.setWt1(wt1);
							marketData.setWt2(wt2);
							marketData.setTs(now);
							
						}

					}
				} else {
					// going opposite way. Check if difference is > than resPercent%.

					if (positive) {
						// currentHigh is a HIGH
						//if (debug) logger.info("currentHighTs: " + currentHighTs + " currentHigh: " + currentHigh);
						if (debug) logger.info("injectVal: " + name + " going opposite way: setting high  " + currentHigh + " high: " + high);
						if (currentHigh != null) {
							
							if (lows.size() > 0) {
								Instant lastLowTs = lows.lastKey();
								BigDecimal lastLow = lows.get(lastLowTs).getLow();
								
								Instant lastHighTs = null;
								BigDecimal lastHigh = null;
								
								if (highs.size() >0) {
									lastHighTs = highs.lastKey();
									lastHigh = highs.get(lastHighTs).getHigh();
								}
								
								if (BigDecimal.ONE.subtract(lastLow.divide(currentHigh, Indicators.roundIndicators)).compareTo(minVarInPercent)>0) {
									
									if ((lastHighTs != null) && (lastHighTs.compareTo(lastLowTs)>0)) {
										
										// lastHigh already after lastLow
										
										if (lastHigh.compareTo(currentHigh) > 0) {
											// lastHigh higher than currentHigh, ignoring cutrentHigh
										} else {
											// replacing
											highs.remove(lastHighTs);
											highs.put(currentHighTs, marketData);
											marketData = new MarketData();
											marketData.setLow(low);
											marketData.setHigh(high);
											marketData.setHlc3(hlc3);
											marketData.setEma100(ema100);
											marketData.setEma180(ema180);
											marketData.setEma390(ema390);
											marketData.setEma720(ema720);
											marketData.setEma1560(ema1560);
											marketData.setEma4320(ema4320);
											marketData.setWt1(wt1);
											marketData.setWt2(wt2);
											marketData.setTs(now);
											
										}
										
									} else {
										
										highs.put(currentHighTs, marketData);
										marketData = new MarketData();
										marketData.setLow(low);
										marketData.setHigh(high);
										marketData.setHlc3(hlc3);
										marketData.setEma100(ema100);
										marketData.setEma180(ema180);
										marketData.setEma390(ema390);
										marketData.setEma720(ema720);
										marketData.setEma1560(ema1560);
										marketData.setEma4320(ema4320);
										marketData.setWt1(wt1);
										marketData.setWt2(wt2);
										marketData.setTs(now);
										
									}
									
									
									
									
								}
							} else {
								highs.put(currentHighTs, marketData);
								marketData = new MarketData();
								marketData.setLow(low);
								marketData.setHigh(high);
								marketData.setHlc3(hlc3);
								marketData.setEma100(ema100);
								marketData.setEma180(ema180);
								marketData.setEma390(ema390);
								marketData.setEma720(ema720);
								marketData.setEma1560(ema1560);
								marketData.setEma4320(ema4320);
								marketData.setWt1(wt1);
								marketData.setWt2(wt2);
								marketData.setTs(now);
								
							}
							
							
						}
						currentHigh = null;
						currentHighTs = null;
						currentLow = low;
						currentLowTs = now;
						positive = false;
					} else {
						if (debug) logger.info("injectVal: " + name + " going opposite way: setting low  " + currentLow + " low: " + low);

						// currentLow is a low
						if (currentLow != null) {
							if (highs.size() > 0) {
								Instant lastHighTs = highs.lastKey();
								BigDecimal lastHigh = highs.get(lastHighTs).getHigh();
								
								Instant lastLowTs = null;
								BigDecimal lastLow = null;
								
								if (lows.size() > 0) {
									lastLowTs = lows.lastKey();
									lastLow = lows.get(lastLowTs).getLow();
								}
								
								
								
								
								if (BigDecimal.ONE.subtract(currentLow.divide(lastHigh, Indicators.roundIndicators)).compareTo(minVarInPercent)>0) {
									
									if ((lastLowTs != null) && (lastLowTs.compareTo(lastHighTs)>0)) {
										
										// lastHigh already after lastLow
										
										if (lastLow.compareTo(currentLow) < 0) {
											// lastHigh higher than currentHigh, ignoring cutrentHigh
										} else {
											// replacing
											lows.remove(lastLowTs);
											lows.put(currentLowTs, marketData);
											
											marketData = new MarketData();
											marketData.setLow(low);
											marketData.setHigh(high);
											marketData.setHlc3(hlc3);
											marketData.setEma100(ema100);
											marketData.setEma180(ema180);
											marketData.setEma390(ema390);
											marketData.setEma720(ema720);
											marketData.setEma1560(ema1560);
											marketData.setEma4320(ema4320);
											marketData.setWt1(wt1);
											marketData.setWt2(wt2);
											marketData.setTs(now);
											
										}
										
									} else {
										lows.put(currentLowTs, marketData);
										
										marketData = new MarketData();
										marketData.setLow(low);
										marketData.setHigh(high);
										marketData.setHlc3(hlc3);
										marketData.setEma100(ema100);
										marketData.setEma180(ema180);
										marketData.setEma390(ema390);
										marketData.setEma720(ema720);
										marketData.setEma1560(ema1560);
										marketData.setEma4320(ema4320);
										marketData.setWt1(wt1);
										marketData.setWt2(wt2);
										marketData.setTs(now);
										
										
									}
									
									
								}
							} else {
								
								lows.put(currentLowTs, marketData);
								marketData = new MarketData();
								marketData.setLow(low);
								marketData.setHigh(high);
								marketData.setHlc3(hlc3);
								marketData.setEma100(ema100);
								marketData.setEma180(ema180);
								marketData.setEma390(ema390);
								marketData.setEma720(ema720);
								marketData.setEma1560(ema1560);
								marketData.setEma4320(ema4320);
								marketData.setWt1(wt1);
								marketData.setWt2(wt2);
								marketData.setTs(now);
							}
							
							
						}
						currentLow = null;
						currentLowTs = null;
						currentHigh = high;
						currentHighTs = now;
						positive = true;
					}

				}


			}
		}
		previousValue = treshold;

	}
	
	
	

	
	public Boolean isPossibleExtreme() {
		if (positive == null) return null;
		if (previousValue == null) return null;
		
		Boolean retval = null;
		
		if (positive) {
			
			
			if (previousValue.divide(currentHigh).compareTo(Indicators.SEVENTY_PERCENT) > 0) {
				retval = true;
			}
			
		} else {
			
			
			if (previousValue.divide(currentLow).compareTo(Indicators.SEVENTY_PERCENT) > 0) {
				retval = false;
			}
		}
		return retval;
	}
	
	
	
	
	
	
	
	
	
}
