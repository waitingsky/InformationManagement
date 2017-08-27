package com.java1234.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.java1234.dao.SystemConfigBO;
import com.java1234.model.ISystemConfig;
import com.java1234.model.SystemConfig;
import com.java1234.model.SystemConfigs;
import com.java1234.util.DateUtil;
import com.java1234.util.NavUtil;
import com.java1234.util.PropertiesUtil;
import com.java1234.util.StringUtil;

public class SystemConfigServlet extends HttpServlet {

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
		if ("config".equals(action)) {
			this.loadConfig(request, response);
		} else if ("configSave".equals(action)) {
			this.save(request, response);
		} else if ("configLogo".equals(action)) {
			this.loadLogo(request, response);
		}
	}

	@SuppressWarnings("unchecked")
	private void save(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		try {
			List<FileItem> items = upload.parseRequest(request);
			if (items != null) {

				Iterator<FileItem> itr = items.iterator();
				HashMap<String, String> map = new HashMap<String, String>();

				while (itr.hasNext()) {
					FileItem item = (FileItem) itr.next();
					if (item.isFormField()) {
						map.put(item.getFieldName(), item.getString("utf-8"));
					} else {
						try {
							String itemName = item.getName();// �ļ�����
							if (StringUtil.isNotEmpty(itemName)) {
								System.out.println("ԭ�ļ�����" + itemName);// Koala.jpg

								String suffix = itemName.substring(itemName.lastIndexOf('.'));
								System.out.println("��չ����" + suffix);// .jpg

								// ���ļ�����Ψһ��
								String newFileName = new Date().getTime() + suffix;
								System.out.println("���ļ�����" + newFileName);// image\1478509873038.jpg

								// 5. ����FileItem��write()������д���ļ�
								String imageName = DateUtil.getCurrentDateStr();
								String filePath = PropertiesUtil.getValue("imagePath") + newFileName;

								// File file = new
								// File("D:/lindaProjects/mySpace/wendao/WebContent/touxiang/"
								// + newFileName);

								File file = new File(filePath);
								System.out.println(file.getAbsolutePath());
								item.write(file);

								// 6. ����FileItem��delete()������ɾ����ʱ�ļ�
								item.delete();

								// map.put(item.getFieldName(), item.getName());
								map.put(item.getFieldName(), newFileName);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}

				List<SystemConfig> configs = new ArrayList<SystemConfig>();

				// configLogoId��fileLogoImage��imgLogo��imgPreLogo
				String tmpContent = "";
				if (StringUtil.isNotEmpty(map.get("fileLogoImage"))) {
					// tmpContent = map.get("fileLogoImage").split("/")[1];
					tmpContent = map.get("fileLogoImage");
				}
				configs.add(
						getSystemConfig(map.get("configLogoId"), ISystemConfig.CONFIG_MODULE_LOGO, tmpContent, "", ""));

				// systemId1��systemType1��systemDesc1��systemUrl1
				configs.add(getSystemConfig(map.get("systemId1"), ISystemConfig.CONFIG_MODULE_SYSTEM1,
						map.get("systemUrl1"), map.get("systemDesc1"), map.get("systemType1")));

				// systemId2��systemType2��systemDesc2��systemUrl2
				configs.add(getSystemConfig(map.get("systemId2"), ISystemConfig.CONFIG_MODULE_SYSTEM2,
						map.get("systemUrl2"), map.get("systemDesc2"), map.get("systemType2")));

				// systemId3��systemType3��systemDesc3��systemUrl3
				configs.add(getSystemConfig(map.get("systemId3"), ISystemConfig.CONFIG_MODULE_SYSTEM3,
						map.get("systemUrl3"), map.get("systemDesc3"), map.get("systemType3")));

				// systemId4��systemType4��systemDesc4��systemUrl4
				configs.add(getSystemConfig(map.get("systemId4"), ISystemConfig.CONFIG_MODULE_SYSTEM4,
						map.get("systemUrl4"), map.get("systemDesc4"), map.get("systemType4")));

				// serviceSupportId��serviceSupport
				configs.add(getSystemConfig(map.get("serviceSupportId"), ISystemConfig.CONFIG_MODULE_SERVICES,
						map.get("serviceSupport"), "", ""));

				SystemConfigBO.getInstance().save(configs);
			}
			request.getRequestDispatcher("/config?action=config").forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private SystemConfig getSystemConfig(String configIdStr, String name, String content, String description,
			String options) {
		SystemConfig config = new SystemConfig();
		if (StringUtil.isNotEmpty(configIdStr)) {
			config.setConfigId(Integer.parseInt(configIdStr));
		}

		config.setName(name);
		config.setDescription(description);
		config.setContent(content);
		config.setOptions(options);

		return config;
	}

	private void loadConfig(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			List<SystemConfig> list = SystemConfigBO.getInstance().list();
			SystemConfigs systemConfigs = new SystemConfigs(list);

			request.setAttribute("systemConfigs", systemConfigs);
			request.setAttribute("navCode", NavUtil.genNewsManageNavigation("ϵͳ����", "ϵͳ����"));
			request.setAttribute("mainPage", "/background/system/systemConfig.jsp");
			request.getRequestDispatcher("/background/mainTemp.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loadLogo(HttpServletRequest request, HttpServletResponse response) throws IOException {

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

		// try{
		// String filePath = PropertiesUtil.getValue("imagePath") +
		// "1503642659908.png";
		//
		// String saveAddress =filePath;
		// FileInputStream hFile = new FileInputStream(saveAddress); //
		// ��byte���ķ�ʽ���ļ� d:\1.gif
		// int i=hFile.available(); //�õ��ļ���С
		// byte data[]=new byte[i];
		// hFile.read(data); //������
		// hFile.close();
		// response.setContentType("image/*"); //���÷��ص��ļ�����
		// OutputStream toClient=response.getOutputStream(); //�õ���ͻ���������������ݵĶ���
		// toClient.write(data); //�������
		// toClient.close();
		// }
		// catch(IOException e) //������
		// {
		// PrintWriter toClient = response.getWriter(); //�õ���ͻ�������ı��Ķ���
		// response.setContentType("text/html;charset=gb2312");
		// toClient.write("�޷���ͼƬ!");
		// toClient.close();
		// }
	}

}
