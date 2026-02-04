package org.uche.t0ken.commons.minmax;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.SortedMap;
import java.util.concurrent.ConcurrentSkipListMap;

import org.graalvm.collections.Pair;
import org.jboss.logging.Logger;


public abstract class AbstractMinMax {

	private static final Logger logger = Logger.getLogger("AbstractMinMax");
	
	
	
	protected NavigableMap<Instant, MarketData> lows = new  ConcurrentSkipListMap<Instant, MarketData>();
	protected NavigableMap<Instant, MarketData> highs = new ConcurrentSkipListMap<Instant, MarketData>();
	protected NavigableMap<Instant, MarketData> lowsAndHighs = new ConcurrentSkipListMap<Instant, MarketData>();
	
	
	protected String name = null;
	
	protected BigDecimal value = null;
	protected BigDecimal previousValue = null;
	
	
	protected BigDecimal currentLow = null;
	protected BigDecimal currentHigh = null;
	protected Instant currentLowTs = null;
	protected Instant currentHighTs = null;
	
	protected MarketData marketData = new MarketData();
	
	
	public Pair<Instant, MarketData> getCurrentLow() {
		return Pair.create(currentLowTs, marketData);
	}
	
	public Pair<Instant, MarketData> getCurrentHigh() {
		return Pair.create(currentHighTs, marketData);
	}
	
	
	public Pair<Instant, MarketData> getPreviousLow() {
		
		if (lows.size()>0) {
			Instant ts = lows.lastKey();
			
			return Pair.create(ts, lows.get(ts));
		}
		return null;
		
	}
	
	public Pair<Instant, MarketData> getPreviousHigh() {
		if (highs.size()>0) {
			Instant ts = highs.lastKey();
			return Pair.create(ts, highs.get(ts));
		}
		return null;
	}
	
	public NavigableMap<Instant, MarketData> getLows() {
		return lows;
	}

	public NavigableMap<Instant, MarketData> getLows(Instant afterTs) {
		return lows.tailMap(afterTs, true);
	}
	
	
	
	public NavigableMap<Instant, MarketData> getLowsLessThan(Instant afterTs, BigDecimal threshold) {
		NavigableMap<Instant, MarketData> map = lows.tailMap(afterTs, true);
		
		NavigableMap<Instant, MarketData> resultMap = new ConcurrentSkipListMap<Instant, MarketData>();
		
		for (Instant current: map.keySet()) {
			MarketData mk = map.get(current);
			if (mk.getValue().compareTo(threshold)<0) {
				resultMap.put(current, mk);
			}
		}
		
		return resultMap;
		
	}
	
	public NavigableMap<Instant, MarketData> getHighsMoreThan(Instant afterTs, BigDecimal threshold) {
		NavigableMap<Instant, MarketData> map = highs.tailMap(afterTs, true);
		
		NavigableMap<Instant, MarketData> resultMap = new ConcurrentSkipListMap<Instant, MarketData>();
		
		for (Instant current: map.keySet()) {
			MarketData mk = map.get(current);
			if (mk.getValue().compareTo(threshold)>0) {
				resultMap.put(current, mk);
			}
		}
		
		return resultMap;
		
	}
	
	
	
	public MarketData getLowest(Instant afterTs) {
		NavigableMap<Instant, MarketData> l = lows.tailMap(afterTs, true);
		MarketData lowest = null;
		if (l != null) {
			for (MarketData current: lows.values()) {
				if (lowest == null) {
					lowest = current;
				} else {
					if (current.getValue().compareTo(lowest.getValue())<0) {
						lowest = current;
					}
				}
			}
		}
		return lowest;
	}
	
	public int countLowerThan(Instant afterTs, BigDecimal value) {
		NavigableMap<Instant, MarketData> l = lows.tailMap(afterTs, true);
		int count = 0;
		if (l != null) {
			for (MarketData current: lows.values()) {
				
					if (current.getValue().compareTo(value)<0) {
						count++;
					}
				
			}
		}
		return count;
	}
	
