package com.sample;

public class TradeKey {

	private String stock;
	private String counterparty;
	private String countryOfExecution;

	public TradeKey(String stock, String counterparty, String countryOfExecution) {
		this.stock = stock;
		this.counterparty = counterparty;
		this.countryOfExecution = countryOfExecution;
	}
	
	public TradeKey(Trade trade) {
		this(trade.getStock(), trade.getCounterparty(), trade.getCountryOfExecution());
	}

	@Override
	public boolean equals(Object object){
		if (object instanceof TradeKey){
			TradeKey that = (TradeKey) object;
			if (this.stock.equals(that.stock) &&
				this.counterparty.equals(that.counterparty) &&
				this.countryOfExecution.equals(that.countryOfExecution)){
				return true;
			}
		}
		return false;
	}
	
	@Override
	public int hashCode(){
		return (this.stock + this.counterparty + this.countryOfExecution).hashCode();
	}
	
	public boolean partialMatch(TradeKey that) {
		if (this.equals(that)){
			return true;
		}
		
		if (that.getStock() != null){
			if (!that.getStock().equals(this.getStock())){
				return false;
			}
		}
		if (that.getCounterparty() != null){
			if (!that.getCounterparty().equals(this.getCounterparty())){
				return false;
			}
		}
		if (that.getCountryOfExecution() != null){
			if (!that.getCountryOfExecution().equals(this.getCountryOfExecution())){
				return false;
			}
		}
		return true;
	}


	public String getStock() {
		return stock;
	}

	public String getCounterparty() {
		return counterparty;
	}

	public String getCountryOfExecution() {
		return countryOfExecution;
	}
	
	public TradeKey getKey(){
		return this;
	}
}
