<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../header.jsp"%>

<!-- main visual -->

<div class="subvisual">
	<div class="sub-inner">
		<div class="sub-title">
			<h2>PORTPOLIO</h2>
			<p>Let's be treated as a professional with patience and
				creativity and challenge ourselves to the end</p>
		</div>
	</div>
</div>

<!-- content -->

<div class="sub-container">
	<div class="sub-left">
		<h3 class="subtit">포트폴리오</h3>
		<div class="btn-sns">
			<a href="">블러그</a> <a href="">페이스북</a> <a href="">인스타그램</a>
		</div>
	</div>
	<div class="board-view">
		<div class="board-header">
			<span class="date">Regdate : ${viewdto.regdate.substring(0,10) }</span>
			<Strong>${viewdto.title }</Strong> <span class="icon-view">views
				: ${viewdto.views }</span>
		</div>
		<div class="board-body">${viewdto.content }</div>
		<div class="board-controller">
			<a href="" class="prev"> <strong>Previous</strong> <span>유럽여행</span>
			</a> <a href="" class="next"> <strong>Next</strong> <span>에베레스트
					여행</span>
			</a>
		</div>
		<div class="btn-board">
			<button type="button" class="submit"
				onclick="location.href='${pageContext.request.contextPath}/port/list.do'">목록</button>
			<button type="button" class="reset"
				onclick="updateGet(${viewdto.bno})">수정</button>
			<button type="button" class="list"
				onclick="deleteGet(${viewdto.bno})">삭제</button>
		</div>

		<!-- Comments section (HTML + CSS only) -->


		<div class="comments">
			<h4>댓글</h4>
			<div class="reply-form" role="form" aria-label="댓글 작성폼">
				<div class="col">
					<label for="replyAuthor">글쓴이</label> 
					<input id="userid" 	
						name="userid" type="text" maxlength="20"
						placeholder="아이디 입력 (익명 가능)" 
						value="${sessionScope.userid }"
						readonly>
				</div>
				<div class="col">
					<label for="replyContent">댓글</label>
					<textarea id="replytext" name="replytext" placeholder="댓글을 입력하세요."></textarea>
					<div class="actions">
						<button id="replySubmit" class="submit" type="button">등록</button>
						<button id="replyCancel" class="cancel" type="button">취소</button>
					</div>
				</div>
			</div>

			<!-- Pre-rendered list of replies -->
			<div id="replyList" class="reply-list">
				<c:forEach var="r" items="${replyList}">
					<div class="reply-item" data-id="${r.bno}">
						<div class="reply-meta">
							<span class="reply-author">${r.userid }</span>
							<span class="reply-date">${r.regdate}</span>
						</div>
						<div class="reply-content">${r.replytext }</div>
						<div class="reply-controls">
							<button class="btn-edit">수정</button>
							<button class="btn-delete">삭제</button>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>
</div>


<!-- footer -->

<script>

	function deleteGet(bno) {
		if(confirm("삭제할래요?")) {
			location.href="/port/delete.do?bno="+bno;
		}
	}
	
	function updateGet(bno) {
		if(confirm("수정 할래요?")) {
			location.href="/port/update.do?bno="+bno;
		}
	}

	// 댓글 등록, jQuery 사용, AJAX로 비동기 처리
	$('#replySubmit').on('click', function() {
        const bno = ${viewdto.bno};
        //서버전송할 블러그 번호
        const replytext = $('#replytext').val().trim();
        //서버 전송할 내용
	    
	    if (replytext === '') {
            alert('댓글 내용을 입력하세요.');
            return;
        }

        $.ajax({
            url: '${pageContext.request.contextPath}/port/replyAdd.do',
            type: 'POST',
            data: {
                bno: bno,
                replytext: replytext
            },
            dataType: 'json',
            success: function(data) {
                if(!data || data === null) {
                    alert('댓글 등록에 실패했습니다.');
                    return;
                }
                // 추가한 댓글은 새로고침 안 하고 바로 보이도록 처리
                const $item = $(
                    '<div class="reply-item" data-id="'+data.bno+'">'+
                        '<div class="reply-meta">'+
                            '<span class="reply-author">'+data.userid+'</span>'+
                            '<span class="reply-date">'+data.regdate+'</span>'+
                        '</div>'+
                        '<div class="reply-content">'+data.replytext+'</div>'+
                        '<div class="reply-controls">'+
                            '<button class="btn-edit">수정</button>'+
                            '<button class="btn-delete">삭제</button>'+
                        '</div>'+
                    '</div>'
                );

                $('#replyList').append($item);
                $('#replytext').val('');
            },
            error: function(xhr, status, error) {
                if(xhr.status === 401) {
                    alert('로그인 후 이용해주세요.');
                    return;
                }
                console.error('AJAX Error:', status, error, xhr.responseText);
                alert('댓글 등록에 실패했습니다.');
            }
        });
    });
      
	//댓글 삭제, jQuery 사용, AJAX로 비동기 처리, 자기글 만 삭제 가능
	$('#replyList').on('click', '.btn-delete', function() {
		
		//클라이언트에서 로그인 체크
		const userid = '${sessionScope.userid}';
		if(!userid) {
        	alert('로그인 후 이용해주세요.');
            return;
        }
		
	    const $replyItem = $(this).closest('.reply-item');
	    const replyBno = $replyItem.data('id');

	    
	    if (!confirm('댓글을 삭제하시겠습니까?')) {
	        return;
	    }

	    $.ajax({
	        url: '${pageContext.request.contextPath}/port/replyDelete.do',
	        type: 'POST',
	        data: { bno: replyBno },
	        // 서버가 plain text "success" 또는 "fail"을 반환하므로 dataType을 사용하지 않음
	        success: function(response) {
	            var res = (typeof response === 'string') ? response.trim() : (response + '').trim();
	            if (res === 'success') {
	                $replyItem.remove();
	                return;
	            }
	            alert('댓글 삭제에 실패했습니다.');
	        },
	        error: function(xhr, status, error) {
	            console.error('AJAX Error:', status, error, xhr.responseText);
	            alert('댓글 삭제에 실패했습니다.');
	        }
	    });
	});

	//댓글 수정, jQuery 사용, AJAX로 비동기 처리, 자기글 만 수정 가능, 모달창으로 수정
	
	$('#replyList').on('click', '.btn-edit', function() {
        
        //클라이언트에서 로그인 체크
        const userid = '${sessionScope.userid}';
        if(!userid) {
        	alert('로그인 후 이용해주세요.');
            return;
        }
        
        const $replyItem = $(this).closest('.reply-item');
        const replyBno = $replyItem.data('id');
        const currentText = $replyItem.find('.reply-content').text();

        const newText = prompt('댓글을 수정하세요:', currentText);
        if (newText === null || newText.trim() === '') {
            return; // 취소 또는 빈 문자열인 경우 종료
        }

        $.ajax({
            url: '${pageContext.request.contextPath}/port/replyUpdate.do',
            type: 'POST',
            data: {
                bno: replyBno,
                replytext: newText.trim()
            },
            success: function(response) {
                var res = (typeof response === 'string') ? response.trim() : (response + '').trim();
                if (res === 'success') {
                    $replyItem.find('.reply-content').text(newText.trim());
                    return;
                }
                alert('댓글 수정에 실패했습니다.');
            },
            error: function(xhr, status, error) {
                console.error('AJAX Error:', status, error, xhr.responseText);
                alert('댓글 수정에 실패했습니다.');
            }
        });
    });
	
	
</script>

<%@ include file="../footer.jsp"%>