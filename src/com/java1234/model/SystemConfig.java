package com.java1234.model;

import java.util.Date;

public class SystemConfig implements ISystemConfig{

	private int configId;
	private String name;
	private String content;
	private String options;
	private Date updateTime;
	 
//	private HashMap<String, String> map = new HashMap<String, String>();


	public SystemConfig() {
		super();
	}

//	public String getServiceSupport() {
//		if(map.containsKey(SystemConfig.CONFIG_SERVICES_AND_SUPPORT)){
//			return map.get(SystemConfig.CONFIG_SERVICES_AND_SUPPORT);
//		}
//		
//		return null;
//	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getConfigId() {
		return configId;
	}

	public void setConfigId(int configId) {
		this.configId = configId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

//	public HashMap<String, String> getMap() {
//		return map;
//	}
//
//	public void setMap(HashMap<String, String> map) {
//		this.map = map;
//	}

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getContent() {
		return content;
	}
}
