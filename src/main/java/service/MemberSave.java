package service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Command;
import model.MemberDao;
import model.MemberDto;
import util.PaswordBcrypt;

public class MemberSave implements Command{

	@Override
	public void doCommand(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//DAO하고 연동해서 isnert처리 하는 구현부를 작성
		
		request.setCharacterEncoding("utf-8");
		String writer = request.getParameter("writer");
		String userid = request.getParameter("userid");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		
		MemberDto dto = new MemberDto();
		
		dto.setEmail(email);
		dto.setWriter(writer);
		dto.setUserid(userid);
		//패스워드 암호화 처리 필요
		String hashpassword = PaswordBcrypt.hashPassword(password);
		dto.setPassword(hashpassword);
		dto.setPhone(phone);
		
//		MemberDao dao = new MemberDao();
//		dao.memberSave(dto);
		new MemberDao().memberSave(dto);
		
		response.sendRedirect("/");
	}

}










