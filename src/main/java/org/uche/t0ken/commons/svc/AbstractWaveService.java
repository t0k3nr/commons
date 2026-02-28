package org.uche.t0ken.commons.svc;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.graalvm.collections.Pair;
import org.jboss.logging.Logger;
import org.uche.t0ken.api.gdax.Side;
import org.uche.t0ken.commons.enums.Action;
import org.uche.t0ken.commons.enums.StatGranularity;
import org.uche.t0ken.commons.minmax.MarketData;
import org.uche.t0ken.commons.minmax.OverMinMax;
import org.uche.t0ken.commons.svc.WaveInterface;
import org.uche.t0ken.commons.util.Indicators;
import org.uche.t0ken.commons.util.SgMove;
import org.uche.t0ken.commons.util.Wave;
import org.uche.t0ken.commons.util.WaveAlignment;
import org.uche.t0ken.commons.vo.OverState;
import org.uche.t0ken.commons.vo.StatVO;
import org.uche.t0ken.commons.vo.Wv;


public abstract class AbstractWaveService implements WaveInterface {

	private final static Logger logger = Logger.getLogger("AbstractWaveService");

	private BigDecimal RN_XN_THRESHOLD;

	private BigDecimal RP_XP_THRESHOLD;

	private BigDecimal WT1_OVERBOUGHT_THRESHOLD;

	private BigDecimal WT1_OVERSOLD_THRESHOLD;



	public void logMoves(NavigableMap<StatGranularity, StatVO> moves, Instant now) {

		StringBuffer buf = new StringBuffer("moves @").append(now).append("\n");

		for (StatGranularity sg: moves.descendingKeySet()) {
			StatVO mv = moves.get(sg);
			buf.append(mv.toMoveString()).append("\n");
		}
		logger.info(buf);

	}

	
	public boolean _trend_invalid(StatVO trend) {
		switch (trend.getMv()) {
		case XP: {
			if (trend.getDiff().compareTo(Indicators.MINUS_FIVE)<0) {
				return true;
			}
			break;
		}
		case XN: {
			if (trend.getDiff().compareTo(Indicators.FIVE)>0) {
				return true;
			}
			break;
		}
		default: {
			break;
		}
		}
		return false;
	}
	
	/*
	 * if overTrend is LL or HH must be div4 else do not act
	 */
	
	public boolean _signal_AB_blockers(StatGranularity maxSg, NavigableMap<StatGranularity, StatVO> moves, boolean sell, boolean live, Instant now) {
		
		NavigableSet<StatGranularity> div4overs = this._build_over_div4(maxSg, moves.firstKey(), moves, sell, live, null, now);
		
		for (StatGranularity sg: moves.descendingKeySet()) {
			if (sg.compareTo(maxSg)<=0) {
				StatVO mv = moves.get(sg);
				if (sell) {
					if (mv.getDiff().compareTo(Indicators.TEN)>=0) {
						if (!div4overs.contains(sg)) {
							logger.info("_signal_AB_blockers: blocked " + sell + " by " + sg + " diff: " + mv.getDiff());
							return false;
						}
					} else if (mv.wt1Above(Indicators.FOURTY) && (mv.getDiff().compareTo(Indicators.NINE)>=0)) {
						if (!div4overs.contains(sg)) {
							logger.info("_signal_AB_blockers: blocked " + sell + " by " + sg + " diff: " + mv.getDiff());
							return false;
						}
					} else if (mv.wt1Above(Indicators.FOURTY_FIVE) && (mv.getDiff().compareTo(Indicators.EIGHT)>=0)) {
						if (!div4overs.contains(sg)) {
							logger.info("_signal_AB_blockers: blocked " + sell + " by " + sg + " diff: " + mv.getDiff());
							return false;
						}
					} else if (mv.wt1Above(Indicators.FIFTY) && (mv.getDiff().compareTo(Indicators.SEVEN)>=0)) {
						if (!div4overs.contains(sg)) {
							logger.info("_signal_AB_blockers: blocked " + sell + " by " + sg + " diff: " + mv.getDiff());
							return false;
						}
					} else if (mv.wt1Above(Indicators.FIFTY_FIVE) && (mv.getDiff().compareTo(Indicators.SIX)>=0)) {
						if (!div4overs.contains(sg)) {
							logger.info("_signal_AB_blockers: blocked " + sell + " by " + sg + " diff: " + mv.getDiff());
							
						}
						return false;
					} else if (mv.wt1Above(Indicators.SIXTY) && (mv.getDiff().compareTo(Indicators.FIVE)>=0)) {
						if (!div4overs.contains(sg)) {
							logger.info("_signal_AB_blockers: blocked " + sell + " by " + sg + " diff: " + mv.getDiff());
							return false;
						}
						return false;
					}
				} else {
					if (mv.getDiff().compareTo(Indicators.MINUS_TEN)<=0) {
						if (!div4overs.contains(sg)) {
							logger.info("_signal_AB_blockers: blocked " + sell + " by " + sg + " diff: " + mv.getDiff());
							return false;
						}
					} else if (mv.wt1Below(Indicators.MINUS_FOURTY) && (mv.getDiff().compareTo(Indicators.MINUS_NINE)<=0)) {
						if (!div4overs.contains(sg)) {
							logger.info("_signal_AB_blockers: blocked " + sell + " by " + sg + " diff: " + mv.getDiff());
							return false;
						}
					} else if (mv.wt1Below(Indicators.MINUS_FOURTY_FIVE) && (mv.getDiff().compareTo(Indicators.MINUS_EIGHT)<=0)) {
						if (!div4overs.contains(sg)) {
							logger.info("_signal_AB_blockers: blocked " + sell + " by " + sg + " diff: " + mv.getDiff());
							return false;
						}
					} else if (mv.wt1Below(Indicators.MINUS_FIFTY) && (mv.getDiff().compareTo(Indicators.MINUS_SEVEN)<=0)) {
						if (!div4overs.contains(sg)) {
							logger.info("_signal_AB_blockers: blocked " + sell + " by " + sg + " diff: " + mv.getDiff());
							return false;
						}
					} else if (mv.wt1Below(Indicators.MINUS_FIFTY_FIVE) && (mv.getDiff().compareTo(Indicators.MINUS_SIX)<=0)) {
						if (!div4overs.contains(sg)) {
							logger.info("_signal_AB_blockers: blocked " + sell + " by " + sg + " diff: " + mv.getDiff());
							return false;
						}
						return false;
					} else if (mv.wt1Below(Indicators.MINUS_SIXTY) && (mv.getDiff().compareTo(Indicators.MINUS_FIVE)<=0)) {
						if (!div4overs.contains(sg)) {
							logger.info("_signal_AB_blockers: blocked " + sell + " by " + sg + " diff: " + mv.getDiff());
							return false;
						}
						return false;
					}
				}
			} 
		}
		
		return true;
		
	}
	
	
	public boolean _signal_AB_blockers2(StatGranularity maxSg, NavigableMap<StatGranularity, StatVO> moves, boolean sell, boolean live, Instant now) {
		
		//NavigableSet<StatGranularity> div4overs = this._build_over_div4(maxSg, moves.firstKey(), moves, sell, live, null, now);
		
		for (StatGranularity sg: moves.descendingKeySet()) {
			if (sg.compareTo(maxSg)<=0) {
				StatVO mv = moves.get(sg);
				if (sell) {
					
					if (mv.wtAbove(Indicators.TEN)) {
						if ((mv.getDiff().compareTo(Indicators.FIVE)>=0)) {
							if (!mv.getDecreasingHighs()) {
								return false;
							}
							
						}
					}
					
				} else {
					if (mv.wtBelow(Indicators.MINUS_TEN)) {
						if (mv.getDiff().compareTo(Indicators.MINUS_FIVE)<=0) {
							if (!mv.getIncreasingLows()) {
								return false;
							}
							
						} 
					}
					
				}
			} 
		}
		
		return true;
		
	}
	
	public Boolean inject_for_dataset(Instant now, BigDecimal bid, BigDecimal ask,
			NavigableMap<StatGranularity, StatVO> moves, boolean live, Action clue) {
		
		//this.logMoves(moves, now);

		Pair<StatVO, StatVO> wavePair = this._get_trend(null, moves);
		if (wavePair == null) return null;

		//logger.info("inject_for_dataset: clue: " + clue + " @" + now);
		
		if (clue.equals(Action.BUY)) {
			return false;
		} else if (clue.equals(Action.SELL)) {
			return true;
		} 
		/*
		List<StatGranularity> granularitiesToTry = List.of(
				
				StatGranularity.S1050,
				StatGranularity.S1150,
				StatGranularity.S1270,
				StatGranularity.S1390,
				StatGranularity.S1530,
				StatGranularity.S1690,
				StatGranularity.S1850,
				StatGranularity.S2040,
				StatGranularity.S2240,
				StatGranularity.S2470,
				StatGranularity.S2720,
				StatGranularity.S2990,
				StatGranularity.S3290,
				StatGranularity.S3610,
				StatGranularity.S3980
				
				);
		
			switch (clue) {
			case SELL: {
				for (StatGranularity sg: granularitiesToTry) {
					Pair<Boolean, StatGranularity> sellInfo = this._search_aligned(
							wavePair.getLeft().getSg(),
							wavePair.getRight().getSg(),
							sg,
							wavePair.getLeft().getWt().signum()>=0,
							3.5d,
							moves,
							true, 
							live, 
							now);

					if (sellInfo != null) {
						
							logger.info("\n"+  "SIG:FULL:SELL@" + now // +  " sellmul: " + new BigDecimal(sellMul).setScale(2, RoundingMode.HALF_EVEN)
									+ " " + sellInfo.getRight()
									//+ " " + selectedTrend.getRight().toShortMoveString()
									
									);
							
							return sellInfo.getLeft();
							
						
					}
				}
				break;
			}
			case BUY: {
				for (StatGranularity sg: granularitiesToTry) {
					Pair<Boolean, StatGranularity> buyInfo = this._search_aligned(
							wavePair.getLeft().getSg(),
							wavePair.getRight().getSg(),
							sg,
							wavePair.getLeft().getWt().signum()>=0,
							3.5d,
							moves,
							false, 
							live,
							now);
					if (buyInfo != null) {
						
							logger.info("\n"+  "SIG:FULL:BUY@" + now //+  " buymul:" + new BigDecimal(buyMul).setScale(2, RoundingMode.HALF_EVEN)
									+ " "+ buyInfo.getRight()
									//+ " " + selectedTrend.getRight().toShortMoveString()
									
									
									);
						
							
								return buyInfo.getLeft();
						
						
					}
				}
				break;
			}
			case WAIT: {
				break;
			}
			}
		
			
		*/
			
			
			
		
			return null;
	}
	
	
	public Boolean inject(Instant now, BigDecimal bid, BigDecimal ask,
			NavigableMap<StatGranularity, StatVO> moves, boolean live, Boolean clue) {

		
		this.logMoves(moves, now);

		
		
		double maxAlignmentWidth = 2d;
		double defaultSellMul = 4.5d;
		double defaultBuyMul = 4.5d;
		
		
		/*
		 * Build overtrends
		 * 
		 */
		
		
		this._signal_overtrends_build(moves, now, live);
		
		//this._signal_divergents_build(moves, now, live);
		
		List<Wv> wvs = new ArrayList<Wv>();
		Pair<StatVO, StatVO> wavePair = this._get_trend(null, moves);
		if (wavePair == null) return null;

		while (wavePair.getRight() != null) {
			Wv w = new Wv().now(now);
			boolean trendSign = wavePair.getRight().getWt().doubleValue()>=0;
			StatGranularity currentSg = wavePair.getLeft().getSg();
			w.left(wavePair.getLeft()).right(wavePair.getRight());
			
			while (currentSg.compareTo(wavePair.getRight().getSg())>=0) {
				Pair<StatGranularity, StatGranularity> overCurrent = 
						this._signal_overtrends_is_wave_ready_for_opposite_sg(OVERTRENDS_PERCENT, trendSign, currentSg, moves, live, now);
				if (overCurrent != null) {
					if (overCurrent.getRight() == null) {
						w.leftOs(OverState.WAIT);
						break;
					}
				}
				currentSg = currentSg.getPrevious();
			}
			
			StatGranularity found = this._search_next_clean_desc(wavePair, moves.firstKey(), moves, trendSign, live, false, now);
			if (found != null) w.action(moves.get(found));
			
			Pair<StatGranularity, StatGranularity> overFirst = null;
			
			if (found != null) {
				overFirst = 
						this._signal_overtrends_is_wave_ready_for_opposite_sg(OVERTRENDS_PERCENT, trendSign, found, moves, live, now);
				if (overFirst != null) {
					if (overFirst.getRight() != null) {
						w.actionOs(OverState.OK);
					} else {
						w.actionOs(OverState.WAIT);
					}
				} 
				
			} 
			wvs.add(w);
			//waves.add(Pair.create(trendSign, found));
			wavePair = this._get_trend(wavePair.getRight().getSg(), moves);
			if (wavePair == null) break;
			
		}
		
		logger.info(Wv.logWvs(wvs));
		
		Wv previous = null;
		Boolean sell1 = null;
		Boolean sell2 = null;
		Boolean sell3 = null;
		
		List<Wv> wvs1 = new ArrayList<Wv>();
		List<Wv> wvs2 = new ArrayList<Wv>();
		List<Wv> wvs3 = new ArrayList<Wv>();
		
		StatGranularity sellSg = null;
		StatGranularity buySg = null;
		Wv sellWv = null;
		Wv buyWv = null;
		
		
		for (Wv wv: wvs) {
			if (previous == null) {
				previous = wv;
				sell1 = wv.sell();
				wvs1.add(wv);
				// alway try to buy/sell with first wave
				if (sell1) {
					sellSg = (wv.getAction()!=null)?wv.getAction().getSg():null;
					sellWv = wv;
				} else {
					buySg = (wv.getAction()!=null)?wv.getAction().getSg():null;
					buyWv = wv;
				}
			} else {
				if (sell2 == null) {
					if (wv.sell() == sell1) {
						// same sign, do nothing
						wvs1.add(wv);
					} else {
						// opposite sign
						sell2 = wv.sell();
						wvs2.add(wv);
						if (wv.getAction() != null) {
							StatGranularity previousRightSg = previous.getRight().getSg();
							StatGranularity actionSg = wv.getAction().getSg();
							if (previousRightSg.getIndex().doubleValue() / actionSg.getIndex().doubleValue() > 7d) {
								break;
							}
							if (sell2) {
								sellSg = (wv.getAction()!=null)?wv.getAction().getSg():null;
								sellWv = wv;
							} else {
								buySg = (wv.getAction()!=null)?wv.getAction().getSg():null;
								buyWv = wv;
							}
						}// else {
//							break;
						//}
						previous = wv;
					}
				} /*else if (sell3 == null) {
					if (wv.sell() == sell2) {
						// same sign, do nothing
						wvs2.add(wv);
					} else {
						// opposite sign
						
						sell3 = wv.sell();
						
						if ((wvs1.size() == 1) && (wvs2.size() == 1)) {
							if (Wv.widthSum(wvs1.getFirst(), wvs2.getLast())<15d) {
								if (wv.getAction() != null) {
									StatGranularity previousRightSg = previous.getRight().getSg();
									StatGranularity actionSg = wv.getAction().getSg();
									if (previousRightSg.getIndex().doubleValue() / actionSg.getIndex().doubleValue() > 4.2d) {
										break;
									}
									if (sell3) {
										sellSg = (wv.getAction()!=null)?wv.getAction().getSg():null;
									} else {
										buySg = (wv.getAction()!=null)?wv.getAction().getSg():null;
									}
								} else {
									break;
								}
							} else {
								break;
							}
						} else {
							break;
						}
						
						
						
					}
				}*/
				
			}
		}
		
		
		Boolean sellNotBlockedByDivergence = null;
		
		if (sellSg != null) {
			//sellNotBlockedByDivergence = this._signal_AB_blockers(sellSg, moves, true, live, now); //this._signal_divergents_minmax_aligned(moves, true, live, now);
			
		}
		
		
		Boolean buyNotBlockedByDivergence = null;
		
		if (buySg != null) {
			//buyNotBlockedByDivergence = this._signal_AB_blockers(buySg, moves, false, live, now); //this._signal_divergents_minmax_aligned(moves, false, live, now);
		}
		
		
		logger.info("FR: " + now + " sellSg: " + sellSg + " buySg: " + buySg + " sellNotBlockedByDivergence: " + sellNotBlockedByDivergence + " buyNotBlockedByDivergence: " + buyNotBlockedByDivergence) ;
		
		
				if (sellSg != null) {

					Pair<Boolean, StatGranularity> sellInfo = this._search_aligned(
							sellWv.getLeft().getSg(),
							sellWv.getRight().getSg(),
							sellSg,
							sellWv.sell(),
							maxAlignmentWidth,
							moves,
							true, 
							live, 
							now);

					if (sellInfo != null) {
						//if (allowSell) {
						if ( (sellNotBlockedByDivergence==null) || sellNotBlockedByDivergence) {
							logger.info("\n"+  "SIG:FULL:SELL@" + now // +  " sellmul: " + new BigDecimal(sellMul).setScale(2, RoundingMode.HALF_EVEN)
									+ " " + sellSg + "/" + sellInfo.getRight()
									//+ " " + selectedTrend.getRight().toShortMoveString()
									+ (buySg!=null?(" b:" + moves.get(buySg).toShortMoveString()):"")
									+ (sellSg!=null?(" s:" + moves.get(sellSg).toShortMoveString()):"")
									
									);
							
							return sellInfo.getLeft();
							
							
						} else {
							logger.info("\n"+  "SIG:FULL:SELL:BLOCKED:DIV@" + now // +  " sellmul: " + new BigDecimal(sellMul).setScale(2, RoundingMode.HALF_EVEN)
									+ " " + sellSg + "/" + sellInfo.getRight()
									//+ " " + selectedTrend.getRight().toShortMoveString()
									+ (buySg!=null?(" b:" + moves.get(buySg).toShortMoveString()):"")
									+ (sellSg!=null?(" s:" + moves.get(sellSg).toShortMoveString()):"")
									
									);
						}
						
						
							
						
					}
				}
			
				if (buySg != null) {
					
					Pair<Boolean, StatGranularity> buyInfo = this._search_aligned(
							buyWv.getLeft().getSg(),
							buyWv.getRight().getSg(),
							buySg,
							buyWv.sell(),
							maxAlignmentWidth,
							moves,
							false, 
							live,
							now);
					if (buyInfo != null) {
						
						if ((buyNotBlockedByDivergence == null) || buyNotBlockedByDivergence) {
							logger.info("\n"+  "SIG:FULL:BUY@" + now //+  " buymul:" + new BigDecimal(buyMul).setScale(2, RoundingMode.HALF_EVEN)
									+ " " + buySg + "/" + buyInfo.getRight()
									//+ " " + selectedTrend.getRight().toShortMoveString()
									+ (buySg!=null?(" b:" + moves.get(buySg).toShortMoveString()) :"")
									+ (sellSg!=null?(" s:" + moves.get(sellSg).toShortMoveString()):"")
									
									
									);
						
							
								return buyInfo.getLeft();
						} else {
							logger.info("\n"+  "SIG:FULL:BUY:BLOCKED:DIV@" + now //+  " buymul:" + new BigDecimal(buyMul).setScale(2, RoundingMode.HALF_EVEN)
									+ " " + buySg + "/" + buyInfo.getRight()
									//+ " " + selectedTrend.getRight().toShortMoveString()
									+ (buySg!=null?(" b:" + moves.get(buySg).toShortMoveString()) :"")
									+ (sellSg!=null?(" s:" + moves.get(sellSg).toShortMoveString()):"")
									
									
									);
						}
						
						
					}
				}
			
			//}
		
		

		return null;
		
	}

	

	public boolean _2025_sg_same_sign(boolean sign, StatVO mv) {

		switch (mv.getMv()) {
		case ST:
		case SP:
		case SN: {

			return true;
		}

		case AP:
		case BP: 
		case RP:
		case XP:
		case YP:
		case ZP: {
			if (mv.getHighestWt().compareTo(Indicators.SIX)>=0) {
				return (sign == true);
			}
			return false;
		}

		case AN:
		case BN:
		case RN:
		case XN:
		case YN:
		case ZN: {
			if (mv.getHighestWt().compareTo(Indicators.MINUS_SIX)<=0) {
				return (sign == false);
			}
			return false;
		}

		}
		return false;
	}

	public Boolean _sg_get_sign(StatVO mv) {

		switch (mv.getMv()) {
		case ST:
		case SP:
		case SN: {

			return null;
		}

		case AP:
		case BP:
		case RP:
		case XP:
		case YP:
		case ZP: {
			return true;
		}

		case AN:
		case BN:
		case RN:
		case XN:
		case YN:
		case ZN: {
			return false;
		}

		}
		return null;
	}

	private boolean _is_stalled(StatVO mv)  {
		switch (mv.getMv()) {
		case ST:
		case SP:
		case SN: {

			return true;
		}

		/*case AP: {
			if ((mv.getHighestWt().compareTo(Indicators.FOUR) <0) && (mv.getDiff().compareTo(Indicators.FOUR) <0)) {
				return true;
			}
			break;
		}
		case BP: {
			if ((mv.getHighestWt().compareTo(Indicators.FIVE) <0) && (mv.getDiff().compareTo(Indicators.FOUR) <0)) {
				return true;
			}
			break;
		}
		case RP:
		case XP:
		case YP:
		case ZP: {
			if (mv.getHighestWt().compareTo(Indicators.FIVE) <0) {
				return true;
			}
			break;
		}


		case AN: {
			if (mv.getHighestWt().compareTo(Indicators.MINUS_FOUR) >0) {
				return true;
			}
			break;
		}
		case BN: { 
			if (mv.getHighestWt().compareTo(Indicators.MINUS_FIVE) >0) {
				return true;
			}
			break;
		}
		case RN:
		case XN:
		case YN:
		case ZN: {
			if (mv.getHighestWt().compareTo(Indicators.MINUS_FIVE) >0) {
				return true;
			}
			break;
		}*/
		default: {
			break;
		}
		}
		return false;
	}


	


	public Pair<StatVO, StatVO> _get_trend(StatGranularity belowSg, NavigableMap<StatGranularity, StatVO> moves) {

		StatGranularity firstFoundSg = null; 
		StatGranularity foundSg = null; 
		StatVO foundMv = null;
		StatVO firstMv = null;
		Boolean positive = null;
		
		//BigDecimal highestWt = null;

		for (Iterator<StatGranularity> i = moves.descendingKeySet().iterator(); i.hasNext();) {


			StatGranularity s = i.next();

			if ((belowSg == null) || (s.compareTo(belowSg)<0)) {

				StatVO mv = moves.get(s);

				if (foundSg == null) {

					positive = this._sg_get_sign(mv);
					if (positive != null) {
						if (
								
								(!this._is_stalled(mv))
								&& (mv.getHighestWt().abs().compareTo(RP_XP_THRESHOLD)>=0)
								
								) {
							firstFoundSg = s;
							foundSg = s;
							foundMv = mv;
							firstMv = foundMv;
							//highestWt = firstMv.getHighestWt();
						} 

					} 
					/*
					if (firstFoundSg == null) {
						 if (mv.getWt1().compareTo(WT1_OVERBOUGHT_THRESHOLD)>=0) {
								if (this._is_stalled(mv)) {
									positive = true;
									firstFoundSg = s;
									foundSg = s;
									foundMv = mv;
									firstMv = foundMv;
								}
								
								
							} else if (mv.getWt1().compareTo(WT1_OVERSOLD_THRESHOLD)<=0) {
								if (this._is_stalled(mv)) {
									positive = false;
									firstFoundSg = s;
									foundSg = s;
									foundMv = mv;
									firstMv = foundMv;
								}
								
							
								
							}
					}*/
					

				} else {

					boolean found = false;
					
					if (this._sg_get_sign(mv) == positive) {
						// same sign

						if (positive) {
							if (mv.getHighestWt()
									.compareTo(
											foundMv.getHighestWt()
											.multiply(Indicators.PERCENT_80, Indicators.roundTwoIndicators))
									>0
									) {
								foundSg = s;
								foundMv = mv;
								found = true;
								/*if (mv.getHighestWt().compareTo(highestWt)>0) {
									highestWt = mv.getHighestWt();
								}*/
							} else {
								break;
							}
						} else {
							if (mv.getHighestWt()
									.compareTo(
											foundMv.getHighestWt()
											.multiply(Indicators.PERCENT_80, Indicators.roundTwoIndicators))
									<0
									) {
								foundSg = s;
								foundMv = mv;
								found = true;
								/*if (mv.getHighestWt().compareTo(highestWt)<0) {
									highestWt = mv.getHighestWt();
								}*/
							} else {
								break;
							}
						}


					} 
					/*if (! found) {
						if (this._is_stalled(mv)) {
							foundSg = s;
							foundMv = mv;
							found = true;
						}
					}*/
					/*
					if (! found)
					if ((positive) && (mv.getWt1().compareTo(WT1_OVERBOUGHT_THRESHOLD)>=0)) {
						
						if (this._is_stalled(mv)) {
							foundSg = s;
							foundMv = mv;
							found = true;
						}
						
						
					} else if ((!positive) && (mv.getWt1().compareTo(WT1_OVERSOLD_THRESHOLD)<=0)) {
						if (this._is_stalled(mv)) {
							foundSg = s;
							foundMv = mv;
							found = true;
						}
						
						
					}*/
					if (!found) {
						break;
					}


				}
			}




		}

		if (foundSg != null) {

			//double width = firstFoundSg.getIndex().doubleValue() / foundSg.getIndex().doubleValue();
			return Pair.create(firstMv, foundMv);
		}

		return null;
	}

	
	public Pair<StatVO, StatVO> _get_wave(StatGranularity belowSg, NavigableMap<StatGranularity, StatVO> moves) {

		StatGranularity firstFoundSg = null; 
		StatGranularity foundSg = null; 
		StatVO foundMv = null;
		StatVO firstMv = null;
		Boolean positive = null;
		
		//BigDecimal highestWt = null;

		for (Iterator<StatGranularity> i = moves.descendingKeySet().iterator(); i.hasNext();) {


			StatGranularity s = i.next();

			if ((belowSg == null) || (s.compareTo(belowSg)<0)) {

				StatVO mv = moves.get(s);

				if (foundSg == null) {

					positive = this._sg_get_sign(mv);
					if (positive != null) {
						if (
								
								(!this._is_stalled(mv))
								&& (mv.getHighestWt().abs().compareTo(RP_XP_THRESHOLD)>=0)
								
								) {
							firstFoundSg = s;
							foundSg = s;
							foundMv = mv;
							firstMv = foundMv;
							//highestWt = firstMv.getHighestWt();
						} 

					} 
					/*
					if (firstFoundSg == null) {
						 if (mv.getWt1().compareTo(WT1_OVERBOUGHT_THRESHOLD)>=0) {
								if (this._is_stalled(mv)) {
									positive = true;
									firstFoundSg = s;
									foundSg = s;
									foundMv = mv;
									firstMv = foundMv;
								}
								
								
							} else if (mv.getWt1().compareTo(WT1_OVERSOLD_THRESHOLD)<=0) {
								if (this._is_stalled(mv)) {
									positive = false;
									firstFoundSg = s;
									foundSg = s;
									foundMv = mv;
									firstMv = foundMv;
								}
								
							
								
							}
					}*/
					

				} else {

					
					if (this._sg_get_sign(mv) == positive) {
						// same sign

						foundSg = s;
						foundMv = mv;
						
					} else {
						break;
					}
					


				}
			}




		}

		if (foundSg != null) {

			//double width = firstFoundSg.getIndex().doubleValue() / foundSg.getIndex().doubleValue();
			return Pair.create(firstMv, foundMv);
		}

		return null;
	}


	/*
	 * TODO
	 * 
	 * SIGNAL:BUY@2021-12-03T23:16:00Z
	 * 
	 * 
	 */
	
	
	


	

	/* 
	 * 
	 * 20250202 
	 * 
	 * search all aligned over
	 * 
	 * 
	 */




	
	
	
	
