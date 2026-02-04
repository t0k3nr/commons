package org.uche.t0ken.commons.util;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.jboss.logging.Logger;


public class Wt1Matrix {
	
	private final static Logger logger = Logger.getLogger("Wt1Matrix");

	private static Map<Wt1Range, Wt1Matrix> store = new HashMap<Wt1Range, Wt1Matrix>();
	
	
	private static Object lockObj = new Object();
	
	public static void buildMatrix() {
		
		/*
		      			//		rp,		xp,		rn,		xn,		apDiff, bpDiff, rpDiff, xpDiff, anDiff, bnDiff, rnDiff, xnDiff, pWtTh, 	pWtTl , nWtTh, 	nWtTl
		store(Wt1Range.P90_, 	0.16, 	0.10, 	0.85, 	0.75,	4.4,	1.4,	-2, 	-4, 	-6, 	-2, 	0,	 	2,	 	3.50,	2, 		-3.5,		-2);
		store(Wt1Range.P80_90, 	0.17, 	0.10, 	0.85, 	0.75,	4.4,	1.4, 	-2, 	-4, 	-6, 	-2, 	0, 		2, 		3.50,	2, 		-3.5,		-2);
		store(Wt1Range.P70_80, 	0.18, 	0.10, 	0.85, 	0.75,	4.4,	1.4, 	-2, 	-4, 	-6, 	-2, 	0, 		2, 		3.50,	2,	 	-3.5,		-2);
		store(Wt1Range.P60_70, 	0.20, 	0.10, 	0.85, 	0.75,	4.5,	1.5, 	-2, 	-4, 	-6, 	-2, 	0, 		2, 		3.50,	2,	 	-3.5,		-2);
		store(Wt1Range.P50_60, 	0.30, 	0.12, 	0.85, 	0.75,	4.6,	1.6, 	-2, 	-4, 	-6, 	-2, 	0, 		2, 		3.50,	2, 		-3.5,		-2);
		store(Wt1Range.P40_50, 	0.40, 	0.15, 	0.85, 	0.75,	4.8,	1.8, 	-2, 	-4, 	-6, 	-2, 	0, 		2, 		3.50,	2, 		-3.5,		-2);
		store(Wt1Range.P30_40, 	0.55, 	0.20, 	0.85, 	0.75,	5.0,	2.0, 	-2, 	-4, 	-6, 	-2, 	0.5,	2.5, 	3.50,	2, 		-3.5,		-2);
		store(Wt1Range.P20_30, 	0.70, 	0.30, 	0.85, 	0.75,	5.3,	2.2, 	-2, 	-4, 	-6, 	-2, 	0.5, 	2.5, 	3.50,	2, 		-3.5,		-2);
		store(Wt1Range.P10_20, 	0.80, 	0.45, 	0.85, 	0.75,	5.8,	2.6, 	-2, 	-4, 	-6, 	-2, 	1, 		3, 		3.50,	2,	 	-3.5,		-2);
		store(Wt1Range.P00_10, 	0.80, 	0.60, 	0.80, 	0.75,	6,		3.2, 	-2, 	-4, 	-6, 	-2, 	1, 		3, 		3.50,	2, 		-3.5,		-2);
	
	*/
		
			 		//		rp,		xp,		rn,		xn,		apDiff, bpDiff, rpDiff, xpDiff, anDiff, bnDiff, rnDiff, xnDiff, pWtTh, 	pWtTl , nWtTh, 	nWtTl
	store(Wt1Range.P90_, 	0.16, 	0.10, 	0.85, 	0.75,	4.4,	1.4,	-2, 	-4, 	-6, 	-2, 	0,	 	2,	 	3.50,	2, 		-3.5,		-2);
	store(Wt1Range.P80_90, 	0.17, 	0.10, 	0.85, 	0.75,	4.4,	1.4, 	-2, 	-4, 	-6, 	-2, 	0, 		2, 		3.50,	2, 		-3.5,		-2);
	store(Wt1Range.P70_80, 	0.18, 	0.10, 	0.85, 	0.75,	4.4,	1.4, 	-2, 	-4, 	-6, 	-2, 	0, 		2, 		3.50,	2,	 	-3.5,		-2);
	store(Wt1Range.P60_70, 	0.20, 	0.10, 	0.85, 	0.75,	4.5,	1.5, 	-2, 	-4, 	-6, 	-2, 	0, 		2, 		3.50,	2,	 	-3.5,		-2);
	store(Wt1Range.P50_60, 	0.30, 	0.12, 	0.85, 	0.75,	4.6,	1.6, 	-2, 	-4, 	-6, 	-2, 	0, 		2, 		3.50,	2, 		-3.5,		-2);
	store(Wt1Range.P40_50, 	0.40, 	0.15, 	0.85, 	0.75,	4.8,	1.8, 	-2, 	-4, 	-6, 	-2, 	0, 		2, 		3.50,	2, 		-3.5,		-2);
	store(Wt1Range.P30_40, 	0.55, 	0.20, 	0.85, 	0.75,	5.0,	2.0, 	-2, 	-4, 	-6, 	-2, 	0.5,	2.5, 	3.50,	2, 		-3.5,		-2);
	store(Wt1Range.P20_30, 	0.70, 	0.30, 	0.85, 	0.75,	5.3,	2.2, 	-2, 	-4, 	-6, 	-2, 	0.5, 	2.5, 	3.50,	2, 		-3.5,		-2);
	store(Wt1Range.P10_20, 	0.80, 	0.45, 	0.85, 	0.75,	5.8,	2.6, 	-2, 	-4, 	-6, 	-2, 	1, 		3, 		3.50,	2,	 	-3.5,		-2);
	store(Wt1Range.P00_10, 	0.80, 	0.60, 	0.80, 	0.75,	6,		3.2, 	-2, 	-4, 	-6, 	-2, 	1, 		3, 		3.50,	2, 		-3.5,		-2);

	
	
	// +70: 0.20
	// +50: 0.30
	// +30: 0.50
	// +20: 0.70
	// -0: 0.80
	// -50: 0.85
	// -100: 0.85
	// 0
	
//  D4.9:XN wt:-7.38 highestWt:-12.03 wt1: 35.21 diff:-3.74 HH HL LP: 2021-03-20T09:09:00Z @2021-05-14T08:21:00Z

	//  D4.9:XP wt:7.38 highestWt:12.03 wt1: -35.21 diff:3.74 HH HL LP: 2021-03-20T09:09:00Z @2021-05-14T08:21:00Z
	
		logger.info(getMatrix());

}
	public static Wt1Matrix get(Wt1Range range) {
		synchronized (lockObj) {
			if (store.size() == 0) {
				buildMatrix();
			}
		}
		
		return store.get(range);
	}
	
	
	private static String getMatrix() {
		
		String res = "";
		res = res + "\n#############################################################################################################################\n";
		
		for (Wt1Range r: Wt1Range.values()) {
			res = res + store.get(r) + "\n";
		}
		res = res + "\n#############################################################################################################################\n";

		return res;
		
	}
	
