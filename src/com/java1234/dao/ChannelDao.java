package com.java1234.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.java1234.model.Channel;

public class ChannelDao {

	public List<Channel> list(Connection con)throws Exception{
		List<Channel> channelList=new ArrayList<Channel>();
		String sql="select * from t_newsType";
		PreparedStatement pstmt=con.prepareStatement(sql);
		ResultSet rs=pstmt.executeQuery();
		while(rs.next()){
			Channel channel=new Channel();
			channel.setChannelId(rs.getInt("newsTypeId"));
			channel.setChannelName(rs.getString("typeName"));
			channelList.add(channel);
		}
		return channelList;
	}
	
	public Channel getByChannelId(Connection con,String channelId)throws Exception{
		Channel channel=new Channel();
		String sql="select * from t_newsType where newsTypeId=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, channelId);
		ResultSet rs=pstmt.executeQuery();
		if(rs.next()){
			channel.setChannelId(rs.getInt("newsTypeId"));
			channel.setChannelName(rs.getString("typeName"));
		}
		return channel;
	}
	
	public int save(Connection con,Channel channel)throws Exception{
		String sql="insert into t_newsType values(null,?)";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, channel.getChannelName());
		return pstmt.executeUpdate();
	}
	
	public int update(Connection con,Channel channel)throws Exception{
		String sql="update t_newsType set typeName=? where newsTypeId=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, channel.getChannelName());
		pstmt.setInt(2, channel.getChannelId());
		return pstmt.executeUpdate();
	}
	
	public int deleteById(Connection con,String channelId)throws Exception{
		String sql="delete from t_newsType where newsTypeId=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, channelId);
		return pstmt.executeUpdate();
	}
}
