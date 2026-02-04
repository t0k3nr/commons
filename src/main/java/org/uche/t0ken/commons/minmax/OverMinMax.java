package org.uche.t0ken.commons.minmax;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.NavigableMap;

import org.jboss.logging.Logger;
import org.uche.t0ken.commons.enums.StatGranularity;
import org.uche.t0ken.commons.util.Indicators;

public class OverMinMax extends AbstractMinMax implements MinMax {

	/*@ConfigProperty(name = "org.uche.t0ken.stat_granularities")
	List<StatGranularity> statGranularities;*/
	
	
	private static final Logger logger = Logger.getLogger("WtMinMax");
	
	
	private boolean wt1_buy = false;
	private boolean wt1_sell = false;
	
	private Instant lastSeenPositiveTs = null;
	private Instant lastSeenNegativeTs = null;
	
	private BigDecimal highestValueSincePositive = null;
	private BigDecimal lowestValueSinceNegative = null;
	
	private MarketData previousMarketData = null;
	
	/*private Instant lastSwitchedToPositiveTs = null;
	private Instant lastSwitchedToNegativeTs = null;
	*/
	
	
	private Boolean increasing = null;
	private boolean signChange = false;
	private BigDecimal resUnits = Indicators.SIX;
	private boolean debug = false;
	
	private Instant firstValueTs = null;
	
	private Instant dataValidAfterTs = null;
	
