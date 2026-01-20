<%@page import="model.BlogDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="../header.jsp" %>
	
<!-- sub visual -->

	<div class="subvisual">
		<div class="sub-inner">
			<div class="sub-title">
				<h2>Favorite</h2>
				<p>Let's be treated as a professional with patience and creativity and challenge ourselves to the end</p>
			</div>
		</div>
	</div>
	
<!-- content -->

	<div class="sub-container">

		<c:forEach var="item" items="${wishlist }">
			<div class="search-content">
				<div>
					<input type="checkbox" class="blogCheckBox" value="${item.wish_bno }">
				</div>
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
							<button  class="more deleteBtn" data-wish_bno="${item.wish_bno}">Delete</button>
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
			<button id="deleteSelected">선택삭제</button>
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
	
	$(".deleteBtn").on("click", function() {
		
		var wishBno = $(this).data("wish_bno");
		var $this = $(this);//$는 jQury 객체임을 구분하기 위한 개발자 관례상 붙이는 것
		$.ajax({
			type:'post',
			data: {wishBno:wishBno},
			url:'/mem/mywishDelete.do',
			success:function(res) {
				  if(res.trim() === "success") {
					  $this.closest(".search-content").remove(); // 찜한 블러그 그룹 삭제
				  }else{
					  alert("삭제실패");
				  }
			},error:function() {
				alert("오류발생");
			}
		})
	})
</script>

<%@ include file="../footer.jsp" %>	
























