package org.uche.t0ken.commons.util;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

import org.graalvm.collections.Pair;
import org.jboss.logging.Logger;
import org.uche.t0ken.commons.enums.StatGranularity;
import org.uche.t0ken.commons.exc.DataIntegrityException;
import org.uche.t0ken.commons.exc.NullEntityException;
import org.uche.t0ken.commons.minmax.MarketData;
import org.uche.t0ken.commons.minmax.RecentPeaks;
import org.uche.t0ken.commons.minmax.WtMinMax;
import org.uche.t0ken.commons.vo.StatVO;


public class Indicators {

	public static BigDecimal[] XI2 = { BigDecimal.ONE, new BigDecimal(2) };
	public static BigDecimal[] XI3 = { BigDecimal.ONE, new BigDecimal(2), new BigDecimal(3) };
	public static BigDecimal[] XI4 = { BigDecimal.ONE, new BigDecimal(2), new BigDecimal(3), new BigDecimal(4) };
	public static BigDecimal[] XI5 = { BigDecimal.ONE, new BigDecimal(2), new BigDecimal(3), new BigDecimal(4), new BigDecimal(5)};
	public static BigDecimal[] XI6 = { BigDecimal.ONE, new BigDecimal(2), new BigDecimal(3), new BigDecimal(4), new BigDecimal(5), new BigDecimal(6) };
	public static BigDecimal[] XI7 = { BigDecimal.ONE, new BigDecimal(2), new BigDecimal(3), new BigDecimal(4), new BigDecimal(5), new BigDecimal(6), new BigDecimal(7) };
	public static BigDecimal[] XI12 = { BigDecimal.ONE, new BigDecimal(2), new BigDecimal(3), new BigDecimal(4), new BigDecimal(5), new BigDecimal(6), new BigDecimal(7), new BigDecimal(8), new BigDecimal(9), new BigDecimal(10), new BigDecimal(11), new BigDecimal(12) };
	public static BigDecimal[] XI26 = { BigDecimal.ONE, new BigDecimal(2), new BigDecimal(3), new BigDecimal(4), new BigDecimal(5), new BigDecimal(6), new BigDecimal(7), new BigDecimal(8), new BigDecimal(9), new BigDecimal(10), new BigDecimal(11), new BigDecimal(12)
			, new BigDecimal(13)
			, new BigDecimal(14)
			, new BigDecimal(15)
			, new BigDecimal(16)
			, new BigDecimal(17)
			, new BigDecimal(18)
			, new BigDecimal(19)
			, new BigDecimal(20)
			, new BigDecimal(21)
			, new BigDecimal(22)
			, new BigDecimal(23)
			, new BigDecimal(24)
			, new BigDecimal(25)
			, new BigDecimal(26)};
	
	public static MathContext roundTwoIndicators = new MathContext(2,RoundingMode.HALF_EVEN);
	public static MathContext roundIndicators = new MathContext(8,RoundingMode.HALF_EVEN);
	public static MathContext accountRounding = new MathContext(16,RoundingMode.HALF_EVEN);
	
	private static Logger logger = Logger.getLogger(Indicators.class);
	public static BigDecimal ZERO_DOT_ONE = new BigDecimal(0.1).round(roundIndicators);
	public static BigDecimal ZERO_DOT_ZERO_ZERO_ONE = new BigDecimal(0.001).round(roundIndicators);
	public static BigDecimal ZERO_DOT_TWO = new BigDecimal(0.2).round(roundIndicators);
	public static BigDecimal ZERO_DOT_THREE = new BigDecimal(0.3).round(roundIndicators);
	public static BigDecimal ZERO_DOT_FOUR = new BigDecimal(0.4).round(roundIndicators);
	public static BigDecimal ZERO_DOT_FIVE = new BigDecimal(0.5).round(roundIndicators);
	public static BigDecimal ZERO_DOT_SIX = new BigDecimal(0.6).round(roundIndicators);
	public static BigDecimal ZERO_DOT_SEVEN = new BigDecimal(0.7).round(roundIndicators);
	public static BigDecimal ZERO_DOT_EIGHT = new BigDecimal(0.8).round(roundIndicators);
	public static BigDecimal ZERO_DOT_NINE = new BigDecimal(0.9).round(roundIndicators);
	public static BigDecimal ZERO_DOT_NINE_NINE = new BigDecimal(0.99).round(roundIndicators);
	public static BigDecimal ONE_DOT_ZERO_ONE = new BigDecimal(1.01).round(roundIndicators);
	public static BigDecimal ONE_DOT_ZERO_FIVE = new BigDecimal(1.05).round(roundIndicators);
	public static BigDecimal ONE_DOT_ZERO_ZERO_FIVE = new BigDecimal(1.005).round(roundIndicators);
	
	// 
	public static BigDecimal ONE = new BigDecimal(1).round(roundIndicators);
	public static BigDecimal ONE_DOT_ONE = new BigDecimal(1.1).round(roundIndicators);
	public static BigDecimal ONE_DOT_TWO = new BigDecimal(1.2).round(roundIndicators);
	public static BigDecimal ONE_DOT_THREE = new BigDecimal(1.3).round(roundIndicators);
	public static BigDecimal ONE_DOT_FOUR = new BigDecimal(1.4).round(roundIndicators);
	public static BigDecimal ONE_DOT_FIVE = new BigDecimal(1.5).round(roundIndicators);
	public static BigDecimal ONE_DOT_SIX = new BigDecimal(1.6).round(roundIndicators);
	public static BigDecimal ONE_DOT_SEVEN = new BigDecimal(1.7).round(roundIndicators);
	public static BigDecimal ONE_DOT_EIGHT = new BigDecimal(1.8).round(roundIndicators);
	public static BigDecimal ONE_DOT_NINE = new BigDecimal(1.9).round(roundIndicators);
	public static BigDecimal TWO = new BigDecimal(2).round(roundIndicators);
	public static BigDecimal TWO_DOT_ONE = new BigDecimal(2.1).round(roundIndicators);
	public static BigDecimal TWO_DOT_TWO = new BigDecimal(2.2).round(roundIndicators);
	public static BigDecimal TWO_DOT_THREE = new BigDecimal(2.3).round(roundIndicators);
	public static BigDecimal TWO_DOT_FOUR = new BigDecimal(2.4).round(roundIndicators);
	public static BigDecimal TWO_DOT_FIVE = new BigDecimal(2.5).round(roundIndicators);
	public static BigDecimal TWO_DOT_SIX = new BigDecimal(2.6).round(roundIndicators);
	public static BigDecimal TWO_DOT_SEVEN = new BigDecimal(2.7).round(roundIndicators);
	public static BigDecimal TWO_DOT_EIGHT = new BigDecimal(2.8).round(roundIndicators);
	public static BigDecimal TWO_DOT_NINE = new BigDecimal(2.9).round(roundIndicators);
	public static BigDecimal THREE = new BigDecimal(3).round(roundIndicators);
	public static BigDecimal THREE_DOT_FIVE = new BigDecimal(3.5).round(roundIndicators);
	public static BigDecimal FOUR = new BigDecimal(4).round(roundIndicators);
	public static BigDecimal FOUR_DOT_FIVE = new BigDecimal(4.5).round(roundIndicators);
	public static BigDecimal HUNDRED = new BigDecimal(100).round(roundIndicators);
	public static BigDecimal THOUSAND = new BigDecimal(1000).round(roundIndicators);
	public static BigDecimal TWO_THOUSAND = new BigDecimal(2000).round(roundIndicators);
	public static BigDecimal THREE_THOUSAND = new BigDecimal(3000).round(roundIndicators);
	public static BigDecimal SIX_THOUSAND = new BigDecimal(6000).round(roundIndicators);
	public static BigDecimal ELEVEN = new BigDecimal(11).round(roundIndicators);
	public static BigDecimal TWELVE = new BigDecimal(12).round(roundIndicators);
	public static BigDecimal FOURTEEN = new BigDecimal(14).round(roundIndicators);
	public static BigDecimal SIXTEEN = new BigDecimal(16).round(roundIndicators);
	public static BigDecimal EIGHTEEN = new BigDecimal(18).round(roundIndicators);
	public static BigDecimal MINUS_FOURTEEN = new BigDecimal(-14).round(roundIndicators);
	public static BigDecimal MINUS_SIXTEEN = new BigDecimal(-16).round(roundIndicators);
	public static BigDecimal MINUS_EIGHTEEN = new BigDecimal(-18).round(roundIndicators);
	public static BigDecimal TWENTY_SIX = new BigDecimal(26).round(roundIndicators);
	public static BigDecimal TWENTY_FIVE = new BigDecimal(25).round(roundIndicators);

	
	public static BigDecimal FOURTY = new BigDecimal(40).round(roundIndicators);
	public static BigDecimal FOURTY_ONE = new BigDecimal(41).round(roundIndicators);
	public static BigDecimal FOURTY_TWO = new BigDecimal(42).round(roundIndicators);
	public static BigDecimal FOURTY_THREE = new BigDecimal(43).round(roundIndicators);
	public static BigDecimal FOURTY_FOUR = new BigDecimal(44).round(roundIndicators);
	public static BigDecimal FOURTY_FIVE = new BigDecimal(45).round(roundIndicators);
	public static BigDecimal FOURTY_SIX = new BigDecimal(46).round(roundIndicators);
	public static BigDecimal FOURTY_SEVEN = new BigDecimal(47).round(roundIndicators);
	public static BigDecimal FOURTY_EIGHT = new BigDecimal(48).round(roundIndicators);
	public static BigDecimal FOURTY_NINE = new BigDecimal(49).round(roundIndicators);
	public static BigDecimal MINUS_FOURTY = new BigDecimal(-40).round(roundIndicators);
	public static BigDecimal MINUS_FOURTY_ONE = new BigDecimal(-41).round(roundIndicators);
	public static BigDecimal MINUS_FOURTY_TWO = new BigDecimal(-42).round(roundIndicators);
	public static BigDecimal MINUS_FOURTY_THREE = new BigDecimal(-43).round(roundIndicators);
	public static BigDecimal MINUS_FOURTY_FOUR = new BigDecimal(-44).round(roundIndicators);
	public static BigDecimal MINUS_FOURTY_FIVE = new BigDecimal(-45).round(roundIndicators);
	public static BigDecimal MINUS_FOURTY_SIX = new BigDecimal(-46).round(roundIndicators);
	public static BigDecimal MINUS_FOURTY_SEVEN = new BigDecimal(-47).round(roundIndicators);
	public static BigDecimal MINUS_FOURTY_EIGHT = new BigDecimal(-48).round(roundIndicators);
	public static BigDecimal MINUS_FOURTY_NINE = new BigDecimal(-49).round(roundIndicators);
	public static BigDecimal MINUS_SIXTY_FIVE = new BigDecimal(-65).round(roundIndicators);
	public static BigDecimal MINUS_TWENTY = new BigDecimal(-20).round(roundIndicators);
	public static BigDecimal MINUS_TWENTY_FIVE = new BigDecimal(-25).round(roundIndicators);
	
	
	public static BigDecimal MINUS_THIRTY = new BigDecimal(-30).round(roundIndicators);
	public static BigDecimal MINUS_THIRTY_ONE = new BigDecimal(-31).round(roundIndicators);
	public static BigDecimal MINUS_THIRTY_TWO = new BigDecimal(-32).round(roundIndicators);
	public static BigDecimal MINUS_THIRTY_THREE = new BigDecimal(-33).round(roundIndicators);
	public static BigDecimal MINUS_THIRTY_FOUR = new BigDecimal(-34).round(roundIndicators);
	public static BigDecimal MINUS_THIRTY_FIVE = new BigDecimal(-35).round(roundIndicators);
	public static BigDecimal MINUS_THIRTY_SIX = new BigDecimal(-36).round(roundIndicators);
	public static BigDecimal MINUS_THIRTY_SEVEN = new BigDecimal(-37).round(roundIndicators);
	public static BigDecimal MINUS_THIRTY_EIGHT = new BigDecimal(-38).round(roundIndicators);
	public static BigDecimal MINUS_THIRTY_NINE = new BigDecimal(-39).round(roundIndicators);
	