	private Integer minPeriodForValidity = null;
	
	
	public OverMinMax(BigDecimal resUnits, boolean debug, String name) {
		this.resUnits = resUnits;
		this.debug = debug;
		this.name = name;
	}
	
	
	public void injectVal(
			BigDecimal target,
			StatGranularity dataSg,
			Instant ts
			) {
		
		if (firstValueTs == null) firstValueTs = ts;
		
		/*if (previousValue == null) {
			previousValue = target;
		}*/
		
		if (previousValue != null) {
			if (previousValue.signum()!=target.signum()) {
				signChange = true;
			} else {
				signChange = false;
			}
		}
		
		value = target;
		
		if (previousValue != null) {
			if (increasing == null) {
				increasing = target.compareTo(previousValue)>0;
				if (increasing) {
					if (debug) logger.info("injectVal: " + name + " comparing currentHigh: " + currentHigh + " target: " + target);
					
					if (currentHigh == null) {
						currentHigh = target;
						currentHighTs = ts;
						// highest high
						marketData.setValue(target);
						marketData.setDataSg(dataSg);
						marketData.setTs(ts);
						marketData.setCreatedTs(ts);
						
					} else if (target.compareTo(currentHigh) > 0) {
						currentHigh = target;
						currentHighTs = ts;
						marketData.setValue(target);
						marketData.setDataSg(dataSg);
						marketData.setTs(ts);
						
					}
					
				} else {
					if (debug) logger.info("injectVal: " + name + " comparing currentLow: " + currentLow + " v: " + target);
					
					if (currentLow == null) {
						currentLow = target;
						currentLowTs = ts;
						marketData.setValue(target);
						marketData.setDataSg(dataSg);
						marketData.setTs(ts);
						marketData.setCreatedTs(ts);
					} else if (target.compareTo(currentLow) < 0) {
						currentLow = target;
						currentLowTs = ts;
						marketData.setValue(target);
						marketData.setDataSg(dataSg);
						marketData.setTs(ts);
						
					}
					
				}
			} else {
				if (increasing == (target.compareTo(previousValue)>0)) {
					if (debug) logger.info("injectVal: " + name + " going same way: increasing: " + increasing + " v.compareTo(previousValue)>0 " + (target.compareTo(previousValue)>0));
					// going the same way, updating extreme
					if (increasing) {
						if (debug) logger.info("injectVal: " + name + " comparing currentHigh: " + currentHigh + " v: " + target);
						if (currentHigh == null) {
							currentHigh = target;
							currentHighTs = ts;
							marketData.setValue(target);
							marketData.setDataSg(dataSg);
							marketData.setTs(ts);
							marketData.setCreatedTs(ts);
						} else if (target.compareTo(currentHigh) > 0) {
							currentHigh = target;
							currentHighTs = ts;
							// highest high
							marketData.setValue(target);
							marketData.setDataSg(dataSg);
							marketData.setTs(ts);
							
						}
					} else {
						if (debug) logger.info("injectVal: " + name + " comparing currentLow: " + currentLow + " v: " + target);
						if (currentLow == null) {
							currentLow = target;
							currentLowTs = ts;
							marketData.setValue(target);
							//marketData.setHighestValue(high);
							marketData.setDataSg(dataSg);
							
							marketData.setTs(ts);
							marketData.setCreatedTs(ts);
						} else if (target.compareTo(currentLow) < 0) {
							currentLow = target;
							currentLowTs = ts;
							// lowest high
							marketData.setValue(target);
							marketData.setDataSg(dataSg);
							marketData.setTs(ts);
							
						}
						
					}
				} else {
					// going opposite way. Check if difference is > than resPercent%.
					 
					
					
					BigDecimal difference = null;
					
					if (increasing) {
						difference = currentHigh.subtract(target);
					} else {
						difference = target.subtract(currentLow);
					}
					//BigDecimal percent = difference.divide(v, Indicators.roundIndicators).abs();
					
					if (debug) logger.info("injectVal: " + name + " going opposite way: " + target + " difference " + difference);
					
					
					if (difference.compareTo(resUnits)<0) {
						// wait
						if (debug) logger.info("injectVal: " + name + " going opposite way: wait");
					} else {
						
						if (increasing) {
							// currentHigh is a HIGH
							//if (debug) logger.info("currentHighTs: " + currentHighTs + " currentHigh: " + currentHigh);
							if (debug) logger.info("injectVal: " + name + " going opposite way: setting high  " + currentHigh + " v: " + target);
							if (currentHigh != null) {
								marketData.setMaximum(true);
								highs.put(marketData.getHighestValueTs(), marketData);
								lowsAndHighs.put(marketData.getHighestValueTs(), marketData);
								previousMarketData = marketData;
								marketData = new MarketData();
								/*if (value.signum()>=0) {
									marketData.setHighestValueSincePositive(previousMarketData.getHighestValueSincePositive());
									marketData.setLastSeenNegativeTs(previousMarketData.getLastSeenNegativeTs());
								} else {
									marketData.setLowestValueSinceNegative(previousMarketData.getLowestValueSinceNegative());
									marketData.setLastSeenPositiveTs(previousMarketData.getLastSeenPositiveTs());
								}*/
								
							}
							currentHigh = null;
							currentHighTs = null;
							currentLow = target;
							currentLowTs = ts;
							increasing = false;
							marketData.setValue(target);
							marketData.setDataSg(dataSg);
							marketData.setTs(ts);
							marketData.setCreatedTs(ts);
							
						} else {
							if (debug) logger.info("injectVal: " + name + " going opposite way: setting low  " + currentLow + " v: " + target);
							
							// currentLow is a low
							if (currentLow != null) {
								marketData.setMaximum(false);
								lows.put(marketData.getLowestValueTs(), marketData);
								lowsAndHighs.put(marketData.getLowestValueTs(), marketData);
								previousMarketData = marketData;
								marketData = new MarketData();
							}
							currentLow = null;
							currentLowTs = null;
							currentHigh = target;
							currentHighTs = ts;
							increasing = true;
							marketData.setValue(target);
							marketData.setDataSg(dataSg);
							marketData.setTs(ts);
							marketData.setCreatedTs(ts);
						}
					}
				}
			}
		}
		
		
		
		
		
		if (marketData.getHighestValue() == null) {
			marketData.setHighestValue(target);
			marketData.setHighestValueTs(ts);
			
		} else if (marketData.getHighestValue().compareTo(target)<0) {
			marketData.setHighestValue(target);
			marketData.setHighestValueTs(ts);
		}
		if (marketData.getLowestValue() == null) {
			marketData.setLowestValue(target);
			marketData.setLowestValueTs(ts);
		} else if (marketData.getLowestValue().compareTo(target)>0) {
			marketData.setLowestValue(target);
			marketData.setLowestValueTs(ts);
		}
		
		
		if (target.signum()>=0) {
			
			if (lastSeenNegativeTs != null) {
				if (lastSeenPositiveTs == null) {
					highestValueSincePositive = value;
				} else if (lastSeenPositiveTs.isBefore(lastSeenNegativeTs)) {
					highestValueSincePositive = value;
				} else {
					if (value.compareTo(highestValueSincePositive)>0) {
						highestValueSincePositive = value;
					}
				}
			} else {
				if (lastSeenPositiveTs == null) {
					highestValueSincePositive = value;
				} else if (value.compareTo(highestValueSincePositive)>0) {
					highestValueSincePositive = value;
				}
			}
			
			lastSeenPositiveTs = ts;
			marketData.setLastSeenPositiveTs(ts);
			marketData.setHighestValueSincePositive(highestValueSincePositive);
			
			

		} else {
			
			if (lastSeenPositiveTs != null) {
				if (lastSeenNegativeTs == null) {
					lowestValueSinceNegative = value;
				} else if (lastSeenNegativeTs.isBefore(lastSeenPositiveTs)) {
					lowestValueSinceNegative = value;
				} else {
					if (value.compareTo(lowestValueSinceNegative)<0) {
						lowestValueSinceNegative = value;
					}
				}
			} else {
				if (lastSeenNegativeTs == null) {
					lowestValueSinceNegative = value;
				} else if (value.compareTo(lowestValueSinceNegative)<0) {
					lowestValueSinceNegative = value;
				}
			}
			lastSeenNegativeTs = ts;
			
			marketData.setLastSeenNegativeTs(ts);
			marketData.setLowestValueSinceNegative(lowestValueSinceNegative);
			
			
		}
		
		/*logger.info(this.getName() + " " + value + " A7 lastSeenNegativeTs: " + lastSeenNegativeTs + " lowestValueSinceNegative: " + lowestValueSinceNegative 
				+ " lastSeenPositiveTs: " + lastSeenPositiveTs + " highestValueSincePositive: " + highestValueSincePositive 
				+ " @" + ts);*/
		
		
		
		
		previousValue = target;
		
	}
	
	
	

	
	public Boolean isPossibleExtreme() {
		if (increasing == null) return null;
		if (previousValue == null) return null;
		
		Boolean retval = null;
		
		if (increasing) {
			
			
			if (previousValue.add(Indicators.THREE).compareTo(currentHigh) < 0) {
				retval = true;
			}
			
		} else {
			
			
			if (previousValue.subtract(Indicators.THREE).compareTo(currentLow) > 0) {
				retval = false;
			}
		}
		return retval;
	}


