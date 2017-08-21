package com.java1234.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.jstl.core.Config;

import com.java1234.model.Information;
import com.java1234.model.PageBean;
import com.java1234.model.SystemConfig;
import com.java1234.util.DateUtil;
import com.java1234.util.PropertiesUtil;
import com.java1234.util.StringUtil;

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

	public int saveConfig(Connection con,SystemConfig config)throws Exception{
		
		String sql="select configId,config,configValue from sys_portalconfig";
		PreparedStatement pstmt=con.prepareStatement(sql);
		ResultSet rs=pstmt.executeQuery();
		HashMap<String, String> map = new HashMap<String, String>();
		while(rs.next()){
			map.put(rs.getString("config"), rs.getString("configValue"));
		}
		pstmt.close();
		
		Iterator<String> iterator = config.getMap().keySet().iterator();
		while(iterator.hasNext()){
			String key = iterator.next();
			if(map.containsKey(key)){
				sql="update sys_portalconfig set configValue=? where config=?";
				pstmt=con.prepareStatement(sql);
				pstmt.setString(1, config.getMap().get(key));
				pstmt.setString(2, key);
			}else{
				sql="insert into sys_portalconfig values(null,?,?)";
				pstmt=con.prepareStatement(sql);
				pstmt.setString(1, key);
				pstmt.setString(2, config.getMap().get(key));
			}
			pstmt.executeUpdate();
			pstmt.close();
			
		}
		
		return 1;
	}
	
	public SystemConfig loadConfig(Connection con)throws Exception{
		String sql="select configId,config,configValue from sys_portalconfig";
		PreparedStatement pstmt=con.prepareStatement(sql);
		ResultSet rs=pstmt.executeQuery();
		SystemConfig config=new SystemConfig();
		HashMap<String, String> map = new HashMap<String, String>();
		while(rs.next()){
			map.put(rs.getString("config"), rs.getString("configValue"));
		}
		config.setMap(map);
//		if(rs.next()){
//			config.setConfigId(rs.getInt("configId"));
//			config.setTitle(rs.getString("title"));
//			config.setContent(rs.getString("content"));
//		}
		return config;
	}

	
	public List<Information> newsList(Connection con,String sql)throws Exception{
		List<Information> newsList=new ArrayList<Information>();
		PreparedStatement pstmt=con.prepareStatement(sql);
		ResultSet rs=pstmt.executeQuery();
		while(rs.next()){
			Information news=new Information();
			news.setNewsId(rs.getInt("newsId"));
			news.setTitle(rs.getString("title"));
			news.setContent(rs.getString("content"));
			news.setPublishDate(DateUtil.formatString(rs.getString("publishDate"), "yyyy-MM-dd HH:mm:ss"));
			news.setAuthor(rs.getString("author"));
			news.setTypeId(rs.getInt("typeId"));
			news.setClick(rs.getInt("click"));
			news.setIsHead(rs.getInt("isHead"));
			news.setImageName(PropertiesUtil.getValue("userImage")+rs.getString("imageName"));
			news.setIsHot(rs.getInt("isHot"));
			newsList.add(news);
		}
		return newsList;
	}
	
	public List<Information> newsList(Connection con,Information s_news,PageBean pageBean,String s_bPublishDate,String s_aPublishDate)throws Exception{
		List<Information> newsList=new ArrayList<Information>();
		StringBuffer sb=new StringBuffer("select * from t_news t1,t_newsType t2 where t1.typeId=t2.newsTypeId ");
		if(s_news.getTypeId()!=-1){
			sb.append(" and t1.typeId="+s_news.getTypeId());
		}
		if(StringUtil.isNotEmpty(s_news.getTitle())){
			sb.append(" and t1.title like '%"+s_news.getTitle()+"%'");
		}
		if(StringUtil.isNotEmpty(s_bPublishDate)){
			sb.append(" and TO_DAYS(t1.publishDate)>=TO_DAYS('"+s_bPublishDate+"')");
		}
		if(StringUtil.isNotEmpty(s_aPublishDate)){
			sb.append(" and TO_DAYS(t1.publishDate)<=TO_DAYS('"+s_aPublishDate+"')");
		}
		sb.append(" order by t1.publishDate desc ");
		if(pageBean!=null){
			sb.append(" limit "+pageBean.getStart()+","+pageBean.getPageSize());
		}
		PreparedStatement pstmt=con.prepareStatement(sb.toString());
		ResultSet rs=pstmt.executeQuery();
		while(rs.next()){
			Information news=new Information();
			news.setNewsId(rs.getInt("newsId"));
			news.setTitle(rs.getString("title"));
			news.setContent(rs.getString("content"));
			news.setPublishDate(DateUtil.formatString(rs.getString("publishDate"), "yyyy-MM-dd HH:mm:ss"));
			news.setAuthor(rs.getString("author"));
			news.setTypeId(rs.getInt("typeId"));
			news.setChannelName(rs.getString("typeName"));
			news.setClick(rs.getInt("click"));
			news.setIsHead(rs.getInt("isHead"));
			news.setImageName(PropertiesUtil.getValue("userImage")+rs.getString("imageName"));
			news.setIsHot(rs.getInt("isHot"));
			newsList.add(news);
		}
		return newsList;
	}
	
	public int newsCount(Connection con,Information s_news,String s_bPublishDate,String s_aPublishDate)throws Exception{
		StringBuffer sb=new StringBuffer("select count(*) as total from t_news");
		if(s_news.getTypeId()!=-1){
			sb.append(" and typeId="+s_news.getTypeId());
		}
		if(StringUtil.isNotEmpty(s_news.getTitle())){
			sb.append(" and title like '%"+s_news.getTitle()+"%'");
		}
		if(StringUtil.isNotEmpty(s_bPublishDate)){
			sb.append(" and TO_DAYS(publishDate)>=TO_DAYS('"+s_bPublishDate+"')");
		}
		if(StringUtil.isNotEmpty(s_aPublishDate)){
			sb.append(" and TO_DAYS(publishDate)<=TO_DAYS('"+s_aPublishDate+"')");
		}
		PreparedStatement pstmt=con.prepareStatement(sb.toString().replaceFirst("and", "where"));
		ResultSet rs=pstmt.executeQuery();
		if(rs.next()){
			return rs.getInt("total");
		}else{
			return 0;
		}
	}
	
	public Information getNewsById(Connection con,String newsId)throws Exception{
		String sql="select * from t_news t1,t_newsType t2 where t1.typeId=t2.newsTypeId and t1.newsId=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, newsId);
		ResultSet rs=pstmt.executeQuery();
		Information news=new Information();
		if(rs.next()){
			news.setNewsId(rs.getInt("newsId"));
			news.setTitle(rs.getString("title"));
			news.setContent(rs.getString("content"));
			news.setPublishDate(DateUtil.formatString(rs.getString("publishDate"), "yyyy-MM-dd HH:mm:ss"));
			news.setAuthor(rs.getString("author"));
			news.setChannelName(rs.getString("typeName"));
			news.setTypeId(rs.getInt("typeId"));
			news.setClick(rs.getInt("click"));
			news.setIsHead(rs.getInt("isHead"));
			news.setIsImage(rs.getInt("isImage"));
			news.setImageName(PropertiesUtil.getValue("userImage")+rs.getString("imageName"));
			news.setIsHot(rs.getInt("isHot"));
		}
		return news;
	}
	
	public int newsClick(Connection con,String newsId)throws Exception{
		String sql="update t_news set click=click+1 where newsId=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, newsId);
		return pstmt.executeUpdate();
	}
	
	public List<Information> getUpAndDownPageId(Connection con,String newsId)throws Exception{
		List<Information> upAndDownPage=new ArrayList<Information>();
		String sql="SELECT * FROM t_news WHERE newsId<? ORDER BY newsId DESC LIMIT 1;";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, newsId);
		ResultSet rs=pstmt.executeQuery();
		if(rs.next()){
			upAndDownPage.add(new Information(rs.getInt("newsId"),rs.getString("title")));
		}else{
			upAndDownPage.add(new Information(-1,""));
		}
		
		sql="SELECT * FROM t_news WHERE newsId>? ORDER BY newsId ASC LIMIT 1;";
		pstmt=con.prepareStatement(sql);
		pstmt.setString(1, newsId);
		rs=pstmt.executeQuery();
		if(rs.next()){
			upAndDownPage.add(new Information(rs.getInt("newsId"),rs.getString("title")));
		}else{
			upAndDownPage.add(new Information(-1,""));
		}
		return upAndDownPage;
	}
	
	
	public boolean existNewsWithNewsTypeId(Connection con,String typeId)throws Exception{
		String sql="select * from t_news where typeId=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, typeId);
		ResultSet rs=pstmt.executeQuery();
		if(rs.next()){
			return true;
		}else{
			return false;
		}
	}
	
	
	public int newsUpdate(Connection con,Information news)throws Exception{
		String sql="update t_news set title=?,content=?,author=?,typeId=?,isHead=?,isImage=?,imageName=?,isHot=? where newsId=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, news.getTitle());
		pstmt.setString(2, news.getContent());
		pstmt.setString(3, news.getAuthor());
		pstmt.setInt(4, news.getTypeId());
		pstmt.setInt(5, news.getIsHead());
		pstmt.setInt(6, news.getIsImage());
		pstmt.setString(7, news.getImageName());
		pstmt.setInt(8, news.getIsHot());
		pstmt.setInt(9, news.getInformationId());
		return pstmt.executeUpdate();
	}
	

//	public int newsDelete(Connection con,String newsId)throws Exception{
//		String sql="delete from t_news where newsId=?";
//		PreparedStatement pstmt=con.prepareStatement(sql);
//		pstmt.setString(1, newsId);
//		return pstmt.executeUpdate();
//	}
	
	public int newsDelete(Connection con,String newsIds)throws Exception{
		String sql="delete from t_news where newsId in ("+newsIds+")";;
		PreparedStatement pstmt=con.prepareStatement(sql);
//		pstmt.setString(1, newsId);
		return pstmt.executeUpdate();
	}

	
//	public int newsBatchDelete(Connection con,String commentIds)throws Exception{
//		String sql="delete from t_comment where commentId in ("+commentIds+")";
//		PreparedStatement pstmt=con.prepareStatement(sql);
//		return pstmt.executeUpdate();
//	}

}