	private StatGranularity _signal_overtrend1(NavigableMap<StatGranularity, StatVO> moves, 
			
			Instant now, boolean sell) {
		
		
		// SIGNAL must still be verified for validity LHHL, etc
		/*
		 * if we find a signal, but higher is already done and X-R, return null
		 * 
		 */
		
		StatGranularity found = null;
		
		if (sell) {
			if (buyOverBoughtSignals.size()>0) {
				StatGranularity lastObSg = buyOverBoughtSignals.lastKey();
				
				if (sellOverSoldSignals.size()>0) {
					StatGranularity lastOsSg = sellOverSoldSignals.lastKey();
					
					if (lastObSg.compareTo(lastOsSg)>0) {
						int max = 2;
						StatGranularity lastKey = null;
						
						for (StatGranularity sg: sellOverSoldSignals.descendingKeySet()) {
							
								if (lastKey != null) {
									if (!sg.getNext().equals(lastKey)) {
										break;
									}
								}
								
								Boolean sellReady = sellOverSoldSignals.get(sg);
								if (sellReady) {
									found = sg;
									break;
								}
								lastKey = sg;
								max--;
								if (max<=0) {
									logger.info("_signal_overtrend1: sell pre-signal: break: " + sg + " " + lastKey + " max:" + max + " @" + now);

									break;
								}
							
						}
						logger.info("_signal_overtrend1: found sell pre-signal: " + found + " max:" + max + " @" + now);
					}
					
					
				}
				

			}
		} else {
			if (sellOverSoldSignals.size()>0) {
				StatGranularity lastOsSg = sellOverSoldSignals.lastKey();
				
				if (buyOverBoughtSignals.size()>0) {
					StatGranularity lastObSg = buyOverBoughtSignals.lastKey();
					if (lastObSg.compareTo(lastOsSg)<0) {
						
						int max = 2;
						StatGranularity lastKey = null;
						
						for (StatGranularity sg: buyOverBoughtSignals.descendingKeySet()) {
							
								if (lastKey != null) {
									if (!sg.getNext().equals(lastKey)) {
										break;
									}
								}
								
								Boolean buyReady = buyOverBoughtSignals.get(sg);
								if (buyReady) {
									found = sg;
									break;
								}
								lastKey = sg;
								max--;
								if (max<=0) {
									logger.info("_signal_overtrend1: sell pre-signal: break: " + sg + " " + lastKey + " max:" + max + " @" + now);

									break;
								}
							
						}
						logger.info("_signal_overtrend1: found buy pre-signal: " + found + " max:" + max + " @" + now);
						
					}
					
				}
			}
		}
		
		return found;
	}
	
	
	
	
	
	
	private static boolean pendingOverTrendTriggeredSell = false;
	private static boolean pendingOverTrendTriggeredBuy = false;
	
	private Pair<StatGranularity, StatGranularity> _signal_overtrends_is_wave_ready_for_opposite_sg(double minPercent, boolean sell,
			StatGranularity sg, NavigableMap<StatGranularity, StatVO> moves, boolean live, Instant now) {
		// check that all waves lower than this sg are ready.
		
		StatGranularity foundWaveSg = null;
		StatGranularity foundSg = null;
		
		if (sell) {
			for (StatGranularity currentWaveSg: overSignalSellWaves.descendingKeySet()) {
				NavigableMap<StatGranularity, Boolean> wave = overSignalSellWaves.get(currentWaveSg);
				
				if (currentWaveSg.compareTo(sg)>=0) {
					if (wave.containsKey(sg)) {
						foundWaveSg = currentWaveSg;
					} /*else {
						for (StatGranularity wsg: wave.keySet()) {
							if (wave.get(wsg)) {
								// one is ready in higher wave: cancel, as now this higher wave is the one that takes the lead
								return (Pair.create(currentWaveSg, null));
							}
						}
					}*/
					
				} else {
					if (foundWaveSg == null) {
						// no wave with sg. return null
						return null;
					}
				}
				// now, all wave musts be ready
				if (foundWaveSg != null) {
					StatGranularity readySg = this._signal_overtrends_is_wave_ready_for_opposite_action(minPercent, sell, currentWaveSg, moves, live, now);
					
					if (readySg == null) {
						break;
					} else {
						if (foundSg == null) {
							foundSg = readySg;
						}
					}
				}
				
				
				
			}
		} else {
			for (StatGranularity currentWaveSg: overSignalBuyWaves.descendingKeySet()) {
				NavigableMap<StatGranularity, Boolean> wave = overSignalBuyWaves.get(currentWaveSg);
				
				if (currentWaveSg.compareTo(sg)>=0) {
					if (wave.containsKey(sg)) {
						foundWaveSg = currentWaveSg;
					} /*else {
						for (StatGranularity wsg: wave.keySet()) {
							if (wave.get(wsg)) {
								// one is ready in higher wave: cancel, as now this higher wave is the one that takes the lead
								return (Pair.create(currentWaveSg, null));
							}
						}
					}*/
					
				} else {
					if (foundWaveSg == null) {
						// no wave with sg. return null
						return null;
					}
				}
				// now, all wave musts be ready
				if (foundWaveSg != null) {
					StatGranularity readySg = this._signal_overtrends_is_wave_ready_for_opposite_action(minPercent, sell, currentWaveSg, moves, live, now);
					if (readySg == null) {
						break;
					} else {
						if (foundSg == null) {
							foundSg = readySg;
						}
					}
				}
				
				
				
			}
		}
		if (foundWaveSg == null) return null;
		return Pair.create(foundWaveSg, foundSg);
	}
	
	private StatGranularity _signal_overtrends_is_wave_ready_for_opposite_action(
			double minPercent, boolean sell,
			StatGranularity waveSg, NavigableMap<StatGranularity, StatVO> moves, boolean live, Instant now) {
		
		// return Pair(firstFound, percentage)
		
		NavigableMap<StatGranularity, Boolean> wave = null;
		
		
		if (sell) {
			wave = overSignalSellWaves.get(waveSg);
		} else {
			wave = overSignalBuyWaves.get(waveSg);
		}
		
		
		
		Integer countFalse = 0;
		Integer size = wave.size();
		Integer index = 0;
		StatGranularity firstFound = null;
		Integer firstIndex = 0;
		
		logger.info("_signal_overtrends_is_wave_ready_for_opposite_action: waveSg: " + waveSg + " @" + now);

		for (StatGranularity sg: wave.descendingKeySet()) {
			
			if(wave.get(sg)) {
				
				if  (firstFound == null) {
					firstFound = sg;
					firstIndex = index;
				}
				
				
			} else {
				countFalse++;
			}
			
			index++;
		}
		double percent = BigDecimal.valueOf((size.doubleValue() - countFalse.doubleValue())/size.doubleValue()).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
		logger.info("_signal_overtrends_is_wave_ready_for_opposite_action: wave " + waveSg + ": " + percent*100d + "%");
		//if (percent>=minPercent) {
			
			//TODO now, check that firstfound is within the 3 first values
			if (firstIndex <3) {
				return firstFound;
			}
			
			//return firstFound;
			
		//}
		return null;
		
	}
	
	private boolean _signal_overtrends_is_wave_ready_for_main_action(
			double maxPercent, boolean sell,
			StatGranularity waveSg, NavigableMap<StatGranularity, StatVO> moves, boolean live, Instant now) {
		
		// return Pair(firstFound, percentage)
		
		NavigableMap<StatGranularity, Boolean> wave = null;
		
		
		if (sell) {
			wave = overSignalSellWaves.get(waveSg);
		} else {
			wave = overSignalBuyWaves.get(waveSg);
		}
		
		Integer countFalse = 0;
		Integer size = wave.size();
		
		StatGranularity firstFound = null;
		
		logger.info("_signal_overtrends_is_wave_ready_for_main_action: waveSg: " + waveSg + " @" + now);

		for (StatGranularity sg: wave.descendingKeySet()) {
			
			StatVO vo = moves.get(sg);
			
			if ((vo.getDivergence() != null) && (vo.getDivergence().booleanValue() != sell)) {
				logger.info("_signal_overtrends_is_wave_ready_for_main_action: waveSg: " + waveSg + " divergent: " + vo.toShortMoveString() + " " + " @" + now);

				return false;
			}
			
			if(wave.get(sg)) {
				firstFound = sg;
				break;
			} else {
				countFalse++;
			}
			
			
		}
		double percent = BigDecimal.valueOf((size.doubleValue() - countFalse.doubleValue())/size.doubleValue()).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
		logger.info("_signal_overtrends_is_wave_ready_for_main_action: wave " + waveSg + ": " + percent*100d + "%");
		if (percent>=maxPercent) {
			return false;
		}
		return true;
		
	}
	
	
	private boolean _signal_overtrends_is_wave_divergent(StatGranularity waveSg, 
			boolean sell,
			NavigableMap<StatGranularity, StatVO> moves, boolean live, Instant now) {
		
		// return Pair(firstFound, percentage)
		
		NavigableMap<StatGranularity, Boolean> wave = null;
		
		
		if (sell) {
			wave = overSignalSellWaves.get(waveSg);
		} else {
			wave = overSignalBuyWaves.get(waveSg);
		}
		
		StatGranularity firstKey = wave.firstKey();
		StatGranularity lastKey = wave.lastKey();
		
		
		for (StatGranularity sg: moves.descendingKeySet()) {
			
			if ((sg.compareTo(firstKey)>=0) && (sg.compareTo(lastKey)<=0)) {
				StatVO vo = moves.get(sg);
				
				if ((vo.getDivergence() != null) && (vo.getDivergence().booleanValue() != sell)) {
					
					logger.info("_signal_overtrends_is_wave_divergent: waveSg: " + waveSg + " divergent: " + vo.toShortMoveString() + " " + " @" + now);

					return true;
				}
					
			}
			
		}
		return false;
		
	}
	
	
	private StatGranularity _signal_overtrends_found_highest_ready(NavigableMap<StatGranularity, StatVO> moves, boolean sell, boolean live, Instant now) {
		
		Iterator<StatGranularity> i = null;
		NavigableMap<StatGranularity, Boolean> wave = null;
		
		
		
		
		if (sell) {
			i = overSignalSellWaves.descendingKeySet().iterator();
			if (i.hasNext()) {
				StatGranularity waveSg = i.next();
				wave = overSignalSellWaves.get(waveSg);
				for (StatGranularity sg: wave.descendingKeySet()) {
					if (sellOverSoldSignals.get(sg)) {
						return sg;
					}
				}
			}
			
			
			return null;
		} else {
			i = overSignalBuyWaves.descendingKeySet().iterator();
			if (i.hasNext()) {
				StatGranularity waveSg = i.next();
				wave = overSignalBuyWaves.get(waveSg);
				for (StatGranularity sg: wave.descendingKeySet()) {
					if (buyOverBoughtSignals.get(sg)) {
						return sg;
					}
				}
			}
			
		}
		
		return null;
	}
	
	private Pair<StatGranularity, Boolean> _signal_overtrends_found_first_wave_lowest_ready(StatGranularity specified, NavigableMap<StatGranularity, StatVO> moves, boolean sell, boolean live, Instant now) {
		
		Iterator<StatGranularity> i = null;
		NavigableMap<StatGranularity, Boolean> wave = null;
		
		boolean foundSpecified = false;
		StatGranularity lowestFound = null;
		boolean noWave = false;
		
		if (sell) {
			i = overSignalSellWaves.descendingKeySet().iterator();
			if (i.hasNext()) {
				StatGranularity waveSg = i.next();
				wave = overSignalSellWaves.get(waveSg);
				for (StatGranularity sg: wave.descendingKeySet()) {
					if (sellOverSoldSignals.get(sg)) {
						lowestFound = sg;
					}
					if (sg.equals(specified)) {
						foundSpecified = true;
					}
				}
			} else {
				noWave = true;
			}
			
			
			
		} else {
			i = overSignalBuyWaves.descendingKeySet().iterator();
			if (i.hasNext()) {
				StatGranularity waveSg = i.next();
				wave = overSignalBuyWaves.get(waveSg);
				for (StatGranularity sg: wave.descendingKeySet()) {
					if (buyOverBoughtSignals.get(sg)) {
						lowestFound = sg;
					}
					if (sg.equals(specified)) {
						foundSpecified = true;
					}
				}
			} else {
				noWave = true;
			}
			
		}
		if (!foundSpecified) {
			lowestFound = null;
		}
		logger.info("_signal_overtrends_found_first_wave_lowest_ready: found: " + lowestFound + " noWave: " + noWave);
		return Pair.create(lowestFound, noWave);
	}
	
	
	private Pair<Boolean,StatGranularity> _signal_overtrends_found_firsts(
			NavigableMap<StatGranularity, StatVO> moves, boolean sell, boolean live, Instant now) {
		
		StatGranularity selectedSg = null;
		
		boolean wait = false;
		
		
		boolean blocked = false;
		
		Iterator<StatGranularity> i = null;
		
		if (sell) {
			i = overSignalSellWaves.descendingKeySet().iterator();
		} else {
			i = overSignalBuyWaves.descendingKeySet().iterator();
		}
		
		NavigableMap<StatGranularity, Boolean> wave = null;
		
		
		
		StatGranularity isReadyN = null;
		StatGranularity sgN = null;
		
		
		int count = 1;
		
		while (i.hasNext()) {
			sgN = i.next();
			isReadyN = this._signal_overtrends_is_wave_ready_for_opposite_action(OVERTRENDS_PERCENT, sell, sgN, moves, live, now);
			
			
			if (isReadyN != null) {
				
				if (!blocked) {
					selectedSg = isReadyN;
					blocked = true;
				}
				
						
			} else {
				selectedSg = sgN;
				wait = true;
				blocked = true;
				
				
			}
			logger.info("_signal_overtrends_found_firsts current: " + sgN + " isReady: " + isReadyN + " waveSign: " + sell + " selectedSg: " + selectedSg  
					+ " blocked: " + blocked
					+ " count: " + count + " @" + now);
			
			
			count++;
		}
		
		logger.info("_signal_overtrends_found_firsts finished: " 
		+ sgN + " isReady: " + isReadyN + " waveSign: " 
				+ (sgN!=null?sell:"?") 
				+ " selectedSg: " + selectedSg 
				+ " wait: " + wait 
				
				+ " count: " + count + " @" + now);
		
			
		
		return Pair.create(wait,  selectedSg);
	}
	
	
	
	/*private Pair<Boolean,Boolean> _signal_divergents_found_firsts(NavigableMap<StatGranularity, StatVO> moves, boolean live, Instant now) {
		
		Boolean sellReady = null;
		Boolean buyReady = null;
		
		Iterator<StatGranularity> i = divergentWaves.descendingKeySet().iterator();
		
		boolean isReadyN = false;
		StatGranularity sgN = null;
		
		//logger.info("_signal_divergents_found_firsts: " + divergentWaves);
		int count = 1;
		
		while (i.hasNext()) {
			sgN = i.next();
			isReadyN = this._signal_divergents_is_wave_ready(0.80d, sgN, moves, live, now);
			
			if (isReadyN) {
				if (divergentWaves.get(sgN).getLeft()) {
					if (sellReady == null) {
						sellReady = true;
					}
				} else {
					if (buyReady == null) {
						buyReady = true;
					}
				}				
			} else {
				if (divergentWaves.get(sgN).getLeft()) {
					if (sellReady == null) {
						sellReady = false;
					}
				} else {
					if (buyReady == null) {
						buyReady = false;
					}
				}		
			}
			logger.info("_signal_divergents_found_firsts current: " + sgN + " isReady: "
			+ isReadyN + " waveSign: " + divergentWaves.get(sgN).getLeft() + " sellReady: " + sellReady + " buyReady: " + buyReady 
					+ " count: " + count + " @" + now);
			
			if ((sellReady != null) && (buyReady != null)) {
				break;
			}
			count++;
		}
		
		logger.info("_signal_divergents_found_firsts finished: " 
		+ sgN + " isReady: " + isReadyN + " waveSign: " 
				+ (sgN!=null?divergentWaves.get(sgN).getLeft():"?") 
				+ " sellReady: " + sellReady + " buyReady: " + buyReady 
				
				+ " count: " + count + " @" + now);
		
			
		
		return Pair.create(sellReady, buyReady);
	}*/
	
	private Boolean _signal_divergents_biggest_x(NavigableMap<StatGranularity, StatVO> moves, boolean sell, boolean live, Instant now) {
		
		// check if all first divergent of each wave are true.
		
		Iterator<StatGranularity> i = null;
		
		if (sell) {
			i = divergentSellWaves.descendingKeySet().iterator();
		} else {
			i = divergentBuyWaves.descendingKeySet().iterator();
		}
		NavigableMap<StatGranularity, Boolean> current = null;
		
		StatGranularity sgN = null;
		
		
		while (i.hasNext()) {
			sgN = i.next();
			
			if (sell) {
				current = divergentSellWaves.get(sgN);
			} else {
				current = divergentBuyWaves.get(sgN);
			}
			
			
			if (current.size()>0) {
				StatGranularity lastKey = current.lastKey();
				if (!current.get(lastKey)) {
					return false;
				}
			}
		
			
		}
		
			
		
		return true;
	}
	
	private Boolean _signal_divergents_aligned(NavigableMap<StatGranularity, StatVO> moves, boolean sell, boolean live, Instant now) {
		
		// check if all first divergent of each wave are true.
		
		Iterator<StatGranularity> i = null;
		
		if (sell) {
			i = divergentSellWaves.descendingKeySet().iterator();
		} else {
			i = divergentBuyWaves.descendingKeySet().iterator();
		}
		NavigableMap<StatGranularity, Boolean> current = null;
		
		StatGranularity sgN = null;
		
		
		while (i.hasNext()) {
			sgN = i.next();
			
			if (sell) {
				current = divergentSellWaves.get(sgN);
			} else {
				current = divergentBuyWaves.get(sgN);
			}
			
			
			if (current.size()>0) {
				StatGranularity lastKey = current.lastKey();
				if (!current.get(lastKey)) {
					return false;
				}
			}
		
			
		}
		
			
		
		return true;
	}
	
	private Boolean _signal_divergents_minmax_aligned(NavigableMap<StatGranularity, StatVO> moves, boolean sell, boolean live, Instant now) {
		
		// check if all first divergent of each wave are true.
		
		Iterator<StatGranularity> i = null;
		
		if (sell) {
			i = divergentSellWaves.descendingKeySet().iterator();
		} else {
			i = divergentBuyWaves.descendingKeySet().iterator();
		}
		NavigableMap<StatGranularity, Boolean> current = null;
		
		StatGranularity sgN = null;
		
		if (!i.hasNext()) return null;
		
		while (i.hasNext()) {
			sgN = i.next();
			
			
			if (sell) {
				current = divergentSellWaves.get(sgN);
			} else {
				current = divergentBuyWaves.get(sgN);
			}
			
			logger.info("_signal_divergents_minmax_aligned: processing " + sgN + " @" + now);
			
			
			if (current.size()>0) {
				OverMinMax minmax = new OverMinMax(new BigDecimal(0.1d), false, null);
				int s = 0;
				for (StatGranularity sg: current.descendingKeySet()) {
					StatVO mv = moves.get(sg);
					logger.info("_signal_divergents_minmax_aligned: processing " + sgN + " @" + now + " injecting " + mv) ;
					
					minmax.injectVal(mv.getWt1(), sg, Instant.ofEpochSecond(s));
					s++;
				}
				
				NavigableMap<Instant, MarketData> highs = null;
				
				if (sell) {
					highs = minmax.getHighs();
					
					if ((highs == null) || (highs.size() == 0)) {
						highs = new TreeMap<Instant, MarketData>();
						
						if (current.size()==1) {
							MarketData mk = new MarketData();
							StatVO mv = moves.get(current.lastKey());
							mk.setValue(mv.getWt1());
							mk.setDataSg(mv.getSg());
							highs.put(Instant.now(), mk);
						} else {
							if (minmax.getMarketData() != null) {
								MarketData mk = new MarketData();
								mk.setValue(minmax.getMarketData().getHighestValue());
								mk.setDataSg(minmax.getMarketData().getDataSg());
								highs.put(Instant.now(), mk);
							}
						}
					}
				} else {
					
					highs = minmax.getLows();
					
					if ((highs == null) || (highs.size() == 0)) {
						highs = new TreeMap<Instant, MarketData>();
						if (current.size()==1) {
							MarketData mk = new MarketData();
							StatVO mv = moves.get(current.lastKey());
							mk.setValue(mv.getWt1());
							mk.setDataSg(mv.getSg());
							highs.put(Instant.now(), mk);
						} else {
							if (minmax.getMarketData() != null) {
								MarketData mk = new MarketData();
								mk.setValue(minmax.getMarketData().getLowestValue());
								mk.setDataSg(minmax.getMarketData().getDataSg());
								highs.put(Instant.now(), mk);
							}
						}
						
						
					}
				}
				
				
				for (Instant key: highs.keySet()) {
					MarketData d = highs.get(key);
					logger.info("_signal_divergents_max_aligned: marketData: " + d);
					
					StatVO mv = moves.get(d.getDataSg());
					
					logger.info("_signal_divergents_max_aligned: found high: " + mv.toMoveString());
					
					if (sell) {
						
						
						if (mv.wt1Above(WT1_OVERBOUGHT_THRESHOLD)) {
							if (!mv.getDecreasingHighs()) {
								
								
								if (mv.getDiff().signum()>0) {
									logger.info("_signal_divergents_max_aligned: sell found high: " + mv.toMoveString() + " diff > 0 not ready");
									
									return false;
								}
								
								/*switch (mv.getMv()) {
								case XP:
								case ST:
								case SN:
								case SP: {
									logger.info("_signal_divergents_max_aligned: found high: " + mv.toMoveString() + " ready");
									
									break;
								}
								default: {
									logger.info("_signal_divergents_max_aligned: found high: " + mv.toMoveString() + " not ready");
									
									return false;
								}
								}*/
								
							}
						} else {
							if (mv.getDiff().signum()>0) {
								logger.info("_signal_divergents_max_aligned: sell found high: " + mv.toMoveString() + " diff > 0 not ready");
								
								return false;
							}
						}
						
						
						
					} else {
						
						
						if (mv.wt1Below(WT1_OVERSOLD_THRESHOLD)) {
							if (!mv.getIncreasingLows()) {
								
								if (mv.getDiff().signum()<0) {
									logger.info("_signal_divergents_max_aligned: buy found high: " + mv.toMoveString() + " diff < 0 not ready");
									
									return false;
								}
								/*
								switch (mv.getMv()) {
								case XN:
								case ST:
								case SN:
								case SP: {
									logger.info("_signal_divergents_max_aligned: found high: " + mv.toMoveString() + " ready");
									
									break;
								}
								default: {
									logger.info("_signal_divergents_max_aligned: found high: " + mv.toMoveString() + " not ready");
									
									return false;
								}
								}*/
							}
						} else {
							if (mv.getDiff().signum()<0) {
								logger.info("_signal_divergents_max_aligned: buy found high: " + mv.toMoveString() + " diff < 0 not ready");
								
								return false;
							}
						}
						
						
					}
				}
				
				
				
			}
		
			
			
		}
		
		logger.info("_signal_divergents_max_aligned: found high: all ready");
		
		return true;
		
		
			
		
		
	}
	/*
	private boolean _signal_divergents_is_wave_ready(double minPercent,
			StatGranularity waveSg,
			NavigableMap<StatGranularity, StatVO> moves, boolean live, Instant now) {
		// return Pair(firstFound, percentage)
		
				Pair<Boolean, NavigableMap<StatGranularity, Boolean>> wave = divergentWaves.get(waveSg);
				NavigableMap<StatGranularity, Boolean> data = wave.getRight();
				
				Integer countFalse = 0;
				Integer size = data.size();
				StatGranularity firstFound = null;
				
				logger.info("_signal_divergents_is_wave_ready: waveSg: " + waveSg + " @" + now);

				for (StatGranularity sg: data.descendingKeySet()) {
					
					if(!data.get(sg)) {
						
						
						StatVO mv = moves.get(sg);
						
						if (wave.getLeft()) {
							// sell
							countFalse++;
						} else {
							countFalse++;
						}
						
						
					}
					
				}
				logger.info("_signal_divergents_is_wave_ready: wave " + waveSg);
				
				
				double percent = BigDecimal.valueOf((size.doubleValue() - countFalse.doubleValue())/size.doubleValue()).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
				
				logger.info("_signal_divergents_is_wave_ready: wave " + waveSg + " percent: " + percent * 100 + "%");
				
				if (percent>=minPercent) {
					
					
					
					return true;
					
				}
				return false;
	}


*/



	private static NavigableMap<StatGranularity, Boolean> buyOverBoughtSignals = new TreeMap<StatGranularity, Boolean>();
	private static NavigableMap<StatGranularity, Boolean> sellOverSoldSignals = new TreeMap<StatGranularity, Boolean>();
	
	private static double OVERTRENDS_PERCENT = 0.80d;
	
