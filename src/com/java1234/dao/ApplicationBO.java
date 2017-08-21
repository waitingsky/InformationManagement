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

}