	public static Wt1Matrix get(BigDecimal wt1) {
		
		
		if (wt1.compareTo(Indicators.P90)>0) {
			return get(Wt1Range.P90_);
			
		} else if (wt1.compareTo(Indicators.P80)>0) {
			return get(Wt1Range.P80_90);
			
		} else if (wt1.compareTo(Indicators.P70)>0) {
			return get(Wt1Range.P70_80);
		} else if (wt1.compareTo(Indicators.P60)>0) {
			return get(Wt1Range.P60_70);
		} else if (wt1.compareTo(Indicators.P50)>0) {
			return get(Wt1Range.P50_60);
		} else if (wt1.compareTo(Indicators.P40)>0) {
			return get(Wt1Range.P40_50);
		} else if (wt1.compareTo(Indicators.P30)>0) {
			return get(Wt1Range.P30_40);
		} else if (wt1.compareTo(Indicators.P20)>0) {
			return get(Wt1Range.P20_30);
		} else if (wt1.compareTo(Indicators.P10)>0) {
			return get(Wt1Range.P10_20);
		} else if (wt1.compareTo(BigDecimal.ZERO)>0) {
			return get(Wt1Range.P00_10);
		} else if (wt1.compareTo(Indicators.N10)>0) {
			return get(Wt1Range.N00_10);
		} else if (wt1.compareTo(Indicators.N20)>0) {
			return get(Wt1Range.N10_20);
		} else if (wt1.compareTo(Indicators.N30)>0) {
			return get(Wt1Range.N20_30);
		} else if (wt1.compareTo(Indicators.N40)>0) {
			return get(Wt1Range.N30_40);
		} else if (wt1.compareTo(Indicators.N50)>0) {
			return get(Wt1Range.N40_50);
		} else if (wt1.compareTo(Indicators.N60)>0) {
			return get(Wt1Range.N50_60);
		} else if (wt1.compareTo(Indicators.N70)>0) {
			return get(Wt1Range.N60_70);
		} else if (wt1.compareTo(Indicators.N80)>0) {
			return get(Wt1Range.N70_80);
		} else if (wt1.compareTo(Indicators.N90)>0) {
			return get(Wt1Range.N80_90);
		} else {
			return get(Wt1Range.N90_);
		}
		
	
	}
	
