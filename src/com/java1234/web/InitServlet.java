package com.java1234.web;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.java1234.dao.ApplicationBO;
import com.java1234.util.ResponseUtil;

import net.sf.json.JSONObject;

public class InitServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		ServletContext application=config.getServletContext();
		ApplicationBO.getInstance().refreshApplication(application);
//		this.refreshSystem(application);
	}
	
//	private void refreshSystem(ServletContext application){
//		try{
//			List<Channel> newsTypeList=ChannelBO.getInstance().list();
//			application.setAttribute("newsTypeList", newsTypeList);
//			
//			List<Information> newestNewsList=InformationBO.getInstance().listLatest(8);
//			application.setAttribute("newestNewsList", newestNewsList);
//			
//			List<Information> hotNewsList=InformationBO.getInstance().listHot(8);
//			application.setAttribute("hotNewsList", hotNewsList);
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//	}
	
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session=request.getSession();
		ServletContext application=session.getServletContext();
//		this.refreshSystem(application);
		ApplicationBO.getInstance().refreshApplication(application);
		JSONObject result=new JSONObject();
		result.put("success", true);
		try {
			ResponseUtil.write(result, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