	public static BigDecimal THIRTY = new BigDecimal(30).round(roundIndicators);
	public static BigDecimal THIRTY_ONE = new BigDecimal(31).round(roundIndicators);
	public static BigDecimal THIRTY_TWO = new BigDecimal(32).round(roundIndicators);
	public static BigDecimal THIRTY_THREE = new BigDecimal(33).round(roundIndicators);
	public static BigDecimal THIRTY_FOUR = new BigDecimal(34).round(roundIndicators);
	public static BigDecimal THIRTY_FIVE = new BigDecimal(35).round(roundIndicators);
	public static BigDecimal THIRTY_SIX = new BigDecimal(36).round(roundIndicators);
	public static BigDecimal THIRTY_SEVEN = new BigDecimal(37).round(roundIndicators);
	public static BigDecimal THIRTY_EIGHT = new BigDecimal(38).round(roundIndicators);
	public static BigDecimal THIRTY_NINE = new BigDecimal(39).round(roundIndicators);
	
	
	public static BigDecimal MINUS_FIFTY = new BigDecimal(-50).round(roundIndicators);
	public static BigDecimal TWENTY = new BigDecimal(20).round(roundIndicators);
	public static BigDecimal FIFTY = new BigDecimal(50).round(roundIndicators);
	public static BigDecimal FIFTY_ONE = new BigDecimal(51).round(roundIndicators);
	public static BigDecimal FIFTY_TWO = new BigDecimal(52).round(roundIndicators);
	public static BigDecimal FIFTY_THREE = new BigDecimal(53).round(roundIndicators);
	public static BigDecimal FIFTY_FOUR = new BigDecimal(54).round(roundIndicators);
	public static BigDecimal FIFTY_FIVE = new BigDecimal(55).round(roundIndicators);
	public static BigDecimal FIFTY_SIX = new BigDecimal(56).round(roundIndicators);
	public static BigDecimal FIFTY_SEVEN = new BigDecimal(57).round(roundIndicators);
	public static BigDecimal FIFTY_EIGHT = new BigDecimal(58).round(roundIndicators);
	public static BigDecimal FIFTY_NINE = new BigDecimal(59).round(roundIndicators);
	public static BigDecimal MINUS_FIFTY_ONE = new BigDecimal(-51).round(roundIndicators);
	public static BigDecimal MINUS_FIFTY_TWO = new BigDecimal(-52).round(roundIndicators);
	public static BigDecimal MINUS_FIFTY_THREE = new BigDecimal(-53).round(roundIndicators);
	public static BigDecimal MINUS_FIFTY_FOUR = new BigDecimal(-54).round(roundIndicators);
	public static BigDecimal MINUS_FIFTY_FIVE = new BigDecimal(-55).round(roundIndicators);
	public static BigDecimal MINUS_FIFTY_SIX = new BigDecimal(-56).round(roundIndicators);
	public static BigDecimal MINUS_FIFTY_SEVEN = new BigDecimal(-57).round(roundIndicators);
	public static BigDecimal MINUS_FIFTY_EIGHT = new BigDecimal(-58).round(roundIndicators);
	public static BigDecimal MINUS_FIFTY_NINE = new BigDecimal(-59).round(roundIndicators);
	public static BigDecimal FIFTEEN = new BigDecimal(15).round(roundIndicators);
	public static BigDecimal THIRTEEN = new BigDecimal(13).round(roundIndicators);
	public static BigDecimal MINUS_TEN = new BigDecimal(-10).round(roundIndicators);
	public static BigDecimal MINUS_TWELVE = new BigDecimal(-12).round(roundIndicators);
	public static BigDecimal TEN = new BigDecimal(10).round(roundIndicators);
	public static BigDecimal SIXTY = new BigDecimal(60).round(roundIndicators);
	public static BigDecimal SEVENTY = new BigDecimal(70).round(roundIndicators);
	public static BigDecimal SEVENTY_FIVE = new BigDecimal(75).round(roundIndicators);
	public static BigDecimal SIXTY_FIVE = new BigDecimal(65).round(roundIndicators);
	public static BigDecimal MINUS_SIXTY = new BigDecimal(-60).round(roundIndicators);
	public static BigDecimal MINUS_SEVENTY = new BigDecimal(-70).round(roundIndicators);
	public static BigDecimal MINUS_SEVENTEEN = new BigDecimal(-17).round(roundIndicators);
	public static BigDecimal MINUS_FIFTEEN = new BigDecimal(-15).round(roundIndicators);
	public static BigDecimal MINUS_SEVENTY_FIVE = new BigDecimal(-75).round(roundIndicators);
	public static BigDecimal MINUS_SEVEN = new BigDecimal(-7).round(roundIndicators);
	public static BigDecimal SEVEN = new BigDecimal(7).round(roundIndicators);
	public static BigDecimal NINE = new BigDecimal(9).round(roundIndicators);
	public static BigDecimal MINUS_EIGHT = new BigDecimal(-8).round(roundIndicators);
	public static BigDecimal MINUS_NINE = new BigDecimal(-9).round(roundIndicators);
	public static BigDecimal MINUS_SIX = new BigDecimal(-6).round(roundIndicators);
	public static BigDecimal MINUS_FIVE = new BigDecimal(-5).round(roundIndicators);
	public static BigDecimal MINUS_FOUR = new BigDecimal(-4).round(roundIndicators);
	public static BigDecimal MINUS_THREE = new BigDecimal(-3).round(roundIndicators);
	public static BigDecimal MINUS_TWO = new BigDecimal(-2).round(roundIndicators);
	public static BigDecimal MINUS_TWO_DOT_NINE = new BigDecimal(-2.9).round(roundIndicators);
	public static BigDecimal MINUS_TWO_DOT_EIGHT = new BigDecimal(-2.8).round(roundIndicators);
	public static BigDecimal MINUS_TWO_DOT_SEVEN = new BigDecimal(-2.7).round(roundIndicators);
	public static BigDecimal MINUS_TWO_DOT_SIX = new BigDecimal(-2.6).round(roundIndicators);
	public static BigDecimal MINUS_TWO_DOT_FIVE = new BigDecimal(-2.5).round(roundIndicators);
	public static BigDecimal MINUS_TWO_DOT_FOUR = new BigDecimal(-2.4).round(roundIndicators);
	public static BigDecimal MINUS_TWO_DOT_THREE = new BigDecimal(-2.3).round(roundIndicators);
	public static BigDecimal MINUS_TWO_DOT_TWO = new BigDecimal(-2.2).round(roundIndicators);
	public static BigDecimal MINUS_TWO_DOT_ONE = new BigDecimal(-2.1).round(roundIndicators);
	public static BigDecimal MINUS_ONE_DOT_ONE = new BigDecimal(-1.1).round(roundIndicators);
	public static BigDecimal MINUS_ONE_DOT_TWO = new BigDecimal(-1.2).round(roundIndicators);
	public static BigDecimal MINUS_ONE_DOT_THREE = new BigDecimal(-1.3).round(roundIndicators);
	public static BigDecimal MINUS_ONE_DOT_FOUR = new BigDecimal(-1.4).round(roundIndicators);
	public static BigDecimal MINUS_ONE_DOT_FIVE = new BigDecimal(-1.5).round(roundIndicators);
	public static BigDecimal MINUS_ONE_DOT_SIX = new BigDecimal(-1.6).round(roundIndicators);
	public static BigDecimal MINUS_ONE_DOT_SEVEN = new BigDecimal(-1.7).round(roundIndicators);
	public static BigDecimal MINUS_ONE_DOT_NINE = new BigDecimal(-1.9).round(roundIndicators);
	public static BigDecimal MINUS_ONE_DOT_EIGHT = new BigDecimal(-1.8).round(roundIndicators);
	public static BigDecimal MINUS_ONE = new BigDecimal(-1).round(roundIndicators);
	public static BigDecimal MINUS_ZERO_DOT_ONE = new BigDecimal(-0.1).round(roundIndicators);
	public static BigDecimal MINUS_ZERO_DOT_TWO = new BigDecimal(-0.2).round(roundIndicators);
	public static BigDecimal MINUS_ZERO_DOT_THREE = new BigDecimal(-0.3).round(roundIndicators);
	public static BigDecimal MINUS_ZERO_DOT_FOUR = new BigDecimal(-0.4).round(roundIndicators);
	public static BigDecimal MINUS_ZERO_DOT_FIVE = new BigDecimal(-0.5).round(roundIndicators);
	public static BigDecimal MINUS_ZERO_DOT_SIX = new BigDecimal(-0.6).round(roundIndicators);
	public static BigDecimal MINUS_ZERO_DOT_SEVEN = new BigDecimal(-0.7).round(roundIndicators);
	public static BigDecimal MINUS_ZERO_DOT_EIGHT = new BigDecimal(-0.8).round(roundIndicators);
	public static BigDecimal MINUS_ZERO_DOT_NINE = new BigDecimal(-0.9).round(roundIndicators);
	
	
	public static BigDecimal EIGHTY = new BigDecimal(80).round(roundIndicators);
	public static BigDecimal NINETY = new BigDecimal(90).round(roundIndicators);
	public static BigDecimal EIGHTY_FIVE = new BigDecimal(85).round(roundIndicators);
	public static BigDecimal NINETY_FIVE = new BigDecimal(95).round(roundIndicators);
	public static BigDecimal NINETY_EIGTH = new BigDecimal(98).round(roundIndicators);
	
	
	public static BigDecimal MINUS_EIGHTY = new BigDecimal(-80).round(roundIndicators);
	public static BigDecimal MINUS_NINETY = new BigDecimal(-90).round(roundIndicators);
	
	// 
	public static BigDecimal MINUS_170 = new BigDecimal(-170).round(roundIndicators);
	public static BigDecimal MINUS_200 = new BigDecimal(-200).round(roundIndicators);
	
	public static BigDecimal SIXTY_PERCENT = new BigDecimal(0.6).round(roundIndicators);
	
	
	
	
	public static BigDecimal TEN_THOUSAND = new BigDecimal(10000).round(roundIndicators);
	public static BigDecimal FIVE = new BigDecimal(5).round(roundIndicators);
	public static BigDecimal FIVE_DOT_FIVE = new BigDecimal(5.5).round(roundIndicators);
	public static BigDecimal SIX = new BigDecimal(6).round(roundIndicators);
	public static BigDecimal EIGHT = new BigDecimal(8).round(roundIndicators);
	public static BigDecimal ONE_THOUSANDTH = new BigDecimal(0.001).round(roundIndicators);
	public static BigDecimal TWO_THOUSANDTH = new BigDecimal(0.002).round(roundIndicators);
	public static BigDecimal THREE_THOUSANDTH = new BigDecimal(0.003).round(roundIndicators);
	public static BigDecimal FOUR_THOUSANDTH = new BigDecimal(0.004).round(roundIndicators);
	public static BigDecimal FIVE_THOUSANDTH = new BigDecimal(0.005).round(roundIndicators);
	public static BigDecimal ONE_THOUSANDTH_NEGATIVE = new BigDecimal(-0.001).round(roundIndicators);
	public static BigDecimal TWO_THOUSANDTH_NEGATIVE = new BigDecimal(-0.002).round(roundIndicators);
	public static BigDecimal THREE_THOUSANDTH_NEGATIVE = new BigDecimal(-0.003).round(roundIndicators);
	public static BigDecimal ONE_TENTHOUSANDTH = new BigDecimal(0.0001).round(roundIndicators);
	public static BigDecimal THREE_TENTHOUSANDTH = new BigDecimal(0.0003).round(roundIndicators);
	public static BigDecimal ONE_TENTHOUSANDTH_NEGATIVE = new BigDecimal(-0.0001).round(roundIndicators);
	public static BigDecimal SIX_THOUSANDTH = new BigDecimal(0.006).round(roundIndicators);
	public static BigDecimal EIGHT_THOUSANDTH = new BigDecimal(0.008).round(roundIndicators);
	public static BigDecimal EIGHT_TENTHOUSANDTH = new BigDecimal(0.0008).round(roundIndicators);
	public static BigDecimal EIGHT_TENTHOUSANDTH_NEGATIVE = new BigDecimal(-0.0008).round(roundIndicators);
	public static BigDecimal FIFTEEN_TENTHOUSANDTH = new BigDecimal(0.0015).round(roundIndicators);
	public static BigDecimal FIFTEEN_TENTHOUSANDTH_NEGATIVE = new BigDecimal(-0.0015).round(roundIndicators);
	
	public static BigDecimal ZERO_DOT_FOUR_PERCENT = new BigDecimal(0.004).round(roundIndicators);
	public static BigDecimal ONE_PERCENT = new BigDecimal(0.01).round(roundIndicators);
	public static BigDecimal ONE_DOT_TWO_PERCENT = new BigDecimal(0.012).round(roundIndicators);
	public static BigDecimal MINUS_ONE_PERCENT = new BigDecimal(-0.01).round(roundIndicators);
	public static BigDecimal MINUS_FIVE_THOUSANDTH = new BigDecimal(-0.005).round(roundIndicators);
	
	
	public static BigDecimal TWO_PERCENT = new BigDecimal(0.02).round(roundIndicators);
	public static BigDecimal THREE_PERCENT = new BigDecimal(0.03).round(roundIndicators);
	public static BigDecimal FOUR_PERCENT = new BigDecimal(0.04).round(roundIndicators);
	public static BigDecimal FIVE_PERCENT = new BigDecimal(0.05).round(roundIndicators);
	public static BigDecimal TEN_PERCENT = new BigDecimal(0.1).round(roundIndicators);
	public static BigDecimal TWENTY_PERCENT = new BigDecimal(0.2).round(roundIndicators);
	public static BigDecimal FOURTY_PERCENT = new BigDecimal(0.4).round(roundIndicators);
	public static BigDecimal HUNDRED_PERCENT = BigDecimal.ONE;
	public static BigDecimal SEVENTY_PERCENT = new BigDecimal(0.7).round(roundIndicators);
	public static BigDecimal PEAK_THRESHOLD = new BigDecimal(0.0035).round(roundIndicators);
	public static BigDecimal PEAK_THRESHOLD_NEGATIVE = new BigDecimal(-0.0035).round(roundIndicators);
	
	
	