	private BigDecimal rpPercentStart = null;
	private BigDecimal xpPercentStart = null;
	private BigDecimal rnPercentStart = null;
	private BigDecimal xnPercentStart = null;
	
	private BigDecimal apDiffThreshold = null;
	private BigDecimal bpDiffThreshold = null;
	private BigDecimal rpDiffThreshold = null;
	private BigDecimal xpDiffThreshold = null;
	
	private BigDecimal anDiffThreshold = null;
	private BigDecimal bnDiffThreshold = null;
	private BigDecimal rnDiffThreshold = null;
	private BigDecimal xnDiffThreshold = null;
	
	private BigDecimal pWtTh = null;
	private BigDecimal pWtTl = null;
	
	private BigDecimal nWtTh = null;
	private BigDecimal nWtTl = null;
	
	/*
	data.setApDiffThreshold(new BigDecimal(8));
	data.setBpDiffThreshold(new BigDecimal(2));
	data.setRpDiffThreshold(new BigDecimal(-2));
	data.setXpDiffThreshold(new BigDecimal(-4));
	
	*/
	
	public String toString() {
		
		String result = range.toString() + ":";
		
		result = result + " rp: " + rpPercentStart;
		result = result + " xp: "+ xpPercentStart;
		result = result + " rn: "+ rnPercentStart;
		result = result + " xn: " + xnPercentStart;
		result = result + " apd: " + apDiffThreshold;
		result = result + " bpd: " + bpDiffThreshold;
		result = result + " rpd: " + rpDiffThreshold;
		result = result + " xpd: " + xpDiffThreshold;
		result = result + " and: " + anDiffThreshold;
		result = result + " bnd: " + bnDiffThreshold;
		result = result + " rnd: " + rnDiffThreshold;
		result = result + " xnd: " + xnDiffThreshold;
		result = result + " pWtTh: " + pWtTh;
		result = result + " pWtTl: " + pWtTl;
		result = result + " nWtTh: " + nWtTh;
		result = result + " nWtTl: " + nWtTl;
		
		return result;
		
	}
	
