package com.addalia.simplemonitor.services.dto;

import java.util.List;

public class DmsConfig {

	private Integer configId;
	private String dmsName;
	private String dmsUrl;
	private String dmsUser;
	private String dmsPass;
	private List<DmsQuery> queries;

	public DmsConfig() {
	}

	public Integer getConfigId() {
		return configId;
	}

	public void setConfigId(Integer configId) {
		this.configId = configId;
	}

	public String getDmsName() {
		return dmsName;
	}

	public void setDmsName(String dmsName) {
		this.dmsName = dmsName;
	}

	public String getDmsUrl() {
		return dmsUrl;
	}

	public void setDmsUrl(String dmsUrl) {
		this.dmsUrl = dmsUrl;
	}

	public String getDmsUser() {
		return dmsUser;
	}

	public void setDmsUser(String dmsUser) {
		this.dmsUser = dmsUser;
	}

	public String getDmsPass() {
		return dmsPass;
	}

	public void setDmsPass(String dmsPass) {
		this.dmsPass = dmsPass;
	}

	public List<DmsQuery> getQueries() {
		return queries;
	}

	public void setQueries(List<DmsQuery> queries) {
		this.queries = queries;
	}

}
