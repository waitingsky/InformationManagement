package com.java1234.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SystemConfigs implements ISystemConfig {

	private HashMap<String, SystemConfig> map = new HashMap<String, SystemConfig>();
	private List<SystemConfig> list = new ArrayList<SystemConfig>();

	// yong session来控制刷新，登录时再刷新，注销时再刷新。

	public SystemConfigs() {
		super();
	}

	public SystemConfig getServiceSupport() {
		if (map.containsKey(SystemConfigs.CONFIG_MODULE_SERVICES)) {
			return map.get(SystemConfigs.CONFIG_MODULE_SERVICES);
		}

		return null;
	}

	public HashMap<String, SystemConfig> getMap() {
		return map;
	}

	public void setMap(HashMap<String, SystemConfig> map) {
		this.map = map;
	}

	public List<SystemConfig> getList() {
		return list;
	}

	public void setList(List<SystemConfig> list) {
		this.list = list;
	}

}
