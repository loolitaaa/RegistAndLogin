package com.tedu.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

import com.tedu.user.UserInfo;

public class JdbcCrud {
	//查询的SQL语句:通过username或email查询,第三个?处设置是否带password查询
	private static final String QUERY_SQL;
	//向user表中添加用户信息的SQL语句
	private static final String CREATE_SQL;
	//更改用户密码的SQL语句
	private static final String UPDATE_PASSWORD_SQL;
	//更改用户邮箱的SQL语句
	private static final String UPDATE_EMAIL_SQL;
	//因异常未查询到用户时的返回值
	public static final UserInfo FLAG_USER;	
	//是否使用password查询
	public static final boolean USEPASSWORD;
			
	static{
		QUERY_SQL = "select * from user where (username = ? or email = ?) and (? or password = ?)";
		CREATE_SQL = "insert into user values (null,?,?,?)";
		UPDATE_PASSWORD_SQL = "update user set password = ? where (username = ? and email = ?)";
		UPDATE_EMAIL_SQL = "update user set password = ? where (username = ? and password = ?)";
		FLAG_USER = new UserInfo("1","1","1");
		USEPASSWORD = true;
	}
	
	/*
	 * 在数据库中查找用户的方法:
	 * 1. 给出了带密码查询和不带密码查询两种选择,通过hasPassword设置
	 * 2. 方法的返回值:
	 * 		若查询到匹配的用户信息,返回用户对象
	 *      若在查询过程中出现异常,返回FLAG_USER
	 *      若为查询到用户信息,返回null
	 */	
	public static UserInfo query(UserInfo user,boolean usePassword){
		//查找到的user
		UserInfo userFound = null;
		
		try {
			
			PreparedStatement ps = getConnection().prepareStatement(QUERY_SQL);
			
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getEmail());
			ps.setString(3, String.valueOf(!usePassword));
			ps.setString(4, user.getPassword());
			
			ResultSet rs = ps.executeQuery();
			if(!rs.next()) return userFound;
			
			//若查找到用户信息,新建一个UserInfo实例并赋值
			userFound = user;
		} catch (SQLException e) {
			e.printStackTrace();
			userFound = FLAG_USER;
		} finally{
			//close
		}
		return userFound;
	}
	
	/*
	 * 在数据库中新建用户:
	 * 1. 不允许在数据库中插入不完整的用户信息
	 * 2. 用户创建成功,返回用户对象,否则返回null
	 */
	public static UserInfo createUser(UserInfo user){
		
		//用户信息不完整,退出
		if(!user.isComplete())return null;
		
		//被新建的user
		UserInfo createdUser = null;				
		try {
			PreparedStatement ps = getConnection().prepareStatement(CREATE_SQL);
			
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getEmail());
			ps.setString(3, user.getPassword());
			ps.executeUpdate();
			
			createdUser = user;//没有异常说明用户在数据库中创建成功
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally{
			//close
		}
		return createdUser;
	}
	
	/*
	 * 在数据库中修改用户密码的方法:
	 * 1. 只有用户名和邮箱全部匹配才能修改密码
	 * 2. 成功返回true 失败返回false
	 */	
	public static boolean changePassword(UserInfo user){
		PreparedStatement ps;
		
		try {
			ps = getConnection().prepareStatement(UPDATE_PASSWORD_SQL);
			
			ps.setString(1, user.getPassword());
			ps.setString(2, user.getUsername());
			ps.setString(3, user.getEmail());
			
			return ps.executeUpdate()==1? true:false;
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			//close
		}	
		return false;
	}
	
	/*
	 * 在数据库中修改用户邮箱的方法:
	 * 1. 只有用户名和密码全部匹配才能修改
	 * 2. 成功返回true 失败返回false
	 */	
	public static boolean changeEmail(UserInfo user){
		PreparedStatement ps;
		
		try {
			ps = getConnection().prepareStatement(UPDATE_EMAIL_SQL);
			
			ps.setString(3, user.getPassword());
			ps.setString(2, user.getUsername());
			ps.setString(1, user.getEmail());
			
			return ps.executeUpdate()==1? true:false;
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			//close
		}	
		return false;
	}
	
	//提供了直接执行sql语句的方法,慎用
	public static boolean excute(String sql){
		try {
			PreparedStatement ps = getConnection().prepareStatement(sql);
			return ps.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			//close
		}
		return false;
	}
	
	private static Connection getConnection(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql:///jtdb","root","root");
			return conn;
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}
		
	@Test
	public void test(){
		UserInfo ls = new UserInfo("李四","78978@qq.com","12345");

		System.out.println(JdbcCrud.changePassword(ls));
	}	
}
