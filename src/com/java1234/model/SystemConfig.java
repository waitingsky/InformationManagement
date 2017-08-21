package com.java1234.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;

public class SystemConfig {

	public final static String CONFIG_SERVICES_AND_SUPPORT = "Services and support";

	private int configId;
	private String title;
	private String content;

	private HashMap<String, String> map = new HashMap<String, String>();

	// private Date publishDate;
	// private String author;
	// private int typeId=-1;
	// private String typeName;
	// private int click;
	// private int isHead;
	// private int isImage;
	// private String imageName;
	// private int isHot;

	public SystemConfig() {
		super();
	}

	public String getServiceSupport() {
		if(map.containsKey(SystemConfig.CONFIG_SERVICES_AND_SUPPORT)){
			return map.get(SystemConfig.CONFIG_SERVICES_AND_SUPPORT);
		}
		
		return null;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getConfigId() {
		return configId;
	}

	public void setConfigId(int configId) {
		this.configId = configId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public HashMap<String, String> getMap() {
		return map;
	}

	public void setMap(HashMap<String, String> map) {
		this.map = map;
	}

}
