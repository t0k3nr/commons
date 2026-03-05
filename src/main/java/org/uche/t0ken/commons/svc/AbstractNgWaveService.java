package org.uche.t0ken.commons.svc;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.NavigableMap;

import org.jboss.logging.Logger;
import org.uche.t0ken.commons.enums.StatGranularity;
import org.uche.t0ken.commons.util.SgMove;
import org.uche.t0ken.commons.vo.StatVO;

public abstract class AbstractNgWaveService extends AbstractWaveService {

	private final static Logger logger = Logger.getLogger("AbstractNgWaveService");

	protected BigDecimal WAVETREND_OS = new BigDecimal(-53);
	protected BigDecimal WAVETREND_OB = new BigDecimal(53);
	protected BigDecimal RP_XP_THRESHOLD = new BigDecimal(6);
	protected BigDecimal RN_XN_THRESHOLD = new BigDecimal(-6);
	protected double D4OVER_MIN_MUL = 3.0;
	protected double D4OVER_MAX_MUL = 5.0;
	protected BigDecimal STALLED_P_THRESHOLD = new BigDecimal(30);
	protected BigDecimal STALLED_N_THRESHOLD = new BigDecimal(-30);
	protected double LOCAL_ALIGNMENT_RATIO = 2.0;
	protected double COMBINED_ALIGNMENT_RATIO = 4.0;
	protected StatGranularity GRANULARITY_FROM = StatGranularity.S60;
	protected StatGranularity GRANULARITY_UNTIL = StatGranularity.S17457600;

	// ========== Validity type labels (6 chars, right-aligned) ==========

	private enum ValidityType {
		D4OVER("D4OVER"),
		OVER("  OVER"),
		LHHL("  LHHL"),
		STLD("  STLD"),
		STLDLH("STLDLH");

		private final String label;
		ValidityType(String label) { this.label = label; }
		public String getLabel() { return label; }
	}

	private static class ValidEntry {
		final StatGranularity sg;
		final StatVO stat;
		final ValidityType type;

		ValidEntry(StatGranularity sg, StatVO stat, ValidityType type) {
			this.sg = sg;
			this.stat = stat;
			this.type = type;
		}
	}

	// ========== Memory Persistence of alignments ==========

	private final Object alignmentLock = new Object();
	private List<List<List<ValidEntry>>> persistedBuyCombinedAlignments = new ArrayList<>();
	private List<List<List<ValidEntry>>> persistedSellCombinedAlignments = new ArrayList<>();
	private boolean persistedBuyHasEnabler = false;
	private boolean persistedSellHasEnabler = false;
	private ValidEntry persistedBuyTendency = null;
	private ValidEntry persistedSellTendency = null;

	@Override
	public Boolean inject(Instant now, BigDecimal bid, BigDecimal ask,
			NavigableMap<StatGranularity, StatVO> moves, boolean live, Boolean clue) {

		// Step 1: Determine valid granularities for each side (ordered desc)
		List<ValidEntry> validBuy = new ArrayList<>();
		List<ValidEntry> validSell = new ArrayList<>();

		for (StatGranularity sg : moves.descendingKeySet()) {
			if (sg.compareTo(GRANULARITY_FROM) < 0 || sg.compareTo(GRANULARITY_UNTIL) > 0) continue;

			StatVO mv = moves.get(sg);
			if (mv == null || mv.getMv() == null) continue;

			ValidityType buyType = getValidityForBuy(sg, mv, moves);
			if (buyType != null) {
				validBuy.add(new ValidEntry(sg, mv, buyType));
			}

			ValidityType sellType = getValidityForSell(sg, mv, moves);
			if (sellType != null) {
				validSell.add(new ValidEntry(sg, mv, sellType));
			}
		}

		// Step 2: Build Local Alignments for each side (ordered desc)
		List<List<ValidEntry>> buyLocalAlignments = buildLocalAlignments(validBuy);
		List<List<ValidEntry>> sellLocalAlignments = buildLocalAlignments(validSell);

		// Step 3: Build Combined Alignments for each side (ordered desc)
		List<List<List<ValidEntry>>> buyCombined = buildCombinedAlignments(buyLocalAlignments);
		List<List<List<ValidEntry>>> sellCombined = buildCombinedAlignments(sellLocalAlignments);

		// Step 4: Combined Alignments that include an Enabler Alignment
		boolean buyHasEnabler = !filterWithEnabler(buyCombined).isEmpty();
		boolean sellHasEnabler = !filterWithEnabler(sellCombined).isEmpty();

		// Step 5: Find Tendency for each side
		ValidEntry buyTendency = findTendency(buyLocalAlignments, true);
		ValidEntry sellTendency = findTendency(sellLocalAlignments, false);

		// Store as persistent variables (synchronized for concurrent access by logAlignments)
		synchronized (alignmentLock) {
			persistedBuyCombinedAlignments = buyCombined;
			persistedSellCombinedAlignments = sellCombined;
			persistedBuyHasEnabler = buyHasEnabler;
			persistedSellHasEnabler = sellHasEnabler;
			persistedBuyTendency = buyTendency;
			persistedSellTendency = sellTendency;
		}

		return null;
	}

