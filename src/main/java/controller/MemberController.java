package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.UseridRecommend;
import service.MemberLogin;
import service.MemberLogout;
import service.MemberSave;
import service.MyDelete;
import service.MyWishList;
import service.UserIdCheck;


@WebServlet("/mem/*")
public class MemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public MemberController() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}
	
	protected void doAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		String uri = request.getPathInfo();
		System.out.println("uri: " + uri);
		
		String page=null;
		
		switch(uri) {
		case "/login.do":
			page="/member/login.jsp";
			break;
		case "/join.do":
			page="/member/join.jsp";
			break;
		case "/useridCheck.do":
			new UserIdCheck().doCommand(request, response);
			break;
		case "/membersave.do":
			new MemberSave().doCommand(request, response);
			break;
		case "/loginpro.do":
			new MemberLogin().doCommand(request, response);
			break;
		case "/logout.do":
			new MemberLogout().doCommand(request, response);
			break;
		case "/mypage.do":
			new MyWishList().doCommand(request, response);
			page="/member/wishlist.jsp";
			break;
		case "/mywishDelete.do":
			new MyDelete().doCommand(request, response);
			break;
		case "/useridRecommend.do":
			new UseridRecommend().doCommand(request, response);
			break;
		
		
		}
		
		if(page != null) {
			request.getRequestDispatcher(page).forward(request, response);
		}
	}

}







