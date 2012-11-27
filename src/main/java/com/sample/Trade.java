package com.sample;

public class Trade {

	private String reference;
	private String stock;
	private String countryOfExecution;
	private double stockAmount;
	private double cashAmount;
	private String counterparty;

	public Trade(String reference, String stock, String counterparty, String countryOfExecution, double stockAmount, double cashAmount) {
		this.reference = reference;
		this.counterparty = counterparty;
		this.stock = stock;
		this.countryOfExecution = countryOfExecution;
		this.stockAmount = stockAmount;
		this.cashAmount = cashAmount;
	}

	public String getCounterparty() {
		return counterparty;
	}

	public String getStock() {
		return stock;
	}

	public void setStock(String stock) {
		this.stock = stock;
	}

	public String getCountryOfExecution() {
		return countryOfExecution;
	}

	public void setCountryOfExecution(String countryOfExecution) {
		this.countryOfExecution = countryOfExecution;
	}

	public TradeKey getKey(){
		return new TradeKey(this);
	}

	public String getReference() {
		return reference;
	}
}
