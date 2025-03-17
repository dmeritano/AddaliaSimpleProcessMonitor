package com.addalia.simplemonitor.dms.dto;

public class SearchParameters {

	private String query;
	private int page;
	private int pageSize;
	private boolean onlyMeta;

	public SearchParameters() {
	}

	public SearchParameters(String query, int page, int pageSize, boolean onlyMeta) {
		this.query = query;
		this.page = page;
		this.pageSize = pageSize;
		this.onlyMeta = onlyMeta;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public boolean isOnlyMeta() {
		return onlyMeta;
	}

	public void setOnlyMeta(boolean onlyMeta) {
		this.onlyMeta = onlyMeta;
	}

}
