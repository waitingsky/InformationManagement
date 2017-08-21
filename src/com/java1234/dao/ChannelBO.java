package com.java1234.dao;

import java.sql.Connection;
import java.util.List;

import com.java1234.model.Channel;
import com.java1234.util.DbUtil;

public class ChannelBO {

	private static ChannelBO instance;

	private ChannelBO() {
	}

	public static ChannelBO getInstance() {
		if (instance == null) {
			instance = new ChannelBO();
		}
		return instance;
	}
	
	DbUtil dbUtil=new DbUtil();

	public List<Channel> list() throws Exception {
		Connection con=null;
		try{
			con=dbUtil.getCon();
			return (new ChannelDao()).list(con);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public Channel getById(String channelId) throws Exception {
		Connection con=null;
		try{
			con=dbUtil.getCon();
			return (new ChannelDao()).getByChannelId(con, channelId);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public int save( Channel channel) throws Exception {
		Connection con=null;
		try{
			con=dbUtil.getCon();
			return (new ChannelDao()).save(con, channel);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return 0;
	}

	public int update(Channel channel) throws Exception {
		Connection con=null;
		try{
			con=dbUtil.getCon();
			return (new ChannelDao()).update(con, channel);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return 0;
	}

	public int deleteById(String channelId) throws Exception {
		Connection con=null;
		try{
			con=dbUtil.getCon();
			return (new ChannelDao()).deleteById(con, channelId);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return 0;
	}
}
