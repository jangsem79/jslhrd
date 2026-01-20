package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.GoogleUser;
import service.GoogleAuthService;


@WebServlet("/google/callback")
public class GoogleAuthServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public GoogleAuthServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    private GoogleAuthService googleAuthService;
    
    @Override
    public void init() {
        googleAuthService = new GoogleAuthService(getServletContext());
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String code = request.getParameter("code");

        GoogleUser googleUser = googleAuthService.getGoogleUser(code);

        // 로그인 처리
        HttpSession session = request.getSession();
        session.setAttribute("userid", googleUser);

        response.sendRedirect(request.getContextPath() + "/");
        return;
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
