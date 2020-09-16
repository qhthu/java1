<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html>

<html>
<head>
<meta charset="utf-8">
<title>Túi xinh | Đăng nhập</title>
<base href="${pageContext.servletContext.contextPath}/">
<style><%@include file="/WEB-INF/resources/css/login.css"%></style>
<style><%@include file="/WEB-INF/resources/css/reset.css"%></style>
<script type="text/javascript" src="js/query.js"></script>
<link href="https://fonts.googleapis.com/css2?family=Quicksand:wght@300&display=swap" rel="stylesheet">
</head>
<body>

	<form:form action="" modelAttribute="users">
	
		<div class="gom">
		${message}
		${message1}
            <p>ĐĂNG NHẬP</p>
            <div>Tên đăng nhập:</div>
            <input type="text" name="username" />
            <div>Mật khẩu:</div>
            <input type="password" name="password" />
            <button>Đăng nhập</button>
            <a href="user/forget-pass.htm">Quên mật khẩu</a>
            <p>
                Chưa có tài khoản? ,<a href="user/register.htm" class="create">Tạo tài khoản tại đây!</a>
            </p>
        </div>
	</form:form>
</body>
</html>