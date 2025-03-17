package com.addalia.simplemonitor.services.dto;

import java.io.Serializable;

public class MessageDTO implements Serializable {

	private String code = "0";
	private String message;

	public MessageDTO() {
	}

	public MessageDTO(String message) {
		this.message = message;
	}

	public MessageDTO(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	private static final long serialVersionUID = -7729793432758392468L;

}
