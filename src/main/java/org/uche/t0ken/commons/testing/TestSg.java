package org.uche.t0ken.commons.testing;

import java.time.Instant;

import org.uche.t0ken.commons.enums.StatGranularity;

public class TestSg {

	public static void main(String[] args) {
		

		StatGranularity sg = StatGranularity.S83950;
		
		System.out.println(sg.getId(Instant.parse("2022-12-02T19:19:30Z")));
		
		System.out.println(sg.toRepString());
		
		System.out.println(sg + " " + sg.getNext());
		
		
		
	}

}
