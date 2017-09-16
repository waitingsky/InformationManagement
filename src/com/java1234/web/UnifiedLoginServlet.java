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

	// ��дdoGet����
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String errorTitle="��֤ʧ��";
		String errorMessage="��ǰ�û���Ŀ��ϵͳ�в����ڣ�����ϵ����Ա��";
		String url="#";
		boolean autoJump = false; 

		String type = request.getParameter("type");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String ssoToken = request.getParameter("ssoToken");

		HttpSession session = request.getSession();

//		request.setAttribute("msg", "������ѱ��۸�");
		// RequestDispatcher rd =
		// request.getRequestDispatcher("login_cmbc.jsp");
		// rd.forward(request, response);
		// response.sendRedirect("ui/main/main.zul");

		System.out.println("��֤��ͨ��");
//		request.setAttribute("msg", "�û���������֤ʧ��");
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
