package org.uche.t0ken.commons.testing;

import java.math.BigDecimal;

import org.uche.t0ken.commons.util.Indicators;

public class TestMul {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		for (int i=8; i>-20; i--) {
			System.out.println("i: " + i + " mul: " + getMul(new BigDecimal(i), true));
		}
	}
	
	
	public static double getMul(BigDecimal diff, boolean sell) {
		
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

}
