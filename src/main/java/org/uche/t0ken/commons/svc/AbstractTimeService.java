package org.uche.t0ken.commons.svc;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.graalvm.collections.Pair;
import org.jboss.logging.Logger;
import org.uche.t0ken.commons.enums.StatGranularity;


public abstract class AbstractTimeService implements TimeInterface {

	
	
	private final Logger logger = Logger.getLogger("AbstractTimeService");
	
	
	public StatGranularity getNextSg(StatGranularity from, StatGranularity to,
			StatGranularity current, boolean increase, boolean live) {
		if (live) {
			current = getNextLiveGranularity(from, to,  current, increase);
		} else {
			current = getNextSimulatorGranularity(from, to, current, increase);
		}
		return current;
	}
	

	//private static String displayTitle = null;
	
	public Duration getDuration(StatGranularity statGranularity) {
		return Duration.of(statGranularity.getIndex(),  ChronoUnit.SECONDS);

	}
	
	public Instant getTimeWithOffset(StatGranularity granularity, Instant currentTime, int units) {
		
		Instant time = currentTime.plus(units * granularity.getIndex(),ChronoUnit.SECONDS); 
		
		
		return time;
		
	}
	
	public Duration getDurationOffsetForGranularity(StatGranularity statGranularity, Integer maxUnits) {
		
		return getDuration(statGranularity).multipliedBy(-maxUnits);
		
	}
	
	
	public Instant getS(StatGranularity granularity, Instant ts) {
		
		
		return this.truncateToSeconds(ts, granularity.getIndex());
		
		
	}
	
	
	public Instant truncateToSeconds(Instant ts, long seconds) {
		long epocSeconds = ts.getEpochSecond();
		long result = (epocSeconds / seconds) * seconds;
		return Instant.ofEpochSecond(result);
	}
	
	
	
	
	public Instant truncateToDays(Instant ts, long days) {
		long epocSeconds = ts.getEpochSecond();
		long secondsPer2Days = 3600l*24l*days;
		long result = (epocSeconds / secondsPer2Days) * secondsPer2Days;
		return Instant.ofEpochSecond(result);
	}

	public Instant truncateToMinutes(Instant ts, long minutes) {
		long epocSeconds = ts.getEpochSecond();
		long secondsPerNMinutes = 60l*minutes;
		long result = (epocSeconds / secondsPerNMinutes) * secondsPerNMinutes;
		return Instant.ofEpochSecond(result);
	}

	
	private List<StatGranularity> cachedReversedStatGranularities = null;
	public List<StatGranularity> getCachedReversedStatGranularities() {
		
		if (cachedReversedStatGranularities == null) {
			cachedReversedStatGranularities = calcReversedStatGranularities(getStatGranularities());
		}
		
		return cachedReversedStatGranularities;
	}
	
	
	private List<StatGranularity> cachedReversedLiveGranularities = null;
	public List<StatGranularity> getCachedReversedLiveGranularities() {
		
		if (cachedReversedLiveGranularities == null) {
			cachedReversedLiveGranularities = calcReversedLiveGranularities(getLiveGranularities());
		}
		
		return cachedReversedLiveGranularities;
	}
	
	private List<StatGranularity> cachedLiveGranularities = null;
	private Long cachedLiveGranularitiesLastUpdate = null;
	public List<StatGranularity> getCachedLiveGranularities() {
		
		if ((cachedLiveGranularitiesLastUpdate == null) || (cachedLiveGranularitiesLastUpdate + 300000l < System.currentTimeMillis())) {
			cachedLiveGranularities = getLiveGranularities();
			cachedLiveGranularitiesLastUpdate = System.currentTimeMillis();
		}
		
		return cachedLiveGranularities;
	}
	
	
	
	private List<StatGranularity> cachedReversedSimulatorGranularities = null;
	public List<StatGranularity> getCachedReversedSimulatorGranularities() {
		
		if (cachedReversedSimulatorGranularities == null) {
			cachedReversedSimulatorGranularities = calcReversedSimulatorGranularities(getSimulatorGranularities());
		}
		
		return cachedReversedSimulatorGranularities;
	}
	
