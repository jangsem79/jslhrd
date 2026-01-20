package service;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.BlogDao;
import model.Command;

public class ReplyUpdate implements Command {

    @Override
    public void doCommand(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");

        // 세션에서 userid 확인
        HttpSession session = request.getSession(false);
        String userid = (session != null) ? (String) session.getAttribute("userid") : null;

        response.setCharacterEncoding("utf-8");
        response.setContentType("text/plain;charset=utf-8");

        if (userid == null || userid.isEmpty()) {
            response.getWriter().write("fail");
            return;
        }

        String bnoParam = request.getParameter("bno");
        String replytext = request.getParameter("replytext");

        if (bnoParam == null || bnoParam.isEmpty() || replytext == null || replytext.trim().isEmpty()) {
            response.getWriter().write("fail");
            return;
        }

        try {
            int bno = Integer.parseInt(bnoParam);
            BlogDao dao = new BlogDao();
            boolean updated = dao.replyUpdate(bno, userid, replytext.trim());
            if (updated) {
                response.getWriter().write("success");
            } else {
                response.getWriter().write("fail");
            }
        } catch (NumberFormatException e) {
            response.getWriter().write("fail");
        }
    }

}
