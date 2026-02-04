package org.uche.t0ken.commons.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.List;

import org.uche.t0ken.commons.enums.StatGranularity;
import org.uche.t0ken.commons.util.Indicators;
import org.uche.t0ken.commons.util.InstantDeserializer;
import org.uche.t0ken.commons.util.InstantSerializer;
import org.uche.t0ken.commons.util.RatioData;
import org.uche.t0ken.commons.util.SgMove;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonInclude(Include.NON_NULL)
public class StatVO implements Serializable {

	
	
	private static final long serialVersionUID = 4157568217152631068L;
	private Long id;
	private StatGranularity sg;
	@JsonSerialize(using = InstantSerializer.class)
	@JsonDeserialize(using = InstantDeserializer.class)
	private Instant ts;

	private BigDecimal buy;
	private BigDecimal sell;
	private BigDecimal close;
	private BigDecimal high;
	private BigDecimal low;
	private BigDecimal open;
	private BigDecimal hlc3;
	
	private BigDecimal wtEsa; 
	private BigDecimal wtD; 
	private BigDecimal wtCi; 
	private BigDecimal wt1; 
	private BigDecimal wt2; 
	private BigDecimal wt;

	
	/*private BigDecimal previousHighestWt1;
	private BigDecimal currentHighestWt1;
	
	private BigDecimal previousLowestWt1;
	private BigDecimal currentLowestWt1;
	*/
	
	private BigDecimal highestWt;
	private BigDecimal diff;
	private Boolean decreasingHighs;
	private Boolean increasingLows;
	private SgMove mv;
	private Instant lastSeenNegativeTs;
	private Instant lastSeenPositiveTs;
	private Boolean divergence;
	private Boolean previousLowWasOver;
	private Boolean previousHighWasOver;
	
	private Instant lastMatchTs;
	
	
	
	private Boolean perfectStart;
	private Boolean perfectEnd;
	
	private Boolean finalized;

	private Long firstId;
	private Long lastId;
	private Long previousLastId;
	
	private List<Long> ids;
	
	private Boolean live;
	
	private Boolean over;
	
	
	public StatGranularity getSg() {
		return sg;
	}

	public void setSg(StatGranularity statGranularity) {
		this.sg = statGranularity;
	}

	public Instant getTs() {
		return ts;
	}

	public void setTs(Instant ts) {
		this.ts = ts;
	}

	public BigDecimal getBuy() {
		return buy;
	}

	public void setBuy(BigDecimal buy) {
		this.buy = buy;
	}

	public BigDecimal getSell() {
		return sell;
	}

	public void setSell(BigDecimal sell) {
		this.sell = sell;
	}

	public BigDecimal getClose() {
		return close;
	}

	public void setClose(BigDecimal close) {
		this.close = close;
	}

	public BigDecimal getHigh() {
		return high;
	}

	public void setHigh(BigDecimal high) {
		this.high = high;
	}

	public BigDecimal getLow() {
		return low;
	}

	public void setLow(BigDecimal low) {
		this.low = low;
	}

	public BigDecimal getOpen() {
		return open;
	}

	public void setOpen(BigDecimal open) {
		this.open = open;
	}

	public BigDecimal getHlc3() {
		return hlc3;
	}

	public void setHlc3(BigDecimal hlc3) {
		this.hlc3 = hlc3;
	}

	public BigDecimal getWtEsa() {
		return wtEsa;
	}

	public void setWtEsa(BigDecimal wtEsa) {
		this.wtEsa = wtEsa;
	}

	public BigDecimal getWtD() {
		return wtD;
	}

	public void setWtD(BigDecimal wtD) {
		this.wtD = wtD;
	}

	public BigDecimal getWtCi() {
		return wtCi;
	}

	public void setWtCi(BigDecimal wtCi) {
		this.wtCi = wtCi;
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

	

	public Boolean getFinalized() {
		return finalized;
	}

	public void setFinalized(Boolean finalized) {
		this.finalized = finalized;
	}

	public BigDecimal getWt() {
		return wt;
	}

	public void setWt(BigDecimal wt) {
		this.wt = wt;
	}

	public Boolean getPerfect() {
		if (perfectStart == null) return false;
		if (perfectEnd == null) return false;
		
		return (perfectStart && perfectEnd);
	}

	
	
	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append(sg);
		buf.append(" o:");
		buf.append(open);
		buf.append(" l:");
		buf.append(low);
		buf.append(" h:");
		buf.append(high);
		buf.append(" c:");
		buf.append(close);
		buf.append(" hlc3:");
		buf.append(hlc3);
		buf.append(" wt1:");
		buf.append(wt1);
		buf.append(" wt:");
		buf.append(wt);
		
		buf.append(" ps:");
		buf.append(perfectStart);
		buf.append(" pe:");
		buf.append(perfectEnd);
		buf.append(" @");
		buf.append(ts);
		
		return buf.toString();
	}

	
	

