package org.uche.t0ken.commons.testing;

import java.math.BigDecimal;

import org.graalvm.collections.Pair;
import org.uche.t0ken.commons.util.Indicators;

public class Divisors {

	public static void main(String[] args) {
		
		
		double def = 6d;
		double aligned = def;
		double opposed = def;
		
		double wt = 0d;
		double diff = 0d;
		
		double data = wt + diff;
		
		double weight = 0;
		
		
		
		
		
		for (int i = 0; i<100; i++) {
			
			Pair<Double, Double> div = Indicators.getDivisors(new BigDecimal(wt), new BigDecimal(diff));
			System.out.println("data: " + wt + " aligned: " + div.getLeft() + " opposed: " + div.getRight());
			
			wt = wt + 0.1;
			
		}
		
		
	}

}