	public int countHigherThan(Instant afterTs, BigDecimal value) {
		NavigableMap<Instant, MarketData> l = highs.tailMap(afterTs, true);
		int count = 0;
		if (l != null) {
			for (MarketData current: highs.values()) {
				
					if (current.getValue().compareTo(value)>0) {
						count++;
					}
				
			}
		}
		return count;
	}
	
	
	
	public MarketData getHighest(Instant afterTs) {
		NavigableMap<Instant, MarketData> l = highs.tailMap(afterTs, true);
		MarketData highest = null;
		if (l != null) {
			for (MarketData current: highs.values()) {
				if (highest == null) {
					highest = current;
				} else {
					if (current.getValue().compareTo(highest.getValue())>0) {
						highest = current;
					}
				}
			}
		}
		return highest;
	}
	
	
	public NavigableMap<Instant, MarketData> getLowsAndHighs(Instant afterTs) {
		return lowsAndHighs.tailMap(afterTs, true);
	}

	public NavigableMap<Instant, MarketData> getLowsAndHighs() {
		return lowsAndHighs;
	}

	
	public int countHighsRemoveNoise(Instant afterTs, BigDecimal noiseFloor) {
		int res = 0;
		NavigableMap<Instant, MarketData> data = getLowsAndHighsRemoveNoise(afterTs, noiseFloor);
		if (data != null) {
			for (MarketData mk: data.values()) {
				if ((mk.getMaximum()!= null) && (mk.getMaximum())) {
					res++;
				}
			}
			
		}
		
		return res;
	}
	
	public int countLowsRemoveNoise(Instant afterTs, BigDecimal noiseFloor) {
		int res = 0;
		NavigableMap<Instant, MarketData> data = getLowsAndHighsRemoveNoise(afterTs, noiseFloor);
		if (data != null) {
			for (MarketData mk: data.values()) {
				if ((mk.getMaximum()!= null) && (!mk.getMaximum())) {
					res++;
				}
			}
		}
		
		
		return res;
	}
	
	
	
	
	public boolean lastHighLowerThanPreviousHighRemoveNoise(Instant afterTs, BigDecimal noiseFloor) {
		
		//logger.info("lastHighLowerThanPreviousHighRemoveNoise: afterTs: " + afterTs + " noiseFloor: " + noiseFloor);

		NavigableMap<Instant, MarketData> data = getLowsAndHighsRemoveNoise(afterTs, noiseFloor);
		if (data != null) {
			BigDecimal firstHigh = null;
			Instant firstHighTs = null;
			for (Instant currentTs: data.descendingKeySet()) {
				MarketData mk = data.get(currentTs);
				
				if ((mk.getMaximum()!= null) && (mk.getMaximum())) {
					if (firstHigh == null) {
						firstHigh = mk.getValue();
						firstHighTs = currentTs;
					} else {
						if (mk.getValue().compareTo(firstHigh)>0) {
							//logger.info("lastHighLowerThanPreviousHighRemoveNoise: first: " + firstHigh + "@" + firstHighTs + " last: " + mk.getTarget() + "@" + currentTs + " true");
							return true;
						} else {
							//logger.info("lastHighLowerThanPreviousHighRemoveNoise: first: " + firstHigh + "@" + firstHighTs + " last: " + mk.getTarget() + "@" + currentTs + " false");

							return false;
						}
					}
				}
			}
		}
		//logger.info("lastHighLowerThanPreviousHighRemoveNoise: afterTs: " + afterTs + " noiseFloor: " + noiseFloor + " catchall ");

		return true;
		
	}


	public boolean lastLowHigherThanPreviousLowRemoveNoise(Instant afterTs, BigDecimal noiseFloor) {
		
		NavigableMap<Instant, MarketData> data = getLowsAndHighsRemoveNoise(afterTs, noiseFloor);
		if (data != null) {
			BigDecimal firstLow = null;
			for (Instant currentTs: data.descendingKeySet()) {
				MarketData mk = data.get(currentTs);
				
				if ((mk.getMaximum()!= null) && (!mk.getMaximum())) {
					if (firstLow == null) {
						firstLow = mk.getValue();
					} else {
						if (mk.getValue().compareTo(firstLow)<0) {
							return true;
						} else {
							return false;
						}
					}
				}
			}
		}
		return true;
		
	}
	