	private List<StatGranularity> cachedSimulatorGranularities = null;
	public List<StatGranularity> getCachedSimulatorGranularities() {
		
		if (cachedSimulatorGranularities == null) {
			cachedSimulatorGranularities = getSimulatorGranularities();
		}
		
		return cachedSimulatorGranularities;
	}
	
	
	private List<StatGranularity> calcReversedStatGranularities(List<StatGranularity> statGranularities) {
			
			List<StatGranularity> retval = new ArrayList<StatGranularity>();
			retval.addAll(statGranularities);
			Collections.reverse(retval);
			
			return retval;
	}
	
	private List<StatGranularity> calcReversedSimulatorGranularities(List<StatGranularity> simulatorGranularities) {
		
		List<StatGranularity> retval = new ArrayList<StatGranularity>();
		retval.addAll(simulatorGranularities);
		Collections.reverse(retval);
		
		return retval;
	}

	private List<StatGranularity> calcReversedLiveGranularities(List<StatGranularity> liveGranularities) {
		
		List<StatGranularity> retval = new ArrayList<StatGranularity>();
		retval.addAll(liveGranularities);
		Collections.reverse(retval);
		
		return retval;
	}

	//private HashMap<StatGranularity, List<StatGranularity>> reversedStatGranularitiesLowerThan = new HashMap<StatGranularity, List<StatGranularity>>();
	
	public List<StatGranularity> calcStatGranularitiesLowerThan(StatGranularity lowerThanGranularity) {
		
		List<StatGranularity> retval = new ArrayList<StatGranularity>();
		for (StatGranularity g: getStatGranularities()) {
			if (getDuration(g).compareTo(getDuration(lowerThanGranularity))<0) {
				retval.add(g);
			}
		}
		return retval;
		
	}


	public List<StatGranularity> calcStatGranularitiesHigherOrEqualTo(StatGranularity higherOrEqualThanGranularity) {
	
		List<StatGranularity> retval = new ArrayList<StatGranularity>();
		for (StatGranularity g: getStatGranularities()) {
			if (getDuration(g).compareTo(getDuration(higherOrEqualThanGranularity))>=0) {
				retval.add(g);
			}
		}
		return retval;
		
	}

	public List<StatGranularity> calcStatGranularitiesLowerThanHigherOrEqualTo(StatGranularity lowerThanGranularity, StatGranularity higherOrEqualThanGranularity) {
		
		List<StatGranularity> retval = new ArrayList<StatGranularity>();
		for (StatGranularity g: getStatGranularities()) {
			if ((getDuration(g).compareTo(getDuration(higherOrEqualThanGranularity))>=0)
			&& (getDuration(g).compareTo(getDuration(lowerThanGranularity))<0)
					
					)
			
			{
				retval.add(g);
			}
		}
		return retval;
		
	}

	
	/*
	public StatGranularity getNextTwoFactorGranularity(StatGranularity from, StatGranularity to,
			StatGranularity current, boolean increase) {
		
		if (current == null) return null;
		Duration currentDuration = this.getDuration(current);
		
		StatGranularity newSg = this.getNextLiveGranularity(from, to, current, increase);
		
		while (newSg != null) {
			Duration newSgDuration  = this.getDuration(newSg);
			if (increase) {
				if (newSgDuration.compareTo(currentDuration.multipliedBy(2))>=0) {
					return newSg;
				}
			} else {
				if (newSgDuration.multipliedBy(2).compareTo(currentDuration)<=0) {
					return newSg;
				}
			}
			current = newSg;
			newSg = this.getNextLiveGranularity(from, to, current, increase);
		}
	
		return null;
		
	}*/
	
