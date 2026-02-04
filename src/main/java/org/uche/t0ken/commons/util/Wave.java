package org.uche.t0ken.commons.util;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.TreeSet;

import org.jboss.logging.Logger;
import org.uche.t0ken.commons.enums.StatGranularity;
import org.uche.t0ken.commons.vo.StatVO;



public class Wave {
	private final static Logger logger = Logger.getLogger("Wave");

	private Boolean sell;
	private NavigableSet<StatGranularity> sgs;
	private boolean discardUnconfirmed;
	private Float ratio = null;
	//private Float disto = null;
	private Float size = null;
	
	
	private Float overBought = null;
	private Float overSold = null;
	private Float diverBought = null;
	private Float diverSold = null;
	
	private Float lhRatio = null;
	private Float hlRatio = null;
	
	private BigDecimal lowerDiff = null;
	private BigDecimal highestWt = null;
	
	
	private Instant now;
	private int index;
	private int i;
	private NavigableMap<StatGranularity, StatVO> moves;
	private StatGranularity minSg;
	private StatGranularity maxSg;
	private WaveAlignment waveAlignment;
	public Boolean getSell() {
		return sell;
	}
	public void setSell(Boolean sell) {
		this.sell = sell;
	}
	public NavigableSet<StatGranularity> getSgs() {
		return sgs;
	}
	public void setSgs(NavigableSet<StatGranularity> sgs) {
		this.sgs = sgs;
	}
	
	private Float abrzRatio = null;
	
	public float getAbrzRatio() {
		if (abrzRatio == null) {
			abrzRatio = this.s_wave_ratio_AB_RZ();
		}
		return abrzRatio;
	}
	
	//public float getDisto() {
		/*if (disto == null) {
			disto = this.s_wave_ratio_distortion();
		}*/
	//	return disto;
	//}
	