	private static NavigableMap<StatGranularity, NavigableMap<StatGranularity, Boolean>> overSignalSellWaves = new TreeMap<StatGranularity, NavigableMap<StatGranularity, Boolean>>();
	private static NavigableMap<StatGranularity, NavigableMap<StatGranularity, Boolean>> overSignalBuyWaves = new TreeMap<StatGranularity, NavigableMap<StatGranularity, Boolean>>();
	
	
	public void _signal_overtrends_inject(StatVO currentVO, Instant now, boolean live) {
		
		StatGranularity currentSg = currentVO.getSg();
		
		
		// overbought
		
		//logger.info("_signal_overtrends_inject: " + currentVO.toMoveString());

		synchronized (buyOverBoughtSignals) {
			if (!buyOverBoughtSignals.containsKey(currentSg)) {
				// insert
				if (currentVO.wt1Above(WT1_OVERBOUGHT_THRESHOLD) && currentVO.highestWtAbove(RP_XP_THRESHOLD)) {
					buyOverBoughtSignals.put(currentSg, false); // not seen negative yet
				}
			} else if (!buyOverBoughtSignals.get(currentSg)) {
				// maintain
				if (currentVO.getHighestWt().compareTo(RN_XN_THRESHOLD)<=0) {
					buyOverBoughtSignals.put(currentSg, true);
				} else if (currentVO.wt1Above(WT1_OVERBOUGHT_THRESHOLD)) {
					//buyOverBoughtSignals.put(currentSg, false);
				}
			} else {
				if (currentVO.getWt().signum()>=0) {
					
					if (buyOverBoughtSignals.lastKey().equals(currentSg)) {
						pendingOverTrendTriggeredBuy = false;
					}
					buyOverBoughtSignals.remove(currentSg);
					
					
				}
			}
			
		}
		
		synchronized (sellOverSoldSignals) {
			// oversold
			if (!sellOverSoldSignals.containsKey(currentSg)) {
				// insert
				if (currentVO.wt1Below(WT1_OVERSOLD_THRESHOLD) && currentVO.highestWtBelow(RN_XN_THRESHOLD)) {
					sellOverSoldSignals.put(currentSg, false); 
				}
			} else if (!sellOverSoldSignals.get(currentSg)) {
				// maintain
				if (currentVO.getHighestWt().compareTo(RP_XP_THRESHOLD)>=0) {
					sellOverSoldSignals.put(currentSg, true);
				} else if (currentVO.wt1Below(WT1_OVERSOLD_THRESHOLD)) {
					//sellOverSoldSignals.put(currentSg, false);
				}
			} else {
				if (currentVO.getWt().signum()<=0) {
					
					if (sellOverSoldSignals.lastKey().equals(currentSg)) {
						pendingOverTrendTriggeredSell = false;
					}
					
					sellOverSoldSignals.remove(currentSg);
				}
			}
		}
					
					
	}
			
	
	private void _signal_overtrends_build(
			
			NavigableMap<StatGranularity, StatVO> moves, 
			
			Instant now, boolean live) {
		
		/*
		 * 
		 * We add a sg is wt1 > Overbought and highestWt > 5.
		 * 
		 * We maintain a sg if:
		 * 
		 * 
		 * Overbought:
		 * 
		 * wt1 is overbought;
		 * wt1 is decreasing, and and wt1 was overbought in last peak
		 * 
		 * We remove a sg if:
		 * 
		 * wt is positive, and wt1 is not overbought.
		 * 
		 * 
		 * Oversold:
		 * 
		 * wt1 is oversold;
		 * wt1 is increasing, and and wt1 was oversold for last peak
		 * 
		 * We remove a sg if:
		 * 
		 * wt is negative, and wt1 is not oversold.
		 * 
		 * 
		 
		 * 
		 * 
		 */
		
		/*for (StatGranularity currentSg: moves.descendingKeySet()) {
			StatVO currentVO = moves.get(currentSg);
		
			this._signal_overtrends_inject(currentVO, now, live);
			
		}*/
		

		overSignalSellWaves.clear();
		overSignalBuyWaves.clear();
		
		StatGranularity lastKey = null;
		StatGranularity firstKey = null;
		NavigableMap<StatGranularity, Boolean> currentWave = null;
		
		logger.info("_signal_overtrends_build: _signal_overtrends_build " + sellOverSoldSignals);
		
		for (StatGranularity sg: sellOverSoldSignals.descendingKeySet()) {
			if (lastKey != null) {
				
				Pair<StatGranularity, StatGranularity> mulPair = getTimeService().multiplyDoubleSg(sg, 2d, live);
				StatGranularity mulSg = mulPair.getRight();
				if (mulSg == null) {
					mulSg = mulPair.getLeft();
				}
				if (lastKey.compareTo(mulSg)>=0) {
					
				//if (lastKey.getPrevious().compareTo(sg)>0) {
					overSignalSellWaves.put(firstKey, currentWave);
					currentWave = new TreeMap<StatGranularity, Boolean>();
					firstKey = sg;
				}
				
				
				
				
			} else {
				currentWave = new TreeMap<StatGranularity, Boolean>();
				firstKey = sg;
			}
			currentWave.put(sg, sellOverSoldSignals.get(sg));
			lastKey = sg;
		}
		if ((currentWave != null) && (currentWave.size()>0)) {
			overSignalSellWaves.put(firstKey, currentWave);
		}
		
		//logger.info("_signal_overtrends_build: overSignalWaves " + overSignalWaves);
		
		
		lastKey = null;
		firstKey = null;
		currentWave = null;
		
		for (StatGranularity sg: buyOverBoughtSignals.descendingKeySet()) {
			if (lastKey != null) {
				
				Pair<StatGranularity, StatGranularity> mulPair = getTimeService().multiplyDoubleSg(sg, 2d, live);
				StatGranularity mulSg = mulPair.getRight();
				if (mulSg == null) {
					mulSg = mulPair.getLeft();
				}
				if (lastKey.compareTo(mulSg)>=0) {
				
				//if (lastKey.getPrevious().compareTo(sg)>0) {
					overSignalBuyWaves.put(firstKey, currentWave);
					currentWave = new TreeMap<StatGranularity, Boolean>();
					firstKey = sg;
				}
			} else {
				currentWave = new TreeMap<StatGranularity, Boolean>();
				firstKey = sg;
			}
			currentWave.put(sg, buyOverBoughtSignals.get(sg));
			lastKey = sg;
		}
		
		if ((currentWave != null) && (currentWave.size()>0)) {
			overSignalBuyWaves.put(firstKey, currentWave);
		}
		
		StringBuffer info = new StringBuffer("\n_build_over_signal_waves:  -----------------" 
				+ (pendingOverTrendTriggeredBuy?"pendingOverTrendTriggeredBuy":"") +" @" + now);
				
		
		//logger.info(overSignalWaves.descendingKeySet());
		
		for (StatGranularity waveSg: overSignalSellWaves.descendingKeySet()) {
			
			
			StringBuffer waveStr = new StringBuffer(waveSg.toString());
			while (waveStr.length()<8) {
				waveStr.append(" ");
			}
			
			NavigableMap<StatGranularity, Boolean> wave = overSignalSellWaves.get(waveSg);
			
			info
				.append("\n")
				.append(waveStr)
				.append(("sellOverSoldSignals "))
				.append(((pendingOverTrendTriggeredSell)?"pendingOverTrendTriggeredSell":"") )
				.append(" @").append(now);
			
			//logger.info(wave.getRight().descendingKeySet());
			
			for (StatGranularity sg: wave.descendingKeySet()) {
				
			info
				.append("\n")
				.append(waveStr)
				.append(" sellOverSoldSignals ")
				.append(wave.get(sg))
				.append(" ")
				.append(moves.get(sg).toMoveString());

				
			}
			
			
			info
			.append("\n")
			.append(waveStr)
			.append(" sellOverSoldSignals ");
			

			
		}
			
		for (StatGranularity waveSg: overSignalBuyWaves.descendingKeySet()) {
			
			
			StringBuffer waveStr = new StringBuffer(waveSg.toString());
			while (waveStr.length()<8) {
				waveStr.append(" ");
			}
			
			NavigableMap<StatGranularity, Boolean> wave = overSignalBuyWaves.get(waveSg);
			
			info
				.append("\n")
				.append(waveStr)
				.append(("buyOverBoughtSignals "))
				.append(((pendingOverTrendTriggeredSell)?"pendingOverTrendTriggeredBuy":"") )
				.append(" @").append(now);
			
			//logger.info(wave.getRight().descendingKeySet());
			
			for (StatGranularity sg: wave.descendingKeySet()) {
				
			info
				.append("\n")
				.append(waveStr)
				.append(" buyOverBoughtSignals ")
				.append(wave.get(sg))
				.append(" ")
				.append(moves.get(sg).toMoveString());

				
			}
			
			
			info
			.append("\n")
			.append(waveStr)
			.append(" buyOverBoughtSignals ");
			

			
		}
		logger.info(info.toString());
				
				
		
		
	}
	
	
	private static NavigableMap<StatGranularity, Boolean> sellDivergentSignals = new TreeMap<StatGranularity, Boolean>();
	private static NavigableMap<StatGranularity, Boolean> buyDivergentSignals = new TreeMap<StatGranularity, Boolean>();
	
	
	private static NavigableMap<StatGranularity, NavigableMap<StatGranularity, Boolean>> divergentSellWaves = new TreeMap<StatGranularity, NavigableMap<StatGranularity, Boolean>>();
	private static NavigableMap<StatGranularity, NavigableMap<StatGranularity, Boolean>> divergentBuyWaves = new TreeMap<StatGranularity, NavigableMap<StatGranularity, Boolean>>();
	
	
	private boolean _signal_is_divergent(StatVO vo, boolean sell, Instant now) {
		boolean wtSignNum = vo.getWt().signum()>=0;
		
		if (this._is_stalled(vo)) {
			return false;
		}
		
		//BigDecimal percentUndone = vo.getWt().divide(vo.getHighestWt(), Indicators.roundTwoIndicators);
		
			
					if (sell) {
						if (vo.wt1Above(WT1_OVERBOUGHT_THRESHOLD)) {
							
							if (wtSignNum) {
								if (vo.getDiff().compareTo(Indicators.ZERO_DOT_FIVE)>=0) {
									if (vo.getDecreasingHighs()) {
										//logger.info("_signal_is_divergent " + vo.getSg() + " leftPercent.compareTo(Indicators.ZERO_DOT_TWO)>0 decreasing highs, false");
										return false;
									} else {
										logger.info("_signal_is_divergent " + vo.getSg() + " leftPercent.compareTo(Indicators.ZERO_DOT_TWO)>0 increasing highs");
										
									}
									return true;
								}
							}
							
							if (vo.getHighestWt().compareTo(RP_XP_THRESHOLD)>=0) {
								BigDecimal left = vo.getWt().add(vo.getDiff().multiply(Indicators.THREE, Indicators.roundIndicators));
								BigDecimal leftPercent = left.divide(vo.getHighestWt(), Indicators.roundTwoIndicators);
								
								if (leftPercent.compareTo(Indicators.ZERO_DOT_TWO)>0) {
									
									if (vo.getDecreasingHighs()) {
										//logger.info("_signal_is_divergent " + vo.getSg() + " leftPercent.compareTo(Indicators.ZERO_DOT_TWO)>0 decreasing highs, false");
										return false;
									} else {
										logger.info("_signal_is_divergent " + vo.getSg() + " leftPercent.compareTo(Indicators.ZERO_DOT_TWO)>0 increasing highs");
										
									}
									
									
									return true;
								}
							}
							
							
							
						
						} else if (vo.wt1Above(Indicators.THIRTY)) {
								
								
								
								if ((vo.getWt().add(vo.getDiff()).compareTo(Indicators.EIGHT)>=0) 
										&& (vo.getDiff().compareTo(Indicators.SIX)>=0)) {
									logger.info("_signal_is_divergent " + vo.getSg() + " vo.getDiff().compareTo(Indicators.EIGHT)>=0");
									return true;
								}
								if ((vo.wt1Above(Indicators.THIRTY_FIVE) && (vo.getWt().add(vo.getDiff()).compareTo(Indicators.SEVEN)>=0) 
										&& (vo.getDiff().compareTo(Indicators.FIVE)>=0))) {
									logger.info("_signal_is_divergent " + vo.getSg() + " vo.getDiff().compareTo(Indicators.SEVEN)>=0");

									return true;
								}
								if ((vo.wt1Above(Indicators.FOURTY) && (vo.getWt().add(vo.getDiff()).compareTo(Indicators.SIX)>=0) 
										&& (vo.getDiff().compareTo(Indicators.FOUR)>=0))) {
									logger.info("_signal_is_divergent " + vo.getSg() + " vo.getDiff().compareTo(Indicators.SIX)>=0");
									return true;
								}
								if ((vo.wt1Above(Indicators.FOURTY_FIVE) && (vo.getWt().add(vo.getDiff()).compareTo(Indicators.FIVE)>=0) 
										&& (vo.getDiff().compareTo(Indicators.THREE)>=0))) {
									logger.info("_signal_is_divergent " + vo.getSg() + " vo.getDiff().compareTo(Indicators.FIVE)>=0");
									return true;
								}
								if ((vo.wt1Above(Indicators.FIFTY) && (vo.getWt().add(vo.getDiff()).compareTo(Indicators.FOUR)>=0) 
										&& (vo.getDiff().compareTo(Indicators.TWO)>=0))) {
									logger.info("_signal_is_divergent " + vo.getSg() + " vo.getDiff().compareTo(Indicators.FOUR)>=0");
									return true;
								}
								
								if (wtSignNum) {
									if (vo.wt1Above(Indicators.FIFTY)) {
										if (vo.getDiff().compareTo(Indicators.ONE)>=0) {
											return true;
										}
									} else if (vo.wt1Above(Indicators.FOURTY_NINE)) {
										if (vo.getDiff().compareTo(Indicators.ONE_DOT_ONE)>=0) {
											return true;
										}
									} else if (vo.wt1Above(Indicators.FOURTY_EIGHT)) {
										if (vo.getDiff().compareTo(Indicators.ONE_DOT_TWO)>=0) {
											return true;
										}
									} else if (vo.wt1Above(Indicators.FOURTY_SEVEN)) {
										if (vo.getDiff().compareTo(Indicators.ONE_DOT_THREE)>=0) {
											return true;
										}
									} else if (vo.wt1Above(Indicators.FOURTY_SIX)) {
										if (vo.getDiff().compareTo(Indicators.ONE_DOT_FOUR)>=0) {
											return true;
										}
									} else if (vo.wt1Above(Indicators.FOURTY_FIVE)) {
										if (vo.getDiff().compareTo(Indicators.ONE_DOT_FIVE)>=0) {
											return true;
										}
									} else if (vo.wt1Above(Indicators.FOURTY_FOUR)) {
										if (vo.getDiff().compareTo(Indicators.ONE_DOT_SIX)>=0) {
											return true;
										}
									} else if (vo.wt1Above(Indicators.FOURTY_THREE)) {
										if (vo.getDiff().compareTo(Indicators.ONE_DOT_SEVEN)>=0) {
											return true;
										}
									} else if (vo.wt1Above(Indicators.FOURTY_TWO)) {
										if (vo.getDiff().compareTo(Indicators.ONE_DOT_EIGHT)>=0) {
											return true;
										}
									} else if (vo.wt1Above(Indicators.FOURTY_ONE)) {
										if (vo.getDiff().compareTo(Indicators.ONE_DOT_NINE)>=0) {
											return true;
										}
									} else if (vo.wt1Above(Indicators.THIRTY_NINE)) {
										if (vo.getDiff().compareTo(Indicators.TWO_DOT_ONE)>=0) {
											return true;
										}
									} else if (vo.wt1Above(Indicators.THIRTY_EIGHT)) {
										if (vo.getDiff().compareTo(Indicators.TWO_DOT_TWO)>=0) {
											return true;
										}
									} else if (vo.wt1Above(Indicators.THIRTY_SEVEN)) {
										if (vo.getDiff().compareTo(Indicators.TWO_DOT_THREE)>=0) {
											return true;
										}
									} else if (vo.wt1Above(Indicators.THIRTY_SIX)) {
										if (vo.getDiff().compareTo(Indicators.TWO_DOT_FOUR)>=0) {
											return true;
										}
									} else if (vo.wt1Above(Indicators.THIRTY_FIVE)) {
										if (vo.getDiff().compareTo(Indicators.TWO_DOT_FIVE)>=0) {
											return true;
										}
									} else if (vo.wt1Above(Indicators.THIRTY_FOUR)) {
										if (vo.getDiff().compareTo(Indicators.TWO_DOT_SIX)>=0) {
											return true;
										}
									} else if (vo.wt1Above(Indicators.THIRTY_THREE)) {
										if (vo.getDiff().compareTo(Indicators.TWO_DOT_SEVEN)>=0) {
											return true;
										}
									} else if (vo.wt1Above(Indicators.THIRTY_TWO)) {
										if (vo.getDiff().compareTo(Indicators.TWO_DOT_EIGHT)>=0) {
											return true;
										}
									} else if (vo.wt1Above(Indicators.THIRTY_ONE)) {
										if (vo.getDiff().compareTo(Indicators.TWO_DOT_NINE)>=0) {
											return true;
										}
									} else if (vo.wt1Above(Indicators.THIRTY)) {
										if (vo.getDiff().compareTo(Indicators.THREE)>=0) {
											return true;
										}
									} 
									
								}
							
						}
					} else {
						if (vo.wt1Below(WT1_OVERSOLD_THRESHOLD)) {
							
							if (!wtSignNum) {
								if (vo.getDiff().compareTo(Indicators.MINUS_ZERO_DOT_FIVE)<=0) {
									if (vo.getIncreasingLows()) {
										return false;
									}
									return true;
								}
							}
							
							if (vo.getHighestWt().compareTo(RN_XN_THRESHOLD)<=0) {
								BigDecimal left = vo.getWt().add(vo.getDiff().multiply(Indicators.THREE, Indicators.roundIndicators));
								BigDecimal leftPercent = left.divide(vo.getHighestWt(), Indicators.roundTwoIndicators);
								
								if (leftPercent.compareTo(Indicators.ZERO_DOT_TWO)>0) {
									if (vo.getIncreasingLows()) {
										return false;
									}
									return true;
								}
							}
							
						} else if (vo.wt1Below(Indicators.MINUS_THIRTY)) {
							
							
							
							if ((vo.getWt().add(vo.getDiff()).compareTo(Indicators.MINUS_EIGHT)<=0) 
									&& (vo.getDiff().compareTo(Indicators.MINUS_SIX)<=0)) {
								return true;
							}
							if ((vo.wt1Below(Indicators.MINUS_THIRTY_FIVE) && (vo.getWt().add(vo.getDiff()).compareTo(Indicators.MINUS_SEVEN)<=0)
									&& (vo.getDiff().compareTo(Indicators.MINUS_FIVE)<=0))) {
								return true;
							}
							if ((vo.wt1Below(Indicators.MINUS_FOURTY) && (vo.getWt().add(vo.getDiff()).compareTo(Indicators.MINUS_SIX)<=0)
									&& (vo.getDiff().compareTo(Indicators.MINUS_FOUR)<=0))) {
								return true;
							}
							if ((vo.wt1Below(Indicators.MINUS_FOURTY_FIVE) && (vo.getWt().add(vo.getDiff()).compareTo(Indicators.MINUS_FIVE)<=0)
									&& (vo.getDiff().compareTo(Indicators.MINUS_THREE)<=0))) {
								return true;
							}
							if ((vo.wt1Below(Indicators.MINUS_FIFTY) && (vo.getWt().add(vo.getDiff()).compareTo(Indicators.MINUS_FOUR)<=0)
									&& (vo.getDiff().compareTo(Indicators.MINUS_TWO)<=0))) {
								return true;
							}
							
							if (!wtSignNum) {
								if (vo.wt1Below(Indicators.MINUS_FIFTY)) {
									if (vo.getDiff().compareTo(Indicators.MINUS_ONE)<=0) {
										return true;
									}
								} else if (vo.wt1Below(Indicators.MINUS_FOURTY_NINE)) {
									if (vo.getDiff().compareTo(Indicators.MINUS_ONE_DOT_ONE)<=0) {
										return true;
									}
								} else if (vo.wt1Below(Indicators.MINUS_FOURTY_EIGHT)) {
									if (vo.getDiff().compareTo(Indicators.MINUS_ONE_DOT_TWO)<=0) {
										return true;
									}
								} else if (vo.wt1Below(Indicators.MINUS_FOURTY_SEVEN)) {
									if (vo.getDiff().compareTo(Indicators.MINUS_ONE_DOT_THREE)<=0) {
										return true;
									}
								} else if (vo.wt1Below(Indicators.MINUS_FOURTY_SIX)) {
									if (vo.getDiff().compareTo(Indicators.MINUS_ONE_DOT_FOUR)<=0) {
										return true;
									}
								} else if (vo.wt1Below(Indicators.MINUS_FOURTY_FIVE)) {
									if (vo.getDiff().compareTo(Indicators.MINUS_ONE_DOT_FIVE)<=0) {
										return true;
									}
								} else if (vo.wt1Below(Indicators.MINUS_FOURTY_FOUR)) {
									if (vo.getDiff().compareTo(Indicators.MINUS_ONE_DOT_SIX)<=0) {
										return true;
									}
								} else if (vo.wt1Below(Indicators.MINUS_FOURTY_THREE)) {
									if (vo.getDiff().compareTo(Indicators.MINUS_ONE_DOT_SEVEN)<=0) {
										return true;
									}
								} else if (vo.wt1Below(Indicators.MINUS_FOURTY_TWO)) {
									if (vo.getDiff().compareTo(Indicators.MINUS_ONE_DOT_EIGHT)<=0) {
										return true;
									}
								} else if (vo.wt1Below(Indicators.MINUS_FOURTY_ONE)) {
									if (vo.getDiff().compareTo(Indicators.MINUS_ONE_DOT_NINE)<=0) {
										return true;
									}
								} else if (vo.wt1Below(Indicators.MINUS_FOURTY)) {
									if (vo.getDiff().compareTo(Indicators.MINUS_TWO)<=0) {
										return true;
									}
								} else if (vo.wt1Below(Indicators.MINUS_THIRTY_NINE)) {
									if (vo.getDiff().compareTo(Indicators.MINUS_TWO_DOT_ONE)<=0) {
										return true;
									}
								} else if (vo.wt1Below(Indicators.MINUS_THIRTY_EIGHT)) {
									if (vo.getDiff().compareTo(Indicators.MINUS_TWO_DOT_TWO)<=0) {
										return true;
									}
								} else if (vo.wt1Below(Indicators.MINUS_THIRTY_SEVEN)) {
									if (vo.getDiff().compareTo(Indicators.MINUS_TWO_DOT_THREE)<=0) {
										return true;
									}
								} else if (vo.wt1Below(Indicators.MINUS_THIRTY_SIX)) {
									if (vo.getDiff().compareTo(Indicators.MINUS_TWO_DOT_FOUR)<=0) {
										return true;
									}
								} else if (vo.wt1Below(Indicators.MINUS_THIRTY_FIVE)) {
									if (vo.getDiff().compareTo(Indicators.MINUS_TWO_DOT_FIVE)<=0) {
										return true;
									}
								} else if (vo.wt1Below(Indicators.MINUS_THIRTY_FOUR)) {
									if (vo.getDiff().compareTo(Indicators.MINUS_TWO_DOT_SIX)<=0) {
										return true;
									}
								} else if (vo.wt1Below(Indicators.MINUS_THIRTY_THREE)) {
									if (vo.getDiff().compareTo(Indicators.MINUS_TWO_DOT_SEVEN)<=0) {
										return true;
									}
								} else if (vo.wt1Below(Indicators.MINUS_THIRTY_TWO)) {
									if (vo.getDiff().compareTo(Indicators.MINUS_TWO_DOT_EIGHT)<=0) {
										return true;
									}
								} else if (vo.wt1Below(Indicators.MINUS_THIRTY_ONE)) {
									if (vo.getDiff().compareTo(Indicators.MINUS_TWO_DOT_NINE)<=0) {
										return true;
									}
								} else if (vo.wt1Below(Indicators.MINUS_THIRTY)) {
									if (vo.getDiff().compareTo(Indicators.MINUS_THREE)<=0) {
										return true;
									}
								} 
								
							}
						
							
						}
					
					}
					
			
			
			
			
		
		return false;
		
	}
	
	public void _signal_divergents_inject(
			
			StatVO currentVO, 
			
			Instant now, boolean live) {
		StatGranularity currentSg = currentVO.getSg();

		synchronized (sellDivergentSignals) {
			if (!sellDivergentSignals.containsKey(currentSg)) {
				// insert
				if (this._signal_is_divergent(currentVO, true, now)) {
					sellDivergentSignals.put(currentSg, false); // not seen undivergent yet
				}
			} else if (currentVO.getWt().signum()>=0) {
				// maintain, or set as ready
				if (this._signal_is_divergent(currentVO, true, now)) {
					sellDivergentSignals.put(currentSg, false); // not seen undivergent yet
				} else {
					sellDivergentSignals.put(currentSg, true);
				} 
			} else {
				sellDivergentSignals.remove(currentSg);
			}
			
		}
		
		synchronized (buyDivergentSignals) {
			// oversold
			if (!buyDivergentSignals.containsKey(currentSg)) {
				// insert
				if (this._signal_is_divergent(currentVO, false, now)) {
					buyDivergentSignals.put(currentSg, false); 
				}
			} else if (currentVO.getWt().signum()<=0) {
				// maintain
				if (this._signal_is_divergent(currentVO, false, now)) {
					buyDivergentSignals.put(currentSg, false); 
				} else  {
					buyDivergentSignals.put(currentSg, true);
				}
			} else {
				buyDivergentSignals.remove(currentSg);
			}
		}
		
		
	}
	
