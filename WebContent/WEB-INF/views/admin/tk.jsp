<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="f" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Thống kê</title>
<base href="${pageContext.servletContext.contextPath}/">
<style><%@include file="/WEB-INF/resources/css/tk.css"%></style>
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
 <script src="<c:url value="https://code.jquery.com/jquery-3.5.1.min.js" />"></script>
 <script src="js/query3.js"></script>
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
                        <i class="fas fa-search"></i>
                    </div>
                    <div class="login d-flex">
						<a href="user/infor.htm">Xin chào Admin!!</a>
						<a href="user/login.htmse=false">Đăng xuất</a>
                    </div>
                </div>
                <div class="contact d-flex">
                    <div class="phone_num d-flex">
                        <i class="fas fa-mobile"></i>
                        <a href="">+(84)855556086</a>
                    </div>
                    <div class="mail">
                        <i class="far fa-envelope"></i>
                        <a href="https://mail.google.com/">quachhongthu131@gmail.com</a>
                    </div>
                    <div class="address">
                        <i class="fas fa-map-marker"></i>
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
<div class="d-flex">

	<div class="table" style="width: 600px;">
         <table>
             <tr>
                 <th>Ngày</th>
                 <th>Số lượng đơn hàng</th>
                 <th>Tổng tiền</th>
                 <th></th>
             </tr>
             <c:forEach var="u" items="${list}">
             	<tr>
	                <td>${u[0]}</td>
	                <td>${u[1]}</td>
	                <td>${u[2]}</td>
	                <th class="thune">
	                	<p>Chi tiết</p>
                        <div class="gom">
                            <c:forEach var="i" items="${test}">
                            	<c:if test="${u[0] == i[0]}">
                            		<ul style="padding: 10px;">
		                                <li>Mã đơn hàng: ${i[1]}</li>
		                                <li>Tên sản phẩm: ${i[2]}</li>
		                                <li>Số lượng: ${i[3]} </li>
		                                <li>Tên khách hàng: ${i[4]}</li>
		                            </ul>
                            	</c:if>
                            </c:forEach>
                            
                        </div>
					</th>
	           	</tr>
             </c:forEach>
     	</table>
	</div>
	<div class="chart">
		<div id="top_x_div" style="width: 500px; height: 500px; margin-top: 50px; z-index: -1;"></div>
	</div>

</div>
</body>
</body>
</html>