	public Boolean getIncreasing() {
		return increasing;
	}


	public boolean isWt1_buy() {
		return wt1_buy;
	}


	public void setWt1_buy(boolean wt1_buy) {
		this.wt1_buy = wt1_buy;
	}


	public boolean isWt1_sell() {
		return wt1_sell;
	}


	public void setWt1_sell(boolean wt1_sell) {
		this.wt1_sell = wt1_sell;
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


	public BigDecimal getResUnits() {
		return resUnits;
	}
	
	
	
	
	public boolean solidIncrease(Instant afterTs, BigDecimal noiseFloor) {
		
		if (increasing == null) return false;
		if (!increasing) return false;
		
		BigDecimal low0 = null;
		BigDecimal low1 = null;
		BigDecimal low2 = null;
		BigDecimal high0 = null;
		BigDecimal high1 = null;
		BigDecimal high2 = null;
		
		int i = 0;
		int j = 0;
		
		NavigableMap<Instant, MarketData> data = getLowsAndHighsRemoveNoise(afterTs, noiseFloor);
		if (data != null) {
			for (Instant currentTs: data.descendingKeySet()) {
				MarketData mk = data.get(currentTs);
				
				if (mk.getMaximum()!= null) {
					
					if (mk.getMaximum()) {
						if (j == 0) {
							high0 = mk.getValue();
							j++;
						} else if (j == 1) {
							high1 = mk.getValue();
							j++;
						} else if (j == 2) {
							high2 = mk.getValue();
							j++;
						}
						
					} else {
						if (i==0) {
							low0 = mk.getValue();
							i++;
						} else if (i==1) {
							low1 = mk.getValue();
							i++;
						} else if (i==2) {
							low2 = mk.getValue();
							i++;
						}
					}
					
					
				}
				if ((i == 3) && (j == 3)) {
					break;
				}
				
				
			}
		}
		if (low0 == null) return false;
		if (low1 == null) return false;
		if (high0 == null) return false;
		if (high1 == null) return false;
		
		boolean firstOption = (low0.compareTo(low1)>0) && (high0.compareTo(high1)>0);
		if (firstOption) {
			
			return true;
		}
		if (low2 == null) return false;
		if (high2 == null) return false;
		
		boolean secondOption = (low0.compareTo(low2)>0) && (high0.compareTo(high2)>0);
		
		
		return secondOption;
	}
	
	public boolean solidDecrease(Instant afterTs, BigDecimal noiseFloor) {
		
		if (increasing == null) return false;
		if (increasing) return false;
		
		BigDecimal low0 = null;
		BigDecimal low1 = null;
		BigDecimal low2 = null;
		BigDecimal high0 = null;
		BigDecimal high1 = null;
		BigDecimal high2 = null;
		
		int i = 0;
		int j = 0;
		
		NavigableMap<Instant, MarketData> data = getLowsAndHighsRemoveNoise(afterTs, noiseFloor);
		if (data != null) {
			for (Instant currentTs: data.descendingKeySet()) {
				MarketData mk = data.get(currentTs);
				
				if (mk.getMaximum()!= null) {
					
					if (mk.getMaximum()) {
						if (j == 0) {
							high0 = mk.getValue();
							j++;
						} else if (j == 1) {
							high1 = mk.getValue();
							j++;
						} else if (j == 2) {
							high2 = mk.getValue();
							j++;
						}
						
					} else {
						if (i==0) {
							low0 = mk.getValue();
							i++;
						} else if (i==1) {
							low1 = mk.getValue();
							i++;
						} else if (i==2) {
							low2 = mk.getValue();
							i++;
						}
					}
					
					
				}
				if ((i == 3) && (j == 3)) {
					break;
				}
				
				
			}
		}
		if (low0 == null) return false;
		if (low1 == null) return false;
		if (high0 == null) return false;
		if (high1 == null) return false;
		
		boolean firstOption = (low0.compareTo(low1)<0) && (high0.compareTo(high1)<0);
		if (firstOption) {
			
			return true;
		}
		if (low2 == null) return false;
		if (high2 == null) return false;
		
		boolean secondOption = (low0.compareTo(low2)<0) && (high0.compareTo(high2)<0);
		
		
		return secondOption;
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


	public MarketData getPreviousMarketData() {
		return previousMarketData;
	}


	public void setPreviousMarketData(MarketData previousMarketData) {
		this.previousMarketData = previousMarketData;
	}


	public boolean hasSignChanged() {
		return signChange;
	}


	public Instant getFirstValueTs() {
		return firstValueTs;
	}


	
	
}