	public NavigableMap<Instant, MarketData> getLowsAndHighsRemoveNoise(Instant afterTs, BigDecimal noiseFloor) {
		NavigableMap<Instant, MarketData> tailMap = lowsAndHighs.tailMap(afterTs, true);
		
		NavigableMap<Instant, MarketData> resultMap = new ConcurrentSkipListMap<Instant, MarketData>();
		
		Pair<Instant, MarketData> lastLower = null;
		Pair<Instant, MarketData> lastHigher = null;
		Pair<Instant, MarketData> lowerBackup = null;
		Pair<Instant, MarketData> higherBackup = null;
		
		Boolean lastType = null;
		
		for (Instant currentTs:tailMap.keySet()) {
			
			MarketData currentMk = tailMap.get(currentTs);
			
			
			if (currentMk.getMaximum()) {
				
				//System.out.println("currentMk.isMaximum() " + currentMk.isMaximum());
				if (lastHigher == null) {
					lastHigher = Pair.create(currentTs, currentMk);
					//System.out.println("currentMk.isMaximum() lastHigher == null create pair " + currentTs + " " + currentMk.getTarget());
					
				} else {
					if (currentMk.getValue()
							.compareTo(
									lastHigher
									.getRight()
									.getValue())>0) {
					//System.out.println("currentMk.getTarget() " + currentMk.getTarget() + " higher than lastHigeher " + currentMk.getTarget());
						lastHigher = Pair.create(currentTs, currentMk);
						
					}
				}
				
				if (lastLower != null) {
					//System.out.println("currentMk.isMaximum() lastLower != null lastLower: " + lastLower.getRight().getTarget() + " lastHigher: " + lastHigher.getRight().getTarget());
					
					// current high and lastLower is not null
					if (lastLower.getRight().getValue().add(noiseFloor).compareTo(lastHigher.getRight().getValue())<0) {
						
						//System.out.println("currentMk.isMaximum() putting in map " + lastLower.getRight().getTarget() + " lastLower.getRight().getTarget().add(noiseLevel).compareTo(lastHigher.getRight().getTarget())<0 lastLower != null lastLower: " + lastLower.getRight().getTarget() + " lastHigher: " + lastHigher.getRight().getTarget());
						
						
						// more than noiseLevel difference with current high and lastLower. Adding lastLower
						resultMap.put(lastLower.getLeft(), lastLower.getRight());
						//System.out.println("currentMk.isMaximum() lastLower set to null");
						
						lastLower = null;
						lowerBackup = null;
					} else {
						// high not higher than lastLower + noise level, wait
						
						//System.out.println("currentMk.isMaximum() not higher than noiseLevel wait ");
						
						
						lowerBackup = lastLower;
						//lastLower = null;
						lastHigher = null;
					}
				}
				
			} else {
				//System.out.println("!currentMk.isMaximum() " + currentMk.isMaximum());
				
				if (lastLower == null) {
					lastLower = Pair.create(currentTs, currentMk);
					//System.out.println("!currentMk.isMaximum() lastLower == null create pair " + currentTs + " " + currentMk.getTarget());
					
				} else {
					if (currentMk.getValue().compareTo(lastLower.getRight().getValue())<0) {
						lastLower = Pair.create(currentTs, currentMk);
						//System.out.println("!currentMk.getTarget() " + currentMk.getTarget() + " lower than lastLower " + currentMk.getTarget());
					}
				}
				if (lastHigher != null) {
					//System.out.println("!currentMk.isMaximum() lastHigher != null lastHigher: " + lastHigher.getRight().getTarget() + " lastLower: " + lastLower.getRight().getTarget());
					
					if (lastHigher.getRight().getValue().subtract(noiseFloor).compareTo(lastLower.getRight().getValue())>0) {
						
						//System.out.println("!currentMk.isMaximum() putting in map " + lastHigher.getRight().getTarget() + " lastHigher.getRight().getTarget().subtract(noiseLevel).compareTo(lastLower.getRight().getTarget())>0 lastHigher != null lastHigher: " + lastHigher.getRight().getTarget() + " lastLower: " + lastLower.getRight().getTarget());
						
						resultMap.put(lastHigher.getLeft(), lastHigher.getRight());
						//System.out.println("!currentMk.isMaximum() lastHigher set to null");
						
						lastHigher = null;
						higherBackup = null;
					} else {
						//System.out.println("!currentMk.isMaximum() not lower than noiseLevel wait ");
						
						higherBackup = lastHigher;
						//lastHigher = null;
						lastLower = null;
					}
				}
				
				
			}
		}
		
		
		if (lastLower != null) {
			resultMap.put(lastLower.getLeft(), lastLower.getRight());
			
		} else if (lowerBackup != null) {
			resultMap.put(lowerBackup.getLeft(), lowerBackup.getRight());
			
		}
		if (lastHigher != null) {
			resultMap.put(lastHigher.getLeft(), lastHigher.getRight());
			
		} else if (higherBackup != null) {
			resultMap.put(higherBackup.getLeft(), higherBackup.getRight());
		}
		return resultMap;
		
	}

	
	
	
	public NavigableMap<Instant, MarketData> getHighs(Instant afterTs) {
		return highs.tailMap(afterTs, true);
	}

	
	public boolean isBestLow(Instant afterTs, BigDecimal lowValue) {
		NavigableMap<Instant, MarketData> lastLows = lows.tailMap(afterTs, true);
		for (MarketData current: lastLows.values()) {
			if (current.getLow().compareTo(lowValue) < 0) {
				return false;
			}
		}
		return true;
	}

