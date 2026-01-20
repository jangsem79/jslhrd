package service;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.BlogDao;
import model.Command;

public class ReplyDelete implements Command {

	@Override
	public void doCommand(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 댓글 삭제처리: 클라이언트로는 반드시 plain text "success" 또는 "fail"만 반환
		request.setCharacterEncoding("utf-8");

		// 세션 체크 (미로그인도 fail 반환)
		HttpSession session = request.getSession(false);
		String userid = (session != null) ? (String) session.getAttribute("userid") : null;

		String bnoParam = request.getParameter("bno");

		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain;charset=utf-8");

		if (userid == null || userid.isEmpty()) {
			response.getWriter().write("fail");
			return;
		}

		if (bnoParam == null || bnoParam.isEmpty()) {
			response.getWriter().write("fail");
			return;
		}

		try {
			int bno = Integer.parseInt(bnoParam);
			BlogDao dao = new BlogDao();
			boolean deleted = dao.replyDelete(bno, userid);
			if (deleted) {
				response.getWriter().write("success");
			} else {
				response.getWriter().write("fail");
			}
		} catch (NumberFormatException e) {
			response.getWriter().write("fail");
		}
	}

}