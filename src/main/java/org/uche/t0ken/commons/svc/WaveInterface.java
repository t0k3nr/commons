package org.uche.t0ken.commons.svc;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.NavigableMap;

import org.uche.t0ken.commons.enums.StatGranularity;
import org.uche.t0ken.commons.vo.StatVO;

public interface WaveInterface {
	public TimeInterface getTimeService();
	public Boolean inject(Instant now, BigDecimal bid, BigDecimal ask,
			NavigableMap<StatGranularity, StatVO> moves, boolean live, Boolean clue);
}

