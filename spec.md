# Spec

## Variables (should be customizable in env of containers using the commons library)

- WAVETREND_OS: default to -53.
- WAVETREND_OB: default to 53.
- RP_XP_THRESHOLD: default to 6.
- RN_XN_THRESHOLD: default to -6.
- D4OVER_MIN_MUL: default to 3.
- D4OVER_MAX_MUL: default to 5.
- STALLED_P_THRESHOLD: default to 30.
- STALLED_N_THRESHOLD: default to -30.
- LOCAL_ALIGNMENT_RATIO: default to 2.
- COMBINED_ALIGNMENT_RATIO: default to 4.
- GRANULARITY_FROM: default to S60.
- GRANULARITY_UNTIL: default to S17457600.

## Alignment Signals

Each time we want to check for aligned signals, we must first check for the validity of the signal of each granularity.

### Validity of a granularity

Using StatVO attributes, for a given Instant ts, we can define if a granularity is to be considered as Valid for a SIDE.

#### Validity of a granularity for BUY SIDE

For BUY side a granularity is considered valid if:

- LHHL: its SgMove is RN or XN AND (current wt low is increasing: higher than previous low (increasingLows is true))

- OVER: its wt1 is oversold: lower than WAVETREND_OS

- D4OVER: its SgMove is AN, BN, XN, RN AND current wt low is increasing: higher than previous low (increasingLows is true) AND granularity D4OVER_MIN_MUL to D4OVER_MAX_MUL times smaller is oversold: lower than WAVETREND_OS

- STLD: its SgMove is SN, SP, ST and wt1 <= STALLED_N_THRESHOLD

- STLDLHHL: its SgMove is SN, SP, ST and increasingLows

#### Validity of a granularity for SELL SIDE

For SELL side a granularity is considered valid if:

- LHHL: its SgMove is RP or XP AND (current wt high is decreasing: lower than previous high (decreasingHighs is true) )
- OVER: its wt1 is overbought: higher than WAVETREND_OB
- D4OVER: its SgMove is AP, BP, XP, RP AND current wt high is decreasing: lower than previous high (decreasingHighs is true) AND granularity D4OVER_MIN_MUL to D4OVER_MAX_MUL times smaller is overbought: higher than WAVETREND_OB

- STLD: its SgMove is SN, SP, ST and wt1 >= STALLED_P_THRESHOLD

- STLDLHHL: its SgMove is SN, SP, ST and decreasingHighs

## Memory Persistence of alignments

When we have selected the valid signals of each side, we can now evaluate signal alignment.

- The AbstractNgWaveService.inject method is always called synchronously, which means it is never called by various threads at the same time. This method is responsible of storing all alignments as persistent variables, and update them each time inject(...) is called. They must be synchronized to prevent concurrency issues, as other methods can access these variables. Inject build them, and each time it is called, replace them with the new evaluation to maintain them up-do-date.

- The AbstractNgWaveService.logAlignments() method can be called independently to log the info using these memory persistent variables.

### Local Alignment

A Local Alignment is a group of ordered Valid Granularities of a given Side where the time ratio between 2 granularities is lower or equal to a factor of LOCAL_ALIGNMENT_RATIO.

Examples using the default LOCAL_ALIGNMENT_RATIO = 2.

#### Example 1

Let's consider that the following granularities are valid for the BUY side:

- S280, S250, S230, S210, S190, S110, S100, S90, S80, S70, S60

the biggest ratio between 2 granularities is 190/110 <=2

so (S280, S250, S230, S210, S190, S110, S100, S90, S80, S70, S60) IS a local alignment

#### Example 2

Let's consider that the following granularities are valid for the BUY side:

- S280, S250, S230, S110, S100, S90, S80, S70, S60

the biggest ratio between 2 granularities is 230/110 >2

so (S280, S250, S230, S110, S100, S90, S80, S70, S60) IS NOT a local alignment

however, S280, S250, S230 AND S110, S100, S90, S80, S70, S60 are local alignments.

### Combined Alignments

A Combined Alignment is a group of ordered Local Alignments of a given Side where the time ratio between 2 Local Alignments is lower or equal to a factor of COMBINED_ALIGNMENT_RATIO.

Example we have the 2 following local alignments for a given SIDE and we use the default COMBINED_ALIGNMENT_RATIO = 4:

- Local alignment #1: S280, S250, S230
- Local alignment #2: S110, S100, S90, S80, S70, S60

230/110 <= 4

So, (Local alignment #1, Local alignment #2) is a Combined Alignment.

### Enabler Alignment

An Enabler Alignment is a Local Alignment whose lower granularity is below GRANULARITY_FROM * 3.

Example: if GRANULARITY_FROM is S60, a Local Alignment is an Enabler Alignment if its lower granularity is < durationOf(S60) * 3 which means S180.

### Logging of Aligments

To produce line like "D36.3:AN wt:-15.7844706 highestWt:-15.8885586 wt1: 35.4532904 diff:-4.2789475 HH LL POB @2026-03-02T16:14:19.183337Z" use toMoveString() method of StatVO

⚠️️: distance too high (local alignment NOT combined with previous one)
🟢: distance OK (local alignment is combined with previous one)

Example:

SG: BUY SIDE - ENABLER

-- Combined Alignment 1
-- Local Alignment - width:    1.46
D4OVER D36.3:AN wt:-15.7844706 highestWt:-15.8885586 wt1: 35.4532904 diff:-4.2789475 HH LL POB @2026-03-02T16:14:19.183337Z
D4OVER D33.0:AN wt:-15.5489583 highestWt:-15.6130857 wt1: 28.7295677 diff:-2.9685056 HH LL POB @2026-03-02T16:14:19.183337Z
D4OVER D30.0:AN wt:-16.6533807 highestWt:-16.7392516 wt1: 25.2373063 diff:-2.2359301 HH LL POB @2026-03-02T16:14:19.183337Z
D4OVER D27.3:BN wt:-17.470556 highestWt:-17.563883 wt1: 17.696148 diff:-1.8669821 HH LL POB @2026-03-02T16:14:19.183337Z
D4OVER D24.8:BN wt:-16.5875998 highestWt:-16.6334982 wt1: 9.8610452 diff:-0.2491690 HH LL POB @2026-03-02T16:14:19.183337Z
-- Local Alignment - width:    2.35 - distance:    1.95 🟢
D4OVER D12.7:BN wt:-10.3671166 highestWt:-19.609573 wt1: -38.1934606 diff:1.0583644 LH LL POB @2026-03-02T16:14:19.183337Z
D4OVER D11.6:BN wt:-9.8937702 highestWt:-20.088283 wt1: -42.7199862 diff:1.6225578 HH LL POB @2026-03-02T16:14:19.183337Z
D4OVER D10.5:RN wt:-8.0881055 highestWt:-20.748485 wt1: -48.5414105 diff:2.2447957 HH LL POB @2026-03-02T16:14:19.183337Z
D4OVER D9.6:RN wt:-6.7622419 highestWt:-20.8890015 wt1: -52.3222729 diff:2.8576248 HH LL @2026-03-02T16:14:19.183337Z
  OVER D8.7:BN wt:-6.3055510 highestWt:-10.609836 wt1: -55.9113360 diff:3.3312573 HH LL POB @2026-03-02T16:14:19.183337Z
  OVER D7.9:XN wt:-4.3667249 highestWt:-10.392079 wt1: -60.7618459 diff:3.5986490 HH LL @2026-03-02T16:14:19.183337Z
  OVER D7.2:XN wt:-3.7407907 highestWt:-10.727799 wt1: -59.6976987 diff:3.6904147 HH LL @2026-03-02T16:14:19.183337Z
  OVER D6.5:XN wt:-3.2333958 highestWt:-11.784329 wt1: -62.2321538 diff:4.2184480 HH LL POB @2026-03-02T16:14:19.183337Z
  OVER D5.9:XN wt:-0.9603490 highestWt:-11.907475 wt1: -64.7292480 diff:3.2126496 HH LL @2026-03-02T16:14:19.183337Z
  OVER D5.4:XN wt:-1.2017583 highestWt:-13.243947 wt1: -65.2588373 diff:3.0578453 HH LL @2026-03-02T16:14:19.183337Z
-- Combined Alignment 2 - distance:    466,500
-- Local Alignment - width:    2.35 - distance:    466,500 ⚠️️
  STLD M1.0:SN wt:-0.7167646 highestWt:-1.0904788 wt1: 46.1705264 diff:-1.5018574 LH HL @2026-03-02T16:14:19.183337Z