	private void _signal_divergents_build(
			
			NavigableMap<StatGranularity, StatVO> moves, 
			
			Instant now, boolean live) {
		
	
		
		/*for (StatGranularity currentSg: moves.descendingKeySet()) {
			StatVO currentVO = moves.get(currentSg);
		
			// overbought
			
			this._signal_divergents_inject(currentVO, now, live);
			
		}
		*/
		logger.info("sellDivergentSignals: " + sellDivergentSignals + " buyDivergentSignals: " + buyDivergentSignals);
		

		divergentSellWaves.clear();
		divergentBuyWaves.clear();
		
		StatGranularity lastKey = null;
		StatGranularity firstKey = null;
		NavigableMap<StatGranularity, Boolean> currentWave = null;
		
		for (StatGranularity sg: buyDivergentSignals.descendingKeySet()) {
			if (lastKey != null) {
				
				if (lastKey.compareTo(getTimeService().multiplyDoubleSg(sg, 1.5d, live).getLeft())>=0) {
					divergentBuyWaves.put(firstKey, currentWave);
					currentWave = new TreeMap<StatGranularity, Boolean>();
					firstKey = sg;
				}
				
				
			} else {
				currentWave = new TreeMap<StatGranularity, Boolean>();
				firstKey = sg;
			}
			currentWave.put(sg, buyDivergentSignals.get(sg));
			lastKey = sg;
		}
		if ((currentWave != null) && (currentWave.size()>0)) {
			divergentBuyWaves.put(firstKey, currentWave);
		}
		
		lastKey = null;
		firstKey = null;
		currentWave = null;
		
		for (StatGranularity sg: sellDivergentSignals.descendingKeySet()) {
			if (lastKey != null) {
				if (lastKey.compareTo(getTimeService().multiplyDoubleSg(sg, 1.5d, live).getLeft())>=0) {
					divergentSellWaves.put(firstKey, currentWave);
					currentWave = new TreeMap<StatGranularity, Boolean>();
					firstKey = sg;
				}
			} else {
				currentWave = new TreeMap<StatGranularity, Boolean>();
				firstKey = sg;
			}
			currentWave.put(sg, sellDivergentSignals.get(sg));
			lastKey = sg;
		}
		
		if ((currentWave != null) && (currentWave.size()>0)) {
			divergentSellWaves.put(firstKey, currentWave);
		}
		
		StringBuffer info = new StringBuffer("\n_build_divergent_signal_waves:  ----------------- @" + now);
				
		for (StatGranularity waveSg: divergentSellWaves.descendingKeySet()) {
			
			
			StringBuffer waveStr = new StringBuffer(waveSg.toString());
			while (waveStr.length()<8) {
				waveStr.append(" ");
			}
			
			NavigableMap<StatGranularity, Boolean> wave = divergentSellWaves.get(waveSg);
			
			info
				.append("\n")
				.append(waveStr)
				.append(" sellDivergentSignals ")
				.append(" @").append(now);
			
			for (StatGranularity sg: wave.descendingKeySet()) {
				
			info
				.append("\n")
				.append(waveStr)
				.append(" sellDivergentSignals ")
				.append(wave.get(sg))
				.append(" ")
				.append(moves.get(sg).toMoveString());

				
			}
			
			
			info
			.append("\n")
			.append(waveStr)
			.append(" sellDivergentSignals ");
			

			
		}
				
		for (StatGranularity waveSg: divergentBuyWaves.descendingKeySet()) {
			
			
			StringBuffer waveStr = new StringBuffer(waveSg.toString());
			while (waveStr.length()<8) {
				waveStr.append(" ");
			}
			
			NavigableMap<StatGranularity, Boolean> wave = divergentBuyWaves.get(waveSg);
			
			info
				.append("\n")
				.append(waveStr)
				.append(" buyDivergentSignals ")
				.append(" @").append(now);
			
			for (StatGranularity sg: wave.descendingKeySet()) {
				
			info
				.append("\n")
				.append(waveStr)
				.append(" buyDivergentSignals ")
				.append(wave.get(sg))
				.append(" ")
				.append(moves.get(sg).toMoveString());

				
			}
			
			
			info
			.append("\n")
			.append(waveStr)
			.append(" buyDivergentSignals ");
			

			
		}
		logger.info(info.toString());
				
				
		
		
	}
	
	
	
	
	private Pair<Boolean, StatGranularity> _search_aligned(
			StatGranularity trendLeftSg, 
			StatGranularity trendRightSg,
			StatGranularity minSg,
			Boolean trendSign,
			double requiredAlignmentMaxWidth,
			NavigableMap<StatGranularity, StatVO> moves, 
			Boolean sign, 
			Boolean live,
			Instant now) {


		NavigableSet<StatGranularity> stalledTrendMoves = new TreeSet<StatGranularity>();
		NavigableSet<StatGranularity> overFoundMoves = new TreeSet<StatGranularity>();
		NavigableSet<StatGranularity> blockerCandidates = new TreeSet<StatGranularity>();
		NavigableSet<StatGranularity> stalledMoves = new TreeSet<StatGranularity>();
		NavigableSet<StatGranularity> stalledlhhl = new TreeSet<StatGranularity>();
		NavigableSet<StatGranularity> lhHlMoves = new TreeSet<StatGranularity>();
		NavigableSet<StatGranularity> rxMoves = new TreeSet<StatGranularity>();
		NavigableSet<StatGranularity> wt8div4overs = new TreeSet<StatGranularity>();
		NavigableSet<StatGranularity> wt8div4lhhl = new TreeSet<StatGranularity>();
		NavigableSet<StatGranularity> weakMoves = new TreeSet<StatGranularity>();

		NavigableSet<StatGranularity> rxBelowWt8div4 = new TreeSet<StatGranularity>();
		

		NavigableSet<StatGranularity> rzNoLhHlmul4OK = new TreeSet<StatGranularity>();


		NavigableSet<StatGranularity> all = new TreeSet<StatGranularity>();

		
		NavigableSet<StatGranularity> allOfThisSign = new TreeSet<StatGranularity>();

		

		
		
		double foundAlignmentMaxWidth = 0d;

		boolean foundFirst = false;

		boolean blockedByDivergence = false;
		
		StatGranularity previousMatchingSg = null;

		boolean unstrict = false;
		
		boolean disableBlockers = false;

		
		
		
		
		
		
		for (StatGranularity currentSg: moves.keySet()) {

			if (currentSg.compareTo(trendLeftSg)<=0) { 
				StatVO currentVO = moves.get(currentSg);


				if (sign) {


					
					if (foundFirst) {
						if (currentVO.isRpXp(RP_XP_THRESHOLD)) {
							
							rxMoves.add(currentSg);
							if (previousMatchingSg != null) {
								double width = previousMatchingSg.getIndex().doubleValue()/currentSg.getIndex().doubleValue();
								if (width>=foundAlignmentMaxWidth) foundAlignmentMaxWidth = width;
							}
							previousMatchingSg = currentSg;
							foundFirst = true;
						}
					}
					// BLOCKER
					
						if (currentVO.wt1Above(Indicators.SIXTY)) {
							blockerCandidates.add(currentSg);
						} else if (currentVO.wt1Above(Indicators.FIFTY_FIVE)) {
							blockerCandidates.add(currentSg);
						} else if (currentVO.wt1Above(Indicators.FIFTY_THREE)) {
							blockerCandidates.add(currentSg);
						} else if (currentVO.wt1Above(Indicators.FIFTY)) {
							if (currentVO.getHighestWt().compareTo(Indicators.FIVE)>=0) {
								if (currentVO.getMv().equals(SgMove.AP) || currentVO.getMv().equals(SgMove.BP)) {
									blockerCandidates.add(currentSg);
								}
							}
						} else if (currentVO.wt1Above(Indicators.FOURTY_FIVE)) {
							if (currentVO.getHighestWt().compareTo(Indicators.TWELVE)>=0) {
								if (currentVO.getMv().equals(SgMove.AP) || currentVO.getMv().equals(SgMove.BP)) {
									blockerCandidates.add(currentSg);
								}
							}
						} else if (currentVO.wt1Above(Indicators.FOURTY)) {
							if (currentVO.getHighestWt().compareTo(Indicators.FOURTEEN)>=0) {
								if (currentVO.getMv().equals(SgMove.AP) || currentVO.getMv().equals(SgMove.BP)) {
									blockerCandidates.add(currentSg);
								}
							}
						} else if (currentVO.wt1Above(Indicators.THIRTY_FIVE)) {
							if (currentVO.getHighestWt().compareTo(Indicators.EIGHTEEN)>=0) {
								if (currentVO.getMv().equals(SgMove.AP) || currentVO.getMv().equals(SgMove.BP)) {
									blockerCandidates.add(currentSg);
								}
							}
						} else if (currentVO.wt1Above(Indicators.THIRTY)) {
							if (currentVO.getHighestWt().compareTo(Indicators.TWENTY_FIVE)>=0) {
								if (currentVO.getMv().equals(SgMove.AP) || currentVO.getMv().equals(SgMove.BP)) {
									blockerCandidates.add(currentSg);
								}
							}
						}
					
					
					// WEAK
					
					/*if (enableWeak) {
						if (currentVO.wt1Below(Indicators.MINUS_THIRTY)) {
							if (currentVO.isApBp(Indicators.SEVEN)) {
								weakMoves.add(currentSg);
								all.add(currentSg);
								allOfThisSign.add(currentSg);
								if (previousMatchingSg != null) {
									double width = previousMatchingSg.getIndex().doubleValue()/currentSg.getIndex().doubleValue();
									if (width>=foundAlignmentMaxWidth) foundAlignmentMaxWidth = width;
								}
								previousMatchingSg = currentSg;
								foundFirst = true;
							}
						}
					}*/
					
					
					
					
					// STALLED
					if (currentVO.wt1Above(Indicators.THIRTY)) {

						switch (currentVO.getMv()) {
						case SN:
						case SP:
						{
							//if (currentVO.getHighestWt().compareTo(Indicators.MINUS_FIVE)>=0) {
							stalledMoves.add(currentSg);
							all.add(currentSg);
							allOfThisSign.add(currentSg);
							if (previousMatchingSg != null) {
								double width = previousMatchingSg.getIndex().doubleValue()/currentSg.getIndex().doubleValue();
								if (width>=foundAlignmentMaxWidth) foundAlignmentMaxWidth = width;
							}
							previousMatchingSg = currentSg;
							foundFirst = true;
							break;
							//}
						}
						default: { break; }
						}



					}

					// OVER

					if (currentVO.wt1Above(WT1_OVERBOUGHT_THRESHOLD)) {

						switch (currentVO.getMv()) {
						case AP:
						case BP:
						case RP:
						case XP:
						case SN:
						case SP:
						{
							//if (currentVO.getHighestWt().compareTo(Indicators.MINUS_FIVE)>=0) {
							overFoundMoves.add(currentSg);
							all.add(currentSg);
							allOfThisSign.add(currentSg);
							if (previousMatchingSg != null) {
								double width = previousMatchingSg.getIndex().doubleValue()/currentSg.getIndex().doubleValue();
								if (width>=foundAlignmentMaxWidth) foundAlignmentMaxWidth = width;
							}
							previousMatchingSg = currentSg;
							foundFirst = true;
							break;
							//}
						}
						default: { 
							break; }
						}



					}


					// LH

					if (currentVO.isRpXpLh(RP_XP_THRESHOLD)) {
						lhHlMoves.add(currentSg);
						all.add(currentSg);
						allOfThisSign.add(currentSg);
						if (previousMatchingSg != null) {
							double width = previousMatchingSg.getIndex().doubleValue()/currentSg.getIndex().doubleValue();
							if (width>=foundAlignmentMaxWidth) foundAlignmentMaxWidth = width;
						}
						previousMatchingSg = currentSg;
						foundFirst = true;
					}
					
					/*if (currentSg.compareTo(trendRightSg)>=0) {
						if (currentVO.isXp(RP_XP_THRESHOLD) && currentVO.wt1Above(Indicators.TEN)) {
							// stalled, end of wave
							
							stalledTrendMoves.add(currentSg);
							all.add(currentSg);
							if (previousMatchingSg != null) {
								double width = previousMatchingSg.getIndex().doubleValue()/currentSg.getIndex().doubleValue();
								if (width>=foundAlignmentMaxWidth) foundAlignmentMaxWidth = width;
							}
							previousMatchingSg = currentSg;
							foundFirst = true;
						}
					}
					*/
					
						
					
					

					if (currentVO.isStLh() && (!currentVO.wt1Below(BigDecimal.ZERO))) {
						stalledlhhl.add(currentSg);
						all.add(currentSg);
						allOfThisSign.add(currentSg);
						if (previousMatchingSg != null) {
							double width = previousMatchingSg.getIndex().doubleValue()/currentSg.getIndex().doubleValue();
							if (width>=foundAlignmentMaxWidth) foundAlignmentMaxWidth = width;
						}
						previousMatchingSg = currentSg;
						foundFirst = true;
					}


					boolean foundDiv4 = false;
					boolean cancelDivergence = false;
					

					if (currentSg.compareTo(getTimeService().multiplyDoubleSg(moves.firstKey(), 3, live).getLeft())>0) {


						Pair<StatGranularity, StatGranularity> div4Sgs = getTimeService().divideDoubleSg(currentSg, 4, live);
						StatGranularity div4Sg = ((div4Sgs.getLeft()!=null)?div4Sgs.getLeft():div4Sgs.getRight());

						Pair<StatGranularity, StatGranularity> div3Sgs = getTimeService().divideDoubleSg(currentSg, 3, live);
						StatGranularity div3Sg = div3Sgs.getRight(); // ((div3Sgs.getRight()!=null)?div3Sgs.getRight():div3Sgs.getRight());

						StatGranularity divCurrentSg = div4Sg;
						StatVO divCurrentVO = null;

						while (divCurrentSg.compareTo(div3Sg)<=0) {
							divCurrentVO = moves.get(divCurrentSg);

							if ( (divCurrentVO.wt1Above(WT1_OVERBOUGHT_THRESHOLD) /* && 

									( //20250301
											(divCurrentVO.getDivergence() == null ) || (!divCurrentVO.getDivergence())

											)

*/
									) 
							
							
							
							)
							
							{

								wt8div4overs.add(currentSg);
								all.add(currentSg);
								allOfThisSign.add(currentSg);
								if (previousMatchingSg != null) {
									double width = previousMatchingSg.getIndex().doubleValue()/currentSg.getIndex().doubleValue();
									if (width>=foundAlignmentMaxWidth) foundAlignmentMaxWidth = width;
								}
								previousMatchingSg = currentSg;
								foundFirst = true;
								foundDiv4 = true;
								break;
							}
							
							/* 20250301
							 * 
							 */ if (wt8div4overs.contains(divCurrentSg)) {
							 
								
								cancelDivergence = true;
							}
							
							divCurrentSg = getTimeService().getNextSg(divCurrentSg, StatGranularity.S17457600, divCurrentSg, true, live);
						}

					}  else {
						//logger.info("divergence: " + currentSg + " ignoring as too small ");

						foundDiv4 = true;
					}

					// All of this sign
					
					/*if ((currentVO.wtAbove(Indicators.THREE))) {
						
						if (!currentSg.equals(minSg)) allOfThisSign.add(currentSg);
						
					}*/
					
					
					
					
					
/*
 * 20250301
 */
					/*if ( (currentVO.getDivergence() != null) &&  (currentVO.getDivergence())) {
						// divergent. Div4? else return null and cancel

						//logger.info("divergence: " + currentSg + " DIV ");
						if ((!foundDiv4)) {
							if (cancelDivergence) {
								logger.info("divergence: " + currentSg + " DIV CANCELED SELL " + currentVO.getDivergence());
							} else {
								logger.info("divergence: " + currentSg + " DIV BLOCKED SELL " + currentVO.getDivergence());
								blockedByDivergence = true;
							}
							
						}
					}

					if ( (currentVO.getSg().compareTo(minSg)>=0) 
						&& currentVO.isApBp(RP_XP_THRESHOLD) 
						&& 	   (currentVO.getWt1().signum()>0)
						&& (!all.contains(currentVO.getSg()))) {
						logger.info("_search_aligned: sell too early " + currentVO.getSg() + " minSg: " + minSg);
						logger.info("20250302 removed too early");

						//return null;
					}
					*/

				} else {

					
					if (foundFirst) {
						
						if (currentVO.isRnXn(RN_XN_THRESHOLD) ) {
							rxMoves.add(currentSg);
							if (previousMatchingSg != null) {
								double width = previousMatchingSg.getIndex().doubleValue()/currentSg.getIndex().doubleValue();
								if (width>=foundAlignmentMaxWidth) foundAlignmentMaxWidth = width;
							}
							previousMatchingSg = currentSg;
							
						}
					}
					// BLOCKER
					
					
					if (currentVO.wt1Below(Indicators.MINUS_SIXTY)) {
						blockerCandidates.add(currentSg);
					} else if (currentVO.wt1Below(Indicators.MINUS_FIFTY_FIVE)) {
						blockerCandidates.add(currentSg);
					} else if (currentVO.wt1Below(Indicators.MINUS_FIFTY_THREE)) {
						blockerCandidates.add(currentSg);
					} else if (currentVO.wt1Below(Indicators.MINUS_FIFTY)) {
						if (currentVO.getHighestWt().compareTo(Indicators.MINUS_FIVE)<=0) {
							if (currentVO.getMv().equals(SgMove.AN) || currentVO.getMv().equals(SgMove.BN)) {
								blockerCandidates.add(currentSg);
							}
						}
					} else if (currentVO.wt1Below(Indicators.MINUS_FOURTY_FIVE)) {
						if (currentVO.getHighestWt().compareTo(Indicators.MINUS_TWELVE)<=0) {
							if (currentVO.getMv().equals(SgMove.AN) || currentVO.getMv().equals(SgMove.BN)) {
								blockerCandidates.add(currentSg);
							}
						}
					} else if (currentVO.wt1Below(Indicators.MINUS_FOURTY)) {
						if (currentVO.getHighestWt().compareTo(Indicators.MINUS_FOURTEEN)<=0) {
							if (currentVO.getMv().equals(SgMove.AN) || currentVO.getMv().equals(SgMove.BN)) {
								blockerCandidates.add(currentSg);
							}
						}
					} else if (currentVO.wt1Below(Indicators.MINUS_THIRTY_FIVE)) {
						if (currentVO.getHighestWt().compareTo(Indicators.MINUS_EIGHTEEN)<=0) {
							if (currentVO.getMv().equals(SgMove.AN) || currentVO.getMv().equals(SgMove.BN)) {
								blockerCandidates.add(currentSg);
							}
						}
					} else if (currentVO.wt1Below(Indicators.MINUS_THIRTY)) {
						if (currentVO.getHighestWt().compareTo(Indicators.MINUS_TWENTY_FIVE)<=0) {
							if (currentVO.getMv().equals(SgMove.AN) || currentVO.getMv().equals(SgMove.BN)) {
								blockerCandidates.add(currentSg);
							}
						}
					}
					
					// WEAK
					
					/*if (enableWeak) {
						if (currentVO.wt1Above(Indicators.THIRTY)) {
							if (currentVO.isAnBn(Indicators.MINUS_SEVEN)) {
								weakMoves.add(currentSg);
								all.add(currentSg);
								allOfThisSign.add(currentSg);
								if (previousMatchingSg != null) {
									double width = previousMatchingSg.getIndex().doubleValue()/currentSg.getIndex().doubleValue();
									if (width>=foundAlignmentMaxWidth) foundAlignmentMaxWidth = width;
								}
								previousMatchingSg = currentSg;
								foundFirst = true;
							}
						}
						
					}*/
					
					
					
					// STALLED
					
					if (currentVO.wt1Below(Indicators.MINUS_THIRTY)) {

						switch (currentVO.getMv()) {
						case SN:
						case SP:
						{
							//if (currentVO.getHighestWt().compareTo(Indicators.MINUS_FIVE)>=0) {
							stalledMoves.add(currentSg);
							all.add(currentSg);
							allOfThisSign.add(currentSg);
							if (previousMatchingSg != null) {
								double width = previousMatchingSg.getIndex().doubleValue()/currentSg.getIndex().doubleValue();
								if (width>=foundAlignmentMaxWidth) foundAlignmentMaxWidth = width;
							}
							previousMatchingSg = currentSg;
							foundFirst = true;
							break;
							//}
						}
						default: { break; }
						}



					}
					// OVER

					if (currentVO.wt1Below(WT1_OVERSOLD_THRESHOLD)) {


						switch (currentVO.getMv()) {
						case AN:
						case BN:
						case RN:
						case XN:
						case SN:
						case SP:
						{
							//if (currentVO.getHighestWt().compareTo(Indicators.FIVE)<=0) {
							overFoundMoves.add(currentSg);
							all.add(currentSg);
							allOfThisSign.add(currentSg);
							if (previousMatchingSg != null) {
								double width = previousMatchingSg.getIndex().doubleValue()/currentSg.getIndex().doubleValue();
								if (width>=foundAlignmentMaxWidth) foundAlignmentMaxWidth = width;
							}
							previousMatchingSg = currentSg;
							foundFirst = true;
							break;
							//}
						}

						default: {
							break;
						}
						}





					}

					// HL

					if (currentVO.isRnXnHl(RN_XN_THRESHOLD)) {
						lhHlMoves.add(currentSg);
						all.add(currentSg);
						allOfThisSign.add(currentSg);
						if (previousMatchingSg != null) {
							double width = previousMatchingSg.getIndex().doubleValue()/currentSg.getIndex().doubleValue();
							if (width>=foundAlignmentMaxWidth) foundAlignmentMaxWidth = width;
						}
						previousMatchingSg = currentSg;
						foundFirst = true;
					}
					
					/*if (currentSg.compareTo(trendRightSg)>=0) {
						if (currentVO.isXn(RN_XN_THRESHOLD) && currentVO.wt1Below(Indicators.MINUS_TEN)) {
							stalledTrendMoves.add(currentSg);
							all.add(currentSg);
							if (previousMatchingSg != null) {
								double width = previousMatchingSg.getIndex().doubleValue()/currentSg.getIndex().doubleValue();
								if (width>=foundAlignmentMaxWidth) foundAlignmentMaxWidth = width;
							}
							previousMatchingSg = currentSg;
							foundFirst = true;
						}
					}*/
					
					
						
					
					
					
					
					if (currentVO.isStHl() && (!currentVO.wt1Above(BigDecimal.ZERO))) {
						stalledlhhl.add(currentSg);
						all.add(currentSg);
						allOfThisSign.add(currentSg);
						if (previousMatchingSg != null) {
							double width = previousMatchingSg.getIndex().doubleValue()/currentSg.getIndex().doubleValue();
							if (width>=foundAlignmentMaxWidth) foundAlignmentMaxWidth = width;
						}
						previousMatchingSg = currentSg;
						foundFirst = true;
					}

					/*if (!foundFirst) {
							if (currentVO.isRnXn(RN_XN_THRESHOLD)) {

									rxMoves.add(currentSg);
									all.add(currentSg);
									if (previousMatchingSg != null) {
										double width = previousMatchingSg.getIndex().doubleValue()/currentSg.getIndex().doubleValue();
										if (width>=foundAlignmentMaxWidth) foundAlignmentMaxWidth = width;
									}
									previousMatchingSg = currentSg;
									foundFirst = true;


							}
						}*/

					// DIV4

					//if (foundFirst) {
					boolean foundDiv4 = false;
					boolean cancelDivergence = false;
					
					//	if (currentVO.highestWtBelow(RN_XN_THRESHOLD)) {

					if (currentSg.compareTo(getTimeService().multiplyDoubleSg(moves.firstKey(), 3, live).getLeft())>0) {

						Pair<StatGranularity, StatGranularity> div4Sgs = getTimeService().divideDoubleSg(currentSg, 4, live);
						StatGranularity div4Sg = ((div4Sgs.getLeft()!=null)?div4Sgs.getLeft():div4Sgs.getRight());

						Pair<StatGranularity, StatGranularity> div3Sgs = getTimeService().divideDoubleSg(currentSg, 3, live);
						StatGranularity div3Sg = div3Sgs.getRight(); // ((div3Sgs.getRight()!=null)?div3Sgs.getRight():div3Sgs.getRight());

						StatGranularity divCurrentSg = div4Sg;
						StatVO divCurrentVO = null;

						while (divCurrentSg.compareTo(div3Sg)<=0) {
							divCurrentVO = moves.get(divCurrentSg);

							if ( (divCurrentVO.wt1Below(WT1_OVERSOLD_THRESHOLD)

									/*&& 

									(
											(divCurrentVO.getDivergence() == null ) || (divCurrentVO.getDivergence())

											)*/
									)
									)
									{
								wt8div4overs.add(currentSg);
								all.add(currentSg);
								if (previousMatchingSg != null) {
									double width = previousMatchingSg.getIndex().doubleValue()/currentSg.getIndex().doubleValue();
									if (width>=foundAlignmentMaxWidth) foundAlignmentMaxWidth = width;
								}
								previousMatchingSg = currentSg;
								foundFirst = true;
								foundDiv4 = true;
							}
							
							/*
							 * 20250301 */
							if (wt8div4overs.contains(divCurrentSg)) {
								
								cancelDivergence = true;
							}
							divCurrentSg = getTimeService().getNextSg(divCurrentSg, StatGranularity.S17457600, divCurrentSg, true, live);


						}



					} else {
						//logger.info("divergence: " + currentSg + " ignoring as too small ");

						foundDiv4 = true;
					}
					
					
					// All of this sign
					
					/*if ((currentVO.wtBelow(Indicators.MINUS_THREE))) {
						
						if (!currentSg.equals(minSg)) allOfThisSign.add(currentSg);
						
					}*/
					
					
					//}


					/*
					 * 20250301
					 */
					/*if ( (currentVO.getDivergence() != null) &&  (!currentVO.getDivergence())) {
					 
						// divergent. Div4? else return null and cancel
						if ((!foundDiv4)) {
							if (cancelDivergence) {
								logger.info("divergence: " + currentSg + " DIV CANCELED BUY " + currentVO.getDivergence());
							} else {
								logger.info("divergence: " + currentSg + " DIV BLOCKED BUY " + currentVO.getDivergence());
								blockedByDivergence = true;
							}
							
						}
					}
					
					*/
					
					
					
					/*if ( (currentVO.getSg().compareTo(minSg)>=0) 
						&&     currentVO.isAnBn(RN_XN_THRESHOLD) 
						&& 	   (currentVO.getWt1().signum()<0)
						&& (!all.contains(currentVO.getSg()))) {
						logger.info("_search_aligned: buy too early " + currentVO.getSg() + " minSg: " + minSg);
						logger.info("20250302 removed too early");

						//return null;
					}*/
					
					
					// MUL4 IS LHHL

					/*
							if (currentVO.isRnXn(RN_XN_THRESHOLD)) {
								StatGranularity mul4 = getTimeService().multiplyDoubleSg(currentSg, 4, false).getLeft();

								if (betweenMaxSg.compareTo(mul4)>=0) {
									StatVO mul4VO = moves.get(betweenMaxSg);

									if (mul4VO.isRnXnHl(RN_XN_THRESHOLD)) {
										rzNoLhHlmul4OK.add(currentSg);
										all.add(currentSg);
										if (previousMatchingSg != null) {
											double width = previousMatchingSg.getIndex().doubleValue()/currentSg.getIndex().doubleValue();
											if (width>=foundAlignmentMaxWidth) foundAlignmentMaxWidth = width;
										}
										previousMatchingSg = currentSg;
										foundFirst = true;
									}
								}
							}*/
					//	}



					/*switch (currentVO.getMv()) {
						case ST:
						case SP:
						case SN: {
							stalleds.add(currentSg);
							all.add(currentSg);
							if (previousMatchingSg != null) {
								double width = previousMatchingSg.getIndex().doubleValue()/currentSg.getIndex().doubleValue();
								if (width>=maxWidth) maxWidth = width;
							}
							previousMatchingSg = currentSg;
							break;
						}
						default: { break; }
						}*/
					//}

				}




			}

		}

		
		
		/*
		 * Overblockers
		 */
		/*
		if (overblockerCandidates.size()>0) {
			StatVO biggestOver = moves.get(overblockerCandidates.last());
			
			if (sign) {
				
				if (biggestOver.isOverBoughtDiverging(WT1_OVERBOUGHT_THRESHOLD)) {
					 
					 logger.info("_search_aligned: sell canceled by overblocker " + biggestOver.toShortMoveString());
				
					 return null;
						
				 }
			} else {
				if (biggestOver.isOverSoldDiverging(WT1_OVERSOLD_THRESHOLD)) {
					 
					 logger.info("_search_aligned: buy canceled by overblocker " + biggestOver.toShortMoveString());
				
					 return null;
						
				 }
			}
		}
		*/
		
		// Add RX if max low/high
		/*if (all.size()>0) {
			StatVO maxVO = moves.get(all.last());
			
			if (maxVO != null) {
				if (sign) {
					if (maxVO.wt1Below(Indicators.MINUS_THIRTY)) {
						all.addAll(rxMoves);

						disableBlockers = true;
					}
				} else {
					if (maxVO.wt1Above(Indicators.THIRTY)) {
						all.addAll(rxMoves);

						disableBlockers = true;
					}
					
				}
			}
		}*/
		/*
		 * Log
		 */

		
		
		if (all.size() > 0) {
			StringBuffer logData = new StringBuffer("Aligned ");

			if (sign) {

				logData.append(now);
				logData.append(": SELL_SIDE").append(all.size()).append("/").append(moves.size()).append(" maxWidth: ").append(foundAlignmentMaxWidth).append("\n");
			} else {
				logData.append(now);
				logData.append(": BUY_SIDE").append(all.size()).append("/").append(moves.size()).append(" maxWidth: ").append(foundAlignmentMaxWidth).append("\n");
			}
			for (StatGranularity sg: all.descendingSet()) {
				if (stalledTrendMoves.contains(sg)) {
					logData.append("TRSTLD ");
					logData.append(moves.get(sg).toMoveString()).append("\n"); 
				} else if (stalledMoves.contains(sg)) {
					logData.append("  STLD ");
					logData.append(moves.get(sg).toMoveString()).append("\n"); 
				} else if (stalledlhhl.contains(sg)) {
					logData.append("STLHHL ");
					logData.append(moves.get(sg).toMoveString()).append("\n");
				} else if (overFoundMoves.contains(sg)) {
					logData.append("  OVER ");
					logData.append(moves.get(sg).toMoveString()).append("\n");
				} else if (lhHlMoves.contains(sg)) {
					logData.append("  LHHL ");
					logData.append(moves.get(sg).toMoveString()).append("\n");
				} else if (wt8div4overs.contains(sg)) {
					logData.append("D4OVER ");
					logData.append(moves.get(sg).toMoveString()).append("\n");
				} else if (wt8div4lhhl.contains(sg)) {
					logData.append("D4LHHL ");
					logData.append(moves.get(sg).toMoveString()).append("\n");
				} else if (rzNoLhHlmul4OK.contains(sg)) {
					logData.append("  RZNO ");
					logData.append(moves.get(sg).toMoveString()).append("\n");
				} else if (rxMoves.contains(sg)) {
					logData.append("    RX ");
					logData.append(moves.get(sg).toMoveString()).append("\n");
				} else if (weakMoves.contains(sg)) {
					logData.append("  WEAK ");
					logData.append(moves.get(sg).toMoveString()).append("\n");
				} /*else if (counterDivergents.contains(sg)) {
					logData.append("COUNTER");
					logData.append(moves.get(sg).toMoveString()).append("\n");
				} else if (stalleds.contains(sg)) {
					logData.append("STALLED");
					logData.append(moves.get(sg).toMoveString()).append("\n");
				} */

			}
			logger.info(logData);
		}

		// SIGNAL?

		/*
		 *Blockers
		 */
		disableBlockers = true;
		if (blockerCandidates.size()>0) {
			
			StatGranularity blockSg = blockerCandidates.last();
			StatVO blockVO = moves.get(blockSg);
			
			if (sign) {
			//	if (blockVO.getDiff().compareTo(Indicators.ZERO_DOT_FIVE)>0) {
				if ((blockVO.getDiff().signum()>0)) {
					
						if (disableBlockers) {
							logger.info("_search_aligned: potential sell DISABLED blocker, diff>X.XX " + blockVO.toShortMoveString());
							
						} else {
							logger.info("_search_aligned: potential sell canceled by blocker, diff>X.XX " + blockVO.toShortMoveString());
							return null;
						}
						
						
				}
				
				 
			} else {
				
				//if (blockVO.getDiff().compareTo(Indicators.MINUS_ZERO_DOT_FIVE)<0) {
				if ((blockVO.getDiff().signum()<0))  {
					
					if (disableBlockers) {
						logger.info("_search_aligned: potential buy DISABLED blocker diff<X.XX " + blockVO.toShortMoveString());	
					} else {
						logger.info("_search_aligned: potential buy canceled by blocker, diff<X.XX " + blockVO.toShortMoveString());						
						return null;
					}
						
					//}
					
					
					
					//return null;
				}
				
				
						
				 
			}
		}
		
		
		

		
		
		/*
		 * 1. find aligned number #2. It MUST be HLLH or OVER. If not, try higher, until leftTrendSg.
		 * 
		 * from the minSg, 
		 */
		
		
		
		
		

		if (all.size() > 0) {

			
			
			StatGranularity highestFoundSg = all.last();
			
			if (highestFoundSg.compareTo(minSg)<0) {
				logger.info("_search_aligned_: highestFoundSg: " + highestFoundSg + " min wanted Sg: " + minSg + ", exiting");
				return null;
			}
			
			
			StatGranularity lastStalledSg = null;
			
			StatGranularity firstAfterStalledSg = null;
			
			for (StatGranularity sg: all.descendingSet()) {
				StatVO mv = moves.get(sg);
				
				//logger.info("processing " + sg);
				
				if ((lastStalledSg == null) || ((lastStalledSg.getIndex().doubleValue() /  sg.getIndex().doubleValue()) <1.5d)) {
					if (this._is_stalled(mv)) {
						if (sign) {
							
							/*
							if (buyOverBoughtSignals.size()>0) {
								StatGranularity highestSg = buyOverBoughtSignals.lastKey();
								
								if (highestSg.compareTo(sg)>=0) {
									
									break;
								}
								
								
							}
							*/
							if (mv.wt1Above(Indicators.THIRTY)) {
								lastStalledSg = sg;
							} /*else {
								firstAfterStalledSg = sg;
								break;
							}*/
						} else {
							if (mv.wt1Below(Indicators.MINUS_THIRTY)) {
								lastStalledSg = sg;
							} /*else {
								firstAfterStalledSg = sg;
								break;
							}*/
						}
					} else {
						firstAfterStalledSg = sg;
						break;
					}
				} else {
					firstAfterStalledSg = sg;
					break;
				}
				
				
			}
			
			if (lastStalledSg != null) {
				Pair<StatGranularity, StatGranularity> divStalledSgPair = getTimeService().divideDoubleSg(lastStalledSg, 8d, live);
				StatGranularity divStalledSg = ((divStalledSgPair.getLeft()!=null)?divStalledSgPair.getLeft():divStalledSgPair.getRight());
				
				if ((firstAfterStalledSg != null) && (firstAfterStalledSg.compareTo(divStalledSg)>=0)) {
					
					if (!wt8div4overs.contains(firstAfterStalledSg)) {
						logger.info("_search_aligned: stalled items at the beginning of the alignment, setting minSg to divStalledSg: " 
								+ divStalledSg + " was minSg: " + minSg
								+ " lastStalledSg: " + lastStalledSg 		
								+ " firstAfterStalledSg: " + firstAfterStalledSg 		
								 + " @" + now);
								
						minSg = firstAfterStalledSg;
					} else {
						logger.info("_search_aligned: stalled items at the beginning of the alignment, NOT setting minSg to divStalledSg: " 
								+ divStalledSg + " was minSg: " + minSg
								+ " lastStalledSg: " + lastStalledSg 		
								+ " firstAfterStalledSg: " + firstAfterStalledSg 
								+ " as firstAfterStalledSg is in wt8div4overs @" + now);
					}
					
					
				}
				
				
			} /*else {
				logger.info("_search_aligned: stalled items at the beginning of the alignment, setting minSg to highestFoundSg: " + highestFoundSg + " was minSg: " + minSg + " @" + now);
				
				minSg = highestFoundSg;
			}*/
			
			if (!all.contains(minSg)) {
				logger.info("_search_aligned_: highestFoundSg: " + highestFoundSg + " but min wanted Sg not in all: " + minSg + ", exiting");
				return null;
			}
			
			
			/*Pair<StatGranularity,StatGranularity> widthCheckFromSgPair = getTimeService().multiplyDoubleSg(minSg, 3, live);
			StatGranularity widthCheckFromSg = (widthCheckFromSgPair.getRight()!=null)?widthCheckFromSgPair.getRight():widthCheckFromSgPair.getLeft();

			if (widthCheckFromSg.compareTo(trendLeftSg)>0) {
				widthCheckFromSg = trendLeftSg;
			}
			StatGranularity lastSg = widthCheckFromSg;

			
			
			double requiredAlignmentMaxWidthAfterStalled = 8d;
			*/
			StatGranularity lastSg = null;
			boolean matchWidth = true;
			for (StatGranularity sg: all.descendingSet()) {

				if (sg.compareTo(minSg)<=0) {

					if (lastSg != null) {
						
						double width = lastSg.getIndex().doubleValue()/sg.getIndex().doubleValue();

						
						
						if (!(
								(width<=requiredAlignmentMaxWidth) 
							
							/*|| 
							
							(
							(
								stalledMoves.contains(lastSg)  || stalledTrendMoves.contains(lastSg)
							) 
							&& (stalledTrendMoves.size()>0)
							
							&& (width<=requiredAlignmentMaxWidthAfterStalled)
							
							) */
							
						)  ) {
							
							logger.info("_search_aligned: " + sg + " width: " + width + " reqWidth: " + requiredAlignmentMaxWidth + " lastSg: " + lastSg + " sg: " + sg);
							
							matchWidth = false;
							break;
						}
						
					} 

					lastSg = sg;
				}

			}
			if (matchWidth) {

				StatGranularity minAcceptableSg = null;

				// check if last found is small enough

				Pair<StatGranularity,StatGranularity> minAcceptableSgPair = getTimeService().multiplyDoubleSg(moves.firstKey(), 2, live);

				minAcceptableSg = (minAcceptableSgPair.getRight()!=null)?minAcceptableSgPair.getRight():minAcceptableSgPair.getLeft();

				if (all.iterator().next().compareTo(minAcceptableSg)<=0) {

					if (blockedByDivergence) {
						//logger.info("_search_aligned: trendSign: " + trendSign + " special: " + special);
						
							logger.info("_search_aligned: BLOCKED BY DIVERGENCE: highestFoundSg: " + highestFoundSg + " minSg: " + minSg + " maxWidth: " + foundAlignmentMaxWidth + " smaller ok");
							return null;
						


					} else {
						logger.info("_search_aligned: highestFoundSg: " + highestFoundSg + " minSg: " + minSg + " maxWidth: " + foundAlignmentMaxWidth + " smaller ok");
						return Pair.create(sign, minSg);
					}

				} else {
					logger.info("_search_aligned: highestFoundSg: " + highestFoundSg + " minSg: " + minSg + " maxWidth: " + foundAlignmentMaxWidth + " smaller NOT ok, exiting");

				}
			}
		




		}
		
		
		/*
		if (allOfThisSign.size() > 0) {

			
			
			StatGranularity highestFoundSg = allOfThisSign.last();
			
			
			if (highestFoundSg.compareTo(minSg)<0) {
				logger.info("_search_aligned_: highestFoundSg: " + highestFoundSg + " but min wanted Sg: " + minSg + ", exiting");
				return null;
			}
			
			
			logger.info("_search_aligned_: allOfThisSign: " + allOfThisSign.toString());
			
			StatGranularity lastSg = null;
			boolean matchWidth = true;
			for (StatGranularity sg: allOfThisSign.descendingSet()) {

				//if (sg.compareTo(minSg)<=0) {

					if (lastSg != null) {
						
						double width = lastSg.getIndex().doubleValue()/sg.getIndex().doubleValue();

						logger.info("_search_aligned: " + sg + " width: " + width + " reqWidth: " + 1.3d + " lastSg: " + lastSg + " sg: " + sg);
						
						
						if (!(
								(width<=1.3d) 
							
							
							
						)  ) {
							matchWidth = false;
							break;
						}
						
					} 

					lastSg = sg;
				//}

			}
			if (matchWidth) {

				StatGranularity minAcceptableSg = null;

				// check if last found is small enough

				Pair<StatGranularity,StatGranularity> minAcceptableSgPair = getTimeService().multiplyDoubleSg(moves.firstKey(), 2, live);

				minAcceptableSg = (minAcceptableSgPair.getRight()!=null)?minAcceptableSgPair.getRight():minAcceptableSgPair.getLeft();

				if (allOfThisSign.iterator().next().compareTo(minAcceptableSg)<=0) {

					if (blockedByDivergence) {
						//logger.info("_search_aligned: trendSign: " + trendSign + " special: " + special);
						
							logger.info("_search_aligned ALL: BLOCKED BY DIVERGENCE: highestFoundSg: " + highestFoundSg + " minSg: " + minSg + " maxWidth: " + foundAlignmentMaxWidth + " smaller ok");
							return null;
						


					} else {
						logger.info("_search_aligned ALL: highestFoundSg: " + highestFoundSg + " minSg: " + minSg + " maxWidth: " + foundAlignmentMaxWidth + " smaller ok");
						return Pair.create(sign, minSg);
					}

				} else {
					logger.info("_search_aligned ALL: highestFoundSg: " + highestFoundSg + " minSg: " + minSg + " maxWidth: " + foundAlignmentMaxWidth + " smaller NOT ok, exiting");

				}
			}
		




		}*/

		return null;

	}
	
	
	private Pair<Boolean, StatGranularity> _search_aligned_poly(
			StatGranularity trendLeftSg, 
			StatGranularity trendRightSg,
			StatGranularity minSg,
			Boolean trendSign,
			double firstMaxWidth,
			double requiredAlignmentMaxWidth,
			NavigableMap<StatGranularity, StatVO> moves, 
			Boolean sign, 
			Boolean live,
			Boolean enableWeak,
			Instant now) {


		NavigableSet<StatGranularity> stalledTrendMoves = new TreeSet<StatGranularity>();
		NavigableSet<StatGranularity> overFoundMoves = new TreeSet<StatGranularity>();
		NavigableSet<StatGranularity> blockerCandidates = new TreeSet<StatGranularity>();
		NavigableSet<StatGranularity> stalledMoves = new TreeSet<StatGranularity>();
		NavigableSet<StatGranularity> stalledlhhl = new TreeSet<StatGranularity>();
		NavigableSet<StatGranularity> lhHlMoves = new TreeSet<StatGranularity>();
		NavigableSet<StatGranularity> rxMoves = new TreeSet<StatGranularity>();
		NavigableSet<StatGranularity> wt8div4overs = new TreeSet<StatGranularity>();
		NavigableSet<StatGranularity> wt8div4lhhl = new TreeSet<StatGranularity>();
		NavigableSet<StatGranularity> weakMoves = new TreeSet<StatGranularity>();

		NavigableSet<StatGranularity> rxBelowWt8div4 = new TreeSet<StatGranularity>();
		

		NavigableSet<StatGranularity> rzNoLhHlmul4OK = new TreeSet<StatGranularity>();


		NavigableSet<StatGranularity> all = new TreeSet<StatGranularity>();

		
		NavigableSet<StatGranularity> allOfThisSign = new TreeSet<StatGranularity>();

		

		
		
		double foundAlignmentMaxWidth = 0d;

		boolean foundFirst = false;

		boolean blockedByDivergence = false;
		
		StatGranularity previousMatchingSg = null;

		boolean unstrict = false;
		
		boolean disableBlockers = false;

		
		
		
		
		
		
		for (StatGranularity currentSg: moves.keySet()) {

			if (currentSg.compareTo(trendLeftSg)<=0) { 
				StatVO currentVO = moves.get(currentSg);


				if (sign) {


					
					if (foundFirst) {
						if (currentVO.isRpXp(RP_XP_THRESHOLD)) {
							
							rxMoves.add(currentSg);
							if (previousMatchingSg != null) {
								double width = previousMatchingSg.getIndex().doubleValue()/currentSg.getIndex().doubleValue();
								if (width>=foundAlignmentMaxWidth) foundAlignmentMaxWidth = width;
							}
							previousMatchingSg = currentSg;
							foundFirst = true;
						}
					}
					// BLOCKER
					
						if (currentVO.wt1Above(Indicators.SIXTY)) {
							blockerCandidates.add(currentSg);
						} else if (currentVO.wt1Above(Indicators.FIFTY_FIVE)) {
							blockerCandidates.add(currentSg);
						} else if (currentVO.wt1Above(Indicators.FIFTY_THREE)) {
							blockerCandidates.add(currentSg);
						} else if (currentVO.wt1Above(Indicators.FIFTY)) {
							if (currentVO.getHighestWt().compareTo(Indicators.FIVE)>=0) {
								if (currentVO.getMv().equals(SgMove.AP) || currentVO.getMv().equals(SgMove.BP)) {
									blockerCandidates.add(currentSg);
								}
							}
						} else if (currentVO.wt1Above(Indicators.FOURTY_FIVE)) {
							if (currentVO.getHighestWt().compareTo(Indicators.TWELVE)>=0) {
								if (currentVO.getMv().equals(SgMove.AP) || currentVO.getMv().equals(SgMove.BP)) {
									blockerCandidates.add(currentSg);
								}
							}
						} else if (currentVO.wt1Above(Indicators.FOURTY)) {
							if (currentVO.getHighestWt().compareTo(Indicators.FOURTEEN)>=0) {
								if (currentVO.getMv().equals(SgMove.AP) || currentVO.getMv().equals(SgMove.BP)) {
									blockerCandidates.add(currentSg);
								}
							}
						} else if (currentVO.wt1Above(Indicators.THIRTY_FIVE)) {
							if (currentVO.getHighestWt().compareTo(Indicators.EIGHTEEN)>=0) {
								if (currentVO.getMv().equals(SgMove.AP) || currentVO.getMv().equals(SgMove.BP)) {
									blockerCandidates.add(currentSg);
								}
							}
						} else if (currentVO.wt1Above(Indicators.THIRTY)) {
							if (currentVO.getHighestWt().compareTo(Indicators.TWENTY_FIVE)>=0) {
								if (currentVO.getMv().equals(SgMove.AP) || currentVO.getMv().equals(SgMove.BP)) {
									blockerCandidates.add(currentSg);
								}
							}
						}
					
					
					// WEAK
					
					if (enableWeak) {
						if (currentVO.wt1Below(Indicators.MINUS_THIRTY)) {
							if (currentVO.isApBp(Indicators.SEVEN)) {
								weakMoves.add(currentSg);
								all.add(currentSg);
								allOfThisSign.add(currentSg);
								if (previousMatchingSg != null) {
									double width = previousMatchingSg.getIndex().doubleValue()/currentSg.getIndex().doubleValue();
									if (width>=foundAlignmentMaxWidth) foundAlignmentMaxWidth = width;
								}
								previousMatchingSg = currentSg;
								foundFirst = true;
							}
						}
					}
					
					
					
					
					// STALLED
					if (currentVO.wt1Above(Indicators.THIRTY)) {

						switch (currentVO.getMv()) {
						case SN:
						case SP:
						{
							//if (currentVO.getHighestWt().compareTo(Indicators.MINUS_FIVE)>=0) {
							stalledMoves.add(currentSg);
							all.add(currentSg);
							allOfThisSign.add(currentSg);
							if (previousMatchingSg != null) {
								double width = previousMatchingSg.getIndex().doubleValue()/currentSg.getIndex().doubleValue();
								if (width>=foundAlignmentMaxWidth) foundAlignmentMaxWidth = width;
							}
							previousMatchingSg = currentSg;
							foundFirst = true;
							break;
							//}
						}
						default: { break; }
						}



					}

					// OVER

					if (currentVO.wt1Above(WT1_OVERBOUGHT_THRESHOLD)) {

						switch (currentVO.getMv()) {
						case AP:
						case BP:
						case RP:
						case XP:
						case SN:
						case SP:
						{
							//if (currentVO.getHighestWt().compareTo(Indicators.MINUS_FIVE)>=0) {
							overFoundMoves.add(currentSg);
							all.add(currentSg);
							allOfThisSign.add(currentSg);
							if (previousMatchingSg != null) {
								double width = previousMatchingSg.getIndex().doubleValue()/currentSg.getIndex().doubleValue();
								if (width>=foundAlignmentMaxWidth) foundAlignmentMaxWidth = width;
							}
							previousMatchingSg = currentSg;
							foundFirst = true;
							break;
							//}
						}
						default: { 
							break; }
						}



					}


					// LH

					if (currentVO.isRpXpLh(RP_XP_THRESHOLD)) {
						lhHlMoves.add(currentSg);
						all.add(currentSg);
						allOfThisSign.add(currentSg);
						if (previousMatchingSg != null) {
							double width = previousMatchingSg.getIndex().doubleValue()/currentSg.getIndex().doubleValue();
							if (width>=foundAlignmentMaxWidth) foundAlignmentMaxWidth = width;
						}
						previousMatchingSg = currentSg;
						foundFirst = true;
					}
					
					/*if (currentSg.compareTo(trendRightSg)>=0) {
						if (currentVO.isXp(RP_XP_THRESHOLD) && currentVO.wt1Above(Indicators.TEN)) {
							// stalled, end of wave
							
							stalledTrendMoves.add(currentSg);
							all.add(currentSg);
							if (previousMatchingSg != null) {
								double width = previousMatchingSg.getIndex().doubleValue()/currentSg.getIndex().doubleValue();
								if (width>=foundAlignmentMaxWidth) foundAlignmentMaxWidth = width;
							}
							previousMatchingSg = currentSg;
							foundFirst = true;
						}
					}
					*/
					
						
					
					

					if (currentVO.isStLh() && (!currentVO.wt1Below(BigDecimal.ZERO))) {
						stalledlhhl.add(currentSg);
						all.add(currentSg);
						allOfThisSign.add(currentSg);
						if (previousMatchingSg != null) {
							double width = previousMatchingSg.getIndex().doubleValue()/currentSg.getIndex().doubleValue();
							if (width>=foundAlignmentMaxWidth) foundAlignmentMaxWidth = width;
						}
						previousMatchingSg = currentSg;
						foundFirst = true;
					}


					boolean foundDiv4 = false;
					boolean cancelDivergence = false;
					

					if (currentSg.compareTo(getTimeService().multiplyDoubleSg(moves.firstKey(), 3, live).getLeft())>0) {


						Pair<StatGranularity, StatGranularity> div4Sgs = getTimeService().divideDoubleSg(currentSg, 4, live);
						StatGranularity div4Sg = ((div4Sgs.getLeft()!=null)?div4Sgs.getLeft():div4Sgs.getRight());

						Pair<StatGranularity, StatGranularity> div3Sgs = getTimeService().divideDoubleSg(currentSg, 3, live);
						StatGranularity div3Sg = div3Sgs.getRight(); // ((div3Sgs.getRight()!=null)?div3Sgs.getRight():div3Sgs.getRight());

						StatGranularity divCurrentSg = div4Sg;
						StatVO divCurrentVO = null;

						while (divCurrentSg.compareTo(div3Sg)<=0) {
							divCurrentVO = moves.get(divCurrentSg);

							if ( (divCurrentVO.wt1Above(WT1_OVERBOUGHT_THRESHOLD) /* && 

									( //20250301
											(divCurrentVO.getDivergence() == null ) || (!divCurrentVO.getDivergence())

											)

*/
									) 
							
							
							
							)
							
							{

								wt8div4overs.add(currentSg);
								all.add(currentSg);
								allOfThisSign.add(currentSg);
								if (previousMatchingSg != null) {
									double width = previousMatchingSg.getIndex().doubleValue()/currentSg.getIndex().doubleValue();
									if (width>=foundAlignmentMaxWidth) foundAlignmentMaxWidth = width;
								}
								previousMatchingSg = currentSg;
								foundFirst = true;
								foundDiv4 = true;
								break;
							}
							
							/* 20250301
							 * 
							 */ if (wt8div4overs.contains(divCurrentSg)) {
							 
								
								cancelDivergence = true;
							}
							
							divCurrentSg = getTimeService().getNextSg(divCurrentSg, StatGranularity.S17457600, divCurrentSg, true, live);
						}

					}  else {
						//logger.info("divergence: " + currentSg + " ignoring as too small ");

						foundDiv4 = true;
					}

					// All of this sign
					
					/*if ((currentVO.wtAbove(Indicators.THREE))) {
						
						if (!currentSg.equals(minSg)) allOfThisSign.add(currentSg);
						
					}*/
					
					
					
					
					
/*
 * 20250301
 */
					/*if ( (currentVO.getDivergence() != null) &&  (currentVO.getDivergence())) {
						// divergent. Div4? else return null and cancel

						//logger.info("divergence: " + currentSg + " DIV ");
						if ((!foundDiv4)) {
							if (cancelDivergence) {
								logger.info("divergence: " + currentSg + " DIV CANCELED SELL " + currentVO.getDivergence());
							} else {
								logger.info("divergence: " + currentSg + " DIV BLOCKED SELL " + currentVO.getDivergence());
								blockedByDivergence = true;
							}
							
						}
					}

					if ( (currentVO.getSg().compareTo(minSg)>=0) 
						&& currentVO.isApBp(RP_XP_THRESHOLD) 
						&& 	   (currentVO.getWt1().signum()>0)
						&& (!all.contains(currentVO.getSg()))) {
						logger.info("_search_aligned: sell too early " + currentVO.getSg() + " minSg: " + minSg);
						logger.info("20250302 removed too early");

						//return null;
					}
					*/

				} else {

					
					if (foundFirst) {
						
						if (currentVO.isRnXn(RN_XN_THRESHOLD) ) {
							rxMoves.add(currentSg);
							if (previousMatchingSg != null) {
								double width = previousMatchingSg.getIndex().doubleValue()/currentSg.getIndex().doubleValue();
								if (width>=foundAlignmentMaxWidth) foundAlignmentMaxWidth = width;
							}
							previousMatchingSg = currentSg;
							
						}
					}
					// BLOCKER
					
					
					if (currentVO.wt1Below(Indicators.MINUS_SIXTY)) {
						blockerCandidates.add(currentSg);
					} else if (currentVO.wt1Below(Indicators.MINUS_FIFTY_FIVE)) {
						blockerCandidates.add(currentSg);
					} else if (currentVO.wt1Below(Indicators.MINUS_FIFTY_THREE)) {
						blockerCandidates.add(currentSg);
					} else if (currentVO.wt1Below(Indicators.MINUS_FIFTY)) {
						if (currentVO.getHighestWt().compareTo(Indicators.MINUS_FIVE)<=0) {
							if (currentVO.getMv().equals(SgMove.AN) || currentVO.getMv().equals(SgMove.BN)) {
								blockerCandidates.add(currentSg);
							}
						}
					} else if (currentVO.wt1Below(Indicators.MINUS_FOURTY_FIVE)) {
						if (currentVO.getHighestWt().compareTo(Indicators.MINUS_TWELVE)<=0) {
							if (currentVO.getMv().equals(SgMove.AN) || currentVO.getMv().equals(SgMove.BN)) {
								blockerCandidates.add(currentSg);
							}
						}
					} else if (currentVO.wt1Below(Indicators.MINUS_FOURTY)) {
						if (currentVO.getHighestWt().compareTo(Indicators.MINUS_FOURTEEN)<=0) {
							if (currentVO.getMv().equals(SgMove.AN) || currentVO.getMv().equals(SgMove.BN)) {
								blockerCandidates.add(currentSg);
							}
						}
					} else if (currentVO.wt1Below(Indicators.MINUS_THIRTY_FIVE)) {
						if (currentVO.getHighestWt().compareTo(Indicators.MINUS_EIGHTEEN)<=0) {
							if (currentVO.getMv().equals(SgMove.AN) || currentVO.getMv().equals(SgMove.BN)) {
								blockerCandidates.add(currentSg);
							}
						}
					} else if (currentVO.wt1Below(Indicators.MINUS_THIRTY)) {
						if (currentVO.getHighestWt().compareTo(Indicators.MINUS_TWENTY_FIVE)<=0) {
							if (currentVO.getMv().equals(SgMove.AN) || currentVO.getMv().equals(SgMove.BN)) {
								blockerCandidates.add(currentSg);
							}
						}
					}
					
					// WEAK
					
					if (enableWeak) {
						if (currentVO.wt1Above(Indicators.THIRTY)) {
							if (currentVO.isAnBn(Indicators.MINUS_SEVEN)) {
								weakMoves.add(currentSg);
								all.add(currentSg);
								allOfThisSign.add(currentSg);
								if (previousMatchingSg != null) {
									double width = previousMatchingSg.getIndex().doubleValue()/currentSg.getIndex().doubleValue();
									if (width>=foundAlignmentMaxWidth) foundAlignmentMaxWidth = width;
								}
								previousMatchingSg = currentSg;
								foundFirst = true;
							}
						}
						
					}
					
					
					
					// STALLED
					
					if (currentVO.wt1Below(Indicators.MINUS_THIRTY)) {

						switch (currentVO.getMv()) {
						case SN:
						case SP:
						{
							//if (currentVO.getHighestWt().compareTo(Indicators.MINUS_FIVE)>=0) {
							stalledMoves.add(currentSg);
							all.add(currentSg);
							allOfThisSign.add(currentSg);
							if (previousMatchingSg != null) {
								double width = previousMatchingSg.getIndex().doubleValue()/currentSg.getIndex().doubleValue();
								if (width>=foundAlignmentMaxWidth) foundAlignmentMaxWidth = width;
							}
							previousMatchingSg = currentSg;
							foundFirst = true;
							break;
							//}
						}
						default: { break; }
						}



					}
					// OVER

					if (currentVO.wt1Below(WT1_OVERSOLD_THRESHOLD)) {


						switch (currentVO.getMv()) {
						case AN:
						case BN:
						case RN:
						case XN:
						case SN:
						case SP:
						{
							//if (currentVO.getHighestWt().compareTo(Indicators.FIVE)<=0) {
							overFoundMoves.add(currentSg);
							all.add(currentSg);
							allOfThisSign.add(currentSg);
							if (previousMatchingSg != null) {
								double width = previousMatchingSg.getIndex().doubleValue()/currentSg.getIndex().doubleValue();
								if (width>=foundAlignmentMaxWidth) foundAlignmentMaxWidth = width;
							}
							previousMatchingSg = currentSg;
							foundFirst = true;
							break;
							//}
						}

						default: {
							break;
						}
						}





					}

					// HL

					if (currentVO.isRnXnHl(RN_XN_THRESHOLD)) {
						lhHlMoves.add(currentSg);
						all.add(currentSg);
						allOfThisSign.add(currentSg);
						if (previousMatchingSg != null) {
							double width = previousMatchingSg.getIndex().doubleValue()/currentSg.getIndex().doubleValue();
							if (width>=foundAlignmentMaxWidth) foundAlignmentMaxWidth = width;
						}
						previousMatchingSg = currentSg;
						foundFirst = true;
					}
					
					/*if (currentSg.compareTo(trendRightSg)>=0) {
						if (currentVO.isXn(RN_XN_THRESHOLD) && currentVO.wt1Below(Indicators.MINUS_TEN)) {
							stalledTrendMoves.add(currentSg);
							all.add(currentSg);
							if (previousMatchingSg != null) {
								double width = previousMatchingSg.getIndex().doubleValue()/currentSg.getIndex().doubleValue();
								if (width>=foundAlignmentMaxWidth) foundAlignmentMaxWidth = width;
							}
							previousMatchingSg = currentSg;
							foundFirst = true;
						}
					}*/
					
					
						
					
					
					
					
					if (currentVO.isStHl() && (!currentVO.wt1Above(BigDecimal.ZERO))) {
						stalledlhhl.add(currentSg);
						all.add(currentSg);
						allOfThisSign.add(currentSg);
						if (previousMatchingSg != null) {
							double width = previousMatchingSg.getIndex().doubleValue()/currentSg.getIndex().doubleValue();
							if (width>=foundAlignmentMaxWidth) foundAlignmentMaxWidth = width;
						}
						previousMatchingSg = currentSg;
						foundFirst = true;
					}

					/*if (!foundFirst) {
							if (currentVO.isRnXn(RN_XN_THRESHOLD)) {

									rxMoves.add(currentSg);
									all.add(currentSg);
									if (previousMatchingSg != null) {
										double width = previousMatchingSg.getIndex().doubleValue()/currentSg.getIndex().doubleValue();
										if (width>=foundAlignmentMaxWidth) foundAlignmentMaxWidth = width;
									}
									previousMatchingSg = currentSg;
									foundFirst = true;


							}
						}*/

					// DIV4

					//if (foundFirst) {
					boolean foundDiv4 = false;
					boolean cancelDivergence = false;
					
					//	if (currentVO.highestWtBelow(RN_XN_THRESHOLD)) {

					if (currentSg.compareTo(getTimeService().multiplyDoubleSg(moves.firstKey(), 3, live).getLeft())>0) {

						Pair<StatGranularity, StatGranularity> div4Sgs = getTimeService().divideDoubleSg(currentSg, 4, live);
						StatGranularity div4Sg = ((div4Sgs.getLeft()!=null)?div4Sgs.getLeft():div4Sgs.getRight());

						Pair<StatGranularity, StatGranularity> div3Sgs = getTimeService().divideDoubleSg(currentSg, 3, live);
						StatGranularity div3Sg = div3Sgs.getRight(); // ((div3Sgs.getRight()!=null)?div3Sgs.getRight():div3Sgs.getRight());

						StatGranularity divCurrentSg = div4Sg;
						StatVO divCurrentVO = null;

						while (divCurrentSg.compareTo(div3Sg)<=0) {
							divCurrentVO = moves.get(divCurrentSg);

							if ( (divCurrentVO.wt1Below(WT1_OVERSOLD_THRESHOLD)

									/*&& 

									(
											(divCurrentVO.getDivergence() == null ) || (divCurrentVO.getDivergence())

											)*/
									)
									)
									{
								wt8div4overs.add(currentSg);
								all.add(currentSg);
								if (previousMatchingSg != null) {
									double width = previousMatchingSg.getIndex().doubleValue()/currentSg.getIndex().doubleValue();
									if (width>=foundAlignmentMaxWidth) foundAlignmentMaxWidth = width;
								}
								previousMatchingSg = currentSg;
								foundFirst = true;
								foundDiv4 = true;
							}
							
							/*
							 * 20250301 */
							if (wt8div4overs.contains(divCurrentSg)) {
								
								cancelDivergence = true;
							}
							divCurrentSg = getTimeService().getNextSg(divCurrentSg, StatGranularity.S17457600, divCurrentSg, true, live);


						}



					} else {
						//logger.info("divergence: " + currentSg + " ignoring as too small ");

						foundDiv4 = true;
					}
					
					
					// All of this sign
					
					/*if ((currentVO.wtBelow(Indicators.MINUS_THREE))) {
						
						if (!currentSg.equals(minSg)) allOfThisSign.add(currentSg);
						
					}*/
					
					
					//}


					/*
					 * 20250301
					 */
					/*if ( (currentVO.getDivergence() != null) &&  (!currentVO.getDivergence())) {
					 
						// divergent. Div4? else return null and cancel
						if ((!foundDiv4)) {
							if (cancelDivergence) {
								logger.info("divergence: " + currentSg + " DIV CANCELED BUY " + currentVO.getDivergence());
							} else {
								logger.info("divergence: " + currentSg + " DIV BLOCKED BUY " + currentVO.getDivergence());
								blockedByDivergence = true;
							}
							
						}
					}
					
					*/
					
					
					
					/*if ( (currentVO.getSg().compareTo(minSg)>=0) 
						&&     currentVO.isAnBn(RN_XN_THRESHOLD) 
						&& 	   (currentVO.getWt1().signum()<0)
						&& (!all.contains(currentVO.getSg()))) {
						logger.info("_search_aligned: buy too early " + currentVO.getSg() + " minSg: " + minSg);
						logger.info("20250302 removed too early");

						//return null;
					}*/
					
					
					// MUL4 IS LHHL

					/*
							if (currentVO.isRnXn(RN_XN_THRESHOLD)) {
								StatGranularity mul4 = getTimeService().multiplyDoubleSg(currentSg, 4, false).getLeft();

								if (betweenMaxSg.compareTo(mul4)>=0) {
									StatVO mul4VO = moves.get(betweenMaxSg);

									if (mul4VO.isRnXnHl(RN_XN_THRESHOLD)) {
										rzNoLhHlmul4OK.add(currentSg);
										all.add(currentSg);
										if (previousMatchingSg != null) {
											double width = previousMatchingSg.getIndex().doubleValue()/currentSg.getIndex().doubleValue();
											if (width>=foundAlignmentMaxWidth) foundAlignmentMaxWidth = width;
										}
										previousMatchingSg = currentSg;
										foundFirst = true;
									}
								}
							}*/
					//	}



					/*switch (currentVO.getMv()) {
						case ST:
						case SP:
						case SN: {
							stalleds.add(currentSg);
							all.add(currentSg);
							if (previousMatchingSg != null) {
								double width = previousMatchingSg.getIndex().doubleValue()/currentSg.getIndex().doubleValue();
								if (width>=maxWidth) maxWidth = width;
							}
							previousMatchingSg = currentSg;
							break;
						}
						default: { break; }
						}*/
					//}

				}




			}

		}

		
		
		/*
		 * Overblockers
		 */
		/*
		if (overblockerCandidates.size()>0) {
			StatVO biggestOver = moves.get(overblockerCandidates.last());
			
			if (sign) {
				
				if (biggestOver.isOverBoughtDiverging(WT1_OVERBOUGHT_THRESHOLD)) {
					 
					 logger.info("_search_aligned: sell canceled by overblocker " + biggestOver.toShortMoveString());
				
					 return null;
						
				 }
			} else {
				if (biggestOver.isOverSoldDiverging(WT1_OVERSOLD_THRESHOLD)) {
					 
					 logger.info("_search_aligned: buy canceled by overblocker " + biggestOver.toShortMoveString());
				
					 return null;
						
				 }
			}
		}
		*/
		
		// Add RX if max low/high
		if (all.size()>0) {
			StatVO maxVO = moves.get(all.last());
			
			if (maxVO != null) {
				if (sign) {
					if (maxVO.wt1Below(Indicators.MINUS_THIRTY)) {
						all.addAll(rxMoves);

						disableBlockers = true;
					}
				} else {
					if (maxVO.wt1Above(Indicators.THIRTY)) {
						all.addAll(rxMoves);

						disableBlockers = true;
					}
					
				}
			}
		}
		/*
		 * Log
		 */

		
		
		if (all.size() > 0) {
			StringBuffer logData = new StringBuffer("Aligned ");

			if (sign) {

				logData.append(now);
				logData.append(": SELL_SIDE").append(all.size()).append("/").append(moves.size()).append(" maxWidth: ").append(foundAlignmentMaxWidth).append("\n");
			} else {
				logData.append(now);
				logData.append(": BUY_SIDE").append(all.size()).append("/").append(moves.size()).append(" maxWidth: ").append(foundAlignmentMaxWidth).append("\n");
			}
			for (StatGranularity sg: all.descendingSet()) {
				if (stalledTrendMoves.contains(sg)) {
					logData.append("TRSTLD");
					logData.append(moves.get(sg).toMoveString()).append("\n"); 
				} else if (stalledMoves.contains(sg)) {
					logData.append("  STLD");
					logData.append(moves.get(sg).toMoveString()).append("\n"); 
				} else if (stalledlhhl.contains(sg)) {
					logData.append("STLHHL");
					logData.append(moves.get(sg).toMoveString()).append("\n");
				} else if (overFoundMoves.contains(sg)) {
					logData.append("  OVER");
					logData.append(moves.get(sg).toMoveString()).append("\n");
				} else if (lhHlMoves.contains(sg)) {
					logData.append("  LHHL");
					logData.append(moves.get(sg).toMoveString()).append("\n");
				} else if (wt8div4overs.contains(sg)) {
					logData.append("D4OVER");
					logData.append(moves.get(sg).toMoveString()).append("\n");
				} else if (wt8div4lhhl.contains(sg)) {
					logData.append("D4LHHL");
					logData.append(moves.get(sg).toMoveString()).append("\n");
				} else if (rzNoLhHlmul4OK.contains(sg)) {
					logData.append("  RZNO");
					logData.append(moves.get(sg).toMoveString()).append("\n");
				} else if (rxMoves.contains(sg)) {
					logData.append("    RX");
					logData.append(moves.get(sg).toMoveString()).append("\n");
				} else if (weakMoves.contains(sg)) {
					logData.append("  WEAK");
					logData.append(moves.get(sg).toMoveString()).append("\n");
				} /*else if (counterDivergents.contains(sg)) {
					logData.append("COUNTER");
					logData.append(moves.get(sg).toMoveString()).append("\n");
				} else if (stalleds.contains(sg)) {
					logData.append("STALLED");
					logData.append(moves.get(sg).toMoveString()).append("\n");
				} */

			}
			logger.info(logData);
		}

		// SIGNAL?

		/*
		 *Blockers
		 */
		disableBlockers = true;
		if (blockerCandidates.size()>0) {
			
			StatGranularity blockSg = blockerCandidates.last();
			StatVO blockVO = moves.get(blockSg);
			
			if (sign) {
			//	if (blockVO.getDiff().compareTo(Indicators.ZERO_DOT_FIVE)>0) {
				if ((blockVO.getDiff().signum()>0)) {
					
						if (disableBlockers) {
							logger.info("_search_aligned: potential sell DISABLED blocker, diff>X.XX " + blockVO.toShortMoveString());
							
						} else {
							logger.info("_search_aligned: potential sell canceled by blocker, diff>X.XX " + blockVO.toShortMoveString());
							return null;
						}
						
						
				}
				
				 
			} else {
				
				//if (blockVO.getDiff().compareTo(Indicators.MINUS_ZERO_DOT_FIVE)<0) {
				if ((blockVO.getDiff().signum()<0))  {
					
					if (disableBlockers) {
						logger.info("_search_aligned: potential buy DISABLED blocker diff<X.XX " + blockVO.toShortMoveString());	
					} else {
						logger.info("_search_aligned: potential buy canceled by blocker, diff<X.XX " + blockVO.toShortMoveString());						
						return null;
					}
						
					//}
					
					
					
					//return null;
				}
				
				
						
				 
			}
		}
		
		
		

		
		
		/*
		 * 1. find aligned number #2. It MUST be HLLH or OVER. If not, try higher, until leftTrendSg.
		 * 
		 * from the minSg, 
		 */
		
		
		
		
		

		if (all.size() > 0) {

			
			
			StatGranularity highestFoundSg = all.last();
			
			
			
			if (!all.contains(minSg)) {
				logger.info("_search_aligned_: highestFoundSg: " + highestFoundSg + " but min wanted Sg not in all: " + minSg + ", exiting");
				return null;
			}
			
			
			
			/*Pair<StatGranularity,StatGranularity> widthCheckFromSgPair = getTimeService().multiplyDoubleSg(minSg, 3, live);
			StatGranularity widthCheckFromSg = (widthCheckFromSgPair.getRight()!=null)?widthCheckFromSgPair.getRight():widthCheckFromSgPair.getLeft();

			if (widthCheckFromSg.compareTo(trendLeftSg)>0) {
				widthCheckFromSg = trendLeftSg;
			}
			StatGranularity lastSg = widthCheckFromSg;

			
			
			double requiredAlignmentMaxWidthAfterStalled = 8d;
			*/
			StatGranularity lastSg = null;
			boolean matchWidth = true;
			for (StatGranularity sg: all.descendingSet()) {

				if (sg.compareTo(minSg)<=0) {

					if (lastSg != null) {
						
						double width = lastSg.getIndex().doubleValue()/sg.getIndex().doubleValue();

						logger.info("_search_aligned: " + sg + " width: " + width + " reqWidth: " + requiredAlignmentMaxWidth + " lastSg: " + lastSg + " sg: " + sg);
						
						
						if (!(
								(width<=requiredAlignmentMaxWidth) 
							
							/*|| 
							
							(
							(
								stalledMoves.contains(lastSg)  || stalledTrendMoves.contains(lastSg)
							) 
							&& (stalledTrendMoves.size()>0)
							
							&& (width<=requiredAlignmentMaxWidthAfterStalled)
							
							) */
							
						)  ) {
							matchWidth = false;
							break;
						}
						
					} 

					lastSg = sg;
				}

			}
			if (matchWidth) {

				StatGranularity minAcceptableSg = null;

				// check if last found is small enough

				Pair<StatGranularity,StatGranularity> minAcceptableSgPair = getTimeService().multiplyDoubleSg(moves.firstKey(), 2, live);

				minAcceptableSg = (minAcceptableSgPair.getRight()!=null)?minAcceptableSgPair.getRight():minAcceptableSgPair.getLeft();

				if (all.iterator().next().compareTo(minAcceptableSg)<=0) {

					if (blockedByDivergence) {
						//logger.info("_search_aligned: trendSign: " + trendSign + " special: " + special);
						
							logger.info("_search_aligned: BLOCKED BY DIVERGENCE: highestFoundSg: " + highestFoundSg + " minSg: " + minSg + " maxWidth: " + foundAlignmentMaxWidth + " smaller ok");
							return null;
						


					} else {
						logger.info("_search_aligned: highestFoundSg: " + highestFoundSg + " minSg: " + minSg + " maxWidth: " + foundAlignmentMaxWidth + " smaller ok");
						return Pair.create(sign, minSg);
					}

				} else {
					logger.info("_search_aligned: highestFoundSg: " + highestFoundSg + " minSg: " + minSg + " maxWidth: " + foundAlignmentMaxWidth + " smaller NOT ok, exiting");

				}
			}
		




		}
		
		
		/*
		if (allOfThisSign.size() > 0) {

			
			
			StatGranularity highestFoundSg = allOfThisSign.last();
			
			
			if (highestFoundSg.compareTo(minSg)<0) {
				logger.info("_search_aligned_: highestFoundSg: " + highestFoundSg + " but min wanted Sg: " + minSg + ", exiting");
				return null;
			}
			
			
			logger.info("_search_aligned_: allOfThisSign: " + allOfThisSign.toString());
			
			StatGranularity lastSg = null;
			boolean matchWidth = true;
			for (StatGranularity sg: allOfThisSign.descendingSet()) {

				//if (sg.compareTo(minSg)<=0) {

					if (lastSg != null) {
						
						double width = lastSg.getIndex().doubleValue()/sg.getIndex().doubleValue();

						logger.info("_search_aligned: " + sg + " width: " + width + " reqWidth: " + 1.3d + " lastSg: " + lastSg + " sg: " + sg);
						
						
						if (!(
								(width<=1.3d) 
							
							
							
						)  ) {
							matchWidth = false;
							break;
						}
						
					} 

					lastSg = sg;
				//}

			}
			if (matchWidth) {

				StatGranularity minAcceptableSg = null;

				// check if last found is small enough

				Pair<StatGranularity,StatGranularity> minAcceptableSgPair = getTimeService().multiplyDoubleSg(moves.firstKey(), 2, live);

				minAcceptableSg = (minAcceptableSgPair.getRight()!=null)?minAcceptableSgPair.getRight():minAcceptableSgPair.getLeft();

				if (allOfThisSign.iterator().next().compareTo(minAcceptableSg)<=0) {

					if (blockedByDivergence) {
						//logger.info("_search_aligned: trendSign: " + trendSign + " special: " + special);
						
							logger.info("_search_aligned ALL: BLOCKED BY DIVERGENCE: highestFoundSg: " + highestFoundSg + " minSg: " + minSg + " maxWidth: " + foundAlignmentMaxWidth + " smaller ok");
							return null;
						


					} else {
						logger.info("_search_aligned ALL: highestFoundSg: " + highestFoundSg + " minSg: " + minSg + " maxWidth: " + foundAlignmentMaxWidth + " smaller ok");
						return Pair.create(sign, minSg);
					}

				} else {
					logger.info("_search_aligned ALL: highestFoundSg: " + highestFoundSg + " minSg: " + minSg + " maxWidth: " + foundAlignmentMaxWidth + " smaller NOT ok, exiting");

				}
			}
		




		}*/

		return null;

	}
	
	
	
