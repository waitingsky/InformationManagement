package com.java1234.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.java1234.dao.ApplicationBO;
import com.java1234.dao.ChannelBO;
import com.java1234.dao.InformationBO;
import com.java1234.model.Channel;
import com.java1234.util.NavUtil;
import com.java1234.util.ResponseUtil;
import com.java1234.util.StringUtil;

import net.sf.json.JSONObject;

public class ChannelServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String action=request.getParameter("action");
		if("edit".equals(action)){
			this.edit(request, response);
		}else if("save".equals(action)){
			this.save(request, response);
		}else if("adminList".equals(action)){
			this.adminList(request, response);
		}else if("delete".equals(action)){
			this.delete(request, response);
		}
		
	}
	
	private void edit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String channelId=request.getParameter("channelId");
		try{
			if(StringUtil.isNotEmpty(channelId)){
				Channel channel=ChannelBO.getInstance().getById( channelId);
				request.setAttribute("channel", channel);
			}
			
			if(StringUtil.isNotEmpty(channelId)){
				request.setAttribute("navCode", NavUtil.genNewsManageNavigation("资讯频道管理", "资讯频道修改"));
			}else{
				request.setAttribute("navCode", NavUtil.genNewsManageNavigation("资讯频道管理", "资讯频道添加"));
			}
			request.setAttribute("mainPage", IUrl.ADMIN_TYPE_EDIT);
			request.getRequestDispatcher(IUrl.ADMIN_MAIN).forward(request, response);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void save(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String channelId=request.getParameter("channelId");
		String channelName=request.getParameter("channelName");
		
		Channel channel=new Channel(channelName);
		
		if(StringUtil.isNotEmpty(channelId)){
			channel.setChannelId(Integer.parseInt(channelId));
		}
		try{
			if(StringUtil.isNotEmpty(channelId)){
				ChannelBO.getInstance().update(channel);
			}else{
				ChannelBO.getInstance().save(channel);
			}
			ApplicationBO.getInstance().refreshApplication(request.getSession().getServletContext());
			request.getRequestDispatcher("/channel?action=adminList").forward(request, response);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	private void adminList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try{
			List<Channel> channelList=ChannelBO.getInstance().list();
			request.setAttribute("channelList", channelList);
			request.setAttribute("navCode", NavUtil.genNewsManageNavigation("资讯频道管理", "资讯频道维护"));
			
			request.setAttribute("mainPage", IUrl.ADMIN_TYPE_LIST);
			request.getRequestDispatcher(IUrl.ADMIN_MAIN).forward(request, response);

		}catch(Exception e){
			e.printStackTrace();
		}		
	}
	
	private void delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String channelId=request.getParameter("channelId");
		try{
			JSONObject result=new JSONObject();
			boolean exist=InformationBO.getInstance().existWithChannelId( channelId);
			if(exist){
				result.put("errorMsg", "该资讯频道下有资讯，不能删除此资讯频道");
			}else{
				int delNums=ChannelBO.getInstance().deleteById(channelId);
				
				if(delNums>0){
					result.put("success", true);
					ApplicationBO.getInstance().refreshApplication(request.getSession().getServletContext());
				}else{
					result.put("errorMsg", "删除失败");
				}
			}
			ResponseUtil.write(result, response);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
