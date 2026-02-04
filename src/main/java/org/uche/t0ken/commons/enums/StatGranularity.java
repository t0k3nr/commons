package org.uche.t0ken.commons.enums;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Set;

public enum StatGranularity implements Serializable {

	S10(10),
	S60(60),
	S70(70),
	S80(80),
	S90(90),
	S100(100),
	S110(110),
	S120(120),
	S130(130),
	S140(140),
	S160(160),
	S170(170),
	S190(190),
	S210(210),
	S230(230),
	S250(250),
	S280(280),
	S300(300),
	S330(330),
	S370(370),
	S400(400),
	S440(440),
	S490(490),
	S540(540),
	S590(590),
	S650(650),
	S720(720),
	S790(790),
	S870(870),
	S950(950),
	S1050(1050),
	S1150(1150),
	S1270(1270),
	S1390(1390),
	S1530(1530),
	S1690(1690),
	S1850(1850),
	S2040(2040),
	S2240(2240),
	S2470(2470),
	S2720(2720),
	S2990(2990),
	S3290(3290),
	S3610(3610),
	S3980(3980),
	S4370(4370),
	S4810(4810),
	S5290(5290),
	S5820(5820),
	S6400(6400),
	S7040(7040),
	S7750(7750),
	S8520(8520),
	S9370(9370),
	S10310(10310),
	S11340(11340),
	S12480(12480),
	S13730(13730),
	S15100(15100),
	S16610(16610),
	S18270(18270),
	S20100(20100),
	S22110(22110),
	S24320(24320),
	S26750(26750),
	S29420(29420),
	S32360(32360),
	S35600(35600),
	S39160(39160),
	S43080(43080),
	S47380(47380),
	S52120(52120),
	S57340(57340),
	S63070(63070),
	S69380(69380),
	S76310(76310),
	S83950(83950),
	S92340(92340),
	S101570(101570),
	S111730(111730),
	S122900(122900),
	S135190(135190),
	S148710(148710),
	S163590(163590),
	S179940(179940),
	S197940(197940),
	S217730(217730),
	S239510(239510),
	S263460(263460),
	S289800(289800),
	S318780(318780),
	S350660(350660),
	S385730(385730),
	S424300(424300),
	S466730(466730),
	S513400(513400),
	S564740(564740),
	S621210(621210),
	S683340(683340),
	S751670(751670),
	S826840(826840),
	S909520(909520),
	S1000470(1000470),
	S1100520(1100520),
	S1210570(1210570),
	S1331630(1331630),
	S1464790(1464790),
	S1611270(1611270),
	S1772400(1772400),
	S1949640(1949640),
	S2144600(2144600),
	S2359060(2359060),
	S2594970(2594970),
	S2854460(2854460),
	S3139910(3139910),
	S3453900(3453900),
	S3799290(3799290),
	S4179220(4179220),
	S4597140(4597140),
	S5056860(5056860),
	S5562540(5562540),
	S6118800(6118800),
	S6730680(6730680),
	S7403760(7403760),
	S8144100(8144100),
	S8958540(8958540),
	S9854400(9854400),
	S10839840(10839840),
	S11923800(11923800),
	S13116180(13116180),
	S14427780(14427780),
	S15870540(15870540),
	S17457600(17457600),
	;



	/*9,223,372,036,854,775,807
	6,730,680 001,642,824,600*/
	
	
	public Long getId(Instant ts) {
		return index * 10000000000l + ts.getEpochSecond();
	}
	
	private StatGranularity(Integer index){
		this.index = index;
	}

	private Integer index;

	public Integer getIndex(){
		return this.index;
	}