	private StatGranularity _search_next_clean(
			StatGranularity maxSg, 
			StatGranularity minSg,
			NavigableMap<StatGranularity, StatVO> moves, 
			Boolean sign, 
			Boolean live,
			Boolean withRx,
			Instant now) {


		NavigableSet<StatGranularity> stalledTrendMoves = new TreeSet<StatGranularity>();
		NavigableSet<StatGranularity> overFoundMoves = new TreeSet<StatGranularity>();
		NavigableSet<StatGranularity> blockerCandidates = new TreeSet<StatGranularity>();
		NavigableSet<StatGranularity> stalledMoves = new TreeSet<StatGranularity>();
		NavigableSet<StatGranularity> stalledlhhl = new TreeSet<StatGranularity>();
		NavigableSet<StatGranularity> lhHlMoves = new TreeSet<StatGranularity>();
		NavigableSet<StatGranularity> rxMoves = new TreeSet<StatGranularity>();
		NavigableSet<StatGranularity> wt8div4overs = new TreeSet<StatGranularity>();
		NavigableSet<StatGranularity> wt8div4lhhl = new TreeSet<StatGranularity>();
		NavigableSet<StatGranularity> weakMoves = new TreeSet<StatGranularity>();

		NavigableSet<StatGranularity> rxBelowWt8div4 = new TreeSet<StatGranularity>();
		

		NavigableSet<StatGranularity> rzNoLhHlmul4OK = new TreeSet<StatGranularity>();


		NavigableSet<StatGranularity> all = new TreeSet<StatGranularity>();

		
		NavigableSet<StatGranularity> allOfThisSign = new TreeSet<StatGranularity>();

		
		NavigableSet<StatGranularity> matchMinimumHighestLhhl = new TreeSet<StatGranularity>();

		
		

		for (StatGranularity currentSg: moves.keySet()) {

			if ((currentSg.compareTo(maxSg)<=0)) { 
				StatVO currentVO = moves.get(currentSg);

				if (sign) {
					
					// STALLED
					if (currentVO.wt1Above(Indicators.THIRTY)) {
						switch (currentVO.getMv()) {
						case SN:
						case SP:
						{
							stalledMoves.add(currentSg);
							all.add(currentSg);
							break;
						}
						default: { break; }
						}
					}

					// OVER

					if (currentVO.wt1Above(WT1_OVERBOUGHT_THRESHOLD)) {

						switch (currentVO.getMv()) {
						case AP:
						case BP:
						case RP:
						case XP:
						case SN:
						case SP:
						{
							overFoundMoves.add(currentSg);
							all.add(currentSg);
							break;
						}
						default: { 
							break; }
						}

					}


					// LH

					if (currentVO.isRpXpLh(RP_XP_THRESHOLD)) {
						lhHlMoves.add(currentSg);
						all.add(currentSg);
						
					}
					
					// stalled

					if (currentVO.isStLh() && (!currentVO.wt1Below(BigDecimal.ZERO))) {
						stalledlhhl.add(currentSg);
						all.add(currentSg);
						
					}

					boolean foundDiv4 = false;
					
					
					if (currentSg.compareTo(getTimeService().multiplyDoubleSg(moves.firstKey(), 3, live).getLeft())>0) {


						Pair<StatGranularity, StatGranularity> div4Sgs = getTimeService().divideDoubleSg(currentSg, 4, live);
						StatGranularity div4Sg = ((div4Sgs.getLeft()!=null)?div4Sgs.getLeft():div4Sgs.getRight());

						Pair<StatGranularity, StatGranularity> div3Sgs = getTimeService().divideDoubleSg(currentSg, 3, live);
						StatGranularity div3Sg = div3Sgs.getRight(); // ((div3Sgs.getRight()!=null)?div3Sgs.getRight():div3Sgs.getRight());

						StatGranularity divCurrentSg = div4Sg;
						StatVO divCurrentVO = null;

						while (divCurrentSg.compareTo(div3Sg)<=0) {
							divCurrentVO = moves.get(divCurrentSg);

							if ( (divCurrentVO.wt1Above(WT1_OVERBOUGHT_THRESHOLD)

									) 
							
							
							
							)
							
							{

								wt8div4overs.add(currentSg);
								all.add(currentSg);
								foundDiv4 = true;
								break;
							}
							
														
							divCurrentSg = getTimeService().getNextSg(divCurrentSg, StatGranularity.S17457600, divCurrentSg, true, live);
						}

					}  


				} else {

				
					
					
					
					
					// STALLED
					
					if (currentVO.wt1Below(Indicators.MINUS_THIRTY)) {

						switch (currentVO.getMv()) {
						case SN:
						case SP:
						{
							stalledMoves.add(currentSg);
							all.add(currentSg);
							
							break;
						}
						default: { break; }
						}



					}
					// OVER

					if (currentVO.wt1Below(WT1_OVERSOLD_THRESHOLD)) {


						switch (currentVO.getMv()) {
						case AN:
						case BN:
						case RN:
						case XN:
						case SN:
						case SP:
						{
							overFoundMoves.add(currentSg);
							all.add(currentSg);
							
							break;
						}

						default: {
							break;
						}
						}





					}

					// HL

					if (currentVO.isRnXnHl(RN_XN_THRESHOLD)) {
						lhHlMoves.add(currentSg);
						all.add(currentSg);
						
					}
					
					
					
					if (currentVO.isStHl() && (!currentVO.wt1Above(BigDecimal.ZERO))) {
						stalledlhhl.add(currentSg);
						all.add(currentSg);
						
					}

					

					// DIV4

					//if (foundFirst) {
					boolean foundDiv4 = false;
					
					//	if (currentVO.highestWtBelow(RN_XN_THRESHOLD)) {

					if (currentSg.compareTo(getTimeService().multiplyDoubleSg(moves.firstKey(), 3, live).getLeft())>0) {

						Pair<StatGranularity, StatGranularity> div4Sgs = getTimeService().divideDoubleSg(currentSg, 4, live);
						StatGranularity div4Sg = ((div4Sgs.getLeft()!=null)?div4Sgs.getLeft():div4Sgs.getRight());

						Pair<StatGranularity, StatGranularity> div3Sgs = getTimeService().divideDoubleSg(currentSg, 3, live);
						StatGranularity div3Sg = div3Sgs.getRight(); // ((div3Sgs.getRight()!=null)?div3Sgs.getRight():div3Sgs.getRight());

						StatGranularity divCurrentSg = div4Sg;
						StatVO divCurrentVO = null;

						while (divCurrentSg.compareTo(div3Sg)<=0) {
							divCurrentVO = moves.get(divCurrentSg);

							if ( (divCurrentVO.wt1Below(WT1_OVERSOLD_THRESHOLD)

									)
									)
									{
								wt8div4overs.add(currentSg);
								all.add(currentSg);
								foundDiv4 = true;
							}
							
							
							divCurrentSg = getTimeService().getNextSg(divCurrentSg, StatGranularity.S17457600, divCurrentSg, true, live);


						}



					}


					

				}

			}


			}


			/*
			 * 1. find aligned number #2. It MUST be HLLH or OVER. If not, try higher, until leftTrendSg.
			 * 
			 * from the minSg, 
			 */
			
			
			
			
			logger.info("_search_next_clean: " + all.toString() + " maxSg:  " + maxSg + " minSg: " + minSg);

			if (all.size() > 0) {

				StatGranularity selectedSg2 = null;
				
				for (StatGranularity currentSg: moves.keySet()) {
					
					if (currentSg.compareTo(minSg)>=0) { 
					
						if (all.contains(currentSg) || matchMinimumHighestLhhl.contains(currentSg)) {
							selectedSg2 = currentSg;
						}
						
					}
					if (selectedSg2!=null) break;
				}
				
				return selectedSg2; 
				
			}	
			

			return null;
	}
	
	
	private NavigableSet<StatGranularity> _build_over_div4(
			StatGranularity maxSg, 
			StatGranularity minSg,
			NavigableMap<StatGranularity, StatVO> moves, 
			Boolean sign, 
			Boolean live,
			Boolean withRx,
			Instant now) {


		NavigableSet<StatGranularity> overFoundMoves = new TreeSet<StatGranularity>();
		
		NavigableSet<StatGranularity> wt8div4overs = new TreeSet<StatGranularity>();
		

		NavigableSet<StatGranularity> all = new TreeSet<StatGranularity>();

		NavigableSet<StatGranularity> matchMinimumHighestLhhl = new TreeSet<StatGranularity>();

		
		

		for (StatGranularity currentSg: moves.keySet()) {

			if ((currentSg.compareTo(maxSg)<=0)) { 
				StatVO currentVO = moves.get(currentSg);

				if (sign) {
					
					

					// OVER

					if (currentVO.wt1Above(WT1_OVERBOUGHT_THRESHOLD)) {

						switch (currentVO.getMv()) {
						case AP:
						case BP:
						case RP:
						case XP:
						case SN:
						case SP:
						{
							overFoundMoves.add(currentSg);
							all.add(currentSg);
							break;
						}
						default: { 
							break; }
						}

					}


					

					boolean foundDiv4 = false;
					
					
					if (currentSg.compareTo(getTimeService().multiplyDoubleSg(moves.firstKey(), 3, live).getLeft())>0) {


						Pair<StatGranularity, StatGranularity> div4Sgs = getTimeService().divideDoubleSg(currentSg, 4, live);
						StatGranularity div4Sg = ((div4Sgs.getLeft()!=null)?div4Sgs.getLeft():div4Sgs.getRight());

						Pair<StatGranularity, StatGranularity> div3Sgs = getTimeService().divideDoubleSg(currentSg, 3, live);
						StatGranularity div3Sg = div3Sgs.getRight(); // ((div3Sgs.getRight()!=null)?div3Sgs.getRight():div3Sgs.getRight());

						StatGranularity divCurrentSg = div4Sg;
						StatVO divCurrentVO = null;

						while (divCurrentSg.compareTo(div3Sg)<=0) {
							divCurrentVO = moves.get(divCurrentSg);

							if ( (divCurrentVO.wt1Above(WT1_OVERBOUGHT_THRESHOLD)

									) 
							
							
							
							)
							
							{

								wt8div4overs.add(currentSg);
								all.add(currentSg);
								foundDiv4 = true;
								break;
							}
							
														
							divCurrentSg = getTimeService().getNextSg(divCurrentSg, StatGranularity.S17457600, divCurrentSg, true, live);
						}

					}  


				} else {

				
										// OVER

					if (currentVO.wt1Below(WT1_OVERSOLD_THRESHOLD)) {


						switch (currentVO.getMv()) {
						case AN:
						case BN:
						case RN:
						case XN:
						case SN:
						case SP:
						{
							overFoundMoves.add(currentSg);
							all.add(currentSg);
							
							break;
						}

						default: {
							break;
						}
						}





					}

					

					

					// DIV4

					//if (foundFirst) {
					boolean foundDiv4 = false;
					
					//	if (currentVO.highestWtBelow(RN_XN_THRESHOLD)) {

					if (currentSg.compareTo(getTimeService().multiplyDoubleSg(moves.firstKey(), 3, live).getLeft())>0) {

						Pair<StatGranularity, StatGranularity> div4Sgs = getTimeService().divideDoubleSg(currentSg, 4, live);
						StatGranularity div4Sg = ((div4Sgs.getLeft()!=null)?div4Sgs.getLeft():div4Sgs.getRight());

						Pair<StatGranularity, StatGranularity> div3Sgs = getTimeService().divideDoubleSg(currentSg, 3, live);
						StatGranularity div3Sg = div3Sgs.getRight(); // ((div3Sgs.getRight()!=null)?div3Sgs.getRight():div3Sgs.getRight());

						StatGranularity divCurrentSg = div4Sg;
						StatVO divCurrentVO = null;

						while (divCurrentSg.compareTo(div3Sg)<=0) {
							divCurrentVO = moves.get(divCurrentSg);

							if ( (divCurrentVO.wt1Below(WT1_OVERSOLD_THRESHOLD)

									)
									)
									{
								wt8div4overs.add(currentSg);
								all.add(currentSg);
								foundDiv4 = true;
							}
							
							
							divCurrentSg = getTimeService().getNextSg(divCurrentSg, StatGranularity.S17457600, divCurrentSg, true, live);


						}



					}


					

				}

			}


			}


			

			return wt8div4overs;
	}
	
