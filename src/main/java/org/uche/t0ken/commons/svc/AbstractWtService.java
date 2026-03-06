package org.uche.t0ken.commons.svc;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashMap;

import org.graalvm.collections.Pair;
import org.jboss.logging.Logger;
import org.uche.t0ken.commons.enums.StatGranularity;
import org.uche.t0ken.commons.minmax.MarketData;
import org.uche.t0ken.commons.minmax.WtMinMax;
import org.uche.t0ken.commons.minmax.WtType;
import org.uche.t0ken.commons.util.Indicators;
import org.uche.t0ken.commons.util.SgMove;
import org.uche.t0ken.commons.vo.StatVO;

public class AbstractWtService {

	
	
	
	protected static HashMap<StatGranularity, WtMinMax> wt1Map = new HashMap<StatGranularity, WtMinMax>(StatGranularity.values().length);
	protected static HashMap<StatGranularity, WtMinMax> wtMap = new HashMap<StatGranularity, WtMinMax>(StatGranularity.values().length);
	
	private static final Logger logger = Logger.getLogger("AbstractWtService");

	
	
	protected void init(StatGranularity fromSg, StatGranularity untilSg, Integer minPeriodForValidity) {
		
		StatGranularity[] sgValues = StatGranularity.values();
		
		for (StatGranularity sg: sgValues) {
			if (untilSg != null) {
				if ((sg.compareTo(fromSg)>=0) && (sg.compareTo(untilSg)<=0)) {
					WtMinMax wt1_minMax = new WtMinMax(Indicators.TWELVE, false, "WT1_"+ sg, sg, WtType.WT1, minPeriodForValidity);
					wt1Map.put(sg, wt1_minMax);
					WtMinMax wt_minMax = new WtMinMax(Indicators.THREE, false, "WT_" + sg, sg, WtType.WT, minPeriodForValidity);
					wtMap.put(sg, wt_minMax);
				}
			} else {
				if ((sg.compareTo(fromSg)>=0)) {
					WtMinMax wt1_minMax = new WtMinMax(Indicators.TWELVE, false, "WT1_"+ sg, sg, WtType.WT1, minPeriodForValidity);
					wt1Map.put(sg, wt1_minMax);
					WtMinMax wt_minMax = new WtMinMax(Indicators.THREE, false, "WT_" + sg, sg, WtType.WT, minPeriodForValidity);
					wtMap.put(sg, wt_minMax);
				}
			}
			
			
		}
	}
	
	
	public Integer _getMaxDepth(StatGranularity sg, Integer wishedDepth, Instant ts, Instant candlesFromTs, boolean debug) {
		//ts = timeService.getS(dataGranularity, ts).plus(timeService.getDuration(dataGranularity)).minus(timeService.getDuration(sg));
		long ms = ts.getEpochSecond() - candlesFromTs.getEpochSecond();
		ms = ms / sg.getIndex();
		/*if (debug) {
			logger.info("_getMaxDepth: " + sg + " wishedDepth: " + wishedDepth + " availableDepth: " + ms + " ts: " + ts);
		}*/
		
		if (ms>wishedDepth) {
			return wishedDepth;
		}
		else {
			
			return (int) (ms);
		}
	}
	
