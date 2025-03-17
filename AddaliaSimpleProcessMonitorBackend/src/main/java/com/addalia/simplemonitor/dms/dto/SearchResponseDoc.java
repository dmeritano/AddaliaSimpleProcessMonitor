package com.addalia.simplemonitor.dms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SearchResponseDoc {

	@JsonProperty("#Id")
	private String Id;

	private String type;

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
