package org.uche.t0ken.commons.util;

import java.time.Instant;

public class TimeTesting {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Instant ts = Instant.now();
		
		Instant ts2 = truncateToSeconds(ts, 77910);
		
		
		System.out.println("ts: " + ts + " ts2: " + ts2);
		
		
	}
	
	
	public static Instant truncateToSeconds(Instant ts, long seconds) {
		long epocSeconds = ts.getEpochSecond();
		long result = (epocSeconds / seconds) * seconds;
		return Instant.ofEpochSecond(result);
	}

}
