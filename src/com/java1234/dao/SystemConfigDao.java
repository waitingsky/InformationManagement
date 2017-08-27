package com.java1234.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.java1234.model.SystemConfig;
import com.java1234.util.DateUtil;

public class SystemConfigDao {

	public List<SystemConfig> list(Connection con) throws Exception {
		List<SystemConfig> result = new ArrayList<SystemConfig>();

		String sql = "select config_id,name,content,description,options,update_time from sys_portalconfig";
		PreparedStatement pstmt = con.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		SystemConfig tmp = null;
		while (rs.next()) {
			tmp = new SystemConfig();

			tmp.setConfigId(rs.getInt(1));
			tmp.setName(rs.getString(2));
			tmp.setContent(rs.getString(3));

			tmp.setDescription(rs.getString(4));
			tmp.setOptions(rs.getString(5));
			tmp.setUpdateTime(DateUtil.formatString(rs.getString(6), "yyyy-MM-dd HH:mm:ss"));

			result.add(tmp);
		}

		return result;
	}

	public int save(Connection con, List<SystemConfig> configs) throws Exception {
		String updateSql = "update sys_portalconfig set content=?,description=?,options=?,update_time=now() where config_id=? and name=?";
		// String insertSql = "insert into
		// sys_portalconfig(config_id,name,content,description,options,update_time)
		// values(?,?,?,?,?,?) ";
		String insertSql = "insert into sys_portalconfig(config_id,name,content,description,options,update_time) values(?,?,?,?,?,now()) ";
		PreparedStatement pstmt = null;

		int result = 0;
		if (configs != null) {
			for (SystemConfig config : configs) {
				if(config.getConfigId()!=0){
					pstmt = con.prepareStatement(updateSql);

					pstmt.setString(1, config.getContent());
					pstmt.setString(2, config.getDescription());
					pstmt.setString(3, config.getOptions());

					// pstmt.setDate(4, config.getUpdateTime());
					pstmt.setInt(4, config.getConfigId());
					pstmt.setString(5, config.getName());

					int updateCount = pstmt.executeUpdate();
					result += updateCount;

					pstmt.close();
					pstmt = null;
				}else{
					pstmt = con.prepareStatement(insertSql);

					// config_id,name,content,description,options,update_time
					pstmt.setInt(1, config.getConfigId());
					pstmt.setString(2, config.getName());
					pstmt.setString(3, config.getContent());

					pstmt.setString(4, config.getDescription());
					pstmt.setString(5, config.getOptions());
					// pstmt.setDate(6, config.getUpdateTime());

					int insertCount = pstmt.executeUpdate();
					result += insertCount;

					pstmt.close();
					pstmt = null;
				}
			}
		}

		return result;
	}

//	public int save(Connection con, List<SystemConfig> configs) throws Exception {
//		String updateSql = "update sys_portalconfig set content=?,description=?,options=?,update_time=now() where config_id=? and name=?";
//		// String insertSql = "insert into
//		// sys_portalconfig(config_id,name,content,description,options,update_time)
//		// values(?,?,?,?,?,?) ";
//		String insertSql = "insert into sys_portalconfig(config_id,name,content,description,options,update_time) values(?,?,?,?,?,now()) ";
//		PreparedStatement pstmt = null;
//
//		int result = 0;
//		if (configs != null) {
//			for (SystemConfig config : configs) {
//				pstmt = con.prepareStatement(updateSql);
//
//				pstmt.setString(1, config.getContent());
//				pstmt.setString(2, config.getDescription());
//				pstmt.setString(3, config.getOptions());
//
//				// pstmt.setDate(4, config.getUpdateTime());
//				pstmt.setInt(4, config.getConfigId());
//				pstmt.setString(5, config.getName());
//
//				int updateCount = pstmt.executeUpdate();
//				result += updateCount;
//
//				pstmt.close();
//				pstmt = null;
//
//				if (updateCount <= 0) {
//					pstmt = con.prepareStatement(insertSql);
//
//					// config_id,name,content,description,options,update_time
//					pstmt.setInt(1, config.getConfigId());
//					pstmt.setString(2, config.getName());
//					pstmt.setString(3, config.getContent());
//
//					pstmt.setString(4, config.getDescription());
//					pstmt.setString(5, config.getOptions());
//					// pstmt.setDate(6, config.getUpdateTime());
//
//					int insertCount = pstmt.executeUpdate();
//					result += insertCount;
//
//					pstmt.close();
//					pstmt = null;
//				}
//			}
//		}
//
//		return result;
//	}
}