	public boolean isBestHigh(Instant afterTs, BigDecimal highValue) {
		NavigableMap<Instant, MarketData> lastHighs = highs.tailMap(afterTs, true);
		for (MarketData current: lastHighs.values()) {
			if (current.getHigh().compareTo(highValue) > 0) {
				return false;
			}
		}
		return true;
	}

	public boolean isHigherHlc3(Instant afterTs, BigDecimal hlc3Value) {
		NavigableMap<Instant, MarketData> lastHighs = highs.tailMap(afterTs, true);
		for (MarketData current: lastHighs.values()) {
			if (current.getHlc3().compareTo(hlc3Value) > 0) {
				return false;
			}
		}
		return true;
	}
	
	public boolean isLowerHlc3(Instant afterTs, BigDecimal hlc3Value) {
		NavigableMap<Instant, MarketData> lastLows = lows.tailMap(afterTs, true);
		for (MarketData current: lastLows.values()) {
			if (current.getHlc3().compareTo(hlc3Value) < 0) {
				return false;
			}
		}
		return true;
	}
	
	
	
	public NavigableMap<Instant, MarketData> getHighs() {
		return highs;
	}

	
	public void resetV2(Instant now) {
		
		SortedMap<Instant, MarketData> map = lows.headMap(now.minus(Duration.ofDays(10)));
		for (Instant ts: map.keySet()) {
			lows.remove(ts);
		}
		
		
		
		map = highs.headMap(now.minus(Duration.ofDays(10)));
		for (Instant ts: map.keySet()) {
			highs.remove(ts);
		}
		
		map = lowsAndHighs.headMap(now.minus(Duration.ofDays(10)));
		for (Instant ts: map.keySet()) {
			highs.remove(ts);
		}
		
		
	}
	
	public String getLowDisplayString() {
		String res = "minMax " + name + ": lows: ";
		for (Instant ts:lows.keySet()) {
			res = res + ts + ": " + lows.get(ts) + " ";
		}
		return res;
	}
	
