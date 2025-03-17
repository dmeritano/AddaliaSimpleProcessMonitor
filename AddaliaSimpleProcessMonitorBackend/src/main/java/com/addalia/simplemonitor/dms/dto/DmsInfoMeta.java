package com.addalia.simplemonitor.dms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DmsInfoMeta {

	@JsonProperty("server_time")
	private String serverTime;
	private String name;
	private String platform;

	public String getServerTime() {
		return serverTime;
	}

	public void setServerTime(String serverTime) {
		this.serverTime = serverTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

}
