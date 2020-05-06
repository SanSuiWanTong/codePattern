
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<jsp:useBean id="beatModel" scope="request" class="DJView.BeatModel" />  <!-- class 需要放包里，默认报错 -->

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<body>
	<form method="get" action="/DJViewWeb/DJViewServlet">
		Get BPM: <input type=text name="bpm" value="<jsp:getProperty name='beatModel' property='BPM' />">
	</form>	
	
	<form method="get" action="/DJViewWeb/DJViewServlet">
		<input type="submit" name="bpm" value="testGetBpm" />
	</form>
	
	<form method="post" action="/DJViewWeb/DJViewServlet">
		<input type="submit" name="set" value="testPost" />
	</form>

	<a href="http://localhost:8080/DJViewWeb/DJViewServlet">http://localhost:8080/DJViewWeb/DJViewServlet</a>
	
	<form method="post" action="/DJViewWeb/DJViewServlet">		<!-- action 要写对 -->
		<input type="submit" name=on  value="start" /> 
		<input type="submit" name=off value="stop" />
	</form>
	
</body>
</html>