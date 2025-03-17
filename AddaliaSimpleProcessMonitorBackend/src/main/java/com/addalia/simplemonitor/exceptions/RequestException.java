package com.addalia.simplemonitor.exceptions;

public class RequestException extends RuntimeException {

	private String code;

	public RequestException(String code, String message) {
		super(message);
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	private static final long serialVersionUID = -4637510864002563403L;

}