	/*public static BigDecimal ONE_DOT_FIVE_PERCENT = new BigDecimal(0.015).round(roundIndicators);
	public static BigDecimal ONE_DOT_FIVE_PERCENT_NEGATIVE = new BigDecimal(-0.015).round(roundIndicators);
	*/
	
	
	
	public static BigDecimal EIGHTY_PERCENT = new BigDecimal(0.8).round(roundIndicators);
	public static BigDecimal EIGHTY_EIGHT_PERCENT = new BigDecimal(0.88).round(roundIndicators);
	public static BigDecimal NINETY_TWO_PERCENT = new BigDecimal(0.92).round(roundIndicators);
	
	public static BigDecimal NINETY_PERCENT = new BigDecimal(0.9).round(roundIndicators);
	public static BigDecimal NINETY_FIVE_PERCENT = new BigDecimal(0.95).round(roundIndicators);
	/*
	public static BigDecimal ZERO_DOT_SIX_PERCENT = new BigDecimal(0.00666667).round(roundIndicators);
	public static BigDecimal TWO_PERCENT = new BigDecimal(0.02).round(roundIndicators);
	public static BigDecimal SIX_PERCENT = new BigDecimal(0.06).round(roundIndicators);
	public static BigDecimal EIGHTEEN_PERCENT = new BigDecimal(0.18).round(roundIndicators);
	public static BigDecimal FORTYTWO_PERCENT = new BigDecimal(0.42).round(roundIndicators);
	public static BigDecimal HUNDRED_PERCENT = new BigDecimal(1).round(roundIndicators);
	*/
	
	public static BigDecimal BUDGET_7 = new BigDecimal(0.0004).round(roundIndicators);
	public static BigDecimal BUDGET_6 = new BigDecimal(0.0012).round(roundIndicators);
	public static BigDecimal BUDGET_5 = new BigDecimal(0.004).round(roundIndicators);
	public static BigDecimal BUDGET_4 = new BigDecimal(0.012).round(roundIndicators);
	public static BigDecimal BUDGET_3 = new BigDecimal(0.036).round(roundIndicators);
	public static BigDecimal BUDGET_2 = new BigDecimal(0.108).round(roundIndicators);
	public static BigDecimal BUDGET_1 = new BigDecimal(0.324).round(roundIndicators);
	public static BigDecimal BUDGET_0 = new BigDecimal(1).round(roundIndicators);
	
	
	
	
	
	
	public static BigDecimal EMA4320 = new BigDecimal(4320).round(roundIndicators);
	public static BigDecimal EMA720 = new BigDecimal(720).round(roundIndicators);
	public static BigDecimal EMA180 = new BigDecimal(180).round(roundIndicators);
	public static BigDecimal EMA390 = new BigDecimal(390).round(roundIndicators);
	public static BigDecimal EMA100 = new BigDecimal(100).round(roundIndicators);
	public static BigDecimal EMA1560 = new BigDecimal(1560).round(roundIndicators);
	
	
	/*
	 * PERCENT
	 */
	
	public static BigDecimal PERCENT_1 = new BigDecimal(0.01).round(roundIndicators);
	public static BigDecimal PERCENT_5 = new BigDecimal(0.05).round(roundIndicators);
	public static BigDecimal PERCENT_10 = new BigDecimal(0.1).round(roundIndicators);
	public static BigDecimal PERCENT_20 = new BigDecimal(0.2).round(roundIndicators);
	public static BigDecimal PERCENT_30 = new BigDecimal(0.3).round(roundIndicators);
	public static BigDecimal PERCENT_40 = new BigDecimal(0.4).round(roundIndicators);
	public static BigDecimal PERCENT_45 = new BigDecimal(0.45).round(roundIndicators);
	public static BigDecimal PERCENT_50 = new BigDecimal(0.5).round(roundIndicators);
	public static BigDecimal PERCENT_60 = new BigDecimal(0.6).round(roundIndicators);
	public static BigDecimal PERCENT_65 = new BigDecimal(0.65).round(roundIndicators);
	public static BigDecimal PERCENT_70 = new BigDecimal(0.7).round(roundIndicators);
	public static BigDecimal PERCENT_75 = new BigDecimal(0.75).round(roundIndicators);
	public static BigDecimal PERCENT_80 = new BigDecimal(0.8).round(roundIndicators);
	public static BigDecimal PERCENT_85 = new BigDecimal(0.85).round(roundIndicators);
	public static BigDecimal PERCENT_90 = new BigDecimal(0.9).round(roundIndicators);
	public static BigDecimal PERCENT_95 = new BigDecimal(0.95).round(roundIndicators);
	public static BigDecimal PERCENT_98 = new BigDecimal(0.98).round(roundIndicators);
	public static BigDecimal PERCENT_99 = new BigDecimal(0.99).round(roundIndicators);
	public static BigDecimal PERCENT_99_5 = new BigDecimal(0.995).round(roundIndicators);
	public static BigDecimal PERCENT_99_75 = new BigDecimal(0.9975).round(roundIndicators);
	public static BigDecimal PERCENT_100 = new BigDecimal(1).round(roundIndicators);
	
	
	
	
	public static BigDecimal P01 = new BigDecimal(1).round(roundIndicators);
	public static BigDecimal P02 = new BigDecimal(2).round(roundIndicators);
	public static BigDecimal P03 = new BigDecimal(3).round(roundIndicators);
	public static BigDecimal P04 = new BigDecimal(4).round(roundIndicators);
	public static BigDecimal P05 = new BigDecimal(5).round(roundIndicators);
	public static BigDecimal P06 = new BigDecimal(6).round(roundIndicators);
	public static BigDecimal P07 = new BigDecimal(7).round(roundIndicators);
	public static BigDecimal P08 = new BigDecimal(8).round(roundIndicators);
	public static BigDecimal P09 = new BigDecimal(9).round(roundIndicators);
	
	
	
	public static BigDecimal P10 = new BigDecimal(10).round(roundIndicators);
	public static BigDecimal P11 = new BigDecimal(11).round(roundIndicators);
	public static BigDecimal P12 = new BigDecimal(12).round(roundIndicators);
	public static BigDecimal P13 = new BigDecimal(13).round(roundIndicators);
	public static BigDecimal P14 = new BigDecimal(14).round(roundIndicators);
	public static BigDecimal P15 = new BigDecimal(15).round(roundIndicators);
	public static BigDecimal P16 = new BigDecimal(16).round(roundIndicators);
	public static BigDecimal P17 = new BigDecimal(17).round(roundIndicators);
	public static BigDecimal P18 = new BigDecimal(18).round(roundIndicators);
	public static BigDecimal P19 = new BigDecimal(19).round(roundIndicators);
	
	
	
	
	
	public static BigDecimal P20 = new BigDecimal(20).round(roundIndicators);
	public static BigDecimal P21 = new BigDecimal(21).round(roundIndicators);
	public static BigDecimal P22 = new BigDecimal(22).round(roundIndicators);
	public static BigDecimal P23 = new BigDecimal(23).round(roundIndicators);
	public static BigDecimal P24 = new BigDecimal(24).round(roundIndicators);
	public static BigDecimal P25 = new BigDecimal(25).round(roundIndicators);
	public static BigDecimal P26 = new BigDecimal(26).round(roundIndicators);
	public static BigDecimal P27 = new BigDecimal(27).round(roundIndicators);
	public static BigDecimal P28 = new BigDecimal(28).round(roundIndicators);
	public static BigDecimal P29 = new BigDecimal(29).round(roundIndicators);
	
	public static BigDecimal P30 = new BigDecimal(30).round(roundIndicators);
	public static BigDecimal P31 = new BigDecimal(31).round(roundIndicators);
	public static BigDecimal P32 = new BigDecimal(32).round(roundIndicators);
	public static BigDecimal P33 = new BigDecimal(33).round(roundIndicators);
	public static BigDecimal P34 = new BigDecimal(34).round(roundIndicators);
	public static BigDecimal P35 = new BigDecimal(35).round(roundIndicators);
	public static BigDecimal P36 = new BigDecimal(36).round(roundIndicators);
	public static BigDecimal P37 = new BigDecimal(37).round(roundIndicators);
	public static BigDecimal P38 = new BigDecimal(38).round(roundIndicators);
	public static BigDecimal P39 = new BigDecimal(39).round(roundIndicators);
	
	public static BigDecimal P40 = new BigDecimal(40).round(roundIndicators);
	public static BigDecimal P41 = new BigDecimal(41).round(roundIndicators);
	public static BigDecimal P42 = new BigDecimal(42).round(roundIndicators);
	public static BigDecimal P43 = new BigDecimal(43).round(roundIndicators);
	public static BigDecimal P44 = new BigDecimal(44).round(roundIndicators);
	public static BigDecimal P45 = new BigDecimal(45).round(roundIndicators);
	public static BigDecimal P46 = new BigDecimal(46).round(roundIndicators);
	public static BigDecimal P47 = new BigDecimal(47).round(roundIndicators);
	public static BigDecimal P48 = new BigDecimal(48).round(roundIndicators);
	public static BigDecimal P49 = new BigDecimal(49).round(roundIndicators);
	
	public static BigDecimal P50 = new BigDecimal(50).round(roundIndicators);
	public static BigDecimal P51 = new BigDecimal(51).round(roundIndicators);
	public static BigDecimal P52 = new BigDecimal(52).round(roundIndicators);
	public static BigDecimal P53 = new BigDecimal(53).round(roundIndicators);
	public static BigDecimal P54 = new BigDecimal(54).round(roundIndicators);
	public static BigDecimal P55 = new BigDecimal(55).round(roundIndicators);
	public static BigDecimal P56 = new BigDecimal(56).round(roundIndicators);
	public static BigDecimal P57 = new BigDecimal(57).round(roundIndicators);
	public static BigDecimal P58 = new BigDecimal(58).round(roundIndicators);
	public static BigDecimal P59 = new BigDecimal(59).round(roundIndicators);
	
	
	public static BigDecimal P60 = new BigDecimal(60).round(roundIndicators);
	public static BigDecimal P61 = new BigDecimal(61).round(roundIndicators);
	public static BigDecimal P62 = new BigDecimal(62).round(roundIndicators);
	public static BigDecimal P63 = new BigDecimal(63).round(roundIndicators);
	public static BigDecimal P64 = new BigDecimal(64).round(roundIndicators);
	public static BigDecimal P65 = new BigDecimal(65).round(roundIndicators);
	public static BigDecimal P66 = new BigDecimal(66).round(roundIndicators);
	public static BigDecimal P67 = new BigDecimal(67).round(roundIndicators);
	public static BigDecimal P68 = new BigDecimal(68).round(roundIndicators);
	public static BigDecimal P69 = new BigDecimal(69).round(roundIndicators);
	
	public static BigDecimal P70 = new BigDecimal(70).round(roundIndicators);
	public static BigDecimal P71 = new BigDecimal(71).round(roundIndicators);
	public static BigDecimal P72 = new BigDecimal(72).round(roundIndicators);
	public static BigDecimal P73 = new BigDecimal(73).round(roundIndicators);
	public static BigDecimal P74 = new BigDecimal(74).round(roundIndicators);
	public static BigDecimal P75 = new BigDecimal(75).round(roundIndicators);
	public static BigDecimal P76 = new BigDecimal(76).round(roundIndicators);
	public static BigDecimal P77 = new BigDecimal(77).round(roundIndicators);
	public static BigDecimal P78 = new BigDecimal(78).round(roundIndicators);
	public static BigDecimal P79 = new BigDecimal(79).round(roundIndicators);
	
	
	public static BigDecimal P80 = new BigDecimal(80).round(roundIndicators);
	public static BigDecimal P81 = new BigDecimal(81).round(roundIndicators);
	public static BigDecimal P82 = new BigDecimal(82).round(roundIndicators);
	public static BigDecimal P83 = new BigDecimal(83).round(roundIndicators);
	public static BigDecimal P84 = new BigDecimal(84).round(roundIndicators);
	public static BigDecimal P85 = new BigDecimal(85).round(roundIndicators);
	public static BigDecimal P86 = new BigDecimal(86).round(roundIndicators);
	public static BigDecimal P87 = new BigDecimal(87).round(roundIndicators);
	public static BigDecimal P88 = new BigDecimal(88).round(roundIndicators);
	public static BigDecimal P89 = new BigDecimal(89).round(roundIndicators);
	
	
	public static BigDecimal P90 = new BigDecimal(90).round(roundIndicators);
	public static BigDecimal P91 = new BigDecimal(91).round(roundIndicators);
	public static BigDecimal P92 = new BigDecimal(92).round(roundIndicators);
	public static BigDecimal P93 = new BigDecimal(93).round(roundIndicators);
	public static BigDecimal P94 = new BigDecimal(94).round(roundIndicators);
	public static BigDecimal P95 = new BigDecimal(95).round(roundIndicators);
	public static BigDecimal P96 = new BigDecimal(96).round(roundIndicators);
	public static BigDecimal P97 = new BigDecimal(97).round(roundIndicators);
	public static BigDecimal P98 = new BigDecimal(98).round(roundIndicators);
	public static BigDecimal P99 = new BigDecimal(99).round(roundIndicators);
	