	public Boolean getLive() {
		return live;
	}

	public void setLive(Boolean live) {
		this.live = live;
	}

	

	public Boolean getPerfectStart() {
		return perfectStart;
	}

	public void setPerfectStart(Boolean perfectStart) {
		this.perfectStart = perfectStart;
	}

	public Boolean getPerfectEnd() {
		return perfectEnd;
	}

	public void setPerfectEnd(Boolean perfectEnd) {
		this.perfectEnd = perfectEnd;
	}
	
	
	public String toMoveString() {
		StringBuffer res = new StringBuffer(200);
		
		/*if (divergence != null) {
			if (divergence) {
				res
				.append("D ");
			} else {
				res
				.append("D ");
			}
		} else {
			res
			.append("- ");
		}
		
		*/
		
		res
		.append(sg.toRepString())
		.append(":");
		
		if (mv != null) {
			res.append(mv.toString());
		} else {
			res.append("??");
		}
		
		res.append(" wt:")
		.append(wt)
		.append(" highestWt:")
		.append(highestWt)
		.append(" wt1: ")
		.append(wt1)
		.append(" diff:")
		.append(diff);
		
		if (decreasingHighs != null) {
			if (decreasingHighs) {
				res.append(" LH");
			} else {
				
				res.append(" HH");
				/*
				if ((previousHighWasOver != null) && (previousHighWasOver)) {
					res.append(" OH");
				} else {
					res.append(" HH");
				}
				*/
				
			}
		}
		if (increasingLows != null) {
			if (increasingLows) {
				res.append(" HL");
			} else {
				
				res.append(" LL");
				
				/*
				if ((previousLowWasOver != null) && (previousLowWasOver)) {
					res.append(" OL");
				} else {
					res.append(" LL");
				}
				*/
			}
		}
		
		if (wt.signum()>=0) {
			if ((previousLowWasOver != null) && previousLowWasOver) {
				res.append(" POS");
			}
			
		} else {
			if ((previousHighWasOver != null) && previousHighWasOver) {
				res.append(" POB");
			}
		}
		
		res.append(" @");
		res.append(lastMatchTs);
		return res.toString();
	}
	
	
	public String toShortMoveString() {
		StringBuffer res = new StringBuffer(200);
		
		/*if (divergence != null) {
			if (divergence) {
				res
				.append("D ");
			} else {
				res
				.append("D ");
			}
		} else {
			res
			.append("- ");
		}
		*/
		
		
		res
		.append(sg.toRepString())
		.append(":");
		
		if (mv != null) {
			res.append(mv.toString());
		} else {
			res.append("??");
		}
		
		res.append(" wt:")
		.append(wt.setScale(2, RoundingMode.HALF_EVEN))
		.append(" hWt:")
		.append(highestWt.setScale(2, RoundingMode.HALF_EVEN))
		.append(" wt1:")
		.append(wt1.setScale(2, RoundingMode.HALF_EVEN))
		.append(" d:")
		.append(diff.setScale(2, RoundingMode.HALF_EVEN));
		
		if (decreasingHighs != null) {
			if (decreasingHighs) {
				res.append(" LH");
			} else {
				
				res.append(" HH");
				/*
				if ((previousHighWasOver != null) && (previousHighWasOver)) {
					res.append(" OH");
				} else {
					res.append(" HH");
				}
				*/
				
			}
		}
		if (increasingLows != null) {
			if (increasingLows) {
				res.append(" HL");
			} else {
				
				res.append(" LL");
				
				/*
				if ((previousLowWasOver != null) && (previousLowWasOver)) {
					res.append(" OL");
				} else {
					res.append(" LL");
				}
				*/
			}
		}
		if (wt.signum()>=0) {
			if ((previousLowWasOver != null) && previousLowWasOver) {
				res.append(" POS");
			}
			
		} else {
			if ((previousHighWasOver != null) && previousHighWasOver) {
				res.append(" POB");
			}
		}
		return res.toString();
	}
	
	
	
	
	

	public BigDecimal getHighestWt() {
		return highestWt;
	}

	public void setHighestWt(BigDecimal highestWt) {
		this.highestWt = highestWt;
	}

	public BigDecimal getDiff() {
		return diff;
	}

	public void setDiff(BigDecimal diff) {
		this.diff = diff;
	}

