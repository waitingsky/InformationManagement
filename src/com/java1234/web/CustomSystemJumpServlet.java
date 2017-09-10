package com.java1234.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.java1234.dao.SystemConfigBO;
import com.java1234.model.ISystemConfig;
import com.java1234.model.SystemConfig;

public class CustomSystemJumpServlet extends HttpServlet {

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
		if ("jump".equals(action)) {
			this.jump(request, response);
		}
	}

	private void jump(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String configId = request.getParameter("configId");
		try {
			SystemConfig config = SystemConfigBO.getInstance().get(configId);
			if (config != null) {
				setParams(config, request);
				request.getRequestDispatcher("jump.jsp").forward(request, response);
				// 需要校验参数
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setParams(SystemConfig config, HttpServletRequest request) {
		Map<String, Object> params = new HashMap<String, Object>();
		if (config.getOptions().equals(String.valueOf(ISystemConfig.CONFIG_OPTIONS_SYSTEM_CFS))) {
			params.put("username", "cfs");
			params.put("password", "cfs2");
		} else if (config.getOptions().equals(String.valueOf(ISystemConfig.CONFIG_OPTIONS_SYSTEM_HFM))
				|| config.getOptions().equals(String.valueOf(ISystemConfig.CONFIG_OPTIONS_SYSTEM_PLANNING))) {
			params.put("SSO_USERNAME", "hfm1");
			params.put("SSO_PASSWORD", "hfm2");
		}

		request.setAttribute("login_sso_params", params);
		request.setAttribute("login_sso_url", config.getContent());
	}

}
