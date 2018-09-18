package com.tedu.servlet;

/**
 *实现了用户登录 
 */
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tedu.jdbc.JdbcCrud;
import com.tedu.user.UserInfo;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = new String(request.getParameter("username").getBytes("iso8859-1"),"utf-8");
		String email = new String(request.getParameter("email").getBytes("iso8859-1"),"utf-8");
		System.out.println(email);
		String password = new String(request.getParameter("password").getBytes("iso8859-1"),"utf-8");
		
		UserInfo user = new UserInfo(username,email,password);
		
		/*
		 * 若数据库中存在该用户信息,成功登陆,跳转主页
		 * 若不存在,停留在注册界面
		 * 若抛异常,跳转至500页面
		 */
//		if(JdbcCrud.query(user, true) != null && !JdbcCrud.query(user, true).equals(JdbcCrud.FLAG_USER)){
		if(JdbcCrud.query(user, JdbcCrud.USEPASSWORD) == null){
			request.getRequestDispatcher("/login.html").forward(request, response);
		} else if(JdbcCrud.query(user, !JdbcCrud.USEPASSWORD).equals(user)){
				request.getRequestDispatcher("/index.html").forward(request, response);
			
		} else{
			//Err500.html
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}

