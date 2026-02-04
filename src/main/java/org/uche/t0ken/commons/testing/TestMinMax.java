package org.uche.t0ken.commons.testing;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import org.uche.t0ken.commons.enums.StatGranularity;
import org.uche.t0ken.commons.minmax.OverMinMax;
import org.uche.t0ken.commons.minmax.WtMinMax;
import org.uche.t0ken.commons.minmax.WtType;
import org.uche.t0ken.commons.util.Indicators;

public class TestMinMax {

	public static void main(String[] args) {
				
		OverMinMax wt1_minMax = new OverMinMax(new BigDecimal(0.1d), false, null);
		
		List<BigDecimal> data = List.of(
				/*new BigDecimal(1),
				new BigDecimal(2),
				new BigDecimal(4),
				new BigDecimal(5),
				new BigDecimal(6),
				new BigDecimal(7),
				new BigDecimal(6),
				new BigDecimal(9),
				new BigDecimal(10),*/
				new BigDecimal(-35.16690100),
				new BigDecimal(-37.50684400),
				new BigDecimal(-37.50356500),
				new BigDecimal(-41.80111800),
				new BigDecimal(-42.47423500),
				new BigDecimal(-43.26735900)
				);
		
		
		for (BigDecimal b: data) {
			wt1_minMax.injectVal(b, StatGranularity.S10, Instant.now());
		}
		System.out.println(wt1_minMax.getHighs());
		System.out.println(wt1_minMax.getCurrentHigh());
		System.out.println(wt1_minMax.getCurrentLow());
		System.out.println(wt1_minMax.getMarketData());
		System.out.println(wt1_minMax.getMarketData().getHighestValue());
		System.out.println(wt1_minMax.getMarketData().getLowestValue());
		System.out.println(wt1_minMax.getMarketData().getDataSg());
		
	}
}
