package org.uche.t0ken.commons.testing;

import java.math.BigDecimal;
import java.time.Instant;

import org.graalvm.collections.Pair;
import org.uche.t0ken.commons.enums.StatGranularity;
import org.uche.t0ken.commons.util.Indicators;
import org.uche.t0ken.commons.util.SgMove;

public class TestMath {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		
		for (StatGranularity sg: StatGranularity.values()) {
			System.out.println(sg.toRepString() + " " + (Math.log10(sg.getIndex().doubleValue())) + " " + sg.getIndex());
			
		}
		
		//BigDecimal bd = new BigDecimal(50);
		
		double d = 50d;
		
		for (int i = 99; i>=0; i--) {
			if (i % 10 == 0)
			System.out.println("i: " + i + " " + (1d - 
					Math.log10(new Double((i+10))
							/12d)) );
		}
		
		for (int i = 99; i>=0; i--) {
			if (i % 10 == 0)
			System.out.println("i: " + i + " " + (1d - 
					Math.log10(new Double((i+10))
					/7.8d)));
		}
		
		
		
		//new BigDecimal(((1d - Math.log10(((wt1.doubleValue()+12d))/7.8d))))
		
	//  D4.9:XN wt:-7.38 highestWt:-12.03 wt1: 35.21 diff:-3.74 HH HL LP: 2021-03-20T09:09:00Z @2021-05-14T08:21:00Z

		BigDecimal wt = new BigDecimal(-7.38);
		BigDecimal highestWt = new BigDecimal(-12.03);
		BigDecimal wt1 = new BigDecimal(35.21);
		BigDecimal diff = new BigDecimal(-3.74);
		
		
		System.out.println(TestMath._get_mv(wt1, wt, highestWt, diff));
		
		// D22.6:BN wt:-2.01 highestWt:-2.13 wt1: 90.26 diff:-4.21
		wt = new BigDecimal(-2.01);
		highestWt = new BigDecimal(-2.13);
		wt1 = new BigDecimal(90.26);
		diff = new BigDecimal(-4.21);
		
		System.out.println(TestMath._get_mv(wt1, wt, highestWt, diff));

		// D27.3:XP wt:0.48 highestWt:18.73 wt1: 90.73 diff:-5.56
		
		wt = new BigDecimal(0.48);
		highestWt = new BigDecimal(18.73);
		wt1 = new BigDecimal(90.73);
		diff = new BigDecimal(-5.56);
		
		System.out.println(TestMath._get_mv(wt1, wt, highestWt, diff));

		// D64.4:BP wt:14.17 highestWt:18.58 wt1: 98.84 diff:-3.82
		
		wt = new BigDecimal(14.17);
		highestWt = new BigDecimal(18.58);
		wt1 = new BigDecimal(98.84);
		diff = new BigDecimal(-3.82);
		
		System.out.println(TestMath._get_mv(wt1, wt, highestWt, diff));

		// D24.8:XN wt:-0.15 highestWt:-0.25 wt1: 88.65 diff:-4.42
		
		wt = new BigDecimal(-0.15);
		highestWt = new BigDecimal(-0.25);
		wt1 = new BigDecimal(88.65);
		diff = new BigDecimal(-4.42);
		
		System.out.println(TestMath._get_mv(wt1, wt, highestWt, diff));

		// D58.5:XP wt:0.55 highestWt:19.17 wt1: 91.31 diff:-10.18 LH HL LN:
		
		wt = new BigDecimal(0.55);
		highestWt = new BigDecimal(19.17);
		wt1 = new BigDecimal(30.31);
		diff = new BigDecimal(4);
		
		System.out.println(TestMath._get_mv(wt1, wt, highestWt, diff));

		// D4.9:BP wt:2.27 highestWt:14.45 wt1: 56.59 diff:0.61
		
				wt = new BigDecimal(2.27);
				highestWt = new BigDecimal(14.45);
				wt1 = new BigDecimal(56.59);
				diff = new BigDecimal(0.61);
				
				
				
				System.out.println(Indicators.getSgMove(wt, highestWt, wt1, diff, Instant.now()));
				
				
				// D4.9:XP wt:1.88 highestWt:14.45 wt1: 55.38 diff:-0.51
				