	public static BigDecimal P100 = new BigDecimal(100).round(roundIndicators);
	
	
	public static BigDecimal N01 = new BigDecimal(-1).round(roundIndicators);
	public static BigDecimal N02 = new BigDecimal(-2).round(roundIndicators);
	public static BigDecimal N03 = new BigDecimal(-3).round(roundIndicators);
	public static BigDecimal N04 = new BigDecimal(-4).round(roundIndicators);
	public static BigDecimal N05 = new BigDecimal(-5).round(roundIndicators);
	public static BigDecimal N06 = new BigDecimal(-6).round(roundIndicators);
	public static BigDecimal N07 = new BigDecimal(-7).round(roundIndicators);
	public static BigDecimal N08 = new BigDecimal(-8).round(roundIndicators);
	public static BigDecimal N09 = new BigDecimal(-9).round(roundIndicators);
	
	
	
	public static BigDecimal N10 = new BigDecimal(-10).round(roundIndicators);
	public static BigDecimal N11 = new BigDecimal(-11).round(roundIndicators);
	public static BigDecimal N12 = new BigDecimal(-12).round(roundIndicators);
	public static BigDecimal N13 = new BigDecimal(-13).round(roundIndicators);
	public static BigDecimal N14 = new BigDecimal(-14).round(roundIndicators);
	public static BigDecimal N15 = new BigDecimal(-15).round(roundIndicators);
	public static BigDecimal N16 = new BigDecimal(-16).round(roundIndicators);
	public static BigDecimal N17 = new BigDecimal(-17).round(roundIndicators);
	public static BigDecimal N18 = new BigDecimal(-18).round(roundIndicators);
	public static BigDecimal N19 = new BigDecimal(-19).round(roundIndicators);
	
	
	
	
	
	
	public static BigDecimal N20 = new BigDecimal(-20).round(roundIndicators);
	public static BigDecimal N21 = new BigDecimal(-21).round(roundIndicators);
	public static BigDecimal N22 = new BigDecimal(-22).round(roundIndicators);
	public static BigDecimal N23 = new BigDecimal(-23).round(roundIndicators);
	public static BigDecimal N24 = new BigDecimal(-24).round(roundIndicators);
	public static BigDecimal N25 = new BigDecimal(-25).round(roundIndicators);
	public static BigDecimal N26 = new BigDecimal(-26).round(roundIndicators);
	public static BigDecimal N27 = new BigDecimal(-27).round(roundIndicators);
	public static BigDecimal N28 = new BigDecimal(-28).round(roundIndicators);
	public static BigDecimal N29 = new BigDecimal(-29).round(roundIndicators);
	
	public static BigDecimal N30 = new BigDecimal(-30).round(roundIndicators);
	public static BigDecimal N31 = new BigDecimal(-31).round(roundIndicators);
	public static BigDecimal N32 = new BigDecimal(-32).round(roundIndicators);
	public static BigDecimal N33 = new BigDecimal(-33).round(roundIndicators);
	public static BigDecimal N34 = new BigDecimal(-34).round(roundIndicators);
	public static BigDecimal N35 = new BigDecimal(-35).round(roundIndicators);
	public static BigDecimal N36 = new BigDecimal(-36).round(roundIndicators);
	public static BigDecimal N37 = new BigDecimal(-37).round(roundIndicators);
	public static BigDecimal N38 = new BigDecimal(-38).round(roundIndicators);
	public static BigDecimal N39 = new BigDecimal(-39).round(roundIndicators);
	
	public static BigDecimal N40 = new BigDecimal(-40).round(roundIndicators);
	public static BigDecimal N41 = new BigDecimal(-41).round(roundIndicators);
	public static BigDecimal N42 = new BigDecimal(-42).round(roundIndicators);
	public static BigDecimal N43 = new BigDecimal(-43).round(roundIndicators);
	public static BigDecimal N44 = new BigDecimal(-44).round(roundIndicators);
	public static BigDecimal N45 = new BigDecimal(-45).round(roundIndicators);
	public static BigDecimal N46 = new BigDecimal(-46).round(roundIndicators);
	public static BigDecimal N47 = new BigDecimal(-47).round(roundIndicators);
	public static BigDecimal N48 = new BigDecimal(-48).round(roundIndicators);
	public static BigDecimal N49 = new BigDecimal(-49).round(roundIndicators);
	
	public static BigDecimal N50 = new BigDecimal(-50).round(roundIndicators);
	public static BigDecimal N51 = new BigDecimal(-51).round(roundIndicators);
	public static BigDecimal N52 = new BigDecimal(-52).round(roundIndicators);
	public static BigDecimal N53 = new BigDecimal(-53).round(roundIndicators);
	public static BigDecimal N54 = new BigDecimal(-54).round(roundIndicators);
	public static BigDecimal N55 = new BigDecimal(-55).round(roundIndicators);
	public static BigDecimal N56 = new BigDecimal(-56).round(roundIndicators);
	public static BigDecimal N57 = new BigDecimal(-57).round(roundIndicators);
	public static BigDecimal N58 = new BigDecimal(-58).round(roundIndicators);
	public static BigDecimal N59 = new BigDecimal(-59).round(roundIndicators);
	
	
	public static BigDecimal N60 = new BigDecimal(-60).round(roundIndicators);
	public static BigDecimal N61 = new BigDecimal(-61).round(roundIndicators);
	public static BigDecimal N62 = new BigDecimal(-62).round(roundIndicators);
	public static BigDecimal N63 = new BigDecimal(-63).round(roundIndicators);
	public static BigDecimal N64 = new BigDecimal(-64).round(roundIndicators);
	public static BigDecimal N65 = new BigDecimal(-65).round(roundIndicators);
	public static BigDecimal N66 = new BigDecimal(-66).round(roundIndicators);
	public static BigDecimal N67 = new BigDecimal(-67).round(roundIndicators);
	public static BigDecimal N68 = new BigDecimal(-68).round(roundIndicators);
	public static BigDecimal N69 = new BigDecimal(-69).round(roundIndicators);
	
	public static BigDecimal N70 = new BigDecimal(-70).round(roundIndicators);
	public static BigDecimal N71 = new BigDecimal(-71).round(roundIndicators);
	public static BigDecimal N72 = new BigDecimal(-72).round(roundIndicators);
	public static BigDecimal N73 = new BigDecimal(-73).round(roundIndicators);
	public static BigDecimal N74 = new BigDecimal(-74).round(roundIndicators);
	public static BigDecimal N75 = new BigDecimal(-75).round(roundIndicators);
	public static BigDecimal N76 = new BigDecimal(-76).round(roundIndicators);
	public static BigDecimal N77 = new BigDecimal(-77).round(roundIndicators);
	public static BigDecimal N78 = new BigDecimal(-78).round(roundIndicators);
	public static BigDecimal N79 = new BigDecimal(-79).round(roundIndicators);
	
	public static BigDecimal N80 = new BigDecimal(-80).round(roundIndicators);
	public static BigDecimal N81 = new BigDecimal(-81).round(roundIndicators);
	public static BigDecimal N82 = new BigDecimal(-82).round(roundIndicators);
	public static BigDecimal N83 = new BigDecimal(-83).round(roundIndicators);
	public static BigDecimal N84 = new BigDecimal(-84).round(roundIndicators);
	public static BigDecimal N85 = new BigDecimal(-85).round(roundIndicators);
	public static BigDecimal N86 = new BigDecimal(-86).round(roundIndicators);
	public static BigDecimal N87 = new BigDecimal(-87).round(roundIndicators);
	public static BigDecimal N88 = new BigDecimal(-88).round(roundIndicators);
	public static BigDecimal N89 = new BigDecimal(-89).round(roundIndicators);
	
	public static BigDecimal N90 = new BigDecimal(-90).round(roundIndicators);
	public static BigDecimal N91 = new BigDecimal(-91).round(roundIndicators);
	public static BigDecimal N92 = new BigDecimal(-92).round(roundIndicators);
	public static BigDecimal N93 = new BigDecimal(-93).round(roundIndicators);
	public static BigDecimal N94 = new BigDecimal(-94).round(roundIndicators);
	public static BigDecimal N95 = new BigDecimal(-95).round(roundIndicators);
	public static BigDecimal N96 = new BigDecimal(-96).round(roundIndicators);
	public static BigDecimal N97 = new BigDecimal(-97).round(roundIndicators);
	public static BigDecimal N98 = new BigDecimal(-98).round(roundIndicators);
	public static BigDecimal N99 = new BigDecimal(-99).round(roundIndicators);
	
	
	
	
	public static BigDecimal P20_= new BigDecimal(20).round(roundIndicators);
	public static BigDecimal P15_20= new BigDecimal(15).round(roundIndicators);
	public static BigDecimal P12_15= new BigDecimal(12).round(roundIndicators);
	public static BigDecimal P10_12= new BigDecimal(10).round(roundIndicators);
	public static BigDecimal P08_10= new BigDecimal(8).round(roundIndicators);
	public static BigDecimal P06_08= new BigDecimal(6).round(roundIndicators);
	public static BigDecimal P04_06= new BigDecimal(4).round(roundIndicators);
	public static BigDecimal P02_04= new BigDecimal(2).round(roundIndicators);
	public static BigDecimal P00_02= new BigDecimal(0).round(roundIndicators);
	
	public static BigDecimal N20_= new BigDecimal(-20).round(roundIndicators);
	public static BigDecimal N15_20= new BigDecimal(-15).round(roundIndicators);
	public static BigDecimal N12_15= new BigDecimal(-12).round(roundIndicators);
	public static BigDecimal N10_12= new BigDecimal(-10).round(roundIndicators);
	public static BigDecimal N08_10= new BigDecimal(-8).round(roundIndicators);
	public static BigDecimal N06_08= new BigDecimal(-6).round(roundIndicators);
	public static BigDecimal N04_06= new BigDecimal(-4).round(roundIndicators);
	public static BigDecimal N02_04= new BigDecimal(-2).round(roundIndicators);
	public static BigDecimal N00_02= new BigDecimal(0).round(roundIndicators);
	
	
	public static BigDecimal P60_= new BigDecimal(60).round(roundIndicators);
	public static BigDecimal P53_60= new BigDecimal(53).round(roundIndicators);
	public static BigDecimal P45_53= new BigDecimal(45).round(roundIndicators);
	public static BigDecimal P35_45= new BigDecimal(35).round(roundIndicators);
	public static BigDecimal P25_35= new BigDecimal(25).round(roundIndicators);
	public static BigDecimal P15_25= new BigDecimal(15).round(roundIndicators);
	public static BigDecimal P05_15= new BigDecimal(5).round(roundIndicators);
	public static BigDecimal P00_05= new BigDecimal(0).round(roundIndicators);
	
	public static BigDecimal N60_= new BigDecimal(-60).round(roundIndicators);
	public static BigDecimal N53_60= new BigDecimal(-53).round(roundIndicators);
	public static BigDecimal N45_53= new BigDecimal(-45).round(roundIndicators);
	public static BigDecimal N35_45= new BigDecimal(-35).round(roundIndicators);
	public static BigDecimal N25_35= new BigDecimal(-25).round(roundIndicators);
	public static BigDecimal N15_25= new BigDecimal(-15).round(roundIndicators);
	public static BigDecimal N05_15= new BigDecimal(-05).round(roundIndicators);
	public static BigDecimal N00_05= new BigDecimal(0).round(roundIndicators);
	
	
	
	
	public static BigDecimal P08_= new BigDecimal(8).round(roundIndicators);
	public static BigDecimal P04_05= new BigDecimal(4).round(roundIndicators);
	public static BigDecimal P03_04= new BigDecimal(3).round(roundIndicators);
	public static BigDecimal P02_03= new BigDecimal(2).round(roundIndicators);
	public static BigDecimal P01_02= new BigDecimal(1).round(roundIndicators);
	public static BigDecimal P00_01= new BigDecimal(0).round(roundIndicators);
	
	public static BigDecimal N08_= new BigDecimal(-8).round(roundIndicators);
	public static BigDecimal N04_05= new BigDecimal(-4).round(roundIndicators);
	public static BigDecimal N03_04= new BigDecimal(-3).round(roundIndicators);
	public static BigDecimal N02_03= new BigDecimal(-2).round(roundIndicators);
	public static BigDecimal N01_02= new BigDecimal(-1).round(roundIndicators);
	public static BigDecimal N00_01= new BigDecimal(0).round(roundIndicators);
	
	
	
	
	
	
	public static Duration DAYS_4 = Duration.ofDays(4);
	
	
	
	
	
	
	
	
	/*
	 * Budget
	 */
	
	
	public static BigDecimal INCREASE_OVERRIDE_BUDGET_TIME = new BigDecimal(0.004).round(roundIndicators);
	public static BigDecimal DECREASE_OVERRIDE_BUDGET_TIME = new BigDecimal(-0.004).round(roundIndicators);
	
	
	
	//macdc1226-1 emac12-1 emac26-1 macdc1226-1 emac12-1 emac26-1 bid-1
	
	/*
	 * Input:
	 * bid/ask
	 * stat1m, stat1m-1, stat1m-2 ... stat1m-n
	 * stat5m, stat5m-1, stat5m-2 ... stat5m-n
	 * stat15m, stat15m-1, stat15m-2 ... stat15m-n
	 * stat1h, stat1h-1, stat1h-2 ... stat1h-n
	 * stat6h, stat6h-1, stat6h-2 ... stat6h-n
	 * stat1d, stat1d-1, stat1d-2 ... stat1d-n
	 * 
	 * output:
	 * order type (limit/market)
	 * order price
	 */
	
