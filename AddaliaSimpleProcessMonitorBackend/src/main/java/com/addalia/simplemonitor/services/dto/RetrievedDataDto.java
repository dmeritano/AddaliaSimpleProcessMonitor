package com.addalia.simplemonitor.services.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class RetrievedDataDto {

	@JsonIgnore
	private long lastRun; // System.currentTimeMillis()

	private List<DmsInstallationDto> services;

	public RetrievedDataDto() {
		this.services = new ArrayList<>();
	}

	public long getLastRun() {
		return lastRun;
	}

	public void setLastRun(long lastRun) {
		this.lastRun = lastRun;
	}

	public List<DmsInstallationDto> getServices() {
		return services;
	}

	public void setServices(List<DmsInstallationDto> services) {
		this.services = services;
	}

}
