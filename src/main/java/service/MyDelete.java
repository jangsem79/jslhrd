package service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Command;
import model.MemberDao;

public class MyDelete implements Command {

	@Override
	public void doCommand(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		
		int wishBno = Integer.parseInt(request.getParameter("wishBno"));
		boolean result = new MemberDao().deleteWish(wishBno);
		
		response.getWriter().write(result?"success":"fail");
		//ajax 호출한 곳으로 참이면 success, 거짓이면 fail 글자를 비동기식으로 보내줘라
	}
}