	public Boolean getDecreasingHighs() {
		return decreasingHighs;
	}

	public void setDecreasingHighs(Boolean decreasingHighs) {
		this.decreasingHighs = decreasingHighs;
	}

	public Boolean getIncreasingLows() {
		return increasingLows;
	}

	public void setIncreasingLows(Boolean increasingLows) {
		this.increasingLows = increasingLows;
	}

	public SgMove getMv() {
		return mv;
	}

	public void setMv(SgMove mv) {
		this.mv = mv;
	}

	public Instant getLastSeenNegativeTs() {
		return lastSeenNegativeTs;
	}

	public void setLastSeenNegativeTs(Instant lastSeenNegativeTs) {
		this.lastSeenNegativeTs = lastSeenNegativeTs;
	}

	public Instant getLastSeenPositiveTs() {
		return lastSeenPositiveTs;
	}

	public void setLastSeenPositiveTs(Instant lastSeenPositiveTs) {
		this.lastSeenPositiveTs = lastSeenPositiveTs;
	}

	public Boolean getDivergence() {
		return divergence;
	}

	public void setDivergence(Boolean divergence) {
		this.divergence = divergence;
	}

	public Boolean getPreviousLowWasOver() {
		return previousLowWasOver;
	}

	public void setPreviousLowWasOver(Boolean previousLowWasOver) {
		this.previousLowWasOver = previousLowWasOver;
	}

	public Boolean getPreviousHighWasOver() {
		return previousHighWasOver;
	}

	public void setPreviousHighWasOver(Boolean previousHighWasOver) {
		this.previousHighWasOver = previousHighWasOver;
	}

	
	public RatioData _ratios(Boolean waveSign) {
		
		Float aligned = null;
		Float opposed = null;
		
		// f(wt, highestWt, wt1, diff)
		
		// to help problem D3:BN wt:-9.51 highestWt:-12.58 wt1: -24.84 diff:0.70 LH HL LP: 2022-08-19T11:59:30Z @2022-09-01T11:51:00Z, take into account wt
		
		BigDecimal aDiff = diff.add(wt1.divide(Indicators.HUNDRED, Indicators.roundIndicators));
		
		BigDecimal absDiff = aDiff.abs();
		
			
			if (absDiff.compareTo(Indicators.FIFTEEN)>=0) {
				aligned = 6f;
				opposed = 64f;
			} else if (absDiff.compareTo(Indicators.FOURTEEN)>=0) {
				aligned = 6f;
				opposed = 48f;
			} else if (absDiff.compareTo(Indicators.THIRTEEN)>=0) {
				aligned = 6f;
				opposed = 40f;
			} else if (absDiff.compareTo(Indicators.TWELVE)>=0) {
				aligned = 6f;
				opposed = 38f;
			} else if (absDiff.compareTo(Indicators.ELEVEN)>=0) {
				aligned = 6f;
				opposed = 36f;
			} else if (absDiff.compareTo(Indicators.TEN)>=0) {
				aligned = 6f;
				opposed = 32f;
			} else if (absDiff.compareTo(Indicators.NINE)>=0) {
				aligned = 6f;
				opposed = 36f;
			} else if (absDiff.compareTo(Indicators.EIGHT)>=0) {
				aligned = 6f;
				opposed = 32f;
			} else if (absDiff.compareTo(Indicators.SEVEN)>=0) {
				aligned = 6f;
				opposed = 32f;
			} else if (absDiff.compareTo(Indicators.SIX)>=0) {
				aligned = 6f;
				opposed = 32f;
			} else if (absDiff.compareTo(Indicators.FIVE)>=0) {
				aligned = 6f;
				opposed = 32f;
			} else if (absDiff.compareTo(Indicators.FOUR)>=0) {
				aligned = 6f;
				opposed = 28f;
			} else if (absDiff.compareTo(Indicators.THREE)>=0) {
				aligned = 6f;
				opposed = 24f;
			} else if (absDiff.compareTo(Indicators.TWO)>=0) {
				aligned = 6f;
				opposed = 12f;
			} else if (absDiff.compareTo(Indicators.ONE)>=0) {
				aligned = 6f;
				opposed = 9f;
			} else if (absDiff.compareTo(BigDecimal.ZERO)>=0) {
				aligned = 6f;
				opposed = 6f;
			} 
			
			RatioData rd = new RatioData();
			rd.setaDiff(aDiff);
			rd.setDiff(diff);
			rd.setWt(wt);
			rd.setWt1(wt1);
			
			if (waveSign) {
				if (diff.signum()>=0) {
					rd.setAligned(aligned);
					rd.setOpposed(opposed);
					
				} else {
					rd.setAligned(opposed);
					rd.setOpposed(aligned);
					
				}
				
			} else {
				if (diff.signum()<=0) {
					rd.setAligned(aligned);
					rd.setOpposed(opposed);
					
				} else {
					rd.setAligned(opposed);
					rd.setOpposed(aligned);
					
				}
			}
			/*
		
		switch (mv) {
		case ST:
		case SP:
		case SN: {
			if (wt1.signum()>=0) {
				if (diff.signum()>=0) {
					return Pair.create(aligned, opposed);
				} else {
					return Pair.create(opposed, aligned);
				}
				
			} else {
				if (diff.signum()<=0) {
					return Pair.create(aligned, opposed);
				} else {
					return Pair.create(opposed, aligned);
				}
			}
			
		}
		default: {
			if (wt.signum()>=0) {
				if (diff.signum()>=0) {
					return Pair.create(aligned, opposed);
				} else {
					return Pair.create(opposed, aligned);
				}
				
			} else {
				if (diff.signum()<=0) {
					return Pair.create(aligned, opposed);
				} else {
					return Pair.create(opposed, aligned);
				}
			}
		}
		}
			
		*/
		
		
		return rd;
		
	}

