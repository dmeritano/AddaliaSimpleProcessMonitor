package com.addalia.simplemonitor.dms.dto;

import java.util.ArrayList;

public class DmsDocumentType {

	private ArrayList<DmsDocumentTypeAttribute> attributes = new ArrayList<>();
	private DmsDocumentTypeMeta meta;

	public ArrayList<DmsDocumentTypeAttribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(ArrayList<DmsDocumentTypeAttribute> attributes) {
		this.attributes = attributes;
	}

	public DmsDocumentTypeMeta getMeta() {
		return meta;
	}

	public void setMeta(DmsDocumentTypeMeta meta) {
		this.meta = meta;
	}

}
