package com.wesine.model;

import java.util.List;

/*
 * 从dvs传过来的交易数据
 */
public class TransData {
	
	private String id;//后台生成唯一uuid
	
	private int Priority;

	private String ShopID;
	
	private long TsStart;
	
	private long TsEnd;
	
	private String RegID;
	
	private String CashierID;
	
	private String CustomerID;
	
	private String TransID;
	
	private String ScriptVer;
	
	
	private List<Bill> billList;


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public int getPriority() {
		return Priority;
	}


	public void setPriority(int priority) {
		Priority = priority;
	}


	public String getShopID() {
		return ShopID;
	}


	public void setShopID(String shopID) {
		ShopID = shopID;
	}


	public long getTsStart() {
		return TsStart;
	}


	public void setTsStart(long tsStart) {
		TsStart = tsStart;
	}


	public long getTsEnd() {
		return TsEnd;
	}


	public void setTsEnd(long tsEnd) {
		TsEnd = tsEnd;
	}


	public String getRegID() {
		return RegID;
	}


	public void setRegID(String regID) {
		RegID = regID;
	}


	public String getCashierID() {
		return CashierID;
	}


	public void setCashierID(String cashierID) {
		CashierID = cashierID;
	}


	public String getCustomerID() {
		return CustomerID;
	}


	public void setCustomerID(String customerID) {
		CustomerID = customerID;
	}


	public String getTransID() {
		return TransID;
	}


	public void setTransID(String transID) {
		TransID = transID;
	}


	public String getScriptVer() {
		return ScriptVer;
	}


	public void setScriptVer(String scriptVer) {
		ScriptVer = scriptVer;
	}


	public List<Bill> getBillList() {
		return billList;
	}


	public void setBillList(List<Bill> billList) {
		this.billList = billList;
	}


	public TransData(String id, int priority, String shopID, long tsStart, long tsEnd, String regID, String cashierID,
			String customerID, String transID, String scriptVer, List<Bill> billList) {
		super();
		this.id = id;
		Priority = priority;
		ShopID = shopID;
		TsStart = tsStart;
		TsEnd = tsEnd;
		RegID = regID;
		CashierID = cashierID;
		CustomerID = customerID;
		TransID = transID;
		ScriptVer = scriptVer;
		this.billList = billList;
	}


	public TransData(int priority, String shopID, long tsStart, long tsEnd, String regID, String cashierID,
			String customerID, String transID, String scriptVer, List<Bill> billList) {
		super();
		Priority = priority;
		ShopID = shopID;
		TsStart = tsStart;
		TsEnd = tsEnd;
		RegID = regID;
		CashierID = cashierID;
		CustomerID = customerID;
		TransID = transID;
		ScriptVer = scriptVer;
		this.billList = billList;
	}
	
	
	
	
	
	
	
}
