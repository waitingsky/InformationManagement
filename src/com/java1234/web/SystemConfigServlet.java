package com.java1234.web;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.java1234.dao.InformationDao;
import com.java1234.dao.ChannelDao;
import com.java1234.dao.SystemConfigDao;
import com.java1234.model.Information;
import com.java1234.model.Channel;
import com.java1234.model.PageBean;
import com.java1234.model.SystemConfig;
import com.java1234.util.DateUtil;
import com.java1234.util.DbUtil;
import com.java1234.util.NavUtil;
import com.java1234.util.PageUtil;
import com.java1234.util.PropertiesUtil;
import com.java1234.util.ResponseUtil;
import com.java1234.util.StringUtil;

import net.sf.json.JSONObject;

public class SystemConfigServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	DbUtil dbUtil=new DbUtil();
	InformationDao newsDao=new InformationDao();
	ChannelDao newsTypeDao=new ChannelDao();
	
	SystemConfigDao systemConfigDao = new SystemConfigDao();
	
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
		if("config".equals(action)){
			this.newsBackList(request, response);
		}else if("configSave".equals(action)){
			this.newsSave(request, response);
		}
//		else if("show".equals(action)){
//			this.newsShow(request, response);
//		}else if("preSave".equals(action)){
//			this.newsPreSave(request, response);
//		}else if("save".equals(action)){
//			this.newsSave(request, response);
//		}else if("backList".equals(action)){
//			this.newsBackList(request,response);
//		}else if("delete".equals(action)){
//			this.newsDelete(request,response);
//		}
		
	}
	
	private void newsSave(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		FileItemFactory factory=new DiskFileItemFactory();
		ServletFileUpload upload=new ServletFileUpload(factory);
		List<FileItem> items=null;
		try {
			items=upload.parseRequest(request);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Iterator itr=items.iterator();
//		News news=new News();
		SystemConfig config = new SystemConfig();
		while(itr.hasNext()){
			FileItem item=(FileItem) itr.next();
			if(item.isFormField()){
				String fieldName=item.getFieldName();
				if("serviceSupport".equals(fieldName)){
					if(StringUtil.isNotEmpty(item.getString("utf-8"))){
						config.getMap().put(SystemConfig.CONFIG_SERVICES_AND_SUPPORT, item.getString("utf-8"));
//						news.setNewsId(Integer.parseInt(item.getString("utf-8")));
					}
				}
//				if("title".equals(fieldName)){
//					news.setTitle(item.getString("utf-8"));
//				}
//				if("content".equals(fieldName)){
//					news.setContent(item.getString("utf-8"));
//				}
//				if("author".equals(fieldName)){
//					news.setAuthor(item.getString("utf-8"));
//				}
//				if("typeId".equals(fieldName)){
//					news.setTypeId(Integer.parseInt(item.getString("utf-8")));
//				}
//				if("isHead".equals(fieldName)){
//					news.setIsHead(Integer.parseInt(item.getString("utf-8")));
//				}
//				if("isImage".equals(fieldName)){
//					news.setIsImage(Integer.parseInt(item.getString("utf-8")));
//				}
//				if("isHot".equals(fieldName)){
//					news.setIsHot(Integer.parseInt(item.getString("utf-8")));
//				}
//				if("imageName".equals(fieldName)&&news.getImageName()==null){
//					if(StringUtil.isNotEmpty(item.getString("utf-8"))){
//						news.setImageName(item.getString("utf-8").split("/")[1]);
//					}
//				}
			}else if(!"".equals(item.getName())){
//				try {
//					String imageName=DateUtil.getCurrentDateStr();
//					news.setImageName(imageName+"."+item.getName().split("\\.")[1]);
//					String filePath=PropertiesUtil.getValue("imagePath")+imageName+"."+item.getName().split("\\.")[1];
//					item.write(new File(filePath));
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
			}
		}
		
		
		Connection con=null;
		try{
			con=dbUtil.getCon();
			systemConfigDao.saveConfig(con, config);
			
//			if(news.getNewsId()!=0){
//				newsDao.newsUpdate(con, news);
//			}else{
//				newsDao.newsAdd(con, news);
//			}
			request.getRequestDispatcher("/config?action=config").forward(request, response);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void newsBackList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection con=null;
		try{
			con=dbUtil.getCon();
			SystemConfig config = systemConfigDao.loadConfig(con);
			
			request.setAttribute("config", config);
			request.setAttribute("navCode", NavUtil.genNewsManageNavigation("系统管理", "系统配置"));
			request.setAttribute("mainPage", "/background/system/systemConfig.jsp");
			request.getRequestDispatcher("/background/mainTemp.jsp").forward(request, response);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void newsPreSave(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String newsId=request.getParameter("newsId");
		Connection con=null;
		try{
			con=dbUtil.getCon();
			if(StringUtil.isNotEmpty(newsId)){
				Information news=newsDao.getByInformationId(con, newsId);
				request.setAttribute("news", news);
			}
			List<Channel> newsTypeList=newsTypeDao.newsTypeList(con);
			request.setAttribute("newsTypeList", newsTypeList);
			if(StringUtil.isNotEmpty(newsId)){
				request.setAttribute("navCode", NavUtil.genNewsManageNavigation("资讯管理", "资讯修改"));				
			}else{
				request.setAttribute("navCode", NavUtil.genNewsManageNavigation("资讯管理", "资讯添加"));				
			}
			request.setAttribute("mainPage", "/background/news/newsSave.jsp");
			request.getRequestDispatcher("/background/mainTemp.jsp").forward(request, response);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void newsList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String typeId=request.getParameter("typeId");
		String page=request.getParameter("page");
		if(StringUtil.isEmpty(page)){
			page="1";
		}
		Connection con=null;
		Information s_news=new Information();
		if(StringUtil.isNotEmpty(typeId)){
			s_news.setTypeId(Integer.parseInt(typeId));
		}
		try{
			con=dbUtil.getCon();
			int total=newsDao.newsCount(con, s_news,null,null);
			PageBean pageBean=new PageBean(Integer.parseInt(page),Integer.parseInt(PropertiesUtil.getValue("pageSize")));
			List<Information> newestNewsListWithType=newsDao.list(con, s_news, pageBean,null,null);
			request.setAttribute("newestNewsListWithType", newestNewsListWithType);
			request.setAttribute("navCode", NavUtil.genNewsListNavigation(newsTypeDao.getNewsTypeById(con, typeId).getChannelName(), typeId));
			request.setAttribute("pageCode", PageUtil.getUpAndDownPagation(total, Integer.parseInt(page), Integer.parseInt(PropertiesUtil.getValue("pageSize")), typeId));
			System.out.println(PageUtil.getUpAndDownPagation(total, Integer.parseInt(page), Integer.parseInt(PropertiesUtil.getValue("pageSize")), typeId));
			
			request.setAttribute("mainPage", "news/newsList.jsp");
			request.getRequestDispatcher("foreground/newsTemp.jsp").forward(request, response);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void newsShow(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String newsId=request.getParameter("newsId");
		Connection con=null;
		try{
			con=dbUtil.getCon();
			newsDao.click(con, newsId);
			Information news=newsDao.getByInformationId(con, newsId);
//			Comment s_comment=new Comment();
//			s_comment.setNewsId(Integer.parseInt(newsId));
//			List<Comment> commentList=commentDao.commentList(con, s_comment,null,null,null);
//			request.setAttribute("commentList", commentList);
			request.setAttribute("news", news);
			request.setAttribute("pageCode", this.genUpAndDownPageCode(newsDao.getUpAndDownPageId(con, newsId)));
			request.setAttribute("navCode", NavUtil.genNewsNavigation(news.getChannelName(), news.getTypeId()+"",news.getTitle()));
			request.setAttribute("mainPage", "news/newsShow.jsp");
			request.getRequestDispatcher("foreground/newsTemp.jsp").forward(request, response);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private String genUpAndDownPageCode(List<Information> upAndDownPage){
		Information upNews=upAndDownPage.get(0);
		Information downNews=upAndDownPage.get(1);
		StringBuffer pageCode=new StringBuffer();
		if(upNews.getNewsId()==-1){
			pageCode.append("<p>上一篇：没有了</p>");
		}else{
			pageCode.append("<p>上一篇：<a href='news?action=show&newsId="+upNews.getNewsId()+"'>"+upNews.getTitle()+"</a></p>");
		}
		if(downNews.getNewsId()==-1){
			pageCode.append("<p>下一篇：没有了</p>");
		}else{
			pageCode.append("<p>下一篇：<a href='news?action=show&newsId="+downNews.getNewsId()+"'>"+downNews.getTitle()+"</a></p>");
		}
		return pageCode.toString();
	}
	
	
	
	
//	private void newsSave(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		FileItemFactory factory=new DiskFileItemFactory();
//		ServletFileUpload upload=new ServletFileUpload(factory);
//		List<FileItem> items=null;
//		try {
//			items=upload.parseRequest(request);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		Iterator itr=items.iterator();
//		News news=new News();
//		while(itr.hasNext()){
//			FileItem item=(FileItem) itr.next();
//			if(item.isFormField()){
//				String fieldName=item.getFieldName();
//				if("newsId".equals(fieldName)){
//					if(StringUtil.isNotEmpty(item.getString("utf-8"))){
//						news.setNewsId(Integer.parseInt(item.getString("utf-8")));
//					}
//				}
//				if("title".equals(fieldName)){
//					news.setTitle(item.getString("utf-8"));
//				}
//				if("content".equals(fieldName)){
//					news.setContent(item.getString("utf-8"));
//				}
//				if("author".equals(fieldName)){
//					news.setAuthor(item.getString("utf-8"));
//				}
//				if("typeId".equals(fieldName)){
//					news.setTypeId(Integer.parseInt(item.getString("utf-8")));
//				}
//				if("isHead".equals(fieldName)){
//					news.setIsHead(Integer.parseInt(item.getString("utf-8")));
//				}
//				if("isImage".equals(fieldName)){
//					news.setIsImage(Integer.parseInt(item.getString("utf-8")));
//				}
//				if("isHot".equals(fieldName)){
//					news.setIsHot(Integer.parseInt(item.getString("utf-8")));
//				}
//				if("imageName".equals(fieldName)&&news.getImageName()==null){
//					if(StringUtil.isNotEmpty(item.getString("utf-8"))){
//						news.setImageName(item.getString("utf-8").split("/")[1]);
//					}
//				}
//			}else if(!"".equals(item.getName())){
//				try {
//					String imageName=DateUtil.getCurrentDateStr();
//					news.setImageName(imageName+"."+item.getName().split("\\.")[1]);
//					String filePath=PropertiesUtil.getValue("imagePath")+imageName+"."+item.getName().split("\\.")[1];
//					item.write(new File(filePath));
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		}
//		
//		
//		Connection con=null;
//		try{
//			con=dbUtil.getCon();
//			if(news.getNewsId()!=0){
//				newsDao.newsUpdate(con, news);
//			}else{
//				newsDao.newsAdd(con, news);
//			}
//			request.getRequestDispatcher("/news?action=backList").forward(request, response);
//		}catch(Exception e){
//			e.printStackTrace();
//		}finally{
//			try {
//				dbUtil.closeCon(con);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//	}
	

	
//	private void newsDelete(HttpServletRequest request,
//			HttpServletResponse response) throws ServletException, IOException{
//		String newsId=request.getParameter("newsId");
//		Connection con=null;
//		boolean delFlag;
//		try{
//			con=dbUtil.getCon();
//			int delNums=newsDao.newsDelete(con, newsId);
//			if(delNums==1){
//				delFlag=true;
//			}else{
//				delFlag=false;
//			}
//			ResponseUtil.write(delFlag, response);
//		}catch(Exception e){
//			e.printStackTrace();
//		}finally{
//			try {
//				dbUtil.closeCon(con);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//	}
	
	private void newsDelete(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		String newsIds=request.getParameter("newsIds");
		Connection con=null;
		boolean delFlag;
		try{
			con=dbUtil.getCon();
			JSONObject result=new JSONObject();
			int delNums=newsDao.deleteById(con, newsIds);
			if(delNums>0){
				result.put("success", true);
				result.put("delNums", delNums);
			}else{
				result.put("errorMsg", "删除失败");
			}
			ResponseUtil.write(result, response);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
//	private void newsBatchDelete(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		String commentIds=request.getParameter("newsIds");
//		Connection con=null;
//		try{
//			con=dbUtil.getCon();
//			JSONObject result=new JSONObject();
//			int delNums=commentDao.commentDelete(con, commentIds);
//			if(delNums>0){
//				result.put("success", true);
//				result.put("delNums", delNums);
//			}else{
//				result.put("errorMsg", "删除失败");
//			}
//			ResponseUtil.write(result, response);
//		}catch(Exception e){
//			e.printStackTrace();
//		}finally{
//			try {
//				dbUtil.closeCon(con);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//	}

}
