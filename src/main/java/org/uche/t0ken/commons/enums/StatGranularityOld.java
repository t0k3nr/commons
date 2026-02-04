package org.uche.t0ken.commons.enums;

import java.io.Serializable;

public enum StatGranularityOld implements Serializable {

	

	/*S1(0),
	S5(1), 
	S10(2), 
	S30(3), */
	
	/*M1(4), 
	M2(5), 
	M5(6), 
	M15(7),*/ 
	//H1(8), 
	//H6(9), 
	//D1(10),
	/*M10(11), 
	M30(12),*/
	//H2(13), 
	//H3(14), 
	//H4(15), 
	//M45(16), 
	
	
	
	S1(1),
	S5(5), 
	S10(10), 
	S30(30), 
	M1(60),
	M2(120),
	M3(180),
	M4(240),
	M5(300),
	M6(360),
	M8(480),
	M9(540),
	M10(600),
	M12(720),
	M15(900),
	M16(960),
	M18(1080),
	M20(1200),
	M24(1440),
	M30(1800),
	M32(1920),
	M36(2160),
	M40(2400),
	M45(2700),
	M48(2880),
	M60(3600), // H1
	M72(4320),
	M80(4800),
	M90(5400),
	M96(5760),
	//M108(6480),
	M120(7200),  // H2
	M144(8640),
	M160(9600),
	M180(10800),  // H3
	M240(14400), // H4
	M288(17280), // 
	M360(21600), // H6
	M480(28800), // H8
	M540(32400), // H8
	M720(43200), // H12
	M960(57600),
	M1080(64800), // H18
	D1(86400), // H24; D1
	M2160(129600),
	D2(172800), // D2
	D3(259200), // D3
	D4(345600), // D4
	D5(432000), // D5
	D6(518400), // D6
	D8(691200), // D8
	D9(777600), // D8
	D10(864000), // D12
	D12(1036800), // D12
	D16(1382400), // D16
	D18(1555200), // D18
	D24(2073600),
	D32(2764800),
	D36(3110400),
	D48(4147200),
	D72(6220800)
	;

	private StatGranularityOld(Integer index){
		this.index = index;
	}

	private Integer index;

	public Integer getIndex(){
		return this.index;
	}

	public static StatGranularityOld getEnum(Integer index){
		if (index == null)
	return null;

		switch(index){
		
		
		case 1: return S1;
		case 5: return S5; 
		case 10: return S10; 
		case 30: return S30;
		case 60: return M1;
		case 120: return M2;
		case 180: return M3;
		case 240: return M4;
		case 300: return M5;
		case 360: return M6;
		case 480 : return M8;
		case 540: return M9;
		case 600: return M10;
		case 720: return M12;
		case 900: return M15;
		case 960: return M16;
		case 1080: return M18;
		case 1200: return M20;
		case 1440: return M24;
		case 1800: return M30;
		case 1920: return M32;
		case 2160: return M36;
		case 2400: return M40;
		case 2700: return M45;
		case 2880: return M48;
		case 3600: return M60;
		case 4320: return M72;
		case 4800: return M80;
		case 5400: return M90;
		case 5760: return M96;
		case 7200: return M120;
		case 8640: return M144;
		case 9600: return M160;
		case 10800: return M180;
		case 14400: return M240;
		case 17280: return M288;
		case 21600: return M360;
		case 28800: return M480;
		case 32400: return M540;
		case 43200: return M720;
		case 57600: return M960;
		case 64800: return M1080;
		case 86400: return D1;
		case 129600: return M2160;
		case 172800: return D2;
		case 259200: return D3;
		case 345600: return D4;
		case 432000: return D5;
		case 518400: return D6;
		case 691200: return D8;
		case 777600: return D9;
		case 864000: return D10;
		case 1036800: return D12;
		case 1382400: return D16;
		case 1555200: return D18;
		case 2073600: return D24;
		case 2764800: return D32;
		case 3110400: return D36;
		case 4147200: return D48;
		case 6220800: return D72;
		
		
		
			default: return null;
		}
	}

}