				wt = new BigDecimal(1.88);
				highestWt = new BigDecimal(14.45);
				wt1 = new BigDecimal(55.38);
				diff = new BigDecimal(-0.51);
				
				
				
				System.out.println(Indicators.getSgMove(wt, highestWt, wt1, diff, Instant.now()));

				// H2.6:XN wt:-11.59 highestWt:-13.41 wt1: -29.92 diff:2.83
				
				wt = new BigDecimal(-11.59);
				highestWt = new BigDecimal(-13.41);
				wt1 = new BigDecimal(-29.92);
				diff = new BigDecimal(2.83);
				
				
				
				System.out.println(Indicators.getSgMove(wt, highestWt, wt1, diff, Instant.now()));

				//@TODO verifier ça
				// D9.6:BP wt:9.88 highestWt:17.44 wt1: 33.17 diff:-0.77 LH LL LN: 2022-12-17T00:00:00Z @2023-04-16T00:54:00Z
				//2023-04-15T21:40:00Z
				
				
				// H19.3:RP wt:7.16 highestWt:8.58 wt1: 1.13 diff:-1.29
				
	}

	private static SgMove _get_mv(BigDecimal wt1, BigDecimal wt, BigDecimal highestWt, BigDecimal diff) {
		
		
		BigDecimal wtVsHighestRatio = null;
		
		if (highestWt.signum()==0) {
			
			wtVsHighestRatio = BigDecimal.ONE;
			
		} else if (highestWt.abs().compareTo(Indicators.TWO)<=0) {
			
				if (diff.compareTo(Indicators.SIX)>=0) {
					return SgMove.AP;
				} else if (diff.compareTo(Indicators.TWO)>=0) {
					return SgMove.BP;
				} else if (diff.signum()>=0) {
					return SgMove.SP;
				} else if (diff.compareTo(Indicators.MINUS_SIX)<=0) {
					return SgMove.AN;
				} else if (diff.compareTo(Indicators.MINUS_TWO)<=0) {
					return SgMove.BN;
				} else {
					return SgMove.SN;
				}
			
		} else {
			
			BigDecimal direction = wt.add(diff);
			wtVsHighestRatio = direction.divide(highestWt, Indicators.roundIndicators);
			if (wtVsHighestRatio.compareTo(BigDecimal.ONE)>0) {
				wtVsHighestRatio = BigDecimal.ONE;
			} else if (wtVsHighestRatio.signum()<=0) {
				wtVsHighestRatio = BigDecimal.ZERO;
			}
			
		}
		
		if (wt.signum()>=0) {
			// XP
			if (wt1.compareTo(Indicators.FIFTY)>=0) {
				if (wtVsHighestRatio.compareTo(Indicators.PERCENT_10)<=0) {
					if (diff.compareTo(Indicators.FOUR)>=0) {
						return SgMove.BP;
					} else if (diff.compareTo(Indicators.TWO)>=0) {
						return SgMove.RP;
					}
					return SgMove.XP;
				}
				
			} else if (wt1.compareTo(Indicators.EIGHT)>=0) {
				
				if (wtVsHighestRatio.compareTo(new BigDecimal((1d - Math.log10((wt1.doubleValue()+10d))/12d)))<=0) {
					if (diff.compareTo(Indicators.FOUR)>=0) {
						return SgMove.BP;
					} else if (diff.compareTo(Indicators.TWO)>=0) {
						return SgMove.RP;
					}
					return SgMove.XP;
				}
			} else {
				if (wtVsHighestRatio.compareTo(Indicators.PERCENT_60)<=0) {
					if (diff.compareTo(Indicators.FOUR)>=0) {
						return SgMove.BP;
					} else if (diff.compareTo(Indicators.TWO)>=0) {
						return SgMove.RP;
					}
					return SgMove.XP;
				}
				
			}
			
			// RP
			
			if (wt1.compareTo(Indicators.EIGHTY_FIVE)>=0) {
				
				if (wtVsHighestRatio.compareTo(Indicators.PERCENT_10)<=0) {
					if (diff.compareTo(Indicators.FOUR)>=0) {
						return SgMove.BP;
					} else if (diff.compareTo(Indicators.TWO)>=0) {
						return SgMove.RP;
					}
					return SgMove.RP;
				}
			} else if (wt1.compareTo(Indicators.FIVE)>=0) {
				
				if (wtVsHighestRatio.compareTo(new BigDecimal(((1d - Math.log10(((wt1.doubleValue()+12d))/7.8d)))))<=0) {
					if (diff.compareTo(Indicators.FOUR)>=0) {
						return SgMove.BP;
					} else if (diff.compareTo(Indicators.TWO)>=0) {
						return SgMove.RP;
					}
					return SgMove.RP;
				}
			} else {
				
				if (wtVsHighestRatio.compareTo(Indicators.PERCENT_85)<=0) {
					if (diff.compareTo(Indicators.FOUR)>=0) {
						return SgMove.BP;
					} else if (diff.compareTo(Indicators.TWO)>=0) {
						return SgMove.RP;
					}
					return SgMove.RP;
				}
			}
			if (wtVsHighestRatio.equals(BigDecimal.ONE)) {
				return SgMove.AP;
			}
			return SgMove.BP;
		} else {
			// N
			// XN
			
			wt1 = wt1.negate();
			wt = wt.negate();
			highestWt = highestWt.negate();
			diff = diff.negate();
			
			if (wt1.compareTo(Indicators.FIFTY)>=0) {
				if (wtVsHighestRatio.compareTo(Indicators.PERCENT_10)<=0) {
					if (diff.compareTo(Indicators.FOUR)>=0) {
						return SgMove.BN;
					} else if (diff.compareTo(Indicators.TWO)>=0) {
						return SgMove.RN;
					}
					return SgMove.XN;
				}
				
			} else if (wt1.compareTo(Indicators.EIGHT)>=0) {
				
				if (wtVsHighestRatio.compareTo(new BigDecimal((1d - Math.log10((wt1.doubleValue()+10d))/12d)))<=0) {
					if (diff.compareTo(Indicators.FOUR)>=0) {
						return SgMove.BN;
					} else if (diff.compareTo(Indicators.TWO)>=0) {
						return SgMove.RN;
					}
					return SgMove.XN;
				}
			} else {
				if (wtVsHighestRatio.compareTo(Indicators.PERCENT_60)<=0) {
					if (diff.compareTo(Indicators.FOUR)>=0) {
						return SgMove.BN;
					} else if (diff.compareTo(Indicators.TWO)>=0) {
						return SgMove.RN;
					}
					return SgMove.XN;
				}
				
			}
			
			// RP
			
			if (wt1.compareTo(Indicators.EIGHTY_FIVE)>=0) {
				
				if (wtVsHighestRatio.compareTo(Indicators.PERCENT_10)<=0) {
					if (diff.compareTo(Indicators.FOUR)>=0) {
						return SgMove.BN;
					} 
					
					return SgMove.RN;
					
				}
			} else if (wt1.compareTo(Indicators.FIVE)>=0) {
				
				if (wtVsHighestRatio.compareTo(new BigDecimal(((1d - Math.log10(((wt1.doubleValue()+12d))/7.8d)))))<=0) {
					if (diff.compareTo(Indicators.FOUR)>=0) {
						return SgMove.BN;
					} 
					return SgMove.RN;
				}
			} else {
				
				if (wtVsHighestRatio.compareTo(Indicators.PERCENT_85)<=0) {
					if (diff.compareTo(Indicators.FOUR)>=0) {
						return SgMove.BN;
					} 
					return SgMove.RN;
				}
			}
			if (wtVsHighestRatio.equals(BigDecimal.ONE)) {
				return SgMove.AN;
			}
			return SgMove.BN;
		}
		
	}
	
	
}

/*
	//		            rp,		xp,		rn,		xn,		apDiff, bpDiff, rpDiff, xpDiff, anDiff, bnDiff, rnDiff, xnDiff, pWtTh, 	pWtTl , nWtTh, 	nWtTl
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




i: 90 0.07918124604762478
i: 80 0.1249387366082999
i: 70 0.17609125905568124
i: 60 0.23408320603336796
i: 50 0.30102999566398114
i: 40 0.38021124171160603
i: 30 0.4771212547196624
i: 20 0.6020599913279624
i: 10 0.7781512503836436
i: 0 1.0791812460476249

*/