	/*
	 * Returns next higher or next lower granularity
	 */
	public StatGranularity getNextLiveGranularity(StatGranularity from, StatGranularity to,
			StatGranularity current, boolean increase) {
		
		List<StatGranularity> workList = null;
		StatGranularity found = null;
		StatGranularity next = null;
		
		if (increase) {
			if (current.equals(to)) {
				return null;
			}
			workList = getLiveGranularities();
			for (StatGranularity g: workList) {
				if (found != null) {
					next = g;
					break;
				}
				if (current.equals(g)) {
					found = g;
				}
			}
		} else {
			if (current.equals(from)) {
				return null;
			}
			workList = this.getCachedReversedLiveGranularities();
			for (StatGranularity g: workList) {
				if (found != null) {
					next = g;
					break;
				}
				if (current.equals(g)) {
					found = g;
				}
			}
			
		}
		
		return next;
	}

	
	/*
	 * Returns next higher or next lower granularity
	 */
	public StatGranularity getNextSimulatorGranularity(StatGranularity from, StatGranularity to,
			StatGranularity current, boolean increase) {
		
		List<StatGranularity> workList = null;
		StatGranularity found = null;
		StatGranularity next = null;
		
		if (increase) {
			if (current.equals(to)) {
				return null;
			}
			workList = getSimulatorGranularities();
			for (StatGranularity g: workList) {
				if (found != null) {
					next = g;
					break;
				}
				if (current.equals(g)) {
					found = g;
				}
			}
		} else {
			if (current.equals(from)) {
				return null;
			}
			workList = this.getCachedReversedSimulatorGranularities();
			for (StatGranularity g: workList) {
				if (found != null) {
					next = g;
					break;
				}
				if (current.equals(g)) {
					found = g;
				}
			}
			
		}
		
		return next;
	}
	public StatGranularity getHighestSg(StatGranularity highestSatisfyingPercent,
			StatGranularity highestSatisfyingOver) {
		if (highestSatisfyingPercent != null) {
			if (highestSatisfyingOver != null) {
				if (highestSatisfyingPercent.compareTo(highestSatisfyingOver)>=0) {
					return highestSatisfyingPercent;
				} else {
					return highestSatisfyingOver;
				}
			} else {
				return highestSatisfyingPercent;
			}
		} else {
			if (highestSatisfyingOver != null) {
				return highestSatisfyingOver;
				
			} else {
				return null;
			}
		}
	}
	
	
	public Pair<StatGranularity, StatGranularity>  multiplyDoubleSg(StatGranularity sg, double div, boolean live) {
		if (div<=1) {
			return Pair.create(sg, sg);
		}
		
		int seconds = (int)(((double)sg.getIndex()) * div);
		
		StatGranularity low = null;
		StatGranularity high = null;
		
		List<StatGranularity> allSgs = null;
		if (live) {
			allSgs = this.getCachedLiveGranularities();
		} else {
			allSgs = this.getCachedSimulatorGranularities();
		}
 		
		for (StatGranularity current: allSgs) {
			
			
			if (current.getIndex() == seconds) {
			//	logger.info("divideDoubleSg: sg: " + sg + " div: " + div + " wish seconds: " + seconds + " found: " + current);
				return Pair.create(current, current);
			} else if (current.getIndex() < seconds) {
			//	logger.info("divideDoubleSg: sg: " + sg + " div: " + div + " wish seconds: " + seconds + " low: " + current);

				low = current;
			} else if (current.getIndex() > seconds) {
			//	logger.info("divideDoubleSg: sg: " + sg + " div: " + div + " wish seconds: " + seconds + " high: " + current);

				high = current;
				break;
			}
			if (high != null) break;
		}
		
		return Pair.create(low, high);
	}
	
	
	public Pair<StatGranularity, StatGranularity>  divideDoubleSg(StatGranularity sg, double div, boolean live) {
		if (div<=1) {
			return Pair.create(sg, sg);
		}
		int seconds = (int)(((double)sg.getIndex()) / div);
		//logger.info("divideDoubleSg: sg: " + sg + " div: " + div + " wish seconds: " + seconds);
		StatGranularity low = null;
		StatGranularity high = null;
		
		List<StatGranularity> allSgs = null;
		if (live) {
			allSgs = this.getCachedLiveGranularities();
		} else {
			allSgs = this.getCachedSimulatorGranularities();
		}
 		
		for (StatGranularity current: allSgs) {
			
			
			if (current.getIndex() == seconds) {
			//	logger.info("divideDoubleSg: sg: " + sg + " div: " + div + " wish seconds: " + seconds + " found: " + current);
				return Pair.create(current, current);
			} else if (current.getIndex() < seconds) {
			//	logger.info("divideDoubleSg: sg: " + sg + " div: " + div + " wish seconds: " + seconds + " low: " + current);

				low = current;
			} else if (current.getIndex() > seconds) {
			//	logger.info("divideDoubleSg: sg: " + sg + " div: " + div + " wish seconds: " + seconds + " high: " + current);

				high = current;
				break;
			}
			if (high != null) break;
		}
		//logger.info("divideDoubleSg: sg: " + sg + " div: " + div + " wish seconds: " + seconds + " final: low: " + low + " high: " + high);

		/*if (low == null) {
			return Pair.create(high, high);
		}*/
		
		return Pair.create(low, high);
	}
	
	
	
