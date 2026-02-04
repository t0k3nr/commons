package org.uche.t0ken.commons.svc;

import java.util.List;

import org.graalvm.collections.Pair;
import org.uche.t0ken.commons.enums.StatGranularity;

public interface TimeInterface {
	public List<StatGranularity> getLiveGranularities();
	public List<StatGranularity> getStatGranularities();
	public List<StatGranularity> getSimulatorGranularities();
	public List<StatGranularity> getWtBuilderGranularities();
	public List<StatGranularity> getGranularities(boolean live);
    
	public Pair<StatGranularity, StatGranularity>  divideDoubleSg(StatGranularity sg, double div, boolean live);
	public Pair<StatGranularity, StatGranularity>  multiplyDoubleSg(StatGranularity sg, double div, boolean live);
	public StatGranularity getNextLiveGranularity(StatGranularity from, StatGranularity to,
			StatGranularity current, boolean increase);
	public StatGranularity getNextSimulatorGranularity(StatGranularity from, StatGranularity to,
			StatGranularity current, boolean increase);
	
	public StatGranularity getMinSg();
	public StatGranularity getNextSg(StatGranularity from, StatGranularity to,
			StatGranularity current, boolean increase, boolean live);
}

