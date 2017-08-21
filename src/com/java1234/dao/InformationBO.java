package com.java1234.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import com.java1234.model.Information;
import com.java1234.model.PageBean;
import com.java1234.util.DbUtil;

public class InformationBO {

	private static InformationBO instance;

	private InformationBO() {
	}

	public static InformationBO getInstance() {
		if (instance == null) {
			instance = new InformationBO();
		}
		return instance;
	}

	DbUtil dbUtil = new DbUtil();

	public List<Information> listLatest(int rowSize) throws Exception {
		Connection con = null;
		try {
			con = dbUtil.getCon();
			String sql = "select * from t_news order by publishDate desc";
			if (rowSize > 0) {
				sql += " limit 0, " + rowSize;
			}
			return (new InformationDao()).list(con, sql);
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

	public List<Information> listPopular(int rowSize) throws Exception {
		Connection con = null;
		try {
			con = dbUtil.getCon();
			String sql = "select * from t_news order by click desc";
			if (rowSize > 0) {
				sql += " limit 0, " + rowSize;
			}
			return (new InformationDao()).list(con, sql);
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
	
	public List<Information> listHead(int rowSize) throws Exception {
		Connection con = null;
		try {
			con = dbUtil.getCon();
			
			String sql = "select * from t_news where isHead=1 order by publishDate desc";
			if (rowSize > 0) {
				sql += "  limit 0, " + rowSize;
			}
			return (new InformationDao()).list(con, sql);
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
	
	public List<Information> listHot(int rowSize) throws Exception {
		Connection con = null;
		try {
			con = dbUtil.getCon();
			
			String sql = "select * from t_news where isHot=1 order by publishDate desc";
			if (rowSize > 0) {
				sql += "  limit 0, " + rowSize;
			}
			return (new InformationDao()).list(con, sql);
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


	public List<Information> list(Information s_news, PageBean pageBean, String s_bPublishDate, String s_aPublishDate)
			throws Exception {
		Connection con = null;
		try {
			con = dbUtil.getCon();
			return (new InformationDao()).list(con, s_news, pageBean, s_bPublishDate, s_aPublishDate);
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
	
	public List<Information> listByChannelId(int channelId,int rowSize)
			throws Exception {
		Connection con = null;
		try {
			con = dbUtil.getCon();
			String sql = "select * from t_news,t_newsType where typeId=newsTypeId and typeId="
					+ channelId + " order by publishDate desc ";
			if (rowSize > 0) {
				sql += "  limit 0, " + 8;
			}

			return (new InformationDao()).list(con, sql);
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
	
	public int count(Information s_news, String s_bPublishDate, String s_aPublishDate) throws Exception {
		Connection con = null;
		try {
			con = dbUtil.getCon();
			return (new InformationDao()).count(con, s_news, s_bPublishDate, s_aPublishDate);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return 0;
	}

	public Information getById(String informationId) throws Exception {
		Connection con = null;
		try {
			con = dbUtil.getCon();
			return (new InformationDao()).getById(con, informationId);
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

	public int click(String informationId) throws Exception {
		Connection con = null;
		try {
			con = dbUtil.getCon();
			return (new InformationDao()).click(con, informationId);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return 0;
	}

	public List<Information> getUpAndDownPageId(String informationId) throws Exception {
		Connection con = null;
		try {
			con = dbUtil.getCon();
			return (new InformationDao()).getUpAndDownPageId(con, informationId);
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

	public boolean existWithChannelId(String typeId) throws Exception {
		DbUtil dbUtil = new DbUtil();
		Connection con = null;
		try {
			con = dbUtil.getCon();

			String sql = "select * from t_news where typeId=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, typeId);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;

	}

	public int save(Information news) throws Exception {
		Connection con = null;
		try {
			con = dbUtil.getCon();
			return (new InformationDao()).save(con, news);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return 0;
	}

	public int update(Information news) throws Exception {
		Connection con = null;
		try {
			con = dbUtil.getCon();
			return (new InformationDao()).update(con, news);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return 0;
	}

	public int deleteById(String informationIds) throws Exception {
		Connection con = null;
		try {
			con = dbUtil.getCon();
			return (new InformationDao()).deleteById(con, informationIds);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return 0;
	}

}