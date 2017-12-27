package com.wesine.model;

import java.util.Date;

public class Event {

	
	private String id;
	
	private String status;
	
	private String result;
	
	private Date date;
	
	private String TransID;
	
	private String counterName;
	
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getTransID() {
		return TransID;
	}

	public void setTransID(String transID) {
		TransID = transID;
	}

	public String getCounterName() {
		return counterName;
	}

	public void setCounterName(String counterName) {
		this.counterName = counterName;
	}

	public Event(String id, String status, String result, Date date, String transID, String counterName) {
		super();
		this.id = id;
		this.status = status;
		this.result = result;
		this.date = date;
		TransID = transID;
		this.counterName = counterName;
	}
	
	
	
	
	
	
	
}
