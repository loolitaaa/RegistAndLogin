package com.tedu.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tedu.verifycode.ImgResult;
import com.tedu.verifycode.VerifyCodeUtils;

/**
 *    用来生成验证码及图片
 */
public class VerifyCodeCreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//获取session
		HttpSession session = request.getSession();
		//生成验证码
		ImgResult verifyCode = VerifyCodeUtils.VerifyCode(120, 50, 4);		
		session.setAttribute("verifyCode",verifyCode.getCode());
		//设置cookie:生存时间 3min path
		Cookie cookie = new Cookie("JSESSIONID",session.getId());
		cookie.setMaxAge(3*60);
		cookie.setPath(request.getContextPath()+"/");
		response.addCookie(cookie);
		//response图片
		response.getWriter().write(verifyCode.getImg());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
