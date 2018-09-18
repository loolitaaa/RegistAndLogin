package com.tedu.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sun.misc.BASE64Encoder;

/**
 *  验证码验证
 */
public class VerifyCodeCheckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//前端传来的验证码
		String code = request.getParameter("verifyCode").toLowerCase();
		//session储存的验证码
		String sessionCode = (String) request.getSession().getAttribute("verifyCode");
		//前端验证码编码
		BASE64Encoder encoder = new BASE64Encoder();
		String base64Code = encoder.encode(code.getBytes());
		System.out.println(base64Code);
		System.out.println(sessionCode);
		if(request.getSession()==null) {
			response.getWriter().write("ERR");
		} else {
			if(base64Code.equals(sessionCode)) {
				response.getWriter().write("OK");
			} else {
				response.getWriter().write("ERR");
			}
		}		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
