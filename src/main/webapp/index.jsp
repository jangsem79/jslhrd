<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ include file="header.jsp" %>
	
	<!-- content -->
	<div class="mainvisual">
		<div class="visual-inner">
			<div class="visual-title">
				<h2>Back End Development</h2>
				<p>Let's be treated as a professional with patience and creativity and challenge ourselves to the end</p>
			</div>
		</div>
	</div>
	<div class="container">
		<div class="box">
			<p>배움의 즐거움이 있는곳</p>
			<h2><span class="logocolor">JSL COLEGE</span> 소식</h2>
			<p class="cont1">JSL인재개발원 다양한 소식을 확인할 수 있습니다</p>
			<span class="more"><a href="">READ MORE ></a></span>
		</div>
		<c:forEach var="item" items="${mainlist }">
			<div class="box">
					<div class="over">
						<img src="/img/${item.imgfile }">
					</div>
					<h3 class="porttitle">${item.title }</h3>
					<div class="txt" style="height:100px; overflow:hidden;">${item.content }</div>
			</div>
		</c:forEach>
	</div>
	
<%@ include file="footer.jsp" %>