	/*
	 * En fonction de la pente de croissance de emac12 et emac26, 
	 * du changement du macdc1226 en valeur absolue, du bid/ask, 
	 * déterminer le prix à l'achat et à la vente.
	 * 
	 * Idées
	 * #1. On peut utiliser la pente des emacs, et en fonction des degrés de pente, définir le prix d'achat/vente.
	 * Ainsi, on limite les valeurs à analyser.
	 * 
	 */
	
	/*
	 * calculates linear interpolation Y = aX + b, correlation coefficient r, variation percent v, estimated next value e by returning [a,b,r,v,e]
	 *
	 * 
	 */
	
	
	public static BigDecimal[] linearInterpolation(BigDecimal[] xis, BigDecimal[] yis, boolean debug) throws NullEntityException, DataIntegrityException {
		BigDecimal[] result = new BigDecimal[5];
		if (xis == null) {
			throw new NullEntityException("linearInterpolation: xis cannot be null");
		}
		if (yis == null) {
			throw new NullEntityException("linearInterpolation: yis cannot be null");
		}
		if(xis.length != yis.length) {
			throw new DataIntegrityException("linearInterpolation: xis and yis must be of the same size");
		}
		BigDecimal sumXis = BigDecimal.ZERO;
		BigDecimal sumYis = BigDecimal.ZERO;
		BigDecimal a = null;
		BigDecimal b = null;
		BigDecimal r = null;
		BigDecimal p = null;
		BigDecimal meanxis = BigDecimal.ZERO;
		BigDecimal meanyis = BigDecimal.ZERO;
		BigDecimal sXY = BigDecimal.ZERO;
		BigDecimal s2X = BigDecimal.ZERO;
		BigDecimal s2Y = BigDecimal.ZERO;
		BigDecimal dTemp = null;
		BigDecimal eTemp = null;
		
		for (int i=0; i<xis.length; i++ ) {
			sumXis = sumXis.add(xis[i]);
			sumYis = sumYis.add(yis[i]);
		}
		meanxis = sumXis.divide(new BigDecimal(xis.length),roundIndicators);
		meanyis = sumYis.divide(new BigDecimal(yis.length),roundIndicators);
		
		for (int i=0; i<xis.length; i++ ) {
			sXY = sXY.add((xis[i].subtract(meanxis)).multiply(yis[i].subtract(meanyis)));
			dTemp = xis[i].subtract(meanxis);
			s2X = s2X.add((dTemp).multiply(dTemp));
			eTemp = yis[i].subtract(meanyis);
			s2Y = s2Y.add((eTemp).multiply(eTemp));
		}
		
		if (debug) {
			logger.info("sumXis: " + sumXis);
			logger.info("sumYis: " + sumYis);
			logger.info("meanxis: " + meanxis);
			logger.info("meanyis: " + meanyis);
			logger.info("sXY: " + sXY);
			logger.info("s2X: " + s2X);
			logger.info("s2Y: " + s2Y);
		}
		
		
		
		a = sXY.divide(s2X,roundIndicators);
		b = meanyis.subtract(a.multiply(meanxis));
		if (!(s2Y.compareTo(BigDecimal.ZERO) == 0)) {
			r = sXY.divide(sqrt(s2X.multiply(s2Y)),roundIndicators);
		} else {
			r = BigDecimal.ZERO;
		}
		if (meanyis.compareTo(BigDecimal.ZERO) != 0) {
			p = a.divide(meanyis, roundIndicators);
			
		} else {
			if (debug) {
				logger.error("linearInterpolation: p=0 ");
				for (int i=0; i<yis.length; i++) {
					logger.error("linearInterpolation: p=0 yis["+i+"]=" + yis[i]);
				}
			}
			
			p = BigDecimal.ZERO;
		}
		
		result[0] = a;
		result[1] = b;
		result[2] = r;
		result[3] = p;
		result[4] = new BigDecimal(((double)xis.length + 1) * result[0].doubleValue() + result[1].doubleValue()).round(roundIndicators);
		
		return result;
	}
	
	public static void main(String[] args) throws NullEntityException, DataIntegrityException {
		BigDecimal[] xis = new BigDecimal[] {
				BigDecimal.valueOf(1),
				BigDecimal.valueOf(2),
				BigDecimal.valueOf(3),
				BigDecimal.valueOf(4)};
		BigDecimal[] yis = new BigDecimal[] {
				BigDecimal.valueOf(12),
				BigDecimal.valueOf(9),
				BigDecimal.valueOf(4),
				BigDecimal.valueOf(-7)};
		
		
		BigDecimal[] result = linearInterpolation(xis, yis, true);
		System.out.println("a = " + result[0]);
		System.out.println("b = " + result[1]);
		System.out.println("r = " + result[2]);
		System.out.println("p = " + result[3]);
	}

	
	public static BigDecimal sqrt(BigDecimal value) {
	    BigDecimal x = new BigDecimal(Math.sqrt(value.doubleValue()));
	    return x.add(new BigDecimal(value.subtract(x.multiply(x)).doubleValue() / (x.doubleValue() * 2.0)));
	}
	
	public static BigDecimal smaY(List<BigDecimal> data) {

		BigDecimal result = BigDecimal.ZERO;
		if (data == null) return result;
		BigDecimal lastNotNull = null;
		int s = 0;
		for (BigDecimal current: data) {
			if (current != null) {
				lastNotNull = current;
				result = result.add(current);
				//logger.info("smaY: adding current: " + current.doubleValue());
				s++;
			} else {
				if (lastNotNull != null) {
					result = result.add(lastNotNull);
					//logger.info("smaY: adding lastNotNull: " + lastNotNull.doubleValue());
					s++;
				} else {
					//logger.info("smaY: all null");
				}
			}
		}
		if (s==0) {
			//logger.info("smaY: s=0");
			return result;
		}
		//logger.info("smaY: result before divide: " + result);
		
		return result.divide(new BigDecimal(s), roundIndicators);

	}

	public static BigDecimal emaXY(BigDecimal xData, BigDecimal yPeriod, BigDecimal previousDayEma) {
	
		/*
		 * Initial SMA: 10-period sum / 10 
			Multiplier: (2 / (Time periods + 1) ) = (2 / (10 + 1) ) = 0.1818 (18.18%)
			EMA: {Close - EMA(previous day)} x multiplier + EMA(previous day). 
		 */
		
		BigDecimal multiplier = new BigDecimal(2).divide(yPeriod.add(BigDecimal.ONE), roundIndicators);
		/*BigDecimal ema = xData.subtract(previousDayEma);
		ema = ema.multiply(multiplier);
		ema = ema.add(previousDayEma);
		return ema.round(roundIndicators);*/
		
		return xData.multiply(multiplier).add(previousDayEma.multiply(BigDecimal.ONE.subtract(multiplier))).round(roundIndicators);
		
	
	}
	
	public static BigDecimal cmoY(List<BigDecimal> data) {
		/*
		 * 14 Period CMO: 
		 * 100 * (C - C14) / (
		 * ABS(C - C1) + ABS(C1 - C2) + ABS(C2 - C3) 
		 * + ABS(C3 - C4) + ABS(C4 - C5) + ABS(C5 - C6) 
		 * + ABS(C6 - C7) + ABS(C7-C8) + ABS(C8 - C9) 
		 * + ABS(C9 - C10) + ABS(C10 - C11) + ABS(C11 - C12) 
		 * + ABS(C12 - C13) + ABS(C13 - C14)
		 * )
		 */
		
		BigDecimal result = BigDecimal.ZERO;
		if (data == null) return result;
		BigDecimal newest = data.get(data.size()-1);
		BigDecimal oldest = data.get(0);
		BigDecimal accum = BigDecimal.ZERO;
		
		for (int i=data.size()-1; i>0; i--) {
			BigDecimal current = data.get(i);
			if ((newest == null) && (current != null)) {
				newest = current;
			}
			if (current != null) {
				oldest = current;
			}
			BigDecimal current_1 = data.get(i-1);
			BigDecimal sum = BigDecimal.ZERO;
			if ( (current_1!=null) && (current!=null)) {
				sum = current.subtract(current_1);
				sum = sum.abs();
			}
			accum = accum.add(sum);
			
			
		}
		BigDecimal uche = null;
		if ((oldest != null) && (newest != null)) {
			uche = newest.subtract(oldest);
		} else {
			uche = BigDecimal.ZERO;
		}
		result = new BigDecimal(100).multiply(uche);
		if (! (accum.compareTo(BigDecimal.ZERO) == 0)) {
			result = result.divide(accum, roundIndicators);

		} else {
			result = BigDecimal.ZERO;
		}
		return result;
		
		
	}
	
	
	
	/*
	 * 
	 * Pattern #1
	 * macdc1226 increase
	 * emac12 increase, emac26 increase,
	 * 
	 * 
	 * 
	 * guess next next high by using the following indicators:
	 * 
	 * - last macdc1226 Sign Change Ts
	 * - last macdc1226 Sign Change Price
	 * - last 10 macdc1226h and macdc1226l
	 */
	
	public static BigDecimal guessNext_Pattern1() {
		
		
		return null;
	}
	
	
	public static BigDecimal sma(List<BigDecimal> values) {
		
		if (values == null) return null;
		if (values.size() == 0) return null;
		
		BigDecimal accum = BigDecimal.ZERO;
		for ( BigDecimal current : values) {
			accum = accum.add(current);
		}
		return accum.divide(new BigDecimal(values.size()), Indicators.roundIndicators);
		
		
	}
	
	public static BigDecimal ema(BigDecimal previousEma, BigDecimal value, BigDecimal smoothing, BigDecimal size) throws NullEntityException {
		
		if (value == null) throw new NullEntityException("ema: value cannot be null");
		if (previousEma == null) throw new NullEntityException("ema: previousEma cannot be null");
		if ( (smoothing == null) || (smoothing.compareTo(Indicators.TWO) < 0)) {
			throw new NullEntityException("ema: smoothing cannot be null and must be greater or equal to 2");
		}
		if ( (size == null) || (size.compareTo(Indicators.TWO) < 0)) {
			throw new NullEntityException("ema: size cannot be null and must be greater or equal to 2");
		}
		
		
		BigDecimal coefficient = smoothing.divide(BigDecimal.ONE.add(size), Indicators.roundIndicators);
		BigDecimal left = coefficient.multiply(value, Indicators.roundIndicators);
		BigDecimal right = previousEma.multiply((BigDecimal.ONE.subtract(coefficient)), Indicators.roundIndicators);
		
		BigDecimal ema = left.add(right);
		
		return ema;
		
	}
	
	public static void setWt(StatVO stat, StatVO previous, StatVO previous2, StatVO previous3, BigDecimal waveTrendN1, BigDecimal waveTrendN2, BigDecimal overBoughtTh, BigDecimal overSoldTh) {
		/*
		 * WaveTrend
		 
		private BigDecimal wtEsa; // esa = ema(ap, n1)
		private BigDecimal wtD; // d = ema(abs(ap - esa), n1)
		private BigDecimal wtCi; // ci = (ap - esa) / (0.015 * d)
		private BigDecimal wt1; // wt1 = tci = ema(ci, n2)
		private BigDecimal wt2; // sma(wt1,4)
		*/
		
		
		/*
		 * Calc esa = ema(ap, n1)
		 */
		if (previous == null) {
			stat.setWtEsa(stat.getHlc3());
			
		} else if (previous.getWtEsa() == null) {
			stat.setWtEsa(stat.getHlc3());
		} else {
			try {
				stat.setWtEsa(Indicators.ema(previous.getWtEsa(), stat.getHlc3(), Indicators.TWO, waveTrendN1));
			} catch (Exception e) {
				stat.setWtEsa(stat.getHlc3());
			}
		}
		
		/*
		 * d = ema(abs(ap - esa), n1)
		 */
		
		//logger.warn(stat.getStatGranularity() + "" + stat.getTs() + " " + stat.getHlc3() + " " + stat.getWtEsa());
		
		if (stat.getWtEsa() != null) {
			BigDecimal absApEsa = stat.getHlc3().subtract(stat.getWtEsa()).abs();
			if (previous == null) {
				stat.setWtD(absApEsa);
				
			} else if (previous.getWtD() == null) {
				stat.setWtD(absApEsa);
			} else {
				try {
					stat.setWtD(Indicators.ema(previous.getWtD(), absApEsa, Indicators.TWO, waveTrendN1));
				} catch (Exception e) {
					stat.setWtD(absApEsa);
				}
			}
		}
		
		
		
		/*
		 * ci = (ap - esa) / (0.015 * d)
		 */
		if ((stat.getWtD() != null) && (!(stat.getWtD().compareTo(BigDecimal.ZERO)==0))) {
			BigDecimal ci = 
					(stat.getHlc3().subtract(stat.getWtEsa()))
					.divide(
							new BigDecimal(0.015).multiply(stat.getWtD(), Indicators.roundIndicators)
							, Indicators.roundIndicators);
			
			stat.setWtCi(ci);
			
		} else {
			stat.setWtCi(BigDecimal.ZERO);
		}
		
		/*
		 * // wt1 = tci = ema(ci, n2)
		 */
		
		if (previous == null) {
			stat.setWt1(stat.getWtCi());
			
		} else if (previous.getWt1() == null) {
			stat.setWt1(stat.getWtCi());
		} else {
			try {
				stat.setWt1(Indicators.ema(previous.getWt1(), stat.getWtCi(), Indicators.TWO, waveTrendN2));
			} catch (Exception e) {
				stat.setWt1(stat.getWtCi());
			}
		}
		
		/*
		 * private BigDecimal wt2; // sma(wt1,4)
		 */
		
				
				
		
		BigDecimal wt2 = stat.getWt1();
		int i = 1;
		if (wt2 != null) {
			
			if (previous != null) {
				if (previous.getWt1() != null) {
					wt2 = wt2.add(previous.getWt1());
					i++;
				}
			}
			if (previous2 != null) {
				if (previous2.getWt1() != null) {
					wt2 = wt2.add(previous2.getWt1());
					i++;
				}
			}
			if (previous3 != null) {
				if (previous3.getWt1() != null) {
					wt2 = wt2.add(previous3.getWt1());
					i++;
				}
			}
			if (i>1) {
				
				
				
				//wt2 = wt2.divide(new BigDecimal(i), Indicators.roundIndicators);
				wt2 = wt2.divide(Indicators.FOUR, Indicators.roundIndicators);
			}
			stat.setWt2(wt2);
			stat.setWt(stat.getWt1().subtract(wt2));
			if ((previous != null) && (previous.getWt() != null)) {
				stat.setDiff(stat.getWt().subtract(previous.getWt()));
			}
		}
		
		if (stat.getWt1() != null) {
			if (stat.getWt1().compareTo(overSoldTh)<=0) {
				stat.setOver(false);
			} else if (stat.getWt1().compareTo(overBoughtTh)>=0) {
				stat.setOver(true);
			}
		}
	}
	
	
	public static Boolean isDivergent(BigDecimal wt, BigDecimal highestWt, BigDecimal wt1, BigDecimal diff) {
		boolean found = false;
		boolean wtSignNum = wt.signum()>=0;
		
	
		
		if (wt.signum()<0) {
			wt = wt.negate();
			highestWt = highestWt.negate();
			wt1 = wt1.negate();
			diff = diff.negate();
		}
		
	
		
		BigDecimal percentUndone = wt.divide(highestWt, Indicators.roundTwoIndicators);
		
		
	
		if (found) {
			return wtSignNum;
		}
		return null;
	}
	
