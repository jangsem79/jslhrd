<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="../header.jsp" %>
	
<!-- main visual -->

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
		<div class="writebox">
			<form name="my" method="post" action="/port/updatepro.do" enctype="multipart/form-data" onsubmit="return check()">
			<input type="hidden" name="bno" value="${viewdto.bno }">
				<table class="border-table">
					<tr>
						<th>글쓴이</th>
						<td><input type="text" name="name" value="${viewdto.name}" readonly></td>
					</tr>
					<tr>
						<th>제목</th>
						<td><input type="text" name="title" id="title" value="${viewdto.title}"></td>
					</tr>
					<tr>
						<th>내용</th>
						<td>
							<button type="button" id="btn-ai" style="padding:6px 12px;">AI글쓰기</button>
							<!-- <button type="button" id="btn-translate" style="padding:6px 12px;">일본어번역</button> -->
							<select name="lang" id="btn-translate"  style="padding:6px 12px;display:inline-block;width:120px;">
								<option value="">언어선택</option>
								<option value="jp">일본어</option>
								<option value="en">영어</option>
								<option value="ch">중국어</option>
							</select>
							<span style="display:block; padding:10px 0;">
							<textarea name="content" id="content">${viewdto.content }</textarea>
							</span>
						</td>
					</tr>
					<tr>
						<th>첨부</th>
						<td>
							<input type="file" name="imgfile">
							<img src="/img/${viewdto.imgfile }" style="width:36px; height:36px;">
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<button type="submit" class="submit">수정글 저장</button>
							<button type="reset" class="reset" onclick="alert('모두 지우고 다시쓸래요?')">다시쓰기</button>
							<button type="button" class="list" onclick="location.href='/port/list.do'">목록</button>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<!-- <div class="side">
			<h2>JSL커뮤니티</h2>
			<ul class="menu">
				<li><a href="" class="active">공지사항</a></li>
				<li><a href="">일본취업정보</a></li>
				<li><a href="">질문답변</a></li>
				<li><a href="">인터뷰</a></li>
			</ul>
		</div> -->
	</div>
	
<script>

	function check() {
		if(!my.title.value) {
			alert("제목입력");
			my.title.focus();
			return false;
		}
		if(!my.content.value) {
			alert("내용입력");
			my.content.focus();
			return false;
		}
		
		alert("포트폴리오 수정 완료!")
		return true;
	}
	
	
	
	
	$("#btn-ai").click(function() {
		let title = $("#title").val();
		if(!title) {
			alert("제목 입력");
			return;
		}
		$.ajax({
			type:"post",
			url:"/port/aiWrite.do",
			data:{title:title},
			success:function(data) {
				//data 변수에 Ai가 글쓴내용이 넘어온다
				$("#content").val(data.content);
			},error:function() {
				alert("AI 통신에러");
			}
		})
	})

	/* $("#btn-translate").click(function() {
		let content = $("#content").val();
		if(!content) {
			alert("내용 입력");
			return;
		}
		$.ajax({
			type:"post",
			url:"/port//aiTranslate.do",
			data:{content:content},
			success:function(data) {
				//data 변수에 Ai가 글쓴내용이 넘어온다
				$("#content").val(data.translated);
			},error:function() {
				alert("AI 통신에러");
			}
		})
	}) */
	
	$("#btn-translate").on("change",function() {
		let lang = $(this).val();
		let content = $("#content").val();
		if(!lang) {
			alert("번역할 언어를 선택");
			return;
		}
		if(!content) {
			alert("내용 입력");
			return;
		}
		$.ajax({
			type:"post",
			url:"/port//aiTranslate.do",
			data:{lang:lang,content:content},
			success:function(data) {
				//data 변수에 Ai가 글쓴내용이 넘어온다
				$("#content").val(data.translated);
			},error:function() {
				alert("AI 통신에러");
			}
		})
	})
	
</script>
	
<%@ include file="../footer.jsp" %>	






















