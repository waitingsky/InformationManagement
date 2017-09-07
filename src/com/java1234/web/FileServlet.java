package com.java1234.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.java1234.util.PropertiesUtil;

public class FileServlet extends HttpServlet {

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

		if ("configLogo".equals(action)) {
			// 读取config图片
			this.configLogo(request, response);
		} else if ("ckeditorImageUpload".equals(action)) {
			// ckeditor文件上传
			this.ckeditorImageUpload(request, response);
		} else if ("ckeditorImage".equals(action)) {
			// ckeditor图片显示
			this.ckeditorImage(request, response);
		} else if ("fileDownload".equals(action)) {
			// 文件下载
			this.fileDownload(request, response);
		}
	}

	private void ckeditorImageUpload(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String callback = request.getParameter("CKEditorFuncNum");

		// response.setCharacterEncoding("GBK");
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");

		PrintWriter out = response.getWriter();
		try {
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			List<FileItem> list = upload.parseRequest(request);

			if (list == null || list.size() == 0) {
				out.println("<script type=\"text/javascript\">");
				out.println("window.parent.CKEDITOR.tools.callFunction(" + callback + ",'',"
						+ "'未获取到上传文件，请重新操作。（图片必须为.jpg/.gif/.bmp/.png文件）');");
				out.println("</script>");
				out.flush();
				return;
			}

			FileItem fileItem = list.get(0);
			String uploadContentType = fileItem.getContentType();
			String expandedName = ""; // 文件扩展名
			if (uploadContentType.equals("image/pjpeg") || uploadContentType.equals("image/jpeg")) {
				// IE6上传jpg图片的headimageContentType是image/pjpeg，而IE9以及火狐上传的jpg图片是image/jpeg
				expandedName = ".jpg";
			} else if (uploadContentType.equals("image/png") || uploadContentType.equals("image/x-png")) {
				// IE6上传的png图片的headimageContentType是"image/x-png"
				expandedName = ".png";
			} else if (uploadContentType.equals("image/gif")) {
				expandedName = ".gif";
			} else if (uploadContentType.equals("image/bmp")) {
				expandedName = ".bmp";
			} else {
				out.println("<script type=\"text/javascript\">");
				out.println("window.parent.CKEDITOR.tools.callFunction(" + callback + ",'',"
						+ "'文件格式不正确（必须为.jpg/.gif/.bmp/.png文件）');");
				out.println("</script>");
				out.flush();
				return;
			}

			String uploadPath = PropertiesUtil.getValue("imagePath");
			String fileName = java.util.UUID.randomUUID().toString(); //
			// 采用时间+UUID的方式随即命名
			fileName += expandedName;
			File toFile = new File(uploadPath, fileName);

			fileItem.write(toFile);

			// 返回“图像”选项卡并显示图片
			out.println("<script type=\"text/javascript\">");
			// out.println("window.parent.CKEDITOR.tools.callFunction(" +
			// callback + ",'" + "img/postImg/" + fileName
			// + "','')");
			out.println("window.parent.CKEDITOR.tools.callFunction(" + callback + ",'"
					+ "file?action=ckeditorImage&filename=" + fileName + "','')");
			out.println("</script>");
			out.flush();
			return;
		} catch (FileUploadException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void ckeditorImage(HttpServletRequest request, HttpServletResponse response) throws IOException {

		FileInputStream fis = null;
		OutputStream os = null;
		try {
			String filename = request.getParameter("filename");

			String filePath = PropertiesUtil.getValue("imagePath") + filename;

			fis = new FileInputStream(filePath);
			os = response.getOutputStream();
			int count = 0;
			byte[] buffer = new byte[1024 * 8];
			while ((count = fis.read(buffer)) != -1) {
				os.write(buffer, 0, count);
				os.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fis.close();
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void fileDownload(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// 获得请求文件名
			String filename = request.getParameter("fileName");

			// 设置文件MIME类型
			response.setContentType(getServletContext().getMimeType(filename));
			// 设置Content-Disposition
			response.setHeader("Content-Disposition", "attachment;filename=" + filename);
			// 读取目标文件，通过response将目标文件写到客户端
			// 获取目标文件的绝对路径

			String filePath = PropertiesUtil.getValue("attachmentPath");
			String fullFileName = filePath + filename;

			// String fullFileName =
			// getServletContext().getRealPath("/download/" + filename);
			// System.out.println(fullFileName);
			// 读取文件
			InputStream in = new FileInputStream(fullFileName);
			OutputStream out = response.getOutputStream();

			// 写文件
			int b;
			while ((b = in.read()) != -1) {
				out.write(b);
			}

			in.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void configLogo(HttpServletRequest request, HttpServletResponse response) throws IOException {

		FileInputStream fis = null;
		OutputStream os = null;
		try {
			String filePath = PropertiesUtil.getValue("imagePath") + "1503642659908.png";

			fis = new FileInputStream(filePath);
			os = response.getOutputStream();
			int count = 0;
			byte[] buffer = new byte[1024 * 8];
			while ((count = fis.read(buffer)) != -1) {
				os.write(buffer, 0, count);
				os.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fis.close();
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