	private Wt1Range range = null;

	
	private static void store(Wt1Range range,
			double rpPercentStart,
			double xpPercentStart,
			double rnPercentStart,
			double xnPercentStart,
			
			double apDiffThreshold,
			double bpDiffThreshold,
			double rpDiffThreshold,
			double xpDiffThreshold,
			double anDiffThreshold,
			double bnDiffThreshold,
			double rnDiffThreshold,
			double xnDiffThreshold,
			
			double pwtTh,
			double pwtTl,
			double nwtTh,
			double nwtTl
			
			
			) {
		
		Wt1Matrix value = new  Wt1Matrix();
		value.setRange(range);
		value.setRpPercentStart(new BigDecimal(rpPercentStart).round(Indicators.roundTwoIndicators));
		value.setXpPercentStart(new BigDecimal(xpPercentStart).round(Indicators.roundTwoIndicators));
		value.setRnPercentStart(new BigDecimal(rnPercentStart).round(Indicators.roundTwoIndicators));
		value.setXnPercentStart(new BigDecimal(xnPercentStart).round(Indicators.roundTwoIndicators));
		
		value.setApDiffThreshold(new BigDecimal(apDiffThreshold).round(Indicators.roundTwoIndicators));
		value.setBpDiffThreshold(new BigDecimal(bpDiffThreshold).round(Indicators.roundTwoIndicators));
		value.setRpDiffThreshold(new BigDecimal(rpDiffThreshold).round(Indicators.roundTwoIndicators));
		value.setXpDiffThreshold(new BigDecimal(xpDiffThreshold).round(Indicators.roundTwoIndicators));
		
		value.setAnDiffThreshold(new BigDecimal(anDiffThreshold).round(Indicators.roundTwoIndicators));
		value.setBnDiffThreshold(new BigDecimal(bnDiffThreshold).round(Indicators.roundTwoIndicators));
		value.setRnDiffThreshold(new BigDecimal(rnDiffThreshold).round(Indicators.roundTwoIndicators));
		value.setXnDiffThreshold(new BigDecimal(xnDiffThreshold).round(Indicators.roundTwoIndicators));
		
		value.setpWtTh(new BigDecimal(pwtTh).round(Indicators.roundTwoIndicators));
		value.setpWtTl(new BigDecimal(pwtTl).round(Indicators.roundTwoIndicators));
		value.setnWtTh(new BigDecimal(nwtTh).round(Indicators.roundTwoIndicators));
		value.setnWtTl(new BigDecimal(nwtTl).round(Indicators.roundTwoIndicators));
		
		store.put(range, value);
		
		range = getContraryRange(range);
		value = new Wt1Matrix();
		value.setRange(range);
		value.setRpPercentStart(new BigDecimal(rnPercentStart).round(Indicators.roundTwoIndicators));
		value.setXpPercentStart(new BigDecimal(xnPercentStart).round(Indicators.roundTwoIndicators));
		value.setRnPercentStart(new BigDecimal(rpPercentStart).round(Indicators.roundTwoIndicators));
		value.setXnPercentStart(new BigDecimal(xpPercentStart).round(Indicators.roundTwoIndicators));
		
		value.setAnDiffThreshold(new BigDecimal(-apDiffThreshold).round(Indicators.roundTwoIndicators));
		value.setBnDiffThreshold(new BigDecimal(-bpDiffThreshold).round(Indicators.roundTwoIndicators));
		value.setRnDiffThreshold(new BigDecimal(-rpDiffThreshold).round(Indicators.roundTwoIndicators));
		value.setXnDiffThreshold(new BigDecimal(-xpDiffThreshold).round(Indicators.roundTwoIndicators));
		
		value.setApDiffThreshold(new BigDecimal(-anDiffThreshold).round(Indicators.roundTwoIndicators));
		value.setBpDiffThreshold(new BigDecimal(-bnDiffThreshold).round(Indicators.roundTwoIndicators));
		value.setRpDiffThreshold(new BigDecimal(-rnDiffThreshold).round(Indicators.roundTwoIndicators));
		value.setXpDiffThreshold(new BigDecimal(-xnDiffThreshold).round(Indicators.roundTwoIndicators));
		
		value.setpWtTh(new BigDecimal(-nwtTh).round(Indicators.roundTwoIndicators));
		value.setpWtTl(new BigDecimal(-nwtTl).round(Indicators.roundTwoIndicators));
		value.setnWtTh(new BigDecimal(-pwtTh).round(Indicators.roundTwoIndicators));
		value.setnWtTl(new BigDecimal(-pwtTl).round(Indicators.roundTwoIndicators));
		
		store.put(range, value);
	}
	
