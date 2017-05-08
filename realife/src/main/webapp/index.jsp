<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
	  <h1>study and restudy，very happy</h1>
    <h1><p align="center">welcome  to  lp's first project</p></h1><br>
    <div>
    <img src="<%=basePath%>images/welcome.png"  alt="" style="width:100px;height: 200px;"/>
    </div>
    <div>
     <img src="<%=basePath%>images/welcome.png"  alt="" style="width:100px;height: 200px;"/>
    </div>
  </body>
</html>