	public static Pair<SgMove, Boolean> getSgMove(BigDecimal wt, BigDecimal highestWt, BigDecimal wt1, BigDecimal diff, Instant now) {
		
		BigDecimal wtVsHighestRatio = null;
		Boolean divergence = isDivergent(wt, highestWt, wt1, diff);
		
		if (highestWt.signum()==0) {
			
			wtVsHighestRatio = BigDecimal.ONE;
			
		} else if ((highestWt.compareTo(Indicators.THREE)<=0) && (highestWt.signum()>=0)) {
			
			if (diff.compareTo(Indicators.SIX)>=0) {
				return Pair.create(SgMove.AP, divergence);
			} else if (diff.compareTo(Indicators.THREE)>=0) {
				return Pair.create(SgMove.BP, divergence); 
			} else if (diff.signum()>=0) {
				return Pair.create(SgMove.SP, divergence); 
			} else if (diff.compareTo(Indicators.MINUS_SIX)<=0) {
				return Pair.create(SgMove.XP, divergence); 
			} else if (diff.compareTo(Indicators.MINUS_THREE)<=0) {
				return Pair.create(SgMove.XP, divergence); 
			} else {
				return Pair.create(SgMove.SP, divergence); 
			}
		
	} else if ((highestWt.compareTo(Indicators.MINUS_THREE)>0) && (highestWt.signum()<0))  {
		
		if (diff.compareTo(Indicators.SIX)>=0) {
			return Pair.create(SgMove.XN, divergence);
		} else if (diff.compareTo(Indicators.THREE)>=0) {
			return Pair.create(SgMove.XN, divergence); 
		} else if (diff.signum()>=0) {
			return Pair.create(SgMove.SN, divergence); 
		} else if (diff.compareTo(Indicators.MINUS_SIX)<=0) {
			return Pair.create(SgMove.AN, divergence); 
		} else if (diff.compareTo(Indicators.MINUS_THREE)<=0) {
			return Pair.create(SgMove.BN, divergence); 
		} else {
			return Pair.create(SgMove.SN, divergence); 
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
			//System.out.println("here " +wtVsHighestRatio + " "  + new BigDecimal(1d -  Math.log10(new Double((wt1.doubleValue()+10)) /12d)) + " " + new BigDecimal((1d - Math.log10((wt1.doubleValue()+10d))/7.8d)));
			
			if (wt1.compareTo(Indicators.FIFTY)>=0) {
				if (wtVsHighestRatio.compareTo(Indicators.PERCENT_10)<=0) {
					if (diff.compareTo(Indicators.FOUR)>=0) {
						return Pair.create(SgMove.BP, divergence);
					} else if (diff.compareTo(Indicators.TWO)>=0) {
						return Pair.create(SgMove.RP, divergence);
					}
					return Pair.create(SgMove.XP, divergence);
				}
				
			} else if (wt1.compareTo(Indicators.EIGHT)>=0) {
				
				if (wtVsHighestRatio.compareTo(new BigDecimal(1d -  Math.log10(new Double((wt1.doubleValue()+10)) /7.8d)))<=0) {
					if (diff.compareTo(Indicators.FOUR)>=0) {
						return Pair.create(SgMove.BP, divergence);
					} else if (diff.compareTo(Indicators.TWO)>=0) {
						return Pair.create(SgMove.RP, divergence);
					}
					return Pair.create(SgMove.XP, divergence);
				}
			} else if (wt1.compareTo(Indicators.MINUS_TEN)>=0) {
				
				if (diff.signum()>=0) {
					return Pair.create(SgMove.BP, divergence);
				} else if (diff.compareTo(Indicators.MINUS_TWO)>=0) {
					return Pair.create(SgMove.RP, divergence);
				}
				return Pair.create(SgMove.XP, divergence);
			

			} else if (wt1.compareTo(Indicators.MINUS_THIRTY)>=0) {
				
				if (diff.compareTo(Indicators.TWO)>=0) {
					return Pair.create(SgMove.AP, divergence);
				} else if (diff.compareTo(Indicators.ONE)>=0)  {
					return Pair.create(SgMove.BP, divergence);
				} else if (diff.compareTo(BigDecimal.ZERO)>=0) {
					return Pair.create(SgMove.RP, divergence);
				}
				return Pair.create(SgMove.XP, divergence);
			

			} else {
				
				if (diff.compareTo(Indicators.THREE)>=0) {
					return Pair.create(SgMove.AP, divergence);
				} else if (diff.compareTo(Indicators.ONE)>=0)  {
					return Pair.create(SgMove.BP, divergence);
				} else if (diff.compareTo(BigDecimal.ZERO)>=0) {
					return Pair.create(SgMove.RP, divergence);
				}
				return Pair.create(SgMove.XP, divergence);
			
				
			}
			
			// RP
			
			if (wt1.compareTo(Indicators.EIGHTY_FIVE)>=0) {
				
				if (wtVsHighestRatio.compareTo(Indicators.PERCENT_10)<=0) {
					if (diff.compareTo(Indicators.FOUR)>=0) {
						
						return Pair.create(SgMove.BP, divergence);
					}
					return Pair.create(SgMove.RP, divergence);
				}
			} else if (wt1.compareTo(Indicators.FIVE)>=0) {
				
				
				if (wtVsHighestRatio.compareTo(new BigDecimal(1d -  Math.log10(new Double((wt1.doubleValue()+10)) /12d)))<=0) {
					
					if (diff.compareTo(Indicators.FOUR)>=0) {
						
						if (wt1.compareTo(Indicators.FIFTY)>=0) {
							divergence = wt.signum()>=0;

						}
						
						return Pair.create(SgMove.BP, divergence);
					}
					return Pair.create(SgMove.RP, divergence);
				}
			} /*else {
				
				if (wtVsHighestRatio.compareTo(Indicators.PERCENT_75)<=0) {
					if (diff.compareTo(Indicators.FOUR)>=0) {
						return Pair.create(SgMove.BP, divergence);
					}
					return Pair.create(SgMove.RP, divergence);
				}
			}*/
			if (wtVsHighestRatio.equals(BigDecimal.ONE)) {
				return Pair.create(SgMove.AP, divergence);
			}
			return Pair.create(SgMove.BP, divergence);
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
						divergence = wt.signum()>=0;
						return Pair.create(SgMove.BN, divergence);
					} else if (diff.compareTo(Indicators.TWO)>=0) {
						return Pair.create(SgMove.RN, divergence);
					}
					return Pair.create(SgMove.XN, divergence);
				}
				
			} else if (wt1.compareTo(Indicators.EIGHT)>=0) {
				
				if (wtVsHighestRatio.compareTo(new BigDecimal(1d -  Math.log10(new Double((wt1.doubleValue()+10)) /7.8d)))<=0) {
						if (diff.compareTo(Indicators.FOUR)>=0) {
						return Pair.create(SgMove.BN, divergence);
					} else if (diff.compareTo(Indicators.TWO)>=0) {
						return Pair.create(SgMove.RN, divergence);
					}
					return Pair.create(SgMove.XN, divergence);
				}
			} else if (wt1.compareTo(Indicators.MINUS_TEN)>=0) {
				
				if (diff.signum()>=0) {
					return Pair.create(SgMove.BN, divergence);
				} else if (diff.compareTo(Indicators.MINUS_TWO)>=0) {
					return Pair.create(SgMove.RN, divergence);
				}
				return Pair.create(SgMove.XN, divergence);
			

			} else if (wt1.compareTo(Indicators.MINUS_THIRTY)>=0) {
				
				if (diff.compareTo(Indicators.TWO)>=0) {
					return Pair.create(SgMove.AN, divergence);
				} else if (diff.compareTo(Indicators.ONE)>=0)  {
					return Pair.create(SgMove.BN, divergence);
				} else if (diff.compareTo(BigDecimal.ZERO)>=0) {
					return Pair.create(SgMove.RN, divergence);
				}
				return Pair.create(SgMove.XN, divergence);
			

			} else {
				
				if (diff.compareTo(Indicators.THREE)>=0) {
					return Pair.create(SgMove.AN, divergence);
				} else if (diff.compareTo(Indicators.ONE)>=0)  {
					return Pair.create(SgMove.BN, divergence);
				} else if (diff.compareTo(BigDecimal.ZERO)>=0) {
					return Pair.create(SgMove.RN, divergence);
				}
				return Pair.create(SgMove.XN, divergence);
			
				
			}
			
			// RN
			