	private StatGranularity _search_next_clean_desc(
			Pair<StatVO, StatVO> trend, 
			StatGranularity minSg,
			NavigableMap<StatGranularity, StatVO> moves, 
			Boolean sign, 
			Boolean live,
			Boolean withRx,
			Instant now) {


		
		NavigableSet<StatGranularity> overFoundMoves = new TreeSet<StatGranularity>();
		NavigableSet<StatGranularity> lhHlMoves = new TreeSet<StatGranularity>();
		NavigableSet<StatGranularity> wt8div4overs = new TreeSet<StatGranularity>();
		
		NavigableSet<StatGranularity> all = new TreeSet<StatGranularity>();

		NavigableSet<StatGranularity> matchMinimumHighestLhhl = new TreeSet<StatGranularity>();

		StatGranularity foundFirst = null;
		
		
		for (StatGranularity currentSg: moves.keySet()) {

			if ((currentSg.compareTo(trend.getLeft().getSg())<=0)) { 
				StatVO currentVO = moves.get(currentSg);

				if (sign) {
					
					// STALLED
					/*if (currentVO.wt1Above(Indicators.THIRTY)) {
						switch (currentVO.getMv()) {
						case SN:
						case SP:
						{
							stalledMoves.add(currentSg);
							all.add(currentSg);
							break;
						}
						default: { break; }
						}
					}*/

					// OVER

					if (currentVO.wt1Above(WT1_OVERBOUGHT_THRESHOLD)) {

						switch (currentVO.getMv()) {
						case AP:
						case BP:
						case RP:
						case XP:
						case SN:
						case SP:
						{
							overFoundMoves.add(currentSg);
							all.add(currentSg);
							break;
						}
						default: { 
							break; }
						}

					}


					// LH

					if (currentVO.isRpXpLh(RP_XP_THRESHOLD)) {
						lhHlMoves.add(currentSg);
						all.add(currentSg);
						
					}
					
					// stalled

					/*if (currentVO.isStLh() && (!currentVO.wt1Below(BigDecimal.ZERO))) {
						stalledlhhl.add(currentSg);
						all.add(currentSg);
						
					}*/

					boolean foundDiv4 = false;
					
					
					if (currentSg.compareTo(getTimeService().multiplyDoubleSg(moves.firstKey(), 3, live).getLeft())>0) {


						Pair<StatGranularity, StatGranularity> div4Sgs = getTimeService().divideDoubleSg(currentSg, 4, live);
						StatGranularity div4Sg = ((div4Sgs.getLeft()!=null)?div4Sgs.getLeft():div4Sgs.getRight());

						Pair<StatGranularity, StatGranularity> div3Sgs = getTimeService().divideDoubleSg(currentSg, 3, live);
						StatGranularity div3Sg = div3Sgs.getRight(); // ((div3Sgs.getRight()!=null)?div3Sgs.getRight():div3Sgs.getRight());

						StatGranularity divCurrentSg = div4Sg;
						StatVO divCurrentVO = null;

						while (divCurrentSg.compareTo(div3Sg)<=0) {
							divCurrentVO = moves.get(divCurrentSg);

							if ( (divCurrentVO.wt1Above(WT1_OVERBOUGHT_THRESHOLD)

									) 
							
							
							
							)
							
							{

								wt8div4overs.add(currentSg);
								all.add(currentSg);
								foundDiv4 = true;
								break;
							}
							
														
							divCurrentSg = getTimeService().getNextSg(divCurrentSg, StatGranularity.S17457600, divCurrentSg, true, live);
						}

					}  


				} else {

				
					
					
					
					
					// STALLED
					
					/*if (currentVO.wt1Below(Indicators.MINUS_THIRTY)) {

						switch (currentVO.getMv()) {
						case SN:
						case SP:
						{
							stalledMoves.add(currentSg);
							all.add(currentSg);
							
							break;
						}
						default: { break; }
						}



					}*/
					// OVER

					if (currentVO.wt1Below(WT1_OVERSOLD_THRESHOLD)) {


						switch (currentVO.getMv()) {
						case AN:
						case BN:
						case RN:
						case XN:
						case SN:
						case SP:
						{
							overFoundMoves.add(currentSg);
							all.add(currentSg);
							
							break;
						}

						default: {
							break;
						}
						}





					}

					// HL

					if (currentVO.isRnXnHl(RN_XN_THRESHOLD)) {
						lhHlMoves.add(currentSg);
						all.add(currentSg);
						
					}
					
					
					/*
					if (currentVO.isStHl() && (!currentVO.wt1Above(BigDecimal.ZERO))) {
						stalledlhhl.add(currentSg);
						all.add(currentSg);
						
					}
					 */
					

					// DIV4

					//if (foundFirst) {
					boolean foundDiv4 = false;
					
					//	if (currentVO.highestWtBelow(RN_XN_THRESHOLD)) {

					if (currentSg.compareTo(getTimeService().multiplyDoubleSg(moves.firstKey(), 3, live).getLeft())>0) {

						Pair<StatGranularity, StatGranularity> div4Sgs = getTimeService().divideDoubleSg(currentSg, 4, live);
						StatGranularity div4Sg = ((div4Sgs.getLeft()!=null)?div4Sgs.getLeft():div4Sgs.getRight());

						Pair<StatGranularity, StatGranularity> div3Sgs = getTimeService().divideDoubleSg(currentSg, 3, live);
						StatGranularity div3Sg = div3Sgs.getRight(); // ((div3Sgs.getRight()!=null)?div3Sgs.getRight():div3Sgs.getRight());

						StatGranularity divCurrentSg = div4Sg;
						StatVO divCurrentVO = null;

						while (divCurrentSg.compareTo(div3Sg)<=0) {
							divCurrentVO = moves.get(divCurrentSg);

							if ( (divCurrentVO.wt1Below(WT1_OVERSOLD_THRESHOLD)

									)
									)
									{
								wt8div4overs.add(currentSg);
								all.add(currentSg);
								foundDiv4 = true;
							}
							
							
							divCurrentSg = getTimeService().getNextSg(divCurrentSg, StatGranularity.S17457600, divCurrentSg, true, live);


						}



					}


					

				}

			}


			}


			/*
			 * 1. find aligned number #2. It MUST be HLLH or OVER. If not, try higher, until leftTrendSg.
			 * 
			 * from the minSg, 
			 */
			
			
			
			
			logger.info("_search_next_clean: " + all.toString() + " trendLeft:  " + trend.getLeft().getSg() + " trendRight: " + trend.getRight().getSg());

			if (all.size() > 0) {

				StatGranularity selectedSg2 = null;
				
				for (StatGranularity currentSg: moves.descendingKeySet()) {
					
					if (currentSg.compareTo(trend.getRight().getSg())>=0) { 
					
						if (all.contains(currentSg) || matchMinimumHighestLhhl.contains(currentSg)) {
							selectedSg2 = currentSg;
						}
						
					}
					if (selectedSg2!=null) break;
				}
				
				return selectedSg2; 
				
			}	
			

			return null;
	}
		
	
	private StatGranularity _search_next(
			StatGranularity trendLeftSg, 
			StatGranularity trendRightSg,
			StatGranularity minSg,
			Boolean trendSign,
			double requiredAlignmentMaxWidth,
			NavigableMap<StatGranularity, StatVO> moves, 
			Boolean sign, 
			Boolean live,
			Boolean enableWeak,
			Instant now) {


		NavigableSet<StatGranularity> stalledTrendMoves = new TreeSet<StatGranularity>();
		NavigableSet<StatGranularity> overFoundMoves = new TreeSet<StatGranularity>();
		NavigableSet<StatGranularity> blockerCandidates = new TreeSet<StatGranularity>();
		NavigableSet<StatGranularity> stalledMoves = new TreeSet<StatGranularity>();
		NavigableSet<StatGranularity> stalledlhhl = new TreeSet<StatGranularity>();
		NavigableSet<StatGranularity> lhHlMoves = new TreeSet<StatGranularity>();
		NavigableSet<StatGranularity> rxMoves = new TreeSet<StatGranularity>();
		NavigableSet<StatGranularity> wt8div4overs = new TreeSet<StatGranularity>();
		NavigableSet<StatGranularity> wt8div4lhhl = new TreeSet<StatGranularity>();
		NavigableSet<StatGranularity> weakMoves = new TreeSet<StatGranularity>();

		NavigableSet<StatGranularity> rxBelowWt8div4 = new TreeSet<StatGranularity>();
		

		NavigableSet<StatGranularity> rzNoLhHlmul4OK = new TreeSet<StatGranularity>();


		NavigableSet<StatGranularity> all = new TreeSet<StatGranularity>();

		
		NavigableSet<StatGranularity> allOfThisSign = new TreeSet<StatGranularity>();

		
		NavigableSet<StatGranularity> matchMinimumHighestLhhl = new TreeSet<StatGranularity>();

		
		
		double foundAlignmentMaxWidth = 0d;

		boolean foundFirst = false;

		boolean blockedByDivergence = false;
		
		StatGranularity previousMatchingSg = null;


		

		for (StatGranularity currentSg: moves.keySet()) {

			if ((currentSg.compareTo(trendLeftSg)<=0)) { 
				StatVO currentVO = moves.get(currentSg);


				if (sign) {

					
					// WEAK
					
					if (enableWeak) {
						if (currentVO.wt1Below(Indicators.MINUS_THIRTY)) {
							if (currentVO.isApBp(this.RP_XP_THRESHOLD)) {
								if (currentVO.getDecreasingHighs()) {
									weakMoves.add(currentSg);
									all.add(currentSg);
									if (previousMatchingSg != null) {
										double width = previousMatchingSg.getIndex().doubleValue()/currentSg.getIndex().doubleValue();
										if (width>=foundAlignmentMaxWidth) foundAlignmentMaxWidth = width;
									}
									previousMatchingSg = currentSg;
									foundFirst = true;
								}
								
							}
						}
					}
					
					
					
					// STALLED
					if (currentVO.wt1Above(Indicators.THIRTY)) {

						switch (currentVO.getMv()) {
						case SN:
						case SP:
						{
							//if (currentVO.getHighestWt().compareTo(Indicators.MINUS_FIVE)>=0) {
							stalledMoves.add(currentSg);
							all.add(currentSg);
							if (previousMatchingSg != null) {
								double width = previousMatchingSg.getIndex().doubleValue()/currentSg.getIndex().doubleValue();
								if (width>=foundAlignmentMaxWidth) foundAlignmentMaxWidth = width;
							}
							previousMatchingSg = currentSg;
							foundFirst = true;
							break;
							//}
						}
						default: { break; }
						}



					}

					// OVER

					if (currentVO.wt1Above(WT1_OVERBOUGHT_THRESHOLD)) {

						switch (currentVO.getMv()) {
						case AP:
						case BP:
						case RP:
						case XP:
						case SN:
						case SP:
						{
							//if (currentVO.getHighestWt().compareTo(Indicators.MINUS_FIVE)>=0) {
							overFoundMoves.add(currentSg);
							all.add(currentSg);
							if (previousMatchingSg != null) {
								double width = previousMatchingSg.getIndex().doubleValue()/currentSg.getIndex().doubleValue();
								if (width>=foundAlignmentMaxWidth) foundAlignmentMaxWidth = width;
							}
							previousMatchingSg = currentSg;
							foundFirst = true;
							break;
							//}
						}
						default: { 
							break; }
						}



					}


					// LH

					if (currentVO.isRpXpLh(RP_XP_THRESHOLD)) {
						lhHlMoves.add(currentSg);
						all.add(currentSg);
						if (previousMatchingSg != null) {
							double width = previousMatchingSg.getIndex().doubleValue()/currentSg.getIndex().doubleValue();
							if (width>=foundAlignmentMaxWidth) foundAlignmentMaxWidth = width;
						}
						previousMatchingSg = currentSg;
						foundFirst = true;
					}
					
					

					if (currentVO.isStLh() && (!currentVO.wt1Below(BigDecimal.ZERO))) {
						stalledlhhl.add(currentSg);
						all.add(currentSg);
						if (previousMatchingSg != null) {
							double width = previousMatchingSg.getIndex().doubleValue()/currentSg.getIndex().doubleValue();
							if (width>=foundAlignmentMaxWidth) foundAlignmentMaxWidth = width;
						}
						previousMatchingSg = currentSg;
						foundFirst = true;
					}


					boolean foundDiv4 = false;
					boolean cancelDivergence = false;
					

					if (currentSg.compareTo(getTimeService().multiplyDoubleSg(moves.firstKey(), 3, live).getLeft())>0) {


						Pair<StatGranularity, StatGranularity> div4Sgs = getTimeService().divideDoubleSg(currentSg, 4, live);
						StatGranularity div4Sg = ((div4Sgs.getLeft()!=null)?div4Sgs.getLeft():div4Sgs.getRight());

						Pair<StatGranularity, StatGranularity> div3Sgs = getTimeService().divideDoubleSg(currentSg, 3, live);
						StatGranularity div3Sg = div3Sgs.getRight(); // ((div3Sgs.getRight()!=null)?div3Sgs.getRight():div3Sgs.getRight());

						StatGranularity divCurrentSg = div4Sg;
						StatVO divCurrentVO = null;

						while (divCurrentSg.compareTo(div3Sg)<=0) {
							divCurrentVO = moves.get(divCurrentSg);

							if ( (divCurrentVO.wt1Above(WT1_OVERBOUGHT_THRESHOLD) /* && 

									( //20250301
											(divCurrentVO.getDivergence() == null ) || (!divCurrentVO.getDivergence())

											)
*/

									) 
							
							
							
							)
							
							{

								wt8div4overs.add(currentSg);
								all.add(currentSg);
								if (previousMatchingSg != null) {
									double width = previousMatchingSg.getIndex().doubleValue()/currentSg.getIndex().doubleValue();
									if (width>=foundAlignmentMaxWidth) foundAlignmentMaxWidth = width;
								}
								previousMatchingSg = currentSg;
								foundFirst = true;
								foundDiv4 = true;
								break;
							}
							
							/* 20250301
							 * 
							 */ if (wt8div4overs.contains(divCurrentSg)) {
							 
								
								cancelDivergence = true;
							}
							
							divCurrentSg = getTimeService().getNextSg(divCurrentSg, StatGranularity.S17457600, divCurrentSg, true, live);
						}

					}  else {
						//logger.info("divergence: " + currentSg + " ignoring as too small ");

						foundDiv4 = true;
					}


				} else {

				
					// WEAK
					
					if (enableWeak) {
						if (currentVO.wt1Above(Indicators.THIRTY)) {
							if (currentVO.isAnBn(this.RN_XN_THRESHOLD)) {
								if (currentVO.getIncreasingLows()) {
									weakMoves.add(currentSg);
									all.add(currentSg);
									if (previousMatchingSg != null) {
										double width = previousMatchingSg.getIndex().doubleValue()/currentSg.getIndex().doubleValue();
										if (width>=foundAlignmentMaxWidth) foundAlignmentMaxWidth = width;
									}
									previousMatchingSg = currentSg;
									foundFirst = true;
								}
								
							}
						}
						
					}
					
					
					// STALLED
					
					if (currentVO.wt1Below(Indicators.MINUS_THIRTY)) {

						switch (currentVO.getMv()) {
						case SN:
						case SP:
						{
							//if (currentVO.getHighestWt().compareTo(Indicators.MINUS_FIVE)>=0) {
							stalledMoves.add(currentSg);
							all.add(currentSg);
							if (previousMatchingSg != null) {
								double width = previousMatchingSg.getIndex().doubleValue()/currentSg.getIndex().doubleValue();
								if (width>=foundAlignmentMaxWidth) foundAlignmentMaxWidth = width;
							}
							previousMatchingSg = currentSg;
							foundFirst = true;
							break;
							//}
						}
						default: { break; }
						}



					}
					// OVER

					if (currentVO.wt1Below(WT1_OVERSOLD_THRESHOLD)) {


						switch (currentVO.getMv()) {
						case AN:
						case BN:
						case RN:
						case XN:
						case SN:
						case SP:
						{
							//if (currentVO.getHighestWt().compareTo(Indicators.FIVE)<=0) {
							overFoundMoves.add(currentSg);
							all.add(currentSg);
							if (previousMatchingSg != null) {
								double width = previousMatchingSg.getIndex().doubleValue()/currentSg.getIndex().doubleValue();
								if (width>=foundAlignmentMaxWidth) foundAlignmentMaxWidth = width;
							}
							previousMatchingSg = currentSg;
							foundFirst = true;
							break;
							//}
						}

						default: {
							break;
						}
						}





					}

					// HL

					if (currentVO.isRnXnHl(RN_XN_THRESHOLD)) {
						lhHlMoves.add(currentSg);
						all.add(currentSg);
						if (previousMatchingSg != null) {
							double width = previousMatchingSg.getIndex().doubleValue()/currentSg.getIndex().doubleValue();
							if (width>=foundAlignmentMaxWidth) foundAlignmentMaxWidth = width;
						}
						previousMatchingSg = currentSg;
						foundFirst = true;
					}
					
					
					
					if (currentVO.isStHl() && (!currentVO.wt1Above(BigDecimal.ZERO))) {
						stalledlhhl.add(currentSg);
						all.add(currentSg);
						if (previousMatchingSg != null) {
							double width = previousMatchingSg.getIndex().doubleValue()/currentSg.getIndex().doubleValue();
							if (width>=foundAlignmentMaxWidth) foundAlignmentMaxWidth = width;
						}
						previousMatchingSg = currentSg;
						foundFirst = true;
					}

					

					// DIV4

					//if (foundFirst) {
					boolean foundDiv4 = false;
					boolean cancelDivergence = false;
					
					//	if (currentVO.highestWtBelow(RN_XN_THRESHOLD)) {

					if (currentSg.compareTo(getTimeService().multiplyDoubleSg(moves.firstKey(), 3, live).getLeft())>0) {

						Pair<StatGranularity, StatGranularity> div4Sgs = getTimeService().divideDoubleSg(currentSg, 4, live);
						StatGranularity div4Sg = ((div4Sgs.getLeft()!=null)?div4Sgs.getLeft():div4Sgs.getRight());

						Pair<StatGranularity, StatGranularity> div3Sgs = getTimeService().divideDoubleSg(currentSg, 3, live);
						StatGranularity div3Sg = div3Sgs.getRight(); // ((div3Sgs.getRight()!=null)?div3Sgs.getRight():div3Sgs.getRight());

						StatGranularity divCurrentSg = div4Sg;
						StatVO divCurrentVO = null;

						while (divCurrentSg.compareTo(div3Sg)<=0) {
							divCurrentVO = moves.get(divCurrentSg);

							if ( (divCurrentVO.wt1Below(WT1_OVERSOLD_THRESHOLD)

								/*	&& 

									(
											(divCurrentVO.getDivergence() == null ) || (divCurrentVO.getDivergence())

											)*/
									)
									)
									{
								wt8div4overs.add(currentSg);
								all.add(currentSg);
								if (previousMatchingSg != null) {
									double width = previousMatchingSg.getIndex().doubleValue()/currentSg.getIndex().doubleValue();
									if (width>=foundAlignmentMaxWidth) foundAlignmentMaxWidth = width;
								}
								previousMatchingSg = currentSg;
								foundFirst = true;
								foundDiv4 = true;
							}
							
							/*
							 * 20250301 */
							if (wt8div4overs.contains(divCurrentSg)) {
								
								cancelDivergence = true;
							}
							divCurrentSg = getTimeService().getNextSg(divCurrentSg, StatGranularity.S17457600, divCurrentSg, true, live);


						}



					} else {
						//logger.info("divergence: " + currentSg + " ignoring as too small ");

						foundDiv4 = true;
					}
					//}


					

				}




			}

		}

		
		
		
		
		/*
		 * 1. find aligned number #2. It MUST be HLLH or OVER. If not, try higher, until leftTrendSg.
		 * 
		 * from the minSg, 
		 */
		
		
		
		
		logger.info("_search_next: " + all.toString() + " minSg: " + minSg);

		if (all.size() > 0) {

			StatGranularity selectedSg2 = null;
			
			for (StatGranularity currentSg: moves.keySet()) {
				
				if (currentSg.compareTo(minSg)>=0) { 
				
					if (all.contains(currentSg) || matchMinimumHighestLhhl.contains(currentSg)) {
						selectedSg2 = currentSg;
					}
					
				}
				if (selectedSg2!=null) break;
			}
			
			return selectedSg2; 
			
		}	
		

		return null;

	}
	