	public Instant getLastMatchTs() {
		return lastMatchTs;
	}

	public void setLastMatchTs(Instant lastMatchTs) {
		this.lastMatchTs = lastMatchTs;
	}

	
	public Boolean getOver() {
		/*if (wt1 != null) {
			if (wt1.compareTo(Indicators.FIFTY_THREE)>=0) {
				return true;
			} else if (wt1.compareTo(Indicators.MINUS_FIFTY_THREE)<=0) {
				return false;
			}
		}*/
		return over;
	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFirstId() {
		return firstId;
	}

	public void setFirstId(Long firstId) {
		this.firstId = firstId;
	}

	public Long getLastId() {
		return lastId;
	}

	public void setLastId(Long lastId) {
		this.lastId = lastId;
	}

	public Long getPreviousLastId() {
		return previousLastId;
	}

	public void setPreviousLastId(Long previousLastId) {
		this.previousLastId = previousLastId;
	}

	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
	}

	public void setOver(Boolean over) {
		this.over = over;
	}
	
	
	public boolean isRpXpLh(BigDecimal highestWtThreshold) {		
		return (this.isRpXp(highestWtThreshold) && this.getDecreasingHighs());
	}
	
	
	public boolean isStLh() {		
		return (this.isSt() && this.getDecreasingHighs());
	}
	
	private boolean isSt() {
		switch (this.getMv()) {
		case ST:
		case SP:
		case SN: {
			return true;
		}
		}
		return false;
	}

	public boolean isStHl() {		
		return (this.isSt() && this.getIncreasingLows());
	}
	
	public boolean isXpLh(BigDecimal highestWtThreshold) {		
		return (this.isXp(highestWtThreshold) && this.getDecreasingHighs());
	}
	
	public boolean isRnXnHl(BigDecimal highestWtThreshold) {
		return (this.isRnXn(highestWtThreshold) && this.getIncreasingLows());
	}
	
	public boolean isXnHl(BigDecimal highestWtThreshold) {
		return (this.isXn(highestWtThreshold) && this.getIncreasingLows());
	}
	
	public boolean isRpXp(BigDecimal highestWtThreshold) {
		
		boolean retval = false;
		
		if (this.getHighestWt().compareTo(highestWtThreshold)>=0) {
			switch (this.getMv()) {
		
			case RP:
			case XP: {
				retval = true;
				break;
			}

			default: {
				break;
			}
			}

		}
		return retval;
	}
	
	
	public boolean isApBp(BigDecimal highestWtThreshold) {
		
		boolean retval = false;
		
		if (this.getHighestWt().compareTo(highestWtThreshold)>=0) {
			switch (this.getMv()) {
		
			case AP:
			case BP: {
				retval = true;
				break;
			}

			default: {
				break;
			}
			}

		}
		return retval;
	}
	
	public boolean isAp(BigDecimal highestWtThreshold) {
		
		boolean retval = false;
		
		if (this.getHighestWt().compareTo(highestWtThreshold)>=0) {
			switch (this.getMv()) {
		
			case AP: {
				retval = true;
				break;
			}

			default: {
				break;
			}
			}

		}
		return retval;
	}
	
	public boolean isAnBn(BigDecimal highestWtThreshold) {
		
		boolean retval = false;
		
		if (this.getHighestWt().compareTo(highestWtThreshold)<=0) {
			switch (this.getMv()) {
		
			case AN:
			case BN: {
				retval = true;
				break;
			}

			default: {
				break;
			}
			}

		}
		return retval;
	}
	
