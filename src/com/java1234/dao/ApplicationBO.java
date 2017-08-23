package com.java1234.dao;

import java.util.List;

import javax.servlet.ServletContext;

import com.java1234.model.Channel;
import com.java1234.model.Information;

public class ApplicationBO {
	private static ApplicationBO instance;

	private ApplicationBO() {
	}

	public static ApplicationBO getInstance() {
		if (instance == null) {
			instance = new ApplicationBO();
		}
		return instance;
	}

	public void refreshApplication(ServletContext application) {
		try {
			List<Channel> channelList = ChannelBO.getInstance().list();
			application.setAttribute("channelList", channelList);

			List<Information> latestInfoList = InformationBO.getInstance().listLatest(8);
			application.setAttribute("latestInfoList", latestInfoList);

			List<Information> popularList = InformationBO.getInstance().listPopular(8);
			application.setAttribute("popularList", popularList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void refreshAll(ServletContext application) {
		try {
			this.refreshChannelList(application);
			this.refreshLatestInfoList(application);
			this.refreshPopularList(application);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 刷新热门列表
	 * @param application
	 */
	public void refreshPopularList(ServletContext application) {
		try {
			List<Information> popularList = InformationBO.getInstance().listPopular(8);
			application.setAttribute("popularList", popularList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 刷新最近更新
	 * @param application
	 */
	public void refreshLatestInfoList(ServletContext application) {
		try {
			List<Information> latestInfoList = InformationBO.getInstance().listLatest(8);
			application.setAttribute("latestInfoList", latestInfoList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 刷新频道列表
	 * @param application
	 */
	public void refreshChannelList(ServletContext application) {
		try {
			List<Channel> channelList = ChannelBO.getInstance().list();
			application.setAttribute("channelList", channelList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
