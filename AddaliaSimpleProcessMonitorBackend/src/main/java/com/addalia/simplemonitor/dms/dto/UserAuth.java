package com.addalia.simplemonitor.dms.dto;

public class UserAuth {

	private String user;
	private String pass;

	public UserAuth() {

	}
	public UserAuth(String user, String pass) {
		this.user = user;
		this.pass = pass;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}
}