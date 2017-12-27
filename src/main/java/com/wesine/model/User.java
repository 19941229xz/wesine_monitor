package com.wesine.model;

public class User {

	private String id;

	private String userName;

	private String password;

	private String authorityId;

	private String phone;

	private String email;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAuthorityId() {
		return authorityId;
	}

	public void setAuthorityId(String authorityId) {
		this.authorityId = authorityId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public User(String id, String userName, String password, String authorityId, String phone, String email) {
		super();
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.authorityId = authorityId;
		this.phone = phone;
		this.email = email;
	}
	
	
	
	

}
