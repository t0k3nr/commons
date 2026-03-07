# Changelog

## [1.16.0](https://github.com/t0k3nr/commons/compare/v1.15.0...v1.16.0) (2026-03-07)


### Features

* add setters and extend AbstractWaveService for backward compatibility ([#20](https://github.com/t0k3nr/commons/issues/20)) ([06e9ff2](https://github.com/t0k3nr/commons/commit/06e9ff26b3c117ac80093a331404bd204a160870))
* add side emojis to alignment log, comment out verbose divergent logs, bump to 1.18.0 ([#24](https://github.com/t0k3nr/commons/issues/24)) ([b406868](https://github.com/t0k3nr/commons/commit/b40686812042147d137f703d46e3bdf9b8a41295))
* alignment memory persistence in inject(), parameterless logAlignments(), bump to 1.21.0 ([#27](https://github.com/t0k3nr/commons/issues/27)) ([060d9b2](https://github.com/t0k3nr/commons/commit/060d9b295459f45c8fc47c9bc43d695b2fe5f42c))
* expose alignment/tendency getters for websocket broadcasting ([#32](https://github.com/t0k3nr/commons/issues/32)) ([360d00e](https://github.com/t0k3nr/commons/commit/360d00eb8aa06376ae83e1808d0a38d4c83cc155))
* OVER as standalone validity type, LHHL requires only increasingLows/decreasingHighs, bump to 1.20.0 ([#26](https://github.com/t0k3nr/commons/issues/26)) ([6d28871](https://github.com/t0k3nr/commons/commit/6d28871354d58e9d9bc475b793d6d7dfd297c1bb))
* rewrite tendency + replace STLDLHHL with STD4LHHL per new spec ([#31](https://github.com/t0k3nr/commons/issues/31)) ([215f902](https://github.com/t0k3nr/commons/commit/215f90213657d93c4851890f1af1870f3f2a41b2))
* tendency logic in inject(), logTendency() method, bump to 1.22.0 ([#28](https://github.com/t0k3nr/commons/issues/28)) ([c4d9df4](https://github.com/t0k3nr/commons/commit/c4d9df4f613b7aed12271698d2fc73d80fc5da8f))
* update D4OVER spec and comments, bump to 1.19.0 ([#25](https://github.com/t0k3nr/commons/issues/25)) ([1a899b1](https://github.com/t0k3nr/commons/commit/1a899b133742a739372112880234b15213003c5f))


### Bug Fixes

* add deriveIndicators() to decouple WtMinMax state from per-match indicator derivation ([#30](https://github.com/t0k3nr/commons/issues/30)) ([47dda7d](https://github.com/t0k3nr/commons/commit/47dda7dd94f7e30f9d04a5329d1b89c5727e6325))
* add null guard to highestWtAbove and highestWtBelow ([#22](https://github.com/t0k3nr/commons/issues/22)) ([0395d13](https://github.com/t0k3nr/commons/commit/0395d13f5660d5970728cfe0f0a04c99938f120e))
* null guard fixes, setter chaining, and extract logAlignments ([#23](https://github.com/t0k3nr/commons/issues/23)) ([5617cec](https://github.com/t0k3nr/commons/commit/5617cec045db607840559594785b707eecd9367a))

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
