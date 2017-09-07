package com.java1234.model;

import java.util.HashMap;
import java.util.List;

public class SystemConfigs implements ISystemConfig {

	private HashMap<String, SystemConfig> map = new HashMap<String, SystemConfig>();

	public SystemConfigs(List<SystemConfig> list) {
		// this.list = list;
		if (list != null) {
			for (SystemConfig tmp : list) {
				map.put(tmp.getName(), tmp);
			}
		}
	}

	public SystemConfig getServiceSupport() {
		if (map.containsKey(SystemConfigs.CONFIG_MODULE_SERVICES)) {
			return map.get(SystemConfigs.CONFIG_MODULE_SERVICES);
		}

		return new SystemConfig();
	}

	public SystemConfig getCustomSystem1() {
		if (map.containsKey(SystemConfigs.CONFIG_MODULE_SYSTEM1)) {
			return map.get(SystemConfigs.CONFIG_MODULE_SYSTEM1);
		}

		return new SystemConfig();
	}

	public SystemConfig getCustomSystem2() {
		if (map.containsKey(SystemConfigs.CONFIG_MODULE_SYSTEM2)) {
			return map.get(SystemConfigs.CONFIG_MODULE_SYSTEM2);
		}

		return new SystemConfig();
	}

	public SystemConfig getCustomSystem3() {
		if (map.containsKey(SystemConfigs.CONFIG_MODULE_SYSTEM3)) {
			return map.get(SystemConfigs.CONFIG_MODULE_SYSTEM3);
		}

		return new SystemConfig();
	}

	public SystemConfig getCustomSystem4() {
		if (map.containsKey(SystemConfigs.CONFIG_MODULE_SYSTEM4)) {
			return map.get(SystemConfigs.CONFIG_MODULE_SYSTEM4);
		}

		return new SystemConfig();
	}

	public SystemConfig getImageLogo() {
		if (map.containsKey(SystemConfigs.CONFIG_MODULE_LOGO)) {
			return map.get(SystemConfigs.CONFIG_MODULE_LOGO);
		}

		return new SystemConfig();
	}
}
