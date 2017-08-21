package com.java1234.web;

import java.io.IOException;
import java.util.HashMap;
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

import com.java1234.dao.ApplicationBO;
import com.java1234.dao.ChannelBO;
import com.java1234.dao.InformationBO;
import com.java1234.model.Channel;
import com.java1234.model.Information;
import com.java1234.model.PageBean;
import com.java1234.util.NavUtil;
import com.java1234.util.PageUtil;
import com.java1234.util.PropertiesUtil;
import com.java1234.util.ResponseUtil;
import com.java1234.util.StringUtil;

import net.sf.json.JSONObject;

public class InformationServlet extends HttpServlet {

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
		String action = request.getParameter("action");
		if ("list".equals(action)) {
			this.list(request, response);
		} else if ("show".equals(action)) {
			this.show(request, response);
		} else if ("edit".equals(action)) {
			this.edit(request, response);
		} else if ("save".equals(action)) {
			this.save(request, response);
		} else if ("adminList".equals(action)) {
			this.infoAdminList(request, response);
		} else if ("delete".equals(action)) {
			this.delete(request, response);
		}

	}

	private void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String channelId = request.getParameter("channelId");
		String page = request.getParameter("page");
		if (StringUtil.isEmpty(page)) {
			page = "1";
		}
		Information information = new Information();
		if (StringUtil.isNotEmpty(channelId)) {
			information.setChannelId(Integer.parseInt(channelId));
		}
		try {
			int total = InformationBO.getInstance().count(information, null, null);
			PageBean pageBean = new PageBean(Integer.parseInt(page),
					Integer.parseInt(PropertiesUtil.getValue("pageSize")));
			List<Information> infoListWithChannel = InformationBO.getInstance().list(information, pageBean, null,
					null);
			request.setAttribute("infoListWithChannel", infoListWithChannel);
			request.setAttribute("navCode",
					NavUtil.genNewsListNavigation(ChannelBO.getInstance().getById(channelId).getChannelName(), channelId));
			request.setAttribute("pageCode", PageUtil.getUpAndDownPagation(total, Integer.parseInt(page),
					Integer.parseInt(PropertiesUtil.getValue("pageSize")), channelId));
			System.out.println(PageUtil.getUpAndDownPagation(total, Integer.parseInt(page),
					Integer.parseInt(PropertiesUtil.getValue("pageSize")), channelId));

			request.setAttribute("mainPage", "news/newsList.jsp");
			request.getRequestDispatcher("foreground/newsTemp.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void show(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String informationId = request.getParameter("informationId");
		try {
			InformationBO.getInstance().click(informationId);
			ApplicationBO.getInstance().refreshApplication(request.getSession().getServletContext());

			Information information = InformationBO.getInstance().getById(informationId);
			// Comment s_comment=new Comment();
			// s_comment.setNewsId(Integer.parseInt(newsId));
			// List<Comment> commentList=commentDao.commentList(con,
			// s_comment,null,null,null);
			// request.setAttribute("commentList", commentList);
			request.setAttribute("information", information);
			request.setAttribute("pageCode",
					this.genUpAndDownPageCode(InformationBO.getInstance().getUpAndDownPageId(informationId)));
			request.setAttribute("navCode",
					NavUtil.genNewsNavigation(information.getChannelName(), information.getChannelId() + "", information.getTitle()));
			request.setAttribute("mainPage", "news/newsShow.jsp");
			request.getRequestDispatcher("foreground/newsTemp.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String genUpAndDownPageCode(List<Information> upAndDownPage) {
		Information upNews = upAndDownPage.get(0);
		Information downNews = upAndDownPage.get(1);
		StringBuffer pageCode = new StringBuffer();
		if (upNews.getInformationId() == -1) {
			pageCode.append("<p>上一篇：没有了</p>");
		} else {
			pageCode.append("<p>上一篇：<a href='information?action=show&informationId=" + upNews.getInformationId() + "'>"
					+ upNews.getTitle() + "</a></p>");
		}
		if (downNews.getInformationId() == -1) {
			pageCode.append("<p>下一篇：没有了</p>");
		} else {
			pageCode.append("<p>下一篇：<a href='information?action=show&informationId=" + downNews.getInformationId()
					+ "'>" + downNews.getTitle() + "</a></p>");
		}
		return pageCode.toString();
	}

	private void edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String informationId = request.getParameter("informationId");
		try {
			if (StringUtil.isNotEmpty(informationId)) {
				Information information = InformationBO.getInstance().getById(informationId);
				request.setAttribute("information", information);
			}
			List<Channel> channelList = ChannelBO.getInstance().list();
			request.setAttribute("channelList", channelList);
			if (StringUtil.isNotEmpty(informationId)) {
				request.setAttribute("navCode", NavUtil.genNewsManageNavigation("资讯管理", "资讯修改"));
			} else {
				request.setAttribute("navCode", NavUtil.genNewsManageNavigation("资讯管理", "资讯添加"));
			}
			request.setAttribute("mainPage", "/background/news/newsSave.jsp");
			request.getRequestDispatcher("/background/mainTemp.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private void save(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);

		List<FileItem> items = null;
		try {
			items = upload.parseRequest(request);
			if (items != null) {
				HashMap<String, String> map = new HashMap<String, String>();

				Iterator<FileItem> iterator = items.iterator();
				
				while (iterator.hasNext()) {
					FileItem item = iterator.next();
					if (item.isFormField()) {
		                map.put(item.getFieldName(), item.getString("utf-8"));

//						String fieldName = item.getFieldName();
//						if ("informationId".equals(fieldName)) {
//							if (StringUtil.isNotEmpty(item.getString("utf-8"))) {
//								information.setInformationId(Integer.parseInt(item.getString("utf-8")));
//							}
//						}
//						if ("title".equals(fieldName)) {
//							information.setTitle(item.getString("utf-8"));
//						}
//						if ("content".equals(fieldName)) {
//							information.setContent(item.getString("utf-8"));
//						}
//						if ("author".equals(fieldName)) {
//							information.setAuthor(item.getString("utf-8"));
//						}
//						if ("channelId".equals(fieldName)) {
//							information.setChannelId(Integer.parseInt(item.getString("utf-8")));
//						}
//
//						if ("isHead".equals(fieldName)) {
//							information.setHead(Integer.parseInt(item.getString("utf-8")) == 1 ? true : false);
//						}
//						if ("isHot".equals(fieldName)) {
//							information.setHot(Integer.parseInt(item.getString("utf-8")) == 1 ? true : false);
//						}
//						if ("optionsRadios".equals(fieldName)) {
//							information.setType(Integer.parseInt(item.getString("utf-8")));
//						}
//
//						// if ("isHead".equals(fieldName)) {
//						// information.setHead(Boolean.parseBoolean(item.getString("utf-8")));
//						// }
//						// // if("isImage".equals(fieldName)){
//						// //
//						// information.setIsImage(Integer.parseInt(item.getString("utf-8")));
//						// // }
//						// if ("isHot".equals(fieldName)) {
//						// information.setHot(Boolean.parseBoolean(item.getString("utf-8")));
//						// }
//						// if("imageName".equals(fieldName)&&information.getImageName()==null){
//						// if(StringUtil.isNotEmpty(item.getString("utf-8"))){
//						// information.setImageName(item.getString("utf-8").split("/")[1]);
//						// }
//						// }
					} else  {
//						String itemName = item.getName();
//						
//						if (!"".equals(itemName)){
//							String imageName = DateUtil.getCurrentDateStr();
//							information.setImageName(imageName + "." + item.getName().split("\\.")[1]);
//							String filePath = PropertiesUtil.getValue("imagePath") + imageName + "."
//									+ item.getName().split("\\.")[1];
//							item.write(new File(filePath));
//						}
					}
				}
				
				
				Information information = new Information();
				if (information.getInformationId() != 0) {
					InformationBO.getInstance().update(information);
				} else {
					InformationBO.getInstance().save(information);
				}
				ApplicationBO.getInstance().refreshApplication(request.getSession().getServletContext());
				
				
				request.getRequestDispatcher("/information?action=adminList").forward(request, response);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void infoAdminList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String s_bPublishDate = request.getParameter("s_bPublishDate");
		String s_aPublishDate = request.getParameter("s_aPublishDate");
		String s_title = request.getParameter("s_title");
		String page = request.getParameter("page");
		HttpSession session = request.getSession();
		if (StringUtil.isEmpty(page)) {
			page = "1";
			session.setAttribute("s_bPublishDate", s_bPublishDate);
			session.setAttribute("s_aPublishDate", s_aPublishDate);
			session.setAttribute("s_title", s_title);
		} else {
			s_bPublishDate = (String) session.getAttribute("s_bPublishDate");
			s_aPublishDate = (String) session.getAttribute("s_aPublishDate");
			s_title = (String) session.getAttribute("s_title");
		}
		Information information = new Information();
		if (StringUtil.isNotEmpty(s_title)) {
			information.setTitle(s_title);
		}
		try {
			int total = InformationBO.getInstance().count(information, s_bPublishDate, s_aPublishDate);
			String pageCode = PageUtil.getPagation(request.getContextPath() + "/information?action=adminList", total,
					Integer.parseInt(page), Integer.parseInt(PropertiesUtil.getValue("backPageSize")));
			PageBean pageBean = new PageBean(Integer.parseInt(page),
					Integer.parseInt(PropertiesUtil.getValue("backPageSize")));
			List<Information> informationList = InformationBO.getInstance().list(information, pageBean, s_bPublishDate,
					s_aPublishDate);
			request.setAttribute("pageCode", pageCode);
			request.setAttribute("informationList", informationList);
			request.setAttribute("navCode", NavUtil.genNewsManageNavigation("资讯管理", "资讯维护"));
			request.setAttribute("mainPage", "/background/news/newsList.jsp");
			request.getRequestDispatcher("/background/mainTemp.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String informationIds = request.getParameter("informationIds");
		try {
			JSONObject result = new JSONObject();
			int delNums = InformationBO.getInstance().deleteById(informationIds);
			ApplicationBO.getInstance().refreshApplication(request.getSession().getServletContext());
			if (delNums > 0) {
				result.put("success", true);
				result.put("delNums", delNums);
			} else {
				result.put("errorMsg", "删除失败");
			}
			ResponseUtil.write(result, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
