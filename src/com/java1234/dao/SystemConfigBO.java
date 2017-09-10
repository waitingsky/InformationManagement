package com.java1234.dao;

import java.sql.Connection;
import java.util.List;

import com.java1234.model.SystemConfig;
import com.java1234.util.DbUtil;

public class SystemConfigBO {

	private static SystemConfigBO instance;

	private SystemConfigBO() {
	}

	public static SystemConfigBO getInstance() {
		if (instance == null) {
			instance = new SystemConfigBO();
		}
		return instance;
	}

	DbUtil dbUtil = new DbUtil();

	public List<SystemConfig> list() throws Exception {
		Connection con = null;
		try {
			con = dbUtil.getCon();
			return (new SystemConfigDao()).list(con);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public SystemConfig get(String id) throws Exception {
		Connection con = null;
		try {
			con = dbUtil.getCon();
			return (new SystemConfigDao()).get(con,id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}


	public int save(List<SystemConfig> configs) throws Exception {
		Connection con = null;
		try {
			con = dbUtil.getCon();
			return (new SystemConfigDao()).save(con, configs);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return -1;
	}

}