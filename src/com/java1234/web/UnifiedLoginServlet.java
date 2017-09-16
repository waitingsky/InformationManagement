package com.java1234.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UnifiedLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// 重写doGet方法
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String errorTitle="验证失败";
		String errorMessage="当前用户在目标系统中不存在，请联系管理员。";
		String url="#";
		boolean autoJump = false; 

		String type = request.getParameter("type");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String ssoToken = request.getParameter("ssoToken");

		HttpSession session = request.getSession();

//		request.setAttribute("msg", "随机数已被篡改");
		// RequestDispatcher rd =
		// request.getRequestDispatcher("login_cmbc.jsp");
		// rd.forward(request, response);
		// response.sendRedirect("ui/main/main.zul");

		System.out.println("验证不通过");
//		request.setAttribute("msg", "用户和密码验证失败");
//		RequestDispatcher rd = request.getRequestDispatcher("login_cmbc.jsp");
//		rd.forward(request, response);
		
		
		

		request.setAttribute("errorTitle", errorTitle);
		request.setAttribute("errorMessage", errorMessage);
		request.setAttribute("url",url);
		request.setAttribute("autoJump", autoJump);

		request.getRequestDispatcher("/error.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
