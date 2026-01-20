package service;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.BlogDao;
import model.BlogDto;
import model.Command;
import model.ReplyDto;

public class BlogView implements Command {

	@Override
	public void doCommand(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		
		int bno = Integer.parseInt(request.getParameter("bno"));
		BlogDao dao = new BlogDao();
		//조회수증가
		dao.viewCount(bno);
		
		BlogDto dto = dao.getSelectByBno(bno);
		request.setAttribute("viewdto", dto);
		
		List<ReplyDto> list = dao.seelctReplyByBno(bno);
		request.setAttribute("replyList", list);
		
		
	}
}










