package org.uche.t0ken.commons.testing;

import java.text.DecimalFormat;

public class Percent {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Float ratio = 0.34f;
		
		String format = "%3.0f";
		
		DecimalFormat df = new DecimalFormat("###%");
	  //  df.setMaximumFractionDigits(0);
	    //df.setMinimumFractionDigits(0);
	    
	    
	    
		System.out.println(df.format(ratio));
		
		
		System.out.println(String.format(format, ratio*100) + "%");
		
		String format2 = "%7.2f";
		
		System.out.println(String.format(format2, 3.4625635256f));
		
		
	}

}
