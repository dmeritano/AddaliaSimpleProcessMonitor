package com.addalia.simplemonitor.exceptions;

import org.springframework.http.HttpStatus;

public class BusinessLogicException extends RuntimeException {

	private String code;
	private HttpStatus status;

	public BusinessLogicException(String code, HttpStatus status, String message) {
		super(message);
		this.code = code;
		this.status = status;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	private static final long serialVersionUID = -8609840306890786212L;

}
