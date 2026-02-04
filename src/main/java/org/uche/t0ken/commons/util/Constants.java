package org.uche.t0ken.commons.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Constants {
	public static final byte[] DEFAULT_KEY = { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
			0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
	public static DateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("EEE, d MMM yy HH:mm Z");
	public static DateFormat DEFAULT_HOUR_DATE_FORMAT = new SimpleDateFormat("EEE, d MMM yy HH:mm Z");
	public static DateFormat DEFAULT_DAY_DATE_FORMAT = new SimpleDateFormat("EEE, d MMM yy Z");
	public static final String STAT_DATE_HEADER_LABEL = "date";
	
	public static S5TemporalUnit s5 = new S5TemporalUnit();
	public static S10TemporalUnit s10 = new S10TemporalUnit();
	public static S15TemporalUnit s15 = new S15TemporalUnit();
	public static S30TemporalUnit s30 = new S30TemporalUnit();
	
	
	public static M2TemporalUnit m2 = new M2TemporalUnit();
	public static M3TemporalUnit m3 = new M3TemporalUnit();
	public static M4TemporalUnit m4 = new M4TemporalUnit();
	public static M5TemporalUnit m5 = new M5TemporalUnit();
	public static M6TemporalUnit m6 = new M6TemporalUnit();
	public static M8TemporalUnit m8 = new M8TemporalUnit();
	public static M9TemporalUnit m9 = new M9TemporalUnit();
	public static M10TemporalUnit m10 = new M10TemporalUnit();
	public static M12TemporalUnit m12 = new M12TemporalUnit();
	public static M15TemporalUnit m15 = new M15TemporalUnit();
	public static M16TemporalUnit m16 = new M16TemporalUnit();
	public static M18TemporalUnit m18 = new M18TemporalUnit();
	public static M20TemporalUnit m20 = new M20TemporalUnit();
	public static M24TemporalUnit m24 = new M24TemporalUnit();
	public static M30TemporalUnit m30 = new M30TemporalUnit();
	public static M32TemporalUnit m32 = new M32TemporalUnit();
	public static M36TemporalUnit m36 = new M36TemporalUnit();
	public static M40TemporalUnit m40 = new M40TemporalUnit();
	public static M45TemporalUnit m45 = new M45TemporalUnit();
	public static M48TemporalUnit m48 = new M48TemporalUnit();
	public static M60TemporalUnit m60 = new M60TemporalUnit();
	public static M72TemporalUnit m72 = new M72TemporalUnit();
	public static M80TemporalUnit m80 = new M80TemporalUnit();
	public static M90TemporalUnit m90 = new M90TemporalUnit();
	public static M96TemporalUnit m96 = new M96TemporalUnit();
	public static M120TemporalUnit m120 = new M120TemporalUnit();
	public static M144TemporalUnit m144 = new M144TemporalUnit();
	public static M160TemporalUnit m160 = new M160TemporalUnit();
	public static M180TemporalUnit m180 = new M180TemporalUnit();
	public static M240TemporalUnit m240 = new M240TemporalUnit();
	public static M288TemporalUnit m288 = new M288TemporalUnit();
	public static M360TemporalUnit m360 = new M360TemporalUnit();
	public static M480TemporalUnit m480 = new M480TemporalUnit();
	public static M720TemporalUnit m720 = new M720TemporalUnit();
	
	public static D1TemporalUnit d1 = new D1TemporalUnit();
	public static D2TemporalUnit d2 = new D2TemporalUnit();
	public static D3TemporalUnit d3 = new D3TemporalUnit();
	public static D4TemporalUnit d4 = new D4TemporalUnit();
	public static D5TemporalUnit d5 = new D5TemporalUnit();
	public static D6TemporalUnit d6 = new D6TemporalUnit();
	public static D8TemporalUnit d8 = new D8TemporalUnit();
	

}