	public void logAlignments() {
		List<List<List<ValidEntry>>> buyCombined;
		List<List<List<ValidEntry>>> sellCombined;
		boolean buyHasEnabler;
		boolean sellHasEnabler;

		synchronized (alignmentLock) {
			buyCombined = persistedBuyCombinedAlignments;
			sellCombined = persistedSellCombinedAlignments;
			buyHasEnabler = persistedBuyHasEnabler;
			sellHasEnabler = persistedSellHasEnabler;
		}

		logAlignmentSide("BUY", buyCombined, buyHasEnabler);
		logAlignmentSide("SELL", sellCombined, sellHasEnabler);
	}

	// ========== Tendency ==========

	/*
	 * Search Local Alignments starting from the biggest granularity.
	 * For BUY: find the lowest granularity entry whose SgMove is AN, BN, RN or XN.
	 * For SELL: find the lowest granularity entry whose SgMove is AP, BP, RP or XP.
	 */
	private ValidEntry findTendency(List<List<ValidEntry>> localAlignments, boolean buy) {
		for (List<ValidEntry> la : localAlignments) {
			for (int i = la.size() - 1; i >= 0; i--) {
				SgMove mv = la.get(i).stat.getMv();
				if (buy) {
					if (mv == SgMove.AN || mv == SgMove.BN || mv == SgMove.RN || mv == SgMove.XN) {
						return la.get(i);
					}
				} else {
					if (mv == SgMove.AP || mv == SgMove.BP || mv == SgMove.RP || mv == SgMove.XP) {
						return la.get(i);
					}
				}
			}
		}
		return null;
	}

	public void logTendency() {
		ValidEntry buyTendency;
		ValidEntry sellTendency;

		synchronized (alignmentLock) {
			buyTendency = persistedBuyTendency;
			sellTendency = persistedSellTendency;
		}

		if (buyTendency != null) {
			logger.info("TENDENCY: \uD83D\uDFE2 " + buyTendency.stat.toMoveString());
		}
		if (sellTendency != null) {
			logger.info("TENDENCY: \uD83D\uDD34 " + sellTendency.stat.toMoveString());
		}
	}

	// ========== Validity type resolution ==========

	private ValidityType getValidityForBuy(StatGranularity sg, StatVO mv, NavigableMap<StatGranularity, StatVO> moves) {
		if (isValidBuyD4OVER(sg, mv, moves)) return ValidityType.D4OVER;
		if (isValidBuyLHHL(mv)) return ValidityType.LHHL;
		if (isValidBuyOVER(mv)) return ValidityType.OVER;
		if (isValidBuySTLD(mv)) return ValidityType.STLD;
		if (isValidBuySTLDLHHL(mv)) return ValidityType.STLDLH;

		return null;
	}

	private ValidityType getValidityForSell(StatGranularity sg, StatVO mv, NavigableMap<StatGranularity, StatVO> moves) {
		if (isValidSellD4OVER(sg, mv, moves)) return ValidityType.D4OVER;
		if (isValidSellLHHL(mv)) return ValidityType.LHHL;
		if (isValidSellOVER(mv)) return ValidityType.OVER;
		if (isValidSellSTLD(mv)) return ValidityType.STLD;
		if (isValidSellSTLDLHHL(mv)) return ValidityType.STLDLH;

		return null;
	}