	public BigDecimal getRN_XN_THRESHOLD() {
		return RN_XN_THRESHOLD;
	}

	public void setRN_XN_THRESHOLD(BigDecimal rN_XN_THRESHOLD) {
		RN_XN_THRESHOLD = rN_XN_THRESHOLD;
	}

	public BigDecimal getRP_XP_THRESHOLD() {
		return RP_XP_THRESHOLD;
	}

	public void setRP_XP_THRESHOLD(BigDecimal rP_XP_THRESHOLD) {
		RP_XP_THRESHOLD = rP_XP_THRESHOLD;
	}

	public BigDecimal getWT1_OVERBOUGHT_THRESHOLD() {
		return WT1_OVERBOUGHT_THRESHOLD;
	}

	public void setWT1_OVERBOUGHT_THRESHOLD(BigDecimal wT1_OVERBOUGHT_THRESHOLD) {
		WT1_OVERBOUGHT_THRESHOLD = wT1_OVERBOUGHT_THRESHOLD;
	}

	public BigDecimal getWT1_OVERSOLD_THRESHOLD() {
		return WT1_OVERSOLD_THRESHOLD;
	}

	public void setWT1_OVERSOLD_THRESHOLD(BigDecimal wT1_OVERSOLD_THRESHOLD) {
		WT1_OVERSOLD_THRESHOLD = wT1_OVERSOLD_THRESHOLD;
	}




	// exceptions:

	// divergence

	/*if (trendSign) {
		// positive trend ie XP
		if (trendMv.getDiff().compareTo(Indicators.ONE)>=0) {
			minSellSg = highestSg;
			maxSellSg = highestSg;
			maxAlignmentWidth = 2d;
			//minSellSgHighestSgDivergentTrendSg = true;
			opts = "minSellSgHighestSgDivergentTrendSg";
		}
	} else {
		// negative trend ie XN

		if (trendMv.getDiff().compareTo(Indicators.MINUS_ONE)<=0) {
			minBuySg = highestSg;
			maxBuySg = highestSg;
			maxAlignmentWidth = 2d;
			//minBuySgHighestSgDivergentTrendSg = true;
			opts = "minBuySgHighestSgDivergentTrendSg";
		}
	}
	 */
	/*
	 *  small trendSg
	 */

	/*Pair<StatGranularity, StatGranularity> div8 = getTimeService().divideDoubleSg(highestSg, 8, live);
	StatGranularity div8Sg = div8.getLeft();
	if (div8Sg == null) {
		div8Sg = div8.getRight();
	}

	if (trendSign) {
		// positive trend ie XP
		if (trendSg.compareTo(div8Sg)<=0) {
			minSellSg = highestSg;
			maxSellSg = highestSg;
			maxAlignmentWidth = 2d;
			//minSellSgHighestSgTooSmallTrendSg = true;
			opts = "minSellSgHighestSgTooSmallTrendSg";
		}

	} else {
		// negative trend ie XN
		if (trendSg.compareTo(div8Sg)<=0) {
			minBuySg = highestSg;
			maxBuySg = highestSg;
			maxAlignmentWidth = 2d;
			//minBuySgHighestSgTooSmallTrendSg = true;
			opts = "minBuySgHighestSgTooSmallTrendSg";
		}

	}
	 */