	public static StatGranularity getEnum(Integer index){
		if (index == null)
	return null;

		switch(index){
		
		case 10: return S10;
		case 60: return S60;
		case 70: return S70;
		case 80: return S80;
		case 90: return S90;
		case 100: return S100;
		case 110: return S110;
		case 120: return S120;
		case 130: return S130;
		case 140: return S140;
		case 160: return S160;
		case 170: return S170;
		case 190: return S190;
		case 210: return S210;
		case 230: return S230;
		case 250: return S250;
		case 280: return S280;
		case 300: return S300;
		case 330: return S330;
		case 370: return S370;
		case 400: return S400;
		case 440: return S440;
		case 490: return S490;
		case 540: return S540;
		case 590: return S590;
		case 650: return S650;
		case 720: return S720;
		case 790: return S790;
		case 870: return S870;
		case 950: return S950;
		case 1050: return S1050;
		case 1150: return S1150;
		case 1270: return S1270;
		case 1390: return S1390;
		case 1530: return S1530;
		case 1690: return S1690;
		case 1850: return S1850;
		case 2040: return S2040;
		case 2240: return S2240;
		case 2470: return S2470;
		case 2720: return S2720;
		case 2990: return S2990;
		case 3290: return S3290;
		case 3610: return S3610;
		case 3980: return S3980;
		case 4370: return S4370;
		case 4810: return S4810;
		case 5290: return S5290;
		case 5820: return S5820;
		case 6400: return S6400;
		case 7040: return S7040;
		case 7750: return S7750;
		case 8520: return S8520;
		case 9370: return S9370;
		case 10310: return S10310;
		case 11340: return S11340;
		case 12480: return S12480;
		case 13730: return S13730;
		case 15100: return S15100;
		case 16610: return S16610;
		case 18270: return S18270;
		case 20100: return S20100;
		case 22110: return S22110;
		case 24320: return S24320;
		case 26750: return S26750;
		case 29420: return S29420;
		case 32360: return S32360;
		case 35600: return S35600;
		case 39160: return S39160;
		case 43080: return S43080;
		case 47380: return S47380;
		case 52120: return S52120;
		case 57340: return S57340;
		case 63070: return S63070;
		case 69380: return S69380;
		case 76310: return S76310;
		case 83950: return S83950;
		case 92340: return S92340;
		case 101570: return S101570;
		case 111730: return S111730;
		case 122900: return S122900;
		case 135190: return S135190;
		case 148710: return S148710;
		case 163590: return S163590;
		case 179940: return S179940;
		case 197940: return S197940;
		case 217730: return S217730;
		case 239510: return S239510;
		case 263460: return S263460;
		case 289800: return S289800;
		case 318780: return S318780;
		case 350660: return S350660;
		case 385730: return S385730;
		case 424300: return S424300;
		case 466730: return S466730;
		case 513400: return S513400;
		case 564740: return S564740;
		case 621210: return S621210;
		case 683340: return S683340;
		case 751670: return S751670;
		case 826840: return S826840;
		case 909520: return S909520;
		case 1000470: return S1000470;
		case 1100520: return S1100520;
		case 1210570: return S1210570;
		case 1331630: return S1331630;
		case 1464790: return S1464790;
		case 1611270: return S1611270;
		case 1772400: return S1772400;
		case 1949640: return S1949640;
		case 2144600: return S2144600;
		case 2359060: return S2359060;
		case 2594970: return S2594970;
		case 2854460: return S2854460;
		case 3139910: return S3139910;
		case 3453900: return S3453900;
		case 3799290: return S3799290;
		case 4179220: return S4179220;
		case 4597140: return S4597140;
		case 5056860: return S5056860;
		case 5562540: return S5562540;
		case 6118800: return S6118800;
		case 6730680: return S6730680;
		
		case 7403760: return S7403760;
		case 8144100: return S8144100;
		case 8958540: return S8958540;
		case 9854400: return S9854400;
		
		case 10839840: return S10839840;
		case 11923800: return S11923800;
		case 13116180: return S13116180;
		case 14427780: return S14427780;
		case 15870540: return S15870540;
		case 17457600: return S17457600;
		
			default: return null;
		}
	}
	
	
	public String toNominalString() {
		return super.toString();
		
	}
	
	public String toString() {
		return toRepString();
	}
	
	public String toRepString() {
		float m = index.floatValue() / 60f;
		float h = m / 60f;
		float d = h / 24f;
		if (d>=1f) {
			return String.format("D%.1f", d);
		} else if (h>=1f) {
			return String.format("H%.1f", h);
		} else if (m>=1f) {
			return String.format("M%.1f", m);
		} else {
			return "S" + index ;
		} 
		
	}
	
	private static List<StatGranularity> sgList = List.of(values());
	
	public StatGranularity getNext() {
		int index = sgList.indexOf(this); 
		if (sgList.size()>=index+2) {
			return sgList.get(index+1); 
		}
		return null;
	}
	
	public StatGranularity getPrevious() {
		int index = sgList.indexOf(this); 
		if (index>=1) {
			return sgList.get(index-1); 
		}
		return null;
	}

}