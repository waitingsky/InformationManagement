package com.java1234.model;

import java.util.Date;

public class SystemConfig implements ISystemConfig {

	private int configId;
	private String name;
	private String content;
	private String description;
	private String options;
	private Date updateTime;

	public SystemConfig() {
		super();
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