	/**
	 * Read-only indicator derivation: computes highestWt, mv, increasingLows, decreasingHighs,
	 * previousHighWasOver, previousLowWasOver from the current WtMinMax state WITHOUT mutating it.
	 * Use this on every match in the live flow. Call full inject() only at candle boundaries.
	 */
	public Instant deriveIndicators(StatVO s, BigDecimal OVERBOUGHT_THRESHOLD, BigDecimal OVERSOLD_THRESHOLD, BigDecimal SELL_WT_THRESHOLD, BigDecimal BUY_WT_THRESHOLD) {

		WtMinMax wt = null;
		synchronized (wtMap) {
			wt = wtMap.get(s.getSg());
		}
		if (wt == null) return null;

		WtMinMax wt1 = null;
		synchronized (wt1Map) {
			wt1 = wt1Map.get(s.getSg());
		}
		if (wt1 == null) return null;

		// --- satisfyHighLow: reads from wt1 peak history (read-only) ---
		Boolean highDecrease = null;
		Boolean lowIncrease = null;
		Instant sinceWhenTs = null;

		lowIncrease = Indicators.satisfyHighLow(
				wt1,
				s.getWt(),
				sinceWhenTs,
				s.getSg(),
				s.getLastMatchTs(), false, false);

		highDecrease = Indicators.satisfyHighLow(
				wt1,
				s.getWt(),
				sinceWhenTs,
				s.getSg(),
				s.getLastMatchTs(), false, true);

		s.setDecreasingHighs(highDecrease);
		s.setIncreasingLows(lowIncrease);

		// --- highestWt: derive from WtMinMax stored peaks + live wt value ---
		BigDecimal currentWt = s.getWt();
		BigDecimal highestWt = null;

		if (currentWt != null) {
			Instant lastPos = wt.getLastSeenPositiveTs();
			Instant lastNeg = wt.getLastSeenNegativeTs();

			if (currentWt.signum() >= 0) {
				BigDecimal stored = wt.getHighestValueSincePositive();
				// Check if last inject was in a negative phase (sign changed since then)
				if (lastNeg != null && (lastPos == null || lastPos.isBefore(lastNeg))) {
					// Sign changed from negative to positive since last inject
					highestWt = currentWt;
				} else {
					// Same positive phase
					if (stored != null && stored.compareTo(currentWt) > 0) {
						highestWt = stored;
					} else {
						highestWt = currentWt;
					}
				}
			} else {
				BigDecimal stored = wt.getLowestValueSinceNegative();
				// Check if last inject was in a positive phase (sign changed since then)
				if (lastPos != null && (lastNeg == null || lastNeg.isBefore(lastPos))) {
					// Sign changed from positive to negative since last inject
					highestWt = currentWt;
				} else {
					// Same negative phase
					if (stored != null && stored.compareTo(currentWt) < 0) {
						highestWt = stored;
					} else {
						highestWt = currentWt;
					}
				}
			}
		}

		s.setHighestWt(highestWt);

		if (s.getDiff() != null && currentWt != null) {
			Pair<SgMove, Boolean> mv = Indicators.getSgMove(currentWt, highestWt, s.getWt1(), s.getDiff(), s.getLastMatchTs());
			s.setMv(mv.getLeft());
			s.setDivergence(mv.getRight());
		}

		// --- previousHighWasOver / previousLowWasOver: reads from wt1 state (read-only) ---
		Boolean overLow = null;
		Boolean overHigh = null;

		if (wt1.getIncreasing() != null) {
			if (wt1.getIncreasing()) {
				if (wt1.getPreviousLow() != null) {
					MarketData previousLow = wt1.getPreviousLow().getRight();
					if (previousLow != null) {
						BigDecimal previousLowWt1 = previousLow.getLowestWt1();
						BigDecimal previousLowLowestWt = previousLow.getLowestWt();
						if ((previousLowWt1 != null) && (previousLowLowestWt != null) && (BUY_WT_THRESHOLD != null) && (OVERSOLD_THRESHOLD != null)) {
							if ((previousLowLowestWt.compareTo(BUY_WT_THRESHOLD) <0) && (previousLowWt1.compareTo(OVERSOLD_THRESHOLD)<=0)) {
								overLow = true;
							}
						}
					}
				}
			} else {
				if (wt1.getPreviousHigh() != null) {
					MarketData previousHigh = wt1.getPreviousHigh().getRight();
					if (previousHigh != null) {
						BigDecimal previousHighWt1 = previousHigh.getHighestWt1();
						BigDecimal previousHighHighestWt = previousHigh.getHighestWt();
						if ((previousHighWt1 != null) && (previousHighHighestWt != null) && (SELL_WT_THRESHOLD != null) && (OVERBOUGHT_THRESHOLD != null)) {
							if ((previousHighHighestWt.compareTo(SELL_WT_THRESHOLD) >0) && (previousHighWt1.compareTo(OVERBOUGHT_THRESHOLD)>=0)) {
								overHigh = true;
							}
						}
					}
				}
			}
		}

		s.setPreviousHighWasOver(overHigh);
		s.setPreviousLowWasOver(overLow);

		s.setLastSeenNegativeTs(wt.getLastSeenNegativeTs());
		s.setLastSeenPositiveTs(wt.getLastSeenPositiveTs());

		return wt.getFirstValueTs();
	}

