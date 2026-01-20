package service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import model.BlogDao;
import model.Command;
import model.ReplyDto;

public class ReplyInsert implements Command {

	@Override
	public void doCommand(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		
		int blog_bno = Integer.parseInt(request.getParameter("bno"));
		//블러그 번호
		// 서버에서는 세션에 저장된 userid를 사용 (클라이언트 파라미터는 신뢰하지 않음)
		String userid = (String) request.getSession().getAttribute("userid");
		String replytext = request.getParameter("replytext");
		
		if(userid == null || userid.isEmpty()) {
			response.setCharacterEncoding("utf-8");
			response.setContentType("application/json;charset=utf-8");
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getWriter().write("{\"error\":\"not_logged_in\"}");
			return;
		}
		
		ReplyDto dto = new ReplyDto();
		
		dto.setBlog_bno(blog_bno);
		dto.setUserid(userid);
		dto.setReplytext(replytext);
		
		//DB에 댓글 insert
		BlogDao dao = new BlogDao();
		ReplyDto rdto = dao.replyInsert(dto);
		
		//insert된 댓글을 JSON으로 응답
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=utf-8");
		if(rdto == null) {
			response.getWriter().write("null");
			return;
		}
		
		//gson 라이브러리 이용하여 Java객체 -> JSON 문자열 변환
		Gson gson = new Gson();
		String json = gson.toJson(rdto);
		response.getWriter().write(json);
		
		

	}



}