	// ========== Validity checks ==========

	// LHHL BUY: SgMove is RN or XN AND increasingLows
	private boolean isValidBuyLHHL(StatVO mv) {
		if (!mv.isRnXn(RN_XN_THRESHOLD)) return false;
		return Boolean.TRUE.equals(mv.getIncreasingLows());
	}

	// OVER BUY: wt1 is oversold (lower than WAVETREND_OS)
	private boolean isValidBuyOVER(StatVO mv) {
		return mv.getWt1() != null && mv.wt1Below(WAVETREND_OS);
	}

	// D4OVER BUY: SgMove is AN, BN, XN, RN AND increasingLows AND granularity D4OVER_MIN_MUL to D4OVER_MAX_MUL times smaller is oversold
	private boolean isValidBuyD4OVER(StatGranularity sg, StatVO mv, NavigableMap<StatGranularity, StatVO> moves) {
		if (!mv.isAnBn(RN_XN_THRESHOLD) && !mv.isRnXn(RN_XN_THRESHOLD)) return false;
		if (!Boolean.TRUE.equals(mv.getIncreasingLows())) return false;
		return hasSmallerGranularityBelow(sg, moves, WAVETREND_OS);
	}

	// STLD BUY: SgMove is SN, SP, ST AND wt1 <= STALLED_N_THRESHOLD
	private boolean isValidBuySTLD(StatVO mv) {
		if (!isStalled(mv)) return false;
		return mv.wt1Below(STALLED_N_THRESHOLD);
	}

	// STLDLHHL BUY: SgMove is SN, SP, ST AND increasingLows
	private boolean isValidBuySTLDLHHL(StatVO mv) {
		if (!isStalled(mv)) return false;
		return Boolean.TRUE.equals(mv.getIncreasingLows());
	}

	// LHHL SELL: SgMove is RP or XP AND decreasingHighs
	private boolean isValidSellLHHL(StatVO mv) {
		if (!mv.isRpXp(RP_XP_THRESHOLD)) return false;
		return Boolean.TRUE.equals(mv.getDecreasingHighs());
	}

	// OVER SELL: wt1 is overbought (higher than WAVETREND_OB)
	private boolean isValidSellOVER(StatVO mv) {
		return mv.getWt1() != null && mv.wt1Above(WAVETREND_OB);
	}

	// D4OVER SELL: SgMove is AP, BP, XP, RP AND decreasingHighs AND granularity D4OVER_MIN_MUL to D4OVER_MAX_MUL times smaller is overbought
	private boolean isValidSellD4OVER(StatGranularity sg, StatVO mv, NavigableMap<StatGranularity, StatVO> moves) {
		if (!mv.isApBp(RP_XP_THRESHOLD) && !mv.isRpXp(RP_XP_THRESHOLD)) return false;
		if (!Boolean.TRUE.equals(mv.getDecreasingHighs())) return false;
		return hasSmallerGranularityAbove(sg, moves, WAVETREND_OB);
	}

	// STLD SELL: SgMove is SN, SP, ST AND wt1 >= STALLED_P_THRESHOLD
	private boolean isValidSellSTLD(StatVO mv) {
		if (!isStalled(mv)) return false;
		return mv.wt1Above(STALLED_P_THRESHOLD);
	}

	// STLDLHHL SELL: SgMove is SN, SP, ST AND decreasingHighs
	private boolean isValidSellSTLDLHHL(StatVO mv) {
		if (!isStalled(mv)) return false;
		return Boolean.TRUE.equals(mv.getDecreasingHighs());
	}

	private boolean isStalled(StatVO mv) {
		switch (mv.getMv()) {
		case ST:
		case SP:
		case SN:
			return true;
		default:
			return false;
		}
	}

	// ========== D4OVER helpers ==========

	private boolean hasSmallerGranularityBelow(StatGranularity sg, NavigableMap<StatGranularity, StatVO> moves, BigDecimal threshold) {
		int duration = sg.getIndex();
		int minDuration = (int) (duration / D4OVER_MAX_MUL);
		int maxDuration = (int) (duration / D4OVER_MIN_MUL);

		for (StatGranularity smallerSg : moves.keySet()) {
			int d = smallerSg.getIndex();
			if (d >= minDuration && d <= maxDuration) {
				StatVO smallerMv = moves.get(smallerSg);
				if (smallerMv != null && smallerMv.getWt1() != null && smallerMv.wt1Below(threshold)) {
					return true;
				}
			}
			if (d > maxDuration) break;
		}
		return false;
	}

