package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.BlogAiTranslate;
import service.BlogAiWrite;
import service.BlogDelete;
import service.BlogSelectAll;
import service.BlogUpdate;
import service.BlogView;
import service.BlogWrite;
import service.MyWish;
import service.ReplyDelete;
import service.ReplyInsert;
import service.ReplyUpdate;

//한개의 서블릿으로 여러 요청을 처리 하려고 한다

@WebServlet("/port/*")
@MultipartConfig(
		fileSizeThreshold = 1024 * 1024 * 2, //2MB 메모리 또는 임시폴더에 잠깐 저장
		maxFileSize = 1024 * 1024 * 10, //10MB 파일 1개당 최대 크기
		maxRequestSize = 1024 * 1024 * 50 //50MB 폼 전체 합산 크기 파일 여러개+텍스트 합쳐서 50MB까지 허용
		)
//@MultipartConfig Servlet에서 파일 업로드를 가능하게 하는 설정
//이거 없으면 request.getPart() 에러 난다

public class BlogController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public BlogController() {
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
//		String uri = request.getRequestURI(); // localhost:8088/port/list.do
//		System.out.println("url: " + uri);
//		String commend = uri.substring(uri.lastIndexOf("/")+1); // list.do
//		System.out.println("commend: " + commend);
		
		//게시판 목록 : list.do, 게시판 글쓰기 : write.do, 게시판 상세보기 : view.do
		String uri = request.getPathInfo();
		System.out.println("uri: " + uri);
		
		String page=null;
		
		switch(uri) {
		case "/list.do":
			new BlogSelectAll().doCommand(request, response);
			page="/portfolio/list.jsp";
			break;
		case "/write.do":
			page="/portfolio/write.jsp";
			break;
		case "/aiWrite.do":
			new BlogAiWrite().doCommand(request, response);
			break;
		case "/aiTranslate.do":
			new BlogAiTranslate().doCommand(request, response);
			break;
		case "/writepro.do":
			new BlogWrite().doCommand(request, response);
			break;
		case "/view.do":
			new BlogView().doCommand(request, response);
			page="/portfolio/view.jsp";
			break;
		case "/update.do":
			new BlogView().doCommand(request, response);
			page="/portfolio/update.jsp";
			break;
		case "/updatepro.do":
			new BlogUpdate().doCommand(request, response);
			break;
		case "/delete.do":
			new BlogDelete().doCommand(request, response);
			break;
		case "/mywish.do":
			new MyWish().doCommand(request, response);
			break;
		case "/replyAdd.do":
			new ReplyInsert().doCommand(request, response);
			break;
		case "/replyDelete.do":
			new ReplyDelete().doCommand(request, response);
			break;
		case "/replyUpdate.do":
			new ReplyUpdate().doCommand(request, response);
			break;
		default:
			System.out.println("잘못된 요청입니다.");
			break;
		}
		if(page != null) {
			RequestDispatcher rd = request.getRequestDispatcher(page);
			rd.forward(request, response);
		}
		
	}
}