	private static Wt1Range getContraryRange(Wt1Range range2) {
		
		switch (range2) {
		
		case P90_: return Wt1Range.N90_;
		case P80_90: return Wt1Range.N80_90;
		case P70_80: return Wt1Range.N70_80; 
		case P60_70: return Wt1Range.N60_70;
		case P50_60: return Wt1Range.N50_60;
		case P40_50: return Wt1Range.N40_50;
		case P30_40: return Wt1Range.N30_40;
		case P20_30: return Wt1Range.N20_30;
		case P10_20: return Wt1Range.N10_20;
		case P00_10: return Wt1Range.N00_10;
		
		default: return null;
		}
		
		
	}

	
	public BigDecimal getRpPercentStart() {
		return rpPercentStart;
	}

	public void setRpPercentStart(BigDecimal rpPercentStart) {
		this.rpPercentStart = rpPercentStart;
	}

	public BigDecimal getXpPercentStart() {
		return xpPercentStart;
	}

	public void setXpPercentStart(BigDecimal xpPercentStart) {
		this.xpPercentStart = xpPercentStart;
	}

	public BigDecimal getRnPercentStart() {
		return rnPercentStart;
	}

	public void setRnPercentStart(BigDecimal rnPercentStart) {
		this.rnPercentStart = rnPercentStart;
	}

	public BigDecimal getXnPercentStart() {
		return xnPercentStart;
	}

	public void setXnPercentStart(BigDecimal xnPercentStart) {
		this.xnPercentStart = xnPercentStart;
	}

	public Wt1Range getRange() {
		return range;
	}

	public void setRange(Wt1Range range) {
		this.range = range;
	}

	public BigDecimal getApDiffThreshold() {
		return apDiffThreshold;
	}

	public void setApDiffThreshold(BigDecimal apDiffThreshold) {
		this.apDiffThreshold = apDiffThreshold;
	}

	public BigDecimal getBpDiffThreshold() {
		return bpDiffThreshold;
	}

	public void setBpDiffThreshold(BigDecimal bpDiffThreshold) {
		this.bpDiffThreshold = bpDiffThreshold;
	}

	public BigDecimal getRpDiffThreshold() {
		return rpDiffThreshold;
	}

	public void setRpDiffThreshold(BigDecimal rpDiffThreshold) {
		this.rpDiffThreshold = rpDiffThreshold;
	}

	public BigDecimal getXpDiffThreshold() {
		return xpDiffThreshold;
	}

	public void setXpDiffThreshold(BigDecimal xpDiffThreshold) {
		this.xpDiffThreshold = xpDiffThreshold;
	}

	public BigDecimal getAnDiffThreshold() {
		return anDiffThreshold;
	}

	public void setAnDiffThreshold(BigDecimal anDiffThreshold) {
		this.anDiffThreshold = anDiffThreshold;
	}

	public BigDecimal getBnDiffThreshold() {
		return bnDiffThreshold;
	}

	public void setBnDiffThreshold(BigDecimal bnDiffThreshold) {
		this.bnDiffThreshold = bnDiffThreshold;
	}

	public BigDecimal getRnDiffThreshold() {
		return rnDiffThreshold;
	}

	public void setRnDiffThreshold(BigDecimal rnDiffThreshold) {
		this.rnDiffThreshold = rnDiffThreshold;
	}

	public BigDecimal getXnDiffThreshold() {
		return xnDiffThreshold;
	}

	public void setXnDiffThreshold(BigDecimal xnDiffThreshold) {
		this.xnDiffThreshold = xnDiffThreshold;
	}



	public BigDecimal getpWtTh() {
		return pWtTh;
	}



	public void setpWtTh(BigDecimal pWtTh) {
		this.pWtTh = pWtTh;
	}



	public BigDecimal getpWtTl() {
		return pWtTl;
	}



	public void setpWtTl(BigDecimal pWtTl) {
		this.pWtTl = pWtTl;
	}



	public BigDecimal getnWtTh() {
		return nWtTh;
	}



	public void setnWtTh(BigDecimal nWtTh) {
		this.nWtTh = nWtTh;
	}



	public BigDecimal getnWtTl() {
		return nWtTl;
	}



	public void setnWtTl(BigDecimal nWtTl) {
		this.nWtTl = nWtTl;
	}

	
	
	
	
}