	/*
	if (sign) {


		if (diff<0) {
			sellmul = sellmul - diff*coef; //5 -- 5 = 10

			buymul = buymul + diff*coef; // 5 - 5 = 0 => 3
			if (buymul < min) {
				buymul = min;
			}
		} else {
			sellmul = sellmul - diff*coef;  //5 - 5 = 0 => 3

			if (sellmul < min) {
				sellmul = min;
			}

			buymul = buymul + diff*coef; //5 + 5 = 10
		}
	} else {


		if (diff>0) {
			buymul = buymul + diff*coef; // 5 + 5 => 10

			sellmul = sellmul - diff*coef;  //5 - 5 = 0 => 3

			if (sellmul < min) {
				sellmul = min;
			}

		} else {

			sellmul = sellmul - diff*coef; //5 -- 5 = 10

			buymul = buymul + diff*coef; // 5 - 5 = 0 => 3
			if (buymul < min) {
				buymul = min;
			}
		}
	}

	 */


	
	public double getMul(BigDecimal diff, boolean sell) {
		
		// diff -5 
		
		double res = 5d;
		
		if (!sell) diff = diff.negate();
		
		if (diff.signum()>=0) {
			res = 5d - diff.doubleValue()*0.25d;
			if (res <3d) res = 3d;
			
		} else if (diff.compareTo(Indicators.MINUS_ONE)>=0) {
			
			res = 8d;
			
			//res = diff.multiply(diff, Indicators.roundIndicators).negate().subtract((new BigDecimal(6.2d).multiply(diff, Indicators.roundIndicators)).add(new BigDecimal(5.2d))).doubleValue();
			
			
		} else if (diff.compareTo(Indicators.MINUS_THREE)>=0) {
			
			res = -1d * Math.pow(diff.doubleValue(), 2d) - 6.2d*diff.doubleValue() + 5.2d;
			
			//res = diff.multiply(diff, Indicators.roundIndicators).negate().subtract((new BigDecimal(6.2d).multiply(diff, Indicators.roundIndicators)).add(new BigDecimal(5.2d))).doubleValue();
			
			
		}  else {
			res = 14.946d + 0.035 * Math.exp(-0.775*diff.doubleValue());
		}
		
		
		return res;
		
	}
	

	
	private Pair<Boolean, StatGranularity> _search_weak(
			StatGranularity minSg,
			double requiredAlignmentMaxWidth,
			NavigableMap<StatGranularity, StatVO> moves, 
			Boolean sign, 
			Boolean live,
			Instant now) {


		NavigableSet<StatGranularity> stalledTrendMoves = new TreeSet<StatGranularity>();
		NavigableSet<StatGranularity> overFoundMoves = new TreeSet<StatGranularity>();
		NavigableSet<StatGranularity> blockerCandidates = new TreeSet<StatGranularity>();
		NavigableSet<StatGranularity> stalledMoves = new TreeSet<StatGranularity>();
		NavigableSet<StatGranularity> stalledlhhl = new TreeSet<StatGranularity>();
		NavigableSet<StatGranularity> lhHlMoves = new TreeSet<StatGranularity>();
		NavigableSet<StatGranularity> rxMoves = new TreeSet<StatGranularity>();
		NavigableSet<StatGranularity> wt8div4overs = new TreeSet<StatGranularity>();
		NavigableSet<StatGranularity> wt8div4lhhl = new TreeSet<StatGranularity>();
		NavigableSet<StatGranularity> weakMoves = new TreeSet<StatGranularity>();

		NavigableSet<StatGranularity> rxBelowWt8div4 = new TreeSet<StatGranularity>();
		

		NavigableSet<StatGranularity> rzNoLhHlmul4OK = new TreeSet<StatGranularity>();


		NavigableSet<StatGranularity> all = new TreeSet<StatGranularity>();

		
		NavigableSet<StatGranularity> allOfThisSign = new TreeSet<StatGranularity>();

		

		
		
		double foundAlignmentMaxWidth = 0d;

		boolean foundFirst = false;

		boolean blockedByDivergence = false;
		
		StatGranularity previousMatchingSg = null;

		boolean unstrict = false;
		
		boolean disableBlockers = false;

		
		
		
		
		
		
		for (StatGranularity currentSg: moves.keySet()) {

			if (currentSg.compareTo(minSg)<=0) { 
				StatVO currentVO = moves.get(currentSg);


				if (sign) {


					
					if (foundFirst) {
						if (currentVO.isRpXp(RP_XP_THRESHOLD)) {
							
							rxMoves.add(currentSg);
							if (previousMatchingSg != null) {
								double width = previousMatchingSg.getIndex().doubleValue()/currentSg.getIndex().doubleValue();
								if (width>=foundAlignmentMaxWidth) foundAlignmentMaxWidth = width;
							}
							previousMatchingSg = currentSg;
							foundFirst = true;
						}
					}
					// BLOCKER
					
						if (currentVO.wt1Above(Indicators.SIXTY)) {
							blockerCandidates.add(currentSg);
						} else if (currentVO.wt1Above(Indicators.FIFTY_FIVE)) {
							blockerCandidates.add(currentSg);
						} else if (currentVO.wt1Above(Indicators.FIFTY_THREE)) {
							blockerCandidates.add(currentSg);
						} else if (currentVO.wt1Above(Indicators.FIFTY)) {
							if (currentVO.getHighestWt().compareTo(Indicators.FIVE)>=0) {
								if (currentVO.getMv().equals(SgMove.AP) || currentVO.getMv().equals(SgMove.BP)) {
									blockerCandidates.add(currentSg);
								}
							}
						} else if (currentVO.wt1Above(Indicators.FOURTY_FIVE)) {
							if (currentVO.getHighestWt().compareTo(Indicators.TWELVE)>=0) {
								if (currentVO.getMv().equals(SgMove.AP) || currentVO.getMv().equals(SgMove.BP)) {
									blockerCandidates.add(currentSg);
								}
							}
						} else if (currentVO.wt1Above(Indicators.FOURTY)) {
							if (currentVO.getHighestWt().compareTo(Indicators.FOURTEEN)>=0) {
								if (currentVO.getMv().equals(SgMove.AP) || currentVO.getMv().equals(SgMove.BP)) {
									blockerCandidates.add(currentSg);
								}
							}
						} else if (currentVO.wt1Above(Indicators.THIRTY_FIVE)) {
							if (currentVO.getHighestWt().compareTo(Indicators.EIGHTEEN)>=0) {
								if (currentVO.getMv().equals(SgMove.AP) || currentVO.getMv().equals(SgMove.BP)) {
									blockerCandidates.add(currentSg);
								}
							}
						} else if (currentVO.wt1Above(Indicators.THIRTY)) {
							if (currentVO.getHighestWt().compareTo(Indicators.TWENTY_FIVE)>=0) {
								if (currentVO.getMv().equals(SgMove.AP) || currentVO.getMv().equals(SgMove.BP)) {
									blockerCandidates.add(currentSg);
								}
							}
						}
					
					
					// WEAK
					
				//	if (enableWeak) {
						if (currentVO.wt1Below(Indicators.MINUS_THIRTY)) {
							if (currentVO.isApBp(RP_XP_THRESHOLD)) {
								if (currentVO.getDecreasingHighs()) {
									weakMoves.add(currentSg);
									all.add(currentSg);
									allOfThisSign.add(currentSg);
									if (previousMatchingSg != null) {
										double width = previousMatchingSg.getIndex().doubleValue()/currentSg.getIndex().doubleValue();
										if (width>=foundAlignmentMaxWidth) foundAlignmentMaxWidth = width;
									}
									previousMatchingSg = currentSg;
									foundFirst = true;
								}
								
							}
						}
					//}
					
					//*/
					
					
					// STALLED
					if (currentVO.wt1Above(Indicators.THIRTY)) {

						switch (currentVO.getMv()) {
						case SN:
						case SP:
						{
							//if (currentVO.getHighestWt().compareTo(Indicators.MINUS_FIVE)>=0) {
							stalledMoves.add(currentSg);
							all.add(currentSg);
							allOfThisSign.add(currentSg);
							if (previousMatchingSg != null) {
								double width = previousMatchingSg.getIndex().doubleValue()/currentSg.getIndex().doubleValue();
								if (width>=foundAlignmentMaxWidth) foundAlignmentMaxWidth = width;
							}
							previousMatchingSg = currentSg;
							//foundFirst = true;
							break;
							//}
						}
						default: { break; }
						}



					}

					// OVER

					if (currentVO.wt1Above(WT1_OVERBOUGHT_THRESHOLD)) {

						switch (currentVO.getMv()) {
						case AP:
						case BP:
						case RP:
						case XP:
						case SN:
						case SP:
						{
							//if (currentVO.getHighestWt().compareTo(Indicators.MINUS_FIVE)>=0) {
							overFoundMoves.add(currentSg);
							all.add(currentSg);
							allOfThisSign.add(currentSg);
							if (previousMatchingSg != null) {
								double width = previousMatchingSg.getIndex().doubleValue()/currentSg.getIndex().doubleValue();
								if (width>=foundAlignmentMaxWidth) foundAlignmentMaxWidth = width;
							}
							previousMatchingSg = currentSg;
							foundFirst = true;
							break;
							//}
						}
						default: { 
							break; }
						}



					}


					// LH

					if (currentVO.isRpXpLh(RP_XP_THRESHOLD)) {
						lhHlMoves.add(currentSg);
						all.add(currentSg);
						allOfThisSign.add(currentSg);
						if (previousMatchingSg != null) {
							double width = previousMatchingSg.getIndex().doubleValue()/currentSg.getIndex().doubleValue();
							if (width>=foundAlignmentMaxWidth) foundAlignmentMaxWidth = width;
						}
						previousMatchingSg = currentSg;
						foundFirst = true;
					}
					
					/*if (currentSg.compareTo(trendRightSg)>=0) {
						if (currentVO.isXp(RP_XP_THRESHOLD) && currentVO.wt1Above(Indicators.TEN)) {
							// stalled, end of wave
							
							stalledTrendMoves.add(currentSg);
							all.add(currentSg);
							if (previousMatchingSg != null) {
								double width = previousMatchingSg.getIndex().doubleValue()/currentSg.getIndex().doubleValue();
								if (width>=foundAlignmentMaxWidth) foundAlignmentMaxWidth = width;
							}
							previousMatchingSg = currentSg;
							foundFirst = true;
						}
					}
					*/
					
						
					
					

					if (currentVO.isStLh() && (!currentVO.wt1Below(BigDecimal.ZERO))) {
						stalledlhhl.add(currentSg);
						all.add(currentSg);
						allOfThisSign.add(currentSg);
						if (previousMatchingSg != null) {
							double width = previousMatchingSg.getIndex().doubleValue()/currentSg.getIndex().doubleValue();
							if (width>=foundAlignmentMaxWidth) foundAlignmentMaxWidth = width;
						}
						previousMatchingSg = currentSg;
						foundFirst = true;
					}


					boolean foundDiv4 = false;
					boolean cancelDivergence = false;
					

					if (currentSg.compareTo(getTimeService().multiplyDoubleSg(moves.firstKey(), 3, live).getLeft())>0) {


						Pair<StatGranularity, StatGranularity> div4Sgs = getTimeService().divideDoubleSg(currentSg, 4, live);
						StatGranularity div4Sg = ((div4Sgs.getLeft()!=null)?div4Sgs.getLeft():div4Sgs.getRight());

						Pair<StatGranularity, StatGranularity> div3Sgs = getTimeService().divideDoubleSg(currentSg, 3, live);
						StatGranularity div3Sg = div3Sgs.getRight(); // ((div3Sgs.getRight()!=null)?div3Sgs.getRight():div3Sgs.getRight());

						StatGranularity divCurrentSg = div4Sg;
						StatVO divCurrentVO = null;

						while (divCurrentSg.compareTo(div3Sg)<=0) {
							divCurrentVO = moves.get(divCurrentSg);

							if ( (divCurrentVO.wt1Above(WT1_OVERBOUGHT_THRESHOLD) /* && 

									( //20250301
											(divCurrentVO.getDivergence() == null ) || (!divCurrentVO.getDivergence())

											)

*/
									) 
							
							
							
							)
							
							{

								wt8div4overs.add(currentSg);
								all.add(currentSg);
								allOfThisSign.add(currentSg);
								if (previousMatchingSg != null) {
									double width = previousMatchingSg.getIndex().doubleValue()/currentSg.getIndex().doubleValue();
									if (width>=foundAlignmentMaxWidth) foundAlignmentMaxWidth = width;
								}
								previousMatchingSg = currentSg;
								foundFirst = true;
								foundDiv4 = true;
								break;
							}
							
							/* 20250301
							 * 
							 */ if (wt8div4overs.contains(divCurrentSg)) {
							 
								
								cancelDivergence = true;
							}
							
							divCurrentSg = getTimeService().getNextSg(divCurrentSg, StatGranularity.S17457600, divCurrentSg, true, live);
						}

					}  else {
						//logger.info("divergence: " + currentSg + " ignoring as too small ");

						foundDiv4 = true;
					}

					// All of this sign
					
					/*if ((currentVO.wtAbove(Indicators.THREE))) {
						
						if (!currentSg.equals(minSg)) allOfThisSign.add(currentSg);
						
					}*/
					
					
					
					
					
/*
 * 20250301
 */
					/*if ( (currentVO.getDivergence() != null) &&  (currentVO.getDivergence())) {
						// divergent. Div4? else return null and cancel

						//logger.info("divergence: " + currentSg + " DIV ");
						if ((!foundDiv4)) {
							if (cancelDivergence) {
								logger.info("divergence: " + currentSg + " DIV CANCELED SELL " + currentVO.getDivergence());
							} else {
								logger.info("divergence: " + currentSg + " DIV BLOCKED SELL " + currentVO.getDivergence());
								blockedByDivergence = true;
							}
							
						}
					}

					if ( (currentVO.getSg().compareTo(minSg)>=0) 
						&& currentVO.isApBp(RP_XP_THRESHOLD) 
						&& 	   (currentVO.getWt1().signum()>0)
						&& (!all.contains(currentVO.getSg()))) {
						logger.info("_search_aligned: sell too early " + currentVO.getSg() + " minSg: " + minSg);
						logger.info("20250302 removed too early");

						//return null;
					}
					*/

				} else {

					
					if (foundFirst) {
						
						if (currentVO.isRnXn(RN_XN_THRESHOLD) ) {
							rxMoves.add(currentSg);
							if (previousMatchingSg != null) {
								double width = previousMatchingSg.getIndex().doubleValue()/currentSg.getIndex().doubleValue();
								if (width>=foundAlignmentMaxWidth) foundAlignmentMaxWidth = width;
							}
							previousMatchingSg = currentSg;
							
						}
					}
					// BLOCKER
					
					
					if (currentVO.wt1Below(Indicators.MINUS_SIXTY)) {
						blockerCandidates.add(currentSg);
					} else if (currentVO.wt1Below(Indicators.MINUS_FIFTY_FIVE)) {
						blockerCandidates.add(currentSg);
					} else if (currentVO.wt1Below(Indicators.MINUS_FIFTY_THREE)) {
						blockerCandidates.add(currentSg);
					} else if (currentVO.wt1Below(Indicators.MINUS_FIFTY)) {
						if (currentVO.getHighestWt().compareTo(Indicators.MINUS_FIVE)<=0) {
							if (currentVO.getMv().equals(SgMove.AN) || currentVO.getMv().equals(SgMove.BN)) {
								blockerCandidates.add(currentSg);
							}
						}
					} else if (currentVO.wt1Below(Indicators.MINUS_FOURTY_FIVE)) {
						if (currentVO.getHighestWt().compareTo(Indicators.MINUS_TWELVE)<=0) {
							if (currentVO.getMv().equals(SgMove.AN) || currentVO.getMv().equals(SgMove.BN)) {
								blockerCandidates.add(currentSg);
							}
						}
					} else if (currentVO.wt1Below(Indicators.MINUS_FOURTY)) {
						if (currentVO.getHighestWt().compareTo(Indicators.MINUS_FOURTEEN)<=0) {
							if (currentVO.getMv().equals(SgMove.AN) || currentVO.getMv().equals(SgMove.BN)) {
								blockerCandidates.add(currentSg);
							}
						}
					} else if (currentVO.wt1Below(Indicators.MINUS_THIRTY_FIVE)) {
						if (currentVO.getHighestWt().compareTo(Indicators.MINUS_EIGHTEEN)<=0) {
							if (currentVO.getMv().equals(SgMove.AN) || currentVO.getMv().equals(SgMove.BN)) {
								blockerCandidates.add(currentSg);
							}
						}
					} else if (currentVO.wt1Below(Indicators.MINUS_THIRTY)) {
						if (currentVO.getHighestWt().compareTo(Indicators.MINUS_TWENTY_FIVE)<=0) {
							if (currentVO.getMv().equals(SgMove.AN) || currentVO.getMv().equals(SgMove.BN)) {
								blockerCandidates.add(currentSg);
							}
						}
					}
					
					// WEAK
					
					//if (enableWeak) {
						if (currentVO.wt1Above(Indicators.THIRTY)) {
							if (currentVO.isAnBn(RN_XN_THRESHOLD)) {
								if (currentVO.getIncreasingLows()) {
									weakMoves.add(currentSg);
									all.add(currentSg);
									allOfThisSign.add(currentSg);
									if (previousMatchingSg != null) {
										double width = previousMatchingSg.getIndex().doubleValue()/currentSg.getIndex().doubleValue();
										if (width>=foundAlignmentMaxWidth) foundAlignmentMaxWidth = width;
									}
									previousMatchingSg = currentSg;
									foundFirst = true;
								}
								
							}
						}
						
					//}
					//*/
					
					
					// STALLED
					
					if (currentVO.wt1Below(Indicators.MINUS_THIRTY)) {

						switch (currentVO.getMv()) {
						case SN:
						case SP:
						{
							//if (currentVO.getHighestWt().compareTo(Indicators.MINUS_FIVE)>=0) {
							stalledMoves.add(currentSg);
							all.add(currentSg);
							allOfThisSign.add(currentSg);
							if (previousMatchingSg != null) {
								double width = previousMatchingSg.getIndex().doubleValue()/currentSg.getIndex().doubleValue();
								if (width>=foundAlignmentMaxWidth) foundAlignmentMaxWidth = width;
							}
							previousMatchingSg = currentSg;
							//foundFirst = true;
							break;
							//}
						}
						default: { break; }
						}



					}
					// OVER

					if (currentVO.wt1Below(WT1_OVERSOLD_THRESHOLD)) {


						switch (currentVO.getMv()) {
						case AN:
						case BN:
						case RN:
						case XN:
						case SN:
						case SP:
						{
							//if (currentVO.getHighestWt().compareTo(Indicators.FIVE)<=0) {
							overFoundMoves.add(currentSg);
							all.add(currentSg);
							allOfThisSign.add(currentSg);
							if (previousMatchingSg != null) {
								double width = previousMatchingSg.getIndex().doubleValue()/currentSg.getIndex().doubleValue();
								if (width>=foundAlignmentMaxWidth) foundAlignmentMaxWidth = width;
							}
							previousMatchingSg = currentSg;
							foundFirst = true;
							break;
							//}
						}

						default: {
							break;
						}
						}





					}

					// HL

					if (currentVO.isRnXnHl(RN_XN_THRESHOLD)) {
						lhHlMoves.add(currentSg);
						all.add(currentSg);
						allOfThisSign.add(currentSg);
						if (previousMatchingSg != null) {
							double width = previousMatchingSg.getIndex().doubleValue()/currentSg.getIndex().doubleValue();
							if (width>=foundAlignmentMaxWidth) foundAlignmentMaxWidth = width;
						}
						previousMatchingSg = currentSg;
						foundFirst = true;
					}
					
					/*if (currentSg.compareTo(trendRightSg)>=0) {
						if (currentVO.isXn(RN_XN_THRESHOLD) && currentVO.wt1Below(Indicators.MINUS_TEN)) {
							stalledTrendMoves.add(currentSg);
							all.add(currentSg);
							if (previousMatchingSg != null) {
								double width = previousMatchingSg.getIndex().doubleValue()/currentSg.getIndex().doubleValue();
								if (width>=foundAlignmentMaxWidth) foundAlignmentMaxWidth = width;
							}
							previousMatchingSg = currentSg;
							foundFirst = true;
						}
					}*/
					
					
						
					
					
					
					
					if (currentVO.isStHl() && (!currentVO.wt1Above(BigDecimal.ZERO))) {
						stalledlhhl.add(currentSg);
						all.add(currentSg);
						allOfThisSign.add(currentSg);
						if (previousMatchingSg != null) {
							double width = previousMatchingSg.getIndex().doubleValue()/currentSg.getIndex().doubleValue();
							if (width>=foundAlignmentMaxWidth) foundAlignmentMaxWidth = width;
						}
						previousMatchingSg = currentSg;
						foundFirst = true;
					}

					/*if (!foundFirst) {
							if (currentVO.isRnXn(RN_XN_THRESHOLD)) {

									rxMoves.add(currentSg);
									all.add(currentSg);
									if (previousMatchingSg != null) {
										double width = previousMatchingSg.getIndex().doubleValue()/currentSg.getIndex().doubleValue();
										if (width>=foundAlignmentMaxWidth) foundAlignmentMaxWidth = width;
									}
									previousMatchingSg = currentSg;
									foundFirst = true;


							}
						}*/

					// DIV4

					//if (foundFirst) {
					boolean foundDiv4 = false;
					boolean cancelDivergence = false;
					
					//	if (currentVO.highestWtBelow(RN_XN_THRESHOLD)) {

					if (currentSg.compareTo(getTimeService().multiplyDoubleSg(moves.firstKey(), 3, live).getLeft())>0) {

						Pair<StatGranularity, StatGranularity> div4Sgs = getTimeService().divideDoubleSg(currentSg, 4, live);
						StatGranularity div4Sg = ((div4Sgs.getLeft()!=null)?div4Sgs.getLeft():div4Sgs.getRight());

						Pair<StatGranularity, StatGranularity> div3Sgs = getTimeService().divideDoubleSg(currentSg, 3, live);
						StatGranularity div3Sg = div3Sgs.getRight(); // ((div3Sgs.getRight()!=null)?div3Sgs.getRight():div3Sgs.getRight());

						StatGranularity divCurrentSg = div4Sg;
						StatVO divCurrentVO = null;

						while (divCurrentSg.compareTo(div3Sg)<=0) {
							divCurrentVO = moves.get(divCurrentSg);

							if ( (divCurrentVO.wt1Below(WT1_OVERSOLD_THRESHOLD)

									/*&& 

									(
											(divCurrentVO.getDivergence() == null ) || (divCurrentVO.getDivergence())

											)*/
									)
									)
									{
								wt8div4overs.add(currentSg);
								all.add(currentSg);
								if (previousMatchingSg != null) {
									double width = previousMatchingSg.getIndex().doubleValue()/currentSg.getIndex().doubleValue();
									if (width>=foundAlignmentMaxWidth) foundAlignmentMaxWidth = width;
								}
								previousMatchingSg = currentSg;
								foundFirst = true;
								foundDiv4 = true;
							}
							
							/*
							 * 20250301 */
							if (wt8div4overs.contains(divCurrentSg)) {
								
								cancelDivergence = true;
							}
							divCurrentSg = getTimeService().getNextSg(divCurrentSg, StatGranularity.S17457600, divCurrentSg, true, live);


						}



					} else {
						//logger.info("divergence: " + currentSg + " ignoring as too small ");

						foundDiv4 = true;
					}
					
					
					// All of this sign
					
					/*if ((currentVO.wtBelow(Indicators.MINUS_THREE))) {
						
						if (!currentSg.equals(minSg)) allOfThisSign.add(currentSg);
						
					}*/
					
					
					//}


					/*
					 * 20250301
					 */
					/*if ( (currentVO.getDivergence() != null) &&  (!currentVO.getDivergence())) {
					 
						// divergent. Div4? else return null and cancel
						if ((!foundDiv4)) {
							if (cancelDivergence) {
								logger.info("divergence: " + currentSg + " DIV CANCELED BUY " + currentVO.getDivergence());
							} else {
								logger.info("divergence: " + currentSg + " DIV BLOCKED BUY " + currentVO.getDivergence());
								blockedByDivergence = true;
							}
							
						}
					}
					
					*/
					
					
					
					/*if ( (currentVO.getSg().compareTo(minSg)>=0) 
						&&     currentVO.isAnBn(RN_XN_THRESHOLD) 
						&& 	   (currentVO.getWt1().signum()<0)
						&& (!all.contains(currentVO.getSg()))) {
						logger.info("_search_aligned: buy too early " + currentVO.getSg() + " minSg: " + minSg);
						logger.info("20250302 removed too early");

						//return null;
					}*/
					
					
					// MUL4 IS LHHL

					/*
							if (currentVO.isRnXn(RN_XN_THRESHOLD)) {
								StatGranularity mul4 = getTimeService().multiplyDoubleSg(currentSg, 4, false).getLeft();

								if (betweenMaxSg.compareTo(mul4)>=0) {
									StatVO mul4VO = moves.get(betweenMaxSg);

									if (mul4VO.isRnXnHl(RN_XN_THRESHOLD)) {
										rzNoLhHlmul4OK.add(currentSg);
										all.add(currentSg);
										if (previousMatchingSg != null) {
											double width = previousMatchingSg.getIndex().doubleValue()/currentSg.getIndex().doubleValue();
											if (width>=foundAlignmentMaxWidth) foundAlignmentMaxWidth = width;
										}
										previousMatchingSg = currentSg;
										foundFirst = true;
									}
								}
							}*/
					//	}



					/*switch (currentVO.getMv()) {
						case ST:
						case SP:
						case SN: {
							stalleds.add(currentSg);
							all.add(currentSg);
							if (previousMatchingSg != null) {
								double width = previousMatchingSg.getIndex().doubleValue()/currentSg.getIndex().doubleValue();
								if (width>=maxWidth) maxWidth = width;
							}
							previousMatchingSg = currentSg;
							break;
						}
						default: { break; }
						}*/
					//}

				}




			}

		}

		
		
		/*
		 * Overblockers
		 */
		/*
		if (overblockerCandidates.size()>0) {
			StatVO biggestOver = moves.get(overblockerCandidates.last());
			
			if (sign) {
				
				if (biggestOver.isOverBoughtDiverging(WT1_OVERBOUGHT_THRESHOLD)) {
					 
					 logger.info("_search_aligned: sell canceled by overblocker " + biggestOver.toShortMoveString());
				
					 return null;
						
				 }
			} else {
				if (biggestOver.isOverSoldDiverging(WT1_OVERSOLD_THRESHOLD)) {
					 
					 logger.info("_search_aligned: buy canceled by overblocker " + biggestOver.toShortMoveString());
				
					 return null;
						
				 }
			}
		}
		*/
		
		// Add RX if max low/high
		/*if (all.size()>0) {
			StatVO maxVO = moves.get(all.last());
			
			if (maxVO != null) {
				if (sign) {
					if (maxVO.wt1Below(Indicators.MINUS_THIRTY)) {
						all.addAll(rxMoves);

						disableBlockers = true;
					}
				} else {
					if (maxVO.wt1Above(Indicators.THIRTY)) {
						all.addAll(rxMoves);

						disableBlockers = true;
					}
					
				}
			}
		}*/
		/*
		 * Log
		 */

		
		
		if (all.size() > 0) {
			StringBuffer logData = new StringBuffer("Aligned ");

			if (sign) {

				logData.append(now);
				logData.append(": SELL_SIDE").append(all.size()).append("/").append(moves.size()).append(" maxWidth: ").append(foundAlignmentMaxWidth).append("\n");
			} else {
				logData.append(now);
				logData.append(": BUY_SIDE").append(all.size()).append("/").append(moves.size()).append(" maxWidth: ").append(foundAlignmentMaxWidth).append("\n");
			}
			for (StatGranularity sg: all.descendingSet()) {
				if (stalledTrendMoves.contains(sg)) {
					logData.append("TRSTLD");
					logData.append(moves.get(sg).toMoveString()).append("\n"); 
				} else if (stalledMoves.contains(sg)) {
					logData.append("  STLD");
					logData.append(moves.get(sg).toMoveString()).append("\n"); 
				} else if (stalledlhhl.contains(sg)) {
					logData.append("STLHHL");
					logData.append(moves.get(sg).toMoveString()).append("\n");
				} else if (overFoundMoves.contains(sg)) {
					logData.append("  OVER");
					logData.append(moves.get(sg).toMoveString()).append("\n");
				} else if (lhHlMoves.contains(sg)) {
					logData.append("  LHHL");
					logData.append(moves.get(sg).toMoveString()).append("\n");
				} else if (wt8div4overs.contains(sg)) {
					logData.append("D4OVER");
					logData.append(moves.get(sg).toMoveString()).append("\n");
				} else if (wt8div4lhhl.contains(sg)) {
					logData.append("D4LHHL");
					logData.append(moves.get(sg).toMoveString()).append("\n");
				} else if (rzNoLhHlmul4OK.contains(sg)) {
					logData.append("  RZNO");
					logData.append(moves.get(sg).toMoveString()).append("\n");
				} else if (rxMoves.contains(sg)) {
					logData.append("    RX");
					logData.append(moves.get(sg).toMoveString()).append("\n");
				} else if (weakMoves.contains(sg)) {
					logData.append("  WEAK");
					logData.append(moves.get(sg).toMoveString()).append("\n");
				} /*else if (counterDivergents.contains(sg)) {
					logData.append("COUNTER");
					logData.append(moves.get(sg).toMoveString()).append("\n");
				} else if (stalleds.contains(sg)) {
					logData.append("STALLED");
					logData.append(moves.get(sg).toMoveString()).append("\n");
				} */

			}
			logger.info(logData);
		}

		// SIGNAL?

		/*
		 *Blockers
		 */
		disableBlockers = true;
		if (blockerCandidates.size()>0) {
			
			StatGranularity blockSg = blockerCandidates.last();
			StatVO blockVO = moves.get(blockSg);
			
			if (sign) {
			//	if (blockVO.getDiff().compareTo(Indicators.ZERO_DOT_FIVE)>0) {
				if ((blockVO.getDiff().signum()>0)) {
					
						if (disableBlockers) {
							logger.info("_search_aligned: potential sell DISABLED blocker, diff>X.XX " + blockVO.toShortMoveString());
							
						} else {
							logger.info("_search_aligned: potential sell canceled by blocker, diff>X.XX " + blockVO.toShortMoveString());
							return null;
						}
						
						
				}
				
				 
			} else {
				
				//if (blockVO.getDiff().compareTo(Indicators.MINUS_ZERO_DOT_FIVE)<0) {
				if ((blockVO.getDiff().signum()<0))  {
					
					if (disableBlockers) {
						logger.info("_search_aligned: potential buy DISABLED blocker diff<X.XX " + blockVO.toShortMoveString());	
					} else {
						logger.info("_search_aligned: potential buy canceled by blocker, diff<X.XX " + blockVO.toShortMoveString());						
						return null;
					}
						
					//}
					
					
					
					//return null;
				}
				
				
						
				 
			}
		}
		
		
		

		
		
		/*
		 * 1. find aligned number #2. It MUST be HLLH or OVER. If not, try higher, until leftTrendSg.
		 * 
		 * from the minSg, 
		 */
		
		
		
		
		

		if (all.size() > 0) {

			
			
			StatGranularity highestFoundSg = all.last();
			
			
			
			if (!all.contains(minSg)) {
				logger.info("_search_aligned_: highestFoundSg: " + highestFoundSg + " but min wanted Sg not in all: " + minSg + ", exiting");
				return null;
			}
			
			
			
			/*Pair<StatGranularity,StatGranularity> widthCheckFromSgPair = getTimeService().multiplyDoubleSg(minSg, 3, live);
			StatGranularity widthCheckFromSg = (widthCheckFromSgPair.getRight()!=null)?widthCheckFromSgPair.getRight():widthCheckFromSgPair.getLeft();

			if (widthCheckFromSg.compareTo(trendLeftSg)>0) {
				widthCheckFromSg = trendLeftSg;
			}
			StatGranularity lastSg = widthCheckFromSg;

			
			
			double requiredAlignmentMaxWidthAfterStalled = 8d;
			*/
			StatGranularity lastSg = null;
			boolean matchWidth = true;
			for (StatGranularity sg: all.descendingSet()) {

				if (sg.compareTo(minSg)<=0) {

					if (lastSg != null) {
						
						double width = lastSg.getIndex().doubleValue()/sg.getIndex().doubleValue();

						logger.info("_search_aligned: " + sg + " width: " + width + " reqWidth: " + requiredAlignmentMaxWidth + " lastSg: " + lastSg + " sg: " + sg);
						
						
						if (!(
								(width<=requiredAlignmentMaxWidth) 
							
							/*|| 
							
							(
							(
								stalledMoves.contains(lastSg)  || stalledTrendMoves.contains(lastSg)
							) 
							&& (stalledTrendMoves.size()>0)
							
							&& (width<=requiredAlignmentMaxWidthAfterStalled)
							
							) */
							
						)  ) {
							matchWidth = false;
							break;
						}
						
					} 

					lastSg = sg;
				}

			}
			if (matchWidth) {

				StatGranularity minAcceptableSg = null;

				// check if last found is small enough

				Pair<StatGranularity,StatGranularity> minAcceptableSgPair = getTimeService().multiplyDoubleSg(moves.firstKey(), 2, live);

				minAcceptableSg = (minAcceptableSgPair.getRight()!=null)?minAcceptableSgPair.getRight():minAcceptableSgPair.getLeft();

				if (all.iterator().next().compareTo(minAcceptableSg)<=0) {

					if (blockedByDivergence) {
						//logger.info("_search_aligned: trendSign: " + trendSign + " special: " + special);
						
							logger.info("_search_aligned: BLOCKED BY DIVERGENCE: highestFoundSg: " + highestFoundSg + " minSg: " + minSg + " maxWidth: " + foundAlignmentMaxWidth + " smaller ok");
							return null;
						


					} else {
						logger.info("_search_aligned: highestFoundSg: " + highestFoundSg + " minSg: " + minSg + " maxWidth: " + foundAlignmentMaxWidth + " smaller ok");
						return Pair.create(sign, minSg);
					}

				} else {
					logger.info("_search_aligned: highestFoundSg: " + highestFoundSg + " minSg: " + minSg + " maxWidth: " + foundAlignmentMaxWidth + " smaller NOT ok, exiting");

				}
			}
		




		}
		

		return null;

	}
	
	


}
