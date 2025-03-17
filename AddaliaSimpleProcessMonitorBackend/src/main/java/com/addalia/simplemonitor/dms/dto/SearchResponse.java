package com.addalia.simplemonitor.dms.dto;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SearchResponse {

	@JsonProperty("meta")
	private SearchResponseMeta meta;

	@JsonProperty("docs")
	private ArrayList<SearchResponseDoc> docs = new ArrayList<>();

	public SearchResponseMeta getMeta() {
		return meta;
	}

	public void setMeta(SearchResponseMeta meta) {
		this.meta = meta;
	}

	public ArrayList<SearchResponseDoc> getDocs() {
		return docs;
	}

	public void setDocs(ArrayList<SearchResponseDoc> docs) {
		this.docs = docs;
	}

}
