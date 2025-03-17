package com.addalia.simplemonitor.services.dto;

public class DmsInstallationQuery {

	private String queryNameOrDescription;
	private Long totalDocuments;

	public DmsInstallationQuery() {
		this.totalDocuments = 0L;
	}

	public String getQueryNameOrDescription() {
		return queryNameOrDescription;
	}

	public void setQueryNameOrDescription(String queryNameOrDescription) {
		this.queryNameOrDescription = queryNameOrDescription;
	}

	public Long getTotalDocuments() {
		return totalDocuments;
	}

	public void setTotalDocuments(Long totalDocuments) {
		this.totalDocuments = totalDocuments;
	}

}
