<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JSLHRD</title>
<link rel="stylesheet" href="/css/mystyle.css">
<script src="/js/jquery-3.7.1.min.js"></script>
</head>
<body>
	<div class="top_navigation">
		<header class="header">
			<nav class="top_left">
				<ul>
					<li class="first"><a href="/">Home</a></li>
					<li><a href="">모집안내</a></li>
					<li><a href="">입학상담</a></li>
					<li><a href="">교육신청</a></li>
				</ul>
			</nav>
			<nav class="top_right">
				<ul>
					<c:choose>
						<c:when test="${empty sessionScope.userid}">
							<li class="first"><a href="/mem/login.do">로그인</a></li>
							<li><a href="/mem/join.do">회원가입</a></li>
						</c:when>
	                    <c:otherwise>
	                    	<li><a href="/mem/logout.do">로그아웃</a></li>				
							<li><a href="/mem/mypage.do">마이페이지</a></li>
						</c:otherwise>
					</c:choose>
				</ul>
			</nav>
		</header>
	</div>
	<div class="gnb_group">
		<h1 class="logo"><a href="/"><span class="jsl">JSL</span>HRD</a></h1>
		<nav class="gnb">
			<ul class="nav_1depth">
				<li>
					<a href="">기업소개</a>
					<ul class="nav_2depth">
						<li><a href="">인사말</a>
						<li><a href="">연혁</a>
						<li><a href="">오시는길</a>
					</ul>
				</li>
				<li>
					<a href="/port/list.do">포트폴리오</a>
					<ul class="nav_2depth">
						<li><a href="/port/list.do">웹프로그래밍</a>
						<li><a href="">UI/UX디자인</a>
						<li><a href="">AI크리액티브</a>
						<li><a href="">앱프로그래밍</a>
					</ul>
				</li>
				<li>
					<a href="">커뮤니티</a>
					<ul class="nav_2depth">
						<li><a href="">질문답변</a>
						<li><a href="">공지사항</a>
						<li><a href="">갤러리</a>
						<li><a href="">졸업후기</a>
						<li><a href="">구인구직</a>
					</ul>
				</li>
			</ul>
		</nav>
	</div>