			if (wt1.compareTo(Indicators.EIGHTY_FIVE)>=0) {
				
				if (wtVsHighestRatio.compareTo(Indicators.PERCENT_10)<=0) {
					if (diff.compareTo(Indicators.FOUR)>=0) {
						return Pair.create(SgMove.BN, divergence);
					} 
					
					return Pair.create(SgMove.RN, divergence);
					
				}
			} else if (wt1.compareTo(Indicators.FIVE)>=0) {
				
				if (wtVsHighestRatio.compareTo(new BigDecimal(1d -  Math.log10(new Double((wt1.doubleValue()+10)) /12d)))<=0) {
								if (diff.compareTo(Indicators.FOUR)>=0) {
						
						if (wt1.compareTo(Indicators.FIFTY)>=0) {
							divergence = wt.signum()>=0;

						}
						
						return Pair.create(SgMove.BN, divergence);
					} 
					return Pair.create(SgMove.RN, divergence);
				}
			} /*else {
				
				if (wtVsHighestRatio.compareTo(Indicators.PERCENT_75)<=0) {
					if (diff.compareTo(Indicators.FOUR)>=0) {
						return Pair.create(SgMove.BN, divergence);
					} 
					return Pair.create(SgMove.RN, divergence);
				}
			}*/
			if (wtVsHighestRatio.equals(BigDecimal.ONE)) {
				return Pair.create(SgMove.AN, divergence);
			}
			return Pair.create(SgMove.BN, divergence); 
		}
		
	}
	
	public static Pair<SgMove, Boolean> getSgMoveOld2025(BigDecimal wt, BigDecimal highestWt, BigDecimal wt1, BigDecimal diff, Instant now) {
		
		SgMove mv = null;
		Wt1Matrix m = Wt1Matrix.get(wt1);
		boolean divergence = false;
		BigDecimal wtVsHighestRatio = null;
		boolean small = false;
		if (wt.signum()>=0) {
			
			if ((highestWt.signum()!=0) && (highestWt.abs().compareTo(Indicators.THREE)>=0)) {
				wtVsHighestRatio = wt.divide(highestWt, Indicators.roundIndicators);
			} else {
				wtVsHighestRatio = BigDecimal.ZERO;
			}
			
			
			if (highestWt.compareTo(m.getpWtTh())<=0) {
				small = true;
			}
			boolean aboveXpPercent = (wtVsHighestRatio.compareTo(m.getXpPercentStart())>0); // RP
			boolean aboveRpPercent = (wtVsHighestRatio.compareTo(m.getRpPercentStart())>0); // BP
			
			
			/*
			 * Divergence
			 */
			
			//8/10 = 0.8
				
			
			if (wt1.compareTo(Indicators.SIXTY)>=0) {
				
				
				if (wt.add(diff).compareTo(Indicators.TWO)>=0) {
					divergence = true;
				}
				
				
				
			}
			/*else if (wt1.compareTo(Indicators.FIFTY_FIVE)>=0) {
				
				
				if (wt.add(diff).compareTo(Indicators.FOUR)>=0) {
					divergence = true;
				}
				
				
				
			} else if (wt1.compareTo(Indicators.FIFTY_THREE)>=0) {
				
				
				if (wt.add(diff).compareTo(Indicators.FIVE)>=0) {
					divergence = true;
				}
				
				
				
			} else if (wt1.compareTo(Indicators.FIFTY)>=0) {
				
				
				if (wt.add(diff).compareTo(Indicators.SIX)>=0) {
					divergence = true;
				}
				
				
				
			} else if (wt1.compareTo(Indicators.FOURTY_FIVE)>=0) {
				
				
				if (wt.add(diff).compareTo(Indicators.EIGHT)>=0) {
					divergence = true;
				}
				
				
				
			} */
			
			
			/*if ((wt1.compareTo(Indicators.THIRTY)>=0) && (wt1.compareTo(Indicators.FIFTY_THREE)<=0)) {
				BigDecimal percentLeft = BigDecimal.ZERO;
				if (highestWt.signum() != 0) {
					percentLeft = wt.divide(highestWt, Indicators.roundIndicators);
				}
				
				if (diff.signum()>=0) {
					percentLeft = percentLeft.add(diff.divide(Indicators.TEN, Indicators.roundIndicators));
					
					//diff = 10, 0.8 + (-10/10) = -0.2
					
					
					if (percentLeft.compareTo(m.getRpPercentStart())>0) {
						divergence = true;
					}
				}
				
			}*/
			
			
			
			if (wtVsHighestRatio.compareTo(Indicators.ONE)>=0) {
				
				if (small && diff.compareTo(m.getBpDiffThreshold()) <0) {
					mv = SgMove.SP;
				} else {
					mv = SgMove.AP;
				}
				
				
			} else if (aboveRpPercent) {
				if (small && diff.compareTo(m.getBpDiffThreshold())<0) {
					mv = SgMove.SP;
				} else {
					mv = SgMove.BP;
				}
				
			} else if (aboveXpPercent) {
				
				if (small) {
					if (diff.compareTo(m.getBpDiffThreshold())<0) {
						mv = SgMove.SP;
					} else {
						mv = SgMove.RP;
					}
					
				} else {
					if (diff.compareTo(m.getBpDiffThreshold())<0) {
						mv = SgMove.RP;
					} else {
						mv = SgMove.RP;
					}
					//mv = SgMove.RP;
				}
				
			} else if (wtVsHighestRatio.signum()>0) {
				if (small) {
					if (diff.compareTo(m.getBpDiffThreshold())<0) {
						mv = SgMove.SP;
					} else {
						mv = SgMove.XP;
					}
				} else {
					if (diff.compareTo(m.getBpDiffThreshold())<0) {
						mv = SgMove.XP;
					} else {
						mv = SgMove.XP;
					}
					//mv = SgMove.XP;
				}
				
				
			} else if (wtVsHighestRatio.signum()==0) {
				if (small && diff.compareTo(m.getApDiffThreshold()) <0) {
					mv = SgMove.SP;
				} else {
					if (diff.compareTo(m.getBpDiffThreshold())<0) {
						mv = SgMove.XP;
					} else {
						mv = SgMove.XP;
					}
					//mv = SgMove.XP;
				}
				
				
			} else {
				if (small) {
					mv = SgMove.SP;
				} else {
					mv = SgMove.YP;
				}
			}
			
			
			
			/*
			if ((wt.compareTo(Indicators.TWO)<=0) 
					&& (diff.compareTo(Indicators.TWO)<=0) 
					&& (diff.compareTo(Indicators.MINUS_TWO)>=0)
					&& (highestWt.compareTo(Indicators.TWO) <=0)
					) {
				mv = SgMove.SP;
			} else if (wtVsHighestRatio.compareTo(Indicators.ONE)>=0) {
				mv = SgMove.AP;
			} else if (aboveRpPercent) {
				mv = SgMove.BP;
			} else if (aboveXpPercent) {
				if (diff.compareTo(Indicators.TWO) < 0) {
					mv = SgMove.RP;
				} else {
					mv = SgMove.BP;
				}
				
			} else {
				if (diff.compareTo(Indicators.TWO) < 0) {
					mv = SgMove.XP;
				} else {
					mv = SgMove.RP;
				}
			}*/
			
			
		} else {
			
			if (highestWt.signum()!=0) {
				wtVsHighestRatio = wt.divide(highestWt, Indicators.roundIndicators).negate();
			} else {
				wtVsHighestRatio = BigDecimal.ONE;
			}
			
				
			if (highestWt.compareTo(m.getnWtTh())>=0) {
				small = true;
			}
			boolean belowXnPercent = (wtVsHighestRatio.compareTo(m.getXnPercentStart().negate())<0);
			boolean belowRnPercent = (wtVsHighestRatio.compareTo(m.getRnPercentStart().negate())<0);
			

			/*
			 * Divergence
			 */
			
			//-8/-10 = 0.8
			
			
			if (wt1.compareTo(Indicators.MINUS_SIXTY)<=0) {
				
				if (wt.add(diff).compareTo(Indicators.MINUS_TWO)<=0) {
					divergence = true;
				} 
				
				
				
				
				
				
			} /*else if (wt1.compareTo(Indicators.MINUS_FIFTY_FIVE)<=0) {
				
				
				if (wt.add(diff).compareTo(Indicators.MINUS_FOUR)<=0) {
					divergence = true;
				}
				
				
				
			} else if (wt1.compareTo(Indicators.MINUS_FIFTY_THREE)<=0) {
				
				
				if (wt.add(diff).compareTo(Indicators.MINUS_FIVE)<=0) {
					divergence = true;
				}
				
				
				
			} else if (wt1.compareTo(Indicators.MINUS_FIFTY)<=0) {
				
				
				if (wt.add(diff).compareTo(Indicators.MINUS_SIX)<=0) {
					divergence = true;
				}
				
				
				
			} else if (wt1.compareTo(Indicators.MINUS_FOURTY_FIVE)<=0) {
				
				
				if (wt.add(diff).compareTo(Indicators.MINUS_EIGHT)<=0) {
					divergence = true;
				}
				
				
				
			} 
*/

/*
			if ((wt1.compareTo(Indicators.MINUS_THIRTY)<=0) && (wt1.compareTo(Indicators.MINUS_FIFTY_THREE)>=0)) {
				
				BigDecimal percentLeft = BigDecimal.ZERO;
				if (highestWt.signum() != 0) {
					percentLeft = wt.divide(highestWt, Indicators.roundIndicators);
				}
				
				if (diff.signum()<=0) {
					percentLeft = percentLeft.subtract(diff.divide(Indicators.TEN, Indicators.roundIndicators));
					
					//diff = 10, 0.8 - (10/10) = -0.2
					
					if (percentLeft.compareTo(m.getRnPercentStart())>=0) {
						divergence = true;
					}
				}
				
				
				
			}*/
			if (wtVsHighestRatio.compareTo(Indicators.MINUS_ONE)<=0) {
				if (small && diff.compareTo(m.getBnDiffThreshold())>0) {
					mv = SgMove.SN;
				} else {
					mv = SgMove.AN;
				}
				
			} else if (belowRnPercent) {
				if (small && diff.compareTo(m.getBnDiffThreshold())>0) {
					mv = SgMove.SN;
				} else {
					mv = SgMove.BN;
				}
			} else if (belowXnPercent) {
				if (small) {
					if (diff.compareTo(m.getBnDiffThreshold())>0) {
						mv = SgMove.SN;
					} else {
						mv = SgMove.RN;
					}
					
				} else {
					
					if (diff.compareTo(m.getBnDiffThreshold())>0) {
						mv = SgMove.RN;
					} else {
						mv = SgMove.RN;
					}
					
					
				}
				
			} else if (wtVsHighestRatio.signum()<0) {
				if (small) {
					if (diff.compareTo(m.getBnDiffThreshold())>0) {
						mv = SgMove.SN;
					} else {
						mv = SgMove.XN;
					}
					
				} else {
					if (diff.compareTo(m.getBnDiffThreshold())>0) {
						mv = SgMove.XN;
					} else {
						mv = SgMove.XN;
					}
				}
			} else if (wtVsHighestRatio.signum()==0) {
				if (small && diff.compareTo(m.getAnDiffThreshold()) >0) {
					mv = SgMove.SN;
					
				} else {
					if (diff.compareTo(m.getBnDiffThreshold())>0) {
						mv = SgMove.XN;
					} else {
						mv = SgMove.XN;
					}
				}
			} else {
				if (small) {
					mv = SgMove.SN;
				} else {
					mv = SgMove.YN;
				}
			}
			/*
			if ((wt.compareTo(Indicators.MINUS_TWO)>=0) 
					&& (diff.compareTo(Indicators.TWO)<=0) 
					&& (diff.compareTo(Indicators.MINUS_TWO)>=0)
					&& (highestWt.compareTo(Indicators.MINUS_TWO) >=0)
					) {
				mv = SgMove.SN;
			} else if (wtVsHighestRatio.compareTo(Indicators.ONE)>=0) {
				mv = SgMove.AN;
			} else if (belowRnPercent) {
				mv = SgMove.BN;
			} else if (belowXnPercent) {
				if (diff.compareTo(Indicators.MINUS_TWO) > 0) {
					mv = SgMove.RN;
				} else {
					mv = SgMove.BN;
				}
				
			} else {
				if (diff.compareTo(Indicators.MINUS_TWO) > 0) {
					mv = SgMove.XN;
				} else {
					mv = SgMove.RN;
				}
			}*/
			
		}
		
		
		return Pair.create(mv, divergence);
		
	}

	
	
	
	public static Pair<SgMove, Boolean> getSgMoveOld(BigDecimal wt, BigDecimal highestWt, BigDecimal wt1, BigDecimal diff, Instant now) {
		
		SgMove mv = null;
		Wt1Matrix m = Wt1Matrix.get(wt1);
		boolean divergence = false;
		BigDecimal wtVsHighestRatio = null;
		boolean small = false;
		if (wt.signum()>=0) {
			
			if (highestWt.compareTo(m.getpWtTh())>=0) {
				wtVsHighestRatio = wt.divide(highestWt, Indicators.roundIndicators);
				
				//wtVsHighestRatio = wtVsHighestRatio.add(diff.divide(Indicators.TEN, Indicators.roundIndicators));
			} else {
				wtVsHighestRatio = wt1.divide(Indicators.HUNDRED, Indicators.roundIndicators);
				wtVsHighestRatio = wtVsHighestRatio.add(diff.divide(Indicators.TEN, Indicators.roundIndicators));
				small = true;
			}

			
			boolean aboveXpPercent = (wtVsHighestRatio.compareTo(m.getXpPercentStart())>0);
			boolean aboveRpPercent = (wtVsHighestRatio.compareTo(m.getRpPercentStart())>0);
			
			
			/*
			 * Divergence
			 */
			
			//8/10 = 0.8
				
			
			if (wt1.compareTo(Indicators.THIRTY)>=0) {
				BigDecimal percentLeft = BigDecimal.ZERO;
				if (highestWt.signum() != 0) {
					percentLeft = wt.divide(highestWt, Indicators.roundIndicators);
				}
				
				percentLeft = percentLeft.add(diff.divide(Indicators.TEN, Indicators.roundIndicators));
				
				//diff = 10, 0.8 + (-10/10) = -0.2
				
				
				if (percentLeft.compareTo(m.getRpPercentStart())>0) {
					divergence = true;
				}
			}
			
			
			
			if (wtVsHighestRatio.compareTo(Indicators.ONE)>=0) {
				
				if (small && diff.compareTo(m.getBpDiffThreshold()) <0) {
					mv = SgMove.SP;
				} else {
					mv = SgMove.AP;
				}
				
				
			} else if (aboveRpPercent) {
				if (small && diff.compareTo(m.getBpDiffThreshold())<0) {
					mv = SgMove.SP;
				} else {
					mv = SgMove.BP;
				}
				
			} else if (aboveXpPercent) {
				
				if (small) {
					if (diff.compareTo(m.getBpDiffThreshold())<0) {
						mv = SgMove.SP;
					} else {
						mv = SgMove.BP;
					}
					
				} else {
					if (diff.compareTo(m.getBpDiffThreshold())<0) {
						mv = SgMove.RP;
					} else {
						mv = SgMove.BP;
					}
					//mv = SgMove.RP;
				}
			} else if (wtVsHighestRatio.signum()>=0) {
				if (small) {
					if (diff.compareTo(m.getBpDiffThreshold())<0) {
						mv = SgMove.SP;
					} else {
						mv = SgMove.BP;
					}
				} else {
					if (diff.compareTo(m.getBpDiffThreshold())<0) {
						mv = SgMove.XP;
					} else {
						mv = SgMove.BP;
					}
					//mv = SgMove.XP;
				}
			} else {
				if (small) {
					mv = SgMove.SP;
				} else {
					mv = SgMove.YP;
				}
			}
			
		} else {
			
			if (highestWt.compareTo(m.getnWtTh())<=0) {
				wtVsHighestRatio = wt.divide(highestWt, Indicators.roundIndicators).negate();
				//wtVsHighestRatio = wtVsHighestRatio.add(diff.divide(Indicators.TEN, Indicators.roundIndicators));
				
			} else {
				wtVsHighestRatio = wt1.divide(Indicators.HUNDRED, Indicators.roundIndicators);
				wtVsHighestRatio = wtVsHighestRatio.add(diff.divide(Indicators.TEN, Indicators.roundIndicators));
				small = true;
			}
			boolean belowXnPercent = (wtVsHighestRatio.compareTo(m.getXnPercentStart().negate())<0);
			boolean belowRnPercent = (wtVsHighestRatio.compareTo(m.getRnPercentStart().negate())<0);
			
			/*
			 * Divergence
			 */
			
			//-8/-10 = 0.8
			if (wt1.compareTo(Indicators.MINUS_THIRTY)<=0) {
				
				BigDecimal percentLeft = BigDecimal.ZERO;
				if (highestWt.signum() != 0) {
					percentLeft = wt.divide(highestWt, Indicators.roundIndicators);
				}
				
				
				percentLeft = percentLeft.subtract(diff.divide(Indicators.TEN, Indicators.roundIndicators));
				
				//diff = 10, 0.8 - (10/10) = -0.2
				
				if (percentLeft.compareTo(m.getRnPercentStart())>=0) {
					divergence = true;
				}
				
			}
			
			if (wtVsHighestRatio.compareTo(Indicators.MINUS_ONE)<=0) {
				if (small && diff.compareTo(m.getBnDiffThreshold())>0) {
					mv = SgMove.SN;
				} else {
					mv = SgMove.AN;
				}
				
			} else if (belowRnPercent) {
				if (small && diff.compareTo(m.getBnDiffThreshold())>0) {
					mv = SgMove.SN;
				} else {
					mv = SgMove.BN;
				}
			} else if (belowXnPercent) {
				if (small) {
					if (diff.compareTo(m.getBnDiffThreshold())>0) {
						mv = SgMove.SN;
					} else {
						mv = SgMove.BN;
					}
					
				} else {
					
					if (diff.compareTo(m.getBnDiffThreshold())>0) {
						mv = SgMove.RN;
					} else {
						mv = SgMove.BN;
					}
					
					
				}
			} else if (wtVsHighestRatio.signum()<=0) {
				if (small) {
					if (diff.compareTo(m.getBnDiffThreshold())>0) {
						mv = SgMove.SN;
					} else {
						mv = SgMove.BN;
					}
					
				} else {
					if (diff.compareTo(m.getBnDiffThreshold())>0) {
						mv = SgMove.XN;
					} else {
						mv = SgMove.BN;
					}
				}
			} else {
				if (small) {
					mv = SgMove.SN;
				} else {
					mv = SgMove.YN;
				}
			}
		}
		
		/*
		 * Divergence
		 */
		//logger.info("getSgMove: wt: " + wt + " highestWt: " + highestWt + " wt1: " + wt1 + " diff: " + diff + " now: " + now + " mv: " + mv + " divergence: " + divergence);
		
		return Pair.create(mv, divergence);
		
	}

	public static boolean satisfyHighLow(
			WtMinMax wt1mm, 
			BigDecimal wt, 
			Instant sinceWhenTs, 
			StatGranularity sg,
			Instant now, 
			boolean sumWtToWt1ForDynamics,
			Boolean sell) {
		
		RecentPeaks wt1_recent_peaks = wt1mm.getRecentPeaks(sinceWhenTs); //now.minus(timeService.getDuration(sg).multipliedBy(192)));
		
		if (wt1_recent_peaks == null) return false;
		if ((wt1_recent_peaks.decr_high1 == null) && (wt1_recent_peaks.incr_low1 == null)) return false;
		
		MarketData wt1_cur0 = wt1mm.getMarketData();
		
		//A17Trader.buySignalInfo = A17Trader.buySignalInfo + " shl: " + sell;
		//A17Trader.sellSignalInfo = A17Trader.sellSignalInfo + " shl: " + sell;
		if (sell) {
			//logger.info("satisfyHighLow: " + sg + " wt1_cur0: highestWt1: " + wt1_cur0.getHighestWt1() + " @" + wt1_cur0.getTs());
			
			BigDecimal currentHighestWt1High = null;
			BigDecimal previousWt1High = null;
			BigDecimal currentHighestHlc3 = null;
			BigDecimal previousHighestHlc3 = null;
			
			if (wt1mm.getIncreasing()) {
				
				if (wt.signum()>=0) {
					currentHighestWt1High = wt1_cur0.getHighestWt1();
					currentHighestHlc3 = wt1_cur0.getHighestHlc3();
					if (wt1_recent_peaks.incr_high2 != null)	{
						previousWt1High = wt1_recent_peaks.incr_high2.getHighestWt1();
						previousHighestHlc3 = wt1_recent_peaks.incr_high2.getHighestHlc3();
						
						//logger.info("satisfyHighLow: if increasing " + sg + " decr_high1:  " + currentHighestWt1High + " previousWt1High: " + previousWt1High + " @" + wt1_cur0.getTs());
						
					}
				} else {
					if (wt1_recent_peaks.decr_high1 != null) {
						currentHighestWt1High = wt1_recent_peaks.decr_high1.getHighestWt1();
						currentHighestHlc3 = wt1_recent_peaks.decr_high1.getHighestHlc3();
						if (wt1_recent_peaks.decr_high3 != null) {
							previousWt1High = wt1_recent_peaks.decr_high3.getHighestWt1();
							previousHighestHlc3 = wt1_recent_peaks.decr_high3.getHighestHlc3();
							
							//logger.info("satisfyHighLow: if decreasing " + sg + " decr_high1:  " + currentHighestWt1High + " previousWt1High: " + previousWt1High + " @" + wt1_cur0.getTs());
							
							
						}
					}
				}
				
				
			} else {
				if (wt.signum()<0) {
					if (wt1_recent_peaks.decr_high1 != null) {
						currentHighestWt1High = wt1_recent_peaks.decr_high1.getHighestWt1();
						currentHighestHlc3 = wt1_recent_peaks.decr_high1.getHighestHlc3();
						if (wt1_recent_peaks.decr_high3 != null) {
							previousWt1High = wt1_recent_peaks.decr_high3.getHighestWt1();
							previousHighestHlc3 = wt1_recent_peaks.decr_high3.getHighestHlc3();
							
							//logger.info("satisfyHighLow: if decreasing " + sg + " decr_high1:  " + currentHighestWt1High + " previousWt1High: " + previousWt1High + " @" + wt1_cur0.getTs());
							
							
						}
					}
				} else {
					currentHighestWt1High = wt1_cur0.getHighestWt1();
					currentHighestHlc3 = wt1_cur0.getHighestHlc3();
					if (wt1_recent_peaks.incr_high2 != null)	{
						previousWt1High = wt1_recent_peaks.incr_high2.getHighestWt1();
						previousHighestHlc3 = wt1_recent_peaks.incr_high2.getHighestHlc3();
						
						//logger.info("satisfyHighLow: if increasing " + sg + " decr_high1:  " + currentHighestWt1High + " previousWt1High: " + previousWt1High + " @" + wt1_cur0.getTs());
						
					}
				}
				
				
			}
			if (sumWtToWt1ForDynamics) {
				/*if (wt.getValue().signum()>0) {
					currentHighestWt1High = currentHighestWt1High.add(wt.getHighestValueSincePositive());
				}*/
				if (currentHighestWt1High != null) {
					currentHighestWt1High = currentHighestWt1High.add(wt);
				}
				
			}
			
			if (currentHighestWt1High == null) return true;
			if (previousWt1High == null) return true;
			
			//logger.info("satisfyHighLow: finally " + sg + " decr_high1:  " + currentHighestWt1High + " previousWt1High: " + previousWt1High + " @" + wt1_cur0.getTs());
			
			
			if (
					(currentHighestWt1High.compareTo(previousWt1High)<0) 
					//|| (currentHighestHlc3.compareTo(previousHighestHlc3)<0)
					) {
				//A17Trader.sellSignalInfo = A17Trader.sellSignalInfo + " SHL " + sg + ": h<=ph:" + currentHighestWt1High + "<=" + previousWt1High;
				return true;
				
			} else {
				//A17Trader.sellSignalInfo = A17Trader.sellSignalInfo + " NSHL " + sg + ": h>ph:" + currentHighestWt1High + ">" + previousWt1High;
				return false;
			}
		} else {
			BigDecimal currentLowestWt1Low = null;
			BigDecimal previousWt1Low = null;
			BigDecimal currentLowestHlc3 = null;
			BigDecimal previousLowestHlc3 = null;
			
			if (wt1mm.getIncreasing()) {
				
				if (wt.signum()>=0) {
					if (wt1_recent_peaks.incr_low1 != null) {
						currentLowestWt1Low = wt1_recent_peaks.incr_low1.getLowestWt1();
						currentLowestHlc3 = wt1_recent_peaks.incr_low1.getLowestHlc3();
						
						if (wt1_recent_peaks.incr_low3 != null) {
							previousWt1Low = wt1_recent_peaks.incr_low3.getLowestWt1();
							previousLowestHlc3 = wt1_recent_peaks.incr_low3.getLowestHlc3();
							
						}
					}
				} else {
					currentLowestWt1Low = wt1_cur0.getLowestWt1();
					currentLowestHlc3 = wt1_cur0.getLowestHlc3();
					
					if (wt1_recent_peaks.decr_low2 != null) {
						previousWt1Low = wt1_recent_peaks.decr_low2.getLowestWt1();
						previousLowestHlc3 = wt1_recent_peaks.decr_low2.getLowestHlc3();
					}
				}
				
				
				
				
			} else {
				
				if (wt.signum()<0) {
					currentLowestWt1Low = wt1_cur0.getLowestWt1();
					currentLowestHlc3 = wt1_cur0.getLowestHlc3();
					
					if (wt1_recent_peaks.decr_low2 != null) {
						previousWt1Low = wt1_recent_peaks.decr_low2.getLowestWt1();
						previousLowestHlc3 = wt1_recent_peaks.decr_low2.getLowestHlc3();
					}
				} else {
					if (wt1_recent_peaks.incr_low1 != null) {
						currentLowestWt1Low = wt1_recent_peaks.incr_low1.getLowestWt1();
						currentLowestHlc3 = wt1_recent_peaks.incr_low1.getLowestHlc3();
						
						if (wt1_recent_peaks.incr_low3 != null) {
							previousWt1Low = wt1_recent_peaks.incr_low3.getLowestWt1();
							previousLowestHlc3 = wt1_recent_peaks.incr_low3.getLowestHlc3();
							
						}
					}
				}
				
				
				
			}
			if (sumWtToWt1ForDynamics) {
				/*if (wt.getValue().signum()<0) {
					currentLowestWt1Low = currentLowestWt1Low.add(wt.getLowestValueSinceNegative());
				}*/
				if (currentLowestWt1Low != null) {
					currentLowestWt1Low = currentLowestWt1Low.add(wt);
				}
				
			}
			
			if (currentLowestWt1Low == null) return true;
			if (previousWt1Low == null) return true;
			
			if ((currentLowestWt1Low.compareTo(previousWt1Low)>0)
				//||(currentLowestHlc3.compareTo(previousLowestHlc3)>0)
					)
			
			{
				//A17Trader.buySignalInfo = A17Trader.buySignalInfo + " SHL " + sg + ": l>=pl:" + currentLowestWt1Low + ">=" + previousWt1Low;
				return true;
				
			} else {
				
				//A17Trader.buySignalInfo = A17Trader.buySignalInfo + " NSHL " + sg + ": l<pl:" + currentLowestWt1Low + "<" + previousWt1Low;
				return false;
			}
		}
		
		
		
	}
	
	
	
	public static Pair<Double, Double> getDivisors(BigDecimal wtbi, BigDecimal diffbi) {
		
		
		double def = 6d;
		double aligned = def;
		double opposed = def;
		
		double wt = wtbi.doubleValue();
		double diff = diffbi.doubleValue();
		// wt: -0.83
		// diff: 3
		if (wt < 0) {
			wt = - wt;
			diff = - diff;
		}
		
		// wt: 0.83
		// diff: -3
		
		double data = wt + diff;
		
		// data : -2.20
		double weight = 0;
		
		if (data>=1) {
			weight = Math.log(data);
			
		} else if (data<=-1) {
			weight = Math.log(-data);
			weight = -weight;
		} else {
			weight = 0;
		}
		
		aligned = def - weight*weight;
		opposed = def + 8*weight*weight;
		
		if (aligned<2.5) {
			aligned = 2.5;
		} else if (aligned>32) {
			aligned = 32;
		}
		if (opposed<2.5) {
			opposed = 2.5;
		} else if (opposed >32) {
			opposed = 32;
		}
		
		
		return Pair.create(aligned, opposed);
		
		
		
		
	}

	
}
