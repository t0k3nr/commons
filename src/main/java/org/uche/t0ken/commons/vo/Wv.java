package org.uche.t0ken.commons.vo;

import java.time.Instant;
import java.util.List;

import org.graalvm.collections.Pair;
import org.jboss.logging.Logger;
import org.uche.t0ken.commons.enums.StatGranularity;

public class Wv {

	private final static Logger logger = Logger.getLogger("Wv");

	private StatVO left;
	private StatVO action;
	private StatVO right;
	
	private OverState leftOs = OverState.NONE;
	private OverState actionOs = OverState.NONE;
	private OverState rightOs = OverState.NONE;
	
	private Instant now;
	
	private static String DOUBLE_FORMAT = "%7.2f";
	
	
	public Wv now(Instant now) {
		this.now = now;
		return this;
	}
	public Wv left(StatVO left) {
		this.left = left;
		return this;
	}
	public Wv action(StatVO action) {
		this.action = action;
		return this;
	}
	public Wv right(StatVO right) {
		this.right = right;
		return this;
	}
	
	public Wv leftOs(OverState leftOs) {
		this.leftOs = leftOs;
		return this;
	}
	public Wv actionOs(OverState actionOs) {
		this.actionOs = actionOs;
		return this;
	}
	public Wv rightOs(OverState rightOs) {
		this.rightOs = rightOs;
		return this;
	}
	
	
	public StatVO getLeft() {
		return left;
	}
	public void setLeft(StatVO left) {
		this.left = left;
	}
	public StatVO getAction() {
		return action;
	}
	public void setAction(StatVO action) {
		this.action = action;
	}
	public StatVO getRight() {
		return right;
	}
	public void setRight(StatVO right) {
		this.right = right;
	}
	public OverState getLeftOs() {
		return leftOs;
	}
	public void setLeftOs(OverState leftOs) {
		this.leftOs = leftOs;
	}
	public OverState getActionOs() {
		return actionOs;
	}
	public void setActionOs(OverState actionOs) {
		this.actionOs = actionOs;
	}
	public OverState getRightOs() {
		return rightOs;
	}
	public void setRightOs(OverState rightOs) {
		this.rightOs = rightOs;
	}

	public double width() {
		return left.getSg().getIndex().doubleValue() / right.getSg().getIndex().doubleValue();
	}
	
	public String toString() {
		
		boolean trendSign = left.getWt().doubleValue()>=0;
		
		StringBuffer frLog = new StringBuffer();
		
		/*
		 * GENERAL
		 */
		frLog.append("\nFR: ");
		frLog.append(now);
		
		if (trendSign) {
			frLog.append("   🟢 ");
			
		} else {
			frLog.append("   🔴 ");
		}
		
		frLog.append("width: ").append(String.format(DOUBLE_FORMAT, this.width()));
		
		/*
		 * LEFT
		 */
		frLog.append("\nFR: ");
		frLog.append(now);
		
		
		if (leftOs.equals(OverState.WAIT)) {
			frLog.append(" ⚠️️ ️");
		} else {
			frLog.append("   ️️");
		}
		
		if (trendSign) {
			frLog.append("🟢 ").append(left.toShortMoveString());
			
		} else {
			frLog.append("🔴 ").append(left.toShortMoveString());
		}
		
		
		/*
		 * ACTION
		 */
		
		frLog.append("\nFR: ");
		frLog.append(now);
		
		if (action != null) {
			switch (actionOs) {
			
			case OK: {
				frLog.append(" 〽️");
				break;
			}
			case WAIT: {
				frLog.append(" ⚠️️ ️");
				break;
			}
			default: {
				frLog.append("   ️️");
			}
			}	
			
			if (trendSign) {
				frLog.append("🟢 ").append(action.toShortMoveString());
				
			} else {
				frLog.append("🔴 ").append(action.toShortMoveString());
			}
		} else {
			if (trendSign) {
				frLog.append( "   🟩 ");
				
			} else {
				frLog.append( "   🟥 " );
			}
		}
		
		/*
		 * RIGHT
		 * 
		 */
		
		frLog.append("\nFR: ");
		frLog.append(now);
		
		if (trendSign) {
			frLog.append("   🟢 ");
			
		} else {
			frLog.append("   🔴 ");
		}
		
		
		frLog.append(right.toShortMoveString());
		
		frLog.append("\nFR: ");
		frLog.append(now);
		frLog.append(" -------");

		return frLog.toString();
	}
	public Instant getNow() {
		return now;
	}
	public void setNow(Instant now) {
		this.now = now;
	}
	
	public static String logWvs(List<Wv> wvs) {
		StringBuffer frLog = new StringBuffer();
		frLog.append("\nFR: ------------------------------------------------------------------ ");
		
		for (Wv wv: wvs) {
			frLog.append(wv.toString());
		}
		
		return frLog.toString();
		
	}
	public boolean sell() {
		return left.getWt().signum()>=0;
	}
	
	public static double widthSum(Wv first, Wv last) {
		double retval = first.getLeft().getSg().getIndex().doubleValue() / last.getRight().getSg().getIndex().doubleValue();
		logger.info("widthSum: " + retval + " first: " + first.getLeft().getSg() + " last: " + last.getRight().getSg());
		return retval;
	}
}
