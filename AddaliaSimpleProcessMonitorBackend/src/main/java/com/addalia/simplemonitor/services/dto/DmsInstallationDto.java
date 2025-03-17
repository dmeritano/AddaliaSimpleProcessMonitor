package com.addalia.simplemonitor.services.dto;

import java.util.ArrayList;
import java.util.List;

public class DmsInstallationDto {

	private Integer id;
	private String installationName;
	private List<DmsInstallationQuery> queries;

	public DmsInstallationDto() {
		this.queries = new ArrayList<>();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getInstallationName() {
		return installationName;
	}

	public void setInstallationName(String installationName) {
		this.installationName = installationName;
	}

	public List<DmsInstallationQuery> getQueries() {
		return queries;
	}

	public void setQueries(List<DmsInstallationQuery> queries) {
		this.queries = queries;
	}

}
