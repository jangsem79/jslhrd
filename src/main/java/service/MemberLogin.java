package service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Command;
import model.MemberDao;
import model.MemberDto;
import util.PaswordBcrypt;

public class MemberLogin implements Command {

	@Override
	public void doCommand(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		String userid = request.getParameter("userid");
		String password = request.getParameter("password");
		
		 MemberDao dao = new MemberDao();
		 MemberDto dto = dao.searchUserId(userid);
		 
		 //아이디가 존재하고 비밀번호가 일치하면
		 if(dto != null && PaswordBcrypt.checkPassword(password, dto.getPassword())) {
			 //세션생성
			 HttpSession session = request.getSession();
			 //로그인한 아이디를 userid 세션 속성에 저장
			 session.setAttribute("userid", dto.getUserid());
			 response.getWriter().print("success"); //ajax로 success문자를 보낸다
		 }
		 
		 
	}

}








