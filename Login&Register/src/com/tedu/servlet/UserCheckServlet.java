package com.tedu.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tedu.jdbc.JdbcCrud;
import com.tedu.user.UserInfo;

/**
 * 
 */
public class UserCheckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//提取信息
		String nameOrEmail = request.getParameter("nameOrEmail");
		
		//查询和处理
		UserInfo user = new UserInfo(nameOrEmail,nameOrEmail,null);
		String result = null;
		
		if(JdbcCrud.query(user, !JdbcCrud.USEPASSWORD) == null){
			result = "true";
		}  else{
			result = "false";
		}
		System.out.println(result);
		System.out.println(user);
		response.getWriter().write(result);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
