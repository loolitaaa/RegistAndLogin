 
<%@ page language="java" pageEncoding="gbk"%>
<%@page import="java.sql.Connection"%>
<%@page import="javax.naming.Context"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="javax.sql.DataSource"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.ResultSet"%>
<%
   //���ӳصĻ�ȡ
    Connection conn = null;
    DataSource ds = null;
    ResultSet rs  =null;
    Statement stmt = null;
    Context initCtx = new InitialContext();
    ds =(DataSource)initCtx.lookup("java:comp/env/jdbc/ens");
   if(ds!=null){
        out.println("�Ѿ����DataSource!");
        out.println("<br>");
        conn = ds.getConnection();
       try{
        stmt = conn.createStatement();
        String sql ="select * from user";
        rs = stmt.executeQuery(sql);
        out.println("�����Ǵ����ݿ��ж�ȡ����������:<br>");
            while(rs.next()){
                out.println("<br>");
                out.println(rs.getString("username"));
            }
       }catch(Exception ex){
         ex.printStackTrace();
       }finally{
          conn.close();
          rs.close();
          stmt.close();
       }
   }
 
%>
