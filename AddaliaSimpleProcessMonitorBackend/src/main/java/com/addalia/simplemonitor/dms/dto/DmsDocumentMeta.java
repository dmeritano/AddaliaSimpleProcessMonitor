package com.addalia.simplemonitor.dms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DmsDocumentMeta {

	private String type;
	private boolean item;

	@JsonProperty("mime-type")
	private String mimeType; //Only returned in response when item=true

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isItem() {
		return item;
	}

	public void setItem(boolean item) {
		this.item = item;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

}