	public float getSize() {
		/*if (size == null) {
			size = this.s_wave_size();
		}*/
		return size;
	}
	public boolean isDiscardUnconfirmed() {
		return discardUnconfirmed;
	}
	public void setDiscardUnconfirmed(boolean discardUnconfirmed) {
		this.discardUnconfirmed = discardUnconfirmed;
	}
	public Instant getNow() {
		return now;
	}
	public void setNow(Instant now) {
		this.now = now;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public NavigableMap<StatGranularity, StatVO> getStatVOs() {
		return moves;
	}
	public void setStatVOs(NavigableMap<StatGranularity, StatVO> moves) {
		this.moves = moves;
	}
	
	
	public static Wave create(StatGranularity maxSg, StatGranularity minSg, NavigableSet<StatGranularity> sgs,
			Boolean sell, NavigableMap<StatGranularity, StatVO> moves, Instant now,
			boolean discardUnconfirmed, int index, int i) {
		
	
		Wave wave = new Wave();
		wave.setSgs(sgs);
		wave.setSell(sell);
		wave.setDiscardUnconfirmed(discardUnconfirmed);
		wave.setStatVOs(moves);
		wave.setNow(now);
		wave.setIndex(index);
		wave.setMaxSg(maxSg);
		wave.setMinSg(minSg);
		wave.setI(i);
		return wave;
	}
	
	private float s_wave_ratio_AB_RZ() {
		
		
		/*
		 * 
		 * 2 R-Z/(total)>40%
		 * 
		 */
		
		int countTotal = 0;
		int countAccel = 0;
		int countSlowdown = 0;
		
		boolean foundFirst = !discardUnconfirmed;
		
		//logger.info("foundFirst: 1 " + foundFirst);
		for (Iterator<StatGranularity> currentSgIterator = sgs.descendingIterator(); currentSgIterator.hasNext();) {
			StatGranularity currentSg = currentSgIterator.next();
			StatVO currentStatVO = moves.get(currentSg);
		//	logger.info("foundFirst: 2 " + foundFirst +  " currentSg: " + currentSg);
			if (!foundFirst) {
				if ((currentStatVO.getHighestWt().abs().compareTo(Indicators.SEVEN)>=0)
						|| 
						(currentStatVO.getWt1().abs().compareTo(Indicators.SIXTY)>=0)
					|| 
					(currentStatVO.getDiff().abs().compareTo(Indicators.TWO)>=0))
					
				{
					foundFirst = true;
				}
			}
		///	logger.info("foundFirst: 3 " + foundFirst +  " currentSg: " + currentSg);
			if (foundFirst) {
				if (sell) {
					
				/*	if (currentStatVO.getWt1().compareTo(Indicators.SIXTY)>=0) {
						countSlowdown++;
						countTotal++;
					} else {*/
						switch (currentStatVO.getMv()) {
						case AP:
						case BP:
						case YN:
						case ZN:
						{
							
							if (currentStatVO.getWt1().signum()>=0) {
								countAccel++;
								countTotal++;
							}
							
							break;
						}
						default: {
								countSlowdown++;
								countTotal++;
							
							break;
						}
						}
					//}
					
					
				} else {
					
					/*if (currentStatVO.getWt1().compareTo(Indicators.MINUS_SIXTY)<=0) {
						countSlowdown++;
						countTotal++;
					} else {*/
						switch (currentStatVO.getMv()) {
						case AN:
						case BN:
						case YP:
						case ZP:
						{
							if (currentStatVO.getWt1().signum()<=0) {
								countAccel++;
								countTotal++;
							}
							
							break;
						}
						default: {
								countSlowdown++;
								countTotal++;
							
							break;
						}
						}
					//}
					
					
				}
			}
			
			
					
		}
		
		//logger.info("s_wave_satisfy_AB_RZ_ratio: countSlowdown: " + countSlowdown + " countTotal: " + countTotal);
		if (!foundFirst) {
			return 1f;
		}
		if (countSlowdown == 0f) {
			return 0;
		}
		float res = ((float)countSlowdown) / ((float)countTotal);
		
		return res;
		
	}
	
	
	
	private float s_wave_ratio_diff() {
		
		
		/*
		 * 
		 * 2 R-Z/(total)>40%
		 * 
		 */
		
		int countTotal = 0;
		int countAccel = 0;
		int countSlowdown = 0;
		
		boolean foundFirst = !discardUnconfirmed;
		
		//logger.info("foundFirst: 1 " + foundFirst);
		for (Iterator<StatGranularity> currentSgIterator = sgs.descendingIterator(); currentSgIterator.hasNext();) {
			StatGranularity currentSg = currentSgIterator.next();
			StatVO currentStatVO = moves.get(currentSg);
		//	logger.info("foundFirst: 2 " + foundFirst +  " currentSg: " + currentSg);
			if (!foundFirst) {
				if ((currentStatVO.getHighestWt().abs().compareTo(Indicators.SEVEN)>=0)
						|| 
						(currentStatVO.getWt1().abs().compareTo(Indicators.SIXTY)>=0)
					|| 
					(currentStatVO.getDiff().abs().compareTo(Indicators.TWO)>=0))
					
				{
					foundFirst = true;
				}
			}
		///	logger.info("foundFirst: 3 " + foundFirst +  " currentSg: " + currentSg);
			if (foundFirst) {
				if (sell) {
					
				/*	if (currentStatVO.getWt1().compareTo(Indicators.SIXTY)>=0) {
						countSlowdown++;
						countTotal++;
					} else {*/
						switch (currentStatVO.getMv()) {
						case AP:
						case BP:
						case YN:
						case ZN:
						{
							
							if (currentStatVO.getWt1().signum()>=0) {
								countAccel++;
								countTotal++;
							}
							
							break;
						}
						default: {
								countSlowdown++;
								countTotal++;
							
							break;
						}
						}
					//}
					
					
				} else {
					
					/*if (currentStatVO.getWt1().compareTo(Indicators.MINUS_SIXTY)<=0) {
						countSlowdown++;
						countTotal++;
					} else {*/
						switch (currentStatVO.getMv()) {
						case AN:
						case BN:
						case YP:
						case ZP:
						{
							if (currentStatVO.getWt1().signum()<=0) {
								countAccel++;
								countTotal++;
							}
							
							break;
						}
						default: {
								countSlowdown++;
								countTotal++;
							
							break;
						}
						}
					//}
					
					
				}
			}
			
			
					
		}
		
		//logger.info("s_wave_satisfy_AB_RZ_ratio: countSlowdown: " + countSlowdown + " countTotal: " + countTotal);
		if (!foundFirst) {
			return 1f;
		}
		if (countSlowdown == 0f) {
			return 0;
		}
		float res = ((float)countSlowdown) / ((float)countTotal);
		
		return res;
		
	}
	
	private float s_wave_ratio_distortion() {
		/*
		 * 1 A/B + over @ /4
		 */
		
		StatGranularity highestStatVOSg = sgs.descendingIterator().next();
		//StatVO highestStatVO = moves.get(highestStatVOSg);
		//StatGranularity lowestStatVOSg = waveData.iterator().next();
		//StatVO lowestStatVO = moves.get(lowestStatVOSg);
		
		// get first over
		StatGranularity firstOverSg = null;
		boolean foundFirst = !discardUnconfirmed;
		
		for (StatGranularity currentSg: sgs.descendingSet()) {
			StatVO currentStatVO = moves.get(currentSg);
			
			
			if (!foundFirst) {
				if (currentStatVO.getHighestWt().abs().compareTo(Indicators.SEVEN)>=0) {
					foundFirst = true;
				}
			}
			
			if (foundFirst) {
				if ((currentStatVO.getOver() != null) && (currentStatVO.getOver() == sell)) {
					firstOverSg = currentSg;
					break;
				}
			}
			
			
			
					
		}
		
		//boolean satisfyOverWaveDistortion = false;
		float rate = 0;
		
		if (firstOverSg != null) {
			rate = ((float)firstOverSg.getIndex()) / ((float) highestStatVOSg.getIndex());
		}
		/*if (firstOverSg != null) {
			
			
			if (rate<=4f) {
				satisfyOverWaveDistortion = true;
			}
			
		}*/
		return rate;
		
	}
	
	private float s_wave_size() {
		
		
		//boolean foundFirst = !discardUnconfirmed;
		StatGranularity highestSg = sgs.descendingIterator().next();
		
		/*
		if (!foundFirst) {
			
			for (Iterator<StatGranularity> sgIterator = sgs.descendingIterator(); sgIterator.hasNext();) {
				StatGranularity sg = sgIterator.next();
				StatVO mv = moves.get(sg);
				if (mv.getHighestWt().abs().compareTo(Indicators.SEVEN)>=0) {
					highestSg = sg;
					break;
				}
			}
			
			
		} else {
			highestSg = sgs.descendingIterator().next();
		}
		*/
		if (highestSg == null) {
			return 1f;
		}
		
		
		return (((float)highestSg.getIndex())
				/ ((float)sgs.iterator().next().getIndex()));
		
	}
	
	public float getSameSideDiv() {
		float retval = 1f;
		float size = this.getSize();
		//float ratio = this.getRatio();
		

		/*/
		 * ratio
		 * 0.00<=r<0.10 => 1.5
		 * 0.10<=r<0.20 => 2
		 * 0.20<=r<0.30 => 3
		 * 0.30<=r<0.40 => 4
		 * 0.40<=r<0.50 => 5
		 * 0.50<=r<0.60 => 6
		 * 0.60<=r<0.70 => 7
		 * 0.70<=r<0.80 => 8
		 * 0.80<=r<0.90 => 9
		 * 0.90<=r<1.00 => 10
		 * 1.00==r      => 12
		 */
		
		if (ratio<0.10) {
			retval = 1.5f;
		} else if (ratio<0.20) {
			retval = 2f;
		} else if (ratio<0.30) {
			retval = 3f;
		} else if (ratio<0.40) {
			retval = 4f;
		} else if (ratio<0.50) {
			retval = 5f;
		} else if (ratio<0.60) {
			retval = 6f;
		} else if (ratio<0.70) {
			retval = 7f;
		} else if (ratio<0.80) {
			retval = 8f;
		} else if (ratio<0.90) {
			retval = 9f;
		} else if (ratio<1.00) {
			retval = 10f;
		} else {
			retval = 12f;
		}
		
		
		
		return retval;
	}
	
	
	public float getOtherSideDiv() {
		float retval = 1f;
		float size = this.getSize();
		//float ratio = this.getRatio();
		

		/*/
		 * ratio
		 * 0.00<=r<0.10 => 1.5
		 * 0.10<=r<0.20 => 2
		 * 0.20<=r<0.30 => 3
		 * 0.30<=r<0.40 => 4
		 * 0.40<=r<0.50 => 5
		 * 0.50<=r<0.60 => 6
		 * 0.60<=r<0.70 => 7
		 * 0.70<=r<0.80 => 8
		 * 0.80<=r<0.90 => 9
		 * 0.90<=r<1.00 => 10
		 * 1.00==r      => 12
		 */
		
		if (ratio<0.10) {
			retval = 32f;
		} else if (ratio<0.20) {
			retval = 24f;
		} else if (ratio<0.30) {
			retval = 16f;
		} else if (ratio<0.40) {
			retval = 14f;
		} else if (ratio<0.50) {
			retval = 12f;
		} else if (ratio<0.60) {
			retval = 10f;
		} else if (ratio<0.70) {
			retval = 10f;
		} else if (ratio<0.80) {
			retval = 10f;
		} else if (ratio<0.90) {
			retval = 10f;
		} else if (ratio<1.00) {
			retval = 10f;
		} else {
			retval = 10f;
		}
		
		
		return retval;
	}
	
   
	
	private static String percentFormat = "%3.0f";
	
	private static String formatSize = "%7.2f";
	
	
	private void log_appendWaveSummary(StringBuffer buf, int i, Instant now) {
		
		if (sell) {
			buf.append("\n+W").append(i);
			
			if (i == 1) {
				buf.append("T");
			} else {
				buf.append(" ");
			}
			
			
			buf.append("  side: sell ratio: +").append(String.format(percentFormat,ratio*100)).append("%")
			.append(" | rz: +").append(String.format(percentFormat,getAbrzRatio()*100)).append("%")
			.append(" | ob: ").append(String.format(percentFormat, overBought*100) ).append("%")
			.append(" | os: ").append(String.format(percentFormat, overSold*100) ).append("%")
			.append(" | db: ").append(String.format(percentFormat, diverBought*100) ).append("%")
			.append(" | ds: ").append(String.format(percentFormat, diverSold*100)).append("%")
			.append(" | hl: ").append(String.format(percentFormat, hlRatio*100) ).append("%")
			.append(" | lh: ").append(String.format(percentFormat, lhRatio*100) ).append("%");
			
			
			buf.append(" | hwt: ");
			if (highestWt.signum()>0) {
				buf.append("+");	
			}
			buf.append(highestWt);
			
			
			
			buf.append(" | diff: ");
			if (lowerDiff.signum()>0) {
				buf.append("+");	
			}
			
			buf.append(lowerDiff)
			.append(" | size: [ +").append(String.format(formatSize, size) ).append(" ] @").append(now) .append(" { ")
			//.append(this.getWaveAlignment())
			.append(sgs.last())
			.append(":")
			.append(sgs.first()).append(" } WSM")
			;
		} else {
			buf.append("\n-W").append(i);
			
			if (i == 1) {
				buf.append("T");
			} else {
				buf.append(" ");
			}
			buf.append("  side:  buy ratio: -").append(String.format(percentFormat,ratio*100)).append("%")
			.append(" | rz: -").append(String.format(percentFormat,getAbrzRatio()*100)).append("%")
			.append(" | ob: ").append(String.format(percentFormat, overBought*100) ).append("%")
			.append(" | os: ").append(String.format(percentFormat, overSold*100) ).append("%")
			.append(" | db: ").append(String.format(percentFormat, diverBought*100) ).append("%")
			.append(" | ds: ").append(String.format(percentFormat, diverSold*100) ).append("%")
			.append(" | hl: ").append(String.format(percentFormat, hlRatio*100) ).append("%")
			.append(" | lh: ").append(String.format(percentFormat, lhRatio*100) ).append("%");
			
			buf.append(" | hwt: ");
			if (highestWt.signum()>0) {
				buf.append("+");	
			}
			buf.append(highestWt);
			
			
			
			buf.append(" | diff: ");
			if (lowerDiff.signum()>0) {
				buf.append("+");	
			}
			
			buf.append(lowerDiff)
			.append(" | size: [ -").append(String.format(formatSize, size) ).append(" ] @").append(now) .append(" { ")
			//.append(this.getWaveAlignment())
			.append(sgs.last())
			.append(":")
			.append(sgs.first()).append(" } WSM")
			;
		}
		
	}
	
	public static void appendLogs(List<Wave> waves, StringBuffer buf, boolean full, Instant now) {
		
		
		StringBuffer sellMuls = new StringBuffer();
		StringBuffer buyMuls = new StringBuffer();
		
		StatGranularity previousSell = null;
		StatGranularity previousBuy = null;
		
		int sellSize = 0;
		int buySize = 0;
		
		for (Wave w: waves) {
			
			if (w.getSell()) {
				sellSize += w.getSize();
				if (previousSell == null) {
					previousSell = w.getSgs().first();
				} else {
					
				}
			} else {
				buySize += w.getSize();
				
			}
			
		}
		
		
		
		//if (full) {
			buf.append("####### WSM buy ")
			.append(String.format
					(percentFormat,  
							(
									(float)buySize / ((float)buySize + (float)sellSize)) *100
			) 
			).append("% sell ")
			
			.append(String.format
					(percentFormat,  
							(
									(float)sellSize / ((float)buySize + (float)sellSize)) *100
			) 
			).append("% ").append(now);
			
		//}
		
		int i = 0;
		
		for (Iterator<Wave> iterator = waves.iterator(); iterator.hasNext();) {
			Wave wave = iterator.next();
			wave.log_appendWaveSummary(buf, i, now);
			
			if (full) {
				for (StatGranularity sg: wave.getSgs().descendingSet()) {
					StatVO move = wave.getStatVOs().get(sg);
					if (wave.getSell()) {
						
						buf.append("\n+W").append(i);
						if (i == 1) {
							buf.append("T");
						} else {
							buf.append(" ");
						}
						
						
						buf.append(move.toMoveString());
					} else {
						buf.append("\n-W").append(i);
						
						if (i == 1) {
							buf.append("T");
						} else {
							buf.append(" ");
						}
						buf.append(move.toMoveString());
					}
				}
				
			}
			
			
			i++;
		}
		if (full) {
			buf.append("\n############################################################################################");

		}
		
	}
	
	public boolean satisfy_minimum() {
		if (sgs.first().compareTo(StatGranularity.S80)<=0) {
			return true;
		}
		return false;
	}
	public boolean stable() {
		
		return (ratio > 0f);
	}
	public boolean undefined() {
		for (StatGranularity sg: sgs) {
			StatVO mv = moves.get(sg);
			switch (mv.getMv()) {
			case AP:
			case BP:
			case RP:
			case XP:
			case YP:
			case ZP:
			case AN:
			case BN:
			case RN:
			case XN:
			case YN:
			case ZN:
				return false;
			default: {
				
			}
			}
			
			
		}
		// true if all are SP, SN, ST
		return true;
	}
	
	public static Wave sumWaves(Wave w1, Wave w2) {
		Wave wave = new Wave();
		
		NavigableSet<StatGranularity> sumedSgs = new TreeSet<StatGranularity>();
		sumedSgs.addAll(w1.getSgs());
		sumedSgs.addAll(w2.getSgs());
		wave.setSgs(sumedSgs);
		wave.setSell(w1.getSell());
		wave.setDiscardUnconfirmed(w1.isDiscardUnconfirmed());
		wave.setStatVOs(w1.getStatVOs());
		wave.setNow(w1.getNow());
		wave.setIndex(w2.getIndex()); // index of w2
		wave.setMaxSg(w1.getMaxSg());
		wave.setMinSg(w1.getMinSg());
		return wave;
	}
	public List<StatGranularity> getCalmSgs() {
		List<StatGranularity> calmSgs = new ArrayList<StatGranularity>();
		
		for (Iterator<StatGranularity> sgIt =  sgs.descendingIterator(); sgIt.hasNext();) {
			StatGranularity sg = sgIt.next();
			StatVO mv = moves.get(sg);
			switch (mv.getMv()) {
			case RP:
			case XP:
			case YP:
			case ZP:
			case SP:
			case RN:
			case XN:
			case YN:
			case ZN:
			case SN:
					
				calmSgs.add(sg);
				break;
			default: {
				break;
			}
			}
		}
		
		/*if (calmSgs.size()>2) {
			List<StatGranularity> retval = new ArrayList<StatGranularity>();
			retval.add(calmSgs.get(0));
			retval.add(calmSgs.get(calmSgs.size()-1));
			calmSgs = retval;
		}*/
		
		if (calmSgs.size() == 0) {
			calmSgs.add(sgs.iterator().next());
		}
		return calmSgs;
	}
	
	public boolean isSmallest() {
		
		
		
		return (sgs
				.contains(
						minSg));
		
	}
	public StatGranularity getMinSg() {
		return minSg;
	}
	public void setMinSg(StatGranularity minSg) {
		this.minSg = minSg;
	}
	public StatGranularity getMaxSg() {
		return maxSg;
	}
	public void setMaxSg(StatGranularity maxSg) {
		this.maxSg = maxSg;
	}
	public WaveAlignment getWaveAlignment() {
		return waveAlignment;
	}
	public void setWaveAlignment(WaveAlignment waveAlignment) {
		this.waveAlignment = waveAlignment;
	}
	
	
	
	
	public boolean includesSg(StatGranularity s) {
		return sgs.contains(s);
	}
	
	public StatVO getStatVO(StatGranularity s) {
		return moves.get(s);
	}
	public int getI() {
		return i;
	}
	public void setI(int i) {
		this.i = i;
	}
	public Float getOverBought() {
		return overBought;
	}
	public void setOverBought(Float overBought) {
		this.overBought = overBought;
	}
	public Float getOverSold() {
		return overSold;
	}
	public void setOverSold(Float overSold) {
		this.overSold = overSold;
	}
	public Float getDiverBought() {
		return diverBought;
	}
	public void setDiverBought(Float diverBought) {
		this.diverBought = diverBought;
	}
	public Float getDiverSold() {
		return diverSold;
	}
	public void setDiverSold(Float diverSold) {
		this.diverSold = diverSold;
	}
	public void setRatio(Float ratio) {
		this.ratio = ratio;
	}
	public void setSize(Float size) {
		this.size = size;
	}
	public Float getRatio() {
		return ratio;
	}
	public Float getLhRatio() {
		return lhRatio;
	}
	public void setLhRatio(Float lhRatio) {
		this.lhRatio = lhRatio;
	}
	public Float getHlRatio() {
		return hlRatio;
	}
	public void setHlRatio(Float hlRatio) {
		this.hlRatio = hlRatio;
	}
	public BigDecimal getLowerDiff() {
		return lowerDiff;
	}
	public void setLowerDiff(BigDecimal lowerDiff) {
		this.lowerDiff = lowerDiff;
	}
	public BigDecimal getHighestWt() {
		return highestWt;
	}
	public void setHighestWt(BigDecimal highestWt) {
		this.highestWt = highestWt;
	}
	
	
	
}

