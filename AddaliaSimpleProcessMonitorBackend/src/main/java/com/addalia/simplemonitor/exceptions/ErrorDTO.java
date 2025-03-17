package com.addalia.simplemonitor.exceptions;

import java.io.Serializable;

public class ErrorDTO implements Serializable {

	private String code = "500";
	private String message = "Undefinded";

	public ErrorDTO() {}
	
	public ErrorDTO(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public ErrorDTO(String message) {
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

	@Override
	public String toString() {
		return "ErrorDTO [code=" + code + ", message=" + message + "]";
	}

	private static final long serialVersionUID = -5732906396723519887L;

}
