package com.java1234.web;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.java1234.dao.ChannelBO;
import com.java1234.dao.InformationBO;
import com.java1234.dao.SystemConfigDao;
import com.java1234.model.Channel;
import com.java1234.model.Information;
import com.java1234.model.SystemConfig;
import com.java1234.util.DbUtil;
import com.java1234.util.StringUtil;

public class IndexServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	DbUtil dbUtil = new DbUtil();
	SystemConfigDao configDao = new SystemConfigDao();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");

		Connection con = null;
		try {
			con = dbUtil.getCon();

			List<Channel> channelList = ChannelBO.getInstance().list();

			// String sql = "select * from t_news where isHead=1 order by
			// publishDate desc limit 0,1 ";
			List<Information> headInfoList = InformationBO.getInstance().listHead(1);

			if (headInfoList != null && headInfoList.size() > 0) {
				Information headInfo = headInfoList.get(0);
				headInfo.setContent(StringUtil.Html2Text(headInfo.getContent()));
				request.setAttribute("headInfo", headInfo);
			}

			// sql = "select * from t_news where isHot=1 order by publishDate
			// desc limit 0,8 ";
			List<Information> hotInfoList = InformationBO.getInstance().listHot(8);
			request.setAttribute("hotInfoList", hotInfoList);

			List<List<Information>> allIndexInfoList = new ArrayList<List<Information>>();
			if (channelList != null && channelList.size() != 0) {
				for (int i = 0; i < channelList.size(); i++) {
					Channel newsType = channelList.get(i);
					// String sql = "select * from t_news,t_newsType where
					// typeId=newsTypeId and typeId="
					// + newsType.getChannelId() + " order by publishDate desc
					// limit 0,8";
					List<Information> oneSubList = InformationBO.getInstance().listByChannelId(newsType.getChannelId(),
							8);
					allIndexInfoList.add(oneSubList);
				}
			}
			request.setAttribute("allIndexInfoList", allIndexInfoList);

//			SystemConfig config = configDao.loadConfig(con);
//			request.setAttribute("config", config);

			request.getRequestDispatcher("index.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
