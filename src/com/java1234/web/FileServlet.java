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
			// ��ȡconfigͼƬ
			this.configLogo(request, response);
		} else if ("ckeditorImageUpload".equals(action)) {
			// ckeditor�ļ��ϴ�
			this.ckeditorImageUpload(request, response);
		} else if ("ckeditorImage".equals(action)) {
			// ckeditorͼƬ��ʾ
			this.ckeditorImage(request, response);
		} else if ("fileDownload".equals(action)) {
			// �ļ�����
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
						+ "'δ��ȡ���ϴ��ļ��������²�������ͼƬ����Ϊ.jpg/.gif/.bmp/.png�ļ���');");
				out.println("</script>");
				out.flush();
				return;
			}

			FileItem fileItem = list.get(0);
			String uploadContentType = fileItem.getContentType();
			String expandedName = ""; // �ļ���չ��
			if (uploadContentType.equals("image/pjpeg") || uploadContentType.equals("image/jpeg")) {
				// IE6�ϴ�jpgͼƬ��headimageContentType��image/pjpeg����IE9�Լ�����ϴ���jpgͼƬ��image/jpeg
				expandedName = ".jpg";
			} else if (uploadContentType.equals("image/png") || uploadContentType.equals("image/x-png")) {
				// IE6�ϴ���pngͼƬ��headimageContentType��"image/x-png"
				expandedName = ".png";
			} else if (uploadContentType.equals("image/gif")) {
				expandedName = ".gif";
			} else if (uploadContentType.equals("image/bmp")) {
				expandedName = ".bmp";
			} else {
				out.println("<script type=\"text/javascript\">");
				out.println("window.parent.CKEDITOR.tools.callFunction(" + callback + ",'',"
						+ "'�ļ���ʽ����ȷ������Ϊ.jpg/.gif/.bmp/.png�ļ���');");
				out.println("</script>");
				out.flush();
				return;
			}

			String uploadPath = PropertiesUtil.getValue("imagePath");
			String fileName = java.util.UUID.randomUUID().toString(); //
			// ����ʱ��+UUID�ķ�ʽ�漴����
			fileName += expandedName;
			File toFile = new File(uploadPath, fileName);

			fileItem.write(toFile);

			// ���ء�ͼ��ѡ�����ʾͼƬ
			out.println("<script type=\"text/javascript\">");
			// out.println("window.parent.CKEDITOR.tools.callFunction(" +
			// callback + ",'" + "img/postImg/" + fileName
			// + "','')");
			out.println("window.parent.CKEDITOR.tools.callFunction(" + callback + ",'"
					+ "file?action=ckeditorImage&filename=" + fileName + "','')");
			out.println("</script>");
			out.flush();
			return;

			// response.setContentType("text/html;charset=UTF-8");
			// request.setCharacterEncoding("UTF-8");
			// response.setCharacterEncoding("UTF-8");
			// PrintWriter out = response.getWriter();
			// FileItemFactory factory = new DiskFileItemFactory();
			// ServletFileUpload upload = new ServletFileUpload(factory);
			// try {
			// List<FileItem> list = upload.parseRequest(request);
			// for (FileItem fileItem : list) {
			// String imageName = DateUtil.getCurrentDateStr();
			// File file = new File(
			// PropertiesUtil.getValue("imagePath") + imageName + "." +
			// fileItem.getName().split("\\.")[1]);
			// String newPath = PropertiesUtil.getValue("imageFile") + "/" +
			// imageName + "."
			// + fileItem.getName().split("\\.")[1];
			// fileItem.write(file);
			// String callback = request.getParameter("CKEditorFuncNum");
			// out.println("<script type=\"text/javascript\">");
			// out.println("window.parent.CKEDITOR.tools.callFunction(" +
			// callback +
			// ",'" + newPath + "',''" + ")");
			// out.println("</script>");
			// out.flush();
			// }
			// } catch (Exception e) {
			// e.printStackTrace();
			// }
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
			// ��������ļ���
			String filename = request.getParameter("filename");

			// �����ļ�MIME����
			response.setContentType(getServletContext().getMimeType(filename));
			// ����Content-Disposition
			response.setHeader("Content-Disposition", "attachment;filename=" + filename);
			// ��ȡĿ���ļ���ͨ��response��Ŀ���ļ�д���ͻ���
			// ��ȡĿ���ļ��ľ���·��

			String filePath = PropertiesUtil.getValue("attachmentPath");
			String fullFileName = filePath + filename;

			// String fullFileName =
			// getServletContext().getRealPath("/download/" + filename);
			// System.out.println(fullFileName);
			// ��ȡ�ļ�
			InputStream in = new FileInputStream(fullFileName);
			OutputStream out = response.getOutputStream();

			// д�ļ�
			int b;
			while ((b = in.read()) != -1) {
				out.write(b);
			}

			in.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// if(!file.exists()){
		// request.setAttribute("msg","<script
		// type=\"text/javascript\">alert(\"ָ�����ļ������ڣ�\");</script>");
		// request.getRequestDispatcher(action).forward(request, response);
		// }else{
		// response.reset();
		// //�� �ļ���д�뵽ǰ�������
		// response.setHeader("Content-disposition",
		// "attachment;filename="+convertFileName(filename));
		// ServletOutputStream sops=response.getOutputStream();
		// FileInputStream fis=new FileInputStream(file);
		// copyStream(fis,sops,true);
		// fis.close();
		// sops.close();
		// fis=null;
		// sops=null;
		// file=null;
		// }

		// //����������������صķ�����ȡ����Դ
		// //����������Դ��ֱ���������URLEncoder.encode(realPath, "utf-8"))
		// response.setHeader("content-disposition","attachment;
		// filename="+URLEncoder.encode(realPath, "utf-8"));
		// //��ȡ�������ص���Դ
		// FileInputStream fis = new FileInputStream(path);
		// int len = 0;
		// byte [] buf = new byte[1024];
		// while((len=fis.read(buf))!=-1){
		// response.getOutputStream().write(buf,0,len);
		// }
		// }

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
