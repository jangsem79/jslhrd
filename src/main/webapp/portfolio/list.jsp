<%@page import="model.BlogDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="../header.jsp" %>
	
<!-- sub visual -->

	<div class="subvisual">
		<div class="sub-inner">
			<div class="sub-title">
				<h2>PORTPOLIO</h2>
				<p>Let's be treated as a professional with patience and creativity and challenge ourselves to the end</p>
			</div>
		</div>
	</div>
	
<!-- content -->

	<div class="sub-container">
		<div class="search-group">
			<div class="count">
				<span class="count">총게시글 : ${totalCount }</span>
			</div>
			<div class="search">
				<form name="my" method="post" action="/port/list.do" onsubmit="return check()">
					<select name="type">
						<option value="">선택</option>
						<option value="title">제목</option>
						<option value="content">내용</option>
					</select>
					<input type="text" name="keyword">
				</form>
			</div>
		</div>
		<c:forEach var="item" items="${list }">
			<div class="search-content">
				<div class="date">
					<span>${item.regdate.substring(8,10) }</span>
					<em>${item.regdate.substring(0,7) }</em>
				</div>
				<div class="search-image">
					<img src="/img/${item.imgfile}" alt="단풍구경이미지">
				</div>
				<div class="search-text">
					<div>
						<span>No.${item.bno }</span> | <span>${item.views }</span>
					</div>
					<div>
						<a href="/port/view.do?bno=${item.bno }"><h3>${item.title }</h3></a>
					</div>
					<div class="txt">
						<div  style="height:72px; overflow:hidden;">
						${item.content}
						</div>
						<p>
							<a href="/port/view.do?bno=${item.bno }" class="more">MORE</a> 
							<button class="wish" data-blogbno="${item.bno}">Wish</button>
						</p>
					</div>
				</div>
			</div>
		</c:forEach>
   </div>
	
	<div class="page">
		<div class="number">
			<c:if test="${startPage > 1 }">
				<a href="/port/list.do?keyword=${param.keyword}&page=${startPage-1}"><<</a>
			</c:if>
			<c:forEach begin="${startPage}" end="${endPage}" var="p">
				<a href="/port/list.do?keyword=${param.keyword}&page=${p}" 
					${p==currentPage?'class="active"':''}>${p}</a>
			</c:forEach>
			<c:if test="${endPage < totalPages }">
				<a href="/port/list.do?keyword=${param.keyword}&page=${endPage+1}">>></a>
			</c:if>
		</div>
		<div class="writer">
			<a href="/port/write.do">글쓰기</a>
		</div>
	</div>
<!-- footer -->	

<script>
	function check() {
		if(!my.type.value) {
			alert("검색방법을 선택해 주세요");
			return false;
		}
		if(!my.keyword.value) {
			alert("검색단어를 입력하세요")
			my.keyword.focus();
			return false;
		}
		return true;
	}
	
	$(".wish").on("click", function() {
		//로그인한 사람만 찜 할 수 있다
		var userid = "${sessionScope.userid}";
		var blogbno = $(this).data("blogbno");
		console.log(userid);
		console.log(blogbno);
		
		if(!userid) {
			alert("블러그 찜은 로그인이 필요합니다");
			return;
		}
		
		//동기식이건 , 비동기식이던지 아이디와 블러그 번호를 서버로 넘겨줘야 insert 하겠지...
		$.ajax({
			type:'post',
			data: {userid:userid,blogbno:blogbno},
			url:'/port/mywish.do',
			success:function(res) {
				alert(res);
			},error:function() {
				alert("오류발생");
			}
		})
	})
</script>

<%@ include file="../footer.jsp" %>	
