	public Pair<StatGranularity, StatGranularity>  divideSg(StatGranularity sg, int div) {
		int seconds = sg.getIndex() / div;
		
		StatGranularity low = null;
		StatGranularity high = null;
		
		for (StatGranularity current: getStatGranularities()) {
			if (current.getIndex() == seconds) {
				return Pair.create(current, current);
			} else if (current.getIndex() < seconds) {
				low = current;
			} else if (current.getIndex() > seconds) {
				high = current;
				break;
			}
			if (high != null) break;
		}
		
		if (low == null) {
			return Pair.create(high, high);
		}
		
		return Pair.create(low, high);
	}
	
	
	public Pair<StatGranularity, StatGranularity>  divideSgDoubleComplete(StatGranularity sg, double div) {
		int seconds = (int)(((double)sg.getIndex()) / div);
		
		StatGranularity low = null;
		StatGranularity high = null;
		
		for (StatGranularity current: getStatGranularities()) {
			if (current.getIndex() == seconds) {
				return Pair.create(current, current);
			} else if (current.getIndex() < seconds) {
				low = current;
			} else if (current.getIndex() > seconds) {
				high = current;
				break;
			}
			if (high != null) break;
		}
		
		
		
		return Pair.create(low, high);
	}
	
	
	
	public Pair<StatGranularity,StatGranularity>  getNearestSg(long seconds, List<StatGranularity> sgs) {

		
		StatGranularity low = null;
		StatGranularity high = null;
		
		for (StatGranularity current: sgs) {
			if (current.getIndex() == seconds) {
				return Pair.create(current, current);
			} else if (current.getIndex() < seconds) {
				low = current;
			} else if (current.getIndex() > seconds) {
				high = current;
				break;
			}
			if (high != null) break;
		}
		
		return Pair.create(low, high);
	}
	
	
	public StatGranularity getLiveMaxPossibleSg(Instant oldestStatTs, Instant tradeFromTs, int minDepth, List<StatGranularity> allsgs) {
		long seconds = tradeFromTs.getEpochSecond() - oldestStatTs.getEpochSecond();
		seconds = seconds / minDepth;
		Pair<StatGranularity, StatGranularity> sgs = getNearestSg(seconds, allsgs);
		return sgs.getLeft();
		
	}
	
	

	private static StatGranularity statMaxPossibleSg = null;
	
	public StatGranularity getCachedStatMaxPossibleSg(Instant oldestStatTs, Instant tradeFromTs, int minDepth, List<StatGranularity> allsgs) {
		if (statMaxPossibleSg == null) {
			long seconds = tradeFromTs.getEpochSecond() - oldestStatTs.getEpochSecond();
			seconds = seconds / minDepth;
			Pair<StatGranularity, StatGranularity> sgs = getNearestSg(seconds, allsgs);
			statMaxPossibleSg = sgs.getLeft();
		}
		
		return statMaxPossibleSg;
	}
	
	private static StatGranularity simulatorMaxPossibleSg = null;
	
	public StatGranularity getCachedSimulatorMaxPossibleSg(Instant oldestStatTs, Instant tradeFromTs, int minDepth, List<StatGranularity> allsgs) {
		if (simulatorMaxPossibleSg == null) {
			long seconds = tradeFromTs.getEpochSecond() - oldestStatTs.getEpochSecond();
			seconds = seconds / minDepth;
			Pair<StatGranularity, StatGranularity> sgs = getNearestSg(seconds, allsgs);
			simulatorMaxPossibleSg = sgs.getLeft();
		}
		
		return simulatorMaxPossibleSg;
	}
	
	@Override
	public List<StatGranularity> getGranularities(boolean live) {
		if (live) {
			return getLiveGranularities();
		} else {
			return getSimulatorGranularities();
		}
	}
}