	private boolean hasSmallerGranularityAbove(StatGranularity sg, NavigableMap<StatGranularity, StatVO> moves, BigDecimal threshold) {
		int duration = sg.getIndex();
		int minDuration = (int) (duration / D4OVER_MAX_MUL);
		int maxDuration = (int) (duration / D4OVER_MIN_MUL);

		for (StatGranularity smallerSg : moves.keySet()) {
			int d = smallerSg.getIndex();
			if (d >= minDuration && d <= maxDuration) {
				StatVO smallerMv = moves.get(smallerSg);
				if (smallerMv != null && smallerMv.getWt1() != null && smallerMv.wt1Above(threshold)) {
					return true;
				}
			}
			if (d > maxDuration) break;
		}
		return false;
	}

	// ========== Local Alignments ==========

	/*
	 * Groups valid granularities (already ordered desc) into Local Alignments.
	 * A Local Alignment is a maximal contiguous subsequence where the ratio
	 * between each consecutive pair (larger / smaller) is <= LOCAL_ALIGNMENT_RATIO.
	 */
	private List<List<ValidEntry>> buildLocalAlignments(List<ValidEntry> validEntries) {
		List<List<ValidEntry>> localAlignments = new ArrayList<>();
		if (validEntries.isEmpty()) return localAlignments;

		List<ValidEntry> current = new ArrayList<>();
		current.add(validEntries.get(0));

		for (int i = 1; i < validEntries.size(); i++) {
			ValidEntry prev = validEntries.get(i - 1);
			ValidEntry curr = validEntries.get(i);

			double ratio = (double) prev.sg.getIndex() / (double) curr.sg.getIndex();

			if (ratio <= LOCAL_ALIGNMENT_RATIO) {
				current.add(curr);
			} else {
				localAlignments.add(current);
				current = new ArrayList<>();
				current.add(curr);
			}
		}
		localAlignments.add(current);

		return localAlignments;
	}

	// ========== Combined Alignments ==========

	/*
	 * Groups Local Alignments (already ordered desc) into Combined Alignments.
	 * The ratio between the lowest granularity of a Local Alignment and the
	 * highest granularity of the next Local Alignment must be <= COMBINED_ALIGNMENT_RATIO.
	 */
	private List<List<List<ValidEntry>>> buildCombinedAlignments(List<List<ValidEntry>> localAlignments) {
		List<List<List<ValidEntry>>> combinedAlignments = new ArrayList<>();
		if (localAlignments.isEmpty()) return combinedAlignments;

		List<List<ValidEntry>> currentCombined = new ArrayList<>();
		currentCombined.add(localAlignments.get(0));

		for (int i = 1; i < localAlignments.size(); i++) {
			List<ValidEntry> prevLA = localAlignments.get(i - 1);
			List<ValidEntry> currLA = localAlignments.get(i);

			double ratio = laDistance(prevLA, currLA);

			if (ratio <= COMBINED_ALIGNMENT_RATIO) {
				currentCombined.add(currLA);
			} else {
				combinedAlignments.add(currentCombined);
				currentCombined = new ArrayList<>();
				currentCombined.add(currLA);
			}
		}
		combinedAlignments.add(currentCombined);

		return combinedAlignments;
	}

	// ========== Enabler Alignment ==========

	/*
	 * An Enabler Alignment is a Local Alignment whose lowest granularity
	 * is < GRANULARITY_FROM * 3.
	 */
	private boolean isEnablerAlignment(List<ValidEntry> localAlignment) {
		ValidEntry lowest = localAlignment.get(localAlignment.size() - 1);
		return lowest.sg.getIndex() < GRANULARITY_FROM.getIndex() * 3;
	}

	/*
	 * Returns only the Combined Alignments that include at least one Enabler Local Alignment.
	 */
	private List<List<List<ValidEntry>>> filterWithEnabler(List<List<List<ValidEntry>>> combinedAlignments) {
		List<List<List<ValidEntry>>> result = new ArrayList<>();
		for (List<List<ValidEntry>> combined : combinedAlignments) {
			for (List<ValidEntry> la : combined) {
				if (isEnablerAlignment(la)) {
					result.add(combined);
					break;
				}
			}
		}
		return result;
	}

