<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="f" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Thay đổi thông tin sản phẩm</title>
<base href="${pageContext.servletContext.contextPath}/">
<style><%@include file="/WEB-INF/resources/css/update_prods.css"%></style>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
<script type="text/javascript" src="js/query.js"></script>
<link href="https://fonts.googleapis.com/css2?family=Quicksand:wght@300&display=swap" rel="stylesheet">
</head>
<body>

<body>
    <header>
        <div class="container d-flex">
            <div class="logo">
                <a href="ad/home.htm">
	                <img src="img/bag.png" alt="không có ảnh">
	            </a>
            </div>
            <div class="login_search_contact">
                <div class="login_search d-flex">
                    <div class="search d-flex">
                        <input type="search" id="search" placeholder="search...">
                        <i class="fa fa-search"></i>
                    </div>
                    <div class="login d-flex">
						<a href="user/infor.htm">Xin chào Admin!!</a>
						<a href="user/login.htm?se=false">Đăng xuất</a>
                    </div>
                </div>
                <div class="contact d-flex">
                    <div class="phone_num d-flex">
                        <i class="fa fa-mobile"></i>
                        <a href="">+(84)855556086</a>
                    </div>
                    <div class="mail">
                        <i class="fa fa-envelope"></i>
                        <a href="https://mail.google.com/">quachhongthu131@gmail.com</a>
                    </div>
                    <div class="address">
                        <i class="fa fa-map-marker"></i>
                        <a href="https://maps.google.com/">Chỉ đường</a>
                    </div>
                </div>
            </div>
        </div>
    </header>

    <div class="title">
        <div class="container d-flex">
            <div class="navbar">
                <ul class="d-flex">
	                <li><a href="ad/del_prods.htm">Xóa, Sửa sản phẩm</a></li>
	                <li><a href="ad/add_prods.htm">Thêm sản phẩm</a></li>
	                <li><a href="ad/manage.htm">Quản lý đơn hàng</a></li>
	                <li><a href="ad/tk.htm">Thống kê</a></li>
                </ul>
            </div>
        </div>
    </div>

    <div class="form d-flex">
        <form:form action="" modelAttribute="prods" enctype="multipart/form-data">
    	${message1}
            <h1>Thay đổi thông tin sản phẩm</h1>
            <div>Mã sản phẩm:</div>
            	<form:input path="code" readonly="true" name="code"/>
            <div>Tên sản phẩm:</div>
            	<form:input path="name" name="name"/>
            <div>Số lượng:</div>
            	<form:input path="totalNum" name="totalNum"/>
            <div>Giá:</div>
            	<form:input path="price" name="price"/>
            <div>Loại:</div>
            	<form:input path="type" name="type"/>
            <div>Link ảnh:</div>
            	<input type="file" name="image1"/>
            <div>Link ảnh:</div>
            	<input type="file" name="image2"/>
            <div>Link ảnh:</div>
            	<input type="file" name="image3"/><br>
            	<img style="width: 100px;" alt="" src="img/${prods.img1}">
            	<img style="width: 100px;" alt="" src="img/${prods.img2}">
            	<img style="width: 100px;" alt="" src="img/${prods.img3}">
            <div>Giới thiệu sản phẩm:</div>
            	<form:input path="des" name="des"/>
            <br>
             
            <button>Thay đổi</button>
        </form:form>
        <div class="table">
            <table>
                <tr>
                    <th>Hình ảnh sản phẩm</th>
                    <th>Tên sản phẩm</th>
                    <th>Ngày tạo</th>
                    <th>Số lượng</th>
                    <th>Giá (VND)</th>
                </tr>
                <c:forEach var="p" items="${product}">
                <tr>
                    <td><img style="width: 100px;" alt="" src="img/${p.img1}"></td>
                    <td>${p.name}</td>
                    <td>${p.dateCreate}</td>
                    <td>${p.totalNum}</td>
                    <td>${p.price}</td>
                </tr>
                </c:forEach>
                
            </table>
        </div>
    </div>
</body>
</body>
</html>