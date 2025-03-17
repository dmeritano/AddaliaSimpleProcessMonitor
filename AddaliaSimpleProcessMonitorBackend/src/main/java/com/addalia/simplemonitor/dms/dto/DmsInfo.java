package com.addalia.simplemonitor.dms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DmsInfo {

	@JsonProperty("meta")
	private DmsInfoMeta meta;

	public DmsInfoMeta getMeta() {
		return meta;
	}

	public void setMeta(DmsInfoMeta meta) {
		this.meta = meta;
	}

}
