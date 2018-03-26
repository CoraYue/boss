<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<shiro:authenticated>
你已经通过认证

</shiro:authenticated>
	<hr>
	<%-- <shiro:hasPermission name="courierAction_pageQuery"> --%>
	<!--权限发生改变  -->
	<shiro:hasPermission name="courierActsssion_pageQuery">
	你有courierAction_pageQuery权限

</shiro:hasPermission>
	<hr>
	<shiro:hasRole name="admin">
你有admin角色

</shiro:hasRole>

</body>
</html>