<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="../header.jsp" %>
	
<!-- main visual -->

	<div class="subvisual">
		<div class="sub-inner">
			<div class="sub-title">
				<h2>Sign In</h2>
				<p>Let's be treated as a professional with patience and creativity and challenge ourselves to the end</p>
			</div>
		</div>
	</div>
	
<!-- content -->

	<div class="sub-container">
		<div class="loginbox">
			<h2>LOGIN</h2>
			<!-- <form name="my" method="post" action="/mem/loginpro.do"  onsubmit="return check()"> -->
			<!-- check() 함수는 전역 함수 이어야 한다 -->
			<form id="myform">
				<table class="border-table">
					<tr>
						<td>
							<input type="checkbox" name="useridcheck" id="saveid">&nbsp;&nbsp;아이디저장
						</td>
					</tr>
					<tr>
						<td><input type="text" name="userid" id="loginUserid" placeholder="아이디"></td>
					</tr>
					<tr>
						<td><input type="password" name="password" id="password" placeholder="비밀번호"></td>
					</tr>
					
					<tr>
						<td>
							<!-- <button type="submit" class="submit" style="width:100%; cursor:pointer">로그인</button> -->
							<button type="button" class="submit" id="btn-login" style="width:100%; cursor:pointer">로그인</button>
						</td>
					</tr>
					<tr>
						<td>
							<a href="">아이디·비밀번호찾기</a> | 
							<a href="">회원가입</a> | 
							<c:set var="clientId" value="${initParam.googleClientId}" />

							<a href="https://accounts.google.com/o/oauth2/v2/auth
							?client_id=${clientId}
							&redirect_uri=http://localhost:8088/google/callback
							&response_type=code
							&scope=openid%20email%20profile">
							    Google 로그인
							</a>
						</td>
					</tr>
				</table>
				<p id="errmsg"></p>
			</form>
		</div>
		
	</div>
	
	
<!-- footer -->	

<%@ include file="../footer.jsp" %>























