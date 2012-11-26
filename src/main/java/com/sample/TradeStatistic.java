package com.sample;

import java.util.HashSet;
import java.util.Set;

public class TradeStatistic {
	
	private TradeKey tradeKey;
	private int count;
	private Set<String> tradeReferenceSet;
	
	public TradeStatistic(TradeKey tradeKey){
		this.tradeKey = tradeKey;
		count = 0;
		tradeReferenceSet = new HashSet<String>();
	}
	
	public void incrementCount(){
		count++;
	}
	
	public int getCount(){
		return count;
	}
	
	public TradeKey getTradeKey(){
		return tradeKey;
	}
	
	public boolean containsTrade(String tradeReference){
		return tradeReferenceSet.contains(tradeReference);
	}
	
	public void addTrade(Trade trade){
		tradeReferenceSet.add(trade.getReference());
		count++;
	}

	@Override
	public boolean equals(Object that) {
		return tradeKey.equals(that);
	}

	@Override
	public int hashCode() {
		return tradeKey.hashCode();
	}
}
