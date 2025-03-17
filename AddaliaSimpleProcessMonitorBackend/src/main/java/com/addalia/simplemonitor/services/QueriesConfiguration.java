package com.addalia.simplemonitor.services;

import java.util.List;

import com.addalia.simplemonitor.services.dto.DmsConfig;

public class QueriesConfiguration {

	private List<DmsConfig> dmsList;

	public QueriesConfiguration() {
	}

	public List<DmsConfig> getDmsList() {
		return dmsList;
	}

	public void setDmsList(List<DmsConfig> dmsList) {
		this.dmsList = dmsList;
	}
		
}
