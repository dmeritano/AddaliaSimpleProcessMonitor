package com.addalia.simplemonitor.dms.dto;

import java.util.ArrayList;

/* Objects within GET dms/types  - Gets all types with its childrens in this format*/
public class DmsDocumentTypeGetAllResponse {
	
	private String name;
	private String description;
	private boolean item;
	private ArrayList<String> children;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isItem() {
		return item;
	}
	public void setItem(boolean item) {
		this.item = item;
	}
	public ArrayList<String> getChildren() {
		return children;
	}
	public void setChildrens(ArrayList<String> children) {
		this.children = children;
	}
	
}
