package org.uche.t0ken.commons.minmax;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.NavigableMap;

import org.jboss.logging.Logger;
import org.uche.t0ken.commons.enums.StatGranularity;
import org.uche.t0ken.commons.util.Indicators;

public class WtMinMax extends AbstractMinMax implements MinMax {

	/*@ConfigProperty(name = "org.uche.t0ken.stat_granularities")
	List<StatGranularity> statGranularities;*/
	
	
	private static final Logger logger = Logger.getLogger("WtMinMax");
	
	private StatGranularity sg;
	private WtType type;
	
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
	
	public Instant getDataValidAfterTs() {
		
		if ((dataValidAfterTs==null) && (firstValueTs != null) && (sg != null) ) {
			
			dataValidAfterTs = firstValueTs.plusSeconds(sg.getIndex().longValue() * minPeriodForValidity);
			
		}
		
		
		return dataValidAfterTs;
	}
	
	public WtMinMax(BigDecimal resUnits, boolean debug, String name, StatGranularity sg, WtType type, Integer minPeriodForValidity) {
		this.resUnits = resUnits;
		this.debug = debug;
		this.name = name;
		this.type=type;
		this.sg=sg;
		this.minPeriodForValidity = minPeriodForValidity;
	}
	
	
	public void injectVal(
			BigDecimal target,
			BigDecimal wt1, 
			BigDecimal wt2, 
			BigDecimal low, 
			BigDecimal high, 
			BigDecimal hlc3,
			Instant ts
			) {
		
		if (firstValueTs == null) firstValueTs = ts;
		
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
						marketData.setLow(low);
						marketData.setHigh(high);
						marketData.setHlc3(hlc3);
						marketData.setWt1(wt1);
						marketData.setWt2(wt2);
						marketData.setTs(ts);
						marketData.setCreatedTs(ts);
						
					} else if (target.compareTo(currentHigh) > 0) {
						currentHigh = target;
						currentHighTs = ts;
						marketData.setValue(target);
						marketData.setLow(low);
						marketData.setHigh(high);
						marketData.setHlc3(hlc3);
						marketData.setWt1(wt1);
						marketData.setWt2(wt2);
						marketData.setTs(ts);
						
					}
					
				} else {
					if (debug) logger.info("injectVal: " + name + " comparing currentLow: " + currentLow + " v: " + target);
					
					if (currentLow == null) {
						currentLow = target;
						currentLowTs = ts;
						marketData.setValue(target);
						marketData.setLow(low);
						marketData.setHigh(high);
						marketData.setHlc3(hlc3);
						marketData.setWt1(wt1);
						marketData.setWt2(wt2);
						marketData.setTs(ts);
						marketData.setCreatedTs(ts);
					} else if (target.compareTo(currentLow) < 0) {
						currentLow = target;
						currentLowTs = ts;
						marketData.setValue(target);
						marketData.setLow(low);
						marketData.setHigh(high);
						marketData.setHlc3(hlc3);
						marketData.setWt1(wt1);
						marketData.setWt2(wt2);
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
							marketData.setLow(low);
							marketData.setHigh(high);
							marketData.setHlc3(hlc3);
							marketData.setWt1(wt1);
							marketData.setWt2(wt2);
							marketData.setTs(ts);
							marketData.setCreatedTs(ts);
						} else if (target.compareTo(currentHigh) > 0) {
							currentHigh = target;
							currentHighTs = ts;
							// highest high
							marketData.setValue(target);
							marketData.setLow(low);
							marketData.setHigh(high);
							marketData.setHlc3(hlc3);
							marketData.setWt1(wt1);
							marketData.setWt2(wt2);
							marketData.setTs(ts);
							
						}
					} else {
						if (debug) logger.info("injectVal: " + name + " comparing currentLow: " + currentLow + " v: " + target);
						if (currentLow == null) {
							currentLow = target;
							currentLowTs = ts;
							marketData.setValue(target);
							//marketData.setHighestValue(high);
							marketData.setLow(low);
							marketData.setHigh(high);
							marketData.setHlc3(hlc3);
							marketData.setWt1(wt1);
							marketData.setWt2(wt2);
							
							marketData.setTs(ts);
							marketData.setCreatedTs(ts);
						} else if (target.compareTo(currentLow) < 0) {
							currentLow = target;
							currentLowTs = ts;
							// lowest high
							marketData.setValue(target);
							marketData.setLow(low);
							marketData.setHigh(high);
							marketData.setHlc3(hlc3);
							marketData.setWt1(wt1);
							marketData.setWt2(wt2);
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
							marketData.setLow(low);
							marketData.setHigh(high);
							marketData.setHlc3(hlc3);
							marketData.setWt1(wt1);
							marketData.setWt2(wt2);
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
							marketData.setLow(low);
							marketData.setHigh(high);
							marketData.setHlc3(hlc3);
							marketData.setWt1(wt1);
							marketData.setWt2(wt2);
							marketData.setTs(ts);
							marketData.setCreatedTs(ts);
						}
					}
				}
			}
		}
		
		
		if (marketData.getHighestHlc3() == null) {
			marketData.setHighestHlc3(hlc3);
		} else {
			if (marketData.getHighestHlc3().compareTo(hlc3)<0) {
				marketData.setHighestHlc3(hlc3);
			}
		}
		
		
		
		if (marketData.getLowestHlc3() == null) {
			marketData.setLowestHlc3(hlc3);
		} else {
			if (marketData.getLowestHlc3().compareTo(hlc3)>0) {
				marketData.setLowestHlc3(hlc3);
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
		
		BigDecimal wt = wt1.subtract(wt2);
		if (marketData.getLowestWt() == null) {
			marketData.setLowestWt(wt);
			marketData.setLowestWtTs(ts);
		}
		else if (wt.compareTo(marketData.getLowestWt())<0) {
			marketData.setLowestWt(wt);
			marketData.setLowestWtTs(ts);
		}
		
		if (marketData.getHighestWt() == null) {
			marketData.setHighestWt(wt);
			marketData.setHighestWtTs(ts);
		}
		else if (wt.compareTo(marketData.getHighestWt())>0) {
			marketData.setHighestWt(wt);
			marketData.setHighestWtTs(ts);
		}
		
		if (marketData.getLowestWt1() == null) {
			marketData.setLowestWt1(wt1);
			marketData.setLowestWt1Ts(ts);
		}
		else if (wt1.compareTo(marketData.getLowestWt1())<0) {
			marketData.setLowestWt1(wt1);
			marketData.setLowestWt1Ts(ts);
		}
		
		if (marketData.getHighestWt1() == null) {
			marketData.setHighestWt1(wt1);
			marketData.setHighestWt1Ts(ts);
		}
		else if (wt1.compareTo(marketData.getHighestWt1())>0) {
			marketData.setHighestWt1(wt1);
			marketData.setHighestWtTs(ts);
		}
		
		/*logger.info(this.getName() + " " + value + " B4 lastSeenNegativeTs: " + lastSeenNegativeTs + " lowestValueSinceNegative: " + lowestValueSinceNegative 
				+ " lastSeenPositiveTs: " + lastSeenPositiveTs + " highestValueSincePositive: " + highestValueSincePositive 
				+ " @" + ts);*/
		
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
	
	
	
	public RecentPeaks getRecentPeaks(Instant now) {
		
		
		int i = 0;
		
		RecentPeaks rp = new RecentPeaks();
		rp.increasing = getIncreasing();
		NavigableMap<Instant, MarketData> lowHighs = getLowsAndHighs();
		if (getIncreasing() != null) {
			if (getIncreasing()) {
				
				for (Instant currentTs: lowHighs.descendingKeySet()) {
					
					if (currentTs.isAfter(this.getDataValidAfterTs())) {
						MarketData mk = lowHighs.get(currentTs);
						
						switch (i) {
						
						case 0: {
							if (mk.getMaximum()) {
								// we only want ascending WT1, ignore
								break;
							}
							rp.incr_low1 = mk;
							break;
						}
						case 1: {
							rp.incr_high2 = mk;
							break;
							
						}
						case 2: {
							rp.incr_low3 = mk;
							break;
							
						}
						case 3: {
							rp.incr_high4 = mk;
							break;
							
						}
						case 4: {
							rp.incr_low5 = mk;
							break;
							
						}
						case 5: {
							rp.incr_high6 = mk;
							break;
							
						}
						case 6: {
							rp.incr_low7 = mk;
							break;
							
						}
						case 7: {
							rp.incr_high8 = mk;
							break;
							
						}
						default: {
							break;
						}
						}
						i++;
						if (i>7) break;
					} else {
						break;
					}
					
					
				}
				
				/*StringBuffer log = new StringBuffer("recent_peaks: " + sg + " " + type);
				
				if (rp.incr_low1!=null) log.append(" incr_low1: ").append(rp.incr_low1.getLowestValue());
				if (rp.incr_high2!=null) log.append(" incr_high2: ").append(rp.incr_high2.getLowestValue());
				if (rp.incr_low3!=null) log.append(" incr_low3: ").append(rp.incr_low3.getLowestValue());
				if (rp.incr_high4!=null) log.append(" incr_high4: ").append(rp.incr_high4.getLowestValue());
				if (rp.incr_low5!=null) log.append(" incr_low5: ").append(rp.incr_low5.getLowestValue());
				if (rp.incr_high6!=null) log.append(" incr_high6: ").append(rp.incr_high6.getLowestValue());
				if (rp.incr_low7!=null) log.append(" incr_low7: ").append(rp.incr_low7.getLowestValue());
				if (rp.incr_high8!=null) log.append(" incr_high8: ").append(rp.incr_high8.getLowestValue());
				
				log.append(" @").append(now);
				
				logger.info(log.toString());
				*/
				
			} else {
				for (Instant currentTs: lowHighs.descendingKeySet()) {
					
					if (currentTs.isAfter(this.getDataValidAfterTs())) {
						MarketData mk = lowHighs.get(currentTs);
						
						switch (i) {
						
						case 0: {
							if (!mk.getMaximum()) {
								// we only want descending WT1, ignore
								break;
							}
							rp.decr_high1 = mk;
							break;
						}
						case 1: {
							rp.decr_low2 = mk;
							break;
							
						}
						case 2: {
							rp.decr_high3 = mk;
							break;
							
						}
						case 3: {
							rp.decr_low4 = mk;
							break;
							
						}
						case 4: {
							rp.decr_high5 = mk;
							break;
							
						}
						case 5: {
							rp.decr_low6 = mk;
							break;
							
						}
						case 6: {
							rp.decr_high7 = mk;
							break;
							
						}
						case 7: {
							rp.decr_low8 = mk;
							break;
							
						}
						default: {
							
							break;
						}
						}
						i++;
						if (i>7) break;
					} else {
						break;
					}
					
				}	
				

				
				/*StringBuffer log = new StringBuffer("recent_peaks: " + sg + " " + type);
				
				if (rp.decr_high1!=null) log.append(" decr_high1: ").append(rp.decr_high1.getLowestValue());
				if (rp.decr_low2!=null) log.append(" decr_low2: ").append(rp.decr_low2.getLowestValue());
				if (rp.decr_high3!=null) log.append(" decr_high3: ").append(rp.decr_high3.getLowestValue());
				if (rp.decr_low4!=null) log.append(" decr_low4: ").append(rp.decr_low4.getLowestValue());
				if (rp.decr_high5!=null) log.append(" decr_high5: ").append(rp.decr_high5.getLowestValue());
				if (rp.decr_low6!=null) log.append(" decr_low6: ").append(rp.decr_low6.getLowestValue());
				if (rp.decr_high7!=null) log.append(" decr_high7: ").append(rp.decr_high7.getLowestValue());
				if (rp.decr_low8!=null) log.append(" decr_low8: ").append(rp.decr_low8.getLowestValue());
				
				log.append(" @").append(now);
				
				logger.info(log.toString());
				*/
				
				
			}
			
		}
		
		return rp;
	}


	

	public StatGranularity getSg() {
		return sg;
	}


	public void setSg(StatGranularity sg) {
		this.sg = sg;
	}


	public WtType getType() {
		return type;
	}


	public void setType(WtType type) {
		this.type = type;
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
