package com.bankFinal;

public class Deal {
	private String dealnum;
	private String kind;
	private String content;
	private int amount;
	private int balance;
	private String dealdate;
	private String faccountnum;
	public String getDealnum() {
		return dealnum;
	}
	public void setDealnum(String dealnum) {
		this.dealnum = dealnum;
	}
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int i) {
		this.amount = i;
	}
	public int getBalance() {
		return balance;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}
	public String getDealdate() {
		return dealdate;
	}
	public void setDealdate(String dealdate) {
		this.dealdate = dealdate;
	}
	public String getFaccountnum() {
		return faccountnum;
	}
	public void setFaccountnum(String faccountnum) {
		this.faccountnum = faccountnum;
	}
	
	
}
