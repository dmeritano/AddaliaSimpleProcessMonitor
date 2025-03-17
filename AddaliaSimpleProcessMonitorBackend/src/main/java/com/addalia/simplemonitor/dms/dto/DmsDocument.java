package com.addalia.simplemonitor.dms.dto;

import java.util.Map;

public class DmsDocument {

	private DmsDocumentMeta meta;
	private Map<String, String> attributes;

	public DmsDocumentMeta getMeta() {
		return meta;
	}

	public void setMeta(DmsDocumentMeta meta) {
		this.meta = meta;
	}

	public Map<String, String> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}

//	public boolean isItem() {
//		return meta.isItem();
//	}
//	public String getParentId() {
//		String parentId = attributes.get("#IdAgregadoSuperior"); 
//		return parentId == null ? "" : parentId;
//	}
//	public String getId() {
//		if (attributes != null) {
//			return attributes.get("#Id");
//		}else {
//			return "";
//		}
//	}
	
	
}