	// ========== Setters ==========

	public void setWAVETREND_OS(BigDecimal v) { this.WAVETREND_OS = v; }
	public void setWAVETREND_OB(BigDecimal v) { this.WAVETREND_OB = v; }
	@Override public void setRP_XP_THRESHOLD(BigDecimal v) { this.RP_XP_THRESHOLD = v; super.setRP_XP_THRESHOLD(v); }
	@Override public void setRN_XN_THRESHOLD(BigDecimal v) { this.RN_XN_THRESHOLD = v; super.setRN_XN_THRESHOLD(v); }
	public void setD4OVER_MIN_MUL(double v) { this.D4OVER_MIN_MUL = v; }
	public void setD4OVER_MAX_MUL(double v) { this.D4OVER_MAX_MUL = v; }
	public void setSTALLED_P_THRESHOLD(BigDecimal v) { this.STALLED_P_THRESHOLD = v; }
	public void setSTALLED_N_THRESHOLD(BigDecimal v) { this.STALLED_N_THRESHOLD = v; }
	public void setLOCAL_ALIGNMENT_RATIO(double v) { this.LOCAL_ALIGNMENT_RATIO = v; }
	public void setCOMBINED_ALIGNMENT_RATIO(double v) { this.COMBINED_ALIGNMENT_RATIO = v; }
	public void setGRANULARITY_FROM(StatGranularity v) { this.GRANULARITY_FROM = v; }
	public void setGRANULARITY_UNTIL(StatGranularity v) { this.GRANULARITY_UNTIL = v; }

	// ========== Alignment metrics ==========

	private double laWidth(List<ValidEntry> la) {
		return (double) la.get(0).sg.getIndex() / (double) la.get(la.size() - 1).sg.getIndex();
	}

	private double laDistance(List<ValidEntry> prevLA, List<ValidEntry> currLA) {
		return (double) prevLA.get(prevLA.size() - 1).sg.getIndex() / (double) currLA.get(0).sg.getIndex();
	}

	// ========== Logging ==========

	private static final String DOUBLE_FORMAT = "%,10.2f";

	private void logAlignmentSide(String side,
			List<List<List<ValidEntry>>> combinedAlignments,
			boolean hasEnabler) {

		if (combinedAlignments.isEmpty()) return;

		StringBuilder buf = new StringBuilder();
		String sideEmoji = side.equals("BUY") ? "\uD83D\uDFE2\uD83D\uDFE2\uD83D\uDFE2" : "\uD83D\uDD34\uD83D\uDD34\uD83D\uDD34";
		buf.append("\n\nSG: ").append(sideEmoji).append(" ").append(side).append(" SIDE");
		if (hasEnabler) buf.append(" - ENABLER");

		List<ValidEntry> prevLA = null;
		int caIndex = 0;

		for (List<List<ValidEntry>> ca : combinedAlignments) {
			caIndex++;

			// -- Combined Alignment header
			buf.append("\n\n-- Combined Alignment ").append(caIndex);
			if (prevLA != null) {
				double distance = laDistance(prevLA, ca.get(0));
				buf.append(" - distance: ").append(String.format(DOUBLE_FORMAT, distance));
			}

			for (List<ValidEntry> la : ca) {

				// -- Local Alignment header
				double width = laWidth(la);
				buf.append("\n-- Local Alignment - width: ").append(String.format(DOUBLE_FORMAT, width));

				if (prevLA != null) {
					double distance = laDistance(prevLA, la);
					buf.append(" - distance: ").append(String.format(DOUBLE_FORMAT, distance));
					if (distance <= COMBINED_ALIGNMENT_RATIO) {
						buf.append(" \uD83D\uDFE2");
					} else {
						buf.append(" \u26A0\uFE0F\uFE0F");
					}
				}

				// -- Each granularity entry
				for (ValidEntry ve : la) {
					buf.append("\n").append(ve.type.getLabel()).append(" ").append(ve.stat.toMoveString());
				}

				prevLA = la;
			}
		}

		logger.info(buf);
	}

}