	public boolean isAn(BigDecimal highestWtThreshold) {
		
		boolean retval = false;
		
		if (this.getHighestWt().compareTo(highestWtThreshold)<=0) {
			switch (this.getMv()) {
		
			case AN:
			 {
				retval = true;
				break;
			}

			default: {
				break;
			}
			}

		}
		return retval;
	}
	
	
	public boolean isXp(BigDecimal highestWtThreshold) {
		
		boolean retval = false;
		
		if (this.getHighestWt().compareTo(highestWtThreshold)>=0) {
			switch (this.getMv()) {
		
			case XP: {
				retval = true;
				break;
			}

			default: {
				break;
			}
			}

		}
		return retval;
	}
	
	public boolean isRnXn(BigDecimal highestWtThreshold) {
		
		boolean retval = false;
		
		if (this.getHighestWt().compareTo(highestWtThreshold)<=0) {
			switch (this.getMv()) {
		
			case RN:
			case XN: {
				retval = true;
				break;
			}

			default: {
				break;
			}
			}

		}
		return retval;
	}
	public boolean isXn(BigDecimal highestWtThreshold) {
		
		boolean retval = false;
		
		if (this.getHighestWt().compareTo(highestWtThreshold)<=0) {
			switch (this.getMv()) {
		
			case XN: {
				retval = true;
				break;
			}

			default: {
				break;
			}
			}

		}
		return retval;
	}
	
	public boolean wt1Above(BigDecimal threshold) {
		return (wt1.compareTo(threshold) >= 0);
	}
	
	public boolean wt1Below(BigDecimal threshold) {
		return (wt1.compareTo(threshold) <= 0);
	}
	
	public boolean wtAbove(BigDecimal threshold) {
		return (wt.compareTo(threshold) >= 0);
	}
	
	public boolean wtBelow(BigDecimal threshold) {
		return (wt.compareTo(threshold) <= 0);
	}
	
	public boolean highestWtAbove(BigDecimal threshold) {
		return (highestWt.compareTo(threshold) >= 0);
	}
	
	public boolean highestWtBelow(BigDecimal threshold) {
		return (highestWt.compareTo(threshold) <= 0);
	}
	
	public boolean isOverSoldDiverging(BigDecimal overSoldThreshold) {
		boolean retval = false;
		if (this.wt1Below(overSoldThreshold)) {
			switch (this.getMv()) {
			case AN:
			case BN: {
				/*if ( (getWt().compareTo(Indicators.MINUS_FOUR)<0)
						&&
						(getDiff().compareTo(Indicators.ONE)<0)
					) {
						return true;
					}
				*/
				BigDecimal div2Highest = getHighestWt().divide(Indicators.TWO, Indicators.roundTwoIndicators);
				
				if (getDiff().signum()<0) return true;
				
				if ( (getWt().compareTo(div2Highest)<0) 
						
						)
						{
					return true;
				}
				
				/*
				if (getHighestWt().compareTo(Indicators.MINUS_SIX)<=0) {
					if ( (getWt().compareTo(div2Highest)<0)
							&&
							(getDiff().compareTo(Indicators.ONE)<0)
						) {
							return true;
					}
				}*/
				
				
				
				
				
				break;
			}
			default: { break; }
			}
			
		}
		
		return retval;
	}
	
	public boolean isOverBoughtDiverging(BigDecimal overBoughtThreshold) {
		boolean retval = false;
		if (this.wt1Above(overBoughtThreshold)) {
			switch (this.getMv()) {
			case AP:
			case BP: {
				
				/*if ( (getWt().compareTo(Indicators.FOUR)>0)
					&&
					(getDiff().compareTo(Indicators.MINUS_ONE)>0)
				) {
					return true;
				}*/
				
				BigDecimal div2Highest = getHighestWt().divide(Indicators.TWO, Indicators.roundTwoIndicators);
				
				
				if (getDiff().compareTo(Indicators.ONE)>=0) return true;
				
				if ( (getWt().compareTo(div2Highest)>0) 
						
						)
						{
					
					
					
					return true;
				}
				
				/*
				if (getHighestWt().compareTo(Indicators.SIX)>=0) {
					if ( (getWt().compareTo(div2Highest)>0)
							&&
							(getDiff().compareTo(Indicators.MINUS_ONE)>0)
						) {
							return true;
						}
					
					
				}
				
				*/
				break;
			}
			default: { break; }
			}
			
		}
		
		return retval;
	}
	
	
	
}