	public String getHighDisplayString() {
		String res =  "minMax " + name +  ": highs: ";
		for (Instant ts:highs.keySet()) {
			res = res + ts + ": " + highs.get(ts) + " ";
		}
		return res;
	}

	public BigDecimal getPreviousValue() {
		return previousValue;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public MarketData getMarketData() {
		return marketData;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}
	
	public Boolean currentValueHigherThanPreviousLow() {
		if (value == null) return null;
		if (lows == null) return null;
		if (lows.size() == 0) return null;
		if (lows.lastEntry().getValue().getValue().compareTo(value)<0) return true;
		return false;
	}
	
	public Boolean currentValueLowerThanPreviousLow() {
		if (value == null) return null;
		if (lows == null) return null;
		if (lows.size() == 0) return null;
		if (lows.lastEntry().getValue().getValue().compareTo(value)>0) return true;
		return false;
	}
	
	public Boolean currentValueHigherThanPreviousHigh() {
		if (value == null) return null;
		if (highs == null) return null;
		if (highs.size() == 0) return null;
		if (highs.lastEntry().getValue().getValue().compareTo(value)<0) return true;
		return false;
	}
	
	public Boolean currentValueLowerThanPreviousHigh() {
		if (value == null) return null;
		if (highs == null) return null;
		if (highs.size() == 0) return null;
		if (highs.lastEntry().getValue().getValue().compareTo(value)>0) return true;
		return false;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public Boolean lastLowHigherThanPreviousLow() {
		if (value == null) return null;
		if (lows == null) return null;
		if (lows.size() <2) return null;
		NavigableSet<Instant> desKeys = lows.descendingKeySet();
		Iterator<Instant> keyIterator = desKeys.iterator();
		if (lows.get(keyIterator.next()).getValue().compareTo(lows.get(keyIterator.next()).getValue())>0) return true;
		return false;
	}
	
	public Boolean lastLowLowerThanPreviousLow() {
		if (value == null) return null;
		if (lows == null) return null;
		if (lows.size() <2) return null;
		NavigableSet<Instant> desKeys = lows.descendingKeySet();
		Iterator<Instant> keyIterator = desKeys.iterator();
		if (lows.get(keyIterator.next()).getValue().compareTo(lows.get(keyIterator.next()).getValue())<0) return true;
		return false;
	}
	
	public Boolean lastHighHigherThanPreviousHigh() {
		if (value == null) return null;
		if (highs == null) return null;
		if (highs.size() <2) return null;
		NavigableSet<Instant> desKeys = highs.descendingKeySet();
		Iterator<Instant> keyIterator = desKeys.iterator();
		if (highs.get(keyIterator.next()).getValue().compareTo(highs.get(keyIterator.next()).getValue())>0) return true;
		return false;
	}
	
	public Boolean lastHighLowerThanPreviousHigh() {
		if (value == null) return null;
		if (highs == null) return null;
		if (highs.size() <2) return null;
		NavigableSet<Instant> desKeys = highs.descendingKeySet();
		Iterator<Instant> keyIterator = desKeys.iterator();
		if (highs.get(keyIterator.next()).getValue().compareTo(highs.get(keyIterator.next()).getValue())<0) return true;
		return false;
	}
	
	
	public Instant getTsOfFifthPeak() {
		int i = 0;
		for (Instant ts: lowsAndHighs.descendingKeySet()) {
			if (i == 5) return ts;
			i++;
		}
		return null;
	}
	
	public boolean previousIsHighSinceTs(Instant ts) {
		Entry<Instant, MarketData> previous = lowsAndHighs.lastEntry();
		if (previous == null) return false;
		if (previous.getValue().getMaximum()) {
			return previous.getKey().isAfter(ts);
		}
		return false;
		
	}
	
	public boolean previousIsLowSinceTs(Instant ts) {
		Entry<Instant, MarketData> previous = lowsAndHighs.lastEntry();
		if (previous == null) return false;
		if (!previous.getValue().getMaximum()) {
			return previous.getKey().isAfter(ts);
		}
		return false;
		
	}
	
}
