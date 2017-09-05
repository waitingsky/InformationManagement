package com.java1234.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.java1234.model.Information;
import com.java1234.model.PageBean;
import com.java1234.util.DateUtil;
import com.java1234.util.StringUtil;

public class InformationDao {

	public List<Information> list(Connection con,String sql)throws Exception{
		List<Information> newsList=new ArrayList<Information>();
		PreparedStatement pstmt=con.prepareStatement(sql);
		ResultSet rs=pstmt.executeQuery();
		while(rs.next()){
			Information news=new Information();
			news.setInformationId(rs.getInt("newsId"));
			news.setTitle(rs.getString("title"));
			news.setContent(rs.getString("content"));
			news.setPublishDate(DateUtil.formatString(rs.getString("publishDate"), "yyyy-MM-dd HH:mm:ss"));
			news.setAuthor(rs.getString("author"));
			news.setChannelId(rs.getInt("typeId"));
			news.setClickCount(rs.getInt("click"));
			news.setHead(rs.getBoolean("isHead"));
//			news.setImageName(PropertiesUtil.getValue("userImage")+rs.getString("imageName"));
			news.setHot(rs.getBoolean("isHot"));
			news.setType(rs.getInt("type"));

			newsList.add(news);
		}
		return newsList;
	}
	
	public List<Information> list(Connection con,Information s_news,PageBean pageBean,String s_bPublishDate,String s_aPublishDate)throws Exception{
		List<Information> newsList=new ArrayList<Information>();
		StringBuffer sb=new StringBuffer("select * from t_news t1,t_newsType t2 where t1.typeId=t2.newsTypeId ");
		if(s_news.getChannelId()!=-1){
			sb.append(" and t1.typeId="+s_news.getChannelId());
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
			news.setInformationId(rs.getInt("newsId"));
			news.setTitle(rs.getString("title"));
			news.setContent(rs.getString("content"));
			news.setPublishDate(DateUtil.formatString(rs.getString("publishDate"), "yyyy-MM-dd HH:mm:ss"));
			news.setAuthor(rs.getString("author"));
			news.setChannelId(rs.getInt("typeId"));
			news.setChannelName(rs.getString("typeName"));
			news.setClickCount(rs.getInt("click"));
			news.setHead(rs.getBoolean("isHead"));
			news.setHot(rs.getBoolean("isHot"));
			news.setType(rs.getInt("type"));
			
			
//			news.setClick(rs.getInt());
//			news.setIsHead(rs.getInt("isHead"));
//			news.setImageName(PropertiesUtil.getValue("userImage")+rs.getString("imageName"));
//			news.setIsHot(rs.getInt("isHot"));
			newsList.add(news);
		}
		return newsList;
	}
	
	public int count(Connection con,Information s_news,String s_bPublishDate,String s_aPublishDate)throws Exception{
		StringBuffer sb=new StringBuffer("select count(*) as total from t_news");
		if(s_news.getChannelId()!=-1){
			sb.append(" and typeId="+s_news.getChannelId());
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
	
	public Information getById(Connection con,String newsId)throws Exception{
		String sql="select newsId,title,content,publishDate,author,typeId,type,click,isHead,isHot,typeName from t_news t1,t_newsType t2 where t1.typeId=t2.newsTypeId and t1.newsId=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, newsId);
		ResultSet rs=pstmt.executeQuery();
		Information news=new Information();
		if(rs.next()){
			news.setInformationId(rs.getInt(1));
			news.setTitle(rs.getString(2));
			news.setContent(rs.getString(3));
			news.setPublishDate(DateUtil.formatString(rs.getString(4), "yyyy-MM-dd HH:mm:ss"));
			news.setAuthor(rs.getString(5));
			
			news.setChannelId(rs.getInt(6));
			news.setType(rs.getInt(7));
			news.setClickCount(rs.getInt(8));
			news.setHead(rs.getBoolean(9));
//			news.setIsImage(rs.getInt("isImage"));
//			news.setImageName(PropertiesUtil.getValue("userImage")+rs.getString("imageName"));
			news.setHot(rs.getBoolean(10));

			news.setChannelName(rs.getString(11));

		}
		return news;
	}
	
	public int click(Connection con,String newsId)throws Exception{
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
	
	
	public boolean existWithChannelId(Connection con,String typeId)throws Exception{
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
	
	public int save(Connection con,Information news)throws Exception{
		String sql="insert into t_news(newsId,title,content,publishDate,author,typeId,type,click,isHead,isHot) values(null,?,?,now(),?,?,?,?,?,?)";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, news.getTitle());
		pstmt.setString(2, news.getContent());
		pstmt.setString(3, news.getAuthor());
		
		pstmt.setInt(4, news.getChannelId());
		pstmt.setInt(5, news.getType());
		pstmt.setInt(6, news.getClickCount());
		pstmt.setBoolean(7, news.isHead());
		pstmt.setBoolean(8, news.isHot());
		
		return pstmt.executeUpdate();
	}
	
	
	 
	
	public int update(Connection con,Information news)throws Exception{
		String sql="update t_news set title=?,content=?,author=?,typeId=?,type=?,isHead=?,isHot=? where newsId=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, news.getTitle());
		pstmt.setString(2, news.getContent());
		pstmt.setString(3, news.getAuthor());
		pstmt.setInt(4, news.getChannelId());
		
		pstmt.setInt(5, news.getType());
		pstmt.setBoolean(6, news.isHead());
		pstmt.setBoolean(7, news.isHot());
		pstmt.setInt(8, news.getInformationId());
		return pstmt.executeUpdate();
	}
	

	public int deleteById(Connection con,String newsIds)throws Exception{
		String sql="delete from t_news where newsId in ("+newsIds+")";;
		PreparedStatement pstmt=con.prepareStatement(sql);
		return pstmt.executeUpdate();
	}

	

}