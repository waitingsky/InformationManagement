package com.java1234.web;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
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
import com.java1234.util.DateUtil;
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
			List<Information> infoListWithChannel = InformationBO.getInstance().list(information, pageBean, null, null);
			request.setAttribute("infoListWithChannel", infoListWithChannel);
			request.setAttribute("navCode", NavUtil
					.genNewsListNavigation(ChannelBO.getInstance().getById(channelId).getChannelName(), channelId));
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
			
			if(information.getType()==0){
				request.setAttribute("information", information);
				request.setAttribute("pageCode",
						this.genUpAndDownPageCode(InformationBO.getInstance().getUpAndDownPageId(informationId)));
				request.setAttribute("navCode", NavUtil.genNewsNavigation(information.getChannelName(),
						information.getChannelId() + "", information.getTitle()));
				request.setAttribute("mainPage", "news/newsShow.jsp");
				request.getRequestDispatcher("foreground/newsTemp.jsp").forward(request, response);
			}else if(information.getType()==1){
				RequestDispatcher dispatcher = request.getRequestDispatcher("/file?action=fileDownload&fileName="+information.getContent());
				dispatcher.forward(request, response);
			}else if(information.getType()==2){
//				RequestDispatcher dispatcher = request.getRequestDispatcher(information.getContent());
//				dispatcher.forward(request, response);
				response.sendRedirect(information.getContent());
			}
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

		try {
			List<FileItem> items = upload.parseRequest(request);
			if (items != null) {
				HashMap<String, String> map = new HashMap<String, String>();
				HashMap<String, FileItem> fileMap = new HashMap<String, FileItem>();

				Iterator<FileItem> iterator = items.iterator();

				while (iterator.hasNext()) {
					FileItem item = iterator.next();
					if (item.isFormField()) {
						map.put(item.getFieldName(), item.getString("utf-8"));
					} else {
						String itemName = item.getName();
						if (StringUtil.isNotEmpty(itemName)) {

							// String fileName = item.getName();// 文件名称
							// System.out.println("原文件名：" + fileName);//
							// Koala.jpg
							//
							// String suffix =
							// fileName.substring(fileName.lastIndexOf('.'));
							// System.out.println("扩展名：" + suffix);// .jpg
							//
							// // 新文件名（唯一）
							// String newFileName = new Date().getTime() +
							// suffix;
							// System.out.println("新文件名：" + newFileName);//
							// image\1478509873038.jpg
							//
							// // 5. 调用FileItem的write()方法，写入文件
							// String imageName = DateUtil.getCurrentDateStr();
							// String filePath =
							// PropertiesUtil.getValue("imagePath") +
							// newFileName;
							//
							// File file = new File(filePath);
							// System.out.println(file.getAbsolutePath());
							// item.write(file);
							//
							// // 6. 调用FileItem的delete()方法，删除临时文件
							// item.delete();
							//
							//// map.put(item.getFieldName(), item.getName());
							// map.put(item.getFieldName(), newFileName);

							fileMap.put(itemName, item);
						}
					}
				}

				Information information = new Information();

				if (StringUtil.isNotEmpty(map.get("informationId"))) {
					information.setInformationId(Integer.parseInt(map.get("informationId")));
				}

				information.setTitle(map.get("title"));

				information.setAuthor(map.get("author"));
				information.setChannelId(Integer.parseInt(map.get("channelId")));

				information.setHead(Integer.parseInt(map.get("isHead")) == 1 ? true : false);
				information.setHot(Integer.parseInt(map.get("isHot")) == 1 ? true : false);
				information.setType(Integer.parseInt(map.get("optionsRadios")));

				if (information.getType() == 0) {
					information.setContent(map.get("content"));

					for (String key : fileMap.keySet()) {
						if (key != null && key.startsWith("file")) {
							saveFile(fileMap.get(key));
						}
					}

				} else if (information.getType() == 1) {
					String newFileName = saveFile(fileMap.get("docFile"));
					information.setContent(newFileName);
				} else if (information.getType() == 2) {
					information.setContent(map.get("infoLink"));
				}

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

	private String saveFile(FileItem item) throws Exception {
		if (item != null) {
			String fileName = item.getName();// 文件名称
			System.out.println("原文件名：" + fileName);// Koala.jpg

			String suffix = fileName.substring(fileName.lastIndexOf('.'));
			System.out.println("扩展名：" + suffix);// .jpg

			// 新文件名（唯一）
			String newFileName = new Date().getTime() + suffix;
			System.out.println("新文件名：" + newFileName);// image\1478509873038.jpg

			// 5. 调用FileItem的write()方法，写入文件
			String imageName = DateUtil.getCurrentDateStr();
			String filePath = PropertiesUtil.getValue("imagePath") + newFileName;

			File file = new File(filePath);
			System.out.println(file.getAbsolutePath());
			item.write(file);

			// 6. 调用FileItem的delete()方法，删除临时文件
			item.delete();

			return newFileName;
		}
		return null;
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
