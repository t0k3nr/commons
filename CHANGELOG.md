# Changelog

## [2.0.0](https://github.com/t0k3nr/commons/compare/v1.15.0...v2.0.0) (2026-03-13)


### ⚠ BREAKING CHANGES

* AbstractWaveService removed. Subclasses must extend AbstractNgWaveService directly.

### Features

* add configurable MIN_TENDENCY_WIDTH, bump to 1.33.0 ([#41](https://github.com/t0k3nr/commons/issues/41)) ([ff4fac4](https://github.com/t0k3nr/commons/commit/ff4fac4289d05218c77bd8c093b0d34836107f94))
* add getTendencySg() getter, bump to 1.31.0 ([#39](https://github.com/t0k3nr/commons/issues/39)) ([7695fc1](https://github.com/t0k3nr/commons/commit/7695fc1240fdfeb96f301dcaf946bc89da6c4375))
* add LOWNRGY validity, remove STD4LHHL, bump to 1.30.0 ([#38](https://github.com/t0k3nr/commons/issues/38)) ([0a90201](https://github.com/t0k3nr/commons/commit/0a9020129b0418a799f4d5829b62b8f89ee6a9dd))
* add setters and extend AbstractWaveService for backward compatibility ([#20](https://github.com/t0k3nr/commons/issues/20)) ([06e9ff2](https://github.com/t0k3nr/commons/commit/06e9ff26b3c117ac80093a331404bd204a160870))
* add side emojis to alignment log, comment out verbose divergent logs, bump to 1.18.0 ([#24](https://github.com/t0k3nr/commons/issues/24)) ([b406868](https://github.com/t0k3nr/commons/commit/b40686812042147d137f703d46e3bdf9b8a41295))
* alignment memory persistence in inject(), parameterless logAlignments(), bump to 1.21.0 ([#27](https://github.com/t0k3nr/commons/issues/27)) ([060d9b2](https://github.com/t0k3nr/commons/commit/060d9b295459f45c8fc47c9bc43d695b2fe5f42c))
* break tendency when same-side highestWt drops below 80% of previous sg, bump to 1.34.0 ([#43](https://github.com/t0k3nr/commons/issues/43)) ([45107c2](https://github.com/t0k3nr/commons/commit/45107c222d6d3b95fe2b394c2fdfef5411a62d43))
* combined width for tendencies, selection uses combinedWidth, bump to 1.42.0 ([#51](https://github.com/t0k3nr/commons/issues/51)) ([9432f79](https://github.com/t0k3nr/commons/commit/9432f799ec12bd59e029b76272c59647b4df4731))
* delete AbstractWaveService, move threshold getters to AbstractNgWaveService ([#33](https://github.com/t0k3nr/commons/issues/33)) ([0f6d92b](https://github.com/t0k3nr/commons/commit/0f6d92b50feebc8ef3b49891ad98daff556160f9))
* expose alignment/tendency getters for websocket broadcasting ([#32](https://github.com/t0k3nr/commons/issues/32)) ([360d00e](https://github.com/t0k3nr/commons/commit/360d00eb8aa06376ae83e1808d0a38d4c83cc155))
* firstPassTendencyFirstSg for tendency width, OVER highestWt checks, bump to 1.36.0 ([#45](https://github.com/t0k3nr/commons/issues/45)) ([2b5b905](https://github.com/t0k3nr/commons/commit/2b5b905ebf179e8ab40476536edc2afd06bce3de))
* implement multi-tendency list (TendencyEntry, findTendencies, logTendency), bump to 1.39.0 ([#48](https://github.com/t0k3nr/commons/issues/48)) ([e65ab3b](https://github.com/t0k3nr/commons/commit/e65ab3b793dc167cc5d408c68d701b9d79bc5450))
* implement signal logic per spec - BUY/SELL conditions based on tendency validity and ENABLER Combined Alignments, bump to 1.28.0 ([#36](https://github.com/t0k3nr/commons/issues/36)) ([8a3bd55](https://github.com/t0k3nr/commons/commit/8a3bd5531b245a207950287b55119799afe48c19))
* make TENDENCY_WT_DROP_THRESHOLD configurable, bump to 1.35.0 ([#44](https://github.com/t0k3nr/commons/issues/44)) ([93e9163](https://github.com/t0k3nr/commons/commit/93e91638c59c469cedfb2eee1af75db4320e214b))
* OVER as standalone validity type, LHHL requires only increasingLows/decreasingHighs, bump to 1.20.0 ([#26](https://github.com/t0k3nr/commons/issues/26)) ([6d28871](https://github.com/t0k3nr/commons/commit/6d28871354d58e9d9bc475b793d6d7dfd297c1bb))
* rewrite tendency + replace STLDLHHL with STD4LHHL per new spec ([#31](https://github.com/t0k3nr/commons/issues/31)) ([215f902](https://github.com/t0k3nr/commons/commit/215f90213657d93c4851890f1af1870f3f2a41b2))
* tendency logic in inject(), logTendency() method, bump to 1.22.0 ([#28](https://github.com/t0k3nr/commons/issues/28)) ([c4d9df4](https://github.com/t0k3nr/commons/commit/c4d9df4f613b7aed12271698d2fc73d80fc5da8f))
* tendency width check &gt;= 2.00 with retry, bump to 1.32.0 ([#40](https://github.com/t0k3nr/commons/issues/40)) ([0c3a2da](https://github.com/t0k3nr/commons/commit/0c3a2da93282fed650a13850c3e2aea6caeda148))
* update D4OVER spec and comments, bump to 1.19.0 ([#25](https://github.com/t0k3nr/commons/issues/25)) ([1a899b1](https://github.com/t0k3nr/commons/commit/1a899b133742a739372112880234b15213003c5f))
* X-based tendency selection, checkmark logging, bump to 1.41.0 ([#50](https://github.com/t0k3nr/commons/issues/50)) ([1fcedb7](https://github.com/t0k3nr/commons/commit/1fcedb780831456003b18c5adc1b19b410e905b5))


### Bug Fixes

* add deriveIndicators() to decouple WtMinMax state from per-match indicator derivation ([#30](https://github.com/t0k3nr/commons/issues/30)) ([47dda7d](https://github.com/t0k3nr/commons/commit/47dda7dd94f7e30f9d04a5329d1b89c5727e6325))
* add getMv() null guards in hasSmallerGranularity methods, bump to 1.27.0 ([#35](https://github.com/t0k3nr/commons/issues/35)) ([eea9b89](https://github.com/t0k3nr/commons/commit/eea9b89304372405264a03e30ba5fddf96feb62d))
* add null guard to highestWtAbove and highestWtBelow ([#22](https://github.com/t0k3nr/commons/issues/22)) ([0395d13](https://github.com/t0k3nr/commons/commit/0395d13f5660d5970728cfe0f0a04c99938f120e))
* add TENDENCIES header to logTendency, bump to 1.40.0 ([#49](https://github.com/t0k3nr/commons/issues/49)) ([b1f8a78](https://github.com/t0k3nr/commons/commit/b1f8a788bdb3a29256109faa25ed3b1678da465d))
* disable contradictory signal decision (buySignal && sellSignal returns null), bump to 1.29.0 ([#37](https://github.com/t0k3nr/commons/issues/37)) ([e8e4458](https://github.com/t0k3nr/commons/commit/e8e445837d93fb0909f5e270a757a9b2f3b48f83))
* null guard fixes, setter chaining, and extract logAlignments ([#23](https://github.com/t0k3nr/commons/issues/23)) ([5617cec](https://github.com/t0k3nr/commons/commit/5617cec045db607840559594785b707eecd9367a))


### Reverts

* remove firstPassTendencyFirstSg from tendency, bump to 1.37.0 ([#46](https://github.com/t0k3nr/commons/issues/46)) ([da176ba](https://github.com/t0k3nr/commons/commit/da176ba7031a2da399776838002928e34626a5a4))

## [1.15.0](https://github.com/t0k3nr/commons/compare/v1.14.0...v1.15.0) (2026-03-03)


### Features

* add AbstractNgWaveService with WaveTrend alignment signal detection ([#19](https://github.com/t0k3nr/commons/issues/19)) ([c3199aa](https://github.com/t0k3nr/commons/commit/c3199aa5f947b78e81edcdb0869c58823cc4da38))


### Bug Fixes

* align pom.xml version with release-please manifest (1.14.0) ([#17](https://github.com/t0k3nr/commons/issues/17)) ([f138bf2](https://github.com/t0k3nr/commons/commit/f138bf2935e272e876e4a53de2aa55927d8392b8))

## [1.14.0](https://github.com/t0k3nr/commons/compare/v1.13.1...v1.14.0) (2026-03-01)


### Features

* initial commit ([48a2b75](https://github.com/t0k3nr/commons/commit/48a2b75d28e521be9e99898f90b04c600c02342f))


### Bug Fixes

* add null guard for wt in StatVO.toMoveString and toShortMoveString ([#3](https://github.com/t0k3nr/commons/issues/3)) ([d96a4ba](https://github.com/t0k3nr/commons/commit/d96a4baa1f6195a19b92c04d530d218fa06a2eee))
* align pom.xml version with release-please manifest ([#11](https://github.com/t0k3nr/commons/issues/11)) ([acddc10](https://github.com/t0k3nr/commons/commit/acddc10ed66a64faeb69869b445039f088b4e9de))
* disable snapshot versioning in release-please config ([#9](https://github.com/t0k3nr/commons/issues/9)) ([a154fd6](https://github.com/t0k3nr/commons/commit/a154fd61ce7539c931f78b5ae6ee04de7b0dd314))
* overhaul release workflow - switch to simple release type and decouple Maven publish ([#15](https://github.com/t0k3nr/commons/issues/15)) ([b78395c](https://github.com/t0k3nr/commons/commit/b78395ca078f69425b4ee59fcdfa984c2933d40b))
* remove old Gitea repo and skip GPG signing in CI ([#4](https://github.com/t0k3nr/commons/issues/4)) ([90c613e](https://github.com/t0k3nr/commons/commit/90c613e5d5050c52aa82fc984d1df09c9568e767))
* remove snapshot key from release-please config ([#13](https://github.com/t0k3nr/commons/issues/13)) ([3c1f0b0](https://github.com/t0k3nr/commons/commit/3c1f0b0a1a11b36f557886a7670efed9927faea7))