	public Instant inject(StatVO s, Instant whenTs, BigDecimal OVERBOUGHT_THRESHOLD, BigDecimal OVERSOLD_THRESHOLD, BigDecimal SELL_WT_THRESHOLD, BigDecimal BUY_WT_THRESHOLD) {
		
	
		WtMinMax wt = null;
		
		synchronized (wtMap) {
			wt = wtMap.get(s.getSg());
		}
		
		wt.injectVal(
				s.getWt(),
				s.getWt1(),
				s.getWt2(),
				s.getLow(),
				s.getHigh(),
				s.getHlc3(),
				whenTs // MP match ts
				);
		WtMinMax wt1 = null;
		synchronized (wt1Map) {
			wt1 = wt1Map.get(s.getSg());
		}
		
		
		wt1.injectVal(
				s.getWt1(),
				s.getWt1(),
				s.getWt2(),
				s.getLow(),
				s.getHigh(),
				s.getHlc3(),
				whenTs // MP match ts
				);
		
		Boolean highDecrease = null;
		Boolean lowIncrease = null;
		
		Instant sinceWhenTs = null;
		
		lowIncrease = Indicators.satisfyHighLow(
				wt1, 
				s.getWt(),
				sinceWhenTs,
				s.getSg(), 
				s.getLastMatchTs(), false, false);
		
		highDecrease = Indicators.satisfyHighLow(
				wt1, 
				s.getWt(), 
				sinceWhenTs, 
				s.getSg(), 
				s.getLastMatchTs(), false, true);
		
		s.setDecreasingHighs(highDecrease);
		s.setIncreasingLows(lowIncrease);
		
		BigDecimal highestWt = null;
		
		if (wt.getValue().signum()>=0) {
			highestWt = wt.getHighestValueSincePositive();
		} else {
			highestWt = wt.getLowestValueSinceNegative();
		}
		
		s.setHighestWt(highestWt);
		
		if (s.getDiff() != null) {
			Pair<SgMove, Boolean> mv = Indicators.getSgMove(wt.getValue(), highestWt, s.getWt1(), s.getDiff(), s.getLastMatchTs());
			
			s.setMv(mv.getLeft());
			s.setDivergence(mv.getRight());
			
		}
		
		
		Boolean overLow = null;
		Boolean overHigh = null;
		
		if (wt1.getIncreasing() != null) {
			
			
			if (wt1.getIncreasing()) {
				//logger.info(s.getSg() + ": increasing ");
				if (wt1.getPreviousLow() != null) {
					MarketData previousLow = wt1.getPreviousLow().getRight();
					//logger.info(s.getSg() + ": increasing: got previous low ");
					if (previousLow != null) {
						BigDecimal previousLowWt1 = previousLow.getLowestWt1();
						BigDecimal previousLowLowestWt = previousLow.getLowestWt();
						
						//logger.info(s.getSg() + ": increasing: got previous low: previousLowWt1: " + previousLowWt1 + " previousLowLowestWt: " + previousLowLowestWt + " @mktdata" + previousLow.getTs());
						
						
						if ((previousLowWt1 != null) && (previousLowLowestWt != null) && (BUY_WT_THRESHOLD != null) && (OVERSOLD_THRESHOLD != null)) {
							if ((previousLowLowestWt.compareTo(BUY_WT_THRESHOLD) <0) && (previousLowWt1.compareTo(OVERSOLD_THRESHOLD)<=0)) {
								overLow = true;
								//logger.info(s.getSg() + ": increasing: got previous low: previousLowWt1: " + previousLowWt1 + " previousLowLowestWt: " + previousLowLowestWt + " @mktdata" + previousLow.getTs() + " overlow");
								
							}
						}
					}
					
				}
				
				
				
			} else {
				////logger.info(s.getSg() + ": decreasing ");
				
				if (wt1.getPreviousHigh() != null) {
					MarketData previousHigh = wt1.getPreviousHigh().getRight();
					//logger.info(s.getSg() + ": decreasing: got previous high ");

					if (previousHigh != null) {
						BigDecimal previousHighWt1 = previousHigh.getHighestWt1();
						BigDecimal previousHighHighestWt = previousHigh.getHighestWt();
						//logger.info(s.getSg() + ": decreasing: got previous high: previousHighWt1: " + previousHighWt1 + " previousHighHighestWt: " + previousHighHighestWt + " @mktdata" + previousHigh.getTs());

						if ((previousHighWt1 != null) && (previousHighHighestWt != null) && (SELL_WT_THRESHOLD != null) && (OVERBOUGHT_THRESHOLD != null)) {
							if ((previousHighHighestWt.compareTo(SELL_WT_THRESHOLD) >0) && (previousHighWt1.compareTo(OVERBOUGHT_THRESHOLD)>=0)) {
								overHigh = true;
								//logger.info(s.getSg() + ": decreasing: got previous high: previousHighWt1: " + previousHighWt1 + " previousHighHighestWt: " + previousHighHighestWt + " @mktdata" + previousHigh.getTs() + " overhigh");

							}
						}
					}
				}
				
				
			}
		}
		
		s.setPreviousHighWasOver(overHigh);
		s.setPreviousLowWasOver(overLow);
		
		s.setLastSeenNegativeTs(wt.getLastSeenNegativeTs());
		s.setLastSeenPositiveTs(wt.getLastSeenPositiveTs());
		
		return wt.getFirstValueTs();
		
		
		